package com.envent.bottlesup.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.envent.bottlesup.utils.MetaData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ronem on 4/8/18.
 */

public class SMSReceiver extends BroadcastReceiver {

    private String TAG = getClass().getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Object[] pdusObj = (Object[]) bundle.get("pdus");
            if (pdusObj == null) {
                return;
            }
            for (Object aPdusObj : pdusObj) {
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) aPdusObj);
                String senderAddress = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();

                Log.e(TAG, "Received SMS: " + message + ", Sender: " + senderAddress);

//                if (senderAddress.contains(MetaData.SMS_SENDER_NUMBER)
//                        || senderAddress.contains(MetaData.SMS_SENDER_ADDRESS)) {
                if (message.contains(MetaData.SMS_CONTAINED_MATCH)) {
                    String otp = getVerificationCode(message);
                    if (otp != null) {
                        new Handler().postDelayed(() -> sendBroadCast(context, otp), 1000);
                    } else {
                        return;
                    }
                } else {
                    return;
                }
//                }

            }
        }
    }

    private void sendBroadCast(Context context, String verificationCode) {
        Intent intent = new Intent(MetaData.SMS_RECEIVED_BROAD_CAST_EVENT);
        intent.putExtra(MetaData.SMS_VERIFICATION_CODE, verificationCode);
        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
    }

    private String getVerificationCode(String message) {

        Pattern pattern = Pattern.compile(MetaData.OTP_REGEX);
        Matcher matcher = pattern.matcher(message);
        String otp = null;
        while (matcher.find()) {
            otp = matcher.group();
        }

        return otp;
    }
}
