package android.util.http.client;

import android.util.http.Encoding;
import android.util.http.Header;
import android.util.http.utils.HttpUtils;
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

    private String message;

    /**
     * 请求返回code
     */
    private int responseCode = -1;

    public void setConnection(HttpURLConnection urlConnection) throws IOException {
        responseCode = urlConnection.getResponseCode();

        if (responseCode <= HttpURLConnection.HTTP_BAD_REQUEST) {
            InputStream is = urlConnection.getInputStream();
            body = HttpUtils.inputStreamToByte(is);
        } else {
            InputStream is = urlConnection.getErrorStream();
            body = HttpUtils.inputStreamToByte(is);
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

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
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

    /**
     * 设置message
     *
     * @param message message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取message
     *
     * @return message
     */
    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "responseEncoding='" + responseEncoding + '\'' +
                ", body=" + getBodyAsString() +
                ", headers=" + headers +
                ", cookies=" + getCookies() +
                ", responseCode=" + responseCode +
                ", message=" + message +
                '}';
    }
}
