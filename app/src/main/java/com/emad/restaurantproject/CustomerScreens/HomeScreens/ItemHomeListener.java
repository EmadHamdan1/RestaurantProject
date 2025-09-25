package com.emad.restaurantproject.CustomerScreens.HomeScreens;

import com.emad.restaurantproject.database.entities.MenuItem;

public interface ItemHomeListener {
    void onClickCategoryItems(int categoryId);

    void onClickMenuItem(MenuItem menuItem, int itemPos);

    void onClickFavorite(MenuItem menuItem);

}
