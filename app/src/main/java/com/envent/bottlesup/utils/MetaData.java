package com.envent.bottlesup.utils;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.TutorialData;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventResponse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ronem on 3/15/18.
 */

public class MetaData {

    public static String userImage = "https://scontent.fktm8-1.fna.fbcdn.net/v/t1.0-9/18813387_1544229595611771_3045076610462682194_n.jpg?_nc_cat=0&oh=599eb366d918be9a35760b150a6ab58b&oe=5B6A093E";
    //clear before launching the app
    public static final double EXTRA_BALANCE = 0;
    public static final String CUSTOMER_CARE_NUMBER = "1234567891";
    public static final String SMS_RECEIVED_BROAD_CAST_EVENT = "com.bidhee.bottlesup.SMS_VERIFICATION";
    public static final String SMS_VERIFICATION_CODE = "verificationCode";
    public static final String SMS_CONTAINED_MATCH = "registering to BottlesUp";
    public static final String OTP_REGEX = "[0-9]{1,4}";
    public static final int TRANSACTION_BALANCE_LIMIT = 10000;
    public static final int WALLET_BALANCE_LIMIT = 25000;

    public static class REQUEST_CODE {
        public static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 1000;
        public static final int LOCATION_REQUEST_CODE = 1001;
        public static final int SMS_REQUEST_CODE = 1002;
        public static final int PHONE_CALL_REQUEST_CODE = 1003;
        public static final int READ_CONTACT_REQUEST_CODE = 1004;
        public static final int CAMERA_REQUEST_CODE = 1005;
        public static final int SMS_SEND_REQUEST_CODE = 1006;
        public static final int EXTERNAL_CODE = 1007;
        public static final int PHONE_STATE_REQUEST_CODE = 1008;
        public static final int SMS_RECEIVE_REQUEST_CODE = 1009;
        public static final int ESEWA_REQUEST_CODE = 1010;
    }

    public static class INTENT_ACTIONS {
        public static final String ACTIVITY_CLOSE_ACTION = "com.bidhee.bottlesup.mvp.view.activities.CLOSE";
        public static final String CHECKOUT_ACTION = "com.bidhee.bottlesup.mvp.view.activities.CHECKOUT";
        //        public static final String NOTIFICATION_ACTION = "com.bidhee.bottlesup.NOTIFICATION";
        public static final String UPDATE_BALANCE_ACTION = "com.bidhee.bottlesup.UPDATE_BALANCE";
        public static final String UPDATE_BALANCE_TO_WALLET_ACTION = "com.bidhee.bottlesup.UPDATE_BALANCE_TO_WALLET";
        public static final String NOTIFICATION_DELETE_ACTION = "com.bidhee.bottlesup.NOTIFICATION_DELETE";
        public static final String ALERT_DELETE_ACTION = "com.bidhee.bottlesup.ALERT_DELETE";
    }

    public static class MESSAGE {

        public static final String TAX_EXCLUSIVE = "Tax Exclusive";
        public static final String YOU_HAVE_BEEN_INVITED = "You have been invited to join BottlesUp! Please visit www.gobottlesup.com for more information on how to become a member of this exclusive club.";
        public static final String NO_VENUE_FOUND = "Venue not found";
        public static final String TAX_INCLUSIVE = "Tax Inclusive";
        public static final String NO_INTERNET = "You are currently not connected to the internet, please connect.";
        public static final String PLEASE_WAIT = "Please wait...";
        public static final String LOGOUT = "Are you sure you want to log out?";
        public static final String STATUS = "status";
        public static final String SUCCESS = "success";
        public static final String MSG = "message";
        public static final String MUST_ACCEPT_TERMS_AND_CONDITION = "You must accept terms and conditions to proceed";
        public static final String PASSWORD_NOT_MATCHED = "Password not matched";
        public static final String NEW_PASSWORD_NOT_MATCHED = "New Password not matched";
        public static final String PASSWORD_LENGTH = "Password must be between 10-16";
        public static final String EMPTY = "Fields cannot be empty";
        public static final String PHONE_NUMBER_ERROR = "Mobile Number must be of 10 digit";
        public static final String EMAIL_NOT_VALID = "Enter valid email";
        public static final String SOME_THING_WENT_WRONG = "Something went wrong";
        public static final String EMPTY_DRINK_OPTION = "Please select Drink Option";
        public static final String EMPTY_MIXTURE = "Please select Mixture";
        public static final String ITEM_ADDED = "Item successfully added to the cart";
        public static final String ALL_ITEM_WILL_BE_DELETED = "By pressing All the items from cart will be removed. Do you want to continue?";
        public static final String NO_ITEM_TO_DELETE = "Cart is empty";
        public static final String SHOULD_SHOW_PHONE_CALL_MESSAGE = "You must give the permission to access Phone call ";
        public static final String SHOULD_SHOW_EXTERNAL_STORAGE_MESSAGE = "You must give the permission to access External Storage ";
        public static final String SHOULD_SHOW_SMS_MESSAGE = "Bottles Up will now request send/read SMS permission on your device.\n\n This is required in order to verify user with OTP";
        public static final String DENIED_PHONE_CALL_SERVICE = "You denied the permission to access phone call";
        public static final String EMPTY_AMOUNT = "Amount must be greater than 0";
        public static final String NOT_CHECKED_IN = "Sorry ! but you are not checked in to any venue";
        public static final String WANT_TO_CHECKIN = "Do you want to check in?";
        public static final String FINE_LOCATION_MESSAGE = "You must provide access fine location permission ";
        public static final String PERMISSION_DENIED = "You have denied permission system won't perform well without this permission";
        public static final String GIVE_PERMISSION = "Give Permission";
        public static final String COMING_SOON = "Coming soon...";
        public static final String SELECT_SEATING_PLACE = "Select Seating";
        public static final String MUST_SELECT_SEATING_PLACE = "Please select seating place";
        public static final String INSUFFICIENT_BALANCE = "Sorry, you don't have enough balance to proceed. You may select the following.";
        public static final String NO_ITEMS_IN_THIS_SECTION = "There are no items in this section";
        public static String EXCESS_TRANSACTION_LIMIT = "Balance Top up cannot be greater than Rs. 10,000";
        public static String EXCESS_BALANCE_LIMIT = "Wallet Balance cannot be greater than Rs. 25,000";
        public static String TITLE_TOPUP_WITH_KHALTI = "Top Up with Khalti";
        public static String TITLE_TOPUP_WITH_ESEWA = "Top Up with eSewa";
    }

    public static class KEY {

        public static final String TABLE_BAR_ID = "table_bar_id";
        public static final String TITLE = "title";
        public static final String ORDER_ID = "order_id";
        public static final String ORDER_TYPE = "order_type";
        public static final String ORDER_TYPE_PENDING = "Pending";
        public static final String IS_FIRST_RUN = "is_first_run";
        public static final String IS_FROM_SPLASH = "is_from_splash";
        public static final String ITEM = "item";
        public static final String CATEGORY_ID = "category_id";
        public static final String EVENT = "event";
        public static final String DEVICE_ID_SAVED_STATUS = "device_id_saved_status";
        public static final String DEVICE_ID_SAVED_STATUS_WITH_USER = "device_id_saved_status_with_user";
        public static final String NOTIFICATION = "notification";
        public static final String NOTIFICATION_TYPE = "notification_type";
        public static final String NOTIFICATION_TYPE_ALERT = "notification_type_alert";
        public static final String NOTIFICATION_TYPE_NOTIFICATION = "notification_type_notification";
        public static final String NOTIFICATION_STATUS = "notification_status";
        public static final String NOTIFICATION_TYPE_TOPUP = "topup";
        public static final String NOTIFICATION_TYPE_ORDER = "order";

    }

    public static class STATUS {
        public static final String PENDING = "Pending";
        public static final String IN_KITCHEN = "In Kitchen";
        public static final String SERVED = "Served";
        public static final String APPROVED = "Approved";
    }

    public static class BOTTOM_TAB {
        public static final String FOOD = "food";
        public static final String DRINK = "drink";
    }

    public static List<TutorialData> getTutorials() {
        List<TutorialData> tutorials = new ArrayList<>();
        tutorials.add(new TutorialData(R.drawable.signup, "Register with BottlesUp"));
        tutorials.add(new TutorialData(R.drawable.login, "Login to BottlesUp"));
        tutorials.add(new TutorialData(R.drawable.topup, "Top Up credits on BottlesUp"));
        tutorials.add(new TutorialData(R.drawable.check_in, "Check-In to a venue"));
        tutorials.add(new TutorialData(R.drawable.menu, "Select your food and drinks"));
        tutorials.add(new TutorialData(R.drawable.order, "Customize your order"));
        tutorials.add(new TutorialData(R.drawable.cart_detail, "Review your order and confirm"));
        tutorials.add(new TutorialData(R.drawable.saving, "Save money"));
        return tutorials;
    }

    public static class APP_LEVEL_CACHE {
        public static EventResponse EVENTS = null;
    }
}
