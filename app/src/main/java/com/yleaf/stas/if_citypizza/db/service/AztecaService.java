package com.yleaf.stas.if_citypizza.db.service;

import android.content.Context;

import com.yleaf.stas.if_citypizza.model.Pizza;
import com.yleaf.stas.if_citypizza.db.dao.AztecaDAO;
import com.yleaf.stas.if_citypizza.db.service.core.OpenDBService;
import com.yleaf.stas.if_citypizza.db.service.core.Service;

import java.util.List;

public class AztecaService extends OpenDBService implements Service<Pizza> {

    public AztecaService(Context context) {
        super(context);
    }

    @Override
    public void save(Pizza pizza) {
        try {
            if(!isOpen()) {
                open();
            }

            // save
            new AztecaDAO(getSqLiteDatabase()).save(pizza);

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
            return new AztecaDAO(getSqLiteDatabase()).getAll();

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
            new AztecaDAO(getSqLiteDatabase()).deleteAll();

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
            new AztecaDAO(getSqLiteDatabase()).deleteById(id);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }
}
