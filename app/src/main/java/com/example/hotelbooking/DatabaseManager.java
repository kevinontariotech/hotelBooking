package com.example.hotelbooking;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "HotelBooking";
    private static final int DATABASE_VERSION = 2;

    // stores hotel rooms
    private static final String TABLE_NAME = "hotelRooms";
    private static final String ID_COL = "id";
    private static final String ROOM_NAME_COL = "roomName";
    private static final String SQUARE_FEET_COL = "squareFeet";
    private static final String BEDS_COL = "beds";
    private static final String PEOPLE_COL = "people";
    private static final String PRICE_COL = "price";

    // Hotel table columns
    private static final String TABLE_HOTEL = "hotel";
    private static final String KEY_ID = "id";

    private static final String KEY_HOTEL_NAME = "hotel_name";
    private static final String KEY_LATITUDE = "latitude";
    private static final String KEY_LONGITUDE = "longitude";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_PHONE_NUMBER = "phone_number";
    private static final String KEY_ROOMS_HOTEL = "rooms";

    // Users table columns
    private static final String TABLE_USERS = "users";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FIRST_NAME = "first_name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_PHONE_NUMBER_USER = "phone_number";

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
        // Create Hotel table
        String CREATE_HOTEL_TABLE = "CREATE TABLE " + TABLE_HOTEL +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY ," +
                KEY_HOTEL_NAME + " varchar(200)," +
                KEY_LATITUDE + " varchar(200)," +
                KEY_LONGITUDE + " varchar(200)," +
                KEY_ADDRESS + " varchar(200)," +
                KEY_PHONE_NUMBER + " varchar(200)," +
                KEY_ROOMS_HOTEL + " INTEGER" +
                ")";
        db.execSQL(CREATE_HOTEL_TABLE);

        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_EMAIL + " varchar(200)," +
                KEY_PASSWORD + " varchar(200)," +
                KEY_FIRST_NAME + " varchar(200)," +
                KEY_LAST_NAME + " varchar(200)," +
                KEY_PHONE_NUMBER_USER + " varchar(200)" +
                ")";
        db.execSQL(CREATE_USERS_TABLE);
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
    public ArrayList<RoomModal> readRooms(String filter, String order) {
        // open database for reading
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorCourses;
        // get data
        if (!order.isEmpty() && !filter.isEmpty()){
            String sql = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + filter + " " + order;
            cursorCourses = db.rawQuery(sql, null);
        } else {
            cursorCourses = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        }


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

    // CRUD methods for Hotel
    public void insertHotel(int id,String hotelName, String latitude, String longitude, String address, String phoneNumber, int rooms) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_HOTEL_NAME, hotelName);
        values.put(KEY_LATITUDE, latitude);
        values.put(KEY_LONGITUDE, longitude);
        values.put(KEY_ADDRESS, address);
        values.put(KEY_PHONE_NUMBER, phoneNumber);
        values.put(KEY_ROOMS_HOTEL, rooms);
        db.insert(TABLE_HOTEL, null, values);
        db.close();
    }
    @SuppressLint("Range")
    public List<HotelModel> getNearbyHotels(double userLatitude, double userLongitude, double radius) {
        List<HotelModel> nearbyHotels = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        // Assuming 'hotels' is the table name
//        String query = "SELECT * FROM "+TABLE_HOTEL +
//                " WHERE SQRT(POW(69.1 * (latitude - ?), 2) + POW(69.1 * (? - longitude) * COS(latitude / 57.3), 2)) < ?";
        double cosValue = Math.cos(userLatitude / 57.3);

        String query = "SELECT * FROM " + TABLE_HOTEL +
                " WHERE (69.1 * (latitude - ?) * 69.1 * (latitude - ?) + 69.1 * (? - longitude) * ? * 69.1 * (? - longitude) * ?) < ?";

        String[] selectionArgs = {
                String.valueOf(userLatitude),
                String.valueOf(userLatitude),
                String.valueOf(userLongitude),
                String.valueOf(cosValue),
                String.valueOf(userLongitude),
                String.valueOf(cosValue),
                String.valueOf(radius)
        };

        Cursor cursor = db.rawQuery(query, selectionArgs);

        if (cursor.moveToFirst()) {
            do {
                // Assuming you have a Hotel class to represent a row in the 'hotels' table
                HotelModel hotel = new HotelModel();
                hotel.setId_hotel(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                hotel.setHotelName(cursor.getString(cursor.getColumnIndex(KEY_HOTEL_NAME)));
                hotel.setLat(cursor.getString(cursor.getColumnIndex(KEY_LATITUDE)));
                hotel.setLon(cursor.getString(cursor.getColumnIndex(KEY_LONGITUDE)));
                hotel.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));
                hotel.setRooms(cursor.getInt(cursor.getColumnIndex(KEY_ROOMS_HOTEL)));
                hotel.setHotelPhoneNO(cursor.getString(cursor.getColumnIndex(KEY_PHONE_NUMBER)));
                // ... other properties

                nearbyHotels.add(hotel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return nearbyHotels;
    }
    // CRUD methods for Users
    public void insertUser(int userId, String email, String password, String firstName, String lastName, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, userId);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);
        values.put(KEY_FIRST_NAME, firstName);
        values.put(KEY_LAST_NAME, lastName);
        values.put(KEY_PHONE_NUMBER_USER, phoneNumber);
        db.insert(TABLE_USERS, null, values);
        db.close();
    }
    /////////////method to check if hotel table empty to insert data for the first time only
    public boolean isHotelTableEmpty() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM " + TABLE_HOTEL;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        int count = cursor.getInt(0);
        cursor.close();

        return count == 0;
    }

    public boolean isValidUser(String email, String password) {
        // Assuming 'users' is your table name
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        String[] selectionArgs = {email, password}; // Please note that storing passwords in plaintext is not secure. Use hashed passwords in practice.

        Cursor cursor = db.rawQuery(query, selectionArgs);

        boolean isValid = cursor.getCount() > 0;

        cursor.close();

        return isValid;
    }

}
