package com.envent.bottlesup.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.mycart.DrinkCart;
import com.envent.bottlesup.mvp.model.mycart.FoodCart;
import com.envent.bottlesup.mvp.view.customview.MyDialog;
import com.envent.bottlesup.utils.UtilityMethods;
import com.envent.bottlesup.viewholder.CartItemViewHolder;

import java.util.List;

/**
 * Created by ronem on 3/22/18.
 */

public class CartItemAdapter extends RecyclerView.Adapter<CartItemViewHolder> {
    private List<Object> objects;
    private Context context;
    private LayoutInflater inflater;

    private LongPressListener listener;

    public void setListener(LongPressListener listener) {
        this.listener = listener;
    }

    public CartItemAdapter(List<Object> objects, Context context) {
        this.objects = objects;
        this.context = context;
        inflater = LayoutInflater.from(this.context);
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.single_row_cart, parent, false);
        return new CartItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartItemViewHolder holder, final int position) {
        final Object o = objects.get(position);
        if (o instanceof FoodCart) {
            FoodCart fc = (FoodCart) o;
            holder.cartItemNameTextView.setText(fc.getName());
            updateFoodItemView(holder, fc);
            holder.cartItemMixerView.setVisibility(View.GONE);
        } else if (o instanceof DrinkCart) {
            DrinkCart dc = (DrinkCart) o;
            holder.cartItemNameTextView.setText(dc.getName());
            holder.cartItemMixerView.setVisibility(View.VISIBLE);
            String none = " ( " + dc.getDrinkOption() + " ) ";
            String withMix = " ( " + dc.getDrinkOption() + ", " + dc.getMixture() + " ) ";
            String mx = dc.getMixture().equalsIgnoreCase("none") ? none : withMix;
            holder.cartItemMixerView.setText(mx);
            updateDrinkItemView(holder, dc);
        }

        //implementing long pressed click
        holder.itemView.setOnLongClickListener(v -> {
            listener.onLongPressed(position);
            return true;
        });

        holder.btnMinus.setOnClickListener(v -> changeQuantity(holder, o, true));

        holder.btnPlus.setOnClickListener(v -> changeQuantity(holder, o, false));

        holder.delete.setOnClickListener(v -> {
            if (o instanceof FoodCart) {
                FoodCart fc = (FoodCart) o;
                FoodCart.deleteCart(fc.getCategoryId(), fc.getItemId(), fc.getRemark());
            } else if (o instanceof DrinkCart) {
                DrinkCart dc = (DrinkCart) o;
                DrinkCart.deleteCart(dc.getCategoryId(), dc.getItemId(), dc.getDrinkOptionId(), dc.getMixtureId(), dc.getRemark());
            }
            listener.onDeleteClicked(position);
        });

        holder.cartNotes.setOnClickListener(v -> {
            String specialRequest = "";
            if (o instanceof FoodCart) {
                FoodCart fc = (FoodCart) o;
                specialRequest = fc.getRemark();
            } else if (o instanceof DrinkCart) {
                DrinkCart dc = (DrinkCart) o;
                specialRequest = dc.getRemark();
            }

            String preMsg = context.getString(R.string.special_request, specialRequest);
            AlertDialog.Builder builder = new MyDialog(context).getDialogBuilder(preMsg);
            builder.setCancelable(false);
            builder.setPositiveButton("Close", ((dialog, which) -> dialog.dismiss()));
            builder.create().show();
        });
    }

    private void changeQuantity(CartItemViewHolder holder, Object o, boolean decrease) {
        int quantity;

        if (o instanceof FoodCart) {
            FoodCart f = (FoodCart) o;

            if (decrease) {
                quantity = UtilityMethods.decreaseQuantity(String.valueOf(f.getQuantity()));
            } else {
                quantity = UtilityMethods.increaseQuantity(String.valueOf(f.getQuantity()));
            }
            f.setQuantity(quantity);
            f.save();

            updateFoodItemView(holder, f);

        } else if (o instanceof DrinkCart) {
            DrinkCart d = (DrinkCart) o;
            if (decrease) {
                quantity = UtilityMethods.decreaseQuantity(String.valueOf(d.getQuantity()));
            } else {
                quantity = UtilityMethods.increaseQuantity(String.valueOf(d.getQuantity()));
            }
            d.setQuantity(quantity);
            d.save();
            updateDrinkItemView(holder, d);
        }

        listener.updateAmount();
    }

    private void updateDrinkItemView(CartItemViewHolder holder, DrinkCart d) {
        int qt = d.getQuantity();
        holder.quantityTextView.setText(String.valueOf(qt));
        double price = qt * d.getBottlesUpPrice();
        holder.totalPrice.setText(context.getResources().getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(price)));

        String sr = d.getRemark();
        if (TextUtils.isEmpty(sr)) {
            holder.cartNotes.setVisibility(View.GONE);
        } else {
            holder.cartNotes.setVisibility(View.VISIBLE);
        }
    }

    private void updateFoodItemView(CartItemViewHolder holder, FoodCart f) {
        int qt = f.getQuantity();
        holder.quantityTextView.setText(String.valueOf(qt));
        double price = qt * f.getBottlesUpPrice();
        holder.totalPrice.setText(context.getResources().getString(R.string.total_value, UtilityMethods.getTwoPlaceDecimal(price)));
        String sr = f.getRemark();
        if (TextUtils.isEmpty(sr)) {
            holder.cartNotes.setVisibility(View.GONE);
        } else {
            holder.cartNotes.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public int getItemCount() {
        return objects.size();
    }

    public interface LongPressListener {
        void onLongPressed(int position);

        void updateAmount();

        void onDeleteClicked(int position);
    }
}
