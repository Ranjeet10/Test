package com.envent.bottlesup.mvp.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventData;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.mvp.model.server_response.food_drink_response.FoodDrinkTabResponse;
import com.envent.bottlesup.mvp.presenter.FoodPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.FoodDrinkBaseFragment;
import com.envent.bottlesup.utils.MetaData;

import java.util.List;

/**
 * Created by ronem on 3/16/18.
 */

public class FragmentFood extends FoodDrinkBaseFragment implements MVPView.FoodView {

    private String TAG = getClass().getSimpleName();

    private MyPresenter.FoodPresenter presenter;

    public static FragmentFood createNewInstance() {
        return new FragmentFood();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new FoodPresenterImpl();
        presenter.addView(this);
        loadCheckedInVenueBanners();

    }

    private void loadCheckedInVenueBanners() {
        EventResponse er = sessionManager.getVenueEvent();
        if (er != null) {
            List<EventData> events = er.getData();
            loadBanner(menuBannerSlider, events);
            presenter.getFoodTabs(getCheckedInVenue().getVenueId(), getUser().getAccess_token());
        } else {
            presenter.getBanners(getCheckedInVenue().getVenueId(), User.getUser().getAccess_token());
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideProgressDialog();
        presenter.cancelCall();
    }

    @Override
    public void showProgressDialog() {
        if (!getProgressDialog().isShowing()) {
            getProgressDialog().show();
        }
    }

    @Override
    public void hideProgressDialog() {
        if (getProgressDialog().isShowing())
            getProgressDialog().dismiss();
    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(getActivity(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void unAuthorized() {
        logoutNow();
    }

    @Override
    public void onBannerResponse(EventResponse eventResponse) {
        sessionManager.saveVenueEvent(eventResponse);

        List<EventData> events = eventResponse.getData();
        loadBanner(menuBannerSlider, events);
        presenter.getFoodTabs(getCheckedInVenue().getVenueId(), getUser().getAccess_token());

    }

    @Override
    public void onFoodTabsResponse(FoodDrinkTabResponse response) {
        if (response.getData() != null && response.getData().size() > 0) {
            loadDrinkFoodPager(categoryTabLayout, menuViewPager, response.getData(), MetaData.BOTTOM_TAB.FOOD);
            noData(false);
        } else {
            noData(true);
        }
    }

}

