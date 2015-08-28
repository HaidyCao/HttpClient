package android.util.http;

import java.util.ArrayList;
import java.util.List;

/**
 * 编码
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class Encoding {

    public static final String UTF_8 = "utf-8";

    public static final String GB2312 = "gb2312";

    public static final String US_ASCII = "US-ASCII";

    public static final String UTF_16 = "UTF-16";

    public static final String ISO_8859_1 = "ISO-8859-1";

    private static List<String> encodingList = new ArrayList<>();

    static {
        encodingList.add(UTF_8);
        encodingList.add(GB2312);
        encodingList.add(US_ASCII);
        encodingList.add(UTF_16);
        encodingList.add(ISO_8859_1);
    }

    public static boolean checkEncoding(String encoding) {
        for (String item : encodingList) {
            if (!item.equals(encoding)) {
                return false;
            }
        }
        return true;
    }
}
