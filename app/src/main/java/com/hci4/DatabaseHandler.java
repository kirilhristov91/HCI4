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
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "Bike.db";

    //Lists table
    public static final String TABLE_USER = "User";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_USERNAME = "_name";
    public static final String COLUMN_PASSWORD = "_password";
    public static final String COLUMN_CONSUMPTION = "_consumption";
    public static final String COLUMN_LOGGED_IN = "_loggedIn";


    public static final String TABLE_HISTORY = "History";
    public static final String COLUMN_HISTORY_ID = "_historyId";
    public static final String COLUMN_USER_ID = "_userId";
    public static final String COLUMN_HISTORY_FROM = "_from";
    public static final String COLUMN_HISTORY_TO = "_to";
    public static final String COLUMN_HISTORY_DATE = "_date";
    public static final String COLUMN_HISTORY_CHOICE = "_choice";

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

        String CreateHistoryTableQuery = "CREATE TABLE " + TABLE_HISTORY + "(" +
                COLUMN_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID + " INTEGER, " +
                COLUMN_HISTORY_FROM + " TEXT, " +
                COLUMN_HISTORY_TO + " TEXT, " +
                COLUMN_HISTORY_DATE + " TEXT, " +
                COLUMN_HISTORY_CHOICE + " TEXT, " +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " +
                TABLE_USER + "(" + COLUMN_ID + ")"+
                ");";


        db.execSQL(CreateUserTableQuery);
        db.execSQL(CreateHistoryTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HISTORY);
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

    public void addToHistory(History h){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, h.getUserId());
        values.put(COLUMN_HISTORY_FROM, h.getFrom());
        values.put(COLUMN_HISTORY_TO, h.getDestination());
        values.put(COLUMN_HISTORY_DATE, h.getDate());
        values.put(COLUMN_HISTORY_CHOICE, h.getChoice());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_HISTORY, null, values);
        db.close();
    }

    public int historyEmpty(int idofuser){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HISTORY +
                " WHERE _userId = " + idofuser;
        // cursor points to a location in the results
        Cursor c = db.rawQuery(query, null);
        // move to the first row
        c.moveToFirst();
        if(c.isAfterLast()) return 0;
        else return 1;
    }

    public ArrayList<History> getHistory(int idofuser){
        // prepare the variables to store a row
        int historyId;
        int userId;
        String from = "";
        String to = "";
        String date = "";
        String choice ="";

        ArrayList<History> historyList = new ArrayList<History>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HISTORY +
                " WHERE _userId = " + idofuser;
        // cursor points to a location in the results
        Cursor c = db.rawQuery(query, null);
        // move to the first row
        c.moveToFirst();

        while (!c.isAfterLast()){

            historyId = c.getInt(c.getColumnIndex("_historyId"));
            userId = c.getInt(c.getColumnIndex("_userId"));
            if(c.getString(c.getColumnIndex("_from")) != null){
                from = c.getString(c.getColumnIndex("_from"));
            }
            if(c.getString(c.getColumnIndex("_to")) != null){
                to = c.getString(c.getColumnIndex("_to"));
            }
            if(c.getString(c.getColumnIndex("_date")) != null){
                date = c.getString(c.getColumnIndex("_date"));
            }
            if(c.getString(c.getColumnIndex("_choice")) != null){
                choice = c.getString(c.getColumnIndex("_choice"));
            }

            History historyItem = new History(userId, from, to, date, choice);
            historyItem.setHistoryId(historyId);

            historyList.add(historyItem);
            c.moveToNext();
        }
        c.close();
        db.close();
        return historyList;
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

    public int getUserId(String username){
        int id = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT *" +
                " FROM " + TABLE_USER +
                " WHERE " + COLUMN_USERNAME + "=\"" + username + "\";";
        Cursor c = db.rawQuery(query, null);
        if(c.getCount()>0) {
            c.moveToFirst();
            if(c.isAfterLast()) return -1;

            id = c.getInt(c.getColumnIndex(COLUMN_ID));
        }
        c.close();
        db.close();
        return id;
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
