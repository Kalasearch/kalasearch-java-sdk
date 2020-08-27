package com.kalasearch.client.http;

import com.kalasearch.client.entity.Config;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author tomsun28
 * @date 2020-08-28 00:59
 */
public class HttpClientUtilTest {

    @Test
    public void get() {
        try {
            HttpClientUtil.get(Config.builder().build(), null, null);
        } catch (Exception e) {
            assertTrue( e instanceof NullPointerException);
        }
    }

    @Test
    public void post() {
        try {
            HttpClientUtil.post(Config.builder().build(), null, null, null);
        } catch (Exception e) {
            assertTrue( e instanceof NullPointerException);
        }
    }

    @Test
    public void put() {
        try {
            HttpClientUtil.put(Config.builder().build(), null, null, null);
        } catch (Exception e) {
            assertTrue( e instanceof NullPointerException);
        }
    }

    @Test
    public void delete() {
        try {
            HttpClientUtil.delete(Config.builder().build(), null, null);
        } catch (Exception e) {
            assertTrue( e instanceof NullPointerException);
        }
    }
}