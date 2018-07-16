package com.envent.bottlesup.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.envent.bottlesup.BuildConfig;
import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.LoginRegisterData;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.RegisterResponse;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.SplashPresenterImpl;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;
import com.google.android.gms.maps.model.Dash;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BottlesUpSplash extends AppCompatActivity implements MVPView.SplashView {
    @BindView(R.id.splash_progress)
    ProgressBar progressBar;

    private MyPresenter.SplashPresenter presenter;
    private User user;
    private Unbinder unbinder;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UtilityMethods.getFullScreen(this);
        setContentView(R.layout.activity_bottles_up_splash);
        unbinder = ButterKnife.bind(this);
        user = User.getUser();
        presenter = new SplashPresenterImpl();
        presenter.addView(this);
        sessionManager = new SessionManager(this);

        sessionManager.checkAndSavetheDeviceId(user);

        if (user != null) {
            presenter.getUser(user.getAccess_token());
        } else {
            new Handler().postDelayed(() -> {
                if (sessionManager.isFirstRun()) {
                    sessionManager.setFirstRun();
                    launchIntent(Tutorial.class);
                } else {
                    launchIntent(LoginRegister.class);
                }
            }, 5000);
        }

    }

    private void launchIntent(Class<?> intentClass) {
        Intent i = new Intent(BottlesUpSplash.this, intentClass);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        if (intentClass == Tutorial.class) {
            i.putExtra(MetaData.KEY.IS_FROM_SPLASH, true);
        }
        startActivity(i);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        presenter.destroyCall();
    }

    @Override
    public void onUserResponseReceived(RegisterResponse response) {
        if (response.getRegisterData() != null) {
            LoginRegisterData data = response.getRegisterData();
            user.setAllowedReferralCode(data.getAllowedReferralCount());
            user.setActivated(data.isActive());
            user.setVerified(data.isVerified());
            user.setBlocked(data.isBlocked());
            if (BuildConfig.DEBUG) {
                user.setBalance(data.getBalance() + MetaData.EXTRA_BALANCE);
            } else {
                user.setBalance(data.getBalance());
            }
            user.save();
            new Handler().postDelayed(() -> {
                if (!user.isBlocked() && user.isVerified() || user.isActivated()) {
                    launchIntent(Dashboard.class);
                } else {
                    launchIntent(LoginRegister.class);
                }
            }, 2000);
        }

    }

    @Override
    public void startLoginRegisterInError() {

        User.getUser().delete();
        launchIntent(LoginRegister.class);

    }

    @Override
    public void showMessage(String msg) {
        CustomToast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unAuthorized() {
        startLoginRegisterInError();
    }

    @Override
    public void showProgressDialog() {
        if (progressBar != null)
            progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        if (progressBar != null)
            progressBar.setVisibility(View.GONE);
    }
}
