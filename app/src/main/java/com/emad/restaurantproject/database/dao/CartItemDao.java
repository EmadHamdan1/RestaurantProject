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

    @Query("SELECT * FROM cart_item_table WHERE customerId  =:customerId")
    List<CartItem> getCartItemByCustomerId(int customerId);

    @Query("SELECT * FROM cart_item_table WHERE customerId  =:customerId")
    LiveData<List<CartItem>> getCartItemByCustomerIdLive(int customerId);


    @Query("SELECT * FROM cart_item_table WHERE menuItemId  =:menuItemId And customerId=:customerId")
    CartItem getCartItemByMenuItemId(int menuItemId, int customerId);

    @Query("DELETE FROM cart_item_table WHERE customerId=:customerId")
    void clearCart(int customerId);


}
