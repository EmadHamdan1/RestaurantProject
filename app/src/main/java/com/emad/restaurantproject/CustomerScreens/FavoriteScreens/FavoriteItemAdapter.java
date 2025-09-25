package com.emad.restaurantproject.CustomerScreens.FavoriteScreens;

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
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.CustomerScreens.HomeScreens.ItemHomeListener;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.data.MyViewModel;
import com.emad.restaurantproject.database.entities.Favorite;
import com.emad.restaurantproject.database.entities.MenuItem;

import com.emad.restaurantproject.databinding.ItemFavoriteItemMenuBinding;

import java.util.List;
import java.util.concurrent.Executors;

public class FavoriteItemAdapter extends RecyclerView.Adapter<FavoriteItemAdapter.FavoriteItemViewHolder> {

    List<MenuItem> menuItems;
    MyViewModel viewModel;
    int userId;
    LifecycleOwner lifecycleOwner;
    ItemHomeListener listener;


    public FavoriteItemAdapter(List<MenuItem> menuItems, int userId, LifecycleOwner lifecycleOwner, ItemHomeListener listener) {
        this.menuItems = menuItems;
        this.userId = userId;
        this.lifecycleOwner = lifecycleOwner;
        this.listener = listener;
    }

    public void updateMenuItems(List<MenuItem> newMenuItems) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return menuItems.size();
            }

            @Override
            public int getNewListSize() {
                return newMenuItems.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return menuItems.get(oldItemPosition).getMenuItemId() ==
                        newMenuItems.get(newItemPosition).getMenuItemId();
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return menuItems.get(oldItemPosition).equals(newMenuItems.get(newItemPosition));
            }
        });

        this.menuItems.clear();
        this.menuItems.addAll(newMenuItems);
        diffResult.dispatchUpdatesTo(this);
    }


    @NonNull
    @Override
    public FavoriteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        viewModel = new ViewModelProvider((ViewModelStoreOwner) parent.getContext()).get(MyViewModel.class);
        return new FavoriteItemViewHolder(ItemFavoriteItemMenuBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false));

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FavoriteItemViewHolder holder, int position) {

        MenuItem menuItem = menuItems.get(position);

        if (menuItem != null) {

            holder.nameItemTv.setText(menuItem.getName());
            holder.caloriesItemTv.setText("🔥 " + menuItem.getCalories() + holder.context.getString(R.string.calories_item_customer));
            holder.priceItemTv.setText(String.valueOf(menuItem.getPrice()));

            Glide.with(holder.imageItemIv.getContext())
                    .load(menuItem.getImageUri())
                    .placeholder(R.drawable.main_dish)
                    .error(R.drawable.restaurant)
                    .centerCrop()
                    .into(holder.imageItemIv);
        }

        holder.favoriteMenuItemIv.setOnClickListener(view -> {
            Executors.newSingleThreadExecutor().execute(() -> {
                Favorite favorite = viewModel.getFavoriteItemByUserIdAndItemId(userId, menuItem.getMenuItemId());
                if (favorite != null) viewModel.deleteFavoriteItem(favorite);
            });
        });

        viewModel.getFavoriteItemByUserIdAndItemIdLive(userId, menuItem.getMenuItemId())
                .observe(lifecycleOwner, favorite -> {
                    if (favorite != null) {
                        holder.favoriteMenuItemIv.setImageResource(R.drawable.heart_y);
                    } else {
                        holder.favoriteMenuItemIv.setImageResource(R.drawable.heart_outline);
                    }
                });


        holder.itemView.setOnClickListener(view -> {

            listener.onClickMenuItem(menuItem, holder.getAdapterPosition());

        });

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class FavoriteItemViewHolder extends RecyclerView.ViewHolder {

        TextView nameItemTv, caloriesItemTv, priceItemTv;
        ImageView imageItemIv, favoriteMenuItemIv;
        Context context;

        public FavoriteItemViewHolder(ItemFavoriteItemMenuBinding binding) {
            super(binding.getRoot());

            nameItemTv = binding.nameItemTv;
            caloriesItemTv = binding.caloriesItemTv;
            priceItemTv = binding.priceItemTv;
            imageItemIv = binding.imageItemIv;
            favoriteMenuItemIv = binding.favoriteMenuItemIv;
            context = binding.getRoot().getContext();

        }
    }

}
