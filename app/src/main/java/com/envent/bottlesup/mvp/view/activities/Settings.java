package com.envent.bottlesup.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Switch;
import android.widget.TextView;

import com.envent.bottlesup.BuildConfig;
import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.utils.SessionManager;
import com.onesignal.OneSignal;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ronem on 3/16/18.
 */

public class Settings extends SimpleBaseActivity {
    @BindString(R.string.settings)
    String toolbarTitle;

    @BindView(R.id.enable_disable_notification_switch)
    Switch enableDisableNotification;
    @BindView(R.id.app_version)
    TextView appVersion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);

        final SessionManager sessionManager = new SessionManager(this);
        enableDisableNotification.setChecked(sessionManager.isNotificationEnabled());
        enableDisableNotification.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sessionManager.setNotificationEnabled(isChecked);
            enableDisableNotification.setChecked(isChecked);
            OneSignal.setSubscription(isChecked);
        });

        setAppVersion();
    }

    private void setAppVersion() {
        appVersion.setText(getResources().getString(R.string.app_version_value, BuildConfig.VERSION_NAME));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(toolbarTitle);
    }

    @OnClick(R.id.edit_profile_layout)
    public void onEditProfileLayoutClicked() {
        startActivity(new Intent(this, EditProfileActivity.class));
    }

    @OnClick(R.id.change_password_layout)
    public void onChanvePasswordLayoutClicked() {
        startActivity(new Intent(this, ChangePasswordActivity.class));
    }

    @Override
    protected void updateBalance() {

    }
}
