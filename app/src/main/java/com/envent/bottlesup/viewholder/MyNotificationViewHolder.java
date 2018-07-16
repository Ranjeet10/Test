package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 4/20/18.
 */

public class MyNotificationViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.notification_title)
    public TextView notificationTitle;
    @BindView(R.id.notification_body)
    public TextView notificationBody;
    @BindView(R.id.created_on)
    public TextView createdOn;

    public MyNotificationViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
