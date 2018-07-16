package com.envent.bottlesup.mvp.presenter;

import android.util.Log;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.mycart.CartItemRequest;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.model.mycart.RequestDrinkItem;
import com.envent.bottlesup.mvp.model.mycart.RequestFoodItem;
import com.envent.bottlesup.mvp.model.mycart.RequestItem;
import com.envent.bottlesup.mvp.model.server_response.order_request_response.OrderRequestResponse;
import com.envent.bottlesup.mvp.model.server_response.seating_places.SeatingPlaceResponse;
import com.envent.bottlesup.network.MessageParser;
import com.envent.bottlesup.utils.RequestItemSerializeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 3/29/18.
 */

public class MyCartPresenterImpl implements MyPresenter.MyCartPresenter {
    private MVPView.MyCartView view;
    private String TAG = getClass().getSimpleName();


    @Override
    public void addView(MVPView.MyCartView view) {
        this.view = view;
    }

    @Override
    public void getMyCartItems(boolean isForOnlyBalanceUpdate) {
        List<DrinkCart> drinkCarts = DrinkCart.getDrinkCartItems();
        List<FoodCart> foodCarts = FoodCart.getFoodCartItems();
        List<Object> carts = new ArrayList<>();
        carts.addAll(drinkCarts);
        carts.addAll(foodCarts);

        CheckedInVenue checkedInVenue = CheckedInVenue.getCheckedInVenue();
        if (checkedInVenue.isTaxInclusive()) {
            List<Object> calculatedCarts = new ArrayList<>();
            for (DrinkCart d : drinkCarts) {
                double bP = getExclusivePrice(d.getBottlesUpInclusivePrice(), checkedInVenue.getVat(), checkedInVenue.getServiceCharge());
                d.setBottlesUpPrice(bP);
                calculatedCarts.add(d);
            }

            for (FoodCart f : foodCarts) {
                double fP = getExclusivePrice(f.getBottlesUpInclusivePrice(), checkedInVenue.getVat(), checkedInVenue.getServiceCharge());
                f.setBottlesUpPrice(fP);
                calculatedCarts.add(f);
            }
            view.onMyCartItemsReceived(calculatedCarts, isForOnlyBalanceUpdate);
            return;
        }

        view.onMyCartItemsReceived(carts, isForOnlyBalanceUpdate);
    }

    private double getExclusivePrice(double taxInclusivePrice, int vat, double serviceCharge) {
        Log.i(TAG, "taxInclusivePrice " + taxInclusivePrice);
        Log.i(TAG, "Vat " + vat);
        Log.i(TAG, "ServiceCharge " + serviceCharge);

        double vatPercent = (double) (vat + 100) / 100;
        Log.i(TAG, "VatPercent " + vatPercent);
        double vatAmount = taxInclusivePrice - taxInclusivePrice / vatPercent;
        Log.i(TAG, "VatAmount " + vatAmount);

        double afterVatDeduction = taxInclusivePrice - vatAmount;
        Log.i(TAG, "After Vat " + afterVatDeduction);

        double servicePercent = (serviceCharge + 100) / 100;
        Log.i(TAG, "ServicePercent " + servicePercent);
        double serviceAmount = afterVatDeduction - afterVatDeduction / servicePercent;
        Log.i(TAG, "Service Amount " + serviceAmount);

        double finalVal = afterVatDeduction - serviceAmount;
        Log.i(TAG, "FInal " + finalVal);
        return finalVal;
    }

    @Override
    public void getSubTotal(List<Object> cartItems) {

        if (cartItems.size() < 1) return;

        CheckedInVenue checkedInVenue = CheckedInVenue.getCheckedInVenue();

        double totalBottlesUpPrice = 0;
        double totalMenuPrice = 0;
        double serviceChargeRate = checkedInVenue.getServiceCharge();
        double vatRate = (double) checkedInVenue.getVat();

        for (Object o : cartItems) {
            if (o instanceof FoodCart) {
                FoodCart fc = (FoodCart) o;
                totalBottlesUpPrice = totalBottlesUpPrice + (fc.getQuantity() * fc.getBottlesUpPrice());
                totalMenuPrice = totalMenuPrice + (fc.getQuantity() * fc.getMenuPrice());
            } else if (o instanceof DrinkCart) {
                DrinkCart dc = (DrinkCart) o;
                totalBottlesUpPrice = totalBottlesUpPrice + (dc.getQuantity() * dc.getBottlesUpPrice());
                totalMenuPrice = totalMenuPrice + (dc.getQuantity() * dc.getMenuPrice());
            }
        }

        //only check and calculate if the bottles up price and menu price is given
        double totalServiceCharges = totalBottlesUpPrice * serviceChargeRate / 100;
        double amountAfterServiceCharge = totalBottlesUpPrice + totalServiceCharges;
        double totalVatAmount = amountAfterServiceCharge * vatRate / 100;
        double grandTotal = totalBottlesUpPrice + totalServiceCharges + totalVatAmount;
        double savedAmount = totalMenuPrice - totalBottlesUpPrice;

        Log.i(TAG, "SubTotal" + totalBottlesUpPrice);
        Log.i(TAG, "ServiceCharge" + totalServiceCharges);
        Log.i(TAG, "Vat" + totalVatAmount);
        Log.i(TAG, "GrandTotal" + grandTotal);
        Log.i(TAG, "Saved" + savedAmount);
        view.onSubTotalReceived(totalBottlesUpPrice, totalServiceCharges, totalVatAmount, grandTotal, savedAmount);
    }

    @Override
    public void getSeatingPlace(int venueId, String apiKey) {
        view.showProgressDialog();
        Call<SeatingPlaceResponse> call = BottlesUpApplication.getApi().getSeatingPlaces(String.valueOf(venueId), apiKey);
        call.enqueue(new Callback<SeatingPlaceResponse>() {
            @Override
            public void onResponse(Call<SeatingPlaceResponse> call, Response<SeatingPlaceResponse> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onSeatingPlaceResponseReceived(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(errorBody));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<SeatingPlaceResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public boolean isBalanceSufficient(double grandTotal) {
        return User.getUser().getBalance() > grandTotal;
    }

    @Override
    public void requestOrder(List<Object> cartItems, double grandTotal, int seatingPlace, String onTheWay, String apiKey) {

        ArrayList<RequestFoodItem> foodItems = new ArrayList<>();
        ArrayList<RequestDrinkItem> drinkItems = new ArrayList<>();
        for (Object o : cartItems) {
            if (o instanceof FoodCart) {
                FoodCart fc = (FoodCart) o;
                foodItems.add(new RequestFoodItem(fc.getItemId(), fc.getQuantity(), fc.getRemark()));
            } else {
                DrinkCart dc = (DrinkCart) o;
                drinkItems.add(new RequestDrinkItem(dc.getItemId(), dc.getQuantity(), dc.getRemark(), dc.getDrinkOptionId(), dc.getMixtureId()));
            }
        }

        CartItemRequest cr = new CartItemRequest(CheckedInVenue.getCheckedInVenue().getVenueId(),
                seatingPlace, onTheWay, grandTotal, foodItems, drinkItems);

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(RequestItem.class, new RequestItemSerializeAdapter());
        Gson gson = gsonBuilder.create();

        JsonElement jsonElement = gson.toJsonTree(cr);
        JsonObject body = (JsonObject) jsonElement;

        Log.i("REQUEST", body.toString());

        proceedRequest(body, apiKey);
    }

    private void proceedRequest(JsonObject body, String apiKey) {
        view.showProgressDialog();
        Log.i("APIKEY", apiKey);
        Log.i("Request", body.toString());
        Call<OrderRequestResponse> call = BottlesUpApplication.getApi().getOrderResponse(body, apiKey);
        call.enqueue(new Callback<OrderRequestResponse>() {
            @Override
            public void onResponse(Call<OrderRequestResponse> call, Response<OrderRequestResponse> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onOrderResponseReceived(response.body());
                } else {
                    try {
                        String str = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(str));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderRequestResponse> call, Throwable t) {
                view.hideProgressDialog();
                view.showMessage(MessageParser.showMessageForFailure(t));
            }
        });
    }

    @Override
    public void cancelCall() {

    }
}
