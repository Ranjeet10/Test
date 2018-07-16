package com.envent.bottlesup.mvp.presenter;

import android.support.annotation.NonNull;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.PendingBalanceResponse;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.model.server_response.mybalance.MyBalanceResponse;
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

public class MyWalletPresenterImpl implements MyPresenter.MyWalletPresenter {
    private MVPView.MyWalletView view;
    private Call<ResponseBody> balanceCall;
    private Call<PendingBalanceResponse> pendingBalanceCall;
    private Call<ResponseBody> topupCall;
    private Call<ResponseBody> khaltiTopupCall;
    private final int BALANCE_API = 1;
    private final int TOPUP_API = 2;

    @Override
    public void addView(MVPView.MyWalletView view) {
        this.view = view;
    }

    @Override
    public void getMyBalance(String apiKey) {
        view.showProgressDialog();
        balanceCall = BottlesUpApplication.getApi().getBalanceResponse(apiKey);
        balanceCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                parseResponse(response, BALANCE_API);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void getPendingBalance(String apiKey, String isFrom) {
        view.showProgressDialog();
        pendingBalanceCall = BottlesUpApplication.getApi().getPendingBalance(apiKey);
        pendingBalanceCall.enqueue(new Callback<PendingBalanceResponse>() {
            @Override
            public void onResponse(Call<PendingBalanceResponse> call, Response<PendingBalanceResponse> response) {
                if (response.isSuccessful()) {
                    view.onPendingBalanceReceived(response.body(), isFrom);
                } else {
                    view.showMessage("Could not fetch the pending balance");
                    view.hideProgressDialog();
                    if (response.code() == 401) {
                        view.unAuthorized();
                    }
                }
            }

            @Override
            public void onFailure(Call<PendingBalanceResponse> call, Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    private void parseResponse(Response<ResponseBody> response, int api) {
        view.hideProgressDialog();
        if (response.isSuccessful()) {
            try {
                String _res = response.body().string();
                JSONObject o = new JSONObject(_res);
                if (o.has(MetaData.MESSAGE.STATUS)) {
                    String status = o.getString(MetaData.MESSAGE.STATUS);
                    if (status.equals(MetaData.MESSAGE.SUCCESS)) {
                        Gson g = new Gson();
                        switch (api) {
                            case BALANCE_API:
                                view.onMybalanceReceived(g.fromJson(_res, MyBalanceResponse.class));
                                break;
                            case TOPUP_API:
                                view.onTopUpReceived(g.fromJson(_res, StatusMessage.class));
                                break;
                        }
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
        }
    }

    @Override
    public void requestTopup(String apiKey, double amount, int venueId, int seatingPlace) {
        view.showProgressDialog();
        topupCall = BottlesUpApplication.getApi().getTopupResponse(venueId, amount, seatingPlace, apiKey);
        topupCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                view.hideProgressDialog();
                parseResponse(response, TOPUP_API);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public boolean isAmountValid(int amount) {
        if (amount > MetaData.TRANSACTION_BALANCE_LIMIT) {
                view.showMessage(MetaData.MESSAGE.EXCESS_TRANSACTION_LIMIT);
            return false;
        } else {
            int walletBalance = (int) User.getUser().getBalance();
            int balanceAfterTopUp = walletBalance + amount;
            if (balanceAfterTopUp > MetaData.WALLET_BALANCE_LIMIT) {
                    view.showMessage(MetaData.MESSAGE.EXCESS_BALANCE_LIMIT);
                return false;
            }
        }
        return true;
    }

    @Override
    public void requestKhaltiTopup(String apiKey, int amount, String khaltiToken) {
        view.showProgressDialog();
        khaltiTopupCall = BottlesUpApplication.getApi().getTopupWithKhaltiResponse(khaltiToken, amount, apiKey);
        khaltiTopupCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                view.hideProgressDialog();
                parseResponse(response, TOPUP_API);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                if (!call.isCanceled()) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            }
        });
    }

    @Override
    public void cancelAllCall() {
        if (balanceCall != null) {
            balanceCall.cancel();
        }
        if (topupCall != null) {
            topupCall.cancel();
        }
        if (khaltiTopupCall != null) {
            khaltiTopupCall.cancel();
        }
        if (pendingBalanceCall != null) {
            pendingBalanceCall.cancel();
        }
    }

}
