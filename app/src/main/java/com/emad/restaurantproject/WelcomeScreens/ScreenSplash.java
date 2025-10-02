package com.emad.restaurantproject.WelcomeScreens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.databinding.ActivityScreenSplashBinding;

public class ScreenSplash extends AppCompatActivity {

    ActivityScreenSplashBinding binding;
    MyViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean nightMode = getSharedPreferences("settings", MODE_PRIVATE)
                .getBoolean("night_mode", false);

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityScreenSplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        viewModel.getAllCategories().observe(this, categories -> {
            viewModel.getAllMenuItems().observe(this, menuItems -> {
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(getBaseContext(), WelcomeActivity.class));
                    finish();
                }, 1000);
            });
        });

    }
}