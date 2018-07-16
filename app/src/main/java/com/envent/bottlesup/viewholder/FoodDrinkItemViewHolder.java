package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 3/19/18.
 */

public class FoodDrinkItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.food_drink_name_text_view)
    public TextView foodDrinkNameTextView;
    @BindView(R.id.food_drink_price_text_view)
    public TextView foodDrinkPriceTextView;

    public FoodDrinkItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
