package com.emad.restaurantproject.OwnerScreens;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.FragmentMenuItemBinding;

import java.util.ArrayList;

public class MenuItemFragment extends Fragment {
    MyViewModel viewModel;
    MenuItemOwnerAdapter adapter;

    public MenuItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentMenuItemBinding binding = FragmentMenuItemBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        adapter = new MenuItemOwnerAdapter(new ArrayList<>(), new ArrayList<>(), new MenuItemListener() {
            @Override
            public void onEditItem(MenuItem item) {
                Intent intent = new Intent(getContext(), UpdateMenuItemActivity.class);
                intent.putExtra("item", item);
                startActivity(intent);
            }

            @Override
            public void onDeleteItem(MenuItem item) {
                if (item != null) {
                    viewModel.deleteMenuItem(item);
                }
            }

        });

        viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            adapter.setCategories(categories);
        });

        viewModel.getAllMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
            adapter.updateMenuItems(menuItems);
        });

        binding.menuItemRv.setAdapter(adapter);
        binding.menuItemRv.setLayoutManager(new GridLayoutManager(getContext(), 2));

        return binding.getRoot();
    }
}