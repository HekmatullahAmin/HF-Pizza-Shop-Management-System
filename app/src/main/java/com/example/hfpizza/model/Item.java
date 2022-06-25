package com.example.hfpizza.model;

public class Item {
//    0 means not favourite
    private int itemId, itemFromCategoryId, itemIsFavourite = 0, itemHaveSize, itemHowOftenIsAddedToCart = 0,
        itemNumberOfTimesOrdered;
    private String itemName, itemDescription;
    private byte[] itemImage;
    private double itemPrice, itemRegularPrice, itemMediumPrice, itemLargePrice;

    public Item() {
    }

    public Item(int itemId, int itemFromCategoryId, int itemIsFavourite, int itemHaveSize,
                int itemHowOftenIsAddedToCart, int itemNumberOfTimesOrdered, String itemName,
                String itemDescription, byte[] itemImage, double itemPrice, double itemRegularPrice,
                double itemMediumPrice, double itemLargePrice) {
        this.itemId = itemId;
        this.itemFromCategoryId = itemFromCategoryId;
        this.itemIsFavourite = itemIsFavourite;
        this.itemHaveSize = itemHaveSize;
        this.itemHowOftenIsAddedToCart = itemHowOftenIsAddedToCart;
        this.itemNumberOfTimesOrdered = itemNumberOfTimesOrdered;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.itemImage = itemImage;
        this.itemPrice = itemPrice;
        this.itemRegularPrice = itemRegularPrice;
        this.itemMediumPrice = itemMediumPrice;
        this.itemLargePrice = itemLargePrice;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getItemFromCategoryId() {
        return itemFromCategoryId;
    }

    public void setItemFromCategoryId(int itemFromCategoryId) {
        this.itemFromCategoryId = itemFromCategoryId;
    }

    public int getItemIsFavourite() {
        return itemIsFavourite;
    }

    public void setItemIsFavourite(int itemIsFavourite) {
        this.itemIsFavourite = itemIsFavourite;
    }

    public int getItemHaveSize() {
        return itemHaveSize;
    }

    public void setItemHaveSize(int itemHaveSize) {
        this.itemHaveSize = itemHaveSize;
    }

    public int getItemHowOftenIsAddedToCart() {
        return itemHowOftenIsAddedToCart;
    }

    public void setItemHowOftenIsAddedToCart(int itemHowOftenIsAddedToCart) {
        this.itemHowOftenIsAddedToCart = itemHowOftenIsAddedToCart;
    }

    public int getItemNumberOfTimesOrdered() {
        return itemNumberOfTimesOrdered;
    }

    public void setItemNumberOfTimesOrdered(int itemNumberOfTimesOrdered) {
        this.itemNumberOfTimesOrdered = itemNumberOfTimesOrdered;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public byte[] getItemImage() {
        return itemImage;
    }

    public void setItemImage(byte[] itemImage) {
        this.itemImage = itemImage;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public double getItemRegularPrice() {
        return itemRegularPrice;
    }

    public void setItemRegularPrice(double itemRegularPrice) {
        this.itemRegularPrice = itemRegularPrice;
    }

    public double getItemMediumPrice() {
        return itemMediumPrice;
    }

    public void setItemMediumPrice(double itemMediumPrice) {
        this.itemMediumPrice = itemMediumPrice;
    }

    public double getItemLargePrice() {
        return itemLargePrice;
    }

    public void setItemLargePrice(double itemLargePrice) {
        this.itemLargePrice = itemLargePrice;
    }
}
