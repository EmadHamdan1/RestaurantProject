package com.emad.restaurantproject.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.emad.restaurantproject.database.entities.MenuItem;

import java.util.List;

@Dao
public interface MenuItemDao {

    @Insert
    void insertMenuItem(MenuItem item);

    @Update
    void updateMenuItem(MenuItem item);

    @Delete
    void deleteMenuItem(MenuItem item);

    @Query("SELECT * FROM menu_item_table")
    LiveData<List<MenuItem>> getAllMenuItems();

    @Query("SELECT * FROM menu_item_table WHERE name LIKE '%' || :name || '%'")
    LiveData<List<MenuItem>> getAllMenuItemsByName(String name);

    @Query("SELECT * FROM menu_item_table WHERE categoryId =:categoryId")
    LiveData<List<MenuItem>> getMenuItemsByCategoryId(int categoryId);

    @Query("SELECT * FROM menu_item_table WHERE menuItemId =:menuItemId")
    LiveData<MenuItem> getMenuItemById(int menuItemId);


}
