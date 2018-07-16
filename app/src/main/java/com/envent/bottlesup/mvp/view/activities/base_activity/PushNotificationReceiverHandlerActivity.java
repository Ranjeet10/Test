package com.envent.bottlesup.mvp.view.activities.base_activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.TableBar;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.view.activities.BottlesUpSplash;
import com.envent.bottlesup.mvp.view.activities.Dashboard;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;
import com.onesignal.OneSignal;

/**
 * Created by ronem on 4/30/18.
 */

public abstract class PushNotificationReceiverHandlerActivity extends AppCompatActivity {

    protected BroadcastReceiver updateBalanceReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateBalance();
        }
    };

    protected abstract void updateBalance();

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateBalanceReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter updateBalanceFilter = new IntentFilter(MetaData.INTENT_ACTIONS.UPDATE_BALANCE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(updateBalanceReceiver, updateBalanceFilter);


        OneSignal.clearOneSignalNotifications();
        Log.i("Status", "OnResume called");
    }

    public void logoutNow() {
        User.logOut();
        OneSignal.setSubscription(false);
        new SessionManager(this).setDeviceIdSavedWithUser(false);
        UtilityMethods.checkoutNow();
        Intent intent = new Intent(this, BottlesUpSplash.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
