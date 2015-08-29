package android.util.http.cookie;

/**
 * cookie
 * <p/>
 * Created by Haidy on 15/8/27.
 */
public class Cookie {

    public String name;
    public String value;
    /**
     * 有效期
     */
    public String expires;

    public String domain;

    public String path;

    /**
     * 只能内发送到http服务器
     */
    public boolean secure;

    /**
     * cookie不能被客户端脚本获取到
     */
    public boolean httpOnly;

    public Cookie(String cookie) {
        // 分解cookie
        String[] cookieInfo = cookie.split(";");
        for (String info : cookieInfo) {
            if (info.startsWith("Expires")) {
                expires = info.substring(info.indexOf("=") + 1);
            } else if (info.startsWith("Domain")) {
                domain = info.substring(info.indexOf("=") + 1);
            } else if (info.startsWith("Path")) {
                path = info.substring(info.indexOf("=") + 1);
            } else if (info.startsWith("Secure")) {
                secure = true;
            } else if (info.startsWith("HttpOnly")) {
                httpOnly = true;
            } else {
                int index = info.indexOf("=");
                if (index != -1) {
                    name = info.substring(0, index - 1);
                    value = info.substring(index + 1);
                }
            }
        }
    }

    public String getCookie() {
        return name + "=" + value;
    }

    @Override
    public String toString() {
        return "Cookie{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", expires='" + expires + '\'' +
                ", domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                ", secure=" + secure +
                ", httpOnly=" + httpOnly +
                '}';
    }
}
