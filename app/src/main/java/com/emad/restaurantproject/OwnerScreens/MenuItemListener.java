package com.emad.restaurantproject.OwnerScreens;

import com.emad.restaurantproject.database.entities.MenuItem;

public interface MenuItemListener {

    void onEditItem(MenuItem item);

    void onDeleteItem(MenuItem item);

}
