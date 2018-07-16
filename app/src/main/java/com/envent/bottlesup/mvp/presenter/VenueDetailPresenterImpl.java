package com.envent.bottlesup.mvp.presenter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.text.Html;

import com.envent.bottlesup.R;
import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.network.MessageParser;
import com.envent.bottlesup.utils.MetaData;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 4/18/18.
 */

public class VenueDetailPresenterImpl implements MyPresenter.VenueDetailPresenter {
    private MVPView.VenueDetailView view;
    private Call<EventResponse> eventResponseCall;
    private Call<StatusMessage> checkoutCall;

    @Override
    public void addView(MVPView.VenueDetailView view) {
        this.view = view;
    }

    @Override
    public void getEvents(int venueId, String apiKey) {
        view.showProgressDialog();
        eventResponseCall = BottlesUpApplication.getApi().getEventByVenue(venueId, apiKey);
        eventResponseCall.enqueue(new Callback<EventResponse>() {
            @Override
            public void onResponse(Call<EventResponse> call, Response<EventResponse> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onEventResponseReceived(response.body());
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
            public void onFailure(Call<EventResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void checkout(final Context context, String apiKey, final CheckedInVenue checkedInVenue) {

        List<FoodCart> fc = FoodCart.getFoodCartItems();
        List<DrinkCart> dc = DrinkCart.getDrinkCartItems();
        boolean isItemPresent = false;
        if (fc.size() > 0) {
            isItemPresent = true;
        } else if (dc.size() > 0) {
            isItemPresent = true;
        }


        if (!isItemPresent) {
            checkOutNow(apiKey, checkedInVenue);
        } else {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
            alertBuilder.setCancelable(false);

            String msg = context.getResources().getString(R.string.item_present);

            alertBuilder.setPositiveButton("Check out", (dialog, which) -> {
                dialog.dismiss();
                checkOutNow(apiKey, checkedInVenue);
            });
            alertBuilder.setNegativeButton("Close", (dialog, which) -> dialog.dismiss());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                alertBuilder.setMessage(Html.fromHtml(msg, Html.FROM_HTML_MODE_LEGACY));
            } else {
                alertBuilder.setMessage(Html.fromHtml(msg));
            }
            AlertDialog dialog = alertBuilder.create();
            dialog.show();
        }

    }

    private void checkOutNow(String apiKey, CheckedInVenue checkedInVenue) {
        view.showProgressDialog();
        checkoutCall = BottlesUpApplication.getApi().checkOut(apiKey, checkedInVenue.getCheckInCheckOutId());
        checkoutCall.enqueue(new Callback<StatusMessage>() {
            @Override
            public void onResponse(Call<StatusMessage> call, Response<StatusMessage> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals(MetaData.MESSAGE.SUCCESS)) {
                        view.onCheckOutSuccess();
                    } else {
                        view.showMessage(response.body().getMessage());
                    }
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
            public void onFailure(Call<StatusMessage> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelCall() {
        if (eventResponseCall != null) eventResponseCall.cancel();
        if (checkoutCall != null) checkoutCall.cancel();
    }
}
