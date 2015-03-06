package com.teamanything.goonsquad;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.User;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, FriendListFragment.OnFragmentInteractionListener, WishListFragment.OnFragmentInteractionListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private Fragment mFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static final String ARG_CUR_USER = "CUR_USER";
    private String curUser;

    private DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        mFragment = new PlaceholderFragment().newInstance(0);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            curUser = extras.getString(ARG_CUR_USER);
        }
        if (curUser == null) {
            Log.e("MainActivity", "curUser is null");
        }

        //Generate user list in console
        db = DatabaseHandler.getInstance(getApplicationContext());

        List<User> userList = db.getAllUsers();
        for (User x : userList) {
            Log.i("List users", x.getEmail());
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        mFragment = new ListFragment();
        switch (position + 1) {
            case 1:
                mFragment = PlaceholderFragment.newInstance(position + 1);
                break;
            case 2:
                mFragment = WishListFragment.newInstance(position + 1, curUser);
                break;
            case 3:
                mFragment = FriendListFragment.newInstance(position + 1, curUser);
                break;
            default:
                break;
        }

        if (mFragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (mFragment instanceof FriendListFragment) {
                fragmentManager.beginTransaction().replace(R.id.container, mFragment, "friend").commit();
            } else {
                fragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
            }
            // update selected item and title, then close the drawer
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }


    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_friends);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_activity, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_logout:
                logout();
                return true;
            case R.id.action_settings:
                //Toast.makeText(this, "Settings not implemented", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about:
                Toast.makeText(this, "GOONSQUAD!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        //TODO implement logout
        finish();
    }

    @Override
    public void showUserDialog(User user) {
        UserDialog userDialog = UserDialog.newInstance(user);
        userDialog.show(getSupportFragmentManager(), "fragment_user_dialog");
    }

    @Override
    public boolean onAddFriendClick(String email) {
        if (db.addConnection(curUser, email)) {
            return true;
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public boolean onRemoveFriendClick(String email) {
        if (db.deleteConnection(curUser, email)) {
            return true;
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public boolean onAddItemClick(String item, Double price) {
        if (db.addWish(curUser, item, price)) {
            return true;
        } else {
            Toast.makeText(this, "Could Not Add Item", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public boolean onRemoveItemClick(String item) {
        if (db.deleteWish(curUser, item)) {
            return true;
        } else {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity, container, false);
            View tv = rootView.findViewById(R.id.section_label);
            int section = getArguments().getInt(ARG_SECTION_NUMBER);
            ((TextView) tv).setText(Integer.toString(section));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
