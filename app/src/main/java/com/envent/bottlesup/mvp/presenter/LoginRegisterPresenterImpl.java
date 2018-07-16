package com.envent.bottlesup.mvp.presenter;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.LoginResponse;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.RegisterResponse;
import com.envent.bottlesup.network.MessageParser;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 3/16/18.
 */

public class LoginRegisterPresenterImpl implements MyPresenter.LoginRegisterPresenter {
    private MVPView.LoginRegisterView view;
    private Call<ResponseBody> registerCall;
    private Call<ResponseBody> loginCall;
    private Call<ResponseBody> verifyCall;
    private Call<ResponseBody> resendOTPCall;
    private Call<ResponseBody> resendReferralCodeCall;

    private final int API_INDEX_LOGIN = 1;
    private final int API_INDEX_REGISTER = 2;
    private final int API_INDEX_VERIFY = 3;
    private final int API_INDEX_RESEND_TOKEN = 4;
    private final int API_INDEX_RESEND_REFERRAL_CODE = 5;

    @Override
    public void addView(MVPView.LoginRegisterView view) {
        this.view = view;
    }

    @Override
    public void proceedLogin() {
        String userName = view.getSignInUserName();
        String password = view.getSignInPassword();

        if (TextUtils.isEmpty(userName)) {
            view.onSignInUserNameError(MetaData.MESSAGE.EMPTY);
        } else if (TextUtils.isEmpty(password)) {
            view.onSignInUserPasswordError(MetaData.MESSAGE.EMPTY);
        } else if (userName.length() != 10) {
            view.onSignInUserNameError(MetaData.MESSAGE.PHONE_NUMBER_ERROR);
        } else {
            //process to login
            view.showProgressDialog();
            HashMap<String, String> body = new HashMap<>();
            body.put("_username", userName);
            body.put("_password", password);
//            body.put("_username", "9851156565");
//            body.put("_password", "123456");
            loginCall = BottlesUpApplication.getApi().getLoginResponse(body);
            loginCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    handleResponse(response, API_INDEX_LOGIN);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    view.hideProgressDialog();
                    view.showMessage(MessageParser.showMessageForFailure(t));
                }
            });
        }
    }

    @Override
    public void proceedRegister() {
        String fName = view.getRegisterFirstName();
        String lName = view.getRegisterLastName();
        String email = view.getRegisterEmail();
        String mobile = view.getRegisterMobile();
        String dob = view.getRegisterDOB();
        int gender = view.getRegisterGender();
        String password = view.getRegisterPassword();
        String confirmPassword = view.getRegisterConfirmPassword();
        boolean isTermAccepted = view.getTermsCondition();
        String referrelCode = view.getReferralCode();

        if (TextUtils.isEmpty(fName)) {
            view.onRegisterFirstNameError(MetaData.MESSAGE.EMPTY);
        } else if (TextUtils.isEmpty(lName)) {
            view.onRegisterLastNameError(MetaData.MESSAGE.EMPTY);
        } else if (TextUtils.isEmpty(dob)) {
            view.showMessage("Please select Date of Birth");
        } else if (TextUtils.isEmpty(email)) {
            view.onRegisterEmailError(MetaData.MESSAGE.EMPTY);
        } else if (!UtilityMethods.isValidEmail(email)) {
            view.onRegisterEmailError(MetaData.MESSAGE.EMAIL_NOT_VALID);
        } else if (TextUtils.isEmpty(mobile)) {
            view.onRegisterMobileError(MetaData.MESSAGE.EMPTY);
        } else if (mobile.length() != 10) {
            view.onRegisterMobileError(MetaData.MESSAGE.PHONE_NUMBER_ERROR);
        } else if (password.length() < 5 || password.length() > 16) {
            view.onRegisterPasswordError(MetaData.MESSAGE.PASSWORD_LENGTH);
        } else if (!password.equals(confirmPassword)) {
            view.showMessage(MetaData.MESSAGE.PASSWORD_NOT_MATCHED);
        } else if (!isTermAccepted) {
            view.showMessage(MetaData.MESSAGE.MUST_ACCEPT_TERMS_AND_CONDITION);
        } else {
            //process to register
            view.showProgressDialog();

            String name = fName + " " + lName;
            registerCall = BottlesUpApplication.getApi().getRegisterResponse(name, email, mobile, dob, gender, isTermAccepted, password, referrelCode);


            registerCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    handleResponse(response, API_INDEX_REGISTER);
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    onResponseFailed(call, t);
                }
            });
        }
    }

    @Override
    public void proceedVerification(String apiKey, String userName, String code) {


        view.showProgressDialog();
        verifyCall = BottlesUpApplication.getApi().getVerifyResponse(apiKey, userName, code);
        verifyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                handleResponse(response, API_INDEX_VERIFY);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onResponseFailed(call, t);
            }
        });

    }

    private void onResponseFailed(Call<ResponseBody> call, Throwable t) {
        if (!call.isCanceled()) {
            view.hideProgressDialog();
            view.showMessage(MessageParser.showMessageForFailure(t));
        }
    }

    @Override
    public void proceedResendOTP(String apiKey, String userName) {
        view.showProgressDialog();
        resendOTPCall = BottlesUpApplication.getApi().getResendOTPResponse(userName, apiKey);
        resendOTPCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                handleResponse(response, API_INDEX_RESEND_TOKEN);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                onResponseFailed(call, t);
            }
        });
    }

    @Override
    public void proceedResendReferralcode(String apiKey, String referralCode) {
        view.showProgressDialog();
        resendReferralCodeCall = BottlesUpApplication.getApi().activateWithReferralCode(referralCode, apiKey);
        resendReferralCodeCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                handleResponse(response, API_INDEX_RESEND_REFERRAL_CODE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onResponseFailed(call, t);
            }
        });
    }

    @Override
    public void cancelCall() {
        if (registerCall != null)
            registerCall.cancel();
        if (verifyCall != null)
            verifyCall.cancel();
        if (loginCall != null)
            loginCall.cancel();
        if (resendOTPCall != null)
            resendOTPCall.cancel();
        if (resendReferralCodeCall != null)
            resendReferralCodeCall.cancel();
    }


    private void handleResponse(Response<ResponseBody> response, int apiIndex) {
        view.hideProgressDialog();
        try {
            if (response.isSuccessful()) {
                String _res = response.body().string();
                JSONObject o = new JSONObject(_res);
                if (o.has(MetaData.MESSAGE.STATUS)) {
                    String status = o.getString(MetaData.MESSAGE.STATUS);
                    if (status.equals(MetaData.MESSAGE.SUCCESS)) {
                        Gson g = new Gson();
                        switch (apiIndex) {
                            case API_INDEX_LOGIN:
                            case API_INDEX_RESEND_REFERRAL_CODE:
                                view.onLoginResponseReceived(g.fromJson(_res, LoginResponse.class));
                                break;
                            case API_INDEX_REGISTER:
                                view.onRegisterResponseReceived(g.fromJson(_res, RegisterResponse.class));
                                break;
                            case API_INDEX_VERIFY:
                                view.onVerifyUserResponseReceived(g.fromJson(_res, RegisterResponse.class));
                                break;
                        }
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
}
