package com.envent.bottlesup.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.envent.bottlesup.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ronem on 5/8/18.
 */

public class ConsumptionHistoryDetailItemViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.row_size)
    public TableRow rowSize;
    @BindView(R.id.row_mixer)
    public TableRow rowMixer;
    @BindView(R.id.item)
    public TextView item;
    @BindView(R.id.size)
    public TextView size;
    @BindView(R.id.mixer)
    public TextView mixer;
    @BindView(R.id.qty)
    public TextView qty;
    @BindView(R.id.rate)
    public TextView rate;
    @BindView(R.id.amount)
    public TextView amount;
    @BindView(R.id.note)
    public ImageView note;

    public ConsumptionHistoryDetailItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}