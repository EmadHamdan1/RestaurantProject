package com.emad.restaurantproject.CustomerScreens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.ActivityDetailsMenuItemBinding;

public class DetailsMenuItemActivity extends AppCompatActivity {

    ActivityDetailsMenuItemBinding binding;
    MyViewModel viewModel;

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

        MenuItem menuItem = (MenuItem) getIntent().getSerializableExtra("menuItem");

        Log.d("TAG", "onCreate: "+menuItem.getImageUri());

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
        });


    }
}