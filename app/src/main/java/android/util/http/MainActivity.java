package android.util.http;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.http.client.AsyncHttpClient;
import android.util.http.client.HttpResponse;
import android.util.http.client.HttpResponseListener;
import android.util.http.entity.ContentEntity;
import android.util.http.entity.FormEntity;
import android.util.http.form.FileBody;
import android.util.http.form.StringBody;

import java.io.File;
import java.net.HttpURLConnection;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                HttpClient client = new HttpClient("http://www.baidu.com");// http://wthrcdn.etouch.cn/WeatherApi
////                client.post(new NameValuePair("citykey", "101010100"));
//                client.execute();
//            }
//        }).start();

        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient("http://ids1.hfut.edu.cn:81/amserver/UI/Login");
        asyncHttpClient.setEntity(new FormEntity(
                Encoding.ISO_8859_1,
                new NameValuePair("IDToken0", ""),
                new NameValuePair("IDToken1", "2012211706"),
                new NameValuePair("IDToken2", "mn931010"),
                new NameValuePair("IDButton", "Submit"),
                new NameValuePair("goto", "aHR0cDovL215LmhmdXQuZWR1LmNuL1N0dUluZGV4LmFzcA=="),
                new NameValuePair("encoded", String.valueOf(true)),
                new NameValuePair("inputCode", ""),
                new NameValuePair("gx_charset", "UTF-8")
        ));
        asyncHttpClient.setSuccessfulCode(HttpURLConnection.HTTP_MOVED_TEMP);
//        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient("http://10.66.209.64:8765/api/upload");
//        FileBody fileBody = new FileBody(new File(Environment.getExternalStorageDirectory(), "360sicheck.txt"));
//        ContentEntity contentEntity = new ContentEntity();
//        contentEntity.addContent("file", fileBody);
//        contentEntity.addContent("string", new StringBody("Hello", Encoding.UTF_8));
//        asyncHttpClient.setEntity(contentEntity);//storage/emulated/360sicheck.txt

//        AsyncHttpClient asyncHttpClient = new AsyncHttpClient("http://210.45.241.153/api.php?op=download&name=%E9%99%84%E4%BB%B6%EF%BC%9A%E5%BA%90%E6%94%BF%E3%80%942015%E3%80%9527%E5%8F%B7%E5%BA%90%E9%98%B3%E5%8C%BA%E7%A7%91%E6%8A%80%E5%88%9B%E6%96%B0%E5%88%9B%E4%B8%9A%E5%A4%A7%E8%B5%9B%E9%80%9A%E7%9F%A5&s=%2Fuploadfile%2F2015%2F0821%2F20150821044031909.doc");
//        asyncHttpClient.setDownload(true);
//        asyncHttpClient.setCookies("jfinalId=bcbea13b72e7490583d6ac4fe39f18f4");
        asyncHttpClient.request(new HttpResponseListener() {

            @Override
            public void onSuccess(HttpResponse response) {
                System.out.println("Success");
                System.out.println(response.toString());
                System.out.println(response.getCookies());
                System.out.println("file name = " + response.getFileName());
            }

            @Override
            public void onFailure(HttpResponse response) {
                System.out.println("Failure");
                System.out.println(response.toString());
            }

            @Override
            public void onFinish() {
                super.onFinish();
                System.out.println("Finish");
            }
        });
    }

}
