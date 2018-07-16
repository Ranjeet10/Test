package com.envent.bottlesup.mvp.view.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.envent.bottlesup.adapter.TopUpHistoryAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.topup_response.TopupHistoryDataItem;
import com.envent.bottlesup.mvp.model.server_response.topup_response.TopupHistoryResponse;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.TopupHistoryPresenterImpl;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.RecyclerViewAndEmptyLayoutBaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronem on 6/3/18.
 */

public class FragmentTopupHistory
        extends RecyclerViewAndEmptyLayoutBaseFragment
        implements MVPView.TopUpHistoryView {

    private List<TopupHistoryDataItem> items;
    private MyPresenter.TopUpHistoryPresenter presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new TopupHistoryPresenterImpl();
        presenter.addView(this);

        items = new ArrayList<>();
        recyclerView.setAdapter(new TopUpHistoryAdapter(getContext(), items));

        fetchData();

        swipeRefreshLayout.setOnRefreshListener(() -> fetchData());

    }

    private void fetchData() {
        User u = User.getUser();
        presenter.getTopUpHistory(1, 50, u.getAccess_token());
    }


    @Override
    public void showMessage(String msg) {
        CustomToast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
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
    public void onTopUpHistoryReceived(TopupHistoryResponse response) {
        emptyLayout.setVisibility(View.GONE);

        if (response.getData().getItems().size() > 0) {
            this.items.clear();
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
