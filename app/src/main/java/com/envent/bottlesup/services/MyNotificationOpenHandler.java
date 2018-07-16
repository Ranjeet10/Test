package com.envent.bottlesup.services;

import android.content.Context;
import android.content.Intent;

import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

/**
 * Created by ronem on 4/13/18.
 */

public class MyNotificationOpenHandler implements OneSignal.NotificationOpenedHandler {
    private Context context;
    private String TAG = getClass().getSimpleName();

    public MyNotificationOpenHandler(Context applicationContext) {
        this.context = applicationContext;
    }
    // This fires when a notification is opened by tapping on it.


    @Override
    public void notificationOpened(OSNotificationOpenResult result) {
//        JSONObject data = result.notification.payload.additionalData;
//
//        Gson g = new Gson();
//        PushNotification p = g.fromJson(data.toString(), PushNotification.class);
//        Log.i("Title", p.getTitle());
//        Log.i("Message", p.getMessage());

//        if (p.getTitle().contains("Your topup request")) {

//            context.startService(new Intent(context, ServiceBalanceUpdate.class));
//
//            Intent i1 = new Intent(context, TopupHistory.class);
//            i1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(i1);

//        }
    }
}
