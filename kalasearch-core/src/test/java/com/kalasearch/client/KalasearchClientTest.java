package com.kalasearch.client;

import com.kalasearch.client.http.HttpClientFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tomsun28
 * @date 2020-08-28 01:04
 */
public class KalasearchClientTest {

    @Test
    public void getIndex() {
        KalasearchClient kalasearchClient = new KalasearchClient("appId",
                "appKey");
        Index<TestDocument> index = kalasearchClient.getIndex("indexId");
        assertNotNull(index);
    }

    @Test
    public void close() {
        KalasearchClient kalasearchClient = new KalasearchClient("xxx", "iii");
        CloseableHttpClient client = HttpClientFactory.getHttpClient();
        assertNotNull(client);
        kalasearchClient.close();
    }
}