package com.hci4;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper{

    private static DatabaseHandler sInstance;
    //if updating the database change the version:
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "Bike.db";

    //Lists table
    public static final String TABLE_USER = "User";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "_name";
    public static final String COLUMN_PASSWORD = "_password";
    public static final String COLUMN_CONSUMPTION = "_consumption";
    public static final String COLUMN_LOGGED_IN = "_loggedIn";

    /*
    public static final String TABLE_HISTORY = "History";
    public static final String COLUMN_HISTORY_ID = "_historyId";
    public static final String COLUMN_USER_ID = "_userId";
    public static final String COLUMN_HISTORY_FROM = "_from";
    public static final String COLUMN_HISTORY_TO = "_to";
    public static final String COLUMN_HISTORY_DATE = "_to";
    public static final String COLUMN_HISTORY_DISTANCE = "_distance";
    public static final String COLUMN_HISTORY_CHOICE = "_choice";
    public static final String COLUMN_HISTORY_PRICE_CAR = "_priceCar";
    public static final String COLUMN_HISTORY_PRICE_BIKE = "_priceBike";
    */
    public static synchronized DatabaseHandler getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CreateUserTableQuery = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_LOGGED_IN + " INTEGER, " +
                COLUMN_CONSUMPTION + " INTEGER " +
                ");";
        db.execSQL(CreateUserTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }

    //Add a new row to table user
    public void addUser(User user){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUsername());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_LOGGED_IN, user.getLoggedIn());
        values.put(COLUMN_CONSUMPTION, user.getConsumption());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    // delete user
    public void deleteUser(String username){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_USER + " WHERE " + COLUMN_USERNAME + "=\"" + username + "\";");
    }

    public void updateUser(String username, int loggedIn){
        SQLiteDatabase db = getWritableDatabase();
        if(loggedIn == 0)
            db.execSQL( "UPDATE " + TABLE_USER +
                        " SET " + COLUMN_LOGGED_IN +"=" + 0  +
                        " WHERE " + COLUMN_USERNAME + "=\"" + username + "\";");
        else
            db.execSQL( "UPDATE " + TABLE_USER +
                    " SET " + COLUMN_LOGGED_IN +"=" + 1 +
                    " WHERE " + COLUMN_USERNAME + "=\"" + username + "\";");

    }

    public boolean authenticate(String username, String password){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT *" +
                " FROM " + TABLE_USER +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\"" +
                " AND " + COLUMN_PASSWORD + "=\"" + password + "\";";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        if(c.isAfterLast()) return false;
        return true;
    }

    public String printDatabase(){
        String s = "";
        String query = "SELECT *" +
                " FROM " + TABLE_USER + ";";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        if(c.isAfterLast()) return "Database is empty";
        s= c.getString(c.getColumnIndex(COLUMN_USERNAME)) + " " + c.getString(c.getColumnIndex(COLUMN_PASSWORD)) + " " +
                c.getInt(c.getColumnIndex(COLUMN_CONSUMPTION)) + " " + c.getInt(c.getColumnIndex(COLUMN_LOGGED_IN));
        return s;
    }

    public boolean getIfLoggedIn(String username){
        boolean returnValue=false;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT *" +
                       " FROM " + TABLE_USER +
                       " WHERE " + COLUMN_USERNAME + "=\"" + username + "\";";
        Cursor c = db.rawQuery(query, null);
        if(c.getCount()>0) {

            c.moveToFirst();
            int l;
            l = c.getInt(c.getColumnIndex(COLUMN_LOGGED_IN));
            c.close();
            db.close();
            if (l == 1) return true;

        }
        c.close();
        db.close();
        return false;
    }
}
