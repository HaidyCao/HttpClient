package android.util.http.cookie;

import android.util.http.Header;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 保存cookie、获取cookie
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class CookieStore {

    /**
     * 保存cookie的列表
     */
    public List<Cookie> cookies = new ArrayList<>();

    public CookieStore(Map<String, List<String>> headers) {
        Set<String> stringSet = headers.keySet();
        for (String key : stringSet) {
            if ("Set-cookie".equalsIgnoreCase(key)) {
                for (String value : headers.get(key)) {
                    cookies.add(new Cookie(value));
                }
            }
        }
    }

    public CookieStore(List<Header> headers) {
        for (Header header : headers) {
            if ("Set-cookie".equalsIgnoreCase(header.getName())) {
                for (String value : header.getValue()) {
                    cookies.add(new Cookie(value));
                }
            }
        }
    }

    /**
     * 获取cookies
     *
     * @return cookies
     */
    public String getCookies() {
        StringBuilder sb = new StringBuilder();
        for (Cookie cookie : cookies) {
            sb.append(cookie.getCookie()).append(";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
