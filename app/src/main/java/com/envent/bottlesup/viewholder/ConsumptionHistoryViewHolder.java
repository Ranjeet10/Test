package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 4/8/18.
 */

public class ConsumptionHistoryViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.venue_name_text_view)
    public TextView venueNameTextView;
    @BindView(R.id.topup_amount)
    public TextView topupAmountTextView;
    @BindView(R.id.topup_date)
    public TextView topupDateTextView;

    public ConsumptionHistoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
