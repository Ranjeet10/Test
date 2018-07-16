package com.envent.bottlesup.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.envent.bottlesup.R;
import com.envent.bottlesup.mvp.model.TableBar;
import com.envent.bottlesup.mvp.model.server_response.seating_places.SeatingPlace;

import java.util.List;

/**
 * Created by ronem on 4/8/18.
 */

public class SpinnerSeatingPlaceAdapter extends BaseAdapter {
    private List<TableBar> items;
    private LayoutInflater inflater;

    public SpinnerSeatingPlaceAdapter(Context context, List<TableBar> items) {
        this.items = items;
        inflater = LayoutInflater.from(context);
    }

    @Override

    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpinnerSingleItemViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.single_item_table, parent, false);
            holder = new SpinnerSingleItemViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (SpinnerSingleItemViewHolder) convertView.getTag();
        }
        holder.tv.setText(items.get(position).getSeatingPlaceName());

        return convertView;
    }

    class SpinnerSingleItemViewHolder {
        TextView tv;

        SpinnerSingleItemViewHolder(View view) {
            tv = (TextView) view.findViewById(R.id.table_number_text_view);
        }
    }

}
