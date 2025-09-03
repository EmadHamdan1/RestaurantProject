package com.emad.restaurantproject.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.emad.restaurantproject.database.entities.CartItem;

import java.util.List;

@Dao
public interface CartItemDao {

    @Insert
    void insertCartItem(CartItem cartItem);

    @Update
    void updateCartItem(CartItem cartItem);

    @Delete
    void deleteCartItem(CartItem cartItem);

    @Query("SELECT * FROM cart_item_table")
    LiveData<List<CartItem>> getAllCartItems();

    @Query("SELECT * FROM cart_item_table WHERE cartItemId  =:cartItemId")
    LiveData<CartItem> getCartItemById(int cartItemId);


}
