package com.example.projetandroid2022.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.projetandroid2022.entities.WatchListEntry;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "projetAndroid";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =  "CREATE TABLE " + DBContract.Form.TABLE_NAME + " (" +
                DBContract.Form.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                DBContract.Form.ID_RESOURCE + " INTEGER," +
                DBContract.Form.COLUMN_DATE + " DATE," +
                DBContract.Form.COLUMN_RATING + " INTEGER," +
                DBContract.Form.COLUMN_IS_SHOW + " BOOLEAN NOT NULL CHECK (" + DBContract.Form.COLUMN_IS_SHOW + " IN (0, 1)))";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldValue, int newValue) {
        String query = "DROP TABLE IF EXISTS " + DBContract.Form.TABLE_NAME;
        onCreate(db);
    }

    public boolean insertEntry(WatchListEntry entry) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBContract.Form.ID, entry.getId());
        row.put(DBContract.Form.ID_RESOURCE, entry.getResourceId());
        row.put(DBContract.Form.COLUMN_DATE, entry.getDateOfViewing().toString());
        row.put(DBContract.Form.COLUMN_RATING, entry.getRating());
        row.put(DBContract.Form.COLUMN_IS_SHOW, entry.isShow());

        return db.insert(DBContract.Form.TABLE_NAME,null,row) > 0;
    }

    public boolean deleteEntryById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.delete(DBContract.Form.TABLE_NAME, DBContract.Form.ID+"="+id,null) > 0;
    }

    public List<WatchListEntry> listAllEntries() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DBContract.Form.ID,
                DBContract.Form.ID_RESOURCE,
                DBContract.Form.COLUMN_DATE,
                DBContract.Form.COLUMN_RATING,
                DBContract.Form.COLUMN_IS_SHOW
        };

        Cursor cursor = db.query(
                DBContract.Form.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        List<WatchListEntry> entries = new ArrayList<>();
        while(cursor.moveToNext()){
            WatchListEntry entry = new WatchListEntry();
            entry.setId(cursor.getInt((int) cursor.getColumnIndex(DBContract.Form.ID)));
            entry.setResourceId(cursor.getInt((int) cursor.getColumnIndex(DBContract.Form.ID_RESOURCE)));
            entry.setDateOfViewing(Date.valueOf(cursor.getString((int) cursor.getColumnIndex(DBContract.Form.COLUMN_DATE))));
            entry.setRating(cursor.getInt((int) cursor.getColumnIndex(DBContract.Form.COLUMN_RATING)));
            entry.setIfItIsShow(cursor.getInt((int) cursor.getColumnIndex(DBContract.Form.COLUMN_IS_SHOW)) == 1);
            entries.add(entry);
        }
        cursor.close();

        return entries;
    }
}
