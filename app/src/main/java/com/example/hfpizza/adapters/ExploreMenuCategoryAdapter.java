package com.example.hfpizza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Category;
import com.example.hfpizza.model.Item;

import java.util.ArrayList;

public class ExploreMenuCategoryAdapter extends RecyclerView.Adapter<ExploreMenuCategoryAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Category> categories;
    private DatabaseHandler databaseHandler;
    private ExploreMenuItemAdapter itemAdapter;

    public ExploreMenuCategoryAdapter(Context context, ArrayList<Category> categories, ExploreMenuItemAdapter itemAdapter) {
        this.context = context;
        this.categories = categories;
        this.itemAdapter = itemAdapter;
        databaseHandler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_explore_menu_category_row,
                parent, false);
        return new ViewHolder(customRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryNameTV.setText(category.getCategoryName());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryNameTV;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryNameTV = itemView.findViewById(R.id.customExploreMenuCategoryRowNameTextViewId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Category category = categories.get(getAdapterPosition());
                    ArrayList<Item> categoriesItems = databaseHandler.getSpecificCategoryItems(category.getCategoryId());
                    itemAdapter.refreshRecyclerView(categoriesItems);
                }
            });
        }
    }
}
