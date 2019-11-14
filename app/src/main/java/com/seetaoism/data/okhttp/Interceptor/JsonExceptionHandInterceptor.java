package com.seetaoism.data.okhttp.Interceptor;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class JsonExceptionHandInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Response response = chain.proceed(request);

        ResponseBody body = response.body();

        if (body != null) {
            MediaType mediaType = body.contentType();
            if (mediaType != null) {
                Charset charset = mediaType.charset();
                if (charset == null) {
                    charset = StandardCharsets.UTF_8;
                }
                if (mediaType.toString().toLowerCase().contains("json")) {

                    String contentStr = new String(body.bytes(), charset);
                    try {
                        JSONObject jsonObject = new JSONObject(contentStr);

                        if (!jsonObject.isNull("data")) {
                            String dataJsonStr = jsonObject.getString("data");
                            if (TextUtils.isEmpty(dataJsonStr)) {
                                jsonObject.remove("data");
                            }
                        }
                        return response.newBuilder().body(ResponseBody.create(mediaType,jsonObject.toString())).build();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
