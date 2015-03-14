package com.teamanything.goonsquad;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.SaleItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SalesReportFragment extends ListFragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CUR_USER = "CUR_USER";
    private int sectionNum;
    private String curUser;
    private List<SaleItem> saleItems;
    private SaleItemAdapter adapter;
    private DatabaseHandler db;
    private OnFragmentInteractionListener mListener;

    private EditText etSalesItem;
    private EditText etPrice;
    private EditText etLocation;

    private Button bAdd;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber the section number
     * @param curUser the current user
     * @return A new instance of fragment WishListFragment.
     */
    public static SalesReportFragment newInstance(int sectionNumber, String curUser) {
        SalesReportFragment fragment = new SalesReportFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(CUR_USER, curUser);
        fragment.setArguments(args);
        return fragment;
    }

    public SalesReportFragment() {
        // Required empty public constructor
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param savedInstanceState for the oncreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sectionNum = getArguments().getInt(ARG_SECTION_NUMBER);
            curUser = getArguments().getString(CUR_USER);
        }

        db = DatabaseHandler.getInstance(getActivity());

        saleItems = db.getSaleList();
        adapter = new SaleItemAdapter(getActivity(), saleItems);
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        super.onListItemClick(l, v, position, id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales_report, container, false);

        etSalesItem = (EditText) view.findViewById(R.id.editText_Item);
        etPrice = (EditText) view.findViewById(R.id.editText_Price);
        etLocation = (EditText) view.findViewById(R.id.editText_Location);

        bAdd = (Button) view.findViewById(R.id.button_add);
        bAdd.setOnClickListener(this);
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
            Double price;
            if (!etPrice.getText().toString().equals("")) {
                price = Double.parseDouble(etPrice.getText().toString());
            } else {
                price = 0.0;
            }
            SaleItem saleItem = new SaleItem(etSalesItem.getText().toString(), price, etLocation.getText().toString());
            if (mListener.onAddSaleItemClick(saleItem)) {
                addToList(saleItem);

                // clear and deselect EditTexts
                etSalesItem.setText("");
                etLocation.setText("");
                etPrice.setText("");
                etSalesItem.clearFocus();
                etLocation.clearFocus();
                etPrice.clearFocus();
            }


            // hides soft input (keyboard)
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPrice.getWindowToken(), 0);
        }
    }

    private void addToList(SaleItem saleItem) {
        if (!saleItems.contains(saleItem)) {
            adapter.add(saleItem);
        }
    }

    private void removeFromList(SaleItem saleItem) {
        if (!saleItems.contains(saleItem)) {
            adapter.remove(saleItem);
        }
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
        public boolean onAddSaleItemClick(SaleItem saleItem);
    }
}
