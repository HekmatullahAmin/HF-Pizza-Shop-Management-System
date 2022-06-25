package com.example.hfpizza.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.activities.FavouriteActivity;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Item;

import java.util.ArrayList;

public class ExploreMenuItemAdapter extends RecyclerView.Adapter<ExploreMenuItemAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Item> items;
    private LinearLayout emptyFavouriteLinearLayout;
    private String className;
    private DatabaseHandler databaseHandler;
    private int isFavourite;

    public ExploreMenuItemAdapter(Context context, ArrayList<Item> items,
                                  LinearLayout emptyFavouriteLinearLayout, String className) {
        this.context = context;
        this.items = items;
        this.emptyFavouriteLinearLayout = emptyFavouriteLinearLayout;
        this.className = className;
        databaseHandler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customRow = LayoutInflater.from(parent.getContext()).inflate(com.example.hfpizza.R.layout.custom_explore_menu_item_row,
                parent, false);
        return new ViewHolder(customRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = items.get(position);
        holder.itemNameTV.setText(item.getItemName());
        holder.itemDescription.setText(item.getItemDescription());
        byte[] byteImage = item.getItemImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.itemImage.setImageBitmap(bitmap);

        isFavourite = item.getItemIsFavourite();
        if (isFavourite == 1) {
            holder.itemIsFavorite.setImageResource(com.example.hfpizza.R.drawable.heart_selected);
        } else {
            holder.itemIsFavorite.setImageResource(com.example.hfpizza.R.drawable.heart_unselected);
        }

        double itemAmount = item.getItemPrice();
        if (itemAmount != 0) {
            holder.itemAmountTV.setText(String.valueOf(itemAmount));
        } else {
            holder.itemAmountTV.setText(String.valueOf(item.getItemRegularPrice()));
        }

        if (item.getItemHowOftenIsAddedToCart() > 0) {
            holder.addToCartItem.setVisibility(View.GONE);
            holder.itemAddedTV.setVisibility(View.VISIBLE);
        } else {
            holder.addToCartItem.setVisibility(View.VISIBLE);
            holder.itemAddedTV.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameTV, itemAmountTV, itemDescription, itemAddedTV;
        private ImageView itemImage, itemIsFavorite;
        private Button addToCartItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemAddedTV = itemView.findViewById(R.id.customExploreMenuItemAddedItemTextViewId);
            itemNameTV = itemView.findViewById(com.example.hfpizza.R.id.customExploreMenuItemNameTextViewId);
            itemAmountTV = itemView.findViewById(com.example.hfpizza.R.id.customExploreMenuItemPriceTextViewId);
            itemDescription = itemView.findViewById(com.example.hfpizza.R.id.customExploreMenuItemDescriptionTextViewId);
            itemImage = itemView.findViewById(com.example.hfpizza.R.id.customExploreMenuItemImageViewId);
            itemIsFavorite = itemView.findViewById(com.example.hfpizza.R.id.customExploreMenuItemIsFavouriteImageViewId);
            addToCartItem = itemView.findViewById(com.example.hfpizza.R.id.customExploreMenuItemAddToCartButtonId);

            itemIsFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Item item = items.get(position);
                    if (item.getItemIsFavourite() == 1) {
//                        if our item is already favourite remove it from favourite
                        item.setItemIsFavourite(0);
                        databaseHandler.updateItem(item);

//                        if we are using this adapter from favourite activity
                        if (className.equals(FavouriteActivity.class.getName())) {
                            items.remove(item);
                            notifyItemRemoved(position);
                        }
                        itemIsFavorite.setImageResource(R.drawable.heart_unselected);

                    } else {
                        item.setItemIsFavourite(1);
                        databaseHandler.updateItem(item);
                        itemIsFavorite.setImageResource(R.drawable.heart_selected);
                    }

                    notifyDataSetChanged();

                    if (className.equals(FavouriteActivity.class.getName())) {
                        if (databaseHandler.totalFavouriteItemCount() == 0) {
                            emptyFavouriteLinearLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            });

            addToCartItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item item = items.get(getAdapterPosition());
                    item.setItemHowOftenIsAddedToCart(1);
                    databaseHandler.updateItem(item);
                    addToCartItem.setVisibility(View.GONE);
                    itemAddedTV.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    public void refreshRecyclerView(ArrayList<Item> items) {
        this.items = items;
        notifyDataSetChanged();
    }
}
