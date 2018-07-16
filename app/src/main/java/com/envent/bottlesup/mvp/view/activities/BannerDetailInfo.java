package com.envent.bottlesup.mvp.view.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.event_response.EventData;
import com.envent.bottlesup.mvp.model.server_response.venue_list.AdditionalImagesItem;
import com.envent.bottlesup.mvp.view.activities.base_activity.SimpleBaseActivity;
import com.envent.bottlesup.utils.MetaData;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ronem on 3/30/18.
 */

public class BannerDetailInfo extends SimpleBaseActivity {

    @BindString(R.string.event_detail)
    String toolbarTitle;

    @BindView(R.id.banner_slider)
    SliderLayout slider;

    @BindView(R.id.venue_name)
    TextView venueName;
    @BindView(R.id.event_name)
    TextView eventName;
    @BindView(R.id.event_address)
    TextView eventAddress;
    @BindView(R.id.event_start)
    TextView eventStart;
    @BindView(R.id.event_end)
    TextView eventEnd;

    @BindView(R.id.event_detail)
    TextView eventDetail;

    private Unbinder unbinder;
    private EventData event;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banner_detail_layout);
        unbinder = ButterKnife.bind(this);

        getIntentData();
    }

    private void getIntentData() {
        event = (EventData) getIntent().getExtras().getSerializable(MetaData.KEY.EVENT);
        venueName.setText(event.getVenue().getName());
        eventName.setText(event.getName());
        eventStart.setText(event.getStartsFrom());
        eventEnd.setText(event.getEnds());
        eventAddress.setText(event.getVenue().getAddress());
        eventDetail.setText(event.getDetails());

        setUpSlider();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getToolbarTitleTextView().setText(toolbarTitle);
    }

    private void setUpSlider() {

        List<AdditionalImagesItem> banners = new ArrayList<>();

        //adding the previously shown banner to the detail's slider banner as well
        AdditionalImagesItem prevBannerImage = new AdditionalImagesItem();
        prevBannerImage.setImage(event.getBannerImage());
        banners.add(prevBannerImage);

        //adding the addition images if present to the slider banner
        banners.addAll(event.getAdditionalImages());

        if (banners.size() > 0) {
            for (int i = 0; i < banners.size(); i++) {
                //show only image
                DefaultSliderView textSliderView = new DefaultSliderView(this);
                textSliderView
                        .image(banners.get(i).getImage())
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop);

                slider.addSlider(textSliderView);
                slider.setCustomAnimation(null);
                slider.setPresetTransformer(SliderLayout.Transformer.Default);
            }
            slider.setDuration(4000);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void updateBalance() {

    }
}
