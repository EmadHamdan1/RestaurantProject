package com.emad.restaurantproject.AuthScreens;

import android.content.Intent;
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

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.User;
import com.emad.restaurantproject.databinding.ActivityRegisterBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    MyViewModel viewModel;
    private final Executor executor = Executors.newSingleThreadExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding.signUpBtSignUp.setOnClickListener(view -> {
            CreateAccount(getUserData());
        });

        binding.haveAccountSignUp.setOnClickListener(view -> {
            startActivity(new Intent(getBaseContext(), LoginActivity.class));
            finish();
        });

        ClickArrowBack();

    }

    User getUserData() {

        String name = binding.nameEtSignUp.getText().toString().trim();
        String email = binding.emailEtSignUp.getText().toString().trim();
        String password = binding.passwordEtSignUp.getText().toString().trim();

        return new User(name, email, password, "customer");

    }

    boolean isFieldsValid(User user) {

        if (user.getName().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            Toast.makeText(this, "Fill The Data", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (user.getPassword().length() < 6) {
            Toast.makeText(this, "Password too short", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }


    void CreateAccount(User user) {

        try {

            if (!isFieldsValid(user)) return;

            executor.execute(() -> {
                User userByEmail = viewModel.getUserByEmail(user.getEmail());

                if (userByEmail != null) {
                    ShowToast("User already exists");
                } else {
                    viewModel.insertUser(user);
                    ShowToast("Registration successful");
                    runOnUiThread(this::ClearFieldsData);
                    finish();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "CreateAccount: " + e.getMessage());
            Toast.makeText(this, "Unexpected error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    void ShowToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    void ClearFieldsData() {
        binding.nameEtSignUp.setText("");
        binding.emailEtSignUp.setText("");
        binding.passwordEtSignUp.setText("");
    }

    void ClickArrowBack() {
        binding.arrowBackIv.setOnClickListener(view -> {
            finish();
        });
    }


}