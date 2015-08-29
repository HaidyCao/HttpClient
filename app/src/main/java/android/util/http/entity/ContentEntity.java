package android.util.http.entity;

import android.util.http.form.ContentBody;
import android.util.http.form.NameContent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * content 实体
 * <p/>
 * Created by Haidy on 15/8/28.
 */
public class ContentEntity extends AbstractHttpEntity {

    private Map<String, ContentBody> contentBodyMap = new HashMap<>();

    private String boundary;

    /**
     * The pool of ASCII chars to be used for generating a multipart boundary.
     */
    private final static char[] MULTIPART_CHARS =
            "-_1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    .toCharArray();

    public ContentEntity() {
        this(null);
    }

    public ContentEntity(final String boundary) {
        this.boundary = boundary == null ? generateBoundary() : boundary;
        getContentType().setValues("multipart/form-data", "boundary=" + this.boundary);
    }

    /**
     * 产生随机boundary
     *
     * @return boundary
     */
    private String generateBoundary() {
        final StringBuilder buffer = new StringBuilder();
        final Random rand = new Random();
        final int count = rand.nextInt(11) + 30; // a random size from 30 to 40
        for (int i = 0; i < count; i++) {
            buffer.append(MULTIPART_CHARS[rand.nextInt(MULTIPART_CHARS.length)]);
        }
        return buffer.toString();
    }

    /**
     * 添加name content
     *
     * @param name        name
     * @param contentBody contentBody
     */
    public void addContent(String name, ContentBody contentBody) {
        if (name != null && contentBody != null) {
            contentBodyMap.put(name, contentBody);
            contentBody.getDisp().getValue().add(1, "name=\"" + name + "\"");
        }
    }

    /**
     * add content
     *
     * @param nameContent name content
     */
    public void addContent(NameContent nameContent) {
        addContent(nameContent.getName(), nameContent.getContent());
    }

    /**
     * 添加name content
     *
     * @param nameContents s
     */
    public void addContents(NameContent... nameContents) {
        for (NameContent nameContent : nameContents) {
            addContent(nameContent);
        }
    }

    @Override
    public long getContentLength() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            long size;
            write(baos, false);
            size = baos.toByteArray().length;
            Set<String> set = contentBodyMap.keySet();

            for (String name : set) {
                size += contentBodyMap.get(name).getContentLength();
            }
            return size;
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 写入
     *
     * @param outputStream output stream
     * @param writeContent 是否写入content
     * @throws IOException io exception
     */
    private void write(OutputStream outputStream, boolean writeContent) throws IOException {
        Set<String> set = contentBodyMap.keySet();

        for (String name : set) {
            outputStream.write("--".getBytes());
            outputStream.write(boundary.getBytes());
            outputStream.write("\r\n".getBytes());

            ContentBody contentBody = contentBodyMap.get(name);

            outputStream.write(contentBody.getHeaderContent().getBytes());
            outputStream.write("\r\n".getBytes());

            if (writeContent) {
                contentBody.writeTo(outputStream);
            }

            outputStream.write("\r\n".getBytes());
        }

        outputStream.write(("--" + boundary + "--").getBytes());
        outputStream.write("\r\n".getBytes());
    }

    @Override
    public InputStream getContent() throws IOException {
        return null;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        write(outputStream, true);
    }
}
