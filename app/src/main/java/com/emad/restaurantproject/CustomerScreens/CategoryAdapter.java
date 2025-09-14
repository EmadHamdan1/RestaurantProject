package com.emad.restaurantproject.CustomerScreens;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.entities.Category;
import com.emad.restaurantproject.databinding.ItemCategoryBinding;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private int selectedPosition = 0;
    List<Category> categories;
    ItemHomeListener listener;

    public CategoryAdapter(List<Category> categories, ItemHomeListener listener) {
        this.categories = categories;
        categories.add(0, new Category("All", R.drawable.all));
        this.listener = listener;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        Category category = categories.get(position);
        holder.categoryNameTv.setText(category.getName());

        Glide.with(holder.categoryIv.getContext())
                .load(category.getImageId())
                .placeholder(R.drawable.main_dish)
                .error(R.drawable.restaurant)
                .centerCrop()
                .into(holder.categoryIv);

        holder.itemView.setSelected(position == selectedPosition);

        holder.itemView.setOnClickListener(v -> {
            int oldPos = selectedPosition;
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(oldPos);
            notifyItemChanged(selectedPosition);
            listener.onClickCategoryItems(categories.get(selectedPosition).getCategoryId());
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView categoryIv;
        TextView categoryNameTv;

        public CategoryViewHolder(ItemCategoryBinding binding) {
            super(binding.getRoot());

            categoryIv = binding.categoryIv;
            categoryNameTv = binding.categoryNameTv;

        }
    }

}
