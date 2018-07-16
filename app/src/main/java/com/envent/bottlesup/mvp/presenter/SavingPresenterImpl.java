package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.saving_response.SavingResponse;
import com.envent.bottlesup.network.MessageParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 5/9/18.
 */

public class SavingPresenterImpl implements MyPresenter.SavingPresenter {
    private MVPView.SavingView view;
    private Call<SavingResponse> savingCall;

    @Override
    public void addView(MVPView.SavingView view) {
        this.view = view;
    }

    @Override
    public void getSaving(String apiKey) {
        view.showProgressDialog();
        savingCall = BottlesUpApplication.getApi().getSaving(apiKey);
        savingCall.enqueue(new Callback<SavingResponse>() {
            @Override
            public void onResponse(Call<SavingResponse> call, Response<SavingResponse> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onSavingResponseReceived(response.body());
                } else {
                    try {
                        String erro = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(erro));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<SavingResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if (savingCall!=null) savingCall.cancel();
    }
}
