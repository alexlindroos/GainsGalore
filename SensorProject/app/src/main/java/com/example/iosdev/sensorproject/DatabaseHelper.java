package com.example.iosdev.sensorproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thanhbinhtran on 02/10/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "reward_database";
    public static final String TABLE_NAME = "reward_table";
    public static final String COL_1 = "_id";
    public static final String COL_2 = "reward";
    public static final String COL_3 = "goal";
    public static final String COL_4 = "current_step";
    public static final String COL_5 = "speed";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_2 +" TEXT, " + COL_3 + " INTEGER, " + COL_4 + " INTEGER, "+ COL_5 + " INTEGER)");
        ContentValues contentValues = new ContentValues();
        this.prePopulateDatabase("1km - Free coffee from the Metropolia Unicafe - MUC123", 500, 1312, 0, db, contentValues);
        this.prePopulateDatabase("2km - 50% off from Luhta winter jackets from the Intersport - IL912", 1000, 2624, 0, db, contentValues);
        this.prePopulateDatabase("3km - 25% off from any food you order in Amarillo - A3139", 1500, 3937, 0, db, contentValues);
        this.prePopulateDatabase("4km - Helsingin Sanomat for 6 months only 20,00€ - HS0900", 2000, 5249, 0, db, contentValues);
        this.prePopulateDatabase("4km - Mens haircut only 12€ in Style Workshop Kruununhaka - SWK2922", 2500, 5249, 0, db, contentValues);
        this.prePopulateDatabase("2km - Free car wash in Koskelan Autopesu - KAP8889", 3000, 2624, 0, db, contentValues);
        this.prePopulateDatabase("1km - Chefs menu 10€ in Töölön Sävel - TS1231", 3500, 1312, 0, db, contentValues);
        this.prePopulateDatabase("3km - Free Gym membership in Fitness 24/7 - F1223", 4000, 3937, 0, db, contentValues);
        this.prePopulateDatabase("4km - Exit room game for 1-6 people only 9€ in Exit Room Helsinki - ER5582", 4500, 5249, 0, db, contentValues);
        this.prePopulateDatabase("4km -Free bucket from Tokmanni - FB9942", 5000, 5249, 0, db, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void prePopulateDatabase (String reward, Integer goal, Integer currentStep, Integer speed, SQLiteDatabase db, ContentValues contentValues) {
        contentValues.put(COL_2, reward);
        contentValues.put(COL_3, goal);
        contentValues.put(COL_4, currentStep);
        contentValues.put(COL_5, speed);
        db.insert(TABLE_NAME, null, contentValues);
        contentValues.clear();
    }

    public Boolean insertData(String reward, Integer goal, Integer currentStep, Integer speed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, reward);
        contentValues.put(COL_3, goal);
        contentValues.put(COL_4, currentStep);
        contentValues.put(COL_5, speed);
        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getAllData () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    public Boolean updateCurrentStep (String id, Integer currentStep) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_4, currentStep);
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[] {id});
        return true;
    }




}
