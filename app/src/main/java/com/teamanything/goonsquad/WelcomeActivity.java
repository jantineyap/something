package com.teamanything.goonsquad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class WelcomeActivity extends ActionBarActivity {



    /**
     * Creates the view and layout for the Welcome Activity
     * @param savedInstanceState for the created activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //selects XML file for layout of the welcome activity screen
        setContentView(R.layout.activity_welcome);
    }

    /**
     * Opens the activity for logging in
     * @param view for activity
     */
    public void openLoginActivity(View view) {
        //begins the login activity
        startActivity(new Intent(this, LoginActivity.class));
    }

    /**
     * Opens the activity for registration on the welcome screen
     * @param view for activity
     */
    public void openRegistrationActivity(View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }


    /**
     * Places menu items in the action bar
     * @param menu where items are to place into action bar
     * @return boolean true if successful
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    /**
     * Called when user presses action button and determines which
     * item is pressed
     * @param item from the menu that is selected
     * @return boolean true if id matches value declared in the
     * corresponding item's action settings
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
