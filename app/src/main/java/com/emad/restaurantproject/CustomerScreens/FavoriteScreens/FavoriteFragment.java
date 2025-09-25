package com.emad.restaurantproject.CustomerScreens.FavoriteScreens;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emad.restaurantproject.CustomerScreens.HomeScreens.DetailsMenuItemActivity;
import com.emad.restaurantproject.CustomerScreens.HomeScreens.ItemHomeListener;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.FragmentFavoriteBinding;

import java.util.ArrayList;


public class FavoriteFragment extends Fragment implements ItemHomeListener {

    MyViewModel viewModel;
    FavoriteItemAdapter adapter;
    private static final String ARG_USER_ID = "userId";
    FragmentFavoriteBinding binding;

    // TODO: Rename and change types of parameters
    private int userId;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance(int userId) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        adapter = new FavoriteItemAdapter(new ArrayList<>(), userId, getViewLifecycleOwner(), this);
        binding.favoriteItemRv.setAdapter(adapter);
        binding.favoriteItemRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        viewModel.getFavoriteMenuItemsByUserId(userId).observe(getViewLifecycleOwner(), menuItems -> {
            adapter.updateMenuItems(menuItems);
        });

        return binding.getRoot();
    }

    @Override
    public void onClickCategoryItems(int categoryId) {

    }

    @Override
    public void onClickMenuItem(MenuItem menuItem, int itemPos) {
        Intent intent = new Intent(getContext(), DetailsMenuItemActivity.class);
        intent.putExtra("menuItem", menuItem);
        intent.putExtra("itemPos", itemPos);
        intent.putExtra("customerId", userId);
        startActivity(intent);
    }

    @Override
    public void onClickFavorite(MenuItem menuItem) {

    }
}