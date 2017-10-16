package com.example.rajko.uhvatime;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.inputmethodservice.Keyboard;
import android.support.v7.app.ActionBar;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rajko on 07-Feb-17.
 */

public class DBAdapter {

    static final String ROW_ID = "id";
    static final String REZ = "rez";
    static final String IME = "ime";

    static final String DBNAME = "DB";
    static final String TBNAME = "TB";
    static final int DBVERSION = 1;

    static String CREATE_TB = "CREATE TABLE " + TBNAME + "(" + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + IME + " TEXT, " + REZ +" INTEGER)";
    final Context c;
    SQLiteDatabase db;
    DBHelper helper;

    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context) {
            super(context, DBNAME, null, DBVERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TB);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("DBAdapter", "Upgrading DB");
            db.execSQL("DROP TABLE IF EXISTS " + TBNAME);

            onCreate(db);
        }
    }

    public DBAdapter openDB() {
        try {
            db = helper.getWritableDatabase();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return this;
    }

    public void close() {
        helper.close();
    }

    public long add(String ime, int rez) {
        try {
            ContentValues cv = new ContentValues();
            cv.put(IME, ime);
            cv.put(REZ, rez);
            return db.insert(TBNAME, null, cv);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Cursor prikaziRezultate() {
        db = helper.getWritableDatabase();
        Cursor rez = db.rawQuery("select * from " + TBNAME, null);
        return rez;
    }
}
