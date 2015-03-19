package com.teamanything.goonsquad;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.WishListItem;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WishListFragment extends ListFragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CUR_USER = "CUR_USER";
    private String curUser;

    private OnFragmentInteractionListener mListener;

    private WishListItemAdapter adapter;

    private EditText etItem;
    private EditText etPrice;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber the section number
     * @param curUser the current user
     * @return A new instance of fragment WishListFragment.
     */
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
     * @param savedInstanceState for the onCreate
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            curUser = getArguments().getString(CUR_USER);
        }

        DatabaseHandler db = DatabaseHandler.getInstance(getActivity());

        adapter = new WishListItemAdapter(getActivity(), db.getWishlist(curUser));
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
        View view = inflater.inflate(R.layout.fragment_wish_list, container, false);

        view.findViewById(R.id.button_add).setOnClickListener(this);
        view.findViewById(R.id.button_remove).setOnClickListener(this);

        etItem = (EditText) view.findViewById(R.id.editText_Item);
        etPrice = (EditText) view.findViewById(R.id.editText_Price);

        final View vb = view.findViewById(R.id.button_add);

        etPrice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    onClick(vb);
                    return true;
                }
                return false;
            }
        });

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
        if (!checkError()) { return; }
        Double price = 0.0;
        if (!etPrice.getText().toString().equals("")) {
            price = Double.parseDouble(etPrice.getText().toString());
        }
        WishListItem wishListItem = new WishListItem(etItem.getText().toString(), price);
        if (id == R.id.button_add) {
            // add
            if (mListener.onAddClick(wishListItem)) {
                add(wishListItem);

                // clear EditTexts
                etItem.setText("");
                etPrice.setText("");

                // deselects EditTexts
                etItem.clearFocus();
                etPrice.clearFocus();
            }
        } else if (id == R.id.button_remove) {
            // remove
            if (mListener.onRemoveClick(wishListItem)) {
                remove(wishListItem);

                // clear and deselect EditTexts
                etItem.setText("");
                etPrice.setText("");
                etItem.clearFocus();
                etPrice.clearFocus();
            }
        }

        // hides soft input (keyboard)
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPrice.getWindowToken(), 0);
    }

    // returns true if all fields are error free
    private boolean checkError() {
        etItem.setError(null);
        etPrice.setError(null);

        String item = etItem.getText().toString();
        String price = etPrice.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(item)) {
            etItem.setError(getString(R.string.error_field_required));
            focusView = etItem;
            cancel = true;
        }
        if (TextUtils.isEmpty(price)) {
            etPrice.setError(getString(R.string.error_field_required));
            focusView = etPrice;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        return !cancel;
    }

    private void add(WishListItem wishListItem) {
        if (!adapter.contains(wishListItem)) {
            adapter.add(wishListItem);
        }
    }

    private void remove(WishListItem wishListItem) {
        if (adapter.contains(wishListItem)) {
            adapter.remove(wishListItem);
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
        public boolean onAddClick(WishListItem wishListItem);
        public boolean onRemoveClick(WishListItem wishListItem);
    }
}
