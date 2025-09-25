package com.emad.restaurantproject.CustomerScreens.HomeScreens;


import android.annotation.SuppressLint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.Favorite;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.ItemMenuItemCustomerBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class MenuItemCustomerAdapter extends RecyclerView.Adapter<MenuItemCustomerAdapter.MenuItemCustomerViewHolder> {

    List<MenuItem> menuItems;
    ItemHomeListener listener;
    MyViewModel viewModel;
    int userId;
    LifecycleOwner lifecycleOwner;

    public MenuItemCustomerAdapter(List<MenuItem> menuItems, ItemHomeListener listener, int userId, LifecycleOwner lifecycleOwner) {
        this.menuItems = menuItems;
        this.listener = listener;
        this.userId = userId;
        this.lifecycleOwner = lifecycleOwner;
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
        viewModel = new ViewModelProvider((ViewModelStoreOwner) parent.getContext()).get(MyViewModel.class);
        return new MenuItemCustomerViewHolder(ItemMenuItemCustomerBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MenuItemCustomerViewHolder holder, int position) {

        MenuItem menuItem = menuItems.get(position);

        holder.nameItemTv.setText(menuItem.getName());
        holder.caloriesItemTv.setText("🔥 " + menuItem.getCalories() + holder.context.getString(R.string.calories_item_customer));
        holder.priceItemTv.setText("$ " + menuItem.getPrice());

        Glide.with(holder.imageItemCustomerIv.getContext())
                .load(menuItem.getImageUri())
                .placeholder(R.drawable.restaurant)
                .error(R.drawable.restaurant)
                .into(holder.imageItemCustomerIv);

        holder.itemView.setOnClickListener(view -> {
            listener.onClickMenuItem(menuItem, holder.getAdapterPosition());
        });

        holder.favoriteIv.setOnClickListener(view -> {
            boolean isFavorite = holder.favoriteIv.getTag() != null && (boolean) holder.favoriteIv.getTag();

            if (!isFavorite) {
                Executors.newSingleThreadExecutor().execute(() -> {
                    viewModel.insertFavoriteItem(new Favorite(menuItem.getMenuItemId(), userId));
                });
            } else {
                Executors.newSingleThreadExecutor().execute(() -> {
                    Favorite favorite = viewModel.getFavoriteItemByUserIdAndItemId(userId, menuItem.getMenuItemId());
                    if (favorite != null) viewModel.deleteFavoriteItem(favorite);
                });
            }
        });

        viewModel.getFavoriteItemByUserIdAndItemIdLive(userId, menuItem.getMenuItemId())
                .observe(lifecycleOwner, favorite -> {
                    if (favorite != null) {
                        holder.favoriteIv.setImageResource(R.drawable.heart_y);
                        holder.favoriteIv.setTag(true);
                    } else {
                        holder.favoriteIv.setImageResource(R.drawable.heart_outline);
                        holder.favoriteIv.setTag(false);
                    }
                });
    }


    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuItemCustomerViewHolder extends RecyclerView.ViewHolder {

        TextView nameItemTv, caloriesItemTv, priceItemTv;
        ImageView imageItemCustomerIv, favoriteIv;
        Context context;

        public MenuItemCustomerViewHolder(ItemMenuItemCustomerBinding binding) {
            super(binding.getRoot());

            nameItemTv = binding.nameItemTv;
            caloriesItemTv = binding.caloriesItemTv;
            priceItemTv = binding.priceItemTv;
            imageItemCustomerIv = binding.imageItemCustomerIv;
            favoriteIv = binding.favoriteIv;
            context = binding.getRoot().getContext();

        }
    }

}
