package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.alias_response.AliasResponse;
import com.envent.bottlesup.network.MessageParser;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 5/7/18.
 */

public class AliasPresenterIml implements MyPresenter.AliasPresenter {
    private MVPView.AliasView view;
    private Call<AliasResponse> aliasResponseCall;

    @Override
    public void addView(MVPView.AliasView view) {
        this.view = view;
    }

    @Override
    public void getAlias(String alias) {
        view.showProgressDialog();
        aliasResponseCall = BottlesUpApplication.getApi().getAlias(alias);
        aliasResponseCall.enqueue(new Callback<AliasResponse>() {
            @Override
            public void onResponse(Call<AliasResponse> call, Response<AliasResponse> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onAliasResponseReceived(response.body());
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
            public void onFailure(Call<AliasResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if (aliasResponseCall != null) {
            aliasResponseCall.cancel();
        }
    }
}
