package com.yleaf.stas.if_citypizza;

import android.content.Context;

import com.yleaf.stas.if_citypizza.admin.Manufacturer;
import com.yleaf.stas.if_citypizza.admin.Pizza;

import java.util.ArrayList;

public final class DataHolder {

    private static DataHolder dataHolder;
    private Context appContext;

    private ArrayList<Pizza> azteca;
    private ArrayList<Pizza> pizzaIF;
    private ArrayList<Pizza> camelotFood;

    private Manufacturer aztecaInfo;
    private Manufacturer pizzaIFInfo;
    private Manufacturer camelotFoodInfo;

    private DataHolder(Context appContext) {
        this.appContext = appContext;

        this.azteca = new ArrayList<>();
        this.pizzaIF = new ArrayList<>();
        this.camelotFood = new ArrayList<>();
    }

    public static DataHolder getData(Context context) {
        if(dataHolder == null) {
            dataHolder = new DataHolder(context.getApplicationContext());
        }
        return dataHolder;
    }

    public void clearCollections() {
        if(azteca.size() > 0)
        azteca.clear();

        if(pizzaIF.size() > 0)
        pizzaIF.clear();

        if(camelotFood.size() > 0)
        camelotFood.clear();

    }

    public Pizza getPizzaById(String collectionName, int id) {
        switch (collectionName) {
            case Resource.AZTECA:
                return searchById(azteca, id);
            case Resource.PIZZAIF:
                return searchById(pizzaIF, id);
            case Resource.CAMELOTFOOD:
                return searchById(camelotFood, id);
        }

        return null;
    }

    public Manufacturer getManufacturerByName(String collectionName) {
        switch (collectionName) {
            case Resource.AZTECA:
                return aztecaInfo;
            case Resource.PIZZAIF:
                return pizzaIFInfo;
            case Resource.CAMELOTFOOD:
                return camelotFoodInfo;
        }
        return null;
    }

    private Pizza searchById(ArrayList<Pizza> pizzas, int id) {
        for (Pizza pizza : pizzas) {
            if(pizza.getId() == id)
                return pizza;
        }
        return null;
    }

    public ArrayList<Pizza> getAzteca() {
        return azteca;
    }

    public ArrayList<Pizza> getPizzaIF() {
        return pizzaIF;
    }

    public ArrayList<Pizza> getCamelotFood() {
        return camelotFood;
    }

    public Manufacturer getAztecaInfo() {
        return aztecaInfo;
    }

    public Manufacturer getPizzaIFInfo() {
        return pizzaIFInfo;
    }

    public Manufacturer getCamelotFoodInfo() {
        return camelotFoodInfo;
    }

    public void setAztecaInfo(Manufacturer aztecaInfo) {
        this.aztecaInfo = aztecaInfo;
    }

    public void setPizzaIFInfo(Manufacturer pizzaIFInfo) {
        this.pizzaIFInfo = pizzaIFInfo;
    }

    public void setCamelotFoodInfo(Manufacturer camelotFoodInfo) {
        this.camelotFoodInfo = camelotFoodInfo;
    }
}
