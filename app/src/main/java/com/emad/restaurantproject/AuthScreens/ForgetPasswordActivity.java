package com.emad.restaurantproject.AuthScreens;

import android.os.Bundle;
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
import com.emad.restaurantproject.databinding.ActivityForgetPasswordBinding;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ForgetPasswordActivity extends AppCompatActivity {

    ActivityForgetPasswordBinding binding;
    MyViewModel viewModel;
    private final Executor executor = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding.resetPasswordForgetBt.setOnClickListener(view -> {

            ResetPassword(getUserData());

        });

        ClickArrowBack();

    }

    User getUserData() {

        String email = binding.emailEtForget.getText().toString().trim();
        String password = binding.newPasswordEtForget.getText().toString().trim();

        return new User(email, password);

    }

    boolean isFieldsValid(User user) {

        if (user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
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

    void ResetPassword(User user) {

        if (!isFieldsValid(user)) return;

        executor.execute(() -> {
            User userByEmail = viewModel.getUserByEmail(user.getEmail());

            if (userByEmail != null) {

                userByEmail.setPassword(user.getPassword());
                viewModel.updateUser(userByEmail);
                ShowToast("Reset Password Successfully");
                finish();
            } else {
                ShowToast("Email Not Correct");
            }

        });


    }

    void ShowToast(String message) {
        runOnUiThread(() -> Toast.makeText(this, message, Toast.LENGTH_SHORT).show());
    }

    void ClickArrowBack() {
        binding.arrowBackIv.setOnClickListener(view -> {
            finish();
        });
    }


}