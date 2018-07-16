package com.envent.bottlesup.mvp.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.envent.bottlesup.adapter.OrderStatusAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.order_status_response.OrderStatusItem;
import com.envent.bottlesup.mvp.model.server_response.order_status_response.OrderStatusResponse;
import com.envent.bottlesup.mvp.presenter.RequestedOrderStatusPresenterImpl;
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

public class FragmentRequestedOrderStatus
        extends RecyclerViewAndEmptyLayoutBaseFragment
        implements MVPView.RequestedOrderStatusView {

    private List<OrderStatusItem> items = new ArrayList<>();
    private RequestedOrderStatusPresenterImpl presenter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new RequestedOrderStatusPresenterImpl();
        presenter.addView(this);

        items = new ArrayList<>();
        recyclerView.setAdapter(new OrderStatusAdapter(getContext(), items));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (recyclerView1, view1, position) -> {
            Intent detailIntent = new Intent(getContext(), ConsumptionHistoryDetail.class);
            detailIntent.putExtra(MetaData.KEY.ORDER_ID, items.get(position).getOrderId());
            detailIntent.putExtra(MetaData.KEY.TITLE, "Requested Order Detail");
            detailIntent.putExtra(MetaData.KEY.ORDER_TYPE, items.get(position).getStatus());
            startActivity(detailIntent);
        }));

        fetchData();

        swipeRefreshLayout.setOnRefreshListener(() -> fetchData());
    }

    private void fetchData() {
        presenter.getOrderStatus(getApiKey());
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
    public void onRequestedOrderStatusResponse(OrderStatusResponse response) {
        this.items.clear();

        if (response.getOrderStatusData().getItems().size() > 0) {
            items.addAll(response.getOrderStatusData().getItems());
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
