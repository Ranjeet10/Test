package com.envent.bottlesup.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.presenter.ChangePasswordPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.MetaData;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ronem on 4/19/18.
 */

public class ChangePasswordActivity extends SimpleBaseActivity implements MVPView.ChangePasswordView {

    @BindString(R.string.change_password)
    String titleChangePassword;

    @BindView(R.id.edt_old_password)
    EditText edtOldPassword;
    @BindView(R.id.edt_new_password)
    EditText edtNewPassword;
    @BindView(R.id.edt_confirm_password)
    EditText edtConfirmPassword;

    private MyPresenter.ChangePasswordPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_layout);
        presenter = new ChangePasswordPresenterImpl();
        presenter.addView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(titleChangePassword);
    }

    @OnClick(R.id.btn_change_password)
    public void onChangePassword() {
        presenter.requestChangePassword();
    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(this, msg, Toast.LENGTH_SHORT).show();
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
    public String getOldPassword() {
        return edtOldPassword.getText().toString();
    }

    @Override
    public String getNewPassword() {
        return edtNewPassword.getText().toString();
    }

    @Override
    public String getConfirmPassword() {
        return edtConfirmPassword.getText().toString();
    }

    @Override
    public void onChangePasswordResponse(StatusMessage res) {
        showMessage(res.getMessage());
        if (res.getStatus().equals(MetaData.MESSAGE.SUCCESS)) {
            Intent newIntent = new Intent(this, BottlesUpSplash.class);
            newIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(newIntent);
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
