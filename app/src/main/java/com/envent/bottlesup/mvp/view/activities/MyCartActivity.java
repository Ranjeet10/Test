package com.envent.bottlesup.mvp.view.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.adapter.CartItemAdapter;
import com.envent.bottlesup.adapter.SpinnerSeatingPlaceAdapter;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.TableBar;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.model.server_response.order_request_response.OrderRequestResponse;
import com.envent.bottlesup.mvp.model.server_response.seating_places.SeatingPlace;
import com.envent.bottlesup.mvp.model.server_response.seating_places.SeatingPlaceResponse;
import com.envent.bottlesup.mvp.presenter.MyCartPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.base_activity.MyCartBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.services.ServiceBalanceUpdate;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.RecyclerItemClickListener;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/21/18.
 */

public class MyCartActivity extends MyCartBaseActivity
        implements MVPView.MyCartView, CartItemAdapter.LongPressListener,
        AdapterView.OnItemSelectedListener {

    private String TAG = getClass().getSimpleName();

    @BindView(R.id.btn_my_cart)
    FrameLayout btnMyCart;
    @BindView(R.id.restaurant_cafe_name)
    AppCompatTextView restaurantCafeName;
    @BindView(R.id.restaurant_cafe_address)
    TextView restaurantCafeAddress;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.btn_add_more_foods)
    LinearLayout btnAddMoreFoods;
    @BindView(R.id.btn_add_more_beverage)
    LinearLayout btnAddMoreBeverage;
    @BindView(R.id.sub_total_text_view)
    AppCompatTextView subTotalTextView;
    @BindView(R.id.service_charge_text_view)
    AppCompatTextView serviceChargeTextView;
    @BindView(R.id.vat_text_view)
    AppCompatTextView vatTextView;
    @BindView(R.id.grand_total_text_view)
    AppCompatTextView grandTotalTextView;
    @BindView(R.id.delete_all_cart_items)
    AppCompatImageView deleteAllCartItems;
    @BindView(R.id.btn_proceed)
    Button btnProceed;
    @BindView(R.id.empty_layout)
    LinearLayout emptyLayout;
    @BindView(R.id.sub_total_layout_parent)
    CardView subTotalLayoutParent;
    @BindView(R.id.you_saved_layout_parent)
    RelativeLayout youSavedLayoutParent;
    @BindView(R.id.you_saved_price_text_view)
    AppCompatTextView youSavedPriceTextView;
    @BindView(R.id.add_credit_layout)
    LinearLayout addCreditLayout;

    @BindView(R.id.order_type_layout_container)
    CardView orderTypeLayoutContainer;
    @BindView(R.id.order_type_radio_group)
    RadioGroup orderTypeRadioGroup;
    @BindView(R.id.order_type_rb_on_my_way)
    RadioButton orderTypeRadioOnMyWay;

    @BindView(R.id.seating_place_layout)
    LinearLayout seatingPlaceLayout;
    @BindView(R.id.seating_place_request_order)
    TextView seatingPlaceTextView;
    @BindView(R.id.spinner_seating_place)
    Spinner spinnerSeatingPlace;

    @BindView(R.id.on_my_way_layout)
    LinearLayout onMyWayLayout;
    @BindView(R.id.on_my_way_radio_group)
    RadioGroup onMyWayRadioGroup;
    @BindView(R.id.scroll_view)
    NestedScrollView scrollView;

    private Unbinder unbinder;
    private List<Object> cartItems;
    private MyPresenter.MyCartPresenter presenter;

    private BroadcastReceiver activityCloseReceiver;

    private ProgressDialog progressDialog;
    private List<TableBar> seatingPlaces;
    private TableBar seatingPlace;
    private boolean orderTypeSelected = false;
    private String selectedArrivalTime;
    private double grandTotal = 0;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_cart_activity_layout);
        unbinder = ButterKnife.bind(this);
        sessionManager = new SessionManager(this);
        presenter = new MyCartPresenterImpl();
        presenter.addView(this);

        setUpProgressDialog();

        showNavigationBackAndHideCart();
        setUpData();
        hideMyCart();
        deleteAllCartItems.setVisibility(View.VISIBLE);
        setUpRecyclerView();
        seatingPlaces = TableBar.getSeatingPlaces();

        setUpOrderTypeLayouts();

        presenter.getMyCartItems(false);
    }

    private void registerBroadCast() {

        activityCloseReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                MyCartActivity.this.finish();
            }
        };

        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(activityCloseReceiver,
                        new IntentFilter(MetaData.INTENT_ACTIONS.ACTIVITY_CLOSE_ACTION));
    }

    private void setUpOrderTypeLayouts() {

        spinnerSeatingPlace.setOnItemSelectedListener(this);
        seatingPlaceTextView.setVisibility(View.VISIBLE);
        orderTypeRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = (RadioButton) findViewById(checkedId);
            String value = (String) rb.getText();
            orderTypeSelected = true;
            if (value.equalsIgnoreCase(getString(R.string.at_venue))) {
                seatingPlaceLayout.setVisibility(View.VISIBLE);
                onMyWayLayout.setVisibility(View.GONE);

                if (seatingPlaces != null && seatingPlaces.size() > 0) {
                    updateSeatingPlaceSpinner();
                    spinnerSeatingPlace.setSelection(sessionManager.getSelectedtableBarId());
                } else {
                    presenter.getSeatingPlace(checkedInVenue.getVenueId(), getUser().getAccess_token());
                }
            } else if (value.equalsIgnoreCase(getString(R.string.on_my_way))) {
                seatingPlaceLayout.setVisibility(View.GONE);
                onMyWayLayout.setVisibility(View.VISIBLE);
            }
        });

        onMyWayRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = (RadioButton) findViewById(checkedId);
            selectedArrivalTime = (String) rb.getText();
            seatingPlace = null;
        });


    }

    private void setUpProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(MetaData.MESSAGE.PLEASE_WAIT);
        progressDialog.setCancelable(false);
    }

    private void setUpRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(UtilityMethods.getDividerDecoration(this));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, (recyclerView1, view, position) -> {

        }));
        recyclerView.setNestedScrollingEnabled(false);
    }

    private void setUpData() {
        setUserAsCheckedIn(checkedInVenue.getVenueName());
        restaurantCafeName.setText(checkedInVenue.getVenueName());
        restaurantCafeAddress.setText(checkedInVenue.getVenueAddress());
    }

    @Override
    protected void updateBalance() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadCast();

    }

    @OnClick(R.id.btn_add_more_foods)
    public void onBtnAddMoreFoodsClicked() {
        Dashboard.selectionType = Dashboard.food;
        sendCloseBroadCast();
    }

    private void sendCloseBroadCast() {
        Intent i = new Intent();
        i.setAction(MetaData.INTENT_ACTIONS.ACTIVITY_CLOSE_ACTION);
        LocalBroadcastManager
                .getInstance(this)
                .sendBroadcast(i);
    }

    @OnClick(R.id.btn_add_more_beverage)
    public void onBtnAddMoreBeverageClicked() {
        Dashboard.selectionType = Dashboard.drink;
        sendCloseBroadCast();
    }

    @OnClick(R.id.btn_proceed)
    public void onBtnProceedClicked() {

        if (!presenter.isBalanceSufficient(grandTotal)) {
            AlertDialog.Builder builder = new MyDialog(this).getDialogBuilder(MetaData.MESSAGE.INSUFFICIENT_BALANCE);
            builder.setCancelable(true);
            builder.setPositiveButton("Top Up", (dialog, which) -> {
                dialog.dismiss();
                new Handler().postDelayed(() -> addCreditLayout.performClick(), 500);

            });
            builder.setNegativeButton("Edit Order", (dialog, which) -> {
                dialog.dismiss();
                scrollView.fullScroll(View.FOCUS_UP);
                scrollView.scrollTo(0, 0);
            });
            builder.create().show();
            return;
        }
        if (!orderTypeSelected) {
            showToast("Please select the order type");
            return;
        }

        if (onMyWayLayout.getVisibility() == View.VISIBLE) {
            if (TextUtils.isEmpty(selectedArrivalTime)) {
                showToast("Please select the arrival time");
            } else {
                Proceed();
            }
        } else {
            if (seatingPlace == null) {
                showToast(MetaData.MESSAGE.MUST_SELECT_SEATING_PLACE);
            } else {
                Proceed();
            }
        }
    }

    private void Proceed() {

        if (seatingPlace == null) {
            presenter.requestOrder(cartItems, grandTotal, 0, selectedArrivalTime, getUser().getAccess_token());
        } else if (TextUtils.isEmpty(selectedArrivalTime)) {
            presenter.requestOrder(cartItems, grandTotal, seatingPlace.getSeatingPlaceId(), "", getUser().getAccess_token());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(activityCloseReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateBalanceReceiver);
    }

    @Override
    public void showProgressDialog() {
        if (!progressDialog.isShowing()) progressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void showMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void unAuthorized() {
        logoutNow();
    }

    @Override
    public void onMyCartItemsReceived(List<Object> items, boolean isForOnlyBalanceUpdate) {
        cartItems = items;

        /**
         * If the boolean flag is only for isForOnlyBalanceUpdate then
         * we no need to deal with the recycler view
         * just update the subtotal only
         */

        //
        if (!isForOnlyBalanceUpdate) {
            if (cartItems.size() > 0) {
                emptyLayout.setVisibility(View.GONE);
                CartItemAdapter adapter = new CartItemAdapter(cartItems, this);
                adapter.setListener(this);
                recyclerView.setAdapter(adapter);
            } else {
                cartEmptyView();
            }
        }

        //update total
        presenter.getSubTotal(cartItems);

    }

    @Override
    public void onSubTotalReceived(double subTotal, double serviceCharge, double vatAmount, double total, double saving) {
        this.grandTotal = total;
        subTotalTextView.setText(getResources().getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(subTotal)));
        serviceChargeTextView.setText(getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(serviceCharge)));
        vatTextView.setText(getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(vatAmount)));
        grandTotalTextView.setText(getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(grandTotal)));
        youSavedPriceTextView.setText(getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(saving)));

        updateBalance(grandTotal);
    }

    @Override
    public void onSeatingPlaceResponseReceived(SeatingPlaceResponse response) {
        for (SeatingPlace sp : response.getData()) {
            new TableBar(sp.getId(), sp.getName()).save();
        }
        seatingPlaces = TableBar.getSeatingPlaces();
        updateSeatingPlaceSpinner();
    }

    private void updateSeatingPlaceSpinner() {
        spinnerSeatingPlace.setAdapter(new SpinnerSeatingPlaceAdapter(this, seatingPlaces));
    }


    @Override
    public void onOrderResponseReceived(OrderRequestResponse res) {
        if (res.getStatus().equals(MetaData.MESSAGE.SUCCESS)) {
            clearAllItems();
            User newUser = User.getUser();
            newUser.setBalance(res.getBalance());
            newUser.save();

            updateBalance(0);

            //need to do this because if we are from myWallet fragment
            //to update the balance
            startService(new Intent(MyCartActivity.this, ServiceBalanceUpdate.class));

            AlertDialog.Builder builder = new MyDialog(this).getDialogBuilder(res.getMessage());
            builder
                    .setPositiveButton("Okay", (dialog, which) -> {
                        dialog.dismiss();
                        btnAddMoreFoods.performClick();
                    })
                    .setNegativeButton("View detail", ((dialog, which) -> {
                        dialog.dismiss();
                        Intent detailIntent = new Intent(MyCartActivity.this, ConsumptionHistoryDetail.class);
                        int orderId = res.getOrderId();
                        detailIntent.putExtra(MetaData.KEY.ORDER_ID, orderId);
                        detailIntent.putExtra(MetaData.KEY.TITLE, "Order Detail");
                        startActivity(detailIntent);
                    })).create().show();

        }
    }

    private void cartEmptyView() {
        emptyLayout.setVisibility(View.VISIBLE);
        subTotalLayoutParent.setVisibility(View.GONE);
        youSavedLayoutParent.setVisibility(View.GONE);
        btnProceed.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);
        orderTypeLayoutContainer.setVisibility(View.GONE);
    }

    @OnClick(R.id.delete_all_cart_items)
    public void onDeleteClicked() {
        if (cartItems.size() < 1) {
            CustomToast.makeText(this, MetaData.MESSAGE.NO_ITEM_TO_DELETE, Toast.LENGTH_LONG).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage(MetaData.MESSAGE.ALL_ITEM_WILL_BE_DELETED);
        builder.setPositiveButton("Remove All", (dialog, which) -> {
            clearAllItems();
            dialog.dismiss();
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        builder.create().show();

    }

    @OnClick(R.id.add_credit_layout)
    public void onAddCreditLayoutClicked() {
        Dashboard.selectionType = Dashboard.wallet;
        sendCloseBroadCast();
    }

    private void clearAllItems() {
        cartItems.clear();
        FoodCart.deleteFoodsFromCart();
        DrinkCart.deleteDrinksFromCart();
        updateCartCount();
        updateBalance(0);
        cartEmptyView();
    }

    @Override
    public void onLongPressed(int position) {

    }

    @Override
    public void updateAmount() {
        presenter.getMyCartItems(true);

    }

    @Override
    public void onDeleteClicked(int position) {
        cartItems.remove(position);
        recyclerView.getAdapter().notifyItemRemoved(position);
        recyclerView.getAdapter().notifyItemRangeChanged(position, cartItems.size() - 1);
        updateCartCount();
        presenter.getMyCartItems(false);
        if (cartItems.size() < 1) {
            clearAllItems();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        seatingPlace = seatingPlaces.get(position);
        selectedArrivalTime = "";
        sessionManager.setSelectedTableBarId(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
