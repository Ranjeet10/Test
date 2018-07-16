package com.envent.bottlesup.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.envent.bottlesup.mvp.view.fragments.FragmentAlert;
import com.envent.bottlesup.mvp.view.fragments.FragmentNotification;

/**
 * Created by ronem on 5/21/18.
 */

public class NotificationPagerAdapter extends FragmentPagerAdapter {
    private String[] tabs = new String[]{"Notification", "Alerts"};

    public NotificationPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragmentNotification();
        } else {
            return new FragmentAlert();
        }
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }
}
