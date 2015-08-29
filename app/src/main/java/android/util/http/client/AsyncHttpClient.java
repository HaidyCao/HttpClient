package android.util.http.client;

import android.os.Handler;
import android.os.Looper;

/**
 * 异步调用http client
 * <p/>
 * Created by Haidy on 15/8/26.
 */
public class AsyncHttpClient extends HttpClient {

    private HttpResponseListener httpResponseListener;

    public AsyncHttpClient() {
        this(null);
    }

    public AsyncHttpClient(String url) {
        super(url);
    }


    /**
     * 下载，使用下载时候需要用这个接口
     *
     * @param httpResponseListener listener
     */
    public void download(HttpResponseListener httpResponseListener) {
        download = true;
        request(httpResponseListener);
    }

    /**
     * 异步请求，不区分post和get
     *
     * @param httpResponseListener listener
     */
    public void request(HttpResponseListener httpResponseListener) {
        this.httpResponseListener = httpResponseListener;
        thread.start();
    }

    private Thread thread = new Thread() {
        @Override
        public void run() {
            super.run();
            // 执行
            execute();
            // 结束
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    responseOver();
                }
            });
        }
    };

    protected void responseOver() {
        if (httpResponseListener != null && !shutdown) {
            if (httpResponse.getResponseCode() == successfulCode) {
                httpResponseListener.onSuccess(httpResponse);
            } else {
                httpResponseListener.onFailure(httpResponse);
            }
            httpResponseListener.onFinish();
        }
    }

    @Override
    protected void disconnect() {
        super.disconnect();
        if (httpResponseListener != null) {
            httpResponseListener.onCancel();
            httpResponseListener.onFinish();
        }
    }
}
