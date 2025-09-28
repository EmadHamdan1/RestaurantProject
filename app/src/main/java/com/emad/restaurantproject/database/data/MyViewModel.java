package com.emad.restaurantproject.database.data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emad.restaurantproject.database.entities.CartItem;
import com.emad.restaurantproject.database.entities.Category;
import com.emad.restaurantproject.database.entities.Favorite;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.database.entities.Order;
import com.emad.restaurantproject.database.entities.OrderItem;
import com.emad.restaurantproject.database.entities.User;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private LiveData<List<Order>> ordersLiveData;
    private LiveData<List<OrderItem>> orderItemsLiveData;
    private LiveData<List<MenuItem>> menuItemsLiveData;
    MyRepository repository;

    public MyViewModel(@NonNull Application application) {
        super(application);
        repository = new MyRepository(application);
    }

    public void loadAllData(int userId) {
        ordersLiveData = repository.getOrdersByCustomerId(userId);
        orderItemsLiveData = repository.getAllOrderItems();
        menuItemsLiveData = repository.getAllMenuItems();
    }

    // User Method

    public void insertUser(User user) {
        repository.insertUser(user);
    }

    public void updateUser(User user) {
        repository.updateUser(user);
    }

    public void deleteUser(User user) {
        repository.deleteUser(user);
    }

    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

    public LiveData<User> getUserById(int userId) {
        return repository.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return repository.getUserByEmail(email);
    }


    // Category Method

    public void insertCategory(Category category) {
        repository.insertCategory(category);
    }

    public void updateCategory(Category category) {
        repository.updateCategory(category);
    }


    public void deleteCategory(Category category) {
        repository.deleteCategory(category);
    }


    public LiveData<List<Category>> getAllCategories() {
        return repository.getAllCategories();
    }


    public LiveData<Category> getCategoryById(int categoryId) {
        return repository.getCategoryById(categoryId);
    }

    public List<Category> getAllCategoriesList() {
        return repository.getAllCategoriesList();
    }


    // MenuItem Method

    public void insertMenuItem(MenuItem item) {
        repository.insertMenuItem(item);
    }

    public void updateMenuItem(MenuItem item) {
        repository.updateMenuItem(item);
    }

    public void deleteMenuItem(MenuItem item) {
        repository.deleteMenuItem(item);
    }

    public LiveData<List<MenuItem>> getAllMenuItems() {
        return repository.getAllMenuItems();
    }

    public LiveData<List<MenuItem>> getAllMenuItemsByName(String name) {
        return repository.getAllMenuItemsByName(name);
    }

    public LiveData<List<MenuItem>> getMenuItemsByCategoryId(int categoryId) {
        return repository.getMenuItemsByCategoryId(categoryId);
    }

    public LiveData<MenuItem> getMenuItemById(int menuItemId) {
        return repository.getMenuItemById(menuItemId);
    }

    // Order Method

    public long insertOrder(Order order) {
        return repository.insertOrder(order);
    }

    public void updateOrder(Order order) {
        repository.updateOrder(order);
    }

    public void deleteOrder(Order order) {
        repository.deleteOrder(order);
    }

    public LiveData<List<Order>> getAllOrders() {
        return repository.getAllOrders();
    }

    public LiveData<Order> getOrderById(int orderId) {
        return repository.getOrderById(orderId);
    }

    public LiveData<Order> getOrderByCustomerId(int customerId) {
        return repository.getOrderByCustomerId(customerId);
    }

    public LiveData<List<Order>> getOrdersByCustomerId(int customerId) {
        return repository.getOrdersByCustomerId(customerId);
    }

    public Integer getLastOrderNumberForUser(int customerId) {
        return repository.getLastOrderNumberForUser(customerId);
    }

    // OrderItem Method

    public void insertOrderItem(OrderItem orderItem) {
        repository.insertOrderItem(orderItem);
    }

    public void updateOrderItem(OrderItem orderItem) {
        repository.updateOrderItem(orderItem);
    }

    public void deleteOrderItem(OrderItem orderItem) {
        repository.deleteOrderItem(orderItem);
    }

    public LiveData<List<OrderItem>> getAllOrderItems() {
        return repository.getAllOrderItems();
    }

    public LiveData<List<OrderItem>> getOrderItemByOrderId(int orderId) {
        return repository.getOrderItemByOrderId(orderId);
    }

    // CartItem Method

    public void insertCartItem(CartItem cartItem) {
        repository.insertCartItem(cartItem);
    }

    public void updateCartItem(CartItem cartItem) {
        repository.updateCartItem(cartItem);
    }

    public void deleteCartItem(CartItem cartItem) {
        repository.deleteCartItem(cartItem);
    }

    public LiveData<List<CartItem>> getAllCartItems() {
        return repository.getAllCartItems();
    }

    public CartItem getCartItemByMenuItemId(int menuItemId, int customerId) {
        return repository.getCartItemByMenuItemId(menuItemId, customerId);
    }

    public List<CartItem> getCartItemByCustomerId(int customerId) {
        return repository.getCartItemByCustomerId(customerId);
    }

    public LiveData<List<CartItem>> getCartItemByCustomerIdLive(int customerId) {
        return repository.getCartItemByCustomerIdLive(customerId);
    }

    public void clearCart(int customerId) {
        repository.clearCart(customerId);
    }


    // Favorite Method

    public void insertFavoriteItem(Favorite favorite) {
        repository.insertFavoriteItem(favorite);
    }

    public void updateFavoriteItem(Favorite favorite) {
        repository.updateFavoriteItem(favorite);
    }

    public void deleteFavoriteItem(Favorite favorite) {
        repository.deleteFavoriteItem(favorite);
    }

    public LiveData<List<Favorite>> getAllFavoriteItem() {
        return repository.getAllFavoriteItem();
    }

    public LiveData<List<Favorite>> getFavoriteItemsByUserId(int customerId) {
        return repository.getFavoriteItemsByUserId(customerId);
    }

    public LiveData<List<MenuItem>> getFavoriteMenuItemsByUserId(int customerId) {
        return repository.getFavoriteMenuItemsByUserId(customerId);
    }

    public Favorite getFavoriteItemByUserIdAndItemId(int customerId, int menuItemId) {
        return repository.getFavoriteItemByUserIdAndItemId(customerId, menuItemId);
    }

    public LiveData<Favorite> getFavoriteItemByUserIdAndItemIdLive(int customerId, int menuItemId){
        return repository.getFavoriteItemByUserIdAndItemIdLive(customerId, menuItemId);
    }

//    public void clearFavorite(int customerId) {
//        repository.clearFavorite(customerId);
//    }

}
