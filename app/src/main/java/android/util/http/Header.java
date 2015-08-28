package android.util.http;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 头
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class Header {

    private String name;
    private List<String> value = new ArrayList<>();

    public Header(String name, String value) {
        this.name = name;
        this.value.add(value);
    }

    public Header(String name, String... values) {
        setName(name);
        setValues(values);
    }

    public Header(String name, List<String> value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValue() {
        return value;
    }

    public String getContent() {
        StringBuilder sb = new StringBuilder();
        for (String item : value) {
            sb.append(item).append("; ");
        }
        return sb.substring(0, sb.length() - 2);
    }

    public void setValue(String value) {
        this.value.clear();
        this.value.add(value);
    }

    public void setValues(String... values) {
        this.value.clear();
        this.value.addAll(Arrays.asList(values));
    }

    public void addValues(String... values) {
        this.value.addAll(Arrays.asList(values));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Header{");
        sb.append(name).append(" = ");
        for (String value : this.value) {
            sb.append(value).append(",");
        }
        if (sb.lastIndexOf(",") != -1) {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("}");
        return sb.toString();
    }
}
