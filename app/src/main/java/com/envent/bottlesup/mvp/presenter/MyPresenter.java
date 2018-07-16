package com.envent.bottlesup.mvp.presenter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.InvitedContact;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.MixersItem;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.ServingTypesItem;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueDataItem;

import java.util.List;


/**
 * Created by ronem on 3/16/18.
 */

public class MyPresenter {
    public interface SplashPresenter {
        void addView(MVPView.SplashView view);

        void getUser(String token);

        void destroyCall();
    }

    public interface DrinkPresenter {
        void addView(MVPView.DrinkView view);

        void getBanners(int venueId, String apiKey);

        void getDrinkTabs(int venueId, String apiKey);

        void cancelCall();
    }

    public interface DrinkItemPresenter {
        void addView(MVPView.DrinkItemView view);

        void getDrinkItems(int venueId, int categoryId, String apiKey);

        void cancelCall();
    }

    public interface FoodPresenter {
        void addView(MVPView.FoodView view);

        void getBanners(int venueId, String apiKey);

        void getFoodTabs(int venueId, String apiKey);

        void cancelCall();
    }

    public interface FoodItemPresenter {
        void addView(MVPView.FoodItemView view);

        void getFoodItems(int venueId, int categoryId, String apiKey);

        void getSearchedFood(int venueId, String keyword, String apiKey);

        void cancelCall();
    }

    public interface MyWalletPresenter {
        void addView(MVPView.MyWalletView view);

        void getMyBalance(String apiKey);

        void getPendingBalance(String apiKey,String isFrom);

        void requestTopup(String apiKey, double amount, int venueId, int seatingPlace);

        boolean isAmountValid(int enteredAmount);

        void requestKhaltiTopup(String apiKey, int amount, String khaltiToken);

        void cancelAllCall();
    }

    public interface VenuePresenter {
        void addView(MVPView.VenueView view);

        void getEvents(String apiKey);

        void getVenueList(String tag, String pageCount, String limit);

        void getManagedList(List<VenueDataItem> venues, Location currentLocation);

        void showCheckInCheckoutDialog(Context context, VenueDataItem data);

        void checkInToVenue(Context context, String apiKey, int venueId, VenueDataItem data, boolean isFirst);

        void checkoutToVenue(Context context, String apiKey, int checkInCheckoutId, String checkedInVenueName, boolean isCheckoutAndCheckIn, int venueId, VenueDataItem data);

        void cancelVenueCall();

        void cancelEventCall();
    }

    public interface VenueDetailPresenter extends CancelCall {
        void addView(MVPView.VenueDetailView view);

        void getEvents(int venueId, String apiKey);

        void checkout(Context context, String apiKey, CheckedInVenue data);
    }

    public interface DashboardPresenter {
        void addView(MVPView.DashboardView view);

        void setMenuEventListener(NavigationView navigationView, BottomNavigationView bottomNavigationView);

        void blurImage(ImageView src, ImageView destination);

//        void logOut();

        void askForAppExit(Activity context);
    }


    public interface LoginRegisterPresenter {

        void addView(MVPView.LoginRegisterView view);

        void proceedLogin();

        void proceedRegister();

        void proceedVerification(String apiKey, String userName, String code);

        void proceedResendOTP(String apiKey, String userName);

        void proceedResendReferralcode(String apiKey, String referralCode);

        void cancelCall();

    }


    public interface MyCartPresenter extends CancelCall{
        void addView(MVPView.MyCartView view);

        void getMyCartItems(boolean isForOnlyBalanceUpdate);

        void getSubTotal(List<Object> cartItems);

        void getSeatingPlace(int venueId, String apiKey);

        boolean isBalanceSufficient(double grandTotal);

        void requestOrder(List<Object> cartItems, double grandTotal, int seatingPlace, String onTheyWay, String apiKey);


    }

    public interface DetailDrinkPresenter {
        void addView(MVPView.DetailDrinkView view);

        void attachDrinkOptionRadioButton(Context context, List<ServingTypesItem> drinkOptions);

        void attachMixtureRadioButton(Context context, List<MixersItem> drinkMixtures);
    }

    public interface ConsumptionHistoryPresenter {
        void addView(MVPView.ConsumptionHistoryView view);

        void getConsumptionHistory(String apiKey);

        void cancelCall();
    }

    public interface TopUpHistoryPresenter extends CancelCall{
        void addView(MVPView.TopUpHistoryView view);

        void getTopUpHistory(int page, int limit, String apiKey);
    }

    public interface ChangePasswordPresenter {
        void addView(MVPView.ChangePasswordView view);

        void requestChangePassword();

        void cancelCall();
    }

    public interface NotificationPresenter {
        void addView(MVPView.NotificationView view);

        void getNotification(String apiKey);

        void cancelCall();

    }

    public interface NotificationDetailPresenter extends CancelCall {
        void addView(MVPView.NotificationDetailView view);

        void deleteNotification(String apiKey, int notificationId);
    }

    public interface ForgotPasswordPresenter {

        void addView(MVPView.ForgotPasswordView view);

        void checkOtpFieldValidationAndRequestOtp();

        void requestOtp(String userName);

        void checkResetPasswordValidationAndRequestResetPassword();

        void resetPassword(int token, String userName, String newPassword);

        void cancelCall();
    }

    public interface SendReferrelCodePresenter {
        void inviteUser(Context context, String mobileNumber, Dialog dialog);
    }

    public interface InviteFriendPresenter {
        void addView(MVPView.InviteFriendView vie);

        void getFriendToInvite(LinearLayout parent, List<InvitedContact> invitedContacts);

        void readContactNumber(Context context, Uri uri);

        void cancelCall();
    }

    public interface AliasPresenter {
        void addView(MVPView.AliasView view);

        void getAlias(String alias);

        void cancelCall();
    }

    public interface RequestedOrderStatusPresenter extends CancelCall {
        void addView(MVPView.RequestedOrderStatusView view);

        void getOrderStatus(String apiKey);
    }

    public interface RequestedTopupStatusPresenter extends CancelCall {
        void addView(MVPView.RequestedTopupStatusView view);

        void getTopupStatus(String apiKey);
    }

    public interface ConsumptionHistoryDetailPresenter {
        void addView(MVPView.ConsumptionHistoryDetailView view);

        void getConsumptionHistoryDetail(String apiKey, int orderId);

        void deletePendingOrder(String apiKey, int orderId);

        void cancelCall();
    }

    public interface SavingPresenter extends CancelCall {
        void addView(MVPView.SavingView view);

        void getSaving(String apiKey);
    }

    public interface CancelCall {
        void cancelCall();
    }

    public interface SupportPresenter extends CancelCall {
        void addView(MVPView.SupportView view);

        void getSupport();
    }

}







