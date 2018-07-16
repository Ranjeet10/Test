package com.envent.bottlesup.mvp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.envent.bottlesup.adapter.DrinkItemAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.DrinkItemResponse;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.DrinkItemsItem;
import com.envent.bottlesup.mvp.presenter.DrinkItemPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.DetailDrinkActivity;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.RecyclerViewAndEmptyLayoutBaseFragment;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.MyLog;
import com.envent.bottlesup.utils.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by ronem on 3/18/18.
 */

public class FragmentDrinkPager extends RecyclerViewAndEmptyLayoutBaseFragment implements MVPView.DrinkItemView {

    private List<DrinkItemsItem> drinks;
    private static final String KEY_DRINK_ID = "drink_id";
    private int drinkId;

    private MyPresenter.DrinkItemPresenter presenter;

    public static FragmentDrinkPager createNewInstance(int drinkId) {
        Bundle box = new Bundle();
        box.putInt(KEY_DRINK_ID, drinkId);
        FragmentDrinkPager fp = new FragmentDrinkPager();
        fp.setArguments(box);
        return fp;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnRecyclerViewItemTouchListener();
        drinkId = getArguments().getInt(KEY_DRINK_ID);
        presenter = new DrinkItemPresenterImpl();
        presenter.addView(this);
        fetchDrinkItems();
        setUpPullToRefresh();
    }

    private void fetchDrinkItems() {
        presenter.getDrinkItems(checkedInVenue.getVenueId(), drinkId, getApiKey());
    }

    private void setUpPullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() -> fetchDrinkItems());
    }


    private void setOnRecyclerViewItemTouchListener() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (recyclerView1, view, position) -> {
            Intent detailIntent = new Intent(getContext(), DetailDrinkActivity.class);

            Bundle box = new Bundle();
            box.putSerializable(MetaData.KEY.ITEM, drinks.get(position));
            box.putInt(MetaData.KEY.CATEGORY_ID, drinkId);
            detailIntent.putExtras(box);
            startActivity(detailIntent);
        }));
    }


    @Override
    public void showSwipeProgress() {
        MyLog.i(getActivity(), "Refreshing shown");
        showRefreshLayoutRefresing();
    }

    @Override
    public void hideSwipeProgress() {
        MyLog.i(getActivity(), "Refreshing hidden");
        hideRefreshLayoutRefreshing();
    }

    @Override
    public void showMessage(String msg) {

    }

    @Override
    public void unAuthorized() {
        logoutNow();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelCall();
    }

    @Override
    public void onDrinkItemResponse(DrinkItemResponse response) {
        this.drinks = response.getData().getItems();
        recyclerView.setAdapter(new DrinkItemAdapter(this.drinks, getActivity()));

        if (this.drinks != null && this.drinks.size() > 0) {
            emptyLayout.setVisibility(View.INVISIBLE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            Log.i("drink","empty");
        }
    }
}
