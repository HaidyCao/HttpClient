package android.util.http.form;

/**
 * name content
 * <p/>
 * Created by Haidy on 15/8/29.
 */
public class NameContent {

    private String name;

    private ContentBody content;

    public NameContent(String name, ContentBody content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public ContentBody getContent() {
        return content;
    }
}
