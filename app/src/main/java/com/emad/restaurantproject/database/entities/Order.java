package com.emad.restaurantproject.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.emad.restaurantproject.utils.Converters;

import java.util.Date;

@Entity(tableName = "order_table",
        foreignKeys = {@ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"customerId"})})
@TypeConverters(Converters.class)
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int orderId;
    private Date date;
    private double totalPrice;
    private String status;
    private int orderNumber;
    private int customerId;

    public Order(double totalPrice, String status, int customerId, Date date, int orderNumber) {
        this.totalPrice = totalPrice;
        this.status = status;
        this.customerId = customerId;
        this.date = date;
        this.orderNumber = orderNumber;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
