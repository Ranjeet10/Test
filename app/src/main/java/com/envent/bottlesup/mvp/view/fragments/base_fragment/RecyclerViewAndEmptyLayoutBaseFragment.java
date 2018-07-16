package com.envent.bottlesup.mvp.view.fragments.base_fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/19/18.
 */

public class RecyclerViewAndEmptyLayoutBaseFragment extends LogoutMethodFragment {

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.empty_recycler_item_layout)
    public LinearLayout emptyLayout;
    @BindView(R.id.error_text_view)
    public TextView errorTextView;

    Unbinder unbinder;
    private Handler refreshHandler;
    private Runnable refreshRunnable;
    public CheckedInVenue checkedInVenue;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recycler_view_empty_swipe_refresh_layout, container, false);
        unbinder = ButterKnife.bind(this, root);
        user = User.getUser();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(false);
        recyclerView.addItemDecoration(UtilityMethods.getDividerDecoration(getContext()));

        refreshHandler = new Handler();
        checkedInVenue = CheckedInVenue.getCheckedInVenue();

        errorTextView.setText(MetaData.MESSAGE.NO_ITEMS_IN_THIS_SECTION);
        return root;
    }

    public String getApiKey() {
        return user.getAccess_token();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        clearRefreshResources();
    }

    private void clearRefreshResources() {
        if (refreshRunnable != null) {
            refreshHandler.removeCallbacks(refreshRunnable);
        }
    }

    public void showRefreshLayoutRefresing() {
        if (swipeRefreshLayout == null) return;
        if (!swipeRefreshLayout.isRefreshing()) {
            clearRefreshResources();

            refreshRunnable = () -> swipeRefreshLayout.setRefreshing(true);
            refreshHandler.postDelayed(refreshRunnable, 100);
        }
    }

    public void hideRefreshLayoutRefreshing() {
        clearRefreshResources();
        if (swipeRefreshLayout == null) return;
        refreshRunnable = () -> swipeRefreshLayout.setRefreshing(false);

        refreshHandler.postDelayed(refreshRunnable, 100);
    }
}
