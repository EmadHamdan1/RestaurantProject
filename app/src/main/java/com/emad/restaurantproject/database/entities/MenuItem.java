package com.emad.restaurantproject.database.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.emad.restaurantproject.utils.Converters;

import java.io.Serializable;

@Entity(tableName = "menu_item_table",
        foreignKeys = {@ForeignKey(entity = Category.class, parentColumns = {"categoryId"}, childColumns = {"categoryId"})})
@TypeConverters(Converters.class)
public class MenuItem implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int menuItemId;
    private String name;
    private int calories;
    private double price;
    private String imageUri;
    private String description;

    private int categoryId;

    @Ignore
    public MenuItem(int menuItemId, String name, double price, int calories, String imageUri, int categoryId, String description) {
        this.menuItemId = menuItemId;
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.imageUri = imageUri;
        this.categoryId = categoryId;
        this.description = description;
    }

    public MenuItem(String name, double price, int calories, String imageUri, int categoryId, String description) {
        this.name = name;
        this.calories = calories;
        this.price = price;
        this.imageUri = imageUri;
        this.categoryId = categoryId;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }


    @Override
    public String toString() {
        return "MenuItem{" +
                "menuItemId=" + menuItemId +
                ", name='" + name + '\'' +
                ", calories=" + calories +
                ", price=" + price +
                ", imageUri='" + imageUri + '\'' +
                ", categoryId=" + categoryId +
                '}';
    }
}
