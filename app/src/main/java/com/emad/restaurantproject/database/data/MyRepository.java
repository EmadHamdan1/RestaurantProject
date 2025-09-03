package com.emad.restaurantproject.database.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.emad.restaurantproject.database.dao.CartItemDao;
import com.emad.restaurantproject.database.dao.CategoryDao;
import com.emad.restaurantproject.database.dao.MenuItemDao;
import com.emad.restaurantproject.database.dao.OrderDao;
import com.emad.restaurantproject.database.dao.OrderItemDao;
import com.emad.restaurantproject.database.dao.UserDao;
import com.emad.restaurantproject.database.entities.CartItem;
import com.emad.restaurantproject.database.entities.Category;
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

    public MyRepository(Application application) {
        RestaurantDatabase db = RestaurantDatabase.getDatabase(application);
        userDao = db.userDao();
        categoryDao = db.categoryDao();
        menuItemDao = db.menuItemDao();
        orderDao = db.orderDao();
        orderItemDao = db.orderItemDao();
        cartItemDao = db.cartItemDao();
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

    public LiveData<MenuItem> getMenuItemById(int menuItemId) {
        return menuItemDao.getMenuItemById(menuItemId);
    }


    // Order Method

    public void insertOrder(Order order) {
        RestaurantDatabase.databaseWriteExecutor.execute(() -> {
            orderDao.insertOrder(order);
        });
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

    public LiveData<OrderItem> getOrderItemById(int orderItemId) {
        return orderItemDao.getOrderItemById(orderItemId);
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

    public LiveData<CartItem> getCartItemById(int cartItemId) {
        return cartItemDao.getCartItemById(cartItemId);
    }

}
