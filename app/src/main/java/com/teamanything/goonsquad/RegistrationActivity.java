package com.teamanything.goonsquad;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;


public class RegistrationActivity extends ActionBarActivity {

    private static final String PREFS_NAME = "LoginInfo";
    private static final String PREFS_USERS = "users";

    private UserRegistrationTask mAuthTask = null;

    private EditText mUsernameView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mUsernameView = (EditText) findViewById(R.id.email_form);
        mPasswordView = (EditText) findViewById(R.id.password_form);
        mPasswordConfirmView = (EditText) findViewById(R.id.password_confirm_form);

        Button mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });

        Button mCancelButton = (Button) findViewById(R.id.cancel_button);
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void attemptRegistration() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mPasswordConfirmView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordConfirm = mPasswordConfirmView.getText().toString();

        boolean cancel = false;
        View focusView = null;


        // Check for a valid password.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid password confirmation.
        if (TextUtils.isEmpty(passwordConfirm)) {
            mPasswordConfirmView.setError(getString(R.string.error_field_required));
            focusView = mPasswordConfirmView;
            cancel = true;
        } else if (!password.equals(passwordConfirm)) {
            mPasswordConfirmView.setError(getString(R.string.error_unmatched_password));
            focusView = mPasswordConfirmView;
            cancel = true;
        }

        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            mUsernameView.setError(getString(R.string.error_invalid_username));
            focusView = mUsernameView;
            cancel = true;
        } else if (getSharedPreferences(PREFS_NAME, 0).getStringSet(PREFS_USERS, new HashSet<String>()).contains(username)) {
            mUsernameView.setError(getString(R.string.error_taken_username));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserRegistrationTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isUsernameValid(String username) {
        //TODO: Replace this with your own logic
        return username.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 3;
    }

    private void createToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegistrationTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserRegistrationTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SharedPreferences loginInfo = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = loginInfo.edit();
            Set<String> users = loginInfo.getStringSet(PREFS_USERS, new HashSet<String>());
            if (!users.contains(mUsername)) {
                editor.putString(mUsername, mPassword);
                users.add(mUsername);
                editor.putStringSet(PREFS_USERS, users);
                return editor.commit();
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                startActivity(new Intent(RegistrationActivity.this.getBaseContext(), MainActivity.class));
            } else {
                createToast("Registration failed");
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }
}
