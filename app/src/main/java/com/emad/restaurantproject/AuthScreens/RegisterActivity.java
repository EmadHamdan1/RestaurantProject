package com.emad.restaurantproject.AuthScreens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.User;
import com.emad.restaurantproject.databinding.ActivityRegisterBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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


    void CreateAccount(User user) {

        try {

            if (!isFieldsValid(user)) return;

            executor.execute(() -> {

                User userByEmail = viewModel.getUserByEmail(user.getEmail());

                if (userByEmail != null) {
                    showDialog(getString(R.string.registration_failed_user), getString(R.string.user_already_exists_user));
                } else {

                    viewModel.insertUser(user);

                    runOnUiThread(() -> {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                        builder.setTitle(R.string.success_user)
                                .setMessage(R.string.registration_successful_user)
                                .setIcon(R.drawable.success)
                                .setPositiveButton(R.string.ok_user, (dialog, which) -> {

                                    runOnUiThread(this::ClearFieldsData);

                                    runOnUiThread(() -> {
                                        startActivity(new Intent(getBaseContext(), LoginActivity.class));
                                        finish();
                                    });

                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                .setTextColor(getResources().getColor(R.color.colorPrimary));
                    });
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
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

    private void showDialog(String title, String message) {
        runOnUiThread(() -> {
            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
            builder.setTitle(title)
                    .setMessage(message)
                    .setIcon(R.drawable.warning)
                    .setPositiveButton(R.string.ok_user, (dialog, which) -> dialog.dismiss());

            AlertDialog dialog = builder.create();
            dialog.show();

            dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(getResources().getColor(R.color.colorPrimary));
        });
    }


}