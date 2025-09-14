package com.emad.restaurantproject.CustomerScreens;


import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.ItemMenuItemCustomerBinding;

import java.util.List;

public class MenuItemCustomerAdapter extends RecyclerView.Adapter<MenuItemCustomerAdapter.MenuItemCustomerViewHolder> {

    List<MenuItem> menuItems;
    ItemHomeListener listener;

    public MenuItemCustomerAdapter(List<MenuItem> menuItems, ItemHomeListener listener) {
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateMenuItems(List<MenuItem> newMenuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(newMenuItems);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MenuItemCustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuItemCustomerViewHolder(ItemMenuItemCustomerBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MenuItemCustomerViewHolder holder, int position) {

        MenuItem menuItem = menuItems.get(position);

        holder.nameItemTv.setText(menuItem.getName());
        holder.caloriesItemTv.setText("🔥 " + menuItem.getCalories() + " Calories");
        holder.priceItemTv.setText("$ " + menuItem.getPrice());

        Glide.with(holder.imageItemCustomerIv.getContext())
                .load(menuItem.getImageUri())
                .placeholder(R.drawable.restaurant)
                .error(R.drawable.restaurant)
                .into(holder.imageItemCustomerIv);

        holder.itemView.setOnClickListener(view -> {

            listener.onClickMenuItem(menuItem);

        });

    }


    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuItemCustomerViewHolder extends RecyclerView.ViewHolder {

        TextView nameItemTv, caloriesItemTv, priceItemTv;
        ImageView imageItemCustomerIv;

        public MenuItemCustomerViewHolder(ItemMenuItemCustomerBinding binding) {
            super(binding.getRoot());

            nameItemTv = binding.nameItemTv;
            caloriesItemTv = binding.caloriesItemTv;
            priceItemTv = binding.priceItemTv;
            imageItemCustomerIv = binding.imageItemCustomerIv;

        }
    }

}
