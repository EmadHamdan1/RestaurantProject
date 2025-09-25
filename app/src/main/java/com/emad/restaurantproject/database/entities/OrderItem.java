package com.emad.restaurantproject.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.emad.restaurantproject.utils.Converters;

@Entity(tableName = "order_items_table",
        foreignKeys = {
                @ForeignKey(entity = Order.class, parentColumns = {"orderId"}, childColumns = {"orderId"}),
                @ForeignKey(entity = MenuItem.class, parentColumns = {"menuItemId"}, childColumns = {"menuItemId"},onDelete = ForeignKey.CASCADE)})

@TypeConverters(Converters.class)
public class OrderItem {

    @PrimaryKey(autoGenerate = true)
    private int orderItemId;
    private int orderId;
    private int menuItemId;
    private int quantity;
    private double itemTotalPrice;

    public OrderItem(int orderId, int menuItemId, int quantity, double itemTotalPrice) {
        this.orderId = orderId;
        this.menuItemId = menuItemId;
        this.quantity = quantity;
        this.itemTotalPrice = itemTotalPrice;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    public double getItemTotalPrice() {
        return itemTotalPrice;
    }

    public void setItemTotalPrice(double itemTotalPrice) {
        this.itemTotalPrice = itemTotalPrice;
    }
}
