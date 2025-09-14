package com.emad.restaurantproject.database.data;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.emad.restaurantproject.R;
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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        User.class, Category.class, MenuItem.class, Order.class, OrderItem.class, CartItem.class}
        , version = 1, exportSchema = false)
public abstract class RestaurantDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract MenuItemDao menuItemDao();

    public abstract OrderDao orderDao();

    public abstract OrderItemDao orderItemDao();

    public abstract CartItemDao cartItemDao();

    private static volatile RestaurantDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RestaurantDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (RestaurantDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RestaurantDatabase.class, "restaurant_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();

                }
            }
        }
        return INSTANCE;
    }

    // لتخزين البيانات عند فتح التطبيق اول مرة
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("INSERT INTO user_table (name, email, password, userType) VALUES ('Admin', 'admin@gmail.com', 'adminadmin', 'owner')");

            databaseWriteExecutor.execute(() -> {

                CategoryDao categoryDao = INSTANCE.categoryDao();

                categoryDao.insertCategory(new Category("Main Dishes", R.drawable.main_dish));
                categoryDao.insertCategory(new Category("Starters", R.drawable.starters));
                categoryDao.insertCategory(new Category("Drinks", R.drawable.drinks));
                categoryDao.insertCategory(new Category("Desserts", R.drawable.desserts));
                categoryDao.insertCategory(new Category("Extras", R.drawable.extras));

            });
        }
    };

}