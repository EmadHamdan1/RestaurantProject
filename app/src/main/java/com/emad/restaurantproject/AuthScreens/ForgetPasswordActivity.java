package com.emad.restaurantproject.AuthScreens;

import android.content.Intent;
import android.os.Bundle;
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
import com.emad.restaurantproject.databinding.ActivityForgetPasswordBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

    void ResetPassword(User user) {

        if (!isFieldsValid(user)) return;

        executor.execute(() -> {
            User userByEmail = viewModel.getUserByEmail(user.getEmail());

            if (userByEmail != null) {

                userByEmail.setPassword(user.getPassword());
                viewModel.updateUser(userByEmail);
                runOnUiThread(() -> {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                    builder.setTitle(R.string.success_user)
                            .setMessage(R.string.reset_password_successfully_user)
                            .setIcon(R.drawable.success)
                            .setPositiveButton(R.string.ok_user, (dialog, which) -> {

                                startActivity(new Intent(getBaseContext(), LoginActivity.class));
                                finish();

                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                    dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                            .setTextColor(getResources().getColor(R.color.colorPrimary));
                });
            } else {
                ShowToast(getString(R.string.email_not_correct_user));
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