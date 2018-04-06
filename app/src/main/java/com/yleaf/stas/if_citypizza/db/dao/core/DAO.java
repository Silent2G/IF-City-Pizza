package com.yleaf.stas.if_citypizza.db.dao.core;

import android.database.Cursor;

import java.util.List;

public interface DAO<T> {
    void save(T t);
    void deleteById(int id);
    void deleteAll();
    List<T> getAll();
    List<T> parseCursor(Cursor cursor);
}
