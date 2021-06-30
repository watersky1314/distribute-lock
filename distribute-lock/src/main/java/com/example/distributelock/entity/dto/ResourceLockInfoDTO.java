package com.example.distributelock.entity.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResourceLockInfoDTO implements Serializable {
    private static final long serialVersionUID = -5051594966825038199L;
    private String resourceTag;
    private String requestId;
    private Long expireTime;
    private String contentDesc;
}
