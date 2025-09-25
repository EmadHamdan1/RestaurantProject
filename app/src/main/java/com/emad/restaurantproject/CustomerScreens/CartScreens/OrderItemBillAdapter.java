package com.emad.restaurantproject.CustomerScreens.CartScreens;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.database.entities.OrderItem;
import com.emad.restaurantproject.databinding.ItemOrderItemBillBinding;

import java.util.List;

public class OrderItemBillAdapter extends RecyclerView.Adapter<OrderItemBillAdapter.OrderItemBillViewHolder> {

    List<OrderItem> ordersItems;
    List<MenuItem> menuItems;

    public OrderItemBillAdapter(List<OrderItem> ordersItems, List<MenuItem> menuItems) {
        this.ordersItems = ordersItems;
        this.menuItems = menuItems;
    }

    @NonNull
    @Override
    public OrderItemBillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderItemBillViewHolder(ItemOrderItemBillBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderItemBillViewHolder holder, int position) {

        OrderItem orderItem = ordersItems.get(position);

        for (MenuItem menuItem : menuItems) {
            if (menuItem.getMenuItemId() == orderItem.getMenuItemId()) {
                holder.nameItemTv.setText(menuItem.getName());
            }
        }
        holder.quantityItemTv.setText(orderItem.getQuantity() + " × ");
        holder.priceItemTv.setText(String.valueOf(orderItem.getItemTotalPrice() / orderItem.getQuantity()));
        holder.totalPriceItemTv.setText(String.valueOf(orderItem.getItemTotalPrice()));

    }

    @Override
    public int getItemCount() {
        return ordersItems.size();
    }

    public static class OrderItemBillViewHolder extends RecyclerView.ViewHolder {

        TextView nameItemTv, quantityItemTv, priceItemTv, totalPriceItemTv;

        public OrderItemBillViewHolder(ItemOrderItemBillBinding binding) {
            super(binding.getRoot());

            nameItemTv = binding.nameItemTv;
            quantityItemTv = binding.quantityItemTv;
            priceItemTv = binding.priceItemTv;
            totalPriceItemTv = binding.totalPriceItemTv;


        }
    }

}
