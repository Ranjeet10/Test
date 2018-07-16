package com.envent.bottlesup.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventData;
import com.envent.bottlesup.viewholder.EventViewHolder;

import java.util.List;

/**
 * Created by ronem on 5/20/18.
 */

public class EventAdapter extends RecyclerView.Adapter<EventViewHolder> {
    private List<EventData> events;
    private LayoutInflater inflater;
    private Context context;

    public EventAdapter(List<EventData> events, Context context) {
        this.events = events;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.single_item_events, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventData e = events.get(position);
        holder.eventName.setText(e.getName());
        holder.eventTime.setText(e.getStartsFrom());
    }

    @Override
    public int getItemCount() {
        return events.size();
    }
}
