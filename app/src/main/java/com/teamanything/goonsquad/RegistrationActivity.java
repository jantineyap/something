package com.teamanything.goonsquad;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.User;


public class RegistrationActivity extends ActionBarActivity {

    private static String CUR_USER;

    private UserRegistrationTask mAuthTask = null;
    private DatabaseHandler db;

    private EditText mEmailView;
    private EditText mNameView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mEmailView = (EditText) findViewById(R.id.email_form);
        mNameView = (EditText) findViewById(R.id.name_form);
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

        db = DatabaseHandler.getInstance(getApplicationContext());
    }

    public void attemptRegistration() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mNameView.setError(null);
        mPasswordView.setError(null);
        mPasswordConfirmView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String name = mNameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordConfirm = mPasswordConfirmView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        //checks for name input
        if (TextUtils.isEmpty(name)) {
            mNameView.setError(getString(R.string.error_field_required));
            focusView = mNameView;
            cancel = true;
        }

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

        // Check for a valid email.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        } else if (db.userRegistered(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mAuthTask = new UserRegistrationTask(email, name, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.length() > 3;
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

        private final String mEmail;
        private final String mPassword;
        private final String mName;
        private User user;

        UserRegistrationTask(String email, String name, String password) {
            mEmail = email;
            mPassword = password;
            mName = name;
            user = new User(mEmail, mName, mPassword);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if (!db.userRegistered(mEmail)) {
                return db.addUser(user);
            }
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (success) {
                CUR_USER = mEmail;
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
