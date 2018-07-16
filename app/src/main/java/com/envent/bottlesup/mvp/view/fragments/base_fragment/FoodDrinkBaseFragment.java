package com.envent.bottlesup.mvp.view.fragments.base_fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.MyPagerAdapter;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventData;
import com.envent.bottlesup.mvp.model.server_response.food_drink_response.FoodDrinkTab;
import com.envent.bottlesup.mvp.view.activities.BannerDetailInfo;
import com.envent.bottlesup.mvp.view.customview.MySliderView;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.SessionManager;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/18/18.
 */

public class FoodDrinkBaseFragment extends LogoutMethodFragment {

    @BindView(R.id.slider_food_drink)
    public SliderLayout menuBannerSlider;
    @BindView(R.id.static_banner_image)
    ImageView staticBannerImage;
    @BindView(R.id.food_drink_tab_layout)
    public TabLayout categoryTabLayout;
    @BindView(R.id.food_drink_view_pager)
    public ViewPager menuViewPager;
    @BindView(R.id.empty_recycler_item_layout)
    public LinearLayout noDataFoundLayout;

    Unbinder unbinder;

    private ProgressDialog progressDialog;
    public SessionManager sessionManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_food_and_drink, container, false);
        unbinder = ButterKnife.bind(this, root);
        initializeProgress();
        return root;
    }

    public User getUser() {
        return User.getUser();
    }

    public void initializeProgress() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage(MetaData.MESSAGE.PLEASE_WAIT);
        sessionManager = new SessionManager(getContext());
    }

    public ProgressDialog getProgressDialog() {
        return progressDialog;
    }

    public void loadDrinkFoodPager(TabLayout tabLayout, final ViewPager viewPager, List<FoodDrinkTab> tabs, String type) {
        viewPager.setAdapter(new MyPagerAdapter(getChildFragmentManager(), tabs, type));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void loadBanner(SliderLayout sliderLayout, List<EventData> events) {
        if (events == null || events.size() == 0) {
            staticBannerImage.setVisibility(View.VISIBLE);
            sliderLayout.setVisibility(View.GONE);
            return;
        }


        for (int i = 0; i < events.size(); i++) {
            MySliderView textSliderView = new MySliderView(getContext());
            EventData eventData = events.get(i);
            String eventTitle = eventData.getName();
            textSliderView
                    .description(eventTitle)
                    .image(eventData.getBannerImage())
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop);

            //set bundle to the slider items
            Bundle box = new Bundle();
            box.putSerializable(MetaData.KEY.EVENT, eventData);
            textSliderView.bundle(box);

            //invoked when user clicks on item
            textSliderView.setOnSliderClickListener(slider -> {
                Intent detailIntent = new Intent(getContext(), BannerDetailInfo.class);
                detailIntent.putExtras(slider.getBundle());
                getContext().startActivity(detailIntent);

            });

            sliderLayout.addSlider(textSliderView);
            sliderLayout.setCustomAnimation(null);
            sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        }
        sliderLayout.setDuration(4000);
    }

    public CheckedInVenue getCheckedInVenue() {
        return CheckedInVenue.getCheckedInVenue();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void noData(boolean isEmpty) {
        if (isEmpty) {
            categoryTabLayout.setVisibility(View.GONE);
            menuViewPager.setVisibility(View.GONE);
            noDataFoundLayout.setVisibility(View.VISIBLE);
        } else {
            categoryTabLayout.setVisibility(View.VISIBLE);
            noDataFoundLayout.setVisibility(View.GONE);
            menuViewPager.setVisibility(View.VISIBLE);
        }
    }
}
