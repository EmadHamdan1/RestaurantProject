package com.emad.restaurantproject.CustomerScreens.CartScreens;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.CartItem;
import com.emad.restaurantproject.database.entities.Order;
import com.emad.restaurantproject.database.entities.OrderItem;
import com.emad.restaurantproject.databinding.FragmentLiveCartBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;

import android.graphics.Canvas;

public class LiveCartFragment extends Fragment implements CartAdapter.CartQuantityListener {


    private static final String ARG_USER_ID = "customerId";

    private int userId;
    MyViewModel viewModel;
    CartAdapter adapter;

    public LiveCartFragment() {
        // Required empty public constructor
    }

    public static LiveCartFragment newInstance(int userId) {
        LiveCartFragment fragment = new LiveCartFragment();
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

        FragmentLiveCartBinding binding = FragmentLiveCartBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(MyViewModel.class);

        adapter = new CartAdapter(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), this);
        binding.cartItemRv.setAdapter(adapter);
        binding.cartItemRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        viewModel.getAllMenuItems().observe(getViewLifecycleOwner(), adapter::updateMenuItems);

        viewModel.getCartItemByCustomerIdLive(userId).observe(getViewLifecycleOwner(), adapter::updateCartItems);

        viewModel.getAllCategories().observe(getViewLifecycleOwner(), adapter::setCategories);

        viewModel.getCartItemByCustomerIdLive(userId).observe(getViewLifecycleOwner(), cartItems -> {

            binding.totalPriceTv.setText(String.valueOf(CalcTotalPrice(cartItems)));

        });

        new ItemTouchHelper(getItemTouchHelper()).attachToRecyclerView(binding.cartItemRv);

        binding.checkoutBt.setOnClickListener(view -> {
            CheckoutOrder();
        });
        return binding.getRoot();
    }

    private ItemTouchHelper.SimpleCallback getItemTouchHelper() {
        return new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            Drawable deleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.outline_cancel_24);
            ColorDrawable background = new ColorDrawable(Color.BLACK);

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                CartItem itemToDelete = adapter.getCartItemAt(position);

                Executors.newSingleThreadExecutor().execute(() -> viewModel.deleteCartItem(itemToDelete));
                adapter.removeItem(position);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c,
                                    @NonNull RecyclerView recyclerView,
                                    @NonNull RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY,
                                    int actionState, boolean isCurrentlyActive) {

                View itemView = viewHolder.itemView;

                int iconMargin = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;

                int boxSize = deleteIcon.getIntrinsicWidth() + 40;
                int boxLeft = itemView.getLeft() + iconMargin - 20;
                int boxTop = itemView.getTop() + iconMargin - 20;
                int boxRight = boxLeft + boxSize;
                int boxBottom = boxTop + boxSize;

                ColorDrawable boxBackground = new ColorDrawable(Color.BLACK);
                boxBackground.setBounds(boxLeft, boxTop, boxRight, boxBottom);
                boxBackground.draw(c);

                int iconLeft = itemView.getLeft() + iconMargin;
                int iconTop = itemView.getTop() + iconMargin;
                int iconRight = iconLeft + deleteIcon.getIntrinsicWidth();
                int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
                deleteIcon.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
    }


    double CalcTotalPrice(List<CartItem> cartItems) {
        double totalPrice = 0;
        if (cartItems != null) {
            for (int i = 0; i < cartItems.size(); i++) {
                totalPrice += cartItems.get(i).getTotalPrice();
            }
        }
        return totalPrice;
    }

    void CheckoutOrder() {
        Executors.newSingleThreadExecutor().execute(() -> {

            List<CartItem> cartItems = viewModel.getCartItemByCustomerId(userId);

            if (!cartItems.isEmpty()) {

                Integer lastOrderNumber = viewModel.getLastOrderNumberForUser(userId);
                int nextOrderNumber = (lastOrderNumber == null ? 1 : lastOrderNumber + 1);

                Order order = new Order(CalcTotalPrice(cartItems), "Completed", userId,
                        Calendar.getInstance().getTime(), nextOrderNumber);

                long orderId = viewModel.insertOrder(order);

                for (CartItem cartItem : cartItems) {
                    OrderItem orderItem = new OrderItem((int) orderId,
                            cartItem.getMenuItemId(), cartItem.getQuantity(), cartItem.getTotalPrice());
                    viewModel.insertOrderItem(orderItem);
                }

                viewModel.clearCart(userId);

            } else {
                requireActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "The Cart is Empty", Toast.LENGTH_SHORT).show();
                });
            }

        });

    }

    @Override
    public void plusQuantity(CartItem cartItem) {
        Executors.newSingleThreadExecutor().execute(() -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            viewModel.updateCartItem(cartItem);
        });
    }

    @Override
    public void minusQuantity(CartItem cartItem) {
        Executors.newSingleThreadExecutor().execute(() -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                viewModel.updateCartItem(cartItem);
            }
        });
    }
}