package android.util.http;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.http.client.AsyncHttpClient;
import android.util.http.entity.ContentEntity;
import android.util.http.entity.FileEntity;
import android.util.http.entity.FormEntity;
import android.util.http.form.ContentBody;
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

//        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient("http://ids1.hfut.edu.cn:81/amserver/UI/Login");
//        asyncHttpClient.setEntity(new FormEntity(
//                Encoding.ISO_8859_1,
//                new NameValuePair("IDToken0", ""),
//                new NameValuePair("IDToken1", "2012211706"),
//                new NameValuePair("IDToken2", "mn931010"),
//                new NameValuePair("IDButton", "Submit"),
//                new NameValuePair("goto", "aHR0cDovL215LmhmdXQuZWR1LmNuL1N0dUluZGV4LmFzcA=="),
//                new NameValuePair("encoded", String.valueOf(true)),
//                new NameValuePair("inputCode", ""),
//                new NameValuePair("gx_charset", "UTF-8")
//        ));
//        asyncHttpClient.setSuccessfulCode(HttpURLConnection.HTTP_MOVED_TEMP);
        final AsyncHttpClient asyncHttpClient = new AsyncHttpClient("http://10.66.209.64:8765/api/upload");
        FileBody fileBody = new FileBody(new File(Environment.getExternalStorageDirectory(), "360sicheck.txt"));
        ContentEntity contentEntity = new ContentEntity();
        contentEntity.addContent("file", fileBody);
        contentEntity.addContent("string", new StringBody("Hello", Encoding.UTF_8));
        asyncHttpClient.setEntity(contentEntity);//storage/emulated/360sicheck.txt
        asyncHttpClient.post(new HttpResponseListener() {

            @Override
            public void onSuccess(HttpResponse response) {
                System.out.println("Success");
                System.out.println(response.toString());
                System.out.println(response.getCookies());
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
