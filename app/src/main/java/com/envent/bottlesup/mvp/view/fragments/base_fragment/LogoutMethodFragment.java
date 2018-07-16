package com.envent.bottlesup.mvp.view.fragments.base_fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.view.activities.BottlesUpSplash;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;
import com.onesignal.OneSignal;

/**
 * Created by ronem on 6/14/18.
 */

public class LogoutMethodFragment extends Fragment {

    public void logoutNow() {
        User.logOut();
        OneSignal.setSubscription(false);
        new SessionManager(getContext()).setDeviceIdSavedWithUser(false);
        UtilityMethods.checkoutNow();
        Intent intent = new Intent(getContext(), BottlesUpSplash.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
