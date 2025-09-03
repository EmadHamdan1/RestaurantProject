package com.emad.restaurantproject.database.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.emad.restaurantproject.utils.Converters;

@Entity(tableName = "menu_item_table",
        foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = {"categoryId"}, childColumns = {"categoryId"})})
@TypeConverters(Converters.class)
public class MenuItem {

    @PrimaryKey(autoGenerate = true)
    private int menuItemId;
    private String name;
    private double price;
    private Bitmap image;
    private int categoryId;

    public MenuItem(String name, double price, Bitmap image, int categoryId) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.categoryId = categoryId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
}
