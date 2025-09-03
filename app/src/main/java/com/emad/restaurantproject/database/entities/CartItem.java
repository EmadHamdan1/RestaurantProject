package com.emad.restaurantproject.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_item_table",
        foreignKeys = {@ForeignKey(entity = MenuItem.class, parentColumns = {"menuItemId"}, childColumns = {"menuItemId"})})
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int cartItemId;
    private int menuItemId;
    private int quantity;
    private double itemPrice;

    public CartItem(int menuItemId, int quantity, double itemPrice) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
    }

    public int getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(int cartItemId) {
        this.cartItemId = cartItemId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }
}
