package com.yleaf.stas.if_citypizza.db.service;

import android.content.Context;

import com.yleaf.stas.if_citypizza.model.Manufacturer;
import com.yleaf.stas.if_citypizza.db.dao.AztecaInfoDAO;
import com.yleaf.stas.if_citypizza.db.service.core.OpenDBService;
import com.yleaf.stas.if_citypizza.db.service.core.Service;

import java.util.List;

public class AztecaInfoService extends OpenDBService implements Service<Manufacturer> {

    public AztecaInfoService(Context context) {
        super(context);
    }

    @Override
    public void save(Manufacturer manufacturer) {
        try {
            if(!isOpen()) {
                open();
            }

            // save
            new AztecaInfoDAO(getSqLiteDatabase()).save(manufacturer);

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
            return new AztecaInfoDAO(getSqLiteDatabase()).getAll();

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
            new AztecaInfoDAO(getSqLiteDatabase()).deleteAll();

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
            new AztecaInfoDAO(getSqLiteDatabase()).deleteById(id);

        } finally {
            if(isOpen()) {
                close();
            }
        }
    }
}
