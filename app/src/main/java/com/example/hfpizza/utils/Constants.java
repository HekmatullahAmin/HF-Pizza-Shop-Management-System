package com.example.hfpizza.utils;

public class Constants {
    public static final String PLAN_DATABASE_NAME = "PIZZA_DB";
    public static final int PLAN_DATABASE_VERSION = 1;

    //    tables name
    public static final String CATEGORY_TABLE_NAME = "CATEGORY_TABLE";
    public static final String ITEM_TABLE_NAME = "ITEM_TABLE";
    public static final String OFFER_TABLE_NAME = "OFFER_TABLE";
    public static final String ORDER_TABLE_NAME = "ORDER_TABLE";
    public static final String USER_TABLE_NAME = "USER_TABLE";

    public static final String ID_COLUMN = "ID";
    //    category columns
    public static final String CATEGORY_NAME_COLUMN = "NAME";
    public static final String CATEGORY_IMAGE_COLUMN = "IMAGE";

    //    ITEM columns
    public static final String ITEM_FROM_CATEGORY_COLUMN = "FROM_CATEGORY_ID";
    public static final String ITEM_NAME_COLUMN = "NAME";
    public static final String ITEM_IMAGE_COLUMN = "IMAGE";
    public static final String ITEM_HAVE_SIZE_COLUMN = "HAVE_SIZE";
    public static final String ITEM_FAVORITE_COLUMN = "FAVORITE";
    public static final String ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN = "ADDED_TO_CART";
    public static final String ITEM_DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String ITEM_AMOUNT_COLUMN = "AMOUNT";
    public static final String ITEM_REGULAR_AMOUNT_COLUMN = "R_AMOUNT";
    public static final String ITEM_MEDIUM_AMOUNT_COLUMN = "M_AMOUNT";
    public static final String ITEM_LARGE_AMOUNT_COLUMN = "L_AMOUNT";
    public static final String ITEM_NUMBER_OF_TIME_ORDERED_COLUMN = "NO_OF_TIMES_ORDERED";

    //    OFFER columns
    public static final String OFFER_STATE_COLUMN = "OFFER_STATE";
    public static final String OFFER_NAME_COLUMN = "OFFER_NAME";
    public static final String OFFER_DESCRIPTION_COLUMN = "OFFER_DESCRIPTION";
    public static final String OFFER_AMOUNT_COLUMN = "OFFER_AMOUNT";

    public static final String CLASS_NAME_KEY = "CLASS_NAME";
    public static final String CART_FRAGMENT_NAME = "CART_FRAGMENT";
    public static final String MAIN_ACTIVITY_NAME = "MAIN_ACTIVITY";

    //    ORDER columns
    public static final String ORDER_AMOUNT = "AMOUNT";
    public static final String ORDER_DATE = "DATE";
    public static final String ORDER_NAMES = "NAMES";
    public static final String ORDER_PAYMENT_MODE = "PAYMENT_MODE";

    //    User columns
    public static final String USER_EMAIL = "EMAIL";
    public static final String USER_PASSWORD = "PASSWORD";
    public static final String USER_TYPE = "TYPE";
    //    intent bundles keys
    public static final String SELECTED_CATEGORY_KEY = "KEY_CATEGORY";
    public static final String SELECTED_CATEGORY_ID = "ID_CATEGORY";


    public static final String USER_TYPE_KEY = "USER_TYPE";

}

