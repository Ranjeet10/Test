package com.envent.bottlesup.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.consumption_history_detail_response.ItemsItem;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.utils.UtilityMethods;
import com.envent.bottlesup.viewholder.ConsumptionHistoryDetailItemViewHolder;

import java.util.List;

/**
 * Created by ronem on 5/8/18.
 */

public class ConsumptionHistoryDetailItemAdapter extends RecyclerView.Adapter<ConsumptionHistoryDetailItemViewHolder> {
    private List<ItemsItem> orderItemsList;
    private LayoutInflater inflater;
    private Context context;

    public ConsumptionHistoryDetailItemAdapter(List<ItemsItem> items, Context context) {
        this.orderItemsList = items;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Nullable
    @Override
    public ConsumptionHistoryDetailItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_row_ordered_layout, parent, false);
        return new ConsumptionHistoryDetailItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsumptionHistoryDetailItemViewHolder holder, int position) {
        holder.item.setText(orderItemsList.get(position).getName());

        ItemsItem items = orderItemsList.get(position);

        holder.rate.setText(context.getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(items.getRate())));
        holder.qty.setText(String.valueOf(items.getQuantity()));
        holder.amount.setText(context.getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(items.getTotal())));
        if (!TextUtils.isEmpty(orderItemsList.get(position).getRemarks())) {
            holder.note.setColorFilter(ContextCompat.getColor(context, R.color.blue));
        } else {
            holder.note.setColorFilter(ContextCompat.getColor(context, R.color.light_white_50));
        }


        String servingType = items.getServingType();
        String mixer = items.getMixer();
        if (TextUtils.isEmpty(servingType)) {
            holder.rowSize.setVisibility(View.GONE);
        } else {
            holder.rowSize.setVisibility(View.VISIBLE);
            holder.size.setText(servingType);
        }

        if (TextUtils.isEmpty(mixer)) {
            holder.rowMixer.setVisibility(View.GONE);
        } else {
            holder.rowMixer.setVisibility(View.VISIBLE);
            holder.mixer.setText(mixer);
        }

        holder.note.setOnClickListener((v) -> {
            String specialRequest = items.getRemarks();
            if (!TextUtils.isEmpty(specialRequest)) {
                AlertDialog.Builder builder = new MyDialog(context).getDialogBuilder(context.getString(R.string.special_request, specialRequest));
                builder.setCancelable(true);
                builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());
                builder.create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }
}
