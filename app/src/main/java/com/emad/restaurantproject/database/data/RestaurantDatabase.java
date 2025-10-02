package com.emad.restaurantproject.database.data;


import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
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

import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {
        User.class, Category.class, MenuItem.class, Order.class, OrderItem.class, CartItem.class, Favorite.class}
        , version = 1, exportSchema = false)
public abstract class RestaurantDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract MenuItemDao menuItemDao();

    public abstract OrderDao orderDao();

    public abstract OrderItemDao orderItemDao();

    public abstract CartItemDao cartItemDao();

    public abstract FavoriteDao favoriteDao();

    private static volatile RestaurantDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static RestaurantDatabase getDatabase(final Context context) {
        appContext = context;
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

    private static Context appContext;
    // لتخزين البيانات عند فتح التطبيق اول مرة
    private static final RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            db.execSQL("INSERT INTO user_table (name, email, password, userType) VALUES ('Admin', 'admin@gmail.com', 'adminadmin', 'owner')");

            databaseWriteExecutor.execute(() -> {

                CategoryDao categoryDao = INSTANCE.categoryDao();
                MenuItemDao menuItemDao = INSTANCE.menuItemDao();

                categoryDao.insertCategory(new Category(appContext.getString(R.string.main_dishes), R.drawable.main_dish));
                categoryDao.insertCategory(new Category(appContext.getString(R.string.starters), R.drawable.starters));
                categoryDao.insertCategory(new Category(appContext.getString(R.string.drinks), R.drawable.drinks));
                categoryDao.insertCategory(new Category(appContext.getString(R.string.desserts), R.drawable.desserts));
                categoryDao.insertCategory(new Category(appContext.getString(R.string.extras), R.drawable.extras));


                String imageUri1 = Uri.parse("android.resource://" + appContext.getPackageName()
                        + "/" + R.drawable.burger).toString();
                menuItemDao.insertMenuItem(new MenuItem("Burger", 20, 200, imageUri1, 1, "The best burger in the world"));

                String imageUri2 = Uri.parse("android.resource://" + appContext.getPackageName()
                        + "/" + R.drawable.peza2).toString();
                menuItemDao.insertMenuItem(new MenuItem("Pizza", 15, 150, imageUri2, 1, "The best Pizza in the world"));

                String imageUri3 = Uri.parse("android.resource://" + appContext.getPackageName()
                        + "/" + R.drawable.jaj_makli).toString();

                menuItemDao.insertMenuItem(new MenuItem("Prosted", 25, 300, imageUri3, 2, "The best Prosted in the world"));

                String imageUri4 = Uri.parse("android.resource://" + appContext.getPackageName()
                        + "/" + R.drawable.jaj2).toString();
                menuItemDao.insertMenuItem(new MenuItem("Chicken", 40, 400, imageUri4, 1, "The best Chicken in the world"));

                String imageUri5 = Uri.parse("android.resource://" + appContext.getPackageName()
                        + "/" + R.drawable.noodles).toString();
                menuItemDao.insertMenuItem(new MenuItem("Noodles", 10, 150, imageUri5, 5, "The best Noodles in the world"));

                String imageUri6 = Uri.parse("android.resource://" + appContext.getPackageName()
                        + "/" + R.drawable.potito).toString();
                menuItemDao.insertMenuItem(new MenuItem("Frid", 10, 100, imageUri6, 2, "The best Frid in the world"));

            });
        }
    };

}