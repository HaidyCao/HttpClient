package android.util.http.form;

import android.util.http.Header;
import android.util.http.Http;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 表单
 * <p/>
 * Created by Haidy on 15/8/28.
 */
public abstract class ContentBody {

    private Header disp = new Header("Content-Disposition", Http.FORM_DATA);

    private Header contentType = new Header("Content-Type", Http.PLAIN_TEXT_TYPE);

    public Header getDisp() {
        return disp;
    }

    public void setValue(String value) {
        addDisp("name=\"" + value + "\n");
    }

    public void addDisp(String nameValue) {
        disp.addValues(nameValue);
    }

    public void setContentType(String... value) {
        contentType.setValues(value);
    }

    public Header getContentType() {
        return contentType;
    }

    public String getHeaderContent() {
        StringBuilder builder = new StringBuilder();
        builder.append(disp.getName())
                .append(": ")
                .append(disp.getContent());

        builder.append("\r\n");
        builder.append(contentType.getName())
                .append(": ")
                .append(contentType.getContent());
        builder.append("\r\n");

        if (getTransferEncoding() != null) {
            builder.append("Content-Transfer-Encoding")
                    .append(": ")
                    .append(getTransferEncoding())
                    .append("\r\n");
        }

        return builder.toString();
    }

    public abstract void writeTo(OutputStream outputStream) throws IOException;

    public abstract String getTransferEncoding();

    public abstract long getContentLength();

}
