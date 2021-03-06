package com.teamanything.goonsquad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.SaleItem;
import com.teamanything.goonsquad.database.User;
import com.teamanything.goonsquad.database.WishListItem;

import com.parse.*;


public class WelcomeActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());

        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "ZYFdwQMTPNSbrYYnmsrFe2yUNSfvX9gKpJWvTvuK", "TR6wWUoJHsUNS7dPODb4a70NpE9UIUif1rEiFvoe");

        db.addUser(new User("temp@temp.com", "temp", "temp"));

    }

    public void openLoginActivity(@SuppressWarnings("UnusedParameters") View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void openRegistrationActivity(@SuppressWarnings("UnusedParameters") View view) {
        startActivity(new Intent(this, RegistrationActivity.class));
    }

    public void setupDatabase(@SuppressWarnings("UnusedParameters") View view) {
        DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());

        boolean success;

        User a = new User("aaaa@aaaa.com", "aaaa", "aaaa");
        User b = new User("bbbb@bbbb.com", "bbbb", "bbbb");
        User c = new User("cccc@cccc.com", "cccc", "cccc");
        db.addUser(a);
        db.addUser(b);
        db.addUser(c);
        db.addConnection(a, b);
        db.addConnection(a, c);

        SaleItem s1 = new SaleItem("Item 1", 1.0);
        SaleItem s2 = new SaleItem("Item 2", 2.0);
        SaleItem s3 = new SaleItem("Item 3", 3.0);
        db.addItem(s1);
        db.addItem(s2);
        db.addItem(s3);

        WishListItem w1 = new WishListItem("Item 1", 3.0);
        WishListItem w2 = new WishListItem("Item 2", 1.0);
        WishListItem w3 = new WishListItem("Item 2", 2.0);
        db.addWish(a, w1);
        db.addWish(a, w2);
        db.addWish(b, w3);
        success = db.addWish(c, w3);

        if (success) {
            Toast.makeText(this, "Database setup properly", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Database setup failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
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
