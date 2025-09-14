package com.emad.restaurantproject.OwnerScreens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.emad.restaurantproject.AuthScreens.LoginActivity;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.databinding.ActivityOwnerBinding;

public class OwnerActivity extends AppCompatActivity {

    ActivityOwnerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityOwnerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.iconBack.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        binding.floatingActionButton.setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), AddMenuItemActivity.class));
        });

        HandleBottomNavigationWithFragment();

    }

    void HandleBottomNavigationWithFragment() {

        MenuItemFragment menuFragment = new MenuItemFragment();
        ProfileFragment profileFragment = new ProfileFragment();

        getSupportFragmentManager().beginTransaction()
                .add(binding.ownerContainerFragmentsFl.getId(), menuFragment, "menu").commitNow();

        getSupportFragmentManager().beginTransaction()
                .add(binding.ownerContainerFragmentsFl.getId(), profileFragment, "profile")
                .hide(profileFragment).commitNow();

        binding.ownerBottomNavigation.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menuItem) {
                getSupportFragmentManager().beginTransaction().hide(profileFragment)
                        .show(menuFragment).commit();

            } else if (item.getItemId() == R.id.profileItem) {
                getSupportFragmentManager().beginTransaction().hide(menuFragment)
                        .show(profileFragment).commit();
            }
            return true;
        });

    }

}