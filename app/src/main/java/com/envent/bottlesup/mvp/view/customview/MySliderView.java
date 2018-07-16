package com.envent.bottlesup.mvp.view.customview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;

/**
 * Created by ronem on 4/30/18.
 */

public class MySliderView extends BaseSliderView {
    public MySliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.my_slider_view,null);
        ImageView target = (ImageView)v.findViewById(R.id.daimajia_slider_image);
        TextView description = (TextView)v.findViewById(R.id.description);
        description.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}