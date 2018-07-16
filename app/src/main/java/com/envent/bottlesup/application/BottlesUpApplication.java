package com.envent.bottlesup.application;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.envent.bottlesup.BuildConfig;
import com.envent.bottlesup.network.ApiInterceptor;
import com.envent.bottlesup.network.BottlesUpAPI;
import com.envent.bottlesup.services.MyNotificationOpenHandler;
import com.envent.bottlesup.services.MyNotificationReceivedHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.onesignal.OneSignal;

import java.lang.reflect.Modifier;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ronem on 3/15/18.
 */

public class BottlesUpApplication extends Application {

    private static BottlesUpAPI api;
    private static Retrofit retrofit;
    private static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(getApplicationContext());


        OneSignal.startInit(this)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .setNotificationReceivedHandler(new MyNotificationReceivedHandler(getApplicationContext()))
                .setNotificationOpenedHandler(new MyNotificationOpenHandler(getApplicationContext()))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
                .init();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ApiInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        GsonBuilder builder = new GsonBuilder()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC);
        gson = builder.create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

    }


    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    public static BottlesUpAPI getApi() {
        if (api == null) {
            api = retrofit.create(BottlesUpAPI.class);
        }
        return api;
    }

    public static Gson getGson() {
        return gson;
    }
}
