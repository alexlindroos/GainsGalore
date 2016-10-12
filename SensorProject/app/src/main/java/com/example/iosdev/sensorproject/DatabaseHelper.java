package com.example.iosdev.sensorproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;

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
    public static final String COL_6 = "isCompleted";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + COL_1 +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 +" TEXT, "
                + COL_3 + " INTEGER, "
                + COL_4 + " INTEGER, "
                + COL_5 + " DOUBLE, "
                + COL_6 + " BOOLEAN)");
        ContentValues contentValues = new ContentValues();
        this.prePopulateDatabase("Free coffee from the Metropolia Unicafe - MUC123", 30, 0, 0, db, contentValues);
        this.prePopulateDatabase("50% off from Luhta winter jackets from the Intersport - IL912", 1000, 0, 0, db, contentValues);
        this.prePopulateDatabase("25% off from any food you order in Amarillo - A3139", 1500, 0, 0, db, contentValues);
        this.prePopulateDatabase("Helsingin Sanomat for 6 months only 20,00€ - HS0900", 2000, 0, 0, db, contentValues);
        this.prePopulateDatabase("Mens haircut only 12€ in Style Workshop Kruununhaka - SWK2922", 2500, 0, 0, db, contentValues);
        this.prePopulateDatabase("Free car wash in Espoon Starwash - EST8889", 3000, 0, 0, db, contentValues);
        this.prePopulateDatabase("Chefs menu 10€ in Töölön Sävel - TS1231", 3500, 0, 0, db, contentValues);
        this.prePopulateDatabase("Free Gym membership in Fitness 24/7 - F1223", 4000, 0, 0, db, contentValues);
        this.prePopulateDatabase("Exit room game for 1-6 people only 9€ in Exit Room Helsinki - ER5582", 4500, 0, 0, db, contentValues);
        this.prePopulateDatabase("Free bucket from Tokmanni - FB9942", 5000, 0, 0, db, contentValues);
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
        contentValues.put(COL_6, false);

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

    /*
     * Get all data from the database and return a {@link Cursor} over the result set.
     * @return A {@link Cursor} object, which is positioned before the first entry.
     */
    public Cursor getAllData () {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    /*
     * Update current_step column in accordance with the _id
     * @param id the id of the value to update
     * @param currentStep the current_step for updating
     *
     * @return the boolean value indicating if the update was successful.
     */
    public Boolean updateCurrentStep (String id, Integer currentStep) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_4, currentStep);
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[] {id});
        return true;
    }

    /*
     * Update isCompleted column in accordance with the _id
     * @param id the id of the value to update
     * @param value the data for the value to update
     *
     * @return the boolean value indicating if the update was successful.
     */
    public Boolean updateIsCompleted (String id, Boolean value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_6, value);
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[] {id});
        return true;
    }

    /*
     * Update speed column in accordance with the _id
     * @param id the id of the value to update
     * @param speed the speed value for updating
     *
     * @return the boolean value indicating if the update was successful.
     */
    public Boolean updateSpeed (String id, double speed) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_1, id);
        contentValues.put(COL_5, speed);
        db.update(TABLE_NAME, contentValues, COL_1 + " = ?", new String[] {id});
        return true;
    }

    /*
     * Get one row of data from the database based on the _id and return a {@link Cursor} over the result set.
     * @return A {@link Cursor} object, which is positioned before the first entry.
     */
    public Cursor getDataById (String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_1 + " = ?", new String[]{id});
        return cursor;
    }

    public int getCurrentStep (String id) {
        int currentStep;
        Cursor cursor = this.getDataById(id);
        if (cursor.moveToFirst()) {
            currentStep = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_4));
        } else {
            currentStep = 0;
        }
        return currentStep;
    }

    public int getSpeed (String id) {
        int speed;
        Cursor cursor = this.getDataById(id);
        if (cursor.moveToFirst()) {
            speed = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_5));
        } else {
            speed = 0;
        }
        return speed;
    }

    public int getGoal (String id) {
        int goal;
        Cursor cursor = this.getDataById(id);
        if (cursor.moveToFirst()) {
            goal = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.COL_3));
        } else {
            goal = 0;
        }
        return goal;
    }



    public void showMessage(String title, String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


}
