package com.emad.restaurantproject.OwnerScreens;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.AuthScreens.LoginActivity;
import com.emad.restaurantproject.CustomerScreens.ProfileScreens.UpdateUserDataActivity;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.databinding.FragmentOwnerProfileBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;


public class ProfileOwnerFragment extends Fragment {

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == AppCompatActivity.RESULT_OK) {
                        requireActivity().recreate();
                        requireActivity().finish();
                        startActivity(requireActivity().getIntent());
                    }
                }
            }
    );

    private static final String ARG_USER_ID = "ownerId";

    private int userId;

    public ProfileOwnerFragment() {
        // Required empty public constructor
    }

    public static ProfileOwnerFragment newInstance(int userId) {
        ProfileOwnerFragment fragment = new ProfileOwnerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_USER_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        boolean nightMode = loadNightMode();
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        FragmentOwnerProfileBinding binding = FragmentOwnerProfileBinding.inflate(inflater, container, false);
        binding.nightMoodSwitch.setChecked(nightMode);

        MyViewModel viewModel = new ViewModelProvider(this).get(MyViewModel.class);

        viewModel.getUserById(1).observe(getViewLifecycleOwner(), user -> {

            binding.nameTv.setText(user.getName());
            binding.emailTv.setText(user.getEmail());

            Glide.with(this)
                    .load(user.getPhotoUri())
                    .centerCrop()
                    .placeholder(R.drawable.man)
                    .error(R.drawable.man)
                    .into(binding.userIv);

        });

        binding.editProfileCl.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), UpdateUserDataActivity.class).putExtra("userId", 1));
        });

        binding.languageCl.setOnClickListener(view -> {
            launcher.launch(new Intent(getActivity(), ChooseLanguageActivity.class));
        });

        binding.nightMoodSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                saveNightMode(true);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                saveNightMode(false);
            }

            requireActivity().recreate();
        });


        binding.logoutCl.setOnClickListener(view -> {

            BottomSheetDialog dialog = new BottomSheetDialog(requireContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_dialog_logout);

            dialog.findViewById(R.id.logoutBt).setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                dialog.dismiss();
            });

            dialog.findViewById(R.id.cancelBt).setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();

        });

        return binding.getRoot();
    }

    private void saveNightMode(boolean isNightMode) {
        requireActivity()
                .getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
                .edit()
                .putBoolean("night_mode", isNightMode)
                .apply();
    }

    private boolean loadNightMode() {
        return requireActivity()
                .getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
                .getBoolean("night_mode", false);
    }


}