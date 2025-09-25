package com.emad.restaurantproject.database.data;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.emad.restaurantproject.database.dao.CartItemDao;
import com.emad.restaurantproject.database.dao.CategoryDao;
import com.emad.restaurantproject.database.dao.FavoriteDao;
import com.emad.restaurantproject.database.dao.MenuItemDao;
import com.emad.restaurantproject.database.dao.OrderDao;
import com.emad.restaurantproject.database.dao.OrderItemDao;
import com.emad.restaurantproject.database.dao.UserDao;
import com.emad.restaurantproject.database.entities.CartItem;
import com.emad.restaurantproject.database.entities.Category;
import com.emad.restaurantproject.database.entities.Favorite;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.database.entities.Order;
import com.emad.restaurantproject.database.entities.OrderItem;
import com.emad.restaurantproject.database.entities.User;

import java.util.List;

public class MyRepository {

    private final UserDao userDao;
    private final CategoryDao categoryDao;
    private final MenuItemDao menuItemDao;
    private final OrderDao orderDao;
    private final OrderItemDao orderItemDao;
    private final CartItemDao cartItemDao;
    private final FavoriteDao favoriteDao;

    public MyRepository(Application application) {
        RestaurantDatabase db = RestaurantDatabase.getDatabase(application);
        userDao = db.userDao();
        categoryDao = db.categoryDao();
        menuItemDao = db.menuItemDao();
        orderDao = db.orderDao();
        orderItemDao = db.orderItemDao();
        cartItemDao = db.cartItemDao();
        favoriteDao = db.favoriteDao();
    }

    // User Method

    public void insertUser(User user) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            userDao.insertUser(user);
        });
    }

    public void updateUser(User user) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            userDao.updateUser(user);
        });
    }

    public void deleteUser(User user) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            userDao.deleteUser(user);
        });
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public LiveData<User> getUserById(int userId) {
        return userDao.getUserById(userId);
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }


    // Category Method

    public void insertCategory(Category category) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.insertCategory(category);
        });
    }

    public void updateCategory(Category category) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.updateCategory(category);
        });
    }


    public void deleteCategory(Category category) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            categoryDao.deleteCategory(category);
        });
    }


    public LiveData<List<Category>> getAllCategories() {
        return categoryDao.getAllCategories();
    }

    public List<Category> getAllCategoriesList() {
        return categoryDao.getAllCategoriesList();
    }


    public LiveData<Category> getCategoryById(int categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }

    // MenuItem Method

    public void insertMenuItem(MenuItem item) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            menuItemDao.insertMenuItem(item);
        });
    }

    public void updateMenuItem(MenuItem item) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            menuItemDao.updateMenuItem(item);
        });
    }

    public void deleteMenuItem(MenuItem item) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            menuItemDao.deleteMenuItem(item);
        });
    }

    public LiveData<List<MenuItem>> getAllMenuItems() {
        return menuItemDao.getAllMenuItems();
    }

    public LiveData<List<MenuItem>> getAllMenuItemsByName(String name) {
        return menuItemDao.getAllMenuItemsByName(name);
    }

    public LiveData<List<MenuItem>> getMenuItemsByCategoryId(int categoryId) {
        return menuItemDao.getMenuItemsByCategoryId(categoryId);
    }

    public LiveData<MenuItem> getMenuItemById(int menuItemId) {
        return menuItemDao.getMenuItemById(menuItemId);
    }


    // Order Method

    public long insertOrder(Order order) {
        return orderDao.insertOrder(order);
    }

    public void updateOrder(Order order) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            orderDao.updateOrder(order);
        });
    }

    public void deleteOrder(Order order) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            orderDao.deleteOrder(order);
        });
    }

    public LiveData<List<Order>> getAllOrders() {
        return orderDao.getAllOrders();
    }

    public LiveData<Order> getOrderById(int orderId) {
        return orderDao.getOrderById(orderId);
    }

    public LiveData<Order> getOrderByCustomerId(int customerId) {
        return orderDao.getOrderByCustomerId(customerId);
    }

    public LiveData<List<Order>> getOrdersByCustomerId(int customerId) {
        return orderDao.getOrdersByCustomerId(customerId);
    }

    public Integer getLastOrderNumberForUser(int customerId) {
        return orderDao.getLastOrderNumberForUser(customerId);
    }

    // OrderItem Method

    public void insertOrderItem(OrderItem orderItem) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            orderItemDao.insertOrderItem(orderItem);
        });
    }

    public void updateOrderItem(OrderItem orderItem) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            orderItemDao.updateOrderItem(orderItem);
        });
    }

    public void deleteOrderItem(OrderItem orderItem) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            orderItemDao.deleteOrderItem(orderItem);
        });
    }

    public LiveData<List<OrderItem>> getAllOrderItems() {
        return orderItemDao.getAllOrderItems();
    }

    public LiveData<List<OrderItem>> getOrderItemByOrderId(int orderId) {
        return orderItemDao.getOrderItemByOrderId(orderId);
    }

    // CartItem Method

    public void insertCartItem(CartItem cartItem) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            cartItemDao.insertCartItem(cartItem);
        });
    }

    public void updateCartItem(CartItem cartItem) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            cartItemDao.updateCartItem(cartItem);
        });
    }

    public void deleteCartItem(CartItem cartItem) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            cartItemDao.deleteCartItem(cartItem);
        });
    }

    public LiveData<List<CartItem>> getAllCartItems() {
        return cartItemDao.getAllCartItems();
    }

    public CartItem getCartItemByMenuItemId(int menuItemId, int customerId) {
        return cartItemDao.getCartItemByMenuItemId(menuItemId, customerId);
    }

    public List<CartItem> getCartItemByCustomerId(int customerId) {
        return cartItemDao.getCartItemByCustomerId(customerId);
    }

    public LiveData<List<CartItem>> getCartItemByCustomerIdLive(int customerId) {
        return cartItemDao.getCartItemByCustomerIdLive(customerId);
    }

    public void clearCart(int customerId) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            cartItemDao.clearCart(customerId);
        });
    }


    // Favorite Method


    public void insertFavoriteItem(Favorite favorite) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            favoriteDao.insertFavoriteItem(favorite);
        });
    }


    public void updateFavoriteItem(Favorite favorite) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            favoriteDao.updateFavoriteItem(favorite);
        });
    }


    public void deleteFavoriteItem(Favorite favorite) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            favoriteDao.deleteFavoriteItem(favorite);
        });
    }

    public LiveData<List<Favorite>> getAllFavoriteItem() {
        return favoriteDao.getAllFavoriteItem();
    }

    public LiveData<List<Favorite>> getFavoriteItemsByUserId(int customerId) {
        return favoriteDao.getFavoriteItemsByUserId(customerId);
    }

    public LiveData<List<MenuItem>> getFavoriteMenuItemsByUserId(int customerId) {
        return favoriteDao.getFavoriteMenuItemsByUserId(customerId);
    }

    public Favorite getFavoriteItemByUserIdAndItemId(int customerId, int menuItemId) {
        return favoriteDao.getFavoriteItemByUserIdAndItemId(customerId, menuItemId);
    }

    public LiveData<Favorite> getFavoriteItemByUserIdAndItemIdLive(int customerId, int menuItemId) {
        return favoriteDao.getFavoriteItemByUserIdAndItemIdLive(customerId, menuItemId);
    }

//    public void clearFavorite(int customerId) {
//        favoriteDao.clearFavorite(customerId);
//    }

}
