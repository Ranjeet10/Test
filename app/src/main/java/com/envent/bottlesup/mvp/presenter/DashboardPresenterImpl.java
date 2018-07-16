package com.envent.bottlesup.mvp.presenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.widget.ImageView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.User;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.view.activities.BottlesUpSplash;
import com.envent.bottlesup.mvp.view.activities.Dashboard;
import com.envent.bottlesup.mvp.view.activities.History;
import com.envent.bottlesup.mvp.view.activities.InviteFriendActivity;
import com.envent.bottlesup.mvp.view.activities.NavNotification;
import com.envent.bottlesup.mvp.view.activities.RequestStatusActivity;
import com.envent.bottlesup.mvp.view.activities.Savings;
import com.envent.bottlesup.mvp.view.activities.Settings;
import com.envent.bottlesup.mvp.view.activities.Support;
import com.envent.bottlesup.mvp.view.activities.TermsAndConditions;
import com.envent.bottlesup.mvp.view.activities.Tutorial;
import com.envent.bottlesup.mvp.view.fragments.FragmentDrink;
import com.envent.bottlesup.mvp.view.fragments.FragmentFood;
import com.envent.bottlesup.mvp.view.fragments.FragmentMyWallet;
import com.envent.bottlesup.mvp.view.fragments.FragmentVenue;
import com.envent.bottlesup.utils.SessionManager;
import com.envent.bottlesup.utils.UtilityMethods;


/**
 * Created by ronem on 3/16/18.
 */

public class DashboardPresenterImpl implements MyPresenter.DashboardPresenter {
    private MVPView.DashboardView view;

    @Override
    public void addView(MVPView.DashboardView view) {
        this.view = view;

    }


    @Override
    public void setMenuEventListener(NavigationView navigationView, BottomNavigationView bottomNavigationView) {
        UtilityMethods.disableShiftMode(bottomNavigationView);

        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_notification:
                    view.launchActivity(NavNotification.class);
                    break;

                case R.id.nav_history:
                    view.launchActivity(History.class);
                    break;

                case R.id.nav_request_status:
                    view.launchActivity(RequestStatusActivity.class);
                    break;
                case R.id.nav_saving:
                    view.launchActivity(Savings.class);
                    break;
                case R.id.nav_tutorial:
                    view.launchActivity(Tutorial.class);
                    break;

                case R.id.nav_support:
                    view.launchActivity(Support.class);
                    break;
                case R.id.nav_settings:
                    view.launchActivity(Settings.class);
                    break;
                case R.id.nav_terms_and_condition:
                    view.launchActivity(TermsAndConditions.class);
                    break;
                case R.id.nav_invite_friend:
                    view.launchActivity(InviteFriendActivity.class);
                    break;
                case R.id.nav_logout:
                    view.logout();
                    break;

            }
            return true;
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_menu_venue:
                    view.launchFragment(FragmentVenue.createNewInstance());
                    break;
                case R.id.bottom_menu_food:
                    view.launchFragment(FragmentFood.createNewInstance());
                    break;
                case R.id.bottom_menu_drink:
                    view.launchFragment(FragmentDrink.createNewInstance());
                    break;
                case R.id.bottom_menu_wallet:
                    view.launchFragment(FragmentMyWallet.createNewInstance());
                    break;
            }
            return true;
        });

    }

    @Override
    public void blurImage(ImageView src, ImageView destination) {
        Bitmap bmp = ((BitmapDrawable) src.getDrawable()).getBitmap();
//        Blurry.with((Dashboard) view)
//                .radius(30)
//                .sampling(2)
//                .async()
//                .from(bmp)
//                .into(destination);
    }
//
//    @Override
//    public void logOut() {
//        User.logOut();
//        Dashboard dashboard = (Dashboard) view;
//        new SessionManager(dashboard).setDeviceIdSavedWithUser(false);
//        FoodCart.deleteFoodsFromCart();
//        DrinkCart.deleteDrinksFromCart();
//        CheckedInVenue.checkoutVenue();
//        dashboard.finish();
//        dashboard.startActivity(new Intent(dashboard, BottlesUpSplash.class));
//    }


    @Override
    public void askForAppExit(Activity context) {
        new AlertDialog.Builder(context).setPositiveButton("Yes", ((dialog, which) -> context.finish()))
                .setNegativeButton("No", ((dialog, which) -> dialog.dismiss()))
                .setMessage("Do you want to exit this app?").show();
    }


}
