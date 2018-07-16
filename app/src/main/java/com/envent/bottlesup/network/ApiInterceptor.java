package com.envent.bottlesup.network;

import com.envent.bottlesup.utils.MyLog;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by ronem on 4/11/17.
 */

public class ApiInterceptor implements Interceptor {
    private String TAG = getClass().getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        long t1 = System.nanoTime();
        MyLog.i(TAG, String.format("Sending request %s on %s%n%s",
                original.url(), chain.connection(), original.headers()));

        // Request customization: add request headers
        Request.Builder requestBuilder = original.newBuilder()
                .header("Content-Type", "application/json")
                .method(original.method(), original.body());

        Request request = requestBuilder.build();

        Response response = chain.proceed(request);
        long t2 = System.nanoTime();

        MyLog.i(TAG, String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        MediaType contentType = response.body().contentType();
        String content = response.body().string();
        MyLog.i(TAG, "OkHttp ResponseBody: " + content);

        return response.newBuilder().body(ResponseBody.create(contentType, content)).build();
    }
}
