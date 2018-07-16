package com.envent.bottlesup.utils;

import android.app.Activity;
import android.util.Log;

import com.envent.bottlesup.BuildConfig;

/**
 * Created by ronem on 3/15/18.
 */

public class MyLog {

    public static int i(Activity a, String message) {
        if (BuildConfig.DEBUG) {
            return Log.i(a.getClass().getSimpleName(), message);
        }
        return 0;
    }

    public static int i(String tag, String message) {
        if (BuildConfig.DEBUG) {
            return Log.i(tag, message);
        }
        return 0;
    }

    public static int v(Activity a, String message) {
        if (BuildConfig.DEBUG) {
            return Log.v(a.getClass().getSimpleName(), message);
        }
        return 0;
    }

    public static int e(Activity a, String message) {
        if (BuildConfig.DEBUG) {
            return Log.e(a.getClass().getSimpleName(), message);
        }
        return 0;
    }

}
