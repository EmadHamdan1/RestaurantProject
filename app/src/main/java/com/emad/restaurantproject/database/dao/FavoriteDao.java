package com.emad.restaurantproject.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.emad.restaurantproject.database.entities.Favorite;
import com.emad.restaurantproject.database.entities.MenuItem;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFavoriteItem(Favorite favorite);

    @Update
    void updateFavoriteItem(Favorite favorite);

    @Delete
    void deleteFavoriteItem(Favorite favorite);

    @Query("SELECT * FROM favorite_table")
    LiveData<List<Favorite>> getAllFavoriteItem();

    @Query("SELECT * FROM favorite_table WHERE customerId =:customerId")
    LiveData<List<Favorite>> getFavoriteItemsByUserId(int customerId);

    @Query("SELECT m.* FROM menu_item_table m " +
            "INNER JOIN favorite_table f ON m.menuItemId = f.menuItemId " +
            "WHERE f.customerId = :customerId")
    LiveData<List<MenuItem>> getFavoriteMenuItemsByUserId(int customerId);

    @Query("SELECT * FROM favorite_table WHERE customerId =:customerId AND menuItemId =:menuItemId Limit 1")
    Favorite getFavoriteItemByUserIdAndItemId(int customerId, int menuItemId);

    @Query("SELECT * FROM favorite_table WHERE customerId =:customerId AND menuItemId =:menuItemId ")
    LiveData<Favorite> getFavoriteItemByUserIdAndItemIdLive(int customerId, int menuItemId);

//    @Query("DELETE FROM cart_item_table WHERE customerId=:customerId")
//    void clearFavorite(int customerId);
}
