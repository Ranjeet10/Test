package com.envent.bottlesup.mvp.presenter;

import android.text.TextUtils;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.network.MessageParser;
import com.envent.bottlesup.utils.MetaData;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 4/19/18.
 */

public class ChangePasswordPresenterImpl implements MyPresenter.ChangePasswordPresenter {
    private MVPView.ChangePasswordView view;
    private Call<StatusMessage> call;

    @Override
    public void addView(MVPView.ChangePasswordView view) {
        this.view = view;
    }

    @Override
    public void requestChangePassword() {
        String op = view.getOldPassword();
        String np = view.getNewPassword();
        String cp = view.getConfirmPassword();

        if (TextUtils.isEmpty(op) || TextUtils.isEmpty(np) || TextUtils.isEmpty(cp)) {
            view.showMessage(MetaData.MESSAGE.EMPTY);
        } else if (!np.equals(cp)) {
            view.showMessage(MetaData.MESSAGE.NEW_PASSWORD_NOT_MATCHED);
        } else {
            HashMap<String, String> body = new HashMap<>();
            body.put("current_password", op);
            body.put("new_password", np);
            body.put("new_password_confirm", cp);
            makeRequest(body, User.getUser().getAccess_token());
        }

    }

    private void makeRequest(HashMap<String, String> map, String apiKey) {
        view.showProgressDialog();
        call = BottlesUpApplication.getApi().getChangePasswordResponse(map, apiKey);
        call.enqueue(new Callback<StatusMessage>() {
            @Override
            public void onResponse(Call<StatusMessage> call, Response<StatusMessage> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onChangePasswordResponse(response.body());
                } else {
                    try {
                        String str = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(str));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<StatusMessage> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });

    }

    @Override
    public void cancelCall() {
        if (call != null && call.isExecuted()) {
            call.cancel();
        }
    }
}
