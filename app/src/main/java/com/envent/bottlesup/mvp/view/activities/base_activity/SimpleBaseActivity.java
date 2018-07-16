package com.envent.bottlesup.mvp.view.activities.base_activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.view.activities.BottlesUpSplash;
import com.envent.bottlesup.mvp.view.activities.Dashboard;
import com.envent.bottlesup.mvp.view.customview.BottlesUpNormalTextView;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/15/18.
 */

@SuppressLint("Registered")
public abstract class SimpleBaseActivity extends PushNotificationReceiverHandlerActivity {

    @BindView(R.id.toolbarTitle)
    BottlesUpNormalTextView toolbarTitleTextView;
    @BindView(R.id.btn_delete)
    ImageView btnDelete;

    private Handler handler;
    private Runnable runnable;
    private Unbinder unbinder;

    private ProgressDialog progressDialog;


    @Override
    public void setContentView(@LayoutRes int layoutId) {
        super.setContentView(layoutId);
        unbinder = ButterKnife.bind(this);
    }

    @OnClick(R.id.navigation_back)
    public void onNavigationBackPressed() {
        UtilityMethods.hideKeyboard(this, toolbarTitleTextView);
        onBackPressed();
    }

    public User getUser() {
        return User.getUser();
    }

    public BottlesUpNormalTextView getToolbarTitleTextView() {
        return toolbarTitleTextView;
    }

    public void showBtnDelete() {
        btnDelete.setVisibility(View.VISIBLE);
    }

    public void instantiateHandler() {
        handler = new Handler();
    }

    public void showRefreshLayout(final SwipeRefreshLayout layout) {
        if (!layout.isRefreshing()) {
            runnable = () -> {
                try {
                    layout.setRefreshing(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            handler.postDelayed(runnable, 200);
        }
    }

    public void hideRefreshLayout(final SwipeRefreshLayout layout) {
        runnable = () -> {
            try {
                layout.setRefreshing(false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        handler.postDelayed(runnable, 200);
    }

    public void showMyProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(MetaData.MESSAGE.PLEASE_WAIT);
        }

        if (!progressDialog.isShowing()) progressDialog.show();
    }

    public void cancelProgressDialog() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
            handler = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (!Dashboard.isDashboardRunning) {
            Intent splashIntent = new Intent(this, BottlesUpSplash.class);
            splashIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(splashIntent);
        }
        super.onBackPressed();
    }
}
