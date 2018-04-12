package com.yleaf.stas.if_citypizza;

/**
 * Created by stas on 21.03.18.
 */

public class Resource {
    //Email Validation pattern
    public static final String regEx = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\" +
            "x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:" +
            "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\" +
            ".){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\" +
            "x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    //Fragments Tags
    public static final String Login_Fragment = "Login_Fragment";
    public static final String SignUp_Fragment = "SignUp_Fragment";
    public static final String ForgotPassword_Fragment = "ForgotPassword_Fragment";

    public static final String AZTECA      = "azteca.if.ua";
    public static final String PIZZAIF     = "pizza-if.com";
    public static final String CAMELOTFOOD = "camelot-food.com";

    public static final String AZTECAINFO      = "azteca.info";
    public static final String PIZZAIFINFO     = "pizza-if.info";
    public static final String CAMELOTFOODINFO = "camelot-food.info";
}
