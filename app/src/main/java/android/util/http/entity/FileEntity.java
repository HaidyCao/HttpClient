package android.util.http.entity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;

/**
 * file实体
 * <p/>
 * Created by Haidy on 15/8/27.
 */
public class FileEntity extends AbstractHttpEntity {

    private File file;

    public FileEntity(File file) {
        if (file.exists()) {
            this.file = file;
        } else {
            throw new RuntimeException("file not fond");
        }
    }

    @Override
    public long getContentLength() {
        return file.exists() ? file.length() : 0;
    }

    @Override
    public InputStream getContent() throws IOException {
        return new FileInputStream(file);
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        final InputStream is = new FileInputStream(this.file);
        final byte[] tmp = new byte[4096];
        int l;
        while ((l = is.read(tmp)) != -1) {
            outputStream.write(tmp, 0, l);
        }
        outputStream.flush();
        is.close();
    }
}
