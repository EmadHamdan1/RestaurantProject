package com.emad.restaurantproject.OwnerScreens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emad.restaurantproject.R;
import com.emad.restaurantproject.database.entities.Category;

import java.util.List;

public class CategorySpAdapter extends ArrayAdapter<Category> {


    private final LayoutInflater inflater;
    private final List<Category> categories;

    public CategorySpAdapter(@NonNull Context context, @NonNull List<Category> categories) {
        super(context, 0, categories);
        this.inflater = LayoutInflater.from(context);
        this.categories = categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent, R.layout.spinner_item_with_icon);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, convertView, parent, R.layout.spinner_item_with_icon);
    }

    private View createView(int position, View convertView, ViewGroup parent, int layout) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        ImageView icon = convertView.findViewById(R.id.item_icon);
        TextView name = convertView.findViewById(R.id.item_name);

        Category category = categories.get(position);
        name.setText(category.getName());
        icon.setImageResource(category.getImageId());

        return convertView;
    }
}
