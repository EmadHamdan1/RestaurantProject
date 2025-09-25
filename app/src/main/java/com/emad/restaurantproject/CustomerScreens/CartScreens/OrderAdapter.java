package com.emad.restaurantproject.CustomerScreens.CartScreens;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;

import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.restaurantproject.CustomerScreens.HomeScreens.NonScrollRecyclerView;

import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.database.entities.Order;

import com.emad.restaurantproject.database.entities.OrderItem;
import com.emad.restaurantproject.databinding.ItemOrderBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {


    List<Order> orders;
    List<OrderItem> allOrderItems;
    List<MenuItem> menuItems;

    public OrderAdapter(List<Order> orders, List<OrderItem> allOrderItems, List<MenuItem> menuItems) {
        this.orders = orders;
        this.allOrderItems = allOrderItems;
        this.menuItems = menuItems;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMenuItems(List<MenuItem> newMenuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(newMenuItems);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateOrders(List<Order> newOrder) {
        this.orders.clear();
        this.orders.addAll(newOrder);
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateOrderItems(List<OrderItem> newOrderItems) {
        this.allOrderItems.clear();
        this.allOrderItems.addAll(newOrderItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(ItemOrderBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {


        Order order = orders.get(position);
        holder.orderNumberTv.setText(String.valueOf(order.getOrderNumber()));
        holder.totalPriceOrderTv.setText(String.valueOf(order.getTotalPrice()));

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String formattedDate = sdf.format(order.getDate());

        holder.dateOrderTv.setText(formattedDate);

        List<OrderItem> orderItemsForThisOrder = new ArrayList<>();
        for (OrderItem item : allOrderItems) {
            if (item.getOrderId() == order.getOrderId()) {
                orderItemsForThisOrder.add(item);
            }
        }

        OrderItemBillAdapter adapter = new OrderItemBillAdapter(orderItemsForThisOrder, menuItems);
        holder.orderItemsRv.setLayoutManager(new LinearLayoutManager(holder.itemView.getContext()));
        holder.orderItemsRv.setAdapter(adapter);

    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {

        TextView orderNumberTv, totalPriceOrderTv, dateOrderTv;
        NonScrollRecyclerView orderItemsRv;

        public OrderViewHolder(ItemOrderBinding binding) {
            super(binding.getRoot());

            orderNumberTv = binding.orderNumberTv;
            totalPriceOrderTv = binding.totalPriceOrderTv;
            dateOrderTv = binding.dateOrderTv;
            orderItemsRv = binding.orderItemsRv;

        }
    }

}
