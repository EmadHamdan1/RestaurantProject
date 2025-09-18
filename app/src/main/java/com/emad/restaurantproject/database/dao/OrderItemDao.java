package com.emad.restaurantproject.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.emad.restaurantproject.database.entities.OrderItem;

import java.util.List;

@Dao
public interface OrderItemDao {

    @Insert
    void insertOrderItem(OrderItem orderItem);

    @Update
    void updateOrderItem(OrderItem orderItem);

    @Delete
    void deleteOrderItem(OrderItem orderItem);

    @Query("SELECT * FROM order_items_table")
    LiveData<List<OrderItem>> getAllOrderItems();

    @Query("SELECT * FROM order_items_table WHERE orderId  =:orderId")
    LiveData<List<OrderItem>> getOrderItemByOrderId(int orderId);



}
