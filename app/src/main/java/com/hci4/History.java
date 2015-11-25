package com.hci4;

public class History {
    /*
    COLUMN_HISTORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
    COLUMN_USER_ID + " INTEGER, " +
    COLUMN_HISTORY_FROM + " TEXT, " +
    COLUMN_HISTORY_TO + " TEXT, " +
    COLUMN_HISTORY_DATE + " TEXT, " +
    COLUMN_HISTORY_CHOICE + " TEXT, " +
            "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " +
    TABLE_USER + "(" + COLUMN_ID + ")"+
            ");";*/

    private int historyId, userId;
    private String from, destination, date, choice;

    public History(int userId, String from, String destination, String date, String choice){
        this.userId = userId;
        this.from = from;
        this.destination = destination;
        this.date = date;
        this.choice = choice;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int historyId) {
        this.historyId = historyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }
}
