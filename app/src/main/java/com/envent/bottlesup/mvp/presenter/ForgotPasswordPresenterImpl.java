package com.envent.bottlesup.mvp.presenter;

import android.text.TextUtils;

import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.network.MessageParser;
import com.envent.bottlesup.utils.MetaData;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ronem on 4/25/18.
 */

public class ForgotPasswordPresenterImpl implements MyPresenter.ForgotPasswordPresenter {
    private MVPView.ForgotPasswordView view;
    private Call<StatusMessage> otpCall;
    private Call<StatusMessage> resetPasswordCall;

    @Override
    public void addView(MVPView.ForgotPasswordView view) {
        this.view = view;
    }

    @Override
    public void checkOtpFieldValidationAndRequestOtp() {
        String userName = view.getOtpUserName();
        if (TextUtils.isEmpty(userName)) {
            view.setOtpUserNameError(MetaData.MESSAGE.EMPTY);
        }

        requestOtp(userName);
    }

    @Override
    public void requestOtp(String userName) {
        view.showProgressDialog();
        otpCall = BottlesUpApplication.getApi().getForgotPasswordResetTokenResponse(userName);
        otpCall.enqueue(new Callback<StatusMessage>() {
            @Override
            public void onResponse(Call<StatusMessage> call, Response<StatusMessage> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onOtpSentResponse(response.body());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        view.showMessage(MessageParser.showMessageForErrorCode(errorBody));
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
                onFailed(call, t);
            }
        });

    }

    private void onFailed(Call<StatusMessage> call, Throwable t) {
        if (!call.isCanceled()) {
            view.hideProgressDialog();
            view.showMessage(MessageParser.showMessageForFailure(t));
        }
    }

    @Override
    public void checkResetPasswordValidationAndRequestResetPassword() {
        String token = view.getResetOtpToken();
        String userName = view.getResetUserName();
        String password = view.getResetPassword();
        String confirmPassword = view.getResetConfirmPassword();

        if (TextUtils.isEmpty(token)) {
            view.setResetOtpTokenError(MetaData.MESSAGE.EMPTY);
        } else if (TextUtils.isEmpty(userName)) {
            view.setResetUerNameError(MetaData.MESSAGE.EMPTY);
        } else if (TextUtils.isEmpty(password)) {
            view.setResetPasswordError(MetaData.MESSAGE.EMPTY);
        } else if (TextUtils.isEmpty(confirmPassword)) {
            view.setResetConfirmPasswordError(MetaData.MESSAGE.EMPTY);
        } else if (!password.equals(confirmPassword)) {
            view.showMessage(MetaData.MESSAGE.NEW_PASSWORD_NOT_MATCHED);
        } else {
            resetPassword(Integer.parseInt(token), userName, password);
        }
    }

    @Override
    public void resetPassword(int token, String userName, String newPassword) {
        view.showProgressDialog();
        resetPasswordCall = BottlesUpApplication.getApi().getForgotPasswordResetPasswordResponse(token, userName, newPassword);
        resetPasswordCall.enqueue(new Callback<StatusMessage>() {
            @Override
            public void onResponse(Call<StatusMessage> call, Response<StatusMessage> response) {
                view.hideProgressDialog();
                if (response.isSuccessful()) {
                    view.onPasswordResetResponse(response.body());
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
            public void onFailure(Call<StatusMessage> call, Throwable t) {
                onFailed(call, t);
            }
        });
    }

    @Override
    public void cancelCall() {
        if (otpCall != null) {
            otpCall.cancel();
        }

        if (resetPasswordCall != null) {
            resetPasswordCall.cancel();
        }
    }
}
