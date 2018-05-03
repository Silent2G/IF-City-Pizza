package com.yleaf.stas.if_citypizza.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yleaf.stas.if_citypizza.model.Manufacturer;
import com.yleaf.stas.if_citypizza.db.Resource;
import com.yleaf.stas.if_citypizza.db.dao.core.DAO;

import java.util.ArrayList;
import java.util.List;

public class AztecaInfoDAO implements DAO<Manufacturer> {

    private SQLiteDatabase sqLiteDatabase;
    private static final String TAG = AztecaInfoDAO.class.getSimpleName();

    public AztecaInfoDAO(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void save(Manufacturer manufacturer) {
        sqLiteDatabase.insert(Resource.Manufacturer.TABLE_NAME_AZTECA_INFO, null, manufacturerContentValues(manufacturer));

        Log.i(TAG, manufacturer.getAddress() + " " + manufacturer.getVodafone());
    }

    private ContentValues manufacturerContentValues(Manufacturer manufacturer) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource.Manufacturer.ADDRESS, manufacturer.getAddress());
        contentValues.put(Resource.Manufacturer.EMAIL, manufacturer.getEmail());
        contentValues.put(Resource.Manufacturer.LOGOSRC, manufacturer.getLogoSrc());
        contentValues.put(Resource.Manufacturer.KYIVSTAR, manufacturer.getKyivstar());
        contentValues.put(Resource.Manufacturer.VODAFONE, manufacturer.getVodafone());
        contentValues.put(Resource.Manufacturer.LIFECELL, manufacturer.getLifecell());
        contentValues.put(Resource.Manufacturer.UKRTELECOM, manufacturer.getUkrtelecom());

        return contentValues;
    }

    @Override
    public List<Manufacturer> getAll() {
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from "
                        + Resource.Manufacturer.TABLE_NAME_AZTECA_INFO,
                null);

        Log.i(TAG, "getAll()");

        return parseCursor(cursor);
    }

    @Override
    public void deleteAll() {
        sqLiteDatabase.execSQL("delete from " + Resource.Manufacturer.TABLE_NAME_AZTECA_INFO);

        Log.i(TAG, "deleteAll()");
    }

    @Override
    public void deleteById(int id) {
        sqLiteDatabase.execSQL("delete from " + Resource.Manufacturer.TABLE_NAME_AZTECA_INFO + " where "
                + Resource.ID + " = " + String.valueOf(id));

        Log.i(TAG, "deleteById " + String.valueOf(id));
    }

    @Override
    public List<Manufacturer> parseCursor(Cursor cursor) {
        List<Manufacturer> manufacturers = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Resource.ID));
                String address = cursor.getString(cursor.getColumnIndex(Resource.Manufacturer.ADDRESS));
                String email = cursor.getString(cursor.getColumnIndex(Resource.Manufacturer.EMAIL));
                String logoSrc = cursor.getString(cursor.getColumnIndex(Resource.Manufacturer.LOGOSRC));
                String kyivstar = cursor.getString(cursor.getColumnIndex(Resource.Manufacturer.KYIVSTAR));
                String vodafone = cursor.getString(cursor.getColumnIndex(Resource.Manufacturer.VODAFONE));
                String lifecell = cursor.getString(cursor.getColumnIndex(Resource.Manufacturer.LIFECELL));
                String ukrtelecom = cursor.getString(cursor.getColumnIndex(Resource.Manufacturer.UKRTELECOM));

                Log.i(TAG, id + "\n" + address + "\n" + email
                        + "\n" + logoSrc + "\n" + kyivstar  + "\n" + vodafone
                        + "\n" + lifecell + "\n" + ukrtelecom);
                manufacturers.add(new Manufacturer(id, address, email, logoSrc,
                        kyivstar, vodafone, lifecell, ukrtelecom));
            } while (cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }
        return manufacturers;
    }
}
