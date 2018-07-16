package com.envent.bottlesup.mvp.presenter;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by ronem on 4/27/18.
 */

public class ReferralCodeImpl implements MyPresenter.SendReferrelCodePresenter {

    @Override
    public void inviteUser(Context context, String phoneNumber, Dialog dialog) {

        if (TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(context, "Please enter mobile number.", Toast.LENGTH_SHORT).show();

        } else {

            dialog.dismiss();

            //dismiss dialog here
        }

    }


}
