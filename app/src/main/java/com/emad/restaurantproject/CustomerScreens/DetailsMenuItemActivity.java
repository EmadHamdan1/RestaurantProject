package com.emad.restaurantproject.CustomerScreens;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.CartItem;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.ActivityDetailsMenuItemBinding;

import java.util.concurrent.Executors;

public class DetailsMenuItemActivity extends AppCompatActivity {

    ActivityDetailsMenuItemBinding binding;
    MyViewModel viewModel;
    MenuItem menuItem;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailsMenuItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        menuItem = (MenuItem) getIntent().getSerializableExtra("menuItem");

        ShowAllItemDetails();

        HandleQuantity();

        binding.addToCartBt.setOnClickListener(view -> AddItemToCart());
    }


    void AddItemToCart() {
        addOrUpdateCartItem(new CartItem(menuItem.getMenuItemId(),
                Integer.parseInt(binding.quantityTv.getText().toString()),
                menuItem.getPrice(), getIntent().getIntExtra("customerId", -1)));
    }

    public void addOrUpdateCartItem(CartItem newItem) {

        Executors.newSingleThreadExecutor().execute(() -> {

            CartItem cartItem = viewModel.getCartItemByMenuItemId(newItem.getMenuItemId(), newItem.getCustomerId());

            if (cartItem == null) {
                viewModel.insertCartItem(newItem);
            } else {

                int updatedQuantity = cartItem.getQuantity() + newItem.getQuantity();

                CartItem updatedItem = new CartItem(cartItem.getCartItemId(),
                        cartItem.getMenuItemId(), updatedQuantity, cartItem.getItemPrice(),
                        cartItem.getCustomerId());

                viewModel.updateCartItem(updatedItem);

            }
        });
    }


    @SuppressLint("SetTextI18n")
    void ShowAllItemDetails() {

        Glide.with(this)
                .load(Uri.parse(menuItem.getImageUri()))
                .placeholder(R.drawable.drinks)
                .error(R.drawable.restaurant)
                .into(binding.imageItemIv);

        binding.nameMenuItemTv.setText(menuItem.getName());

        binding.priceItemTv.setText("$ " + menuItem.getPrice());
        binding.caloriesItemTv.setText("🔥 " + menuItem.getCalories() + " Calories");
        binding.descriptionTv.setText(String.valueOf(menuItem.getDescription()));

        viewModel.getCategoryById(menuItem.getCategoryId()).observe(this, category -> {
            binding.categoryNameTv.setText(category.getName());
            binding.iconCategoryIv.setImageResource(category.getImageId());
        });

    }

    int quantity = 0;

    void HandleQuantity() {

        binding.addQuantityIv.setOnClickListener(view -> {
            binding.quantityTv.setText(String.valueOf(++quantity));
        });
        binding.minusQuantityIv.setOnClickListener(view -> {
            if (quantity > 0)
                binding.quantityTv.setText(String.valueOf(--quantity));
        });


    }

}