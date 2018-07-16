package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 3/15/18.
 */

public class VenueViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.venue_image_view)
    public ImageView venueImageView;
    @BindView(R.id.venue_name_text_view)
    public TextView venueNameTextView;
    @BindView(R.id.venue_type_text_view)
    public TextView venueTypeTextView;
    @BindView(R.id.venue_location_text_view)
    public TextView venueLocationTextView;
    @BindView(R.id.venue_distance_text_view)
    public TextView venueDistanceTextView;

    public VenueViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
