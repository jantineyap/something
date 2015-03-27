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
 * Customer Adapter for a ListView which contains two TextViews.
 */
public class SaleItemAdapter extends BaseAdapter {

    Context context;
    List<SaleItem> data;
    private static LayoutInflater inflater = null;

    /**
     * Adapter for the saleItem with a list to populate with saleitems
     *
     * @param context, the given context the adapter is called in
     * @param data, the list of saleitems used to populate the list
     * @return boolean of success or not
     */
    public SaleItemAdapter(Context context, List<SaleItem> data) {
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // getters and setters
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

    /**
     * add an item to list for the listview
     *
     * @param saleItem, the item to add
     */
    public void add(SaleItem saleItem) {
        data.add(saleItem);
        this.notifyDataSetChanged();
    }

    /**
     * remove an item from the list for the listview
     *
     * @param saleItem, the item to remove
     */
    public void remove(SaleItem saleItem) {
        data.remove(saleItem);
        this.notifyDataSetChanged();
    }

    /**
     * check if a saleitem is in the list
     *
     * @param saleItem, the item to add
     * @return boolean is true if data contains saleItem
     */
    public boolean contains(SaleItem saleItem) { return data.contains(saleItem); }
}
