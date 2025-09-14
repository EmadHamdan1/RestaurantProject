package com.emad.restaurantproject.CustomerScreens;

import com.emad.restaurantproject.database.entities.MenuItem;

public interface ItemHomeListener {
    void onClickCategoryItems(int categoryId);

    void onClickMenuItem(MenuItem menuItem);
}
