package com.kalasearch.client.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * http client
 * @author tomsun28
 * @date 2020-08-10 22:15
 */
@Slf4j
public class HttpClientFactory {

    private static CloseableHttpClient httpClient;

    /**
     * 最大连接数
     */
    private static final int MAX_TOTAL_CONNECTIONS = 200;

    /**
     * 每个路由最大连接数
     */
    private static final int MAX_PER_ROUTE_CONNECTIONS = 200;

    /**
     * 从连接池获取连接的超时时间 20s
     */
    private static final int GET_CONNECT_TIMEOUT = 20000;

    /**
     * 建立连接的超时时间 20s
     */
    private static int CONNECT_TIMEOUT = 20000;

    /**
     * socket数据传输过程中数据包之间间隔的最大超时时间 60s
     */
    private static int SOCKET_TIMEOUT = 60000;

    /**
     * 空闲连接过期时间，重用空闲连接时会先检查判断是否空闲时间超过此时间，若超过此时间则释放此连接socket重建
     */
    private static final int VALIDATE_AFTER_INACTIVITY = 60 * 1000;

    /**
     * 所支持的ssl版本
     */
    private static final String[] SUPPORT_SSL = {"TLSv1", "TLSv1.1", "TLSv1.2", "SSLv3"};

    static {
        // 初始化连接池管理器 生成 httpClient
        try {
            // 构建ssl上下文
            SSLContext sslContext = SSLContexts.createDefault();
            X509TrustManager x509TrustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {}

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {}

                @Override
                public X509Certificate[] getAcceptedIssuers() { return null; }
            };
            sslContext.init(null, new TrustManager[]{x509TrustManager}, null);

            // 设置所支持ssl版本
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    SUPPORT_SSL, null, new NoopHostnameVerifier());

            // 注册http https
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", sslConnectionSocketFactory)
                    .build();

            // 连接池
            PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);

            // 设置参数
            connectionManager.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            connectionManager.setDefaultMaxPerRoute(MAX_PER_ROUTE_CONNECTIONS);
            connectionManager.setValidateAfterInactivity(VALIDATE_AFTER_INACTIVITY);

            // connect 请求默认配置参数
            RequestConfig requestConfig = RequestConfig.custom()
                    // 从连接池获取连接的超时时间
                    .setConnectionRequestTimeout(GET_CONNECT_TIMEOUT)
                    // 连接建立的超时时间，三次握手完成时间
                    .setConnectTimeout(CONNECT_TIMEOUT)
                    // 数据传输过程中数据包之间间隔的最大时间
                    .setSocketTimeout(SOCKET_TIMEOUT)
                    // 设置不响应 301 302，即不重定向自动跳转
                    .setRedirectsEnabled(false)
                    .build();

            // build httpClient
            httpClient = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .setConnectionManager(connectionManager)
                    // 默认的重试策略
                    .setRetryHandler(DefaultHttpRequestRetryHandler.INSTANCE)
                    // 定期清理不可用过期连接
                    .evictExpiredConnections()
                    // 定期清理长时间的空闲可用连接
                    .evictIdleConnections(1, TimeUnit.MINUTES)
                    .build();

        } catch (Exception e) {
            log.error("init httpClient error happen. {}.", e.getMessage(), e);
        }
    }

    /**
     * 获取httpClient实例
     * @return client
     */
    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static int getConnectTimeout() {
        return CONNECT_TIMEOUT;
    }

    public static void setConnectTimeout(int connectTimeout) {
        CONNECT_TIMEOUT = connectTimeout;
    }

    public static int getSocketTimeout() {
        return SOCKET_TIMEOUT;
    }

    public static void setSocketTimeout(int socketTimeout) {
        SOCKET_TIMEOUT = socketTimeout;
    }
}
