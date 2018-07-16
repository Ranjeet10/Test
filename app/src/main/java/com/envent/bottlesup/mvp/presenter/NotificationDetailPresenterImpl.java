package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.network.MessageParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 5/20/18.
 */

public class NotificationDetailPresenterImpl implements MyPresenter.NotificationDetailPresenter {
    private MVPView.NotificationDetailView view;
    private Call<StatusMessage> call;

    @Override
    public void addView(MVPView.NotificationDetailView view) {
        this.view = view;
    }

    @Override
    public void deleteNotification(String apiKey, int notificationId) {
        view.showProgressDialog();
        call = BottlesUpApplication.getApi().deleteNotification(apiKey, notificationId);
        call.enqueue(new Callback<StatusMessage>() {
            @Override
            public void onResponse(Call<StatusMessage> call, Response<StatusMessage> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onNotificationDeleteResponse(response.body());
                } else {
                    try {
                        String err = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(err));
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
                view.hideProgressDialog();
                if (!call.isCanceled()) {
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if (call != null) call.cancel();
    }
}
