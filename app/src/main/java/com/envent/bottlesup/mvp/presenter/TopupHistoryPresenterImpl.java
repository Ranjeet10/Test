package com.envent.bottlesup.mvp.presenter;


import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.topup_response.TopupHistoryResponse;
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
 * Created by ronem on 4/10/18.
 */

public class TopupHistoryPresenterImpl implements MyPresenter.TopUpHistoryPresenter {
    private MVPView.TopUpHistoryView view;
    private Call<ResponseBody> topupCall;

    @Override
    public void addView(MVPView.TopUpHistoryView view) {
        this.view = view;
    }

    @Override
    public void getTopUpHistory(int page, int limit, String apiKey) {
        view.showSwipeProgress();

        topupCall = BottlesUpApplication.getApi().getTopupHistoryResponse(page, limit, apiKey);
        topupCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.hideSwipeProgress();
                if (response.isSuccessful()) {
                    try {
                        String _res = response.body().string();
                        JSONObject o = new JSONObject(_res);
                        if (o.has(MetaData.MESSAGE.STATUS)) {
                            String status = o.getString(MetaData.MESSAGE.STATUS);
                            if (status.equals(MetaData.MESSAGE.SUCCESS)) {
                                Gson g = new Gson();
                                view.onTopUpHistoryReceived(g.fromJson(_res, TopupHistoryResponse.class));

                            } else {
                                view.showMessage(MessageParser.showMessageForSuccessCode(o));
                            }
                        } else {
                            view.showMessage(MessageParser.showMessageForSuccessCode(o));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    view.showMessage(MessageParser.showMessageForErrorCode(response));
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideSwipeProgress();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if(topupCall!=null)topupCall.cancel();
    }
}
