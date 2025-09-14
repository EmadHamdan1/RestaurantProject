package com.emad.restaurantproject.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emad.restaurantproject.database.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertCategory(Category category);

    @Update
    void updateCategory(Category category);

    @Delete
    void deleteCategory(Category category);

    @Query("SELECT * FROM category_table")
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM category_table")
    List<Category> getAllCategoriesList();

    @Query("SELECT * FROM category_table WHERE categoryId =:categoryId")
    LiveData<Category> getCategoryById(int categoryId);

}
