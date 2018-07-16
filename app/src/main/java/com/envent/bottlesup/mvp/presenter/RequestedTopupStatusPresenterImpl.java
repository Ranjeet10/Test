package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.topup_status_response.TopupStatusResponse;
import com.envent.bottlesup.network.MessageParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 6/3/18.
 */

public class RequestedTopupStatusPresenterImpl implements MyPresenter.RequestedTopupStatusPresenter {

    private MVPView.RequestedTopupStatusView view;
    private Call<TopupStatusResponse> responseCall;


    @Override
    public void addView(MVPView.RequestedTopupStatusView view) {
        this.view = view;
    }

    @Override
    public void getTopupStatus(String apiKey) {
        view.showSwipeProgress();
        responseCall = BottlesUpApplication.getApi().getTopupsStatusResponse(apiKey);
        responseCall.enqueue(new Callback<TopupStatusResponse>() {
            @Override
            public void onResponse(Call<TopupStatusResponse> call, Response<TopupStatusResponse> response) {
                view.hideSwipeProgress();
                if (response.isSuccessful()) {
                    view.onRequestedTopUpStatusResponse(response.body());
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
            public void onFailure(Call<TopupStatusResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideSwipeProgress();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if (responseCall != null) responseCall.cancel();
    }
}
