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

    private InputStream inputStream;

    /**
     * 请求返回code
     */
    private int responseCode = -1;

    /**
     * 设置content
     *
     * @param urlConnection url content
     * @param download      download
     * @throws IOException io exception
     */
    protected void setConnection(HttpURLConnection urlConnection, boolean download) throws IOException {
        responseCode = urlConnection.getResponseCode();

        if (!download) {
            if (responseCode <= HttpURLConnection.HTTP_BAD_REQUEST) {
                InputStream is = urlConnection.getInputStream();
                body = HttpUtils.inputStreamToByte(is);
                is.close();
            } else {
                InputStream is = urlConnection.getErrorStream();
                body = HttpUtils.inputStreamToByte(is);
                is.close();
            }
        } else {
            if (responseCode <= HttpURLConnection.HTTP_BAD_REQUEST) {
                inputStream = urlConnection.getInputStream();
            } else {
                inputStream = urlConnection.getErrorStream();
            }
        }

        setHeaders(urlConnection.getHeaderFields());
        cookiesStore = new CookieStore(headers);
    }

    /**
     * 获取文件名字
     *
     * @return file name
     */
    public String getFileName() {
        for (Header header : headers) {
            if ("Content-disposition".equalsIgnoreCase(header.getName())) {
                String content = header.getContent();
                int index = content.indexOf("filename=");
                int end;
                if (index != -1) {
                    String fileName = content.substring(index + 9,
                            (end = content.indexOf(";", index)) == -1 ? content.length() : end - 1);
                    if (fileName.startsWith("\"") && fileName.endsWith("\"")) {
                        return fileName.substring(1, fileName.length() - 1);
                    } else {
                        return fileName;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 设置header
     *
     * @param headers header map
     */
    private void setHeaders(Map<String, List<String>> headers) {
        Set<String> stringSet = headers.keySet();
        for (String key : stringSet) {
            this.headers.add(new Header(key, headers.get(key)));
        }
    }

    /**
     * 获取data
     *
     * @return body data
     */
    public byte[] getBody() {
        return body;
    }

    public void setResponseEncoding(String responseEncoding) {
        this.responseEncoding = responseEncoding;
    }

    /**
     * 获取response encoding
     *
     * @return encoding
     */
    public String getResponseEncoding() {
        return responseEncoding;
    }

    /**
     * 获取header
     *
     * @return header
     */
    public List<Header> getHeaders() {
        return headers;
    }

    /**
     * 获取响应code
     *
     * @return code
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * 获取输入流，在设置为download的时候有效
     *
     * @return input stream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * 设置成功响应的code
     *
     * @param responseCode code
     */
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
            throw new RuntimeException(e.getMessage());
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
