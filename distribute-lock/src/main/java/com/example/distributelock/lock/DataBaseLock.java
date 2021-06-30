package com.example.distributelock.lock;

import com.example.distributelock.entity.ResourceLockInfo;
import com.example.distributelock.entity.dto.ResourceLockInfoDTO;
import com.example.distributelock.mapper.ResourceLockInfoMapper;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class DataBaseLock {

    @Resource
    private ResourceLockInfoMapper resourceLockInfoMapper;

    @Transactional
    public boolean lock(ResourceLockInfoDTO lockRequest) {
        ResourceLockInfo lockInfo = resourceLockInfoMapper.selectOneByExample(
                Example.builder(ResourceLockInfo.class).where(Sqls.custom()
                        .andEqualTo("resourceTag", lockRequest.getResourceTag())).build());
        if (Objects.nonNull(lockInfo)) {
            // 判断是否是同一请求，实现锁的可重入性
            if(lockRequest.getRequestId().equals(lockInfo.getRequestId())) {
                // 通过更新时间，实现锁续期
                resourceLockInfoMapper.updateByPrimaryKeySelective(ResourceLockInfo.builder()
                        .updateTime(new Date()).id(lockInfo.getId()).build());
                return true;
            }
            return false;
        }

        // 不存在，插入新记录，直接获取锁
        ResourceLockInfo resourceLockInfo = ResourceLockInfo.builder()
                .resourceTag(lockRequest.getResourceTag())
                .requestId(lockRequest.getRequestId())
                .expireTime(lockRequest.getExpireTime())
                .contentDesc(lockRequest.getContentDesc())
                .createTime(new Date())
                .updateTime(new Date())
                .build();
        resourceLockInfoMapper.insert(resourceLockInfo);
        return true;
    }

    /**
     * 同一客户端处理完业务释放锁
     * @param resourceTag 锁资源标识
     * @param requestId 客户端请求唯一标识
     */
    public void release(String resourceTag, String requestId) {
        resourceLockInfoMapper.deleteByExample(Example.builder(ResourceLockInfo.class).where(Sqls.custom()
                .andEqualTo("resourceTag", resourceTag).andEqualTo("requestId", requestId)).build());
    }

    /**
     * 死锁后，定时任务释放过期锁
     * @param resourceTag 锁资源标识
     */
    public void release(String resourceTag) {
        resourceLockInfoMapper.deleteByExample(Example.builder(ResourceLockInfo.class).where(Sqls.custom()
                .andEqualTo("resourceTag", resourceTag)).build());
    }

    // no blocking
    public boolean tryLock(ResourceLockInfoDTO lockInfoDTO) {
        return lock(lockInfoDTO);
    }

    // blocking
    public boolean tryLock(ResourceLockInfoDTO lockInfoDTO, Long timeout, TimeUnit timeUnit) {
        long endTime = System.currentTimeMillis() + timeUnit.toMillis(timeout);
        while (true) {
            if (lock(lockInfoDTO)) {
                return true;
            }
            if (System.currentTimeMillis() > endTime){
                return false;
            }
        }
    }

}
