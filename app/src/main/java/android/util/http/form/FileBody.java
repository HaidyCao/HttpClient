package android.util.http.form;

import android.util.http.Http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * file body
 * <p/>
 * Created by Haidy on 15/8/28.
 */
public class FileBody extends ContentBody {

    private File file;

    public FileBody(File file) {
        if (file.exists()) {
            this.file = file;
            setContentType(Http.APPLICATION_OCTET_STREAM);
            getDisp().addValues("filename=\"" + file.getName() + "\"");
        } else {
            throw new RuntimeException("file not found");
        }
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        if (file.exists()) {
            // 先写入头

            final InputStream is = new FileInputStream(this.file);
            final byte[] tmp = new byte[4096];
            int l;
            while ((l = is.read(tmp)) != -1) {
                outputStream.write(tmp, 0, l);
            }
            is.close();
        }
    }

    @Override
    public String getTransferEncoding() {
        return "binary";
    }

    @Override
    public long getContentLength() {
        if (file != null && file.exists()) {
            return file.length();
        }
        return 0;
    }
}
