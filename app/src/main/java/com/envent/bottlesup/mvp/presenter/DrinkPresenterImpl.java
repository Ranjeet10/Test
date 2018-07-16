package com.envent.bottlesup.mvp.presenter;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.mvp.model.server_response.food_drink_response.FoodDrinkTabResponse;
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
 * Created by ronem on 4/2/18.
 */

public class DrinkPresenterImpl implements MyPresenter.DrinkPresenter {
    private MVPView.DrinkView view;
    private Call<ResponseBody> drinkCall;
    private Call<EventResponse> eventResponseCall;

    @Override
    public void addView(MVPView.DrinkView view) {
        this.view = view;
    }

    @Override
    public void getBanners(int venueId, String apiKey) {
        view.showProgressDialog();
        eventResponseCall = BottlesUpApplication.getApi().getEventByVenue(venueId, apiKey);
        eventResponseCall.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                if (response.isSuccessful()) {
                    view.onBannerResponse(response.body());
                } else {
                    view.hideProgressDialog();
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
            public void onFailure(Call<EventResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void getDrinkTabs(int venueId, String apiKey) {
        view.showProgressDialog();
        drinkCall = BottlesUpApplication.getApi().getDrinkCategoryResponse(String.valueOf(venueId), apiKey);
        drinkCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                view.hideProgressDialog();
                try {
                    if (response.isSuccessful()) {
                        String _res = response.body().string();
                        JSONObject o = new JSONObject(_res);
                        if (o.has(MetaData.MESSAGE.STATUS)) {
                            String status = o.getString(MetaData.MESSAGE.STATUS);
                            if (status.equals(MetaData.MESSAGE.SUCCESS)) {
                                Gson g = new Gson();
                                view.onDrinkTabsResponse(g.fromJson(_res, FoodDrinkTabResponse.class));
                            } else {
                                view.showMessage(MessageParser.showMessageForSuccessCode(o));
                            }
                        }
                    } else {
                        view.showMessage(MessageParser.showMessageForErrorCode(response));
                    }
                } catch (IOException | JSONException | NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                view.hideProgressDialog();
                if (!call.isCanceled()) {
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if (drinkCall != null) {
            drinkCall.cancel();
        }
        if (eventResponseCall != null) {
            eventResponseCall.cancel();
        }
    }
}
