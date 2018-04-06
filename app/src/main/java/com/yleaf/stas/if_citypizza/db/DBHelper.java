package com.yleaf.stas.if_citypizza.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, Resource.DB_NAME, null, Resource.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(Resource.Pizza.CREATE_TABLE_AZTECA_COLLECTION);
        sqLiteDatabase.execSQL(Resource.Pizza.CREATE_TABLE_PIZZAIF_COLLECTION);
        sqLiteDatabase.execSQL(Resource.Pizza.CREATE_TABLE_CAMELOTFOOD_COLLECTION);

        sqLiteDatabase.execSQL(Resource.Manufacturer.CREATE_TABLE_AZTECA_INFO);
        sqLiteDatabase.execSQL(Resource.Manufacturer.CREATE_TABLE_PIZZAIF_INFO);
        sqLiteDatabase.execSQL(Resource.Manufacturer.CREATE_TABLE_CAMELOTFOOD_INFO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
