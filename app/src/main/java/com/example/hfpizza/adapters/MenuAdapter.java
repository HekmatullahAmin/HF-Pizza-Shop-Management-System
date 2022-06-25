package com.example.hfpizza.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.activities.ExploreMenuActivity;
import com.example.hfpizza.model.Category;
import com.example.hfpizza.utils.Constants;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> categories;

    public MenuAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customMenuRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_category_row,
                parent, false);
        return new ViewHolder(customMenuRow);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapter.ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getCategoryName());
        byte[] byteImage = category.getCategoryImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.categoryImage.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryName;
        private CircleImageView categoryImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.customCategoryRowNameTextViewId);
            categoryImage = itemView.findViewById(R.id.customCategoryRowCircleImageViewId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Category category = categories.get(getAdapterPosition());
                    Intent exploreMenuActivityIntent = new Intent(context, ExploreMenuActivity.class);
//                    Bundle myCategoryBundle = new Bundle();
//                    myCategoryBundle.putSerializable(Constants.SELECTED_CATEGORY_KEY, category);
//                    exploreMenuActivityIntent.putExtras(myCategoryBundle);
                    exploreMenuActivityIntent.putExtra(Constants.SELECTED_CATEGORY_ID, category.getCategoryId());
                    exploreMenuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(exploreMenuActivityIntent);
                }
            });
        }
    }
}
