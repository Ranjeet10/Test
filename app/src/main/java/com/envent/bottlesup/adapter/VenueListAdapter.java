package com.envent.bottlesup.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventData;
import com.envent.bottlesup.mvp.model.server_response.venue_list.VenueDataItem;
import com.envent.bottlesup.mvp.view.activities.BannerDetailInfo;
import com.envent.bottlesup.mvp.view.customview.MySliderView;
import com.envent.bottlesup.utils.MetaData;
import com.envent.bottlesup.viewholder.HeaderViewHolder;
import com.envent.bottlesup.viewholder.VenueViewHolder;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ronem on 3/15/18.
 */

public class VenueListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<VenueDataItem> venues;
    private LayoutInflater inflater;
    private Context context;
    private int TYPE_HEADER = 0;
    private int TYPE_ITEM = 1;
    private List<EventData> events;


    public VenueListAdapter(Context context, List<VenueDataItem> venues) {
        this.context = context;
        this.venues = venues;
        inflater = LayoutInflater.from(this.context);
        events = MetaData.APP_LEVEL_CACHE.EVENTS.getData();
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    @Override

    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == TYPE_HEADER) {
            itemView = inflater.inflate(R.layout.recycler_view_header, parent, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == TYPE_ITEM) {
            itemView = inflater.inflate(R.layout.single_row_venue, parent, false);
            return new VenueViewHolder(itemView);
        }
        throw new RuntimeException("there were no view to inflate");

    }


    @Override

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof VenueViewHolder) {
            VenueViewHolder vHolder = (VenueViewHolder) holder;
            VenueDataItem v = venues.get(position - 1);
            vHolder.venueNameTextView.setText(v.getName());
            Picasso.with(context)
                    .load(v.getImage())
                    .placeholder(R.drawable.placeholder)
                    .into(vHolder.venueImageView);
            vHolder.venueTypeTextView.setText(v.getOutletType());
            vHolder.venueLocationTextView.setText(v.getAddress());
            vHolder.venueDistanceTextView.setText(context.getResources().getString(R.string.venue_distance, v.getDistance()));

        } else {
            HeaderViewHolder hHolder = (HeaderViewHolder) holder;

            if (events == null) {
                hHolder.slider.setVisibility(View.GONE);
                hHolder.staticBannerImageView.setVisibility(View.VISIBLE);
                return;
            }
            if (events != null && events.size() > 0) {
                for (int i = 0; i < events.size(); i++) {
                    MySliderView textSliderView = new MySliderView(context);
                    EventData eventData = events.get(i);
                    String eventTitle = eventData.getName();
                    textSliderView
                            .description(eventTitle)
                            .image(eventData.getBannerImage())
                            .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                    //set bundle to the slider items
                    Bundle box = new Bundle();
                    box.putSerializable(MetaData.KEY.EVENT, eventData);
                    textSliderView.bundle(box);

                    //invoked when user clicks on item
                    textSliderView.setOnSliderClickListener(slider -> {
                        Intent detailIntent = new Intent(context, BannerDetailInfo.class);
                        detailIntent.putExtras(slider.getBundle());
                        context.startActivity(detailIntent);

                    });

                    hHolder.slider.addSlider(textSliderView);
                    hHolder.slider.setCustomAnimation(null);
                    hHolder.slider.setPresetTransformer(SliderLayout.Transformer.Default);
                }
                hHolder.slider.setDuration(4000);
            }
        }
    }

    @Override
    public int getItemCount() {
        return venues.size() + 1;
    }
}
