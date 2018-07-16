package com.envent.bottlesup.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.envent.bottlesup.mvp.model.TutorialData;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.FragmentTutorial;

import java.util.List;

/**
 * Created by ronem on 5/6/18.
 */

public class TutorialPagerAdater extends FragmentPagerAdapter {
    private List<TutorialData> tutorials;

    public TutorialPagerAdater(FragmentManager fm, List<TutorialData> tutorials) {
        super(fm);
        this.tutorials = tutorials;
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentTutorial.createNewInstance(tutorials.get(position));
    }

    @Override
    public int getCount() {
        return tutorials.size();
    }
}
