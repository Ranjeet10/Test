package com.envent.bottlesup.mvp.view.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.NotificationAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.notification_response.ItemsItem;
import com.envent.bottlesup.mvp.model.server_response.notification_response.NotificationBody;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.presenter.NotificationPresenterImpl;
import com.envent.bottlesup.mvp.view.activities.NavNotification;
import com.envent.bottlesup.mvp.view.activities.NotificationDetail;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.fragments.base_fragment.LogoutMethodFragment;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.RecyclerItemClickListener;
import com.envent.bottlesup.utils.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ronem on 5/21/18.
 */

public class FragmentAlert extends LogoutMethodFragment implements MVPView.NotificationView {

    private Unbinder unbinder;
    private List<ItemsItem> notifications = new ArrayList<>();
    private MyPresenter.NotificationPresenter presenter;
    protected BroadcastReceiver alertDeleteReceiver;
    protected int selectedPosition = 0;


    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.empty_recycler_item_layout)
    LinearLayout emptyLayout;
    @BindView(R.id.error_text_view)
    TextView errorText;
    @BindView(R.id.recycler_swipe_refresh_layout)
    SwipeRefreshLayout recyclerSwipeRefreshLayout;
    @BindView(R.id.recycler_card)
    CardView recyclerCard;


    private void setUpBroadCast() {
        alertDeleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                notifications.remove(selectedPosition);
                recyclerView.getAdapter().notifyDataSetChanged();
                toggleEmptyLayout();
            }
        };
        IntentFilter filter = new IntentFilter(MetaData.INTENT_ACTIONS.ALERT_DELETE_ACTION);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(alertDeleteReceiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, root);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(UtilityMethods.getDividerDecoration(getContext()));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((NavNotification) getActivity()).instantiateHandler();

        NotificationAdapter notificationAdapter = new NotificationAdapter(getContext(), notifications);
        recyclerView.setAdapter(notificationAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), ((recyclerView1, v, position) -> {
            selectedPosition = position;
            ItemsItem ni = notifications.get(position);
            Intent dIntent = new Intent(getContext(), NotificationDetail.class);
            dIntent.putExtra(MetaData.KEY.NOTIFICATION, ni);
            dIntent.putExtra(MetaData.KEY.NOTIFICATION_TYPE, MetaData.KEY.NOTIFICATION_TYPE_ALERT);
            dIntent.putExtra(MetaData.KEY.TITLE, "Alert Detail");
            startActivity(dIntent);

        })));

        errorText.setText(MetaData.MESSAGE.NO_ITEMS_IN_THIS_SECTION);

        presenter = new NotificationPresenterImpl();
        presenter.addView(this);
        setUpRefreshLayout();
        fetchNotification();

        setUpBroadCast();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.cancelCall();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(alertDeleteReceiver);
    }

    @Override
    public void showSwipeProgress() {
        ((NavNotification) getActivity()).showRefreshLayout(recyclerSwipeRefreshLayout);
    }

    @Override
    public void hideSwipeProgress() {
        ((NavNotification) getActivity()).hideRefreshLayout(recyclerSwipeRefreshLayout);

    }


    @Override
    public void onNotificationResponse(NotificationBody response) {
        if (response == null) return;

        List<ItemsItem> responseItems = response.getData().getItems();
        if (responseItems != null && responseItems.size() > 0) {
            if (this.notifications.size() > 0) {
                this.notifications.clear();
            }
            this.notifications.addAll(responseItems);
            recyclerView.getAdapter().notifyDataSetChanged();
        }
        toggleEmptyLayout();

    }


    @Override
    public void showMessage(String msg) {
        new CustomToast(getContext(), msg, Toast.LENGTH_LONG);
    }

    @Override
    public void unAuthorized() {
        logoutNow();
    }

    private void fetchNotification() {
        presenter.getNotification(User.getUser().getAccess_token());
        hideSwipeProgress();

    }

    private void setUpRefreshLayout() {
        recyclerSwipeRefreshLayout.setOnRefreshListener(() -> fetchNotification());
    }

    public void toggleEmptyLayout() {
        if (notifications.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
            recyclerCard.setVisibility(View.GONE);
        } else {
            emptyLayout.setVisibility(View.GONE);
            recyclerCard.setVisibility(View.VISIBLE);
        }
    }
}
