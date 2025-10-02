package com.emad.restaurantproject.OwnerScreens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.ActivityUpdateMenuItemBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.concurrent.Executors;

public class UpdateMenuItemActivity extends AppCompatActivity {

    ActivityUpdateMenuItemBinding binding;
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
        binding = ActivityUpdateMenuItemBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        binding.updateItemBt.setOnClickListener(view -> {
            updateItemMenu();
        });

        showOldData();

        handleAdapterWithSpinner();

        addPhotoFromGallery();

        ClickArrowBack();

    }

    void showOldData() {
        MenuItem oldItem = (MenuItem) getIntent().getSerializableExtra("item");

        binding.nameEtAddItem.setText(oldItem.getName());
        binding.priceEtAddItem.setText(String.valueOf(oldItem.getPrice()));
        binding.caloriesEtAddItem.setText(String.valueOf(oldItem.getCalories()));
        binding.descriptionEtAddItem.setText(String.valueOf(oldItem.getDescription()));


        if (oldItem.getImageUri() != null && !oldItem.getImageUri().isEmpty()) {

            Glide.with(this)
                    .load(oldItem.getImageUri())
                    .centerCrop()
                    .placeholder(R.drawable.desserts)
                    .error(R.drawable.restaurant)
                    .into(binding.itemIv);

            imageUri = oldItem.getImageUri();
        }


        binding.categorySp.post(() -> {
            binding.categorySp.setSelection(oldItem.getCategoryId() - 1);
        });
    }

    void handleAdapterWithSpinner() {
        viewModel.getAllCategories().observe(this, categories -> {
            binding.categorySp.setAdapter(new CategorySpAdapter(this, categories));
        });
    }

    void updateItemMenu() {

        String name = binding.nameEtAddItem.getText().toString().trim();
        String price = binding.priceEtAddItem.getText().toString().trim();
        String calories = binding.caloriesEtAddItem.getText().toString().trim();
        String description = binding.descriptionEtAddItem.getText().toString().trim();


        if (name.isEmpty() || price.isEmpty() || calories.isEmpty() || imageUri == null || imageUri.isEmpty() || description.isEmpty()) {
            Toast.makeText(this, R.string.fill_the_data_user, Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.getAllCategories().observe(this, categories -> {

            int categoryPos = binding.categorySp.getSelectedItemPosition();
            int categoryId = categories.get(categoryPos).getCategoryId();
            MenuItem oldId = (MenuItem) getIntent().getSerializableExtra("item");

            Executors.newSingleThreadExecutor().execute(() -> {

                if (oldId != null) {
                    viewModel.updateMenuItem(new MenuItem(oldId.getMenuItemId(), name, Double.parseDouble(price), Integer.parseInt(calories), imageUri, categoryId, description));
                    runOnUiThread(() -> {
                        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
                        builder.setTitle(R.string.update_successful)
                                .setIcon(R.drawable.success)
                                .setMessage(R.string.your_item_data_has_been_updated_successfully)
                                .setPositiveButton(R.string.ok_user, (dialog, which) -> {
                                    finish();
                                });

                        AlertDialog dialog = builder.create();
                        dialog.show();

                        dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                                .setTextColor(getResources().getColor(R.color.colorPrimary));

                    });
                }
            });

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

    void ClickArrowBack() {
        binding.arrowBackIv.setOnClickListener(view -> {
            finish();
        });
    }

}