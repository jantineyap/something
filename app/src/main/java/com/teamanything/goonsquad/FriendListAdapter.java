package com.teamanything.goonsquad;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Adam on 3/13/2015.
 *
 * Customer Adapter for a ListView which contains one TextView.
 */
class FriendListAdapter extends BaseAdapter {

    private final List<String> data;
    private static LayoutInflater inflater = null;

    public FriendListAdapter(Context context, List<String> data) {
        Context context1 = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
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
            vi = inflater.inflate(R.layout.row_friend_item, parent, false);

        String friend = data.get(position);

        TextView item = (TextView) vi.findViewById(R.id.textView_Friend);

        item.setText(friend);
        return vi;
    }

    public void add(String friend) {
        data.add(friend);
        this.notifyDataSetChanged();
    }

    public void remove(String friend) {
        data.remove(friend);
        this.notifyDataSetChanged();
    }

    public boolean contains(String friend) { return data.contains(friend); }
}
