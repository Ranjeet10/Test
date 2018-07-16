package com.envent.bottlesup.mvp.presenter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.MVPView;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.MixersItem;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.ServingTypesItem;

import java.util.List;

/**
 * Created by ronem on 4/1/18.
 */

public class DetailDrinkPresenterImpl implements MyPresenter.DetailDrinkPresenter {
    private MVPView.DetailDrinkView view;

    @Override
    public void addView(MVPView.DetailDrinkView view) {
        this.view = view;
    }

    @Override
    public void attachDrinkOptionRadioButton(Context context, List<ServingTypesItem> drinkOptions) {

        for (int i = 0; i < drinkOptions.size(); i++) {
            ServingTypesItem d = drinkOptions.get(i);
            Log.i("RadioIds:", d.getId() + "::" + d.getName());

            RadioButton rb = (RadioButton) LayoutInflater.from(context).inflate(R.layout.radio_button, null);
            rb.setTag(d.getId());
            rb.setText(d.getName());
            if (i == 0) {
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                params.leftMargin = 0;
                rb.setLayoutParams(params);
            } else {
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                params.leftMargin = 20;
                rb.setLayoutParams(params);
            }

            view.onDrinkOptionRadioButtonCreated(rb);

            if (i == drinkOptions.size() - 1) {
                view.onInflatedAllServingTypes();
            }
        }
    }

    @Override
    public void attachMixtureRadioButton(Context context, List<MixersItem> drinkMixtures) {

        for (int i = 0; i < drinkMixtures.size(); i++) {
            MixersItem d = drinkMixtures.get(i);
            Log.i("RadioIds:", d.getId() + "::" + d.getName());
            RadioButton rb = (RadioButton) LayoutInflater.from(context).inflate(R.layout.radio_button, null);
            rb.setTag(d.getId());
            rb.setText(d.getName());
            if (i == 0) {
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                params.leftMargin = 0;
                rb.setLayoutParams(params);
            } else {
                RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);

                params.leftMargin = 20;
                rb.setLayoutParams(params);
            }

            view.onDrinkMixtureRadioButtonCreated(rb);
            if (i == drinkMixtures.size() - 1) {
                view.onInflatedAllMixtures();
            }
        }
    }


}
