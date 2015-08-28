package android.util.http.entity;

import android.util.http.Encoding;
import android.util.http.Header;
import android.util.http.Http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * string 实体
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class StringEntity extends AbstractHttpEntity {

    protected byte[] content;

    public StringEntity(String contentStr) {
        this(contentStr, null, null);
    }

    public StringEntity(String contentStr, String contentType) {
        this(contentStr, contentType, null);
    }

    public StringEntity(String contentStr, String contentType, String encoding) {
        // check content string
        if (contentStr == null) {
            throw new RuntimeException("content can not is null");
        }

        setContentType(contentType == null ? Http.CONTENT_ENCODING : contentType);

        setContentEncoding(Encoding.checkEncoding(encoding) ? encoding : Encoding.ISO_8859_1);

        // set content
        Header header = getContentEncoding();
        try {
            content = contentStr.getBytes(header != null ? header.getContent() : Encoding.ISO_8859_1);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public long getContentLength() {
        return content.length;
    }

    @Override
    public InputStream getContent() throws IOException {
        return new ByteArrayInputStream(this.content);
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(content);
    }
}
