package com.example.distributelock.lock;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Value;

public class ZookeeperLock {
    @Value("${zookeeper.address}")
    private String zookeeperAddress;
    @Value("${zookeeper.session.timeout}")
    private Integer sessionTimeout;
    @Value("${zookeeper.connection.timeout}")
    private Integer connectionTimeout;
    @Value("${zookeeper.baseSleepTimeMs}")
    private Integer baseSleepTimeMs;
    @Value("${zookeeper.maxRetries}")
    private Integer maxRetries;
    @Value("${zookeeper.maxSleepMs}")
    private Integer maxSleepMs;

    RetryPolicy retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs,maxRetries,maxSleepMs);
    CuratorFramework client = CuratorFrameworkFactory.newClient(
            zookeeperAddress, sessionTimeout, connectionTimeout, retryPolicy);

}
