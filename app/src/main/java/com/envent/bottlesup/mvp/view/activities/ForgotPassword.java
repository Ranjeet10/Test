package com.envent.bottlesup.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.presenter.ForgotPasswordPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ronem on 4/22/18.
 */

public class ForgotPassword extends SimpleBaseActivity implements MVPView.ForgotPasswordView {
    @BindString(R.string.password_recovery)
    String passwordRecoverTitle;
    @BindView(R.id.forgot_password_request_otp_parent)
    RelativeLayout showOtpLayout;
    @BindView(R.id.edt_otp_user_name)
    EditText edtOtUserName1;

    //reset part
    @BindView(R.id.forgot_password_reset_layout_parent)
    ScrollView resetPasswordLayout;
    @BindView(R.id.edt_otp_token)
    EditText edtOtpToken;
    @BindView(R.id.edt_user_name)
    EditText edtUserName2;
    @BindView(R.id.edt_user_password)
    EditText edtUserPassword;
    @BindView(R.id.edt_user_confirm_password)
    EditText edtUserConfirmPassword;

    private MyPresenter.ForgotPasswordPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password_layout);
        presenter = new ForgotPasswordPresenterImpl();
        presenter.addView(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(passwordRecoverTitle);
    }

    @OnClick(R.id.btn_request_otp)
    public void onRequestOTPClicked() {
        presenter.checkOtpFieldValidationAndRequestOtp();
    }

    @OnClick(R.id.already_have_token_layout)
    public void onAlreadyHaveTokenLayoutClicked() {
        viewAsFoundToken();
    }

    private void viewAsFoundToken() {
        resetPasswordLayout.setVisibility(View.VISIBLE);
        showOtpLayout.setVisibility(View.GONE);
        UtilityMethods.hideKeyboard(this,resetPasswordLayout);
    }


    @OnClick(R.id.btn_reset_password)
    public void onResetPasswordClicked() {
        presenter.checkResetPasswordValidationAndRequestResetPassword();
    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(this, msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void unAuthorized() {
        logoutNow();
    }

    @Override
    public void showProgressDialog() {
        showMyProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        cancelProgressDialog();
    }

    @Override
    public String getOtpUserName() {
        return edtOtUserName1.getText().toString();
    }

    @Override
    public void setOtpUserNameError(String msg) {
        edtOtUserName1.setError(msg);
    }

    @Override
    public String getResetOtpToken() {
        return edtOtpToken.getText().toString();
    }

    @Override
    public void setResetOtpTokenError(String msg) {
        edtOtpToken.setError(msg);
    }

    @Override
    public String getResetUserName() {
        return edtUserName2.getText().toString();
    }

    @Override
    public void setResetUerNameError(String msg) {
        edtUserName2.setError(msg);
    }

    @Override
    public String getResetPassword() {
        return edtUserPassword.getText().toString();
    }

    @Override
    public void setResetPasswordError(String msg) {
        edtUserPassword.setError(msg);
    }

    @Override
    public String getResetConfirmPassword() {
        return edtUserConfirmPassword.getText().toString();
    }

    @Override
    public void setResetConfirmPasswordError(String msg) {
        edtUserConfirmPassword.setError(msg);
    }

    @Override
    public void onOtpSentResponse(StatusMessage response) {
        showMessage(response.getMessage());
        if (response.getStatus().equals(MetaData.MESSAGE.SUCCESS)) {
            viewAsFoundToken();
        }
    }

    @Override
    public void onPasswordResetResponse(StatusMessage response) {
        showMessage(response.getMessage());
        if (response.getStatus().equals(MetaData.MESSAGE.SUCCESS)) {
            Intent i = new Intent(this, BottlesUpSplash.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cancelCall();
    }

    @Override
    protected void updateBalance() {

    }
}
