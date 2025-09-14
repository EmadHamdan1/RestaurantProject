package com.emad.restaurantproject.OwnerScreens;

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
import com.emad.restaurantproject.databinding.ActivityAddMenuItemBinding;

import java.util.concurrent.Executors;

public class AddMenuItemActivity extends AppCompatActivity {

    ActivityAddMenuItemBinding binding;
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
                            .placeholder(R.drawable.restaurant)
                            .error(R.drawable.restaurant)
                            .into(binding.itemIv);

                    imageUri = uri.toString();
                }
            }
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityAddMenuItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        handleAdapterWithSpinner();

        binding.addItemBt.setOnClickListener(view -> {

            addNewItemMenu();

        });

        addPhotoFromGallery();

        ClickArrowBack();

    }

    void addNewItemMenu() {

        String name = binding.nameEtAddItem.getText().toString().trim();
        String price = binding.priceEtAddItem.getText().toString().trim();
        String calories = binding.caloriesEtAddItem.getText().toString().trim();
        String description = binding.descriptionEtAddItem.getText().toString().trim();


        if (name.isEmpty() || price.isEmpty() || calories.isEmpty() || imageUri == null || imageUri.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, "Fill The Data", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.getAllCategories().observe(this, categories -> {

            int categoryPos = binding.categorySp.getSelectedItemPosition();
            int categoryId = categories.get(categoryPos).getCategoryId();

            Executors.newSingleThreadExecutor().execute(() -> {
                viewModel.insertMenuItem(new MenuItem(name, Double.parseDouble(price), Integer.parseInt(calories), imageUri, categoryId, description));
                ShowToast("Added Item Successfully");

            });

        });


    }

    void handleAdapterWithSpinner() {
        viewModel.getAllCategories().observe(this, categories -> {
            binding.categorySp.setAdapter(new CategorySpAdapter(this, categories));
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