package com.teamanything.goonsquad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teamanything.goonsquad.database.SaleItem;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Adam on 3/13/2015.
 *
 * Customer Adapter for a ListView which contains three TextViews.
 */
public class SaleItemAdapter extends BaseAdapter {

    Context context;
    List<SaleItem> data;
    private static LayoutInflater inflater = null;

    public SaleItemAdapter(Context context, List<SaleItem> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public SaleItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.row_sale_item, parent, false);

        SaleItem saleItem = data.get(position);

        TextView item = (TextView) vi.findViewById(R.id.textView_Item);
        TextView price = (TextView) vi.findViewById(R.id.textView_Price);

        item.setText(saleItem.getItem());
        price.setText(NumberFormat.getCurrencyInstance().format(saleItem.getPrice()));
        return vi;
    }

    public void add(SaleItem saleItem) {
        data.add(saleItem);
        this.notifyDataSetChanged();
    }

    public void remove(SaleItem saleItem) {
        data.remove(saleItem);
        this.notifyDataSetChanged();
    }

    public boolean contains(SaleItem saleItem) { return data.contains(saleItem); }
}
