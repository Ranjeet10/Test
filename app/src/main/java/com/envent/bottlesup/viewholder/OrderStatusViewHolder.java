package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 6/3/18.
 */

public class OrderStatusViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.order_id)
    public TextView orderIdView;
    @BindView(R.id.created_on)
    public TextView createdOnView;
    @BindView(R.id.status)
    public TextView statusView;
    @BindView(R.id.amount)
    public TextView amountView;
    @BindView(R.id.venue_name)
    public TextView venueNameView;

    public OrderStatusViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
