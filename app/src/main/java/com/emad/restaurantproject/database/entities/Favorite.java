package com.emad.restaurantproject.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table",
        foreignKeys = {
                @ForeignKey(entity = MenuItem.class, parentColumns = {"menuItemId"}, childColumns = {"menuItemId"}),
                @ForeignKey(entity = User.class, parentColumns = {"userId"}, childColumns = {"customerId"})})
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    private int favoriteId;
    private int menuItemId;
    private int customerId;

    public Favorite(int menuItemId, int customerId) {
        this.menuItemId = menuItemId;
        this.customerId = customerId;
    }


    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(int menuItemId) {
        this.menuItemId = menuItemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
