package com.katrina.mp8_kat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by User on 06/04.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper MAIN_HELPER;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "myDatabase.db";

    private static final String ITEMS_TBL_NAME = "items";
    private static final String ITEMS_COL_ID = "id";
    private static final String ITEMS_COL_NAME = "name";
    private static final String ITEMS_COL_DESC = "desc";
    private static final String ITEMS_COL_PRICE = "price";

    private static final ArrayList<Item> LIST_OF_ITEMS = new ArrayList<>();

    public static DBHelper createHelper(Context context) {
        if (MAIN_HELPER == null)
            MAIN_HELPER = new DBHelper(context);
        return MAIN_HELPER;
    }

    public static DBHelper getHelper() {
        return MAIN_HELPER;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE " + ITEMS_TBL_NAME + " (" +
                        ITEMS_COL_ID + " INTEGER PRIMARY KEY, " +
                        ITEMS_COL_NAME + " TEXT, " +
                        ITEMS_COL_DESC + " TEXT, " +
                        ITEMS_COL_PRICE + " DOUBLE" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TBL_NAME);
        onCreate(db);
    }

    public long addItem(int id, String name, String desc, double price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ITEMS_COL_ID, id);
        contentValues.put(ITEMS_COL_NAME, name);
        contentValues.put(ITEMS_COL_DESC, desc);
        contentValues.put(ITEMS_COL_PRICE, price);

        return db.insert(ITEMS_TBL_NAME, null, contentValues);
    }

    public int updateItem(int oldId, int newId, String name, String desc, double price) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(ITEMS_COL_ID, newId);
        contentValues.put(ITEMS_COL_NAME, name);
        contentValues.put(ITEMS_COL_DESC, desc);
        contentValues.put(ITEMS_COL_PRICE, price);

        return db.update(ITEMS_TBL_NAME, contentValues, "id = ? ", new String[] { Integer.toString(oldId) });
    }

    public int deleteItem(int pos) {
        SQLiteDatabase db = this.getWritableDatabase();
        int id = LIST_OF_ITEMS.get(pos).getId();
        return db.delete(ITEMS_TBL_NAME, "id = ? ", new String[] { Integer.toString(id) });
    }

    public Item getItemAtPos(int pos) {
        getItems();
        return LIST_OF_ITEMS.get(pos);
    }

    public ArrayList<Item> getItems() {
        LIST_OF_ITEMS.clear();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + ITEMS_TBL_NAME, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Item item = new Item(
                    cursor.getInt(cursor.getColumnIndex(ITEMS_COL_ID)),
                    cursor.getString(cursor.getColumnIndex(ITEMS_COL_NAME)),
                    cursor.getString(cursor.getColumnIndex(ITEMS_COL_DESC)),
                    cursor.getDouble(cursor.getColumnIndex(ITEMS_COL_PRICE))
            );
            LIST_OF_ITEMS.add(item);
            cursor.moveToNext();
        }

        return LIST_OF_ITEMS;
    }

    public boolean isListEmpty() {
        getItems();
        return LIST_OF_ITEMS.isEmpty();
    }
}