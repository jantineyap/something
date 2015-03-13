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
    private static final String TABLE_ITEMS = "Items";
    private static final String TABLE_WISHLIST = "Wishlist";


    // User Table Columns emails
    private static final String KEY_EMAIL = "email"; // unique key email in usertable
    private static final String KEY_NAME = "username"; // John Doe, not checked for duplicates
    private static final String KEY_PASS = "pass";

    private static final String KEY_USER = "user"; // user email  in friendsTable
    private static final String KEY_FRIEND = "friends"; // user friendsEmail  in friendsTable

    private static final String KEY_ITEM = "item"; // item in itemsTable
    private static final String KEY_PRICE = "price"; // price in itemsTable
    private static final String KEY_LOC = "location"; //location in itemsTable
    private static final String KEY_BOOLEAN = "boolean"; //boolean values


    /**
     * Handler getInstance to work with the database
     *
     * @param context the context to pass into the handler
     */
    public static DatabaseHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context);
        }
        return sInstance;
    }
    /**
     * Handler takes a context to work with the database
     *
     * @param context the context to pass into the handler
     */
    private DatabaseHandler(Context context) {
        super(context, DATABASE_EMAIL, null, DATABASE_VERSION);
    }

    /**
     * Creates tables
     *
     * @param db the SQLiteDatabase to pass in
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_EMAIL + " varchar(255),"
                + KEY_NAME + " varchar(255),"
                + KEY_PASS + " varchar(255)" + ");";
        String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIEND + "("
                + KEY_USER + " varchar(255),"
                + KEY_FRIEND + " varchar(255)" + ");";
        String CREATE_ITEMS_TABLE = "CREATE TABLE " + TABLE_ITEMS + "("
                +KEY_ITEM + " varchar(255),"
                +KEY_PRICE + " DOUBLE,"
                +KEY_LOC + " varchar(255));";
        String CREATE_WISHLIST_TABLE = "CREATE TABLE " + TABLE_WISHLIST + "("
                +KEY_USER + " varchar(255),"
                +KEY_ITEM + " varchar(255),"
                +KEY_PRICE+ " DOUBLE,"
                +KEY_BOOLEAN + " INT);";
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_FRIENDS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_WISHLIST_TABLE);
    }

    /**
     * used to update tables with upgrade without
     * having to reinstall the entire app
     *
     * @param db the SQLiteDatabase to pass in
     * @param oldVersion the old version of the DB (int)
     * @param newVersion the old version of the DB (int)
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WISHLIST);
        // Create tables again
        onCreate(db);
    }

    /**
     * adds a new user to the database
     *
     * @param user the user to add to the database
     * @return boolean to check if the method executed properly
     */
    public boolean addUser(User user) {
        if (userRegistered(user.getEmail())) {
            return false;
        }
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

    /**Pulls all users from database
     *
     *@return list of all users
     */
    public List<User> getAllUsers() {
        List<User> userLists = new ArrayList<>();

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
        if (userRegistered(email) && !isFriends(user, email) && !email.equals(user)) {
            SQLiteDatabase database = this.getWritableDatabase();

            //Creates value and puts user and friend into it
            ContentValues values = new ContentValues();
            values.put(KEY_USER, user);
            values.put(KEY_FRIEND, email);
            ContentValues values2 = new ContentValues();
            values2.put(KEY_USER, email);
            values2.put(KEY_FRIEND, user);

            //Insert value into row
            database.insert(TABLE_FRIEND, null, values);
            database.insert(TABLE_FRIEND, null, values2);
            database.close();
            return true;
        }
        return false;

    }
    /**
     * deleteFriend method that takes a user String and email to delete
     *
     * @param  user, the user to add the friendslist of
     * @param  email, the email to add the friendslist of
     * @return boolean to see if it worked
     */
    public boolean deleteConnection(String user, String email){
        if (userRegistered(email) && isFriends(user, email)) {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(TABLE_FRIEND, "ROWID = (SELECT Max(ROWID) FROM "
                    + TABLE_FRIEND + " WHERE " + "user=? AND friends=?)",
                    new String[] { user, email });
            database.delete(TABLE_FRIEND, "ROWID = (SELECT Max(ROWID) FROM "
                    + TABLE_FRIEND + " WHERE " + "user=? AND friends=?)",
                    new String[] { email, user });
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
        List<String> friends = new ArrayList<>();

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

    /**
     * getFriends method that takes a user String and returns a friendslist
     *
     * @param user, the user to find the friendslist of
     * @param email, email of friend
     * @return boolean whether success
     */
    public boolean isFriends(String user, String email) {
        String selectQuery = "SELECT  * FROM " + TABLE_FRIEND;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loops through rows checking friends
        if (cursor.moveToFirst()) {
            do {
                if (user.equals(cursor.getString(0))
                        && email.equals(cursor.getString(1))){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    /**
     * add a wish to Wishlist
     *
     * @param user, the user
     * @param item, the wanted item
     * @param price, the desired price
     * @return boolean of success or not
     */
    public boolean addWish(String user, String item, double price) {
        if (isWish(user, item)) {
            deleteWish(user, item);
        }
        SQLiteDatabase database = this.getWritableDatabase();

        //Creates value and puts email and pass into it
        ContentValues values = new ContentValues();
        values.put(KEY_USER, user);
        values.put(KEY_ITEM, item);
        values.put(KEY_PRICE, price);
        values.put(KEY_BOOLEAN, 0);

        //Insert value into row
        database.insert(TABLE_WISHLIST, null, values);
        database.close();
        return true;
    }

    /**
     * checks to see if already wish
     *
     * @param user, the user
     * @param item, the wanted item
     * @return boolean of item or not
     */
    private boolean isWish(String user, String item) {
        //Select query
        String selectQuery = "SELECT  * FROM " + TABLE_WISHLIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through all the rows
        if (cursor.moveToFirst()){
            do {
                if (cursor.getString(0).equals(user) && cursor.getString(1).equals(item)){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    /**
     * delete the wish
     *
     * @param user, the user
     * @param item, the wanted item
     * @return boolean of success or not
     */
    public boolean deleteWish(String user, String item) {
        if (isWish(user, item)) {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(TABLE_WISHLIST, "ROWID = (SELECT Max(ROWID) FROM "
                    + TABLE_WISHLIST + " WHERE " + KEY_USER + "=? AND " + KEY_ITEM + "=?)",
                    new String[] { user, item });
            database.close();
            return true;
        }
        return false;
    }

    /**
     * return list of users wishlist
     *
     * @param user, the user
     * @return list of items that is user's wishlist
     */
    public List<WishListItem> getWishlist(String user) {
        List<WishListItem> items = new ArrayList<>();

        //Select query
        String selectQuery = "SELECT  * FROM " + TABLE_WISHLIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loops through rows adding friends to list
        if (cursor.moveToFirst()) {
            do {
                if (user.equals(cursor.getString(0))){
                    WishListItem item = new WishListItem(cursor.getString(1), cursor.getDouble(2));
                    if (cursor.getInt(3) == 1) {
                        item.setMatched(true);
                    } else if (saleCheck(cursor.getString(1), cursor.getDouble(2)) != null) {
                        item.setMatched(true);
                        ContentValues value = new ContentValues();
                        value.put(KEY_BOOLEAN, 1);
                        String where = KEY_USER + "=? AND " + KEY_ITEM + "=?";
                        db.update(TABLE_WISHLIST, value, where, new String[]{user, cursor.getString(1)});
                    }
                    items.add(item);
                }
            } while (cursor.moveToNext());
        }
        return items;
    }

    private SaleItem saleCheck(String item, double price) {
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(0).equals(item)) {
                    if (price >= cursor.getDouble(1)) {
                        return new SaleItem(cursor.getString(0), cursor.getDouble(1), cursor.getString(2));
                    }
                }
            } while (cursor.moveToNext());
        }
        return null;
    }

    public double getPrice(String user, String item) {

        String selectQuery = "SELECT  * FROM " + TABLE_WISHLIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through all the rows
        if (cursor.moveToFirst()){
            do {
                if (cursor.getString(0).equals(user) && cursor.getString(1).equals(item)){
                    return cursor.getDouble(2);
                }
            } while (cursor.moveToNext());
        }
        return -1;
    }

    /**
     * add an item to items table
     * if item exists checks to see if price is lower and replaces in database
     *
     * @param item, the item
     * @return boolean of success or not
     */
    public boolean addItem(SaleItem item) {
        SQLiteDatabase database = this.getWritableDatabase();
        if (isSaleItem(item)) {
            return true;
        }

        //Creates value and puts email and pass into it
        ContentValues values = new ContentValues();
        values.put(KEY_ITEM, item.getItem());
        values.put(KEY_PRICE, item.getPrice());
        values.put(KEY_LOC, item.getLocation());

        //Insert value into row
        database.insert(TABLE_ITEMS, null, values);
        database.close();
        return true;
    }

    /**
     * helper for addItem
     * if item exists checks to see if price is lower and replaces in database
     *
     * @param item, the item
     * @return boolean whether or not item exists
     */
    private boolean isSaleItem(SaleItem item) {
        //Select query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through all the rows
        if (cursor.moveToFirst()){
            do {
                if (cursor.getString(0).equals(item.getItem())
                        && cursor.getString(2).equals(item.getLocation())){

                    // if price is lower then replace
                    if(cursor.getDouble(1) > item.getPrice()) {
                        ContentValues value =  new ContentValues();
                        value.put(KEY_PRICE, item.getPrice());
                        String where = KEY_ITEM + " = ?" + " AND "
                                + KEY_LOC + " = ?";
                        String[] args = new String[]{item.getItem(), item.getLocation()};
                        db.update(TABLE_ITEMS, value, where, args);
                    }

                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public boolean deleteSaleItem(SaleItem item) {
        if (isSaleItem(item)) {
            SQLiteDatabase database = this.getWritableDatabase();
            database.delete(TABLE_ITEMS, "ROWID = (SELECT Max(ROWID) FROM "
                            + TABLE_ITEMS + " WHERE " + KEY_ITEM + "=? AND " + KEY_LOC + "=?)",
                    new String[] { item.getItem(), item.getLocation() });
            database.close();
            return true;
        }
        return false;
    }

    public List<SaleItem> getSaleList() {
        List<SaleItem> items = new ArrayList<>();

        //Select query
        String selectQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loops through rows adding friends to list
        if (cursor.moveToFirst()) {
            do {
                SaleItem item = new SaleItem(cursor.getString(0)
                        , cursor.getDouble(1), cursor.getString(2));
                items.add(item);
            } while (cursor.moveToNext());
        }
        return items;
    }

    public List<SaleItem> getUserSales(String user) {
        List<SaleItem> items = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_WISHLIST;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(0).equals(user)) {
                    SaleItem hasSale = saleCheck(cursor.getString(1), cursor.getDouble(2));
                    if (hasSale != null) {
                        items.add(hasSale);
                    }
                }
            } while (cursor.moveToNext());
        }
        return items;
    }

}