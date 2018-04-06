package com.yleaf.stas.if_citypizza.db;

import com.yleaf.stas.if_citypizza.Resources;

public class Resource {
    public static final String DB_NAME = "com.yleaf.stas.if_citypizza";
    public static final int DB_VERSION = 1;

    public static final String ID = "id";

    public static final class Pizza {

        public static final String TABLE_NAME_AZTECA_COLLECTION = Resources.AZTECA;
        public static final String TABLE_NAME_PIZZAIF_COLLECTION = Resources.PIZZAIF;
        public static final String TABLE_NAME_CAMELOTFOOD_COLLECTION = Resources.CAMELOTFOOD;

        // Pizza fields
        public static final String TITLE = "title";
        public static final String WEIGHT = "weight";
        public static final String PRICESTANDART = "price_standart";
        public static final String PRICELARGE = "price_large";
        public static final String DIAMETERSTANDART = "diameter_standart";
        public static final String DIAMETERLARGE = "diameter_large";
        public static final String DESCRIPTION = "description";
        public static final String IMAGESRC = "image_src";

        public static final String CREATE_TABLE_AZTECA_COLLECTION =
                "CREATE TABLE "
                        + TABLE_NAME_AZTECA_COLLECTION
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TITLE
                        + " TEXT(255), "
                        + DIAMETERSTANDART
                        + " TEXT(255), "
                        + DIAMETERLARGE
                        + " TEXT(255), "
                        + PRICESTANDART
                        + " TEXT(255), "
                        + PRICELARGE
                        + " TEXT(255), "
                        + IMAGESRC
                        + " TEXT(255), "
                        + DESCRIPTION
                        + " TEXT(255));";

        public static final String CREATE_TABLE_PIZZAIF_COLLECTION =
                "CREATE TABLE "
                        + TABLE_NAME_PIZZAIF_COLLECTION
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TITLE
                        + " TEXT(255), "
                        + WEIGHT
                        + " TEXT(255), "
                        + DIAMETERSTANDART
                        + " TEXT(255), "
                        + PRICESTANDART
                        + " TEXT(255), "
                        + IMAGESRC
                        + " TEXT(255), "
                        + DESCRIPTION
                        + " TEXT(255));";

        public static final String CREATE_TABLE_CAMELOTFOOD_COLLECTION =
                "CREATE TABLE "
                        + TABLE_NAME_CAMELOTFOOD_COLLECTION
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + TITLE
                        + " TEXT(255), "
                        + DIAMETERSTANDART
                        + " TEXT(255), "
                        + PRICESTANDART
                        + " TEXT(255), "
                        + IMAGESRC
                        + " TEXT(255), "
                        + DESCRIPTION
                        + " TEXT(255));";

    }

    public static final class Manufacturer {

        // Manufacturer fields
        public static final String ADDRESS = "address";
        public static final String EMAIL = "email";
        public static final String LOGOSRC = "logo_src";
        public static final String KYIVSTAR = "kyivstar";
        public static final String VODAFONE = "vodafone";
        public static final String LIFECELL = "lifecell";
        public static final String UKRTELECOM = "ukrtelecom";

        public static final String TABLE_NAME_AZTECA_INFO = Resources.AZTECAINFO;
        public static final String TABLE_NAME_PIZZAIF_INFO = Resources.PIZZAIFINFO;
        public static final String TABLE_NAME_CAMELOTFOOD_INFO = Resources.CAMELOTFOODINFO;

        public static final String CREATE_TABLE_AZTECA_INFO =
                "CREATE TABLE "
                        + TABLE_NAME_AZTECA_INFO
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ADDRESS
                        + " TEXT(255), "
                        + EMAIL
                        + " TEXT(255), "
                        + LOGOSRC
                        + " TEXT(255), "
                        + KYIVSTAR
                        + " TEXT(255), "
                        + VODAFONE
                        + " TEXT(255), "
                        + LIFECELL
                        + " TEXT(255), "
                        + UKRTELECOM
                        + " TEXT(255));";

        public static final String CREATE_TABLE_PIZZAIF_INFO =
                "CREATE TABLE "
                        + TABLE_NAME_PIZZAIF_INFO
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ADDRESS
                        + " TEXT(255), "
                        + EMAIL
                        + " TEXT(255), "
                        + LOGOSRC
                        + " TEXT(255), "
                        + KYIVSTAR
                        + " TEXT(255), "
                        + VODAFONE
                        + " TEXT(255), "
                        + LIFECELL
                        + " TEXT(255), "
                        + UKRTELECOM
                        + " TEXT(255));";

        public static final String CREATE_TABLE_CAMELOTFOOD_INFO =
                "CREATE TABLE "
                        + TABLE_NAME_CAMELOTFOOD_INFO
                        + " ("
                        + ID
                        + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + ADDRESS
                        + " TEXT(255), "
                        + LOGOSRC
                        + " TEXT(255), "
                        + KYIVSTAR
                        + " TEXT(255), "
                        + VODAFONE
                        + " TEXT(255));";
    }
}
