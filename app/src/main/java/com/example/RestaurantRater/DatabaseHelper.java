package com.example.RestaurantRater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "mealrater.db";
    public static final String RESTAURANT_TABLE_NAME = "Restaurant";
    public static final String DISH_TABLE_NAME = "Dish";
    public static final String RESTAURANT_ID = "RestaurantId";
    public static final String RESTAURANT_NAME = "Name";
    public static final String RESTAURANT_STREET_ADDRESS = "StreetAddress";
    public static final String RESTAURANT_CITY = "City";
    public static final String RESTAURANT_STATE = "State";
    public static final String RESTAURANT_ZIPCODE = "ZipCode";
    public static final String DISH_ID = "DishId";
    public static final String DISH_NAME = "Name";
    public static final String DISH_TYPE = "Type";
    public static final String DISH_RATING = "Rating";
    public static final String DISH_RESTAURANT_ID = "RestaurantId";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create the Restaurant table
        String createRestaurantTable = "CREATE TABLE " + RESTAURANT_TABLE_NAME + "("
                + RESTAURANT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + RESTAURANT_NAME + " TEXT,"
                + RESTAURANT_STREET_ADDRESS + " TEXT,"
                + RESTAURANT_CITY + " TEXT,"
                + RESTAURANT_STATE + " TEXT,"
                + RESTAURANT_ZIPCODE + " TEXT"
                + ")";
        db.execSQL(createRestaurantTable);

        // Create the Dish table
        String createDishTable = "CREATE TABLE " + DISH_TABLE_NAME + "("
                + DISH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DISH_NAME + " TEXT,"
                + DISH_TYPE + " TEXT,"
                + DISH_RATING + " INTEGER,"
                + DISH_RESTAURANT_ID + " INTEGER,"
                + " FOREIGN KEY (" + DISH_RESTAURANT_ID + ") REFERENCES " + RESTAURANT_TABLE_NAME + "(" + RESTAURANT_ID + ")"
                + ")";
        db.execSQL(createDishTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RESTAURANT_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DISH_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertRestaurant(String name, String streetAddress, String city, String state, String zipCode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RESTAURANT_NAME, name);
        contentValues.put(RESTAURANT_STREET_ADDRESS, streetAddress);
        contentValues.put(RESTAURANT_CITY, city);
        contentValues.put(RESTAURANT_STATE, state);
        contentValues.put(RESTAURANT_ZIPCODE, zipCode);
        long result = db.insert(RESTAURANT_TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllRestaurants() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + RESTAURANT_TABLE_NAME, null);
    }

    public boolean insertDish(String name, String type, int rating, int restaurantId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DISH_NAME, name);
        contentValues.put(DISH_TYPE, type);
        contentValues.put(DISH_RATING, rating);
        Cursor cursor = db.rawQuery("SELECT MAX(" + DISH_ID + ") FROM " + DISH_TABLE_NAME + " WHERE " + DISH_RESTAURANT_ID + " = " + restaurantId, null);
        int dishId;
        if (cursor.moveToFirst()) {
            dishId = cursor.getInt(0) + 1;
        } else {
            dishId = 1;
        }
        contentValues.put(DISH_RESTAURANT_ID, restaurantId);




        long result = db.insert(DISH_TABLE_NAME, null, contentValues);
        return result != -1;
    }
    public boolean updateDataDish(int dishId, String name, String type, float rating, int restaurantId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DISH_ID, dishId);
        contentValues.put(DISH_NAME, name);
        contentValues.put(DISH_TYPE, type);
        contentValues.put(DISH_RATING, rating);
        contentValues.put(DISH_RESTAURANT_ID, restaurantId);
        int numRowsUpdated = db.update(DISH_TABLE_NAME, contentValues, DISH_ID + " = ?", new String[] { Integer.toString(dishId) });
        return numRowsUpdated > 0;
    }

    public Integer deleteDataDish(int dishId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(DISH_TABLE_NAME, DISH_ID + " = ?", new String[] { Integer.toString(dishId) });
    }

    public Cursor getAllDataDish() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + DISH_TABLE_NAME, null);
    }

}