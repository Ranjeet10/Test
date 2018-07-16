package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.DrinkItemResponse;
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
 * Created by ronem on 3/16/18.
 */

public class DrinkItemPresenterImpl implements MyPresenter.DrinkItemPresenter {
    private MVPView.DrinkItemView view;
    private Call<ResponseBody> itemCall;

    @Override
    public void addView(MVPView.DrinkItemView view) {
        this.view = view;
    }

    @Override
    public void getDrinkItems(int venueId, int categoryId, String apiKey) {
        view.showSwipeProgress();
        itemCall = BottlesUpApplication.getApi().getDrinkItemResponse(String.valueOf(venueId), String.valueOf(categoryId),50, apiKey);
        itemCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.hideSwipeProgress();
                try {
                    if (response.isSuccessful()) {
                        String _res = response.body().string();
                        JSONObject o = new JSONObject(_res);
                        if (o.has(MetaData.MESSAGE.STATUS)) {
                            String status = o.getString(MetaData.MESSAGE.STATUS);
                            if (status.equals(MetaData.MESSAGE.SUCCESS)) {
                                Gson g = new Gson();
                                view.onDrinkItemResponse(g.fromJson(_res, DrinkItemResponse.class));
                            } else {
                                view.showMessage(MessageParser.showMessageForSuccessCode(o));
                            }
                        }
                    } else {
                        view.showMessage(MessageParser.showMessageForErrorCode(response));
                        if (response.code() == 401) {
                            view.unAuthorized();
                        }
                    }
                } catch (IOException | JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if(!call.isCanceled()) {
                    view.hideSwipeProgress();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }
    @Override
    public void cancelCall() {
        if (itemCall != null) {
            itemCall.cancel();
        }
    }
}
