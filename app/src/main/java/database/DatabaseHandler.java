package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
                + KEY_NAME + " INTEGER PRIMARY KEY," + KEY_PASS + " TEXT," + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
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
    public void addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        //Creates value and puts name and pass into it
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_PASS, user.getPass());

        //Insert value into row
        database.insert(TABLE_USER, null, values);
        database.close();
    }

    //Get single User login info
    public User getUser(String name) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_USER, new String[] {KEY_NAME,
                KEY_PASS}, KEY_NAME + "=?",
                new String[] {String.valueOf(name)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(cursor.getString(0), cursor.getString(1));
        return user;
    }

    //Checks database to see if user is register
    public boolean userRegistered(String name) {
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //loop through all the rows
        if (cursor.moveToFirst()){
            do {
                if (cursor.getString(0) == name){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }
}
