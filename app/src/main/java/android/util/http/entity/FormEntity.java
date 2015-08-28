package android.util.http.entity;

import android.util.http.Encoding;
import android.util.http.Http;
import android.util.http.NameValuePair;
import android.util.http.utils.EncodedUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 表单
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class FormEntity extends StringEntity {

    public FormEntity(NameValuePair... pairs) {
        this(Encoding.ISO_8859_1, pairs);
    }

    public FormEntity(String encoding, NameValuePair... pairs) {
        this(Arrays.asList(pairs), encoding);
    }

    public FormEntity(List<NameValuePair> pairList, String encoding) {
        super(EncodedUtils.format(pairList, encoding));
        setContentType(Http.APPLICATION_X_WWW_FORM_URLENCODE);
    }

}
