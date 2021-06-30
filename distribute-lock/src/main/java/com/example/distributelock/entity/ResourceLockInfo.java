package com.example.distributelock.entity;

import lombok.Builder;

import java.util.Date;
import javax.persistence.*;

@Builder
@Table(name = "resource_lock")
public class ResourceLockInfo {
    /**
     * 主键id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 资源标识
     */
    @Column(name = "resource_tag")
    private String resourceTag;

    /**
     * 请求唯一标识
     */
    @Column(name = "request_id")
    private String requestId;

    /**
     * 过期时间（单位：毫秒）
     */
    @Column(name = "expire_time")
    private Long expireTime;

    /**
     * 描述内容
     */
    @Column(name = "content_desc")
    private String contentDesc;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取资源标识
     *
     * @return resource_tag - 资源标识
     */
    public String getResourceTag() {
        return resourceTag;
    }

    /**
     * 设置资源标识
     *
     * @param resourceTag 资源标识
     */
    public void setResourceTag(String resourceTag) {
        this.resourceTag = resourceTag;
    }

    /**
     * 获取请求唯一标识
     *
     * @return request_id - 请求唯一标识
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * 设置请求唯一标识
     *
     * @param requestId 请求唯一标识
     */
    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    /**
     * 获取过期时间（单位：毫秒）
     *
     * @return expire_time - 过期时间（单位：毫秒）
     */
    public Long getExpireTime() {
        return expireTime;
    }

    /**
     * 设置过期时间（单位：毫秒）
     *
     * @param expireTime 过期时间（单位：毫秒）
     */
    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取描述内容
     *
     * @return content_desc - 描述内容
     */
    public String getContentDesc() {
        return contentDesc;
    }

    /**
     * 设置描述内容
     *
     * @param contentDesc 描述内容
     */
    public void setContentDesc(String contentDesc) {
        this.contentDesc = contentDesc;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}