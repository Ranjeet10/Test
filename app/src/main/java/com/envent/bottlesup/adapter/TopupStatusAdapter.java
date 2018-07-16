package com.envent.bottlesup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.topup_status_response.TopUpStatusItem;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;
import com.envent.bottlesup.viewholder.TopupStatusViewHolder;

import java.util.List;

/**
 * Created by ronem on 6/3/18.
 */

public class TopupStatusAdapter extends RecyclerView.Adapter<TopupStatusViewHolder> {

    private Context context;
    private List<TopUpStatusItem> items;
    private LayoutInflater inflater;

    public TopupStatusAdapter(Context context, List<TopUpStatusItem> items) {
        this.context = context;
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TopupStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.single_item_status_topup, parent, false);
        return new TopupStatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TopupStatusViewHolder holder, int position) {
        TopUpStatusItem item = items.get(position);
        holder.amountView.setText(context.getString(R.string.currency_value, UtilityMethods.getTwoPlaceDecimal(item.getAmount())));
        holder.createdOnView.setText(item.getTopUpOn());
        holder.venueNameView.setText(item.getVenue());
//        holder.topupTypeView.setText(context.getString(R.string.topup_type, item.getType()));
        holder.topUpIdView.setText(context.getString(R.string.topup_id, String.valueOf(item.getId())));

        if (item.getStatus() != null) {
            holder.statusView.setText(item.getStatus());
            if (item.getStatus().equalsIgnoreCase(MetaData.STATUS.PENDING)) {
                holder.statusView.setBackgroundResource(R.drawable.status_pending_bg);
            } else if (item.getStatus().equalsIgnoreCase(MetaData.STATUS.APPROVED)) {
                holder.statusView.setBackgroundResource(R.drawable.status_served);
            } else {
                holder.statusView.setBackgroundResource(R.drawable.status_cancelled);
            }
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
