package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.envent.bottlesup.R;
import com.daimajia.slider.library.SliderLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 4/29/18.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.feature_event_slider_header)
    public SliderLayout slider;
    @BindView(R.id.static_banner_image)
    public ImageView staticBannerImageView;
    public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
