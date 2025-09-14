package com.emad.restaurantproject.WelcomeScreens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emad.restaurantproject.databinding.FragmentWelcomeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WelcomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WelcomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_IMAGE = "image";
    private static final String ARG_TITLE = "title";
    private static final String ARG_TEXT = "text";

    // TODO: Rename and change types of parameters
    private int imageId;
    private String title;
    private String text;

    public WelcomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static WelcomeFragment newInstance(int imageId, String title, String text) {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, imageId);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageId = getArguments().getInt(ARG_IMAGE);
            title = getArguments().getString(ARG_TITLE);
            text = getArguments().getString(ARG_TEXT);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentWelcomeBinding binding = FragmentWelcomeBinding.inflate(inflater, container, false);

        if (title != null && imageId != 0 && text != null) {
            binding.imageView.setImageResource(imageId);
            binding.titleTv.setText(title);
            binding.textTv.setText(text);
        }

        return binding.getRoot();
    }
}