package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 3/22/18.
 */

public class CartItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cart_item_name)
    public TextView cartItemNameTextView;
    @BindView(R.id.cart_item_mixer)
    public TextView cartItemMixerView;
    @BindView(R.id.edt_quantity)
    public TextView quantityTextView;
    @BindView(R.id.btn_minus)
    public TextView btnMinus;
    @BindView(R.id.btn_plus)
    public TextView btnPlus;
    @BindView(R.id.total_price)
    public TextView totalPrice;
    @BindView(R.id.delete)
    public ImageView delete;
    @BindView(R.id.cart_notes)
    public ImageView cartNotes;

    public CartItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
