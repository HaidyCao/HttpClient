package android.util.http.client;

import android.util.http.Encoding;
import android.util.http.Header;
import android.util.http.entity.HttpEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * http 请求
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class HttpRequest {

    private List<Header> headers = new ArrayList<>();

    private String requestCoding = Encoding.ISO_8859_1;

    private HttpEntity entity;

    public void setHeaders(List<Header> headers) {
        this.headers.clear();
        this.headers.addAll(headers);
    }

    public void setHeaders(Header... headers) {
        this.headers.clear();
        this.headers.addAll(Arrays.asList(headers));
    }

    public void addHeader(String name, String... values) {
        addHeader(new Header(name, values));
    }

    public void addHeader(Header header) {
        headers.add(header);
    }

    public void addHeaders(List<Header> headers) {
        this.headers.addAll(headers);
    }

    public void addHeaders(Header... headers) {
        this.headers.addAll(Arrays.asList(headers));
    }

    public List<Header> getHeaders() {
        return headers;
    }

    public HttpEntity getEntity() {
        return entity;
    }

    public void setEntity(HttpEntity entity) {
        this.entity = entity;
    }

    public void setRequestCoding(String requestCoding) {
        if (requestCoding == null) {
            this.requestCoding = Encoding.ISO_8859_1;
        } else {
            this.requestCoding = requestCoding;
        }
    }

    public String getRequestCoding() {
        return requestCoding;
    }


}
