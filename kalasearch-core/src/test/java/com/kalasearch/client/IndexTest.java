package com.kalasearch.client;

import com.kalasearch.client.entity.IndexInfo;
import com.kalasearch.client.entity.QueryInfo;
import com.kalasearch.client.entity.RespEntity;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

/**
 * @author tomsun28
 * @date 2020-08-28 01:11
 */
public class IndexTest {

    private static KalasearchClient kalasearchClient;
    private static Index<TestDocument> index;

    @BeforeClass
    public static void before() {
        assertNull(kalasearchClient);
        assertNull(index);
        kalasearchClient = new KalasearchClient("6163c6bf-98e3-4e49-ada1-857d15b84bc2",
                "c740cd54-935b-4b6d-a2d4-076ecb122639");
        index = kalasearchClient.getIndex("17bdfa83-2443-426f-b45e-c955053891ff");
        assertNotNull(kalasearchClient);
        assertNotNull(index);
    }

    @AfterClass
    public static void after() {
        assertNotNull(kalasearchClient);
        kalasearchClient.close();
    }

    @Test
    public void getInfo() {
        Optional<IndexInfo> optional = index.getInfo();
        assertTrue(optional.isPresent());
        assertTrue(optional.get().getNumOfDocs() >= 0);
    }

    @Test
    public void addObject() {
        TestDocument testDocument = TestDocument.builder().name("大话西游").actors("周星驰/吴孟达").year("2000").build();
        Optional<RespEntity<TestDocument>> optional = index.addObject(testDocument);
        assertTrue(optional.isPresent());
        RespEntity<TestDocument> indexResp = optional.get();
        assertNotNull(indexResp.getId());
    }

    @Test
    public void addObjects() {
        TestDocument[] documents = new TestDocument[20];
        for (int i = 0; i < 20; i++) {
            documents[i] = TestDocument.builder().name("大话西游" + i)
                    .actors("周星驰/吴孟达" + i).year("2000" + i).build();
        }
        Optional<RespEntity<TestDocument>> optional = index.addObjects(documents);
        assertTrue(optional.isPresent());
        RespEntity<TestDocument> indexResp = optional.get();
        assertNotNull(indexResp.getId());
        assertEquals("STARTING", indexResp.getStatus());
    }

    @Test
    public void deleteObject() {
        TestDocument testDocument = TestDocument.builder().name("大话西游").actors("周星驰/吴孟达").year("2000").build();
        Optional<RespEntity<TestDocument>> optional = index.addObject(testDocument);
        assertTrue(optional.isPresent());
        RespEntity<TestDocument> indexResp = optional.get();
        String objectId = indexResp.getId();
        assertNotNull(objectId);
        optional = index.deleteObject(objectId);
        assertTrue(optional.isPresent());
        assertNotNull(optional.get().getId());
    }

    @Test
    public void updateObject() {
        TestDocument testDocument = TestDocument.builder().name("大话西游").actors("周星驰/吴孟达").year("2000").build();
        Optional<RespEntity<TestDocument>> optional = index.addObject(testDocument);
        assertTrue(optional.isPresent());
        RespEntity<TestDocument> indexResp = optional.get();
        String objectId = indexResp.getId();
        testDocument = TestDocument.builder().name("大话西游").actors("周杰伦/吴孟达").year("2020").build();
        optional = index.updateObject(objectId, testDocument);
        assertTrue(optional.isPresent());
        assertNotNull(optional.get().getId());
    }

    @Test
    public void search() {
        TestDocument testDocument = TestDocument.builder().name("大话西游").actors("周星驰/吴孟达").year("2000").build();
        index.addObject(testDocument);
        QueryInfo queryInfo = QueryInfo.builder().query("孟达").build();
        Optional<RespEntity<TestDocument>> optional = index.search(queryInfo);
        assertTrue(optional.isPresent());
        assertNotNull(optional.get().getHits());
    }
}