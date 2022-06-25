package com.example.hfpizza.data;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.hfpizza.model.Category;
import com.example.hfpizza.model.Item;
import com.example.hfpizza.model.Offer;
import com.example.hfpizza.model.Order;
import com.example.hfpizza.model.User;
import com.example.hfpizza.utils.Constants;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.PLAN_DATABASE_NAME, null, Constants.PLAN_DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + Constants.CATEGORY_TABLE_NAME + " ( " + Constants.ID_COLUMN +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.CATEGORY_NAME_COLUMN + " TEXT, " + Constants.CATEGORY_IMAGE_COLUMN +
                " BLOB );";

        String CREATE_ITEM_TABLE = "CREATE TABLE " + Constants.ITEM_TABLE_NAME + " ( " + Constants.ID_COLUMN +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.ITEM_FROM_CATEGORY_COLUMN +
                " INTEGER, " + Constants.ITEM_NAME_COLUMN + " TEXT, " + Constants.ITEM_IMAGE_COLUMN +
                " BLOB, " + Constants.ITEM_HAVE_SIZE_COLUMN + " INTEGER, " + Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN +
                " INTEGER, " + Constants.ITEM_FAVORITE_COLUMN + " INTEGER, " + Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN +
                " INTEGER, " + Constants.ITEM_DESCRIPTION_COLUMN + " TEXT, " + Constants.ITEM_AMOUNT_COLUMN +
                " REAL, " + Constants.ITEM_REGULAR_AMOUNT_COLUMN + " REAL, " + Constants.ITEM_MEDIUM_AMOUNT_COLUMN +
                " REAL, " + Constants.ITEM_LARGE_AMOUNT_COLUMN + " REAL );";

        String CREATE_OFFER_TABLE = "CREATE TABLE " + Constants.OFFER_TABLE_NAME + " ( " + Constants.ID_COLUMN +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.OFFER_STATE_COLUMN +
                " INTEGER, " + Constants.OFFER_NAME_COLUMN + " TEXT, " +
                Constants.OFFER_AMOUNT_COLUMN + " REAL, " + Constants.OFFER_DESCRIPTION_COLUMN + " TEXT);";

        String CREATE_ORDER_TABLE = "CREATE TABLE " + Constants.ORDER_TABLE_NAME + " ( " + Constants.ID_COLUMN +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.ORDER_NAMES + " TEXT, " +
                Constants.ORDER_PAYMENT_MODE + " TEXT, " + Constants.ORDER_DATE + " LONG, " +
                Constants.ORDER_AMOUNT + " REAL);";

        String CREATE_USER_TABLE = "CREATE TABLE " + Constants.USER_TABLE_NAME + " ( " + Constants.ID_COLUMN +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + Constants.USER_EMAIL +
                " TEXT, " + Constants.USER_PASSWORD + " TEXT, " + Constants.USER_TYPE + " TEXT);";


        sqLiteDatabase.execSQL(CREATE_CATEGORY_TABLE);
        sqLiteDatabase.execSQL(CREATE_ITEM_TABLE);
        sqLiteDatabase.execSQL(CREATE_OFFER_TABLE);
        sqLiteDatabase.execSQL(CREATE_ORDER_TABLE);
        sqLiteDatabase.execSQL(CREATE_USER_TABLE);

        ContentValues values = new ContentValues();
        values.put(Constants.ID_COLUMN, 1);
        values.put(Constants.USER_EMAIL, "hekmat@yahoo.com");
        values.put(Constants.USER_PASSWORD, "hekmat@123");
        values.put(Constants.USER_TYPE, "A");
        sqLiteDatabase.insert(Constants.USER_TABLE_NAME, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addCategory(Category category) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.CATEGORY_NAME_COLUMN, category.getCategoryName());
        values.put(Constants.CATEGORY_IMAGE_COLUMN, category.getCategoryImage());
        database.insert(Constants.CATEGORY_TABLE_NAME, null, values);
        database.close();
    }

    public void updateCategory(Category category) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.CATEGORY_NAME_COLUMN, category.getCategoryName());
        values.put(Constants.CATEGORY_IMAGE_COLUMN, category.getCategoryImage());
        database.update(Constants.CATEGORY_TABLE_NAME, values, Constants.ID_COLUMN + " =? ",
                new String[]{String.valueOf(category.getCategoryId())});
        database.close();
    }

    public void deleteCategory(int categoryId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Constants.CATEGORY_TABLE_NAME, Constants.ID_COLUMN + " =?",
                new String[]{String.valueOf(categoryId)});
        database.close();
    }

    @SuppressLint("Range")
    public ArrayList<Category> getAllCategories() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Category> categories = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.CATEGORY_TABLE_NAME, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Category category = new Category();
                    category.setCategoryId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                    category.setCategoryName(cursor.getString(cursor.getColumnIndex(Constants.CATEGORY_NAME_COLUMN)));
                    category.setCategoryImage(cursor.getBlob(cursor.getColumnIndex(Constants.CATEGORY_IMAGE_COLUMN)));
                    categories.add(category);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
        return categories;
    }

    @SuppressLint("Range")
    public Category getFirstCategory() {
        SQLiteDatabase database = this.getReadableDatabase();
        Category category = new Category();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.CATEGORY_TABLE_NAME + " LIMIT 1;", null);
        if (cursor.moveToFirst()) {
            category.setCategoryId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
            category.setCategoryName(cursor.getString(cursor.getColumnIndex(Constants.CATEGORY_NAME_COLUMN)));
            category.setCategoryImage(cursor.getBlob(cursor.getColumnIndex(Constants.CATEGORY_IMAGE_COLUMN)));
        }
        return category;
    }

    public void addItem(Item item) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.ITEM_FROM_CATEGORY_COLUMN, item.getItemFromCategoryId());
        values.put(Constants.ITEM_NAME_COLUMN, item.getItemName());
        values.put(Constants.ITEM_IMAGE_COLUMN, item.getItemImage());
        values.put(Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN, item.getItemHowOftenIsAddedToCart());
        values.put(Constants.ITEM_HAVE_SIZE_COLUMN, item.getItemHaveSize());
        values.put(Constants.ITEM_FAVORITE_COLUMN, item.getItemIsFavourite());
        values.put(Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN, item.getItemNumberOfTimesOrdered());
        values.put(Constants.ITEM_DESCRIPTION_COLUMN, item.getItemDescription());
        values.put(Constants.ITEM_AMOUNT_COLUMN, item.getItemPrice());
        values.put(Constants.ITEM_REGULAR_AMOUNT_COLUMN, item.getItemRegularPrice());
        values.put(Constants.ITEM_MEDIUM_AMOUNT_COLUMN, item.getItemMediumPrice());
        values.put(Constants.ITEM_LARGE_AMOUNT_COLUMN, item.getItemLargePrice());
        database.insert(Constants.ITEM_TABLE_NAME, null, values);
        database.close();
    }

    public void updateItem(Item item) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.ITEM_FROM_CATEGORY_COLUMN, item.getItemFromCategoryId());
        values.put(Constants.ITEM_NAME_COLUMN, item.getItemName());
        values.put(Constants.ITEM_IMAGE_COLUMN, item.getItemImage());
        values.put(Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN, item.getItemHowOftenIsAddedToCart());
        values.put(Constants.ITEM_HAVE_SIZE_COLUMN, item.getItemHaveSize());
        values.put(Constants.ITEM_FAVORITE_COLUMN, item.getItemIsFavourite());
        values.put(Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN, item.getItemNumberOfTimesOrdered());
        values.put(Constants.ITEM_DESCRIPTION_COLUMN, item.getItemDescription());
        values.put(Constants.ITEM_AMOUNT_COLUMN, item.getItemPrice());
        values.put(Constants.ITEM_REGULAR_AMOUNT_COLUMN, item.getItemRegularPrice());
        values.put(Constants.ITEM_MEDIUM_AMOUNT_COLUMN, item.getItemMediumPrice());
        values.put(Constants.ITEM_LARGE_AMOUNT_COLUMN, item.getItemLargePrice());
        database.update(Constants.ITEM_TABLE_NAME, values, Constants.ID_COLUMN + " =? ",
                new String[]{String.valueOf(item.getItemId())});
        database.close();
    }

    public void deleteItem(int itemId) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Constants.ITEM_TABLE_NAME, Constants.ID_COLUMN + " =?",
                new String[]{String.valueOf(itemId)});
        database.close();
    }

    @SuppressLint("Range")
    public ArrayList<Item> getFavouriteItems() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Item> favouritesItems = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ITEM_TABLE_NAME
                + " WHERE " + Constants.ITEM_FAVORITE_COLUMN + " =?", new String[]{String.valueOf(1)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                    item.setItemFromCategoryId(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FROM_CATEGORY_COLUMN)));
                    item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.ITEM_NAME_COLUMN)));
                    item.setItemImage(cursor.getBlob(cursor.getColumnIndex(Constants.ITEM_IMAGE_COLUMN)));
                    item.setItemDescription(cursor.getString(cursor.getColumnIndex(Constants.ITEM_DESCRIPTION_COLUMN)));
                    item.setItemIsFavourite(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FAVORITE_COLUMN)));
                    item.setItemNumberOfTimesOrdered(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN)));
                    item.setItemHowOftenIsAddedToCart(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN)));
                    item.setItemHaveSize(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HAVE_SIZE_COLUMN)));
                    item.setItemPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_AMOUNT_COLUMN)));
                    item.setItemRegularPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_REGULAR_AMOUNT_COLUMN)));
                    item.setItemMediumPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_MEDIUM_AMOUNT_COLUMN)));
                    item.setItemLargePrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_LARGE_AMOUNT_COLUMN)));
                    favouritesItems.add(item);
                } while (cursor.moveToNext());
            }
        }
        return favouritesItems;
    }

    @SuppressLint("Range")
    public ArrayList<Item> getBestsellersItem() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Item> bestSellersItem = new ArrayList<>();
//        "SELECT * FROM " + Constants.CATEGORY_TABLE_NAME + " LIMIT 1;"
//        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ITEM_TABLE_NAME, null);
        Cursor cursor = database.query(Constants.ITEM_TABLE_NAME, new String[]{Constants.ID_COLUMN, Constants.ITEM_FROM_CATEGORY_COLUMN,
                        Constants.ITEM_NAME_COLUMN, Constants.ITEM_IMAGE_COLUMN, Constants.ITEM_DESCRIPTION_COLUMN,
                        Constants.ITEM_FAVORITE_COLUMN, Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN,
                        Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN, Constants.ITEM_HAVE_SIZE_COLUMN, Constants.ITEM_AMOUNT_COLUMN,
                        Constants.ITEM_REGULAR_AMOUNT_COLUMN, Constants.ITEM_MEDIUM_AMOUNT_COLUMN, Constants.ITEM_LARGE_AMOUNT_COLUMN},
                Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN + " >= ?", new String[]{String.valueOf(2)},
                null, null, Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN + " DESC", new String(String.valueOf(10)));
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                    item.setItemFromCategoryId(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FROM_CATEGORY_COLUMN)));
                    item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.ITEM_NAME_COLUMN)));
                    item.setItemImage(cursor.getBlob(cursor.getColumnIndex(Constants.ITEM_IMAGE_COLUMN)));
                    item.setItemDescription(cursor.getString(cursor.getColumnIndex(Constants.ITEM_DESCRIPTION_COLUMN)));
                    item.setItemIsFavourite(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FAVORITE_COLUMN)));
                    item.setItemNumberOfTimesOrdered(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN)));
                    item.setItemHowOftenIsAddedToCart(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN)));
                    item.setItemHaveSize(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HAVE_SIZE_COLUMN)));
                    item.setItemPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_AMOUNT_COLUMN)));
                    item.setItemRegularPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_REGULAR_AMOUNT_COLUMN)));
                    item.setItemMediumPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_MEDIUM_AMOUNT_COLUMN)));
                    item.setItemLargePrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_LARGE_AMOUNT_COLUMN)));
                    bestSellersItem.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
        return bestSellersItem;
    }

    @SuppressLint("Range")
    public Item getItem(Item item) {
        SQLiteDatabase database = this.getReadableDatabase();
        Item returnItem = new Item();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ITEM_TABLE_NAME + " WHERE " +
                Constants.ID_COLUMN + " = " + item.getItemId(), null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                returnItem.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                returnItem.setItemFromCategoryId(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FROM_CATEGORY_COLUMN)));
                returnItem.setItemName(cursor.getString(cursor.getColumnIndex(Constants.ITEM_NAME_COLUMN)));
                returnItem.setItemImage(cursor.getBlob(cursor.getColumnIndex(Constants.ITEM_IMAGE_COLUMN)));
                returnItem.setItemDescription(cursor.getString(cursor.getColumnIndex(Constants.ITEM_DESCRIPTION_COLUMN)));
                returnItem.setItemIsFavourite(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FAVORITE_COLUMN)));
                returnItem.setItemNumberOfTimesOrdered(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN)));
                returnItem.setItemHowOftenIsAddedToCart(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN)));
                returnItem.setItemHaveSize(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HAVE_SIZE_COLUMN)));
                returnItem.setItemPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_AMOUNT_COLUMN)));
                returnItem.setItemRegularPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_REGULAR_AMOUNT_COLUMN)));
                returnItem.setItemMediumPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_MEDIUM_AMOUNT_COLUMN)));
                returnItem.setItemLargePrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_LARGE_AMOUNT_COLUMN)));
            }
        }
        cursor.close();
        database.close();
        return returnItem;
    }

    @SuppressLint("Range")
    public ArrayList<Item> getAllItems() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ITEM_TABLE_NAME, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                    item.setItemFromCategoryId(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FROM_CATEGORY_COLUMN)));
                    item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.ITEM_NAME_COLUMN)));
                    item.setItemImage(cursor.getBlob(cursor.getColumnIndex(Constants.ITEM_IMAGE_COLUMN)));
                    item.setItemDescription(cursor.getString(cursor.getColumnIndex(Constants.ITEM_DESCRIPTION_COLUMN)));
                    item.setItemIsFavourite(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FAVORITE_COLUMN)));
                    item.setItemNumberOfTimesOrdered(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN)));
                    item.setItemHowOftenIsAddedToCart(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN)));
                    item.setItemHaveSize(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HAVE_SIZE_COLUMN)));
                    item.setItemPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_AMOUNT_COLUMN)));
                    item.setItemRegularPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_REGULAR_AMOUNT_COLUMN)));
                    item.setItemMediumPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_MEDIUM_AMOUNT_COLUMN)));
                    item.setItemLargePrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_LARGE_AMOUNT_COLUMN)));
                    items.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
        return items;
    }

    @SuppressLint("Range")
    public ArrayList<Item> getAllCardsItems() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ITEM_TABLE_NAME + " WHERE " + Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN
                + " IS NOT 0 ", null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                    item.setItemFromCategoryId(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FROM_CATEGORY_COLUMN)));
                    item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.ITEM_NAME_COLUMN)));
                    item.setItemImage(cursor.getBlob(cursor.getColumnIndex(Constants.ITEM_IMAGE_COLUMN)));
                    item.setItemDescription(cursor.getString(cursor.getColumnIndex(Constants.ITEM_DESCRIPTION_COLUMN)));
                    item.setItemIsFavourite(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FAVORITE_COLUMN)));
                    item.setItemNumberOfTimesOrdered(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN)));
                    item.setItemHowOftenIsAddedToCart(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN)));
                    item.setItemHaveSize(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HAVE_SIZE_COLUMN)));
                    item.setItemPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_AMOUNT_COLUMN)));
                    item.setItemRegularPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_REGULAR_AMOUNT_COLUMN)));
                    item.setItemMediumPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_MEDIUM_AMOUNT_COLUMN)));
                    item.setItemLargePrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_LARGE_AMOUNT_COLUMN)));
                    items.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
        return items;
    }

    @SuppressLint("Range")
    public ArrayList<Item> getSpecificCategoryItems(int categoryId) {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = database.query(false, Constants.ITEM_TABLE_NAME, new String[]{Constants.ID_COLUMN,
                        Constants.ITEM_FROM_CATEGORY_COLUMN, Constants.ITEM_NAME_COLUMN, Constants.ITEM_IMAGE_COLUMN,
                        Constants.ITEM_DESCRIPTION_COLUMN, Constants.ITEM_FAVORITE_COLUMN, Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN,
                        Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN, Constants.ITEM_HAVE_SIZE_COLUMN,
                        Constants.ITEM_AMOUNT_COLUMN, Constants.ITEM_REGULAR_AMOUNT_COLUMN,
                        Constants.ITEM_MEDIUM_AMOUNT_COLUMN, Constants.ITEM_LARGE_AMOUNT_COLUMN},
                Constants.ITEM_FROM_CATEGORY_COLUMN + " =? ", new String[]{String.valueOf(categoryId)},
                null, null, null, null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    Item item = new Item();
                    item.setItemId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                    item.setItemFromCategoryId(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FROM_CATEGORY_COLUMN)));
                    item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.ITEM_NAME_COLUMN)));
                    item.setItemImage(cursor.getBlob(cursor.getColumnIndex(Constants.ITEM_IMAGE_COLUMN)));
                    item.setItemDescription(cursor.getString(cursor.getColumnIndex(Constants.ITEM_DESCRIPTION_COLUMN)));
                    item.setItemIsFavourite(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_FAVORITE_COLUMN)));
                    item.setItemNumberOfTimesOrdered(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_NUMBER_OF_TIME_ORDERED_COLUMN)));
                    item.setItemHowOftenIsAddedToCart(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN)));
                    item.setItemHaveSize(cursor.getInt(cursor.getColumnIndex(Constants.ITEM_HAVE_SIZE_COLUMN)));
                    item.setItemPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_AMOUNT_COLUMN)));
                    item.setItemRegularPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_REGULAR_AMOUNT_COLUMN)));
                    item.setItemMediumPrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_MEDIUM_AMOUNT_COLUMN)));
                    item.setItemLargePrice(cursor.getDouble(cursor.getColumnIndex(Constants.ITEM_LARGE_AMOUNT_COLUMN)));
                    items.add(item);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
        database.close();
        return items;
    }

    public int totalItemCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        int totalCount;
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ITEM_TABLE_NAME + " WHERE " + Constants.ITEM_HOW_OFTEN_IS_ADDED_TO_CARD_COLUMN
                + " IS NOT 0 ", null);
        totalCount = cursor.getCount();
        cursor.close();
        database.close();
        return totalCount;
    }

    public int totalFavouriteItemCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        int totalCount;
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ITEM_TABLE_NAME + " WHERE " + Constants.ITEM_FAVORITE_COLUMN
                + " IS NOT 0 ", null);
        totalCount = cursor.getCount();
        cursor.close();
        database.close();
        return totalCount;
    }


    public void addOffer(Offer offer) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.OFFER_STATE_COLUMN, offer.getOfferState());
        values.put(Constants.OFFER_NAME_COLUMN, offer.getOfferName());
        values.put(Constants.OFFER_DESCRIPTION_COLUMN, offer.getOfferDescription());
        values.put(Constants.OFFER_AMOUNT_COLUMN, offer.getOfferAmount());
        database.insert(Constants.OFFER_TABLE_NAME, null, values);
        database.close();
    }

    public void updateOffer(Offer offer) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.OFFER_STATE_COLUMN, offer.getOfferState());
        values.put(Constants.OFFER_NAME_COLUMN, offer.getOfferName());
        values.put(Constants.OFFER_DESCRIPTION_COLUMN, offer.getOfferDescription());
        values.put(Constants.OFFER_AMOUNT_COLUMN, offer.getOfferAmount());
        database.update(Constants.OFFER_TABLE_NAME, values, Constants.ID_COLUMN + " =? ",
                new String[]{String.valueOf(offer.getOfferId())});
        database.close();
    }

    public void deleteOffer(int id) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(Constants.OFFER_TABLE_NAME, Constants.ID_COLUMN + " =? ",
                new String[]{String.valueOf(id)});
        database.close();
    }

    @SuppressLint("Range")
    public Offer getSelectedOffer() {
        Offer offer = new Offer();
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(false, Constants.OFFER_TABLE_NAME, new String[]{Constants.ID_COLUMN, Constants.OFFER_STATE_COLUMN,
                        Constants.OFFER_NAME_COLUMN, Constants.OFFER_AMOUNT_COLUMN, Constants.OFFER_DESCRIPTION_COLUMN},
                Constants.OFFER_STATE_COLUMN + " = ? ", new String[]{String.valueOf(1)}, null, null, null, null);
        if (cursor.moveToFirst()) {
            offer.setOfferId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
            offer.setOfferState(cursor.getInt(cursor.getColumnIndex(Constants.OFFER_STATE_COLUMN)));
            offer.setOfferName(cursor.getString(cursor.getColumnIndex(Constants.OFFER_NAME_COLUMN)));
            offer.setOfferAmount(cursor.getDouble(cursor.getColumnIndex(Constants.OFFER_AMOUNT_COLUMN)));
            offer.setOfferDescription(cursor.getString(cursor.getColumnIndex(Constants.OFFER_DESCRIPTION_COLUMN)));
        }
        cursor.close();
        database.close();
        return offer;
    }

    @SuppressLint("Range")
    public ArrayList<Offer> getAllOffers() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Offer> dealsAndOffers = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.OFFER_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Offer offer = new Offer();
                offer.setOfferId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                offer.setOfferState(cursor.getInt(cursor.getColumnIndex(Constants.OFFER_STATE_COLUMN)));
                offer.setOfferName(cursor.getString(cursor.getColumnIndex(Constants.OFFER_NAME_COLUMN)));
                offer.setOfferDescription(cursor.getString(cursor.getColumnIndex(Constants.OFFER_DESCRIPTION_COLUMN)));
                offer.setOfferAmount(cursor.getDouble(cursor.getColumnIndex(Constants.OFFER_AMOUNT_COLUMN)));
                dealsAndOffers.add(offer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return dealsAndOffers;
    }

    public int totalOfferCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        int totalCount;
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.OFFER_TABLE_NAME, null);
        totalCount = cursor.getCount();
        cursor.close();
        database.close();
        return totalCount;
    }

    public void addOrder(Order order) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.ORDER_NAMES, order.getOrderNames());
        values.put(Constants.ORDER_PAYMENT_MODE, order.getPaymentMode());
        values.put(Constants.ORDER_DATE, System.currentTimeMillis());
        values.put(Constants.ORDER_AMOUNT, order.getOrderAmount());
        database.insert(Constants.ORDER_TABLE_NAME, null, values);
        database.close();
    }

    @SuppressLint("Range")
    public ArrayList<Order> getAllOrders() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Order> orders = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ORDER_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setOrderId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                order.setOrderNames(cursor.getString(cursor.getColumnIndex(Constants.ORDER_NAMES)));
                order.setPaymentMode(cursor.getString(cursor.getColumnIndex(Constants.ORDER_PAYMENT_MODE)));
                order.setOrderDate(cursor.getLong(cursor.getColumnIndex(Constants.ORDER_DATE)));
                order.setOrderAmount(cursor.getDouble(cursor.getColumnIndex(Constants.ORDER_AMOUNT)));
                orders.add(order);
            } while (cursor.moveToNext());
        }
        database.close();
        return orders;
    }

    public int totalOrderCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        int totalCount;
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.ORDER_TABLE_NAME, null);
        totalCount = cursor.getCount();
        cursor.close();
        database.close();
        return totalCount;
    }

    public void addUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constants.USER_EMAIL, user.getUserEmail());
        values.put(Constants.USER_PASSWORD, user.getUserPassword());
        values.put(Constants.USER_TYPE, user.getUserType());
        database.insert(Constants.USER_TABLE_NAME, null, values);
        database.close();
    }

    @SuppressLint("Range")
    public ArrayList<User> getAllUsers() {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<User> users = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.USER_TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setUserId(cursor.getInt(cursor.getColumnIndex(Constants.ID_COLUMN)));
                user.setUserEmail(cursor.getString(cursor.getColumnIndex(Constants.USER_EMAIL)));
                user.setUserPassword(cursor.getString(cursor.getColumnIndex(Constants.USER_PASSWORD)));
                user.setUserType(cursor.getString(cursor.getColumnIndex(Constants.USER_TYPE)));
                users.add(user);
            } while (cursor.moveToNext());
        }
        database.close();
        return users;
    }

    public int totalUserCount() {
        SQLiteDatabase database = this.getReadableDatabase();
        int totalCount;
        Cursor cursor = database.rawQuery("SELECT * FROM " + Constants.USER_TABLE_NAME, null);
        totalCount = cursor.getCount();
        cursor.close();
        database.close();
        Log.d("TagCount = ", totalCount + "");
        return totalCount;
    }
}
