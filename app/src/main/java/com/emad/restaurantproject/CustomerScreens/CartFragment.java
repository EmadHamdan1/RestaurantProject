package com.emad.restaurantproject.CustomerScreens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.CartItem;
import com.emad.restaurantproject.database.entities.Order;
import com.emad.restaurantproject.database.entities.OrderItem;
import com.emad.restaurantproject.databinding.FragmentCartBinding;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;


public class CartFragment extends Fragment {

    MyViewModel viewModel;
    FragmentCartBinding binding;
    private static final String ARG_USER_ID = "customerId";

    private int userId;

    public CartFragment() {
        // Required empty public constructor
    }

    public static CartFragment newInstance(int userId) {
        CartFragment fragment = new CartFragment();
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
        binding = FragmentCartBinding.inflate(inflater, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        viewModel.loadAllData(userId);

        ArrayList<String> categories = new ArrayList<>();
        categories.add("Live Order");
        categories.add("Completed Order");

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(LiveCartFragment.newInstance(userId));
        fragments.add(CompletedOrderFragment.newInstance(userId));

        binding.viewPagerVp.setAdapter(new CartAdapterVp((FragmentActivity) getContext(), fragments));


        new TabLayoutMediator(binding.cartTl, binding.viewPagerVp, (tab, position) -> {
            tab.setText(categories.get(position));
        }).attach();

        return binding.getRoot();
    }


}