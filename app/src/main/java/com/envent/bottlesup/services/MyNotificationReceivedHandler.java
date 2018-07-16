package com.envent.bottlesup.services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.envent.bottlesup.BuildConfig;
import com.envent.bottlesup.mvp.model.server_response.notification_response.ItemsItem;
import com.envent.bottlesup.mvp.view.activities.BottlesUpSplash;
import com.envent.bottlesup.mvp.view.activities.ConsumptionHistoryDetail;
import com.envent.bottlesup.mvp.view.activities.NotificationDetail;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.NotificationUtils;
import com.onesignal.OSNotification;
import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ronem on 4/13/18.
 */

public class MyNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
    private String TAG = getClass().getSimpleName();
    private Context context;

    public MyNotificationReceivedHandler(Context context) {
        this.context = context;
    }

    @Override
    public void notificationReceived(OSNotification notification) {

        Log.i(TAG, "Notification received Called");
        /*
        {
            "a":{
            "amount":23276.26,
                    "message":"Dear Navin Koirala, Your Top Up request for Rs. 500 on Jun 28th, 2018 - 01:45pm from Warehouse Cafe has been cancelled.",
                    "title":"Your topup request for Rs. 500 has been cancelled.",
                    "type":"topup",
                    "createdOn":"2018-06-28 13:45:25"
        },
            "i":"885bfbfc-c1f2-48d3-b0ec-46163cc2627c"
        }
        */

//        JSONObject data = notification.payload.additionalData;
//        String title = notification.payload.title;
//        String body = notification.payload.body;
//        String smallIcon = notification.payload.smallIcon;
//        String largeIcon = notification.payload.largeIcon;
//        String bigPicture = notification.payload.bigPicture;
//        String smallIconAccentColor = notification.payload.smallIconAccentColor;
//        String sound = notification.payload.sound;
//        String ledColor = notification.payload.ledColor;
//        int lockScreenVisibility = notification.payload.lockScreenVisibility;
//        String groupKey = notification.payload.groupKey;
//        String groupMessage = notification.payload.groupMessage;
//        String fromProjectNumber = notification.payload.fromProjectNumber;

//        String notificationID = notification.payload.notificationID;

        String formattedString = notification.payload.rawPayload;
        Log.v("OneSignal", "NotificationReceived : " + formattedString);
        try {
            JSONObject jsonObject = new JSONObject(formattedString);
            String customString = jsonObject.getString("custom");
            Log.i("CustomString::", customString);
            JSONObject customObject = new JSONObject(customString);
            JSONObject aObject = customObject.getJSONObject("a");
            String title = aObject.getString("title");
            String message = aObject.getString("message");
            String notificationType = aObject.getString("type");
            String createdOn = aObject.getString("createdOn");

            //Topup(approve/cancel) -> notification's->alert detail
            //Order(approve/rejected)-> order detail
            int notificationId;
            Intent tapIntent;

            switch (notificationType) {
                case MetaData.KEY.NOTIFICATION_TYPE_TOPUP:
                    notificationId = 0;
                    int topupId = aObject.getInt("id");
                    ItemsItem ni = new ItemsItem(topupId, "", title, notificationType, message, createdOn);
                    tapIntent = new Intent(context, NotificationDetail.class);
                    tapIntent.putExtra(MetaData.KEY.NOTIFICATION, ni);
                    tapIntent.putExtra(MetaData.KEY.NOTIFICATION_TYPE, MetaData.KEY.NOTIFICATION_TYPE_ALERT);
                    tapIntent.putExtra(MetaData.KEY.TITLE, "Alert Detail");

                    //we need to launch the ServiceBalanceUpdate if the the notification is for topup
                    //the code is written inside try/catch block because if the application is no longer
                    //visible and is destroyed completely then we are unable to launch the service from background
                    //as a result notification will not be fired. since the program stop at this point
                    // and will not move forward to show the notification
                    //error : java.lang.IllegalStateException: Not allowed to start service Intent
                    // { cmp=com.envent.bottlesup.test/com.envent.bottlesup.services.ServiceBalanceUpdate }:
                    // app is in background uid UidRecord{57d702b u0a81 RCVR idle procs:1 seq(0,0,0)}
                    try {
                        context.startService(new Intent(context, ServiceBalanceUpdate.class));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case MetaData.KEY.NOTIFICATION_TYPE_ORDER:
                    notificationId = 1;
                    tapIntent = new Intent(context, ConsumptionHistoryDetail.class);
                    int orderId = aObject.getInt("id");
                    tapIntent.putExtra(MetaData.KEY.ORDER_ID, orderId);
                    tapIntent.putExtra(MetaData.KEY.TITLE, "Order Detail");
                    Log.i("NotificationType", "Order");
                    break;

                default:
                    notificationId = 2;
                    tapIntent = new Intent(context, BottlesUpSplash.class);
                    Log.i("NotificationType", "None");

            }

            NotificationUtils notificationUtils = new NotificationUtils(context);

            notificationUtils.notify(notificationId, title, message, tapIntent);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        OneSignal.clearOneSignalNotifications();
    }

}
