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

public class TopupStatusViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.top_up_id)
    public TextView topUpIdView;

    @BindView(R.id.top_type)
    public TextView topupTypeView;

    @BindView(R.id.venue_name)
    public TextView venueNameView;

    @BindView(R.id.created_on)
    public TextView createdOnView;

    @BindView(R.id.amount)
    public TextView amountView;

    @BindView(R.id.status)
    public TextView statusView;

    public TopupStatusViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
