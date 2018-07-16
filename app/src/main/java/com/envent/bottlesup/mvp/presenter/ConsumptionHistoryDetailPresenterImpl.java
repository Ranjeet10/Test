package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.CancelOrderResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.ConsumptionHistoryDetailResponse;
import com.envent.bottlesup.network.MessageParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 5/8/18.
 */

public class ConsumptionHistoryDetailPresenterImpl implements MyPresenter.ConsumptionHistoryDetailPresenter {
    private MVPView.ConsumptionHistoryDetailView view;
    private Call<ConsumptionHistoryDetailResponse> responseCall;
    private Call<CancelOrderResponse> cancelOrderResponse;

    @Override
    public void addView(MVPView.ConsumptionHistoryDetailView view) {
        this.view = view;
    }

    @Override
    public void getConsumptionHistoryDetail(String apiKey, int orderId) {
        view.showProgressDialog();
        responseCall = BottlesUpApplication.getApi().getOrderDetail(orderId, apiKey);
        responseCall.enqueue(new Callback<ConsumptionHistoryDetailResponse>() {
            @Override
            public void onResponse(Call<ConsumptionHistoryDetailResponse> call, Response<ConsumptionHistoryDetailResponse> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onConsumptionHistoryDetailReceived(response.body());
                } else {
                    try {
                        String errr = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(errr));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }

                }
            }

            @Override
            public void onFailure(Call<ConsumptionHistoryDetailResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });

    }

    @Override
    public void deletePendingOrder(String apiKey, int orderId) {

        view.showProgressDialog();
        cancelOrderResponse = BottlesUpApplication.getApi().deletePendingOrder(orderId, apiKey);
        cancelOrderResponse.enqueue(new Callback<CancelOrderResponse>() {
            @Override
            public void onResponse(Call<CancelOrderResponse> call, Response<CancelOrderResponse> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onCancelOrderResponseReceived(response.body());
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
            public void onFailure(Call<CancelOrderResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });


    }

    @Override
    public void cancelCall() {
        if (responseCall!=null) responseCall.cancel();
    }
}
