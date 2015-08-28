package android.util.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * http utils
 * Created by Haidy on 15/8/26.
 */
public class HttpUtils {

    public static byte[] inputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) != -1) {
            outputStream.write(buffer, 0, len);
        }
        byte[] data = outputStream.toByteArray();
        outputStream.close();
        return data;
    }
}
