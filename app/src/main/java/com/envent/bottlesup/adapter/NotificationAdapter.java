package com.envent.bottlesup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.notification_response.ItemsItem;
import com.envent.bottlesup.utils.UtilityMethods;
import com.envent.bottlesup.viewholder.MyNotificationViewHolder;

import java.util.List;

/**
 * Created by ronem on 4/20/18.
 */

public class NotificationAdapter extends RecyclerView.Adapter<MyNotificationViewHolder> {
    private List<ItemsItem> notifications;
    private LayoutInflater inflater;
    private String SPLIT_EXPRESSION = " - ";

    public NotificationAdapter(Context context, List<ItemsItem> notifications) {
        this.notifications = notifications;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.single_item_notification, parent, false);
        return new MyNotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyNotificationViewHolder holder, int position) {
        ItemsItem n = notifications.get(position);

        if (n.getCreatedOn().contains(SPLIT_EXPRESSION)) {
            String[] date = n.getCreatedOn().split(SPLIT_EXPRESSION);
            holder.createdOn.setText(date[0]);
        } else {
            holder.createdOn.setText(n.getCreatedOn());
        }

        holder.notificationTitle.setText(UtilityMethods.captializeAllFirstLetter(n.getFrom()));
        holder.notificationBody.setText(n.getTitle());

    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }
}
