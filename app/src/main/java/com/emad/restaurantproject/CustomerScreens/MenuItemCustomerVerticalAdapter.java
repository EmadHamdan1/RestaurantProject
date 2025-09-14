package com.emad.restaurantproject.CustomerScreens;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.OwnerScreens.MenuItemListener;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.entities.Category;
import com.emad.restaurantproject.database.entities.MenuItem;

import com.emad.restaurantproject.databinding.ItemMenuItemOwnerBinding;
import com.emad.restaurantproject.databinding.ItemMenuItemVerticalCustomerBinding;

import java.util.List;

public class MenuItemCustomerVerticalAdapter extends RecyclerView.Adapter<MenuItemCustomerVerticalAdapter.MenuItemCustomerViewHolder> {

    List<MenuItem> menuItems;
    List<Category> categories;
    ItemHomeListener listener;

    public MenuItemCustomerVerticalAdapter(List<MenuItem> menuItems, List<Category> categories, ItemHomeListener listener) {
        this.menuItems = menuItems;
        this.categories = categories;
        this.listener = listener;

    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
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
        return new MenuItemCustomerViewHolder(ItemMenuItemVerticalCustomerBinding.inflate(
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


        holder.categoryMenuItemIv.setImageResource(categories.get(menuItem.getCategoryId() - 1).getImageId());


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
        ImageView imageItemCustomerIv, categoryMenuItemIv;

        public MenuItemCustomerViewHolder(ItemMenuItemVerticalCustomerBinding binding) {
            super(binding.getRoot());

            nameItemTv = binding.nameItemTv;
            caloriesItemTv = binding.caloriesItemTv;
            priceItemTv = binding.priceItemTv;
            imageItemCustomerIv = binding.imageItemIv;
            categoryMenuItemIv = binding.categoryMenuItemIv;

        }
    }

}
