package com.envent.bottlesup.mvp.view.activities;

import android.util.Log;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.alias_response.AliasResponse;
import com.envent.bottlesup.mvp.view.activities.base_activity.AliasBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;

import butterknife.BindString;

/**
 * Created by ronem on 3/16/18.
 */

public class TermsAndConditions extends AliasBaseActivity {
    @BindString(R.string.terms_and_condition)
    String toolbarTitle;


    @Override
    protected int getLayout() {
        return R.layout.alias_layout;
    }

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(toolbarTitle);
        presenter.getAlias("terms-and-condition");
    }

    @Override
    protected void updateBalance() {

    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
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
    public void onAliasResponseReceived(AliasResponse response) {
        if (response != null && response.getStatus().equals(MetaData.MESSAGE.SUCCESS)) {
            Log.i("FAG", response.toString());
            aliasTitle.setText(response.getAliasData().getTitle());
            UtilityMethods.loadHtmlText(response.getAliasData().getDescription(), aliasDetail);
        }
    }
}

