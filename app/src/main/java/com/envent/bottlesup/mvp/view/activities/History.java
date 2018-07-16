package com.envent.bottlesup.mvp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.MyCommonFragmentPagerAdapter;
import com.envent.bottlesup.mvp.view.activities.base_activity.TabViewPagerBaseActivity;
import com.envent.bottlesup.mvp.view.fragments.FragmentConsumptionHistory;
import com.envent.bottlesup.mvp.view.fragments.FragmentTopupHistory;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;

/**
 * Created by ronem on 6/3/18.
 */

public class History extends TabViewPagerBaseActivity {
    @BindString(R.string.history)
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
        fragments.add(new FragmentConsumptionHistory());
        fragments.add(new FragmentTopupHistory());

        String[] titles = new String[]{"Consumption", "Top Up"};

        viewPager.setAdapter(new MyCommonFragmentPagerAdapter(getSupportFragmentManager(), fragments, titles));
        tabLayout.setupWithViewPager(viewPager);
    }
}
