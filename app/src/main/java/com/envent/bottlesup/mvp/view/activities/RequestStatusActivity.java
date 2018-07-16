package com.envent.bottlesup.mvp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.MyCommonFragmentPagerAdapter;
import com.envent.bottlesup.mvp.view.activities.base_activity.TabViewPagerBaseActivity;
import com.envent.bottlesup.mvp.view.fragments.FragmentRequestedOrderStatus;
import com.envent.bottlesup.mvp.view.fragments.FragmentRequestedTopupStatus;
import com.envent.bottlesup.utils.MetaData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;

/**
 * Created by ronem on 6/3/18.
 */

public class RequestStatusActivity extends TabViewPagerBaseActivity {
    @BindString(R.string.status)
    String title;

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(title);
    }

    @Override
    protected void updateBalance() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new FragmentRequestedOrderStatus());
        fragments.add(new FragmentRequestedTopupStatus());

        String[] titles = new String[]{"Order", "Top Up"};

        viewPager.setAdapter(new MyCommonFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);

        if (getIntent().getExtras() != null && getIntent().getStringExtra(MetaData.KEY.NOTIFICATION_TYPE).equalsIgnoreCase(MetaData.KEY.NOTIFICATION_TYPE_TOPUP)) {
            //set to top-up page
            viewPager.setCurrentItem(1);
        }

    }
}
