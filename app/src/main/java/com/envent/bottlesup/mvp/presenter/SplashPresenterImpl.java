package com.envent.bottlesup.mvp.presenter;

import android.util.Log;
import android.widget.Toast;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.RegisterResponse;
import com.envent.bottlesup.network.MessageParser;
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
 * Created by ronem on 4/3/18.
 */

public class SplashPresenterImpl implements MyPresenter.SplashPresenter {
    private MVPView.SplashView view;
    private Call<ResponseBody> call;

    @Override
    public void addView(MVPView.SplashView view) {
        this.view = view;
    }

    @Override
    public void getUser(String token) {
        view.showProgressDialog();
        call = BottlesUpApplication.getApi().getUserResponse(token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.hideProgressDialog();
                try {
                    String _rs = response.body().string();
                    JSONObject o = new JSONObject(_rs);
                    if (response.isSuccessful()) {
                        if (o.has(MetaData.MESSAGE.STATUS)) {
                            String status = o.getString(MetaData.MESSAGE.STATUS);
                            if (status.equals(MetaData.MESSAGE.SUCCESS)) {
                                Gson g = new Gson();
                                RegisterResponse registerResponse = g.fromJson(_rs, RegisterResponse.class);
                                view.onUserResponseReceived(registerResponse);
                            } else {
                                view.showMessage(MessageParser.showMessageForSuccessCode(o));
                            }
                        }
                    } else {
                        view.showMessage(MessageParser.showMessageForErrorCode(response));
                        Log.i("Status","UnAuthorized");
                        if (response.code() == 401) view.unAuthorized();
                    }
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                } catch (JSONException je) {
                    je.printStackTrace();
                } catch (Exception e) {
                    view.startLoginRegisterInError();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }


    @Override
    public void destroyCall() {
        if (call != null) {
            call.cancel();
        }
    }
}
