package com.emad.restaurantproject.database.entities;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.emad.restaurantproject.utils.Converters;

@Entity(tableName = "category_table")
@TypeConverters(Converters.class)
public class Category {

    @PrimaryKey(autoGenerate = true)
    private int categoryId;
    private String name;
    private Bitmap image;

    public Category(String name, Bitmap image) {
        this.name = name;
        this.image = image;
    }

    @Ignore
    public Category(String name) {
        this.name = name;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
