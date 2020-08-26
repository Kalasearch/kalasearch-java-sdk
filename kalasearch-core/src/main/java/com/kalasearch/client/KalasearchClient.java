package com.kalasearch.client;

import com.kalasearch.client.entity.Config;
import com.kalasearch.client.http.HttpClient;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * kalasearch client
 * @author tomsun28
 * @date 2020-08-26 21:40
 */
@Slf4j
public class KalasearchClient {

    private final Config config;

    public KalasearchClient(@NonNull String appId, @NonNull String appKey, @NonNull String domain) {
        this.config = Config.builder()
                .appId(appId).appKey(appKey)
                .domain(domain).build();
    }

    public KalasearchClient(@NonNull String appId, @NonNull String appKey) {
        this.config = Config.builder()
                .appId(appId).appKey(appKey)
                .domain("https://api.kalasearch.cn/v1").build();
    }

    public Index getIndex(@NonNull String indexId) {
        return new Index(this.config, indexId);
    }

    public void close() {
        HttpClient.close();
    }
}
