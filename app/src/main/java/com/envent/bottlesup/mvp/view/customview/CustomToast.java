package com.envent.bottlesup.mvp.view.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.envent.bottlesup.R;

/**
 * Created by ronem on 3/25/18.
 */

public class CustomToast extends Toast {

    public CustomToast(Context context, String text, int duration) {
        super(context);
        init(context, text, duration);
    }

    private void init(Context context, String text, int duration) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_toast, null, false);
        TextView textView = view.findViewById(R.id.text);
        textView.setText(text);
        setView(view);
        setDuration(duration);
        show();
    }
}
