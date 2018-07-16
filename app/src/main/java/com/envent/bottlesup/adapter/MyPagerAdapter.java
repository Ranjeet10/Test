package com.envent.bottlesup.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.envent.bottlesup.mvp.model.server_response.food_drink_response.FoodDrinkTab;
import com.envent.bottlesup.mvp.view.fragments.FragmentDrinkPager;
import com.envent.bottlesup.mvp.view.fragments.FragmentFoodPager;
import com.envent.bottlesup.utils.MetaData;

import java.util.List;

/**
 * Created by ronem on 3/18/18.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<FoodDrinkTab> tabs;
    private String TYPE;

    public MyPagerAdapter(FragmentManager fm, List<FoodDrinkTab> tabs, String type) {
        super(fm);
        this.tabs = tabs;
        TYPE = type;
    }

    @Override
    public Fragment getItem(int position) {
        if (TYPE.equals(MetaData.BOTTOM_TAB.FOOD)) {
            return FragmentFoodPager.createNewInstance(tabs.get(position).getId());
        } else if (TYPE.equals(MetaData.BOTTOM_TAB.DRINK)) {
            return FragmentDrinkPager.createNewInstance(tabs.get(position).getId());
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs.get(position).getName();
    }
}
