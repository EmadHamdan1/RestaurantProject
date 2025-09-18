package com.emad.restaurantproject.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_item_table",
        foreignKeys = {
                @ForeignKey(entity = MenuItem.class, parentColumns = {"menuItemId"}, childColumns = {"menuItemId"}),
                @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"customerId"})})
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int cartItemId;
    private int menuItemId;
    private int quantity;
    private double itemPrice;
    private int customerId;

    public CartItem(int menuItemId, int quantity, double itemPrice, int customerId) {
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.customerId = customerId;
    }

    @Ignore
    public CartItem(int cartItemId, int menuItemId, int quantity, double itemPrice, int customerId) {
        this.cartItemId = cartItemId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.customerId = customerId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    public double getTotalPrice() {
        return quantity * itemPrice;
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
