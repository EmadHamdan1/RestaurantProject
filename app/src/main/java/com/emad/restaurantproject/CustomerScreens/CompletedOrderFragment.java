package com.emad.restaurantproject.CustomerScreens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.databinding.FragmentCartBinding;
import com.emad.restaurantproject.databinding.FragmentCompletedOrderBinding;

import java.util.ArrayList;


public class CompletedOrderFragment extends Fragment {

    MyViewModel viewModel;
    private static final String ARG_USER_ID = "customerId";

    private int userId;


    public CompletedOrderFragment() {
        // Required empty public constructor
    }


    public static CompletedOrderFragment newInstance(int userId) {
        CompletedOrderFragment fragment = new CompletedOrderFragment();
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
        FragmentCompletedOrderBinding binding = FragmentCompletedOrderBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        OrderAdapter adapter = new OrderAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        binding.completedOrdersRv.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.completedOrdersRv.setAdapter(adapter);


        viewModel.getOrdersByCustomerId(userId).observe(getViewLifecycleOwner(), orders -> {
            adapter.updateOrders(orders);
        });

        viewModel.getAllOrderItems().observe(getViewLifecycleOwner(), orderItems -> {
            adapter.updateOrderItems(orderItems);
        });

        viewModel.getAllMenuItems().observe(getViewLifecycleOwner(), menuItems -> {
            adapter.updateMenuItems(menuItems);
        });
        return binding.getRoot();
    }
}