package com.envent.bottlesup.mvp.view.activities.base_activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.envent.bottlesup.R;

import butterknife.BindView;

/**
 * Created by ronem on 6/3/18.
 */

public abstract class TabViewPagerBaseActivity extends SimpleBaseActivity {

    @BindView(R.id.tab_layout)
    protected TabLayout tabLayout;
    @BindView(R.id.view_pager)
    protected ViewPager viewPager;

}
