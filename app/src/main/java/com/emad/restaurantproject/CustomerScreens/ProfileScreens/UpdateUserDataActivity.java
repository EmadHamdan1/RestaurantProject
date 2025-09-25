package com.emad.restaurantproject.CustomerScreens.ProfileScreens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.database.entities.User;
import com.emad.restaurantproject.databinding.ActivityUpdateUserDataBinding;

import java.util.concurrent.Executors;

public class UpdateUserDataActivity extends AppCompatActivity {

    ActivityUpdateUserDataBinding binding;
    MyViewModel viewModel;

    String imageUri;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Uri uri = result.getData().getData();

                    getContentResolver().takePersistableUriPermission(
                            uri,
                            Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    );

                    Glide.with(this)
                            .load(uri)
                            .centerCrop()
                            .placeholder(R.drawable.man)
                            .error(R.drawable.man)
                            .into(binding.userIv);

                    imageUri = uri.toString();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityUpdateUserDataBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        ShowOldData();

        addPhotoFromGallery();

        binding.updateUserBt.setOnClickListener(view -> {
            UpdateUserData();
        });

        ClickArrowBack();

    }

    void ShowOldData() {

        viewModel.getUserById(getIntent().getIntExtra("userId", -1)).observe(this, user -> {

            binding.nameEtUpdateUser.setText(user.getName());
            binding.emailEtUpdateUser.setText(user.getEmail());
            binding.passwordEtAddUser.setText(user.getPassword());

            if (user.getPhotoUri() != null && !user.getPhotoUri().isEmpty()) {

                Glide.with(this)
                        .load(user.getPhotoUri())
                        .centerCrop()
                        .placeholder(R.drawable.man)
                        .error(R.drawable.man)
                        .into(binding.userIv);

                imageUri = user.getPhotoUri();
            }
        });

    }

    void UpdateUserData() {

        String name = binding.nameEtUpdateUser.getText().toString().trim();
        String email = binding.emailEtUpdateUser.getText().toString().trim();
        String password = binding.passwordEtAddUser.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Fill The Data", Toast.LENGTH_SHORT).show();
            return;
        }


        Executors.newSingleThreadExecutor().execute(() -> {

            if (imageUri != null) {
                viewModel.updateUser(new User(getIntent().getIntExtra("userId", -1), name, email, password, imageUri));
                ShowToast("Update User Successfully");
            }


        });


    }

    void addPhotoFromGallery() {
        binding.addPhotoIv.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            launcher.launch(intent);
        });
    }

    void ShowToast(String message) {
        runOnUiThread(() -> {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    void ClickArrowBack() {
        binding.arrowBackIv.setOnClickListener(view -> {
            finish();
        });
    }

}