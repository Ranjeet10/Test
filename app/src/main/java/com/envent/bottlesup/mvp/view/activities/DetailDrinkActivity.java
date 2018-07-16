package com.envent.bottlesup.mvp.view.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.DrinkItemsItem;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.MixersItem;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.ServingTypesItem;
import com.envent.bottlesup.mvp.presenter.DetailDrinkPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.base_activity.MyCartBaseActivity;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by ronem on 3/19/18.
 */

public class DetailDrinkActivity extends MyCartBaseActivity implements MVPView.DetailDrinkView {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.food_drink_name_text_view)
    TextView drinkNameTextView;
    @BindView(R.id.food_drink_menu_price_text_view)
    TextView drinkMenuPriceTextView;
    @BindView(R.id.drink_icon)
    ImageView drinkImage;
    @BindView(R.id.food_drink_bottles_up_price_text_view)
    TextView drinkBottlesUpPriceTextView;
    @BindView(R.id.food_drink_total_saving_text_view)
    TextView drinkTotalSavingTextView;
    @BindView(R.id.drink_option_radio_group)
    RadioGroup drinkOptionRadioGroup;
    @BindView(R.id.mixtures_radio_group)
    RadioGroup mixturesRadioGroup;
    @BindView(R.id.btn_minus)
    TextView btnMinus;
    @BindView(R.id.btn_plus)
    TextView btnPlus;
    @BindView(R.id.cancel_btn)
    RelativeLayout cancelBtn;
    @BindView(R.id.add_to_cart_btn)
    RelativeLayout addToCartBtn;
    @BindView(R.id.edt_quantity)
    TextView edtQuantity;
    @BindView(R.id.edt_remark)
    EditText edtRemark;
    @BindView(R.id.tax_inclusive_exclusive_view)
    TextView taxInclusiveExclusiveView;

    private int quantity = 1;
    private int drinkOptionId;
    private int mixtureId;
    private String drinkOptionName;
    private String mixtureName;
    private int categoryId;
    private DrinkItemsItem drink;
    private List<ServingTypesItem> servingTypes;
    private List<MixersItem> drinkMixtures;
    private double menuPrice, bottlesUpPrice;
    private double perUnitMenuPrice, perUnitBottlesUpPrice;
    private MyPresenter.DetailDrinkPresenter presenter;

    private BroadcastReceiver activityCloseReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drink_detail_layout);
        presenter = new DetailDrinkPresenterImpl();
        presenter.addView(this);

        edtQuantity.setText("1");
        showNavigationBack();
        getIntentData();
        setUpRadioButtonListener();

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
                Log.i("CLOSING", "Detail Drink");
                DetailDrinkActivity.this.finish();
            }
        };

        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(activityCloseReceiver,
                        new IntentFilter(MetaData.INTENT_ACTIONS.ACTIVITY_CLOSE_ACTION));
    }

    private void setUpRadioButtonListener() {
        setUpRadioButtons();

        drinkOptionRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            drinkOptionId = (int) rb.getTag();
            drinkOptionName = rb.getText().toString();
            setUpDetail(quantity);

        });

        mixturesRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton rb = findViewById(checkedId);
            mixtureId = (int) rb.getTag();
            mixtureName = rb.getText().toString();
            Log.i(TAG, "Mixture " + mixtureName);
        });

        Log.i(TAG, "Radio button setup complete");

        ((RadioButton) drinkOptionRadioGroup.getChildAt(0)).setChecked(true);

        int lastRadioCount = mixturesRadioGroup.getChildCount();
        ((RadioButton) mixturesRadioGroup.getChildAt(lastRadioCount - 1)).setChecked(true);


    }

    private void setUpDetail(int quantity) {
        ServingTypesItem servingType = getServingType(drinkOptionId);
        perUnitMenuPrice = servingType.getMenuPrice();
        perUnitBottlesUpPrice = servingType.getBottlesUpPrice();

        menuPrice = perUnitMenuPrice * quantity;
        bottlesUpPrice = perUnitBottlesUpPrice * quantity;

        drinkMenuPriceTextView.setText(getResources().getString(R.string.menu_price, UtilityMethods.getTwoPlaceDecimal(menuPrice)));
        drinkBottlesUpPriceTextView.setText(getResources().getString(R.string.bottles_up_price, String.valueOf(UtilityMethods.getTwoPlaceDecimal(bottlesUpPrice))));

        double savePrice = menuPrice - bottlesUpPrice;
        drinkTotalSavingTextView.setText(getResources().getString(R.string.total_saving, UtilityMethods.getTwoPlaceDecimal(savePrice)));
    }

    private ServingTypesItem getServingType(int drinkOptionId) {

        for (int i = 0; i < servingTypes.size(); i++) {
            if (drinkOptionId == servingTypes.get(i).getId()) {
                return servingTypes.get(i);
            }
        }

        return null;
    }

    private void getIntentData() {
        Bundle box = getIntent().getExtras();
        drink = (DrinkItemsItem) box.getSerializable(MetaData.KEY.ITEM);
        categoryId = box.getInt(MetaData.KEY.CATEGORY_ID);
        drinkNameTextView.setText(drink.getItemName());

        String imagePath = drink.getItemImage();
        if (!TextUtils.isEmpty(imagePath)) {
            Picasso.with(this)
                    .load(drink.getItemImage())
                    .placeholder(R.mipmap.drink)
                    .into(drinkImage);
        } else {
            drinkImage.setImageResource(R.mipmap.drink);
        }

        if (checkedInVenue.isTaxInclusive()) {
            taxInclusiveExclusiveView.setText(MetaData.MESSAGE.TAX_INCLUSIVE);
        } else {
            taxInclusiveExclusiveView.setText(MetaData.MESSAGE.TAX_EXCLUSIVE);
        }

    }

    private void setUpRadioButtons() {

        servingTypes = drink.getServingTypes();
        drinkMixtures = drink.getMixers();

        MixersItem mi = new MixersItem();
        mi.setId(-1);
        mi.setName("None");
        drinkMixtures.add(mi);

        drinkOptionRadioGroup.removeAllViews();
        mixturesRadioGroup.removeAllViews();

        presenter.attachDrinkOptionRadioButton(this, servingTypes);
        presenter.attachMixtureRadioButton(this, drinkMixtures);
    }

    @OnClick(R.id.btn_minus)
    public void onMinusClicked() {
        String q = edtQuantity.getText().toString().trim();
        quantity = UtilityMethods.decreaseQuantity(q);
        edtQuantity.setText(String.valueOf(quantity));
        setUpDetail(quantity);
    }

    @OnClick(R.id.btn_plus)
    public void onPlusClicked() {
        String q = edtQuantity.getText().toString().trim();
        quantity = UtilityMethods.increaseQuantity(q);
        edtQuantity.setText(String.valueOf(quantity));
        setUpDetail(quantity);
    }

    @OnClick(R.id.cancel_btn)
    public void onCancelClicked() {
        this.finish();
    }

    @OnClick(R.id.add_to_cart_btn)
    public void onAddToCartClicked() {

        String remarks = edtRemark.getText().toString();
        if (TextUtils.isEmpty(drinkOptionName)) {
            showToast(MetaData.MESSAGE.EMPTY_DRINK_OPTION);
        } else if (TextUtils.isEmpty(mixtureName)) {
            showToast(MetaData.MESSAGE.EMPTY_MIXTURE);
        } else {
            DrinkCart dc = DrinkCart.getDrinkCart(
                    categoryId,
                    drink.getId(),
                    drinkOptionId,
                    mixtureId, remarks);

            if (dc != null && dc.getRemark().equalsIgnoreCase(remarks)) {
                quantity = quantity + dc.getQuantity();
            } else {
                dc = new DrinkCart();
                dc.setCategoryId(categoryId);
                dc.setItemId(drink.getId());
                dc.setName(drink.getItemName());
                dc.setMenuPrice(perUnitMenuPrice);
                dc.setBottlesUpPrice(perUnitBottlesUpPrice);
                dc.setBottlesUpInclusivePrice(perUnitBottlesUpPrice);
                dc.setDrinkOptionId(drinkOptionId);
                dc.setDrinkOption(drinkOptionName);
                dc.setMixtureId(mixtureId);
                dc.setMixture(mixtureName);
                dc.setRemark(edtRemark.getText().toString());
            }
            dc.setQuantity(quantity);
            dc.save();
            itemAdded();
            UtilityMethods.hideKeyboard(this,addToCartBtn);
        }

    }


    @Override
    public void onDrinkOptionRadioButtonCreated(RadioButton rb) {
        drinkOptionRadioGroup.addView(rb);
    }

    @Override
    public void onDrinkMixtureRadioButtonCreated(RadioButton rb) {
        mixturesRadioGroup.addView(rb);
    }

    @Override
    public void onInflatedAllServingTypes() {
        Log.i(TAG, "selected serving types");
//        ((RadioButton) drinkOptionRadioGroup.getChildAt(0)).setSelected(true);
    }

    @Override
    public void onInflatedAllMixtures() {
        Log.i(TAG, "selected mixture types");
//        ((RadioButton) mixturesRadioGroup.getChildAt(0)).setSelected(true);
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
