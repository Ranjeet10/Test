package com.envent.bottlesup.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.server_response.drink_item_response.DrinkItemsItem;
import com.envent.bottlesup.viewholder.FoodDrinkItemViewHolder;

import java.util.List;

/**
 * Created by ronem on 3/19/18.
 */

public class DrinkItemAdapter extends RecyclerView.Adapter<FoodDrinkItemViewHolder> {
    private List<DrinkItemsItem> items;
    private Context context;
    private LayoutInflater inflater;

    public DrinkItemAdapter(List<DrinkItemsItem> items, Context context) {
        this.items = items;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @Override
    public FoodDrinkItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.single_row_food_and_drink, parent, false);
        return new FoodDrinkItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FoodDrinkItemViewHolder holder, int position) {
        DrinkItemsItem d = items.get(position);
        holder.foodDrinkNameTextView.setText(d.getItemName());
        if (d.getServingTypes().size() > 0) {
            double roundOff = Math.round(d.getServingTypes().get(0).getBottlesUpPrice());
            int menuPrice = (int) roundOff;
            holder.foodDrinkPriceTextView.setText(
                    context.getResources()
                            .getString(R.string.item_price_text_view, String.valueOf(menuPrice)));
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
