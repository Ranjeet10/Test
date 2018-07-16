package com.envent.bottlesup.mvp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.envent.bottlesup.adapter.FoodItemAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.fooditems.FoodItemResponse;
import com.envent.bottlesup.mvp.model.server_response.fooditems.FoodItems;
import com.envent.bottlesup.mvp.model.server_response.fooditems.SearchedItemResponse;
import com.envent.bottlesup.mvp.presenter.FoodItemPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.DetailFoodActivity;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.RecyclerViewAndEmptyLayoutBaseFragment;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.RecyclerItemClickListener;

import java.util.List;

/**
 * Created by ronem on 3/18/18.
 */

public class FragmentFoodPager extends RecyclerViewAndEmptyLayoutBaseFragment
        implements RecyclerItemClickListener.OnItemClickListener,
        MVPView.FoodItemView {

    private MyPresenter.FoodItemPresenter presenter;
    private List<FoodItems> foods;
    private static final String KEY_FOOD_ID = "food_id";
    private int foodCategoryId;

    public static FragmentFoodPager createNewInstance(int foodId) {
        Bundle box = new Bundle();
        box.putInt(KEY_FOOD_ID, foodId);
        FragmentFoodPager fp = new FragmentFoodPager();
        fp.setArguments(box);
        return fp;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setOnRecyclerViewItemTouchListener();
        foodCategoryId = getArguments().getInt(KEY_FOOD_ID);
        presenter = new FoodItemPresenterImpl();
        presenter.addView(this);
        presenter.getFoodItems(checkedInVenue.getVenueId(), foodCategoryId, getApiKey());

        setUpPullToRefresh();
    }

    private void setUpPullToRefresh() {
        swipeRefreshLayout.setOnRefreshListener(() ->
                presenter.getFoodItems(checkedInVenue.getVenueId(), foodCategoryId, getApiKey()));
    }

    private void setOnRecyclerViewItemTouchListener() {
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (recyclerView1, view, position) -> {
            Intent detailIntent = new Intent(getContext(), DetailFoodActivity.class);
            detailIntent.putExtra(MetaData.KEY.ITEM, foods.get(position));
            detailIntent.putExtra(MetaData.KEY.CATEGORY_ID, foodCategoryId);

            startActivity(detailIntent);
        }));
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View view, int position) {

    }

    @Override
    public void showSwipeProgress() {
        showRefreshLayoutRefresing();
    }

    @Override
    public void hideSwipeProgress() {
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
    public void onFoodItemsResponse(FoodItemResponse response) {
        if (response.getFoodItemData() != null && response.getFoodItemData().getItems().size() > 0) {
            this.foods = response.getFoodItemData().getItems();
            recyclerView.setAdapter(new FoodItemAdapter(foods, getContext()));
            emptyLayout.setVisibility(View.INVISIBLE);
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
            Log.i("food","empty");
        }
    }

    @Override
    public void onSearchedFoodItemsResponse(SearchedItemResponse foodItemResponse) {

    }
}