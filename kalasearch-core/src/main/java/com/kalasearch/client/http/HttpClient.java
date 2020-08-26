package com.kalasearch.client.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalasearch.client.entity.Config;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import java.io.IOException;

/**
 * httpClient util, support http get post put delete
 * @author tomsun28
 * @date 2020-08-26 23:07
 */
@Slf4j
public class HttpClient {


    public static <T> T get(@NonNull Config config, @NonNull String path, @NonNull Class<T> t) {
        CloseableHttpClient client = HttpClientFactory.getHttpClient();
        HttpGet httpGet = new HttpGet(config.getDomain() + path);
        try {
            httpGet.addHeader("X-Kalasearch-Id", config.getAppId());
            httpGet.addHeader("X-Kalasearch-Key", config.getAppKey());
            httpGet.addHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(httpGet);
            if (isValidateResponseCode(response.getStatusLine().getStatusCode())) {
                return null;
            }
            String result = EntityUtils.toString(response.getEntity());
            if (log.isDebugEnabled()) {
                log.debug(result);
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.fromJson(result, t);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    public static <T> T post(@NonNull Config config, @NonNull String path, Object body, @NonNull Class<T> t) {
        CloseableHttpClient client = HttpClientFactory.getHttpClient();
        HttpPost httpPost = new HttpPost(config.getDomain() + path);
        try {
            httpPost.addHeader("X-Kalasearch-Id", config.getAppId());
            httpPost.addHeader("X-Kalasearch-Key", config.getAppKey());
            httpPost.addHeader("Content-Type", "application/json");
            Gson gson = new GsonBuilder().serializeNulls().create();
            StringEntity entity = new StringEntity(gson.toJson(body));
            entity.setContentEncoding("application/json");
            entity.setContentType("text/json");
            httpPost.setEntity(entity);
            HttpResponse response = client.execute(httpPost);
            if (isValidateResponseCode(response.getStatusLine().getStatusCode())) {
                return null;
            }
            String result = EntityUtils.toString(response.getEntity());
            if (log.isDebugEnabled()) {
                log.debug(result);
            }
            return gson.fromJson(result, t);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    public static <T> T put(@NonNull Config config, @NonNull String path, Object body, @NonNull Class<T> t) {
        CloseableHttpClient client = HttpClientFactory.getHttpClient();
        HttpPut httpPut = new HttpPut(config.getDomain() + path);
        try {
            httpPut.addHeader("X-Kalasearch-Id", config.getAppId());
            httpPut.addHeader("X-Kalasearch-Key", config.getAppKey());
            httpPut.addHeader("Content-Type", "application/json");
            Gson gson = new GsonBuilder().serializeNulls().create();
            StringEntity entity = new StringEntity(gson.toJson(body));
            entity.setContentEncoding("application/json");
            entity.setContentType("text/json");
            httpPut.setEntity(entity);
            HttpResponse response = client.execute(httpPut);
            if (isValidateResponseCode(response.getStatusLine().getStatusCode())) {
                return null;
            }
            String result = EntityUtils.toString(response.getEntity());
            if (log.isDebugEnabled()) {
                log.debug(result);
            }
            return gson.fromJson(result, t);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpPut.releaseConnection();
        }
        return null;
    }

    public static <T> T delete(@NonNull Config config, @NonNull String path, @NonNull Class<T> t) {
        CloseableHttpClient client = HttpClientFactory.getHttpClient();
        HttpDelete httpDelete = new HttpDelete(config.getDomain() + path);
        try {
            httpDelete.addHeader("X-Kalasearch-Id", config.getAppId());
            httpDelete.addHeader("X-Kalasearch-Key", config.getAppKey());
            httpDelete.addHeader("Content-Type", "application/json");
            HttpResponse response = client.execute(httpDelete);
            if (isValidateResponseCode(response.getStatusLine().getStatusCode())) {
                return null;
            }
            String result = EntityUtils.toString(response.getEntity());
            if (log.isDebugEnabled()) {
                log.debug(result);
            }
            Gson gson = new GsonBuilder().serializeNulls().create();
            return gson.fromJson(result, t);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpDelete.releaseConnection();
        }
        return null;
    }

    private static boolean isValidateResponseCode(int statusCode) {
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            log.error("body格式不正确，请用 json检查器 检查格式");
            return true;
        } else if (statusCode == HttpStatus.SC_NOT_FOUND) {
            log.error("索引不存在，请检查indexId参数");
            return true;
        }
        // todo
        return false;
    }

    public static void close() {
        try {
            HttpClientFactory.getHttpClient().close();
        } catch (IOException e) {
            log.error("can not close kalasearch client, error: {} .", e.getMessage(), e);
        }
    }
}
