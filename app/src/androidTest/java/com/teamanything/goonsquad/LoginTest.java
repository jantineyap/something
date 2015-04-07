package com.teamanything.goonsquad;

import android.test.AndroidTestCase;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.User;


/**
 * Created by Sarthak Mohapatra on 4/2/15.
 */

public class LoginTest extends AndroidTestCase {

    DatabaseHandler db = DatabaseHandler.getInstance(getContext());

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        User a = new User("abcd@abcd.com", "abcd", "abcd");
        User b = new User("dcba@dcba.com", "dcba", "dcba");
        db.addUser(a);
        db.addUser(b);
        db.addConnection(a, b);
    }

    public void testLogin() throws Exception {
        assertTrue(db.userRegistered("dcba@dcba.com"));
        assertTrue(db.userRegistered("abcd@abcd.com"));
        assertTrue(db.isFriends("abcd", "dcba@dcba.com"));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
