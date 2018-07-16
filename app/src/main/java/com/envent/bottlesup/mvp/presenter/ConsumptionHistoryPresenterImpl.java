package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.consumption_history.ConsumptionHistoryResponse;
import com.envent.bottlesup.network.MessageParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 4/10/18.
 */

public class ConsumptionHistoryPresenterImpl implements MyPresenter.ConsumptionHistoryPresenter {
    private MVPView.ConsumptionHistoryView view;
    private Call<ConsumptionHistoryResponse> cCall;


    @Override
    public void addView(MVPView.ConsumptionHistoryView view) {
        this.view = view;
    }

    @Override
    public void getConsumptionHistory(String apiKey) {
        view.showSwipeProgress();
        cCall = BottlesUpApplication.getApi().getConsumptionHistory(apiKey);
        cCall.enqueue(new Callback<ConsumptionHistoryResponse>() {
            @Override
            public void onResponse(Call<ConsumptionHistoryResponse> call, Response<ConsumptionHistoryResponse> response) {
                view.hideSwipeProgress();
                if (response.isSuccessful()) {
                    view.onConsumptionHistoryReceived(response.body());
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
            public void onFailure(Call<ConsumptionHistoryResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideSwipeProgress();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if (cCall != null) cCall.cancel();
    }
}
