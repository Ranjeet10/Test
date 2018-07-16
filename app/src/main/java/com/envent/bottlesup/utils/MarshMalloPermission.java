package com.envent.bottlesup.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by ronem on 3/15/18.
 */

public class MarshMalloPermission {

    private final Context context;

    //permissions array
    public String[] readContactpermission = new String[]{Manifest.permission.READ_CONTACTS};
    public String[] readPhoneStatePermission = new String[]{Manifest.permission.READ_PHONE_STATE};
    public String[] fineLocationPermissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
    public String[] smsPermissionReceive = new String[]{Manifest.permission.RECEIVE_SMS};
    public String[] smsPermissionRead = new String[]{Manifest.permission.READ_SMS};
    public String[] smsPermissionSend = new String[]{Manifest.permission.SEND_SMS};
    public String[] phoneCallPermissions = new String[]{Manifest.permission.CALL_PHONE};
    public String[] ExternalPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
    public String[] CameraPermissions = new String[]{Manifest.permission.CAMERA};


    public MarshMalloPermission(Context context) {
        this.context = context;
    }


    /**
     * Read External Storage run time permission
     *
     * @return check and request
     */
    public boolean isExternalPermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, ExternalPermissions[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;

    }

    public void requestExternalStorage() {
        ActivityCompat.requestPermissions((Activity) context, ExternalPermissions, MetaData.REQUEST_CODE.EXTERNAL_CODE);
    }


    /**
     * Fine Location run time permission
     *
     * @return check and request
     */
    public boolean isFineLocationPermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, fineLocationPermissions[0]) != PackageManager.PERMISSION_GRANTED)
            return false;
        return true;
    }

    public void requestFineLocationPermission() {
        ActivityCompat.requestPermissions((Activity) context, fineLocationPermissions, MetaData.REQUEST_CODE.ACCESS_FINE_LOCATION_REQUEST_CODE);
    }


    /**
     * Sms Read run time permission
     *
     * @return check and request
     */
    public boolean isSMSPermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, smsPermissionRead[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestSMSPermission() {
        ActivityCompat.requestPermissions((Activity) context, smsPermissionRead, MetaData.REQUEST_CODE.SMS_REQUEST_CODE);
    }


    /**
     * Sms Receive run time permission
     *
     * @return check and request
     */
    public boolean isSMSReceivePermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, smsPermissionReceive[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestSMSReceivePermission() {
        ActivityCompat.requestPermissions((Activity) context, smsPermissionReceive, MetaData.REQUEST_CODE.SMS_RECEIVE_REQUEST_CODE);
    }


    /**
     * Sms Send run time permission
     *
     * @return check and request
     */
    public boolean isSMSSendPermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, smsPermissionSend[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestSMSSendPermission() {
        ActivityCompat.requestPermissions((Activity) context, smsPermissionSend, MetaData.REQUEST_CODE.SMS_SEND_REQUEST_CODE);
    }


    public boolean isPhoneStatePermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, readPhoneStatePermission[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestPhoneStatePermission() {
        ActivityCompat.requestPermissions((Activity) context, readPhoneStatePermission, MetaData.REQUEST_CODE.PHONE_STATE_REQUEST_CODE);
    }


    /**
     * Phone Call run time permission
     *
     * @return check and request
     */
    public boolean isPhoneCallPermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, phoneCallPermissions[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestPhoneCallPermission() {
        ActivityCompat.requestPermissions((Activity) context, phoneCallPermissions, MetaData.REQUEST_CODE.PHONE_CALL_REQUEST_CODE);
    }


    /**
     * Read Contact run time permission
     *
     * @return check and request
     */
    public boolean isContactReadPermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, readContactpermission[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestReadContactPermission() {
        ActivityCompat.requestPermissions((Activity) context, readContactpermission, MetaData.REQUEST_CODE.READ_CONTACT_REQUEST_CODE);
    }


    /**
     * Camera  run time permission
     *
     * @return check and request
     */
    public boolean isCameraPermissionGranted() {
        if (ContextCompat.checkSelfPermission(context, CameraPermissions[0]) != PackageManager.PERMISSION_GRANTED) {
            return false;
        }
        return true;
    }

    public void requestCameraPermission() {
        ActivityCompat.requestPermissions((Activity) context, CameraPermissions, MetaData.REQUEST_CODE.CAMERA_REQUEST_CODE);
    }
}
