package com.envent.bottlesup.mvp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.NotificationPagerAdapter;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by ronem on 3/16/18.
 */

public class NavNotification extends SimpleBaseActivity {

    private String TAG = getClass().getSimpleName();

    @BindString(R.string.notification)
    String toolbarTitle;
    @BindView(R.id.tab_layout)
    TabLayout notificationTabLayout;
    @BindView(R.id.view_pager)
    ViewPager pager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_layout);

        pager.setAdapter(new NotificationPagerAdapter(getSupportFragmentManager()));
        notificationTabLayout.setupWithViewPager(pager);

    }


    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(toolbarTitle);
    }

    @Override
    protected void updateBalance() {

    }
}
