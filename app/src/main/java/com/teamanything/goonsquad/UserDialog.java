package com.teamanything.goonsquad;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teamanything.goonsquad.database.User;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDialog extends DialogFragment {

    private static final String ARG_EMAIL = "email";
    private static final String ARG_NAME = "name";

    private String mEmail;
    private String mName;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user User
     * @return A new instance of fragment UserDialog.
     */
    public static UserDialog newInstance(User user) {
        UserDialog fragment = new UserDialog();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, user.getEmail());
        args.putString(ARG_NAME, user.getName());
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param email Email
     * @param name Name
     * @return A new instance of fragment UserDialog.
     */
    public static UserDialog newInstance(String email, String name) {
        UserDialog fragment = new UserDialog();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, email);
        args.putString(ARG_NAME, name);
        fragment.setArguments(args);
        return fragment;
    }

    public UserDialog() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(ARG_EMAIL);
            mName = getArguments().getString(ARG_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        ((TextView) view.findViewById(R.id.textView_email)).setText(mEmail);
        ((TextView) view.findViewById(R.id.textView_name)).setText(mName);

        return view;
    }

}