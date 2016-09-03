package me.hncn.util.http;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * HttpClient
 * Created by XMH on 2016/5/21.
 */
public class HttpClient {
    private static OkHttpClient client = new OkHttpClient();

    public static String sendGet(String url) throws IOException {

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String sendGet(String url, Map<String,Object> param) throws IOException {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append(url).append("?");
        for(String key:param.keySet()){
            Object value = param.get(key);
            stringBuffer.append(URLEncoder.encode(key,"utf-8")).append("=").append(URLEncoder.encode(String.valueOf(value),"utf-8")).append("&");
        }
        url =stringBuffer.toString();
        url=url.substring(0,url.length()-1);
        return sendGet(url);

    }
}
