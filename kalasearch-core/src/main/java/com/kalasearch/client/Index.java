package com.kalasearch.client;

import com.kalasearch.client.entity.Config;
import com.kalasearch.client.entity.IndexInfo;
import com.kalasearch.client.entity.QueryInfo;
import com.kalasearch.client.entity.RespEntity;
import com.kalasearch.client.http.HttpClientUtil;

import java.util.Optional;

/**
 * kalasearch index
 * @author tomsun28
 * @date 2020-08-26 22:56
 */
@SuppressWarnings("unchecked")
public class Index<T> {

    private final String indexId;

    private final Config config;

    public Index(Config config, String indexId) {
        this.indexId = indexId;
        this.config = config;
    }

    public Optional<IndexInfo> getInfo() {
        String path = String.format("indexes/%s/info", this.indexId);
        return Optional.ofNullable(HttpClientUtil.get(this.config, path, IndexInfo.class));
    }

    public Optional<RespEntity<T>> addObject(T document) {
        String path = String.format("indexes/%s/objects", this.indexId);
        return Optional.ofNullable(HttpClientUtil.post(this.config, path, document, RespEntity.class));
    }

    public Optional<RespEntity<T>> addObjects(T[] documents) {
        String path = String.format("indexes/%s/objects/batch", this.indexId);
        return Optional.ofNullable(HttpClientUtil.post(this.config, path, documents, RespEntity.class));
    }

    public Optional<RespEntity<T>> deleteObject(String objectId) {
        String path = String.format("indexes/%s/objects/%s", this.indexId, objectId);
        return Optional.ofNullable(HttpClientUtil.delete(this.config, path, RespEntity.class));
    }

    public Optional<RespEntity<T>> updateObject(String objectId, Object document) {
        String path = String.format("indexes/%s/objects/%s", this.indexId, objectId);
        return Optional.ofNullable(HttpClientUtil.put(this.config, path, document, RespEntity.class));
    }

    public Optional<RespEntity<T>> search(QueryInfo queryInfo) {
        String path = String.format("indexes/%s/query", this.indexId);
        return Optional.ofNullable(HttpClientUtil.post(this.config, path, queryInfo, RespEntity.class));
    }
}
