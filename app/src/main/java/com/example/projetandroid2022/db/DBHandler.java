package com.example.projetandroid2022.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public void insertEntry(WatchListEntry entry) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues row = new ContentValues();
        row.put(DBContract.Form.ID, entry.getId());
        row.put(DBContract.Form.ID_RESOURCE, entry.getResourceId());
        row.put(DBContract.Form.COLUMN_DATE, entry.getDate());
        row.put(DBContract.Form.COLUMN_RATING, entry.getNote());
        row.put(DBContract.Form.COLUMN_EPISODE, entry.getEpisode());
        row.put(DBContract.Form.COLUMN_SEASON, entry.getSeason());

        long newRowId = db.insert(DBContract.Form.TABLE_NAME,null,row);
    }

    public List<WatchListEntry> listAllEntries() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                DBContract.Form.ID,
                DBContract.Form.ID_RESOURCE,
                DBContract.Form.COLUMN_DATE,
                DBContract.Form.COLUMN_RATING,
                DBContract.Form.COLUMN_EPISODE,
                DBContract.Form.COLUMN_SEASON
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
            int id = cursor.getInt((int)cursor.getColumnIndex(DBContract.Form.ID));
            int idResource = cursor.getInt((int)cursor.getColumnIndex(DBContract.Form.ID_RESOURCE));
            String date = cursor.getString((int)cursor.getColumnIndex(DBContract.Form.COLUMN_DATE));
            int note = cursor.getInt((int)cursor.getColumnIndex(DBContract.Form.COLUMN_RATING));
            int episode = cursor.getInt((int)cursor.getColumnIndex(DBContract.Form.COLUMN_EPISODE));
            int saison = cursor.getInt((int)cursor.getColumnIndex(DBContract.Form.COLUMN_SEASON));

            WatchListEntry entry = null;
            new Response(name, date,genre,cursor.getInt((int)cursor.getColumnIndex(DBContract.Form._ID)),note);
            entries.add(entry);
        }
        cursor.close();
        return entries;
    }
}
