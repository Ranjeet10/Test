package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 4/10/18.
 */

public class TopUpHistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.top_up_by)
    public TextView topUpByTextView;
    @BindView(R.id.topup_amount)
    public TextView topupAmountTextView;
    @BindView(R.id.top_up_date_and_venue)
    public TextView topupDateVenueTextView;

    public TopUpHistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}