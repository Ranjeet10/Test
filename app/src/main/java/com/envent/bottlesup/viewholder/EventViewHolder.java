package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 5/20/18.
 */

public class EventViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.event_name)
    public TextView eventName;
    @BindView(R.id.event_time)
    public TextView eventTime;

    public EventViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
