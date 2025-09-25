package com.emad.restaurantproject.OwnerScreens;


import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.databinding.ActivityChooseLanguageBinding;
import com.yariksoffice.lingver.Lingver;

public class ChooseLanguageActivity extends AppCompatActivity {

    ActivityChooseLanguageBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityChooseLanguageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String currentLang = Lingver.getInstance().getLanguage();
        if (currentLang.equals("ar")) {
            binding.arabicRb.setChecked(true);
        } else {
            binding.englishRb.setChecked(true);
        }

        binding.languageTg.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.englishRb) {
                Lingver.getInstance().setLocale(this, "en");
            } else if (checkedId == R.id.arabicRb) {
                Lingver.getInstance().setLocale(this, "ar");
            }

            setResult(RESULT_OK);
            finish();
        });


        ClickArrowBack();
    }

    void ClickArrowBack() {
        binding.arrowBackIv.setOnClickListener(view -> {
            finish();
        });
    }

}