package com.emad.restaurantproject.AuthScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.emad.restaurantproject.CustomerScreens.HomeScreens.HomeActivity;
import com.emad.restaurantproject.OwnerScreens.OwnerActivity;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.User;
import com.emad.restaurantproject.databinding.ActivityLoginBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    MyViewModel viewModel;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding.signInBtLogin.setOnClickListener(view -> {
            LoginUser(getUserData());
        });

        binding.createAccTvLogin.setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), RegisterActivity.class));
        });

        binding.forgetPassTvLogin.setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), ForgetPasswordActivity.class));
        });

        ClickArrowBack();

    }

    User getUserData() {

        String email = binding.emailEtLogin.getText().toString().trim();
        String password = binding.passwordEtLogin.getText().toString().trim();

        return new User(email, password);

    }

    boolean isFieldsValid(User user) {

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            Toast.makeText(this, R.string.fill_the_data_user, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            Toast.makeText(this, R.string.invalid_email_format_user, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getPassword().length() < 6) {
            Toast.makeText(this, R.string.password_too_short_user, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    void CheckUserType(User userByEmail) {

        if (userByEmail == null || userByEmail.getUserType() == null) {
            ShowToast("User type not found");
            return;
        }

        if (userByEmail.getUserType().equalsIgnoreCase("customer")) {
            Intent intent = new Intent(getBaseContext(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("customerId", userByEmail.getUserId());
            startActivity(intent);
            finish();

        } else if (userByEmail.getUserType().equalsIgnoreCase("owner")) {
            startActivity(new Intent(getBaseContext(), OwnerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        }

    }

    void LoginUser(User user) {

        try {

            if (!isFieldsValid(user)) return;

            executor.execute(() -> {

                User userByEmail = viewModel.getUserByEmail(user.getEmail());

                if (userByEmail == null) {
                    ShowToast(getString(R.string.email_not_correct_user));
                    return;
                }

                if (!userByEmail.getPassword().equals(user.getPassword())) {
                    ShowToast(getString(R.string.password_not_correct_user));
                    return;
                }

                SaveLoginData(userByEmail);
                CheckUserType(userByEmail);
            });


        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Unexpected error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    void ShowToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    void ClickArrowBack() {
        binding.arrowBackIv.setOnClickListener(view -> {
            finish();
        });
    }

    void SaveLoginData(User user) {

        sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);
        editor = sharedPref.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.putString("userEmail", user.getEmail());
        editor.putInt("customerId", user.getUserId());
        editor.putString("userType", user.getUserType());
        editor.apply();
    }

}