package android.util.http.entity;

import android.util.http.Header;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Http 请求实体
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public interface HttpEntity {

    Header getContentType();

    Header getContentEncoding();

    long getContentLength();

    InputStream getContent() throws IOException;

    void writeTo(OutputStream outputStream) throws IOException;
}
