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
import com.emad.restaurantproject.database.entities.CartItem;
import com.emad.restaurantproject.database.entities.Category;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.ItemCartCustomerBinding;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MenuItemCartViewHolder> {

    List<CartItem> cartItems;
    List<MenuItem> menuItems;
    List<Category> categories;

    public CartAdapter(List<CartItem> cartItems, List<MenuItem> menuItems, List<Category> categories) {
        this.cartItems = cartItems;
        this.menuItems = menuItems;
        this.categories = categories;
    }

    public void updateMenuItems(List<MenuItem> newMenuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(newMenuItems);
        notifyDataSetChanged();
    }

    public void updateCartItems(List<CartItem> newCartItems) {
        this.cartItems.clear();
        this.cartItems.addAll(newCartItems);
        notifyDataSetChanged();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }

    public CartItem getCartItemAt(int position) {
        return cartItems.get(position);
    }

    public void removeItem(int position) {
        cartItems.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public MenuItemCartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartAdapter.MenuItemCartViewHolder(ItemCartCustomerBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MenuItemCartViewHolder holder, int position) {

        CartItem cartItem = cartItems.get(position);

        MenuItem menuItem = null;
        for (MenuItem item : menuItems) {
            if (item.getMenuItemId() == cartItem.getMenuItemId()) {
                menuItem = item;
                break;
            }
        }

        if (menuItem != null) {

            holder.nameItemTv.setText(menuItem.getName());
            holder.caloriesItemTv.setText("🔥 " + menuItem.getCalories() + " Calories");
            holder.totalPriceItemTv.setText(String.valueOf(cartItem.getTotalPrice()));
            holder.quantityItemTv.setText(cartItem.getQuantity() + " Item");

            Glide.with(holder.imageItemIv.getContext())
                    .load(menuItem.getImageUri())
                    .placeholder(R.drawable.main_dish)
                    .error(R.drawable.restaurant)
                    .centerCrop()
                    .into(holder.imageItemIv);
        }

        if (menuItem != null) {
            for (Category category : categories) {
                if (category.getCategoryId() == menuItem.getCategoryId()) {
                    holder.categoryMenuItemIv.setImageResource(category.getImageId());
                    break;
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class MenuItemCartViewHolder extends RecyclerView.ViewHolder {

        TextView nameItemTv, caloriesItemTv, totalPriceItemTv, quantityItemTv;
        ImageView imageItemIv, categoryMenuItemIv;

        public MenuItemCartViewHolder(ItemCartCustomerBinding binding) {
            super(binding.getRoot());

            nameItemTv = binding.nameItemTv;
            caloriesItemTv = binding.caloriesItemTv;
            totalPriceItemTv = binding.totalPriceItemTv;
            quantityItemTv = binding.quantityItemTv;
            imageItemIv = binding.imageItemIv;
            categoryMenuItemIv = binding.categoryMenuItemIv;

        }
    }
}
