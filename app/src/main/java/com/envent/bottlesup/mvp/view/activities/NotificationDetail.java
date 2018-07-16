package com.envent.bottlesup.mvp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.model.server_response.notification_response.ItemsItem;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.NotificationDetailPresenterImpl;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.MyLog;
import com.envent.bottlesup.utils.UtilityMethods;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ronem on 5/20/18.
 */

public class NotificationDetail extends SimpleBaseActivity implements MVPView.NotificationDetailView {

    private String TAG = getClass().getSimpleName();

    @BindString(R.string.notification)
    String toolbarTitle;

    @BindView(R.id.btn_delete)
    ImageView btnDelete;
    @BindView(R.id.notification_title)
    TextView notificationTitle;
    @BindView(R.id.created_on)
    TextView createdOn;
    @BindView(R.id.notification_body)
    TextView notificationBody;
    @BindView(R.id.notification_type)
    TextView notificationType;

    private MyPresenter.NotificationDetailPresenter presenter;
    private ItemsItem notification;
    private String NOTIFICATION_TYPE;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_detail);
        ButterKnife.bind(this);
        presenter = new NotificationDetailPresenterImpl();
        presenter.addView(this);

        showBtnDelete();
        getIntentData();
    }

    private void getIntentData() {
        notification = getIntent().getParcelableExtra(MetaData.KEY.NOTIFICATION);
        NOTIFICATION_TYPE = getIntent().getStringExtra(MetaData.KEY.NOTIFICATION_TYPE);
        String title = getIntent().getStringExtra(MetaData.KEY.TITLE);
        if (!TextUtils.isEmpty(title)) {
            getToolbarTitleTextView().setText(title);
        } else {
            getToolbarTitleTextView().setText(toolbarTitle);
        }
        MyLog.i(TAG, notification.toString());
        notificationTitle.setText(notification.getTitle());
        notificationBody.setText(notification.getMessage());
        createdOn.setText(notification.getCreatedOn());
        notificationType.setText(UtilityMethods.captializeAllFirstLetter(notification.getType()));

    }

    @Override
    protected void updateBalance() {

    }

    @OnClick(R.id.btn_delete)
    public void onDelete() {
        if (NOTIFICATION_TYPE.equals(MetaData.KEY.NOTIFICATION_TYPE_ALERT)) {
            presenter.deleteNotification(getUser().getAccess_token(), notification.getId());
        } else {
            //todo
        }
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
    public void onNotificationDeleteResponse(StatusMessage statusMessage) {
        showMessage(statusMessage.getMessage());
        if (statusMessage.getStatus().equalsIgnoreCase(MetaData.MESSAGE.SUCCESS)) {
            Intent i = new Intent();
            if (NOTIFICATION_TYPE.equals(MetaData.KEY.NOTIFICATION_TYPE_ALERT)) {
                i.setAction(MetaData.INTENT_ACTIONS.ALERT_DELETE_ACTION);
            } else {
                i.setAction(MetaData.INTENT_ACTIONS.NOTIFICATION_DELETE_ACTION);
            }
            LocalBroadcastManager.getInstance(NotificationDetail.this).sendBroadcast(i);
            this.finish();
        }
    }

}
