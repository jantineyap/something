package com.teamanything.goonsquad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.teamanything.goonsquad.database.WishListItem;

import java.text.NumberFormat;
import java.util.List;

/**
 * Created by Adam on 3/13/2015.
 *
 * Customer Adapter for a ListView which contains two TextViews.
 */
public class WishListItemAdapter extends BaseAdapter {

    Context context;
    List<WishListItem> data;
    private static LayoutInflater inflater = null;

    public WishListItemAdapter(Context context, List<WishListItem> data) {
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
    public WishListItem getItem(int position) {
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
            vi = inflater.inflate(R.layout.row_wish_list_item, parent, false);

        WishListItem wishListItem = data.get(position);

        TextView item = (TextView) vi.findViewById(R.id.textView_Item);
        TextView price = (TextView) vi.findViewById(R.id.textView_Price);

        item.setText(data.get(position).getItem());
        price.setText(NumberFormat.getCurrencyInstance().format(wishListItem.getPrice()));
        return vi;
    }

    public void add(WishListItem wishListItem) {
        data.add(wishListItem);
        this.notifyDataSetChanged();
    }

    public void remove(WishListItem wishListItem) {
        data.remove(wishListItem);
        this.notifyDataSetChanged();
    }
}
