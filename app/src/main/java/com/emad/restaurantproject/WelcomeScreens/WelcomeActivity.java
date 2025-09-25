package com.emad.restaurantproject.WelcomeScreens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.emad.restaurantproject.AuthScreens.AuthActivity;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.databinding.ActivityWelcomeBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    ActivityWelcomeBinding binding;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityWelcomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPref = getSharedPreferences("MyAppPrefs", MODE_PRIVATE);

        binding.fragmentContainerVp.setOffscreenPageLimit(3);

        if (isLoggedIn()) {
            startActivity(new Intent(getBaseContext(), AuthActivity.class));
            finish();
        }

        CreateFragments();

        HandleTabsWithViewPager();

        HandleNextButton();

        HandleTextButton();

        OnClickBack();

    }

    void CreateFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(WelcomeFragment.newInstance(R.drawable.lunch_time, getString(R.string.delicious_food), getString(R.string.enjoy_a_unique_and_tasty_dining_experience)));
        fragments.add(WelcomeFragment.newInstance(R.drawable.order_food, getString(R.string.order_with_ease), getString(R.string.browse_the_menu_and_order_your_favorite_meal_in_just_a_few_steps)));
        fragments.add(WelcomeFragment.newInstance(R.drawable.eat_breakfast, getString(R.string.let_s_eat), getString(R.string.explore_our_special_dishes_and_get_your_meal_fast)));
        binding.fragmentContainerVp.setAdapter(new WelcomeAdapter(this, fragments));
    }

    void HandleTabsWithViewPager() {

        new TabLayoutMediator(binding.tabDots, binding.fragmentContainerVp, (tab, position) -> {
        }).attach();

    }

    void HandleNextButton() {
        binding.nextBt.setOnClickListener(view -> {

            int currentItem = binding.fragmentContainerVp.getCurrentItem();
            if (currentItem < 2) {
                binding.fragmentContainerVp.setCurrentItem(currentItem + 1);
            } else {
                startActivity(new Intent(this, AuthActivity.class));
                SaveSkipWelcome();
                finish();
            }
        });
    }

    void HandleTextButton() {

        binding.fragmentContainerVp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {

                if (binding.fragmentContainerVp.getCurrentItem() == 0) {
                    binding.nextBt.setText(R.string.next_bt_welcome);
                } else if (binding.fragmentContainerVp.getCurrentItem() == 1) {
                    binding.nextBt.setText(R.string.next_bt_welcome);
                } else if (binding.fragmentContainerVp.getCurrentItem() == 2) {
                    binding.nextBt.setText(R.string.get_started_bt_welcome);
                }

                super.onPageSelected(position);

            }
        });

    }

    void OnClickBack() {
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

                int currentItem = binding.fragmentContainerVp.getCurrentItem();

                if (currentItem > 0) {
                    binding.fragmentContainerVp.setCurrentItem(currentItem - 1);
                } else {
                    finish();
                }

            }
        });
    }


    private boolean isLoggedIn() {
        return sharedPref.getBoolean("isSkipWelcome", false);
    }

    void SaveSkipWelcome() {
        editor = sharedPref.edit();
        editor.putBoolean("isSkipWelcome", true);
        editor.apply();
    }

}