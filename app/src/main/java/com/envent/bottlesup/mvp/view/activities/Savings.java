package com.envent.bottlesup.mvp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.saving_response.SavingResponse;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.SavingPresenterImpl;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.UtilityMethods;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by ronem on 3/16/18.
 */

public class Savings extends SimpleBaseActivity implements MVPView.SavingView {

    @BindString(R.string.saving)
    String toolbarTitle;
    @BindView(R.id.your_expenses)
    TextView yourExpenses;
    @BindView(R.id.your_savings)
    TextView yourSavings;

    private MyPresenter.SavingPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savings_layout);

        presenter = new SavingPresenterImpl();
        presenter.addView(this);
        presenter.getSaving(getUser().getAccess_token());

    }


    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(toolbarTitle);
    }

    @Override
    protected void updateBalance() {

    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(this, msg, Toast.LENGTH_LONG);
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
    public void onSavingResponseReceived(SavingResponse response) {
        yourExpenses.setText(getString(R.string.expenses_value, UtilityMethods.getTwoPlaceDecimal(response.getData().getTotalExpenses())));
        yourSavings.setText(getString(R.string.saving_value, UtilityMethods.getTwoPlaceDecimal(response.getData().getSavings())));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cancelCall();
    }
}
