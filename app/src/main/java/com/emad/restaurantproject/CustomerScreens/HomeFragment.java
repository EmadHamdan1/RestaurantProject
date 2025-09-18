package com.emad.restaurantproject.CustomerScreens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;

import com.emad.restaurantproject.OwnerScreens.OwnerActivity;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.FragmentHomeBinding;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements ItemHomeListener {

    private static final String ARG_USER_ID = "customerId";

    private int userId;
    MyViewModel viewModel;
    FragmentHomeBinding binding;
    MenuItemCustomerAdapter horizontalAdapter;
    MenuItemCustomerVerticalAdapter verticalAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int userId) {
        HomeFragment fragment = new HomeFragment();
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

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        viewModel.loadAllData(userId);

        HandleSearchEt();

        HandleHorizontalRecyclerView();

        HandleVerticalRecyclerView();

        ShowUserData();

        HandleCategoryRecycler();

        ShowAllMenuItemsFirstLunch();

        binding.backIv.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), OwnerActivity.class));
        });
        binding.cancelSearchIv.setVisibility(View.INVISIBLE);
        return binding.getRoot();
    }

    @Override
    public void onClickCategoryItems(int categoryId) {

        if (categoryId == 0) {
            viewModel.getAllMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
                verticalAdapter.updateMenuItems(menuItems);
                binding.menuItemsVerticalRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            });
        } else {
            viewModel.getMenuItemsByCategoryId(categoryId).observe(getViewLifecycleOwner(), menuItems -> {
                verticalAdapter.updateMenuItems(menuItems);
                binding.menuItemsVerticalRv.setLayoutManager(new GridLayoutManager(getContext(), 2));
            });
        }

    }

    @Override
    public void onClickMenuItem(MenuItem menuItem, int itemPos) {
        Intent intent = new Intent(getContext(), DetailsMenuItemActivity.class);
        intent.putExtra("menuItem", menuItem);
        intent.putExtra("itemPos", itemPos);
        intent.putExtra("customerId", userId);
        startActivity(intent);
    }

    void HandleHorizontalRecyclerView() {

        horizontalAdapter = new MenuItemCustomerAdapter(new ArrayList<>(), this);
        binding.menuItemsRv.setAdapter(horizontalAdapter);
        binding.menuItemsRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        viewModel.getAllMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
            horizontalAdapter.updateMenuItems(menuItems);
        });

    }

    void HandleVerticalRecyclerView() {
        verticalAdapter = new MenuItemCustomerVerticalAdapter(new ArrayList<>(), new ArrayList<>(), this);
        binding.menuItemsVerticalRv.setAdapter(verticalAdapter);


        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        binding.menuItemsVerticalRv.setLayoutManager(gridLayoutManager);

        viewModel.getAllMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
            verticalAdapter.updateMenuItems(menuItems);
        });

        viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            verticalAdapter.setCategories(categories);
        });

    }

    void ShowAllMenuItemsFirstLunch() {
        viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {

            if (categories.get(0).getCategoryId() == 1) {
                viewModel.getAllMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
                    horizontalAdapter.updateMenuItems(menuItems);
                    verticalAdapter.updateMenuItems(menuItems);
                });
            }
        });
    }

    void HandleCategoryRecycler() {
        viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
            binding.categoryRv.setAdapter(new CategoryAdapter(categories, this));
            binding.categoryRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        });
    }

    void HandleSearchEt() {

        binding.searchEtHome.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(binding.searchEtHome.getWindowToken(), 0);
                }
                binding.searchEtHome.clearFocus();
                binding.searchEtHome.setCursorVisible(false);

                binding.cancelSearchIv.setVisibility(View.VISIBLE);

                binding.categoryRv.setVisibility(View.GONE);
                binding.menuItemsRv.setVisibility(View.GONE);
                binding.exploreTV.setVisibility(View.GONE);
                binding.titleTv.setVisibility(View.GONE);

                verticalAdapter = new MenuItemCustomerVerticalAdapter(new ArrayList<>(), new ArrayList<>(), this);
                binding.menuItemsVerticalRv.setAdapter(verticalAdapter);


                GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
                binding.menuItemsVerticalRv.setLayoutManager(gridLayoutManager);

                viewModel.getAllMenuItemsByName(binding.searchEtHome.getText().toString()).observe(getViewLifecycleOwner(), menuItems -> {
                    verticalAdapter.updateMenuItems(menuItems);
                });

                viewModel.getAllCategories().observe(getViewLifecycleOwner(), categories -> {
                    verticalAdapter.setCategories(categories);
                });

                binding.cancelSearchIv.setOnClickListener(view -> {

                    binding.cancelSearchIv.setVisibility(View.INVISIBLE);

                    binding.categoryRv.setVisibility(View.VISIBLE);
                    binding.menuItemsRv.setVisibility(View.VISIBLE);
                    binding.exploreTV.setVisibility(View.VISIBLE);
                    binding.titleTv.setVisibility(View.VISIBLE);

                    binding.searchEtHome.setText("");

                    viewModel.getAllMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
                        verticalAdapter.updateMenuItems(menuItems);
                    });

                });

                return true;
            }
            return false;
        });

    }

    void ShowUserData() {

        viewModel.getUserById(userId).observe(getViewLifecycleOwner(), user -> {
            binding.userNameTv.setText(user.getName());
            Glide.with(this)
                    .load(user.getPhotoUri())
                    .placeholder(R.drawable.man)
                    .error(R.drawable.man)
                    .into(binding.photoUserIv);
        });

    }

}