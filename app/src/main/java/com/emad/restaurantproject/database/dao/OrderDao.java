package com.emad.restaurantproject.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.emad.restaurantproject.database.entities.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void insertOrder(Order order);

    @Update
    void updateOrder(Order order);

    @Delete
    void deleteOrder(Order order);

    @Query("SELECT * FROM order_table")
    LiveData<List<Order>> getAllOrders();

    @Query("SELECT * FROM order_table WHERE orderId =:orderId")
    LiveData<Order> getOrderById(int orderId);


}

