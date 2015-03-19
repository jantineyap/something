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
import com.teamanything.goonsquad.database.User;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendListFragment extends ListFragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String ARG_CUR_USER = "CUR_USER";

    private String curUser;
    private FriendListAdapter adapter;
    private DatabaseHandler db;
    private OnFragmentInteractionListener mListener;

    private EditText etEmail;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sectionNumber the section number
     * @param curUser the current user
     * @return A new instance of fragment FriendListFragment.
     */
    public static FriendListFragment newInstance(int sectionNumber, String curUser) {
        FriendListFragment fragment = new FriendListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        args.putString(ARG_CUR_USER, curUser);
        fragment.setArguments(args);
        return fragment;
    }

    public FriendListFragment() {
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
            curUser = getArguments().getString(ARG_CUR_USER);
        }

        db = DatabaseHandler.getInstance(getActivity());

        adapter = new FriendListAdapter(getActivity(), db.getFriends(curUser));
        setListAdapter(adapter);
    }

    public void onListItemClick(ListView l, View v, int position, long id) {
        // do something with the data
        super.onListItemClick(l, v, position, id);
        // Get the item that was clicked

        mListener.showUserDialog(db.getUser(l.getItemAtPosition(position).toString()));

        etEmail.setText(l.getItemAtPosition(position).toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_list, container, false);

        view.findViewById(R.id.button_add).setOnClickListener(this);
        view.findViewById(R.id.button_remove).setOnClickListener(this);

        etEmail = (EditText) view.findViewById(R.id.editText_email);

        final View vb = view.findViewById(R.id.button_add);

        etEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
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
        if (id == R.id.button_add) {
            // add
            String email = etEmail.getText().toString();
            if (mListener.onAddClick(email)) {
                add(email);

                // clear and deselect EditText
                etEmail.setText("");
                etEmail.clearFocus();
            }
        } else if (id == R.id.button_remove) {
            // remove
            String email = etEmail.getText().toString();
            if (mListener.onRemoveClick(email)) {
                remove(email);

                // clear and deselect EditText
                etEmail.setText("");

                etEmail.clearFocus();
            }
        }

        // hides soft input (keyboard)
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etEmail.getWindowToken(), 0);
    }

    // returns true if all fields are error free
    private boolean checkError() {
        etEmail.setError(null);

        String email = etEmail.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        }
        return !cancel;
    }

    public void add(String email) {
        if (!adapter.contains(email)) {
            adapter.add(email);
        }
    }

    public void remove(String email) {
        if (adapter.contains(email)) {
            adapter.remove(email);
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
        public boolean onAddClick(String email);
        public boolean onRemoveClick(String email);
        public void showUserDialog(User user);
    }
}
