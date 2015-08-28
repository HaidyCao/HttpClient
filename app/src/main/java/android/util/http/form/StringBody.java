package android.util.http.form;

import java.io.IOException;
import java.io.OutputStream;

/**
 * string body
 * <p/>
 * Created by Haidy on 15/8/28.
 */
public class StringBody extends ContentBody {

    private byte[] data;

    public StringBody(String str, String encoding) {
        try {
            data = str.getBytes(encoding);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(data);
    }

    @Override
    public String getTransferEncoding() {
        return null;
    }

    @Override
    public long getContentLength() {
        return data.length;
    }
}
