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

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager";

    // User table name
    private static final String TABLE_USER = "User";

    // User Table Columns names
    private static final String KEY_NAME = "name";
    private static final String KEY_PASS = "pass";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_NAME + " varchar(255)," + KEY_PASS + " varchar(255)" + ");";
        db.execSQL(CREATE_CONTACTS_TABLE);
//        addUser(new User("username", "password"));
    }

    // Upgrading database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    //Adds new User
    public boolean addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        //Creates value and puts name and pass into it
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASS, user.getPass());

        //Insert value into row
        database.insert(TABLE_USER, null, values);
        database.close();
        return true;
    }

    //Get single User login info
    public User getUser(String name) {
        if (!userRegistered(name)) {
            return null;
        }

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_USER, new String[] {KEY_NAME,
                KEY_PASS}, KEY_NAME + "=?",
                new String[] {String.valueOf(name)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return new User(cursor.getString(0), cursor.getString(1));
    }

    //Checks database to see if user is register
    public boolean userRegistered(String name) {
        //Select query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through all the rows
        if (cursor.moveToFirst()){
            do {
                if (cursor.getString(0).equals(name)){
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
                user.setName(cursor.getString(0));
                user.setPass(cursor.getString(1));
                userLists.add(user);
            } while (cursor.moveToNext());
        }
        return userLists;
    }

}
