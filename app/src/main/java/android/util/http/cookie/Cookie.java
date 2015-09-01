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
        String[] cookieInfo = cookie.replace(" ", "").split(";");
        for (String info : cookieInfo) {
            if (info.toLowerCase().startsWith("expires")) {
                expires = info.substring(info.indexOf("=") + 1);
            } else if (info.toLowerCase().startsWith("domain")) {
                domain = info.substring(info.indexOf("=") + 1);
            } else if (info.toLowerCase().startsWith("path")) {
                path = info.substring(info.indexOf("=") + 1);
            } else if (info.toLowerCase().startsWith("secure")) {
                secure = true;
            } else if (info.toLowerCase().startsWith("httpOnly")) {
                httpOnly = true;
            } else {
                int index = info.indexOf("=");
                if (index != -1) {
                    name = info.substring(0, index);
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
