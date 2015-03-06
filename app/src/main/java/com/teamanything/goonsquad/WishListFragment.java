package com.teamanything.goonsquad;

import android.app.Activity;
import android.support.v4.app.ListFragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.teamanything.goonsquad.R;

import java.util.ArrayList;
import java.util.List;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.Item;
import com.teamanything.goonsquad.database.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishListFragment extends ListFragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CUR_USER = "CUR_USER";
    // TODO: Rename and change types of parameters
    private int sectionNum;
    private String curUser;
    private List<String> items;
    private ArrayAdapter<String> adapter;
    private DatabaseHandler db;
    private OnFragmentInteractionListener mListener;
    private List<Item> holder;

    private EditText etItem;
    private EditText etPrice;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber
     * @param curUser
     * @return A new instance of fragment WishListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WishListFragment newInstance(int sectionNumber, String curUser) {
        WishListFragment fragment = new WishListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(CUR_USER, curUser);
        fragment.setArguments(args);
        return fragment;
    }

    public WishListFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param savedInstanceState for the oncreate
     * @return A new instance of fragment WishListFragment.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionNum = getArguments().getInt(ARG_SECTION_NUMBER);
            curUser = getArguments().getString(CUR_USER);
        }

        db = DatabaseHandler.getInstance(getActivity());

        holder = db.getWishlist(curUser);
        List<String> temp = new ArrayList<>();
        for (Item i : holder) {
            temp.add(i.getItem() + "     " + i.getPrice());
        }
        items = temp;

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, items);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        super.onListItemClick(l, v, position, id);
        // Get the item that was clicked
        String set = l.getItemAtPosition(position).toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        view.findViewById(R.id.button_add).setOnClickListener(this);
        view.findViewById(R.id.button_remove).setOnClickListener(this);

        etItem = (EditText) view.findViewById(R.id.editText_Item);
        etPrice = (EditText) view.findViewById(R.id.editText_Price);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.button_add) {
            // add
            String item = etItem.getText().toString();
            Double price;
            if (!etPrice.getText().toString().equals("")) {
                price = Double.parseDouble(etPrice.getText().toString());
            } else {
                price = 0.0;
            }

            if (mListener.onAddItemClick(item, price)) {
                addToList(item, price);
            }
        } else if (id == R.id.button_remove) {
            // remove
            String item = etItem.getText().toString();
            if (mListener.onRemoveItemClick(item)) {
                removeFromList(item);
            }
        }
    }

    public void addToList(String item, Double price) {
        if (!items.contains(item + "     " + price)) {
            items.add(item + "     " + price);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this.getActivity(), "Already Added", Toast.LENGTH_SHORT).show();
        }
    }

    public void removeFromList(String item) {
        double pTemp = 0;
        for (Item x : holder) {
            if (x.getItem().equals(item)) {
                pTemp = x.getPrice();
            }
        }
        items.remove(item + "     " + pTemp);
        adapter.notifyDataSetChanged();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public boolean onAddItemClick(String item, Double price);
        public boolean onRemoveItemClick(String item);
    }
}
