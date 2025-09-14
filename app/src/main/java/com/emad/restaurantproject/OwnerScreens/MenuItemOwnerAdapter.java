package com.emad.restaurantproject.OwnerScreens;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;

import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.entities.Category;
import com.emad.restaurantproject.database.entities.MenuItem;
import com.emad.restaurantproject.databinding.ItemMenuItemOwnerBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;
public class MenuItemOwnerAdapter extends RecyclerView.Adapter<MenuItemOwnerAdapter.MenuItemViewHolder> {

    List<MenuItem> menuItems;
    List<Category> categories;
    MenuItemListener listener;


    public MenuItemOwnerAdapter(List<MenuItem> menuItems, List<Category> categories, MenuItemListener listener) {
        this.menuItems = menuItems;
        this.categories = categories;
        this.listener = listener;
    }

    public void updateMenuItems(List<MenuItem> newMenuItems) {
        this.menuItems.clear();
        this.menuItems.addAll(newMenuItems);
        notifyDataSetChanged();
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MenuItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MenuItemViewHolder(ItemMenuItemOwnerBinding.inflate
                (LayoutInflater.from(parent.getContext()), parent, false));
    }

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    public void onBindViewHolder(@NonNull MenuItemViewHolder holder, int position) {

        MenuItem item = menuItems.get(position);

        holder.nameItemTv.setText(item.getName());
        holder.caloriesItemTv.setText("🔥 " + item.getCalories() + " Calories");
        holder.priceItemTv.setText("$ " + item.getPrice());

        Glide.with(holder.imageItemIv.getContext())
                .load(item.getImageUri())
                .placeholder(R.drawable.main_dish)
                .error(R.drawable.restaurant)
                .centerCrop()
                .into(holder.imageItemIv);

        holder.categoryMenuItemIv.setImageResource(categories.get(item.getCategoryId() - 1).getImageId());

        holder.openMenuItemIv.setOnClickListener(view -> {

            BottomSheetDialog dialog = new BottomSheetDialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.bottom_sheet_dialog);


            dialog.findViewById(R.id.edit_Item).setOnClickListener(v -> {
                listener.onEditItem(item);
                dialog.dismiss();
            });

            dialog.findViewById(R.id.delete_item).setOnClickListener(v -> {
                listener.onDeleteItem(item);
                dialog.dismiss();
            });

            dialog.show();

        });

    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public static class MenuItemViewHolder extends RecyclerView.ViewHolder {

        TextView nameItemTv, caloriesItemTv, priceItemTv;
        ImageView imageItemIv, openMenuItemIv, categoryMenuItemIv;

        public MenuItemViewHolder(ItemMenuItemOwnerBinding binding) {
            super(binding.getRoot());

            nameItemTv = binding.nameItemTv;
            caloriesItemTv = binding.caloriesItemTv;
            priceItemTv = binding.priceItemTv;
            imageItemIv = binding.imageItemIv;
            openMenuItemIv = binding.openMenuItemIv;
            categoryMenuItemIv = binding.categoryMenuItemIv;

        }
    }

}
