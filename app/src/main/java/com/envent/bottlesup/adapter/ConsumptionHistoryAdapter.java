package com.envent.bottlesup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.consumption_history.ConsumptionHistoryItemsItem;
import com.envent.bottlesup.utils.UtilityMethods;
import com.envent.bottlesup.viewholder.ConsumptionHistoryViewHolder;

import java.util.List;

/**
 * Created by ronem on 4/10/18.
 */

public class ConsumptionHistoryAdapter extends RecyclerView.Adapter<ConsumptionHistoryViewHolder> {
    private List<ConsumptionHistoryItemsItem> items;
    private Context context;
    private LayoutInflater inflater;

    public ConsumptionHistoryAdapter(Context context, List<ConsumptionHistoryItemsItem> items) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ConsumptionHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.single_item_consumption_history, parent, false);
        return new ConsumptionHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumptionHistoryViewHolder holder, int position) {
        ConsumptionHistoryItemsItem c = items.get(position);
        holder.venueNameTextView.setText(c.getConsumptionHistoryVenue().getName());
        holder.topupDateTextView.setText(c.getCreatedOn());
        holder.topupAmountTextView.setText(context.getResources().getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(c.getAmount())));

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
