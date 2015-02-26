package com.teamanything.goonsquad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jantine on 2/10/2015.
 *
 * Handles SQLite database built into Android
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static DatabaseHandler sInstance;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_EMAIL = "UserManager";

    // User table email
    private static final String TABLE_USER = "User";
    private static final String TABLE_FRIEND = "FriendsC";

    // User Table Columns emails
    private static final String KEY_EMAIL = "email"; // unique key email in usertable
    private static final String KEY_NAME = "username"; // John Doe, not checked for duplicates
    private static final String KEY_PASS = "pass";
    private static final String KEY_USER = "user"; // user email  in friendsTable
    private static final String KEY_FRIEND = "friends"; // user friendsEmail  in friendsTable

    public static DatabaseHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context);
        }
        return sInstance;
    }

    private DatabaseHandler(Context context) {
        super(context, DATABASE_EMAIL, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_EMAIL + " varchar(255),"
                + KEY_NAME + " varchar(255),"
                + KEY_PASS + " varchar(255)" + ");";
        String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIEND + "("
                + KEY_USER + " varchar(255),"
                + KEY_FRIEND + " varchar(255)" + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_FRIENDS_TABLE);
    }

    // Upgrading database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        // Create tables again
        onCreate(db);
    }

    //Adds new User
    public boolean addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        //Creates value and puts email and pass into it
        ContentValues values = new ContentValues();
        values.put(KEY_EMAIL, user.getEmail());
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASS, user.getPass());

        //Insert value into row
        database.insert(TABLE_USER, null, values);
        database.close();
        return true;
    }

    //Get single User login info
    public User getUser(String email) {
        if (!userRegistered(email)) {
            return null;
        }

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_USER, new String[] {KEY_EMAIL, KEY_NAME,
                        KEY_PASS}, KEY_EMAIL + "=?",
                new String[] {String.valueOf(email)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new User(cursor.getString(0), cursor.getString(1), cursor.getString(2));
    }

    //Checks database to see if user is register
    public boolean userRegistered(String email) {
        //Select query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through all the rows
        if (cursor.moveToFirst()){
            do {
                if (cursor.getString(0).equals(email)){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    //Pulls all users from database
    public List<User> getAllUsers() {
        List<User> userLists = new ArrayList<User>();

        //Select query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loops through rows adding user to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setEmail(cursor.getString(0));
                user.setPass(cursor.getString(1));
                userLists.add(user);
            } while (cursor.moveToNext());
        }
        return userLists;
    }
    /**
     * getFriends method that takes a user String and returns a friendslist
     *
     * @param  user, the user to add the friendslist of
     * @param  email, the email to add the friendslist of
     * @return boolean to see if it worked
     */
    public boolean addConnection(String user, String email){
        if (userRegistered(email)) {
            SQLiteDatabase database = this.getWritableDatabase();

            //Creates value and puts user and friend into it
            ContentValues values = new ContentValues();
            values.put(KEY_USER, user);
            values.put(KEY_FRIEND, email);

            //Insert value into row
            database.insert(TABLE_FRIEND, null, values);
            database.close();
            return true;
        }
        return false;

    }

    /**
     * getFriends method that takes a user String and returns a friendslist
     *
     * @param user, the user to find the friendslist of
     * @return A list of friend connection.
     */
    public List<String> getFriends(String user){
        List<String> friends = new ArrayList<String>();

        //Select query
        String selectQuery = "SELECT  * FROM " + TABLE_FRIEND;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loops through rows adding friends to list
        if (cursor.moveToFirst()) {
            do {
                if (user.equals(cursor.getString(0))){
                    friends.add(cursor.getString(1));
                }
            } while (cursor.moveToNext());
        }
        return friends;
    }

}