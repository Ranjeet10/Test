package com.envent.bottlesup.mvp.view.customview;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by ronem on 4/2/18.
 */

public class MyDialog {
    private Context context;

    public MyDialog(Context context) {
        this.context = context;
    }

    public AlertDialog showDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("Okay", (dialog, which) -> dialog.dismiss());

        return builder.create();
    }

    public AlertDialog.Builder getDialogBuilder(String msg) {
        return new AlertDialog.Builder(context)
                .setMessage(msg)
                .setCancelable(false);
    }



}
