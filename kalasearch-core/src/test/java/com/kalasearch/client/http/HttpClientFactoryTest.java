package com.kalasearch.client.http;

import com.google.gson.Gson;
import com.kalasearch.client.Index;
import com.kalasearch.client.KalasearchClient;
import com.kalasearch.client.entity.QueryInfo;
import com.kalasearch.client.entity.RespEntity;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tomsun28
 * @date 2020-08-27 00:37
 */
public class HttpClientFactoryTest {

    @Test
    public void getHttpClient() {
        KalasearchClient kalasearchClient = new KalasearchClient("YOUR AppId", "YOUR ApiKey");

        Index index = kalasearchClient.getIndex("your index");

        Gson gson = new Gson();
        String document = gson.fromJson("{'name': '大话西游', 'actors': '周星驰/吴孟达', 'year':2000}", String.class);
        RespEntity indexResp = index.addObject(document);
        QueryInfo queryInfo = QueryInfo.builder().query("孟达").build();
        RespEntity queryResp = index.search(queryInfo);
        // todo

    }
}