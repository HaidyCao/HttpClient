package android.util.http;

/**
 * 请求表单
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class NameValuePair {

    public String name;

    public String value;

    public NameValuePair() {
    }

    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }
}
