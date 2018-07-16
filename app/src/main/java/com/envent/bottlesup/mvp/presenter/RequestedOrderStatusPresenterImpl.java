package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.order_status_response.OrderStatusResponse;
import com.envent.bottlesup.network.MessageParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 6/3/18.
 */

public class RequestedOrderStatusPresenterImpl implements MyPresenter.RequestedOrderStatusPresenter {
    private Call<OrderStatusResponse> responseCall;
    private MVPView.RequestedOrderStatusView view;

    @Override
    public void addView(MVPView.RequestedOrderStatusView view) {
        this.view = view;
    }

    @Override
    public void getOrderStatus(String apiKey) {
        view.showSwipeProgress();
        responseCall = BottlesUpApplication.getApi().getOdersStatusResponse(apiKey);
        responseCall.enqueue(new Callback<OrderStatusResponse>() {
            @Override
            public void onResponse(Call<OrderStatusResponse> call, Response<OrderStatusResponse> response) {
                view.hideSwipeProgress();
                if (response.isSuccessful()) {
                    view.onRequestedOrderStatusResponse(response.body());
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
            public void onFailure(Call<OrderStatusResponse> call, Throwable t) {
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
