package com.emad.restaurantproject.CustomerScreens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.WelcomeScreens.WelcomeFragment;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.User;
import com.emad.restaurantproject.databinding.ActivityHomeBinding;

import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        int userId = getIntent().getIntExtra("userId", -1);

        HomeFragment homeFragment = HomeFragment.newInstance(userId);
        WelcomeFragment welcomeFragment = new WelcomeFragment();

        getSupportFragmentManager().beginTransaction().add(
                binding.customerContainerFragmentFl.getId(), homeFragment).commit();

        getSupportFragmentManager().beginTransaction()
                .add(binding.customerContainerFragmentFl.getId(), welcomeFragment)
                .hide(welcomeFragment).commitNow();

        binding.customerBottomNavigationBn.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.homeItem) {
                getSupportFragmentManager().beginTransaction().hide(welcomeFragment)
                        .show(homeFragment).commit();

            } else if (item.getItemId() == R.id.profileItem) {
                getSupportFragmentManager().beginTransaction().hide(homeFragment)
                        .show(welcomeFragment).commit();
            }
            return true;
        });


    }
}