package com.teamanything.goonsquad;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;

import com.teamanything.goonsquad.database.DatabaseHandler;
import com.teamanything.goonsquad.database.User;


/**
 * Created by Sarthak Mohapatra on 4/2/15.
 *
 * JUnits for DatabaseHandler and LoginUseCase
 */

public class LoginTest extends AndroidTestCase {

    DatabaseHandler db;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = DatabaseHandler.getInstance(context);
        User a = new User("abcd@abcd.com", "abcd", "abcd");
        User b = new User("dcba@dcba.com", "dcba", "dcba");
        db.addUser(a);
        db.addUser(b);
        db.addConnection(a, b);
    }

    public void testLogin() throws Exception {
        assertTrue(db.userRegistered("dcba@dcba.com"));
        assertTrue(db.userRegistered("abcd@abcd.com"));
        assertTrue(db.isFriends("abcd@abcd.com", "dcba@dcba.com"));
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

}
