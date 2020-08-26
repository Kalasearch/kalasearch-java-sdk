package com.kalasearch.client;

import com.kalasearch.client.entity.Config;
import com.kalasearch.client.entity.RespEntity;
import com.kalasearch.client.http.HttpClient;

/**
 * @author tomsun28
 * @date 2020-08-26 22:56
 */
public class Index {

    private final String indexId;

    private final Config config;

    public Index(Config config, String indexId) {
        this.indexId = indexId;
        this.config = config;
    }

    public RespEntity getInfo() {
        String path = String.format("indexes/%s/info", this.indexId);
        return HttpClient.get(this.config, path, RespEntity.class);
    }

    public RespEntity addObject(Object document) {
        String path = String.format("indexes/%s/objects", this.indexId);
        return HttpClient.post(this.config, path, document, RespEntity.class);
    }

    public RespEntity addObjects(Object document) {
        String path = String.format("indexes/%s/objects/batch", this.indexId);
        return HttpClient.post(this.config, path, document, RespEntity.class);
    }

    public RespEntity deleteObject(String objectId) {
        String path = String.format("indexes/%s/objects/%s", this.indexId, objectId);
        return HttpClient.delete(this.config, path, RespEntity.class);
    }

    public RespEntity updateObject(String objectId, Object document) {
        String path = String.format("indexes/%s/objects/%s", this.indexId, objectId);
        return HttpClient.put(this.config, path, document, RespEntity.class);
    }

    // todo search

}
