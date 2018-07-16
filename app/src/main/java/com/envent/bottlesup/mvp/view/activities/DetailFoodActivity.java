package com.envent.bottlesup.mvp.view.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.model.server_response.fooditems.FoodItems;
import com.envent.bottlesup.mvp.view.activities.base_activity.MyCartBaseActivity;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ronem on 3/19/18.
 */

public class DetailFoodActivity extends MyCartBaseActivity {

    @BindView(R.id.food_drink_name_text_view)
    TextView foodDrinkNameTextView;
    @BindView(R.id.food_drink_menu_price_text_view)
    TextView foodDrinkMenuPriceTextView;
    @BindView(R.id.food_drink_bottles_up_price_text_view)
    TextView foodDrinkBottlesUpPriceTextView;
    @BindView(R.id.food_drink_total_saving_text_view)
    TextView foodDrinkTotalSavingTextView;
    @BindView(R.id.btn_minus)
    TextView btnMinus;
    @BindView(R.id.edt_quantity)
    TextView edtQuantity;
    @BindView(R.id.btn_plus)
    TextView btnPlus;
    @BindView(R.id.add_to_cart_btn)
    RelativeLayout addToCartBtn;
    @BindView(R.id.tax_inclusive_exclusive_view)
    TextView taxInclusiveExclusiveView;

    @BindView(R.id.edt_remark)
    EditText edtRemark;

    private FoodItems food;
    private int categoryId;
    private int quantity = 1;
    private BroadcastReceiver activityCloseReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_detail_layout);

        edtQuantity.setText("1");
        showNavigationBack();
        getIntentData();

        CheckedInVenue checkedInVenue = CheckedInVenue.getCheckedInVenue();
        setUserAsCheckedIn(checkedInVenue.getVenueName());
        setUpReceiver();

        setUpRemarkEditText();
    }

    private void setUpRemarkEditText() {
        edtRemark.setImeOptions(EditorInfo.IME_ACTION_DONE);
        edtRemark.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }

    private void setUpReceiver() {
        activityCloseReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.i("CLOSING", "Detail food");
                DetailFoodActivity.this.finish();
            }
        };
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(activityCloseReceiver,
                        new IntentFilter(MetaData.INTENT_ACTIONS.ACTIVITY_CLOSE_ACTION));
    }

    private void getIntentData() {
        food = getIntent().getParcelableExtra(MetaData.KEY.ITEM);
        categoryId = getIntent().getIntExtra(MetaData.KEY.CATEGORY_ID, 0);
        String name = food.getItemName();
        foodDrinkNameTextView.setText(name);
        updatePrice();
    }


    @OnClick(R.id.btn_minus)
    public void onMinusClicked() {
        String q = edtQuantity.getText().toString().trim();

        quantity = UtilityMethods.decreaseQuantity(q);
        edtQuantity.setText(String.valueOf(quantity));
        updatePrice();
    }

    private void updatePrice() {
        double menuPrice = quantity * food.getMenuPrice();
        double bottlesUpPrice = quantity * food.getBottlesUpToCustomerPrice();
        double saving = menuPrice - bottlesUpPrice;
        foodDrinkMenuPriceTextView.setText(getResources().getString(R.string.menu_price, UtilityMethods.getTwoPlaceDecimal(menuPrice)));
        foodDrinkBottlesUpPriceTextView.setText(getResources().getString(R.string.bottles_up_price, UtilityMethods.getTwoPlaceDecimal(bottlesUpPrice)));
        foodDrinkTotalSavingTextView.setText(getResources().getString(R.string.total_saving, UtilityMethods.getTwoPlaceDecimal(saving)));
        if (checkedInVenue.isTaxInclusive()) {
            taxInclusiveExclusiveView.setText(MetaData.MESSAGE.TAX_INCLUSIVE);
        } else {
            taxInclusiveExclusiveView.setText(MetaData.MESSAGE.TAX_EXCLUSIVE);
        }
    }

    @OnClick(R.id.btn_plus)
    public void onPlusClicked() {
        String q = edtQuantity.getText().toString().trim();

        quantity = UtilityMethods.increaseQuantity(q);
        edtQuantity.setText(String.valueOf(quantity));
        updatePrice();
    }

    @OnClick(R.id.cancel_btn)
    public void onCancelClicked() {
        this.finish();
    }

    @OnClick(R.id.add_to_cart_btn)
    public void onAddToCartClicked() {
        String remarks = edtRemark.getText().toString();
        FoodCart fc = FoodCart.getFoodCart(categoryId, food.getId(), remarks);
        if (fc != null && fc.getRemark().equalsIgnoreCase(remarks)) {
            quantity = quantity + fc.getQuantity();
        } else {
            fc = new FoodCart();
            fc.setCategoryId(categoryId);
            fc.setItemId(food.getId());
            fc.setName(food.getItemName());
            fc.setMenuPrice(food.getMenuPrice());
            fc.setBottlesUpPrice(food.getBottlesUpToCustomerPrice());
            //testing
            fc.setBottlesUpInclusivePrice(food.getBottlesUpToCustomerPrice());
            fc.setRemark(remarks);
        }
        fc.setQuantity(quantity);
        fc.save();
        itemAdded();
        UtilityMethods.hideKeyboard(this,addToCartBtn);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.isFinishing())
            LocalBroadcastManager.getInstance(this).unregisterReceiver(activityCloseReceiver);
    }

    @Override
    protected void updateBalance() {

    }
}
