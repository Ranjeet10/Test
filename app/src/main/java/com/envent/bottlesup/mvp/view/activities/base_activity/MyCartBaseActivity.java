package com.envent.bottlesup.mvp.view.activities.base_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.view.activities.MyCartActivity;
import com.envent.bottlesup.mvp.view.activities.VenueDetailInfo;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/19/18.
 */

@SuppressLint("Registered")
public abstract class MyCartBaseActivity extends PushNotificationReceiverHandlerActivity {

    @BindView(R.id.drawerIcon)
    AppCompatImageView drawerIcon;
    @BindView(R.id.checked_in_layout)
    LinearLayout checkedInLayout;
    @BindView(R.id.btn_my_cart)
    FrameLayout myCartLayout;
    @BindView(R.id.navigation_back)
    AppCompatImageView navigationBack;
    @BindView(R.id.cartCount)
    TextView cartCount;
    @BindView(R.id.toolbarTitle)
    TextView toolbarTitle;
    @BindView(R.id.toolbarTitleCheckedIn)
    TextView toolbarTitleCheckedIn;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.balance_indicator_layout)
    public RelativeLayout balanceIndicatorLayout;
    @BindView(R.id.available_balance_text_view)
    TextView availableBalanceTextView;

    private Unbinder unbinder;
    public CheckedInVenue checkedInVenue;


    @Override
    public void setContentView(@LayoutRes int layout) {
        super.setContentView(layout);
        unbinder = ButterKnife.bind(this);
        updateCartCount();
        checkedInVenue = CheckedInVenue.getCheckedInVenue();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }

    }

    public User getUser() {
        return User.getUser();
    }

    public void itemAdded() {
        onBackPressed();
        showToast(MetaData.MESSAGE.ITEM_ADDED);
    }

    public void showNavigationBack() {
        drawerIcon.setVisibility(View.GONE);
        navigationBack.setVisibility(View.VISIBLE);

        //code here if the user is checked in or not
            checkedInLayout.setVisibility(View.VISIBLE);
            toolbarTitle.setVisibility(View.GONE);
    }

    public void showNavigationBackAndHideCart() {
        showNavigationBack();
        myCartLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.btn_my_cart)
    public void onBtnMyCartClicked() {
        startActivity(new Intent(this, MyCartActivity.class));
    }

    @OnClick(R.id.navigation_back)
    public void onNavigationbackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.checked_in_layout)
    public void onCheckedInlayoutClicked() {
        startActivity(new Intent(this, VenueDetailInfo.class));
    }

    public void setUserAsCheckedIn(String venueName) {
        toolbarTitle.setVisibility(View.INVISIBLE);
        checkedInLayout.setVisibility(View.VISIBLE);
        toolbarTitleCheckedIn.setText(venueName);
        myCartLayout.setVisibility(View.VISIBLE);
    }

    public void setUserAsCheckedOut() {
        checkedInLayout.setVisibility(View.INVISIBLE);
        toolbarTitle.setVisibility(View.VISIBLE);
        myCartLayout.setVisibility(View.INVISIBLE);
    }

    public void hideMyCart() {
        myCartLayout.setVisibility(View.INVISIBLE);
    }

    public void updateCartCount() {
        List<DrinkCart> dcs = DrinkCart.getDrinkCartItems();
        List<FoodCart> fcs = FoodCart.getFoodCartItems();

        int totalCartItems = 0;
        if (dcs != null) {
            totalCartItems = dcs.size();
        }
        if (fcs != null) {
            totalCartItems = totalCartItems + fcs.size();
        }
        cartCount.setText(String.valueOf(totalCartItems));
    }

    public void showToast(String msg) {
        CustomToast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void updateBalance(double amountToDeduct) {
        if (balanceIndicatorLayout.getVisibility() == View.GONE) {
            balanceIndicatorLayout.setVisibility(View.VISIBLE);
        }
        User u = User.getUser();
        double b = u.getBalance() - amountToDeduct;
        String available_balance = UtilityMethods.getTwoPlaceDecimal(b);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            availableBalanceTextView.setText(Html.fromHtml(getString(R.string.available_balance, available_balance), Html.FROM_HTML_MODE_LEGACY));
        } else {
            availableBalanceTextView.setText(Html.fromHtml(getString(R.string.available_balance, available_balance)));
        }

        if (b < 200) {
            balanceIndicatorLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.less_than_100));
        } else if (b < 500) {
            balanceIndicatorLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.less_than_200));
        } else {
            balanceIndicatorLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.excess));
        }
    }
}
