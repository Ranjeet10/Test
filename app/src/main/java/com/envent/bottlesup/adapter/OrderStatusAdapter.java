package com.envent.bottlesup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.order_status_response.OrderStatusItem;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.utils.UtilityMethods;
import com.envent.bottlesup.viewholder.OrderStatusViewHolder;

import java.util.List;

/**
 * Created by ronem on 6/3/18.
 */

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusViewHolder> {
    private List<OrderStatusItem> items;
    private Context context;
    private LayoutInflater inflater;

    public OrderStatusAdapter(Context context, List<OrderStatusItem> items) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public OrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.single_item_status_order, parent, false);
        return new OrderStatusViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusViewHolder holder, int position) {
        OrderStatusItem item = items.get(position);
        holder.orderIdView.setText(context.getString(R.string.order_id, String.valueOf(item.getOrderId())));
        holder.amountView.setText(context.getString(R.string.currency_value, String.valueOf(UtilityMethods.getTwoPlaceDecimal(item.getAmount()))));
        holder.createdOnView.setText(item.getCreatedOn());
        holder.venueNameView.setText(item.getVenue().getName());

        holder.statusView.setText(item.getStatus());
        if (item.getStatus().equalsIgnoreCase(MetaData.STATUS.PENDING)) {
            holder.statusView.setBackgroundResource(R.drawable.status_pending_bg);
        } else if (item.getStatus().equalsIgnoreCase(MetaData.STATUS.IN_KITCHEN)) {
            holder.statusView.setBackgroundResource(R.drawable.status_in_kitchen_bg);
        } else if (item.getStatus().equalsIgnoreCase(MetaData.STATUS.SERVED)) {
            holder.statusView.setBackgroundResource(R.drawable.status_served);
        } else {
            holder.statusView.setBackgroundResource(R.drawable.status_cancelled);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
