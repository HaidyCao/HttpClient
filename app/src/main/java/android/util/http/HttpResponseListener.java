package android.util.http;

/**
 * http response listener
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public abstract class HttpResponseListener {

    /**
     * 请求成功
     *
     * @param response 响应信息
     */
    public abstract void onSuccess(HttpResponse response);

    /**
     * 请求失败
     *
     * @param response 响应信息
     */
    public abstract void onFailure(HttpResponse response);

    /**
     * 取消请求
     */
    public void onCancel() {
    }

    /**
     * 请求结束
     */
    public void onFinish() {
    }
}
