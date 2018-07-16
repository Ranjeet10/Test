package com.envent.bottlesup.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.MyLog;
import com.envent.bottlesup.utils.SessionManager;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 4/13/18.
 */

public class DeviceRegistrationService extends IntentService {
    private String TAG = getClass().getSimpleName();
    private String apiKey = null;
    private SessionManager sessionManager;

    public DeviceRegistrationService() {
        super("DeviceRegistrationService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        MyLog.i(TAG, "registering");
        sessionManager = new SessionManager(this);
        //player id as device reg id
//        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
//        String regId = status.getSubscriptionStatus().getUserId();

        String regId = FirebaseInstanceId.getInstance().getToken();

        if (TextUtils.isEmpty(regId))
            return;

        Log.i("RegistrationID1", regId);
        User user = User.getUser();
        if (user != null) {
            apiKey = user.getAccess_token();
            MyLog.i(TAG, "APi Key was found");
        }
        proceedRegistration(regId);

    }

    private void proceedRegistration(String adId) {

        Call<ResponseBody> call = BottlesUpApplication.getApi().getDeviceRegistrationResponse(adId, "ANDROID", apiKey);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String _res = response.body().string();
                        JSONObject node = new JSONObject(_res);
                        if (node.has(MetaData.MESSAGE.STATUS)) {
                            String status = node.getString(MetaData.MESSAGE.STATUS);
                            if (status.equals(MetaData.MESSAGE.SUCCESS)) {

                                /**
                                 * check if the response was for only device id registration
                                 * or for device id registration along with the user api Key
                                 */
                                if (apiKey == null) {
                                    MyLog.i(TAG, "API KEY was null");
                                    sessionManager.setDeviceIdSaved();
                                } else {
                                    MyLog.i(TAG, "API KEY was not null");
                                    sessionManager.setDeviceIdSavedWithUser(true);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
