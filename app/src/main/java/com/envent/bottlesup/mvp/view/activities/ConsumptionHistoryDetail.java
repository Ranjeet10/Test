package com.envent.bottlesup.mvp.view.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.ConsumptionHistoryDetailItemAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.CancelOrderResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.ConsumptionHistoryDetailResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.Data;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.ItemsItem;
import com.envent.bottlesup.mvp.presenter.ConsumptionHistoryDetailPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ronem on 5/8/18.
 */

public class ConsumptionHistoryDetail extends SimpleBaseActivity implements MVPView.ConsumptionHistoryDetailView {
    @BindString(R.string.consumption_detail)
    String toolbarTitle;

    int orderId;
    String orderType;

    @BindView(R.id.navigation_back)
    AppCompatImageView navigationBack;
    @BindView(R.id.personname)
    TextView personname;
    @BindView(R.id.tablenumber)
    TextView tablenumber;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.subtotal)
    TextView subtotal;
    @BindView(R.id.servicecharge)
    TextView servicecharge;
    @BindView(R.id.vatamount)
    TextView vatamount;
    @BindView(R.id.grandtotal)
    TextView grandtotal;
    @BindView(R.id.detailprogressbar)
    ProgressBar detailprogressbar;
    @BindView(R.id.cancel_pending_order)
    Button cancelPendingOrder;
    private MyPresenter.ConsumptionHistoryDetailPresenter presenter;

    private List<ItemsItem> orders = new ArrayList<>();

    @Override
    protected void updateBalance() {

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consumption_history_detail);
        ButterKnife.bind(this);

        orderId = getIntent().getIntExtra(MetaData.KEY.ORDER_ID, -1);
        orderType = getIntent().getStringExtra(MetaData.KEY.ORDER_TYPE);
        String title = getIntent().getStringExtra(MetaData.KEY.TITLE);
        if (title != null) {
            getToolbarTitleTextView().setText(title);
        } else {
            getToolbarTitleTextView().setText(toolbarTitle);
        }

        if (orderType != null) {
            Log.d("order type", orderType);
            if (orderType.equalsIgnoreCase(MetaData.KEY.ORDER_TYPE_PENDING)) {
                cancelPendingOrder.setVisibility(View.VISIBLE);
            }else {
                cancelPendingOrder.setVisibility(View.GONE);
            }
        }

        presenter = new ConsumptionHistoryDetailPresenterImpl();
        presenter.addView(this);

        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        recyclerview.setHasFixedSize(true);
        recyclerview.setNestedScrollingEnabled(false);
        recyclerview.addItemDecoration(UtilityMethods.getDividerDecoration(this));
        recyclerview.setAdapter(new ConsumptionHistoryDetailItemAdapter(orders, this));
        presenter.getConsumptionHistoryDetail(getUser().getAccess_token(), orderId);
    }



    @Override
    public void showMessage(String msg) {
        new CustomToast(this, msg, Toast.LENGTH_LONG);
    }

    @Override
    public void unAuthorized() {
        logoutNow();
    }

    @Override
    public void showProgressDialog() {
        showMyProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        cancelProgressDialog();
    }

    @Override
    public void onConsumptionHistoryDetailReceived(ConsumptionHistoryDetailResponse res) {
        orders.clear();
        orders.addAll(res.getData().getItems());
        recyclerview.getAdapter().notifyDataSetChanged();

        Data data = res.getData();

        grandtotal.setText(getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(data.getGrandTotal())));
        subtotal.setText(getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(data.getSubTotal())));
        vatamount.setText(getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(data.getVat())));
        date.setText(data.getOrderedOn());
        personname.setText(data.getCustomer().getName());
        tablenumber.setText(data.getVenue().getName());
        servicecharge.setText(getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(data.getServiceCharge())));
    }

    @Override
    public void onCancelOrderResponseReceived(CancelOrderResponse res) {
        Log.d("response received", res.toString());
        orders.clear();

        if(res.getStatus().equalsIgnoreCase(MetaData.MESSAGE.SUCCESS)) {
            Intent detailIntent = new Intent(this, RequestStatusActivity.class);
            startActivity(detailIntent);
        }

    }

    @OnClick(R.id.btn_print_layout)
    public void onBtnPrintLayoutClicked() {
        showMessage("Coming soon");
    }

    @OnClick(R.id.btn_share_layout)
    public void onBtnShareLayoutClicked() {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Consumed History");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "body");
        startActivity(Intent.createChooser(sharingIntent, "Share consumption history via"));
    }

    @OnClick(R.id.cancel_pending_order)
    public void onCancelPendingOrderBtnClicked() {

        AlertDialog.Builder builder = new MyDialog(this).getDialogBuilder("Are you sure you want to cancel the order?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (dialog, which) -> {
            presenter.deletePendingOrder(User.getUser().getAccess_token(), orderId);
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.cancelCall();
    }
}
