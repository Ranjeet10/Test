package com.envent.bottlesup.mvp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.envent.bottlesup.adapter.ConsumptionHistoryAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.consumption_history.ConsumptionHistoryItemsItem;
import com.envent.bottlesup.mvp.model.server_response.consumption_history.ConsumptionHistoryResponse;
import com.envent.bottlesup.mvp.presenter.ConsumptionHistoryPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.ConsumptionHistoryDetail;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.RecyclerViewAndEmptyLayoutBaseFragment;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronem on 6/3/18.
 */

public class FragmentConsumptionHistory
        extends RecyclerViewAndEmptyLayoutBaseFragment
        implements MVPView.ConsumptionHistoryView {

    private List<ConsumptionHistoryItemsItem> consumptionHistoryItemsItems;
    private MyPresenter.ConsumptionHistoryPresenter presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new ConsumptionHistoryPresenterImpl();
        presenter.addView(this);

        consumptionHistoryItemsItems = new ArrayList<>();
        recyclerView.setAdapter(new ConsumptionHistoryAdapter(getContext(), consumptionHistoryItemsItems));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (recyclerView, v, position) -> {

            Intent detailIntent = new Intent(getContext(), ConsumptionHistoryDetail.class);
            detailIntent.putExtra(MetaData.KEY.ORDER_ID, consumptionHistoryItemsItems.get(position).getOrderId());
            startActivity(detailIntent);
        }));

        fetchData();

        setUpRefreshLayout();
    }

    private void fetchData() {
        User u = User.getUser();
        presenter.getConsumptionHistory(u.getAccess_token());
    }

    private void setUpRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(() -> fetchData());
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
    public void onConsumptionHistoryReceived(ConsumptionHistoryResponse response) {

        if (response.getConsumptionHistoryData().getItems().size() > 0) {
            consumptionHistoryItemsItems.clear();
            emptyLayout.setVisibility(View.GONE);
            consumptionHistoryItemsItems.addAll(response.getConsumptionHistoryData().getItems());
        } else {
            emptyLayout.setVisibility(View.VISIBLE);
        }
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.cancelCall();
    }
}
