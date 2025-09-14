package com.emad.restaurantproject.AuthScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.emad.restaurantproject.CustomerScreens.HomeActivity;
import com.emad.restaurantproject.OwnerScreens.OwnerActivity;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.databinding.ActivityAuthBinding;

public class AuthActivity extends AppCompatActivity {

    ActivityAuthBinding binding;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAuthBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (isUserLogin()) return;

        binding.loginBt.setOnClickListener(view -> {

            startActivity(new Intent(getBaseContext(), LoginActivity.class));

        });

        binding.registerBt.setOnClickListener(view -> {

            startActivity(new Intent(getBaseContext(), RegisterActivity.class));

        });

    }

    boolean isUserLogin() {

        if (isLoggedIn()) {

            String userType = sharedPref.getString("userType", "");
            String userEmail = sharedPref.getString("userEmail", "");
            int userId = sharedPref.getInt("userId", -1);

            Intent intent;
            if (userType.equalsIgnoreCase("customer")) {

                intent = new Intent(this, HomeActivity.class);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userId", userId);

            } else if (userType.equalsIgnoreCase("owner")) {

                intent = new Intent(this, OwnerActivity.class);
                intent.putExtra("userEmail", userEmail);
                intent.putExtra("userId", userId);
            } else {
                return false;
            }

            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private boolean isLoggedIn() {
        sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        return sharedPref.getBoolean("isLoggedIn", false);
    }


}