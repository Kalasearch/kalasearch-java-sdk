package com.kalasearch.client.entity;


import lombok.Builder;
import lombok.Data;

/**
 * kalasearch server config
 * @author tomsun28
 * @date 2020-08-26 21:47
 */
@Data
@Builder
public class Config {

    private String domain;

    private String appId;

    private String appKey;
}
