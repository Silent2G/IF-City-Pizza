package com.yleaf.stas.if_citypizza.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.yleaf.stas.if_citypizza.model.Pizza;
import com.yleaf.stas.if_citypizza.db.Resource;
import com.yleaf.stas.if_citypizza.db.dao.core.DAO;

import java.util.ArrayList;
import java.util.List;

public class PizzaIFDAO implements DAO<Pizza> {
    private SQLiteDatabase sqLiteDatabase;
    private static final String TAG = PizzaIFDAO.class.getSimpleName();

    public PizzaIFDAO(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public void save(Pizza pizza) {
        sqLiteDatabase.insert(Resource.Pizza.TABLE_NAME_PIZZAIF_COLLECTION, null, pizzaContentValues(pizza));

        Log.i(TAG, pizza.getTitle() + " " + pizza.getPriceStandart());
    }

    private ContentValues pizzaContentValues(Pizza pizza) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(Resource.Pizza.TITLE, pizza.getTitle());
        contentValues.put(Resource.Pizza.WEIGHT, pizza.getWeight());
        contentValues.put(Resource.Pizza.PRICESTANDART, pizza.getPriceStandart());
        contentValues.put(Resource.Pizza.DIAMETERSTANDART, pizza.getDiameterStandart());
        contentValues.put(Resource.Pizza.DESCRIPTION, pizza.getDescription());
        contentValues.put(Resource.Pizza.IMAGESRC, pizza.getImageSrc());

        return contentValues;
    }

    @Override
    public List<Pizza> getAll() {
        Cursor cursor = sqLiteDatabase.rawQuery(
                "select * from "
                        + Resource.Pizza.TABLE_NAME_PIZZAIF_COLLECTION,
                null);

        Log.i(TAG, "getAll()");

        return parseCursor(cursor);
    }

    @Override
    public void deleteAll() {
        sqLiteDatabase.execSQL("delete from " + Resource.Pizza.TABLE_NAME_PIZZAIF_COLLECTION);

        Log.i(TAG, "deleteAll()");
    }

    @Override
    public void deleteById(int id) {
        sqLiteDatabase.execSQL("delete from " + Resource.Pizza.TABLE_NAME_PIZZAIF_COLLECTION + " where "
                + Resource.ID + " = " + String.valueOf(id));

        Log.i(TAG, "deleteById " + String.valueOf(id));
    }

    @Override
    public List<Pizza> parseCursor(Cursor cursor) {
        List<Pizza> pizzas = new ArrayList<>();
        if(cursor != null && cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(Resource.ID));
                String title = cursor.getString(cursor.getColumnIndex(Resource.Pizza.TITLE));
                String weight = cursor.getString(cursor.getColumnIndex(Resource.Pizza.WEIGHT));
                String priceStandart = cursor.getString(cursor.getColumnIndex(Resource.Pizza.PRICESTANDART));
                String diameterStandart = cursor.getString(cursor.getColumnIndex(Resource.Pizza.DIAMETERSTANDART));
                String description = cursor.getString(cursor.getColumnIndex(Resource.Pizza.DESCRIPTION));
                String imageSrc = cursor.getString(cursor.getColumnIndex(Resource.Pizza.IMAGESRC));

                Log.i(TAG, id + "\n" + title + "\n" + weight
                        + "\n" + priceStandart + "\n" + description + "\n" + imageSrc);
                pizzas.add(new Pizza(id, title, weight, priceStandart,
                        null, diameterStandart, null, description, imageSrc));
            } while (cursor.moveToNext());
        }

        if(cursor != null) {
            cursor.close();
        }
        return pizzas;
    }
}
