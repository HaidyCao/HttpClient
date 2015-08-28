package android.util.http;

import android.util.http.cookie.CookieStore;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 请求结果
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class HttpResponse {

    private String responseEncoding = Encoding.ISO_8859_1;

    private byte[] body;

    /**
     * 响应头
     */
    private List<Header> headers = new ArrayList<>();

    private CookieStore cookiesStore;

    /**
     * 请求返回code
     */
    private int responseCode;

    public void setConnection(HttpURLConnection urlConnection) throws IOException {
        responseCode = urlConnection.getResponseCode();

        System.out.println(responseCode);

        if (responseCode <= HttpURLConnection.HTTP_BAD_REQUEST) {
            InputStream is = urlConnection.getInputStream();
            body = HttpUtils.inputStreamToByte(is);
            System.out.println(new String(body));
        } else {
            InputStream is = urlConnection.getErrorStream();
            body = HttpUtils.inputStreamToByte(is);
            System.out.println(new String(body));
        }

        setHeaders(urlConnection.getHeaderFields());
        cookiesStore = new CookieStore(headers);
    }

    private void setHeaders(Map<String, List<String>> headers) {
        Set<String> stringSet = headers.keySet();
        for (String key : stringSet) {
            this.headers.add(new Header(key, headers.get(key)));
        }
    }

    public byte[] getBody() {
        return body;
    }

    public void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }

    public String getResponseEncoding() {
        return responseEncoding;
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public int getResponseCode() {
        return responseCode;
    }

    /**
     * 获取请求结果
     *
     * @return as string
     */
    public String getBodyAsString() {
        try {
            if (body != null) {
                return new String(body, responseEncoding);
            } else {
                return null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("response encoding is not enable");
        }
    }

    /**
     * 获取cookies
     *
     * @return cookies
     */
    public String getCookies() {
        if (cookiesStore != null) {
            return cookiesStore.getCookies();
        }
        return null;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "responseEncoding='" + responseEncoding + '\'' +
                ", body=" + getBodyAsString() +
                ", headers=" + headers +
                ", cookies=" + getCookies() +
                ", responseCode=" + responseCode +
                '}';
    }
}
