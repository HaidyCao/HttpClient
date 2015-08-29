package android.util.http.client;

import android.util.http.Encoding;
import android.util.http.Header;
import android.util.http.Http;
import android.util.http.NameValuePair;
import android.util.http.entity.FormEntity;
import android.util.http.entity.HttpEntity;
import android.util.http.entity.StringEntity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Http Client
 * <p/>
 * Created by Haidy on 15/8/25.
 */
public class HttpClient {

    private String url;         // 要请求的网址

    private HttpURLConnection conn = null;

    protected boolean shutdown = false;

    /**
     * 连接超时
     */
    private static int connectionTimeout = 30 * 1000;

    /**
     * 读取超时
     */
    private static int soTimeout = 30 * 1000;

    /**
     * 连接超时message
     */
    private static String connectionTimeoutMessage = "connection time out";

    /**
     * read time message
     */
    private static String soTimeoutMessage = "read time out";

    private int connectionTimeoutOnce = connectionTimeout;

    private int soTimeoutOnce = soTimeout;

    /**
     * 请求的方法
     */
    private String requestMethod = Http.GET;
    protected HttpRequest httpRequest = new HttpRequest();
    protected HttpResponse httpResponse = new HttpResponse();

    /**
     * 是否重定向
     */
    private boolean redirection = false;

    /**
     * 请求成功的代码
     */
    protected int successfulCode = HttpURLConnection.HTTP_OK;

    public HttpClient() {
        this(null);
    }

    public HttpClient(String url) {
        setUrl(url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static void setConnectionTimeout(int connectionTimeout) {
        HttpClient.connectionTimeout = connectionTimeout;
    }

    public void setConnectionTimeoutOnce(int connectionTimeoutOnce) {
        this.connectionTimeoutOnce = connectionTimeoutOnce;
    }

    public static void setConnectionTimeoutMessage(String connectionTimeoutMessage) {
        HttpClient.connectionTimeoutMessage = connectionTimeoutMessage;
    }

    public static void setSoTimeoutMessage(String soTimeoutMessage) {
        HttpClient.soTimeoutMessage = soTimeoutMessage;
    }

    public static void setSoTimeout(int soTimeout) {
        HttpClient.soTimeout = soTimeout;
    }

    public void setSoTimeoutOnce(int soTimeoutOnce) {
        this.soTimeoutOnce = soTimeoutOnce;
    }

    public void setResponseEncoding(String responseEncoding) {
        httpResponse.setResponseEncoding(responseEncoding);
    }

    public void setRequestCoding(String requestCoding) {
        this.httpRequest.setRequestCoding(requestCoding);
    }

    public void setSuccessfulCode(int successfulCode) {
        this.successfulCode = successfulCode;
    }

    public HttpRequest getHttpRequest() {
        return httpRequest;
    }

    public HttpClient setRedirection(boolean redirection) {
        this.redirection = redirection;
        return this;
    }

    public HttpClient addHeader(String name, String... values) {
        this.httpRequest.addHeader(name, values);
        return this;
    }

    public HttpClient addHeader(Header header) {
        this.httpRequest.addHeader(header);
        return this;
    }

    public HttpClient addHeaders(List<Header> headers) {
        this.httpRequest.addHeaders(headers);
        return this;
    }

    public HttpClient addHeaders(Header... headers) {
        this.httpRequest.addHeaders(headers);
        return this;
    }

    public void postJson(String json) {
        post(json, Http.APPLICATION_JSON);
    }

    public HttpClient post(String value, String contentType) {
        setEntity(new StringEntity(value, contentType));
        requestMethod = Http.POST;
        return this;
    }

    public HttpClient post(NameValuePair... valuePairs) {
        post(Encoding.ISO_8859_1, valuePairs);
        return this;
    }

    public HttpClient post(String encoding, NameValuePair... valuePairs) {
        post(Arrays.asList(valuePairs), encoding);
        return this;
    }

    public HttpClient post(List<NameValuePair> pairList, String encoding) {
        setEntity(new FormEntity(pairList, encoding));
        return this;
    }

    public HttpClient setEntity(HttpEntity entity) {
        this.httpRequest.setEntity(entity);
        requestMethod = Http.POST;
        return this;
    }

    /**
     * 开始执行
     */
    public void execute() {
        shutdown = false;
        try {
            conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod(requestMethod);
            conn.setConnectTimeout(connectionTimeoutOnce);
            conn.setReadTimeout(soTimeoutOnce);
            // 设置重定向
            conn.setInstanceFollowRedirects(redirection);

            List<Header> headers = httpRequest.getHeaders();

            for (Header header : headers) {
                conn.addRequestProperty(header.getName(), header.getContent());
            }

            if (Http.POST.equals(requestMethod)) {
                conn.setDoOutput(true);
                conn.setUseCaches(false);
                HttpEntity entity = httpRequest.getEntity();
                if (entity != null) {
                    System.out.println(entity.getContentLength());
                    conn.addRequestProperty(Http.CONTENT_LEN, String.valueOf(entity.getContentLength()));
                    System.out.println(entity.getContentLength());
                    conn.addRequestProperty(entity.getContentType().getName(), entity.getContentType().getContent());
                    conn.addRequestProperty(entity.getContentEncoding().getName(), entity.getContentEncoding().getContent());
                    /// 写入数据
                    conn.connect();
                    entity.writeTo(conn.getOutputStream());
                }
            } else {
                // 连接
                conn.connect();
            }
            httpResponse.setResponseCode(-2);
            if (!shutdown) {
                // 获取请求结果
                httpResponse.setConnection(conn);
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            if (httpResponse.getResponseCode() == -1) {
                httpResponse.setMessage(connectionTimeoutMessage);
            } else {
                httpResponse.setMessage(soTimeoutMessage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

    }

    /**
     * 停止连接
     */
    public void shutdown() {
        if (conn != null) {
            disconnect();
        }
    }

    /**
     * 停止连接
     */
    protected void disconnect() {
        conn.disconnect();
        conn = null;
        shutdown = true;
    }
}
