package com.emad.restaurantproject.OwnerScreens;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.FragmentMenuItemBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
                    requireActivity().runOnUiThread(() -> {
                        new MaterialAlertDialogBuilder(requireActivity())
                                .setTitle(R.string.delete_item)
                                .setIcon(R.drawable.warning)
                                .setMessage(R.string.warning)
                                .setPositiveButton(getString(R.string.delete), (dialog, which) -> {
                                    viewModel.deleteMenuItem(item);

                                    new MaterialAlertDialogBuilder(requireActivity())
                                            .setTitle(getString(R.string.delete))
                                            .setIcon(R.drawable.delete_dialog)
                                            .setMessage(R.string.the_item_has_been_successfully_deleted_from_the_system)
                                            .setPositiveButton(R.string.ok_user, null)
                                            .show();
                                })
                                .setNegativeButton(R.string.cancel, (dialog, which) -> {
                                    dialog.dismiss();
                                })
                                .show();
                    });
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