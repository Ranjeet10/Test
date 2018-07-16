package com.envent.bottlesup.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.text.Html;
import android.text.InputType;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.CheckedInVenue;
import com.envent.bottlesup.mvp.model.TableBar;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by ronem on 3/15/18.
 */

public class UtilityMethods {

    public static void checkoutNow() {
        FoodCart.deleteFoodsFromCart();
        DrinkCart.deleteDrinksFromCart();
        CheckedInVenue.checkoutVenue();
        TableBar.clearSeatingPlaces();
    }

    public static void togglePasswordField(ImageView btnEyePassword, EditText etPasswordField) {
        int currentInputType = etPasswordField.getInputType();
        int passwordType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;
        int normalType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD;

        if (currentInputType == passwordType) {
            etPasswordField.setInputType(normalType);
            btnEyePassword.setImageResource(R.drawable.ic_show);

        } else {
            etPasswordField.setInputType(passwordType);
            btnEyePassword.setImageResource(R.drawable.ic_hide);
        }
        etPasswordField.setSelection(etPasswordField.length());
    }


    public static String captializeAllFirstLetter(String name) {
        char[] array = name.toCharArray();
        array[0] = Character.toUpperCase(array[0]);

        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }

        return new String(array);
    }

    public static String getTwoPlaceDecimal(double value) {
        //input output
        //22.05 ->22.05
        //22.00 ->22.00
        //22    ->22.00
        //22.137  ->22.14
        //2234.137  ->2,234.14

        double roundOff = Math.round(value * 100) / 100.00;

        if (value < 1) {
            //two decimal places with round of after the decimal
            return String.format(Locale.CANADA, "%.2f", roundOff);

        } else {
            //two decimal places with round of after the decimal and in comma separated format
            return new DecimalFormat("#,###.00").format(roundOff);
        }

    }

    public static String getTwoPlaceDecimalWithoutCommaSeparated(double value) {

        double roundOff = Math.round(value * 100) / 100.00;
        return String.format(Locale.CANADA, "%.2f", roundOff);


    }

    public static String getRoundOffInString(double value) {
        double roundOff = Math.round(value);
        return String.valueOf(roundOff);
    }

    public static void loadHtmlText(String text, TextView tv) {
        Spanned html;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            html = Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            html = Html.fromHtml(text);
        }
        tv.setText(html);
    }

    public static DividerItemDecoration getDividerDecoration(Context context) {
        DividerItemDecoration divider = new
                DividerItemDecoration(context,
                DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(context,
                R.drawable.line_divider));
        return divider;
    }

    public static DateFormat getDateFormat() {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        return sdf;
    }

    public static void getFullScreen(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public static Spannable getSpannableOfNormalText(String textToSpan, int start, int end, int spanColor) {
        Spannable spannable = new SpannableString(textToSpan);
        spannable.setSpan(new ForegroundColorSpan(spanColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static Spannable getSpannableOfHtmlText(Spanned textToSpan, int start, int end, int spanColor) {
        Spannable spannable = new SpannableString(textToSpan);
        spannable.setSpan(new ForegroundColorSpan(spanColor), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                //noinspection RestrictedApi
                item.setShiftingMode(false);
                // set once again checked value, so view will be updated
                //noinspection RestrictedApi
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    public static void hideKeyboard(Activity a, View v) {
        InputMethodManager imm = (InputMethodManager) a.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public static int decreaseQuantity(String q) {
        int quantity = 1;
        if (!TextUtils.isEmpty(q)) {
            int qt = Integer.parseInt(q);
            if (qt > 1) {
                quantity = qt - 1;
            }
        }
        Log.i("Decreased", String.valueOf(quantity));
        return quantity;
    }

    public static int increaseQuantity(String q) {
        int quantity = 1;
        if (!TextUtils.isEmpty(q)) {
            int qt = Integer.parseInt(q);
            quantity = qt + 1;
        }
        Log.i("Increased", String.valueOf(quantity));
        return quantity;
    }

    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean checkPhoneNumber(String phoneNumber) {
        return (phoneNumber.isEmpty() || phoneNumber.length() < 10);
    }

    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

}