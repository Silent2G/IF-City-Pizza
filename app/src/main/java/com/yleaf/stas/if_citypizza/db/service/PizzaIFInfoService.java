package com.yleaf.stas.if_citypizza.db.service;

import android.content.Context;

import com.yleaf.stas.if_citypizza.model.Manufacturer;
import com.yleaf.stas.if_citypizza.db.dao.PizzaIFInfoDAO;
import com.yleaf.stas.if_citypizza.db.service.core.OpenDBService;
import com.yleaf.stas.if_citypizza.db.service.core.Service;

import java.util.List;

public class PizzaIFInfoService extends OpenDBService implements Service<Manufacturer> {

    public PizzaIFInfoService(Context context) {
        super(context);
    }

    @Override
    public void save(Manufacturer manufacturer) {
        try {
            if(!isOpen()) {
                open();
            }

            // save
            new PizzaIFInfoDAO(getSqLiteDatabase()).save(manufacturer);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }

    @Override
    public List<Manufacturer> getAll() {
        try {
            if(!isOpen()) {
                open();
            }

            // get all
            return new PizzaIFInfoDAO(getSqLiteDatabase()).getAll();

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
            new PizzaIFInfoDAO(getSqLiteDatabase()).deleteAll();

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
            new PizzaIFInfoDAO(getSqLiteDatabase()).deleteById(id);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }
}
