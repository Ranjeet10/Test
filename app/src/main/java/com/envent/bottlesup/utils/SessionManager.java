package com.envent.bottlesup.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;

import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.services.DeviceRegistrationService;
import com.google.gson.Gson;

/**
 * Created by ronem on 3/15/18.
 */

public class SessionManager {
    private String TAG = getClass().getSimpleName();
    private SharedPreferences spf;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        spf = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setFirstRun() {
        spf.edit().putBoolean(MetaData.KEY.IS_FIRST_RUN, false).apply();
        setNotificationEnabled(true);
    }

    public boolean isFirstRun() {
        return spf.getBoolean(MetaData.KEY.IS_FIRST_RUN, true);
    }

    public void setDeviceIdSaved() {
        spf.edit().putBoolean(MetaData.KEY.DEVICE_ID_SAVED_STATUS, true).apply();
    }

    private boolean isDeviceIdSaved() {
        return spf.getBoolean(MetaData.KEY.DEVICE_ID_SAVED_STATUS, false);
    }

    public void setDeviceIdSavedWithUser(boolean isSaved) {
        spf.edit().putBoolean(MetaData.KEY.DEVICE_ID_SAVED_STATUS_WITH_USER, isSaved).apply();
    }

    private boolean isDeviceIdSavedWithUser() {
        return spf.getBoolean(MetaData.KEY.DEVICE_ID_SAVED_STATUS_WITH_USER, false);
    }


    public void checkAndSavetheDeviceId(User user) {
        if (isDeviceIdSaved()) {
            Log.i(TAG, "Device saved");
            if (!isDeviceIdSavedWithUser()) {
                Log.i(TAG, "Device saved without user");
                if (user != null) {
                    context.startService(new Intent(context, DeviceRegistrationService.class));
                } else {
                    Log.i(TAG, "Device will not register with out user");
                }
            }
        } else {
            Log.i(TAG, "Device not saved");
            context.startService(new Intent(context, DeviceRegistrationService.class));
        }
    }

    public void setNotificationEnabled(boolean isChecked) {
        spf.edit().putBoolean(MetaData.KEY.NOTIFICATION_STATUS, isChecked).apply();
    }

    public boolean isNotificationEnabled() {
        return spf.getBoolean(MetaData.KEY.NOTIFICATION_STATUS, false);
    }

    public void saveVenueEvent(EventResponse response) {
        Log.i(TAG, "response " + response);
        String er;
        if (response == null) {
            er = "";
        } else if (response.getData() != null && response.getData().size() > 0) {
            Gson g = new Gson();
            er = g.toJson(response);
        } else {
            er = "";
        }
        spf.edit().putString(MetaData.KEY.EVENT, er).apply();
    }

    public EventResponse getVenueEvent() {
        String es = spf.getString(MetaData.KEY.EVENT, "");
        Log.i(TAG, "Event was " + es);
        if (!TextUtils.isEmpty(es)) {
            Gson gson = new Gson();
            return gson.fromJson(es, EventResponse.class);
        }
        return null;
    }

    public int getSelectedtableBarId() {
        return spf.getInt(MetaData.KEY.TABLE_BAR_ID, 0);
    }

    public void setSelectedTableBarId(int id) {
        spf.edit().putInt(MetaData.KEY.TABLE_BAR_ID, id).apply();
    }
}
