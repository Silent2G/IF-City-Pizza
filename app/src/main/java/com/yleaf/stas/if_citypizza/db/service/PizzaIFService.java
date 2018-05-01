package com.yleaf.stas.if_citypizza.db.service;

import android.content.Context;

import com.yleaf.stas.if_citypizza.model.Pizza;
import com.yleaf.stas.if_citypizza.db.dao.PizzaIFDAO;
import com.yleaf.stas.if_citypizza.db.service.core.OpenDBService;
import com.yleaf.stas.if_citypizza.db.service.core.Service;

import java.util.List;

public class PizzaIFService extends OpenDBService implements Service<Pizza> {

    public PizzaIFService(Context context) {
        super(context);
    }

    @Override
    public void save(Pizza pizza) {
        try {
            if(!isOpen()) {
                open();
            }

            // save
            new PizzaIFDAO(getSqLiteDatabase()).save(pizza);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public List<Pizza> getAll() {
        try {
            if(!isOpen()) {
                open();
            }

            // get all
            return new PizzaIFDAO(getSqLiteDatabase()).getAll();

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public void deleteAll() {
        try {
            if(!isOpen()) {
                open();
            }

            // delete all records
            new PizzaIFDAO(getSqLiteDatabase()).deleteAll();

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public void deleteById(int id) {
        try {
            if(!isOpen()) {
                open();
            }

            // delete record by id
            new PizzaIFDAO(getSqLiteDatabase()).deleteById(id);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }
}
