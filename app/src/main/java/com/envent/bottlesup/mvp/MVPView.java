package com.envent.bottlesup.mvp;

import android.support.v4.app.Fragment;
import android.widget.RadioButton;

import com.envent.bottlesup.mvp.model.ContactsToInvite;
import com.envent.bottlesup.mvp.model.server_response.PendingBalanceResponse;
import com.envent.bottlesup.mvp.model.server_response.StatusMessage;
import com.envent.bottlesup.mvp.model.server_response.alias_response.AliasResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history.ConsumptionHistoryResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.CancelOrderResponse;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.ConsumptionHistoryDetailResponse;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.DrinkItemResponse;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;
import com.envent.bottlesup.mvp.model.server_response.food_drink_response.FoodDrinkTabResponse;
import com.envent.bottlesup.mvp.model.server_response.fooditems.FoodItemResponse;
import com.envent.bottlesup.mvp.model.server_response.fooditems.SearchedItemResponse;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.LoginResponse;
import com.envent.bottlesup.mvp.model.server_response.login_register_verified_blocked.RegisterResponse;
import com.envent.bottlesup.mvp.model.server_response.mybalance.MyBalanceResponse;
import com.envent.bottlesup.mvp.model.server_response.notification_response.NotificationBody;
import com.envent.bottlesup.mvp.model.server_response.order_request_response.OrderRequestResponse;
import com.envent.bottlesup.mvp.model.server_response.order_status_response.OrderStatusResponse;
import com.envent.bottlesup.mvp.model.server_response.saving_response.SavingResponse;
import com.envent.bottlesup.mvp.model.server_response.seating_places.SeatingPlaceResponse;
import com.envent.bottlesup.mvp.model.server_response.topup_response.TopupHistoryResponse;
import com.envent.bottlesup.mvp.model.server_response.topup_status_response.TopupStatusResponse;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueDataItem;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueResponse;

import java.util.List;

/**
 * Created by ronem on 3/16/18.
 */

public class MVPView {

    public interface SplashView extends MessageInterface, ProgressDialogInterface {
        void onUserResponseReceived(RegisterResponse response);

        void startLoginRegisterInError();
    }

    public interface MessageInterface {

        void showMessage(String msg);

        void unAuthorized();
    }

    public interface ProgressDialogInterface {
        void showProgressDialog();

        void hideProgressDialog();

    }

    public interface SwipeProgressInterface {
        void showSwipeProgress();

        void hideSwipeProgress();

    }

    public interface VenueView extends SwipeProgressInterface, ProgressDialogInterface, MessageInterface {
        void onEventsResponseReceived(EventResponse response);

        void onVenueListResponse(VenueResponse response);

        void onVenueManagedListReceived(List<VenueDataItem> venues);

        void onCheckIn(String venueName);

        void onCheckOut(String venueName);
    }

    public interface VenueDetailView extends ProgressDialogInterface, MessageInterface {
        void onEventResponseReceived(EventResponse eventResponse);

        void onCheckOutSuccess();
    }

    public interface FoodView extends ProgressDialogInterface, MessageInterface {
        void onBannerResponse(EventResponse eventResponse);

        void onFoodTabsResponse(FoodDrinkTabResponse tabResponse);
    }

    public interface FoodItemView extends SwipeProgressInterface, MessageInterface {
        void onFoodItemsResponse(FoodItemResponse foodItemResponse);
        void onSearchedFoodItemsResponse(SearchedItemResponse foodItemResponse);
    }

    public interface DrinkView extends ProgressDialogInterface, MessageInterface {
        void onBannerResponse(EventResponse eventResponse);

        void onDrinkTabsResponse(FoodDrinkTabResponse tabResponse);

    }

    public interface DrinkItemView extends SwipeProgressInterface, MessageInterface {
        void onDrinkItemResponse(DrinkItemResponse response);
    }

    public interface MyWalletView extends ProgressDialogInterface, MessageInterface {
        void onMybalanceReceived(MyBalanceResponse response);

        void onPendingBalanceReceived(PendingBalanceResponse response, String isFrom);

        void onTopUpReceived(StatusMessage response);

    }

    public interface DashboardView {
        void logout();

        void launchFragment(Fragment fragment);

        void launchActivity(Class<?> activity);

        void launchWebView(String url);

        void showMessage(String msg);
    }

    public interface LoginRegisterView extends ProgressDialogInterface, MessageInterface {
        void onRegisterResponseReceived(RegisterResponse response);

        void onLoginResponseReceived(LoginResponse response);

        void onVerifyUserResponseReceived(RegisterResponse response);

        void onResendOTPResponseReceived(LoginResponse response);

        String getSignInUserName();

        String getSignInPassword();

        void onSignInUserNameError(String msg);

        void onSignInUserPasswordError(String msg);

        String getRegisterFirstName();

        String getRegisterLastName();

        String getRegisterEmail();

        String getRegisterMobile();

        String getRegisterPassword();

        String getRegisterConfirmPassword();

        String getReferralCode();

        String getRegisterDOB();

        int getRegisterGender();

        boolean getTermsCondition();

        void onRegisterFirstNameError(String msg);

        void onRegisterLastNameError(String msg);

        void onRegisterEmailError(String msg);

        void onRegisterMobileError(String msg);

        void onRegisterPasswordError(String msg);

        void onRegisterConfirmPasswordError(String msg);

        void onRegisterReferralCodeError(String msg);


    }

    public interface MyCartView extends ProgressDialogInterface, MessageInterface {
        void onMyCartItemsReceived(List<Object> items, boolean isForOnlyBalanceUpdate);

        void onSubTotalReceived(double subTotal, double serviceCharge, double vatAmount, double grandTotal, double saving);

        void onSeatingPlaceResponseReceived(SeatingPlaceResponse response);

        void onOrderResponseReceived(OrderRequestResponse resonse);
    }

    public interface DetailDrinkView {
        void onDrinkOptionRadioButtonCreated(RadioButton rb);

        void onDrinkMixtureRadioButtonCreated(RadioButton rb);

        void onInflatedAllServingTypes();

        void onInflatedAllMixtures();
    }

    public interface ConsumptionHistoryView extends SwipeProgressInterface, MessageInterface {
        void onConsumptionHistoryReceived(ConsumptionHistoryResponse response);
    }

    public interface TopUpHistoryView extends SwipeProgressInterface, MessageInterface {
        void onTopUpHistoryReceived(TopupHistoryResponse response);
    }

    public interface RequestedOrderStatusView extends SwipeProgressInterface, MessageInterface {
        void onRequestedOrderStatusResponse(OrderStatusResponse response);
    }

    public interface RequestedTopupStatusView extends SwipeProgressInterface, MessageInterface {
        void onRequestedTopUpStatusResponse(TopupStatusResponse response);
    }

    public interface ChangePasswordView extends MessageInterface, ProgressDialogInterface {
        String getOldPassword();

        String getNewPassword();

        String getConfirmPassword();

        void onChangePasswordResponse(StatusMessage res);
    }

    public interface NotificationView extends SwipeProgressInterface, MessageInterface {
        void onNotificationResponse(NotificationBody response);
    }

    public interface NotificationDetailView extends ProgressDialogInterface, MessageInterface {
        void onNotificationDeleteResponse(StatusMessage statusMessage);
    }

    public interface ForgotPasswordView extends ProgressDialogInterface, MessageInterface {
        String getOtpUserName();

        void setOtpUserNameError(String msg);

        String getResetOtpToken();

        void setResetOtpTokenError(String msg);

        String getResetUserName();

        void setResetUerNameError(String msg);

        String getResetPassword();

        void setResetPasswordError(String msg);

        String getResetConfirmPassword();

        void setResetConfirmPasswordError(String msg);

        void onOtpSentResponse(StatusMessage response);

        void onPasswordResetResponse(StatusMessage response);


    }

    public interface AliasView extends ProgressDialogInterface, MessageInterface {
        void onAliasResponseReceived(AliasResponse response);
    }

    public interface InviteFriendView extends ProgressDialogInterface, MessageInterface {
        void onContactNumberReceivedFromStartActivityForResult(String contactNumber);

        void onContactsToBeInvited(List<ContactsToInvite> contactsToInvites);
    }

    public interface ConsumptionHistoryDetailView extends ProgressDialogInterface, MessageInterface {
        void onConsumptionHistoryDetailReceived(ConsumptionHistoryDetailResponse res);
        void onCancelOrderResponseReceived(CancelOrderResponse res);
    }

    public interface SavingView extends ProgressDialogInterface, MessageInterface {
        void onSavingResponseReceived(SavingResponse response);
    }

    public interface SupportView extends ProgressDialogInterface, MessageInterface {

    }

    public interface KhaltiTopupView extends ProgressDialogInterface, MessageInterface {

    }

}
