package com.kalasearch.client.http;

import com.kalasearch.client.Index;
import com.kalasearch.client.KalasearchClient;
import com.kalasearch.client.TestDocument;
import com.kalasearch.client.entity.QueryInfo;
import com.kalasearch.client.entity.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author tomsun28
 * @date 2020-08-27 00:37
 */
@Slf4j
public class HttpClientFactoryTest {

    @Test
    public void getHttpClient() {
        assertEquals(20000 ,HttpClientFactory.getConnectTimeout());
        assertEquals(60000 ,HttpClientFactory.getSocketTimeout());
        assertNotNull(HttpClientFactory.getHttpClient());


        KalasearchClient kalasearchClient = new KalasearchClient("6163c6bf-98e3-4e49-ada1-857d15b84bc2",
                "c740cd54-935b-4b6d-a2d4-076ecb122639");

        Index<TestDocument> index = kalasearchClient.getIndex("17bdfa83-2443-426f-b45e-c955053891ff");

        TestDocument testDocument = TestDocument.builder().name("大话西游").actors("周星驰/吴孟达").year("2000").build();
        Optional<RespEntity<TestDocument>> optional = index.addObject(testDocument);
        if (optional.isPresent()) {
            RespEntity<TestDocument> indexResp = optional.get();
        }

        QueryInfo queryInfo = QueryInfo.builder().query("孟达").build();
        Optional<RespEntity<TestDocument>> optionalRespEntity = index.search(queryInfo);
        if (optionalRespEntity.isPresent()) {
            RespEntity<TestDocument> queryResp = optionalRespEntity.get();
        }
    }
}