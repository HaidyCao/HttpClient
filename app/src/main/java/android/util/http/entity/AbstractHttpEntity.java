package android.util.http.entity;

import android.util.http.Encoding;
import android.util.http.Header;
import android.util.http.Http;

/**
 * http 实体的抽象类
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public abstract class AbstractHttpEntity implements HttpEntity {

    protected Header contentType = new Header(Http.CONTENT_TYPE, Http.APPLICATION_X_WWW_FORM_URLENCODE);

    protected Header contentEncoding = new Header(Http.CONTENT_ENCODING, Encoding.ISO_8859_1);

    @Override
    public Header getContentType() {
        return contentType;
    }

    @Override
    public Header getContentEncoding() {
        return contentEncoding;
    }

    public void setContentType(Header contentType) {
        this.contentType = contentType;
    }

    public void setContentType(String contentType) {
        Header header = null;
        if (contentType != null) {
            header = new Header(Http.CONTENT_TYPE, contentType);
        }
        setContentType(header);
    }

    public void setContentEncoding(Header contentEncoding) {
        this.contentEncoding = contentEncoding;
    }

    public void setContentEncoding(String contentEncoding) {
        Header header = null;
        if (contentEncoding != null) {
            header = new Header(Http.CONTENT_ENCODING, contentEncoding);
        }
        setContentEncoding(header);
    }
}
