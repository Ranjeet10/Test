package com.envent.bottlesup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.topup_response.TopupHistoryDataItem;
import com.envent.bottlesup.utils.UtilityMethods;
import com.envent.bottlesup.viewholder.TopUpHistoryViewHolder;

import java.util.List;

/**
 * Created by ronem on 4/8/18.
 */

public class TopUpHistoryAdapter extends RecyclerView.Adapter<TopUpHistoryViewHolder> {
    private List<TopupHistoryDataItem> items;
    private Context context;
    private LayoutInflater inflater;

    public TopUpHistoryAdapter(Context context, List<TopupHistoryDataItem> items) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public TopUpHistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.single_item_topup_history, parent, false);
        return new TopUpHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TopUpHistoryViewHolder holder, int position) {
        TopupHistoryDataItem t = items.get(position);
        holder.topupDateVenueTextView.setText(t.getTopUpOn());
        holder.topUpByTextView.setText(t.getVenue());
        holder.topupAmountTextView.setText(context.getString(R.string.currency_value, UtilityMethods.getTwoPlaceDecimal(t.getAmount())));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
