package com.yleaf.stas.if_citypizza.db;

import android.content.Context;

import com.yleaf.stas.if_citypizza.DataHolder;
import com.yleaf.stas.if_citypizza.admin.Manufacturer;
import com.yleaf.stas.if_citypizza.admin.Pizza;
import com.yleaf.stas.if_citypizza.db.service.AztecaInfoService;
import com.yleaf.stas.if_citypizza.db.service.AztecaService;
import com.yleaf.stas.if_citypizza.db.service.CamelotFoodInfoService;
import com.yleaf.stas.if_citypizza.db.service.CamelotFoodService;
import com.yleaf.stas.if_citypizza.db.service.PizzaIFInfoService;
import com.yleaf.stas.if_citypizza.db.service.PizzaIFService;

import java.util.ArrayList;

public class AddDataToDB implements Runnable {

    private Context context;

    private ArrayList<Pizza> azteca;
    private ArrayList<Pizza> pizzaIF;
    private ArrayList<Pizza> camelotFood;

    private Manufacturer aztecaInfo;
    private Manufacturer pizzaIFInfo;
    private Manufacturer camelotFoodInfo;


    public AddDataToDB(Context context) {
        this.context = context;
    }

    @Override
    public void run() {

        azteca = DataHolder.getData(context).getAzteca();
        for(int i = 0; i < azteca.size(); i++) {
            new AztecaService(context).save(azteca.get(i));
        }

        pizzaIF = DataHolder.getData(context).getPizzaIF();
        for(int i = 0; i < pizzaIF.size(); i++) {
            new PizzaIFService(context).save(pizzaIF.get(i));
        }

        camelotFood = DataHolder.getData(context).getCamelotFood();
        for(int i = 0; i < camelotFood.size(); i++) {
            new CamelotFoodService(context).save(camelotFood.get(i));
        }

        aztecaInfo = DataHolder.getData(context).getAztecaInfo();
        new AztecaInfoService(context).save(aztecaInfo);

        pizzaIFInfo = DataHolder.getData(context).getPizzaIFInfo();
        new PizzaIFInfoService(context).save(pizzaIFInfo);

        camelotFoodInfo = DataHolder.getData(context).getCamelotFoodInfo();
        new CamelotFoodInfoService(context).save(camelotFoodInfo);
    }
}
