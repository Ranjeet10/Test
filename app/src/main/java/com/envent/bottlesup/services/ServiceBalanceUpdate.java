package com.envent.bottlesup.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.mybalance.MyBalanceResponse;
import com.envent.bottlesup.utils.MetaData;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 3/15/18.
 */

public class ServiceBalanceUpdate extends IntentService {
    public ServiceBalanceUpdate() {
        super("ServiceBalanceUpdate");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (User.getUser() == null)
            return;

        Call<ResponseBody> call = BottlesUpApplication.getApi().getBalanceResponse(User.getUser().getAccess_token());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String _res = response.body().string();
                        JSONObject o = new JSONObject(_res);
                        if (o.has(MetaData.MESSAGE.STATUS)) {
                            String status = o.getString(MetaData.MESSAGE.STATUS);
                            if (status.equals(MetaData.MESSAGE.SUCCESS)) {
                                Gson g = new Gson();
                                User u = User.getUser();
                                MyBalanceResponse myBalanceResponse = g.fromJson(_res, MyBalanceResponse.class);
                                u.setBalance(myBalanceResponse.getMyBalance().getBalance());
                                u.save();
                                Intent i = new Intent();
                                i.setAction(MetaData.INTENT_ACTIONS.UPDATE_BALANCE_ACTION);
                                LocalBroadcastManager.getInstance(ServiceBalanceUpdate.this).sendBroadcast(i);

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
