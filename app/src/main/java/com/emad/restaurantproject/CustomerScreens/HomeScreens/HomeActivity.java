package com.emad.restaurantproject.CustomerScreens.HomeScreens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.emad.restaurantproject.CustomerScreens.CartScreens.CartFragment;
import com.emad.restaurantproject.CustomerScreens.FavoriteScreens.FavoriteFragment;
import com.emad.restaurantproject.CustomerScreens.ProfileScreens.ProfileCustomerFragment;
import com.emad.restaurantproject.OwnerScreens.MenuItemFragment;
import com.emad.restaurantproject.OwnerScreens.ProfileOwnerFragment;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.WelcomeScreens.WelcomeFragment;
import com.emad.restaurantproject.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    ActivityHomeBinding binding;

    HomeFragment homeFragment;
    CartFragment cartFragment;
    FavoriteFragment favoriteFragment;
    ProfileCustomerFragment profileCustomerFragment;
    private static final String keySelectedItem = "selected_item";

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
        int userId = getIntent().getIntExtra("customerId", -1);

        if (savedInstanceState == null) {
            homeFragment = HomeFragment.newInstance(userId);
            cartFragment = CartFragment.newInstance(userId);
            favoriteFragment = FavoriteFragment.newInstance(userId);
            profileCustomerFragment = ProfileCustomerFragment.newInstance(userId);

            getSupportFragmentManager().beginTransaction()
                    .add(binding.customerContainerFragmentFl.getId(), homeFragment, "home")
                    .commitNow();

            getSupportFragmentManager().beginTransaction()
                    .add(binding.customerContainerFragmentFl.getId(), cartFragment, "cart")
                    .hide(cartFragment)
                    .commitNow();

            getSupportFragmentManager().beginTransaction()
                    .add(binding.customerContainerFragmentFl.getId(), favoriteFragment, "favorite")
                    .hide(favoriteFragment)
                    .commitNow();

            getSupportFragmentManager().beginTransaction()
                    .add(binding.customerContainerFragmentFl.getId(), profileCustomerFragment, "profile")
                    .hide(profileCustomerFragment)
                    .commitNow();
        } else {
            homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("home");
            cartFragment = (CartFragment) getSupportFragmentManager().findFragmentByTag("cart");
            favoriteFragment = (FavoriteFragment) getSupportFragmentManager().findFragmentByTag("favorite");
            profileCustomerFragment = (ProfileCustomerFragment) getSupportFragmentManager().findFragmentByTag("profile");
        }

        int selectedItem = R.id.menuItem;
        if (savedInstanceState != null) {
            selectedItem = savedInstanceState.getInt(keySelectedItem, R.id.homeItem);
        }

        binding.customerBottomNavigationBn.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.homeItem) {
                getSupportFragmentManager().beginTransaction().hide(profileCustomerFragment).hide(cartFragment).hide(favoriteFragment)
                        .show(homeFragment).commit();

            } else if (item.getItemId() == R.id.profileItem) {
                getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(cartFragment).hide(favoriteFragment)
                        .show(profileCustomerFragment).commit();

            } else if (item.getItemId() == R.id.cartItem) {
                getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(profileCustomerFragment).hide(favoriteFragment)
                        .show(cartFragment).commit();
            } else if (item.getItemId() == R.id.favoriteItem) {
                getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(profileCustomerFragment).hide(cartFragment)
                        .show(favoriteFragment).commit();
            }
            return true;
        });

        binding.customerBottomNavigationBn.setSelectedItemId(selectedItem);

        onClickBack();

    }

//    void addAndHandleFragments() {
//
//
//        HomeFragment homeFragment = HomeFragment.newInstance(userId);
//        CartFragment cartFragment = CartFragment.newInstance(userId);
//        FavoriteFragment favoriteFragment = FavoriteFragment.newInstance(userId);
//        ProfileCustomerFragment profileCustomerFragment = ProfileCustomerFragment.newInstance(userId);
//
//        getSupportFragmentManager().beginTransaction().add(
//                binding.customerContainerFragmentFl.getId(), homeFragment).commit();
//
//        getSupportFragmentManager().beginTransaction()
//                .add(binding.customerContainerFragmentFl.getId(), profileCustomerFragment)
//                .hide(profileCustomerFragment).commitNow();
//
//        getSupportFragmentManager().beginTransaction()
//                .add(binding.customerContainerFragmentFl.getId(), cartFragment)
//                .hide(cartFragment).commitNow();
//
//        getSupportFragmentManager().beginTransaction()
//                .add(binding.customerContainerFragmentFl.getId(), favoriteFragment)
//                .hide(favoriteFragment).commitNow();
//
//        binding.customerBottomNavigationBn.setOnItemSelectedListener(item -> {
//
//            if (item.getItemId() == R.id.homeItem) {
//                getSupportFragmentManager().beginTransaction().hide(profileCustomerFragment).hide(cartFragment).hide(favoriteFragment)
//                        .show(homeFragment).commit();
//
//            } else if (item.getItemId() == R.id.profileItem) {
//                getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(cartFragment).hide(favoriteFragment)
//                        .show(profileCustomerFragment).commit();
//
//            } else if (item.getItemId() == R.id.cartItem) {
//                getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(profileCustomerFragment).hide(favoriteFragment)
//                        .show(cartFragment).commit();
//            } else if (item.getItemId() == R.id.favoriteItem) {
//                getSupportFragmentManager().beginTransaction().hide(homeFragment).hide(profileCustomerFragment).hide(cartFragment)
//                        .show(favoriteFragment).commit();
//            }
//            return true;
//        });
//
//    }

    void onClickBack() {

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                int selectedItemId = binding.customerBottomNavigationBn.getSelectedItemId();

                if (selectedItemId == R.id.cartItem) {
                    binding.customerBottomNavigationBn.setSelectedItemId(R.id.homeItem);
                } else if (selectedItemId == R.id.profileItem) {
                    binding.customerBottomNavigationBn.setSelectedItemId(R.id.homeItem);
                } else if (selectedItemId == R.id.favoriteItem) {
                    binding.customerBottomNavigationBn.setSelectedItemId(R.id.homeItem);
                } else {
                    finish();
                }

            }
        });

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(keySelectedItem, binding.customerBottomNavigationBn.getSelectedItemId());
    }

}