package com.emad.restaurantproject.CustomerScreens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
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

        addAndHandleFragments();
        onClickBack();

    }

    void addAndHandleFragments() {
        int userId = getIntent().getIntExtra("customerId", -1);

        HomeFragment homeFragment = HomeFragment.newInstance(userId);
        WelcomeFragment welcomeFragment = new WelcomeFragment();
        CartFragment cartFragment = CartFragment.newInstance(userId);

        getSupportFragmentManager().beginTransaction().add(
                binding.customerContainerFragmentFl.getId(), homeFragment).commit();

        getSupportFragmentManager().beginTransaction()
                .add(binding.customerContainerFragmentFl.getId(), welcomeFragment)
                .hide(welcomeFragment).commitNow();

        getSupportFragmentManager().beginTransaction()
                .add(binding.customerContainerFragmentFl.getId(), cartFragment)
                .hide(cartFragment).commitNow();

        binding.customerBottomNavigationBn.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.homeItem) {
                getSupportFragmentManager().beginTransaction().hide(welcomeFragment).hide(cartFragment)
                        .show(homeFragment).commit();

            } else if (item.getItemId() == R.id.profileItem) {
                getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(cartFragment)
                        .show(welcomeFragment).commit();

            } else if (item.getItemId() == R.id.cartItem) {
                getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(welcomeFragment)
                        .show(cartFragment).commit();
            }
            return true;
        });

    }

    void onClickBack() {

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                int selectedItemId = binding.customerBottomNavigationBn.getSelectedItemId();

                if (selectedItemId == R.id.cartItem) {
                    binding.customerBottomNavigationBn.setSelectedItemId(R.id.homeItem);
                } else if (selectedItemId == R.id.profileItem) {
                    binding.customerBottomNavigationBn.setSelectedItemId(R.id.homeItem);
                } else if (selectedItemId == R.id.notificationsItem) {
                    binding.customerBottomNavigationBn.setSelectedItemId(R.id.homeItem);
                } else {
                    finish();
                }

            }
        });

    }
}