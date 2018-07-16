package com.envent.bottlesup.mvp.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.envent.bottlesup.adapter.TopupStatusAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.topup_status_response.TopUpStatusItem;
import com.envent.bottlesup.mvp.model.server_response.topup_status_response.TopupStatusResponse;
import com.envent.bottlesup.mvp.presenter.RequestedTopupStatusPresenterImpl;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.RecyclerViewAndEmptyLayoutBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronem on 6/3/18.
 */

public class FragmentRequestedTopupStatus
        extends RecyclerViewAndEmptyLayoutBaseFragment
        implements MVPView.RequestedTopupStatusView {

    private List<TopUpStatusItem> items = new ArrayList<>();
    private RequestedTopupStatusPresenterImpl presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new RequestedTopupStatusPresenterImpl();
        presenter.addView(this);

        items = new ArrayList<>();
        recyclerView.setAdapter(new TopupStatusAdapter(getContext(), items));

        fetchData();

        swipeRefreshLayout.setOnRefreshListener(() -> fetchData());
    }

    private void fetchData() {
        presenter.getTopupStatus(getApiKey());
    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(getContext(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void unAuthorized() {
        logoutNow();
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
    public void onRequestedTopUpStatusResponse(TopupStatusResponse response) {

        this.items.clear();
        if (response.getData().getItems().size() > 0) {
            items.addAll(response.getData().getItems());
        }

        recyclerView.getAdapter().notifyDataSetChanged();
        if (this.items.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
        } else {
            emptyLayout.setVisibility(View.GONE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelCall();
    }
}
