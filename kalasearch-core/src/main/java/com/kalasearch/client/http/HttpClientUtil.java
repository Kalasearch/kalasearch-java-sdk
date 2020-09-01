package com.kalasearch.client.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kalasearch.client.entity.Config;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * httpClient util, support http get post put delete
 * @author tomsun28
 * @date 2020-08-26 23:07
 */
@Slf4j
public class HttpClientUtil {


    public static <T> T get(@NonNull Config config, @NonNull String path, @NonNull Class<T> clazz) {
        HttpGet httpGet = new HttpGet(config.getDomain() + path);
        try {
            buildRequest(httpGet, config);
            return executeRequest(httpGet, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpGet.releaseConnection();
        }
        return null;
    }

    public static <T> T post(@NonNull Config config, @NonNull String path, Object body, @NonNull Class<T> clazz) {
        HttpPost httpPost = new HttpPost(config.getDomain() + path);
        try {
            buildRequest(httpPost, config, body);
            return executeRequest(httpPost, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpPost.releaseConnection();
        }
        return null;
    }

    public static <T> T put(@NonNull Config config, @NonNull String path, Object body, @NonNull Class<T> clazz) {
        HttpPut httpPut = new HttpPut(config.getDomain() + path);
        try {
            buildRequest(httpPut, config, body);
            return executeRequest(httpPut, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpPut.releaseConnection();
        }
        return null;
    }



    public static <T> T delete(@NonNull Config config, @NonNull String path, @NonNull Class<T> clazz) {
        HttpDelete httpDelete = new HttpDelete(config.getDomain() + path);
        try {
            buildRequest(httpDelete, config);
            return executeRequest(httpDelete, clazz);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            httpDelete.releaseConnection();
        }
        return null;
    }

    private static <T> T executeRequest(HttpRequestBase requestBase, Class<T> clazz) throws IOException {
        CloseableHttpClient client = HttpClientFactory.getHttpClient();
        HttpResponse response = client.execute(requestBase);
        if (isInvalidResponseCode(response.getStatusLine().getStatusCode())) {
            return null;
        }
        String result = EntityUtils.toString(response.getEntity());
        if (log.isDebugEnabled()) {
            log.debug(result);
        }
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(result, clazz);
    }

    private static boolean isInvalidResponseCode(int statusCode) {
        if (statusCode == HttpStatus.SC_BAD_REQUEST) {
            log.error("bad request, maybe the body format is incorrect, please check the format with json checker");
            return true;
        } else if (statusCode == HttpStatus.SC_NOT_FOUND) {
            log.error("maybe the index does not exist, please check");
            return true;
        } else if (statusCode == HttpStatus.SC_CONFLICT) {
            log.error("type conflict, the index not allow several type document");
            return true;
        } else if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR) {
            log.error("server error happen, please wait");
            return true;
        }
        return false;
    }

    private static void buildRequest(HttpRequestBase requestBase, Config config) {
        buildRequest(requestBase, config, null);
    }

    private static void buildRequest(HttpRequestBase requestBase, Config config, Object body) {
        Header[] headers = new Header[3];
        headers[0] = new BasicHeader("X-Kalasearch-Id", config.getAppId());
        headers[1] = new BasicHeader("X-Kalasearch-Key", config.getAppKey());
        headers[2] = new BasicHeader("Content-Type", "application/json");
        requestBase.setHeaders(headers);
        if (body != null && requestBase instanceof HttpEntityEnclosingRequestBase) {
            Gson gson = new GsonBuilder().create();
            StringEntity entity = new StringEntity(gson.toJson(body), StandardCharsets.UTF_8);
            entity.setContentEncoding("application/json");
            ((HttpEntityEnclosingRequestBase)requestBase).setEntity(entity);
        }
    }

    public static void close() {
        try {
            HttpClientFactory.getHttpClient().close();
        } catch (IOException e) {
            log.error("can not close kalasearch client, error: {} .", e.getMessage(), e);
        }
    }
}
