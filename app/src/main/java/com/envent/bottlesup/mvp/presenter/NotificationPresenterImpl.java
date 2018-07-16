package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.notification_response.NotificationBody;
import com.envent.bottlesup.network.MessageParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 4/20/18.
 */

public class NotificationPresenterImpl implements MyPresenter.NotificationPresenter {
    private MVPView.NotificationView view;
    private retrofit2.Call<NotificationBody> notificationCallback;

    @Override
    public void addView(MVPView.NotificationView view) {
        this.view = view;
    }

    @Override
    public void getNotification(String apiKey) {
        view.showSwipeProgress();
        notificationCallback = BottlesUpApplication.getApi().getNotificationList(apiKey, 50);
        notificationCallback.enqueue(new Callback<NotificationBody>() {
            @Override
            public void onResponse(Call<NotificationBody> call, Response<NotificationBody> response) {
                if (response.isSuccessful()) {
                    view.onNotificationResponse(response.body());
                } else {
                    try {
                        String error = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(error));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotificationBody> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideSwipeProgress();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
        view.hideSwipeProgress();
    }

    @Override
    public void cancelCall() {
        if (notificationCallback != null) notificationCallback.cancel();
    }

}
