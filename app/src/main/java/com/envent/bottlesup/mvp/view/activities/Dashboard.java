package com.envent.bottlesup.mvp.view.activities;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;
import com.envent.bottlesup.application.BottlesUpApplication;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.presenter.DashboardPresenterImpl;
import com.envent.bottlesup.mvp.presenter.MyPresenter;
import com.envent.bottlesup.mvp.view.activities.base_activity.MyCartBaseActivity;
import com.envent.bottlesup.mvp.view.customview.CustomToast;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ronem on 3/15/18.
 */

public class Dashboard extends MyCartBaseActivity implements MVPView.DashboardView {

    @BindView(R.id.user_image)
    CircleImageView userImage;
    @BindView(R.id.user_name_text_view)
    TextView userNameTextView;
    @BindView(R.id.user_balance_text_view)
    TextView userBalanceTextView;
    @BindView(R.id.logged_in_header)
    RelativeLayout loggedInHeader;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.bottom_menu_view)
    public BottomNavigationView bottomMenuView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.top_header_layout)
    LinearLayout topHeaderLayout;


    private MyPresenter.DashboardPresenter presenter;
    private FragmentManager fragmentManager;
    private Unbinder unbinder;
    private User user;
    public static final int idle = 0;
    public static final int food = 1;
    public static final int drink = 2;
    public static final int wallet = 3;
    public static int selectionType = idle;
    private Fragment currentFragment;
    private BroadcastReceiver checkoutReceiver;
    private boolean isFromDetail = false;
    //    private SessionManager sessionManager;
    public static boolean isDashboardRunning = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        unbinder = ButterKnife.bind(this);
        fragmentManager = getSupportFragmentManager();

        presenter = new DashboardPresenterImpl();
        presenter.addView(this);

        setUpNavigationView();

        setUpCheckoutReceiver();

        isDashboardRunning = true;

        //in-case if subscription was enabled to false
        //during logout
        OneSignal.setSubscription(true);
    }

    private void setUpCheckoutReceiver() {
        checkoutReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                setUpBottomNavigationMenuView(false, true);
            }
        };
        LocalBroadcastManager
                .getInstance(this)
                .registerReceiver(checkoutReceiver, new IntentFilter(MetaData.INTENT_ACTIONS.CHECKOUT_ACTION));

        updateUserStatus();
    }

    private void setUpNavigationView() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(Dashboard.this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //setup bottom navigation menu
        setUpBottomNavigationMenuView(true, false);

    }

    public void setUpBottomNavigationMenuView(boolean firstAdd, boolean isFromDetail) {
        if (firstAdd) {
            presenter.setMenuEventListener(navigationView, bottomMenuView);
        } else {
            bottomMenuView.getMenu().clear();
            updateCartCount();
        }
        CheckedInVenue c = CheckedInVenue.getCheckedInVenue();
        int bv;
        if (c != null) {
            bv = R.menu.bottom_navigation_view_checked_in;
            setUserAsCheckedIn(c.getVenueName());
            bottomMenuView.inflateMenu(bv);
            UtilityMethods.disableShiftMode(bottomMenuView);
            bottomMenuView.setSelectedItemId(R.id.bottom_menu_food);
        } else {
            bv = R.menu.bottom_navigation_view_checked_out;
            setUserAsCheckedOut();
            bottomMenuView.inflateMenu(bv);
            UtilityMethods.disableShiftMode(bottomMenuView);
        }

        //setting the default fragment for first time
        if (firstAdd) {
            showVenueSelection();
        }

        if (isFromDetail) {
            this.isFromDetail = isFromDetail;
        }
    }

    private void showVenueSelection() {
        bottomMenuView.setSelectedItemId(R.id.bottom_menu_venue);
    }


    @Override
    protected void updateBalance() {
        onResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUserStatus();

        //check flag if the request is came from venue detail page
        //to checkout
        //this flag is updated in the onReceive of checkout receiver
        if (isFromDetail) {
            showVenueSelection();
            isFromDetail = false;
        }

        //update the page for food or drink
        //as per user selection from the my cart page
        if (selectionType == food) {
            bottomMenuView.setSelectedItemId(R.id.bottom_menu_food);
        } else if (selectionType == drink) {
            bottomMenuView.setSelectedItemId(R.id.bottom_menu_drink);
        } else if (selectionType == wallet) {
            bottomMenuView.setSelectedItemId(R.id.bottom_menu_wallet);
        }
        selectionType = idle;
    }

        public void updateUserStatus() {
        user = User.getUser();
            Log.d("api", user.getAccess_token());
        new SessionManager(this).checkAndSavetheDeviceId(user);
        if (user != null) {
            Picasso.with(this)
                    .load(user.getProfileImage())
                    .placeholder(R.drawable.users)
                    .into(userImage);

            userNameTextView.setText(user.getFullName());
            userBalanceTextView.setText(getResources().getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(user.getBalance())));
            updateCartCount();
        } else {
            userImage.setImageResource(R.drawable.users);
            presenter.blurImage(userImage, userImage);
        }

        //send broadcast for balance update for my wallet if fragment is visible
        Intent walletBroadCastIntent = new Intent();
        walletBroadCastIntent.setAction(MetaData.INTENT_ACTIONS.UPDATE_BALANCE_TO_WALLET_ACTION);
        LocalBroadcastManager.getInstance(this).sendBroadcast(walletBroadCastIntent);
    }

    @OnClick(R.id.drawerIcon)
    public void onDrawerIconClicked() {
        UtilityMethods.hideKeyboard(this, bottomMenuView);
        toggleDrawer();
    }

    private void toggleDrawer() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    @Override
    public void logout() {
        drawerLayout.closeDrawer(GravityCompat.START);
        AlertDialog.Builder builder = new MyDialog(this).getDialogBuilder(MetaData.MESSAGE.LOGOUT);
        builder.setCancelable(false);
        builder.setPositiveButton("Logout", (dialog, which) -> {
            dialog.dismiss();
            logoutNow();
//
//            //do not subscribe to One Signal notification
//            OneSignal.setSubscription(false);
        });

        builder.setNegativeButton("Not Now", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void launchFragment(Fragment fragment) {
        currentFragment = fragment;
        UtilityMethods.hideKeyboard(this, bottomMenuView);

        fragmentManager.beginTransaction()
                .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                .replace(R.id.container, currentFragment)
                .commit();
    }

    @OnClick(R.id.btn_my_cart)
    public void onBtnMyCartClicked() {
        startActivity(new Intent(this, MyCartActivity.class));
    }

    @OnClick(R.id.search_icon)
    public void onBtnSearchClicked() {
        startActivity(new Intent(this, FoodDrinkSearchActivity.class));
    }

    @Override
    public void launchActivity(Class<?> activity) {
        drawerLayout.closeDrawer(GravityCompat.START);
        startActivity(new Intent(this, activity));
    }

    @Override
    public void launchWebView(String url) {
        new FinestWebView.Builder(this).show(url);
    }

    @Override
    public void showMessage(String msg) {
        new CustomToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        LocalBroadcastManager.getInstance(this).unregisterReceiver(checkoutReceiver);
        isDashboardRunning = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        currentFragment.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        currentFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            //presenter.askForAppExit(this);

            if(bottomMenuView.getSelectedItemId() != R.id.bottom_menu_venue) {
                bottomMenuView.setSelectedItemId(R.id.bottom_menu_venue);
            }else{
                presenter.askForAppExit(this);
            }
        }

    }
}
