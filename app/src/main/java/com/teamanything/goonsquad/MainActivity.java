package com.teamanything.goonsquad;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.SaleItem;
import com.teamanything.goonsquad.database.User;
import com.teamanything.goonsquad.database.WishListItem;

import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, FriendListFragment.OnFragmentInteractionListener,
        WishListFragment.OnFragmentInteractionListener, SalesReportFragment.OnFragmentInteractionListener,
        SaleItemFragment.OnFragmentInteractionListener {

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

        mFragment = new Fragment();

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
                mFragment = SalesReportFragment.newInstance(position + 1, curUser);
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

    void restoreActionBar() {
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

    @Override // from FriendListFragment
    public boolean onAddClick(String email) {
        if (db.addConnection(curUser, email)) {
            return true;
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override // from WishListFragment
    public boolean onAddClick(WishListItem wishListItem) {
        if (db.addWish(curUser, wishListItem)) {
            return true;
        } else {
            Toast.makeText(this, "Could Not Add Item", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override // from SalesReportFragment
    public boolean onAddClick(SaleItem saleItem) {
        if (db.addItem(saleItem)) {
            return true;
        } else {
            Toast.makeText(this, "Could Not Add Item", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override // from FriendListFragment
    public boolean onRemoveClick(String email) {
        if (db.deleteConnection(curUser, email)) {
            return true;
        } else {
            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override // from WishListFragment
    public boolean onRemoveClick(WishListItem wishListItem) {
        if (db.deleteWish(curUser, wishListItem)) {
            return true;
        } else {
            Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override // from SalesReportFragment
    public void onListItemClick(SaleItem saleItem) {
        Fragment newFragment = SaleItemFragment.newInstance(saleItem);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // TODO add animations here

        transaction.addToBackStack(null).replace(R.id.container, newFragment).commit();
    }

}
