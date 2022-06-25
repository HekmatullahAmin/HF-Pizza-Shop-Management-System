package com.example.hfpizza.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Item;

import java.util.ArrayList;

public class BestSellersRecyclerViewAdapter extends RecyclerView.Adapter<BestSellersRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Item> bestSellersItem;
    private DatabaseHandler databaseHandler;

    public BestSellersRecyclerViewAdapter(Context context, ArrayList<Item> bestSellersItem) {
        this.context = context;
        this.bestSellersItem = bestSellersItem;
        databaseHandler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customBestSellerRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_best_seller_row, parent, false);
        return new ViewHolder(customBestSellerRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = bestSellersItem.get(position);
        holder.bestSellerItemName.setText(item.getItemName());
        holder.bestSellerItemAmount.setText(String.valueOf(item.getItemPrice()));
        byte[] byteImage = item.getItemImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteImage, 0, byteImage.length);
        holder.bestSellerImageView.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return bestSellersItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView bestSellerImageView;
        private TextView bestSellerItemName, bestSellerItemAmount;
        private Button addToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bestSellerImageView = itemView.findViewById(R.id.customBestSellerRowImageViewId);
            bestSellerItemName = itemView.findViewById(R.id.customBestSellerRowItemNameTextViewId);
            bestSellerItemAmount = itemView.findViewById(R.id.customBestSellerRowItemAmountTextViewId);
            addToCart = itemView.findViewById(R.id.customBestSellerRowAddToCartButtonId);

            addToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Item item = bestSellersItem.get(getAdapterPosition());
                    item.setItemHowOftenIsAddedToCart(1);
                    databaseHandler.updateItem(item);
                    addToCart.setText("Added");
                    notifyDataSetChanged();
                }
            });
        }
    }
}
