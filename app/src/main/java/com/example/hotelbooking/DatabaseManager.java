package com.example.hotelbooking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HotelBooking";
    private static final int DATABASE_VERSION = 1;

    // stores hotel rooms
    private static final String TABLE_NAME = "hotelRooms";
    private static final String ID_COL = "id";
    private static final String ROOM_NAME_COL = "roomName";
    private static final String SQUARE_FEET_COL = "squareFeet";
    private static final String BEDS_COL = "beds";
    private static final String PEOPLE_COL = "people";
    private static final String PRICE_COL = "price";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ROOM_NAME_COL + " TEXT,"
                + SQUARE_FEET_COL + " TEXT,"
                + BEDS_COL + " TEXT,"
                + PEOPLE_COL + " TEXT,"
                + PRICE_COL + " TEXT)";

        // execute query
        db.execSQL(query);
    }

    public void addNewRoom(String roomName, String squareFeet, String beds, String people, String price) {

        // open database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // used to update data
        ContentValues values = new ContentValues();

        // assign values
        values.put(ROOM_NAME_COL, roomName);
        values.put(SQUARE_FEET_COL, squareFeet);
        values.put(BEDS_COL, beds);
        values.put(PEOPLE_COL, people);
        values.put(PRICE_COL, price);

        // add row to table
        db.insert(TABLE_NAME, null, values);

        // close database
        db.close();
    }

    public void deleteAllRooms() {
        // open the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // delete all rows from the hotel rooms table
        db.delete(TABLE_NAME, null, null);

        // close the database
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // check if table exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // get rooms from database and return it as an array of objects
    public ArrayList<RoomModal> readRooms() {
        // open database for reading
        SQLiteDatabase db = this.getReadableDatabase();

        // get data
        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // create arraylist to store rows
        ArrayList<RoomModal> roomModalArrayList = new ArrayList<>();

        // moving cursor to first row
        if (cursorCourses.moveToFirst()) {
            do {
                // add data to array list
                roomModalArrayList.add(new RoomModal(
                        cursorCourses.getInt(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2),
                        cursorCourses.getString(3),
                        cursorCourses.getString(4),
                        cursorCourses.getString(5)));
            } while (cursorCourses.moveToNext());
            // move to next row
        }

        // close cursor
        cursorCourses.close();

        // return array list
        return roomModalArrayList;
    }
}