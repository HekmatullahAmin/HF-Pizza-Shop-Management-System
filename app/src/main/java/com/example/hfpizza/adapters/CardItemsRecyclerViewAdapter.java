package com.example.hfpizza.adapters;

import static com.example.hfpizza.R.drawable.ic_baseline_delete_24;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Item;

import java.util.ArrayList;
import java.util.function.ToDoubleBiFunction;

public class CardItemsRecyclerViewAdapter extends RecyclerView.Adapter<CardItemsRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private DatabaseHandler databaseHandler;
    private ArrayList<Item> addedItemsToCart;
    private TextView subTotalTV, discountTV, taxesAndChargesTV, grandTotalTV;
    private LinearLayout emptyCartLayout;
    private ScrollView scrollView;
    private Button selectedPaymentModeButton;

    public CardItemsRecyclerViewAdapter(Context context, ArrayList<Item> addedItemsToCart, TextView subTotalTV,
                                        TextView discountTV, TextView taxesAndChargesTV, TextView grandTotalTV,
                                        LinearLayout emptyCartLayout, ScrollView scrollView, Button selectPaymentModeButton) {
        this.context = context;
        this.addedItemsToCart = addedItemsToCart;
        this.subTotalTV = subTotalTV;
        this.discountTV = discountTV;
        this.taxesAndChargesTV = taxesAndChargesTV;
        this.grandTotalTV = grandTotalTV;
        this.emptyCartLayout = emptyCartLayout;
        this.scrollView = scrollView;
        this.selectedPaymentModeButton = selectPaymentModeButton;
        databaseHandler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customRow = LayoutInflater.from(parent.getContext()).inflate(com.example.hfpizza.R.layout.custom_cart_item_row,
                parent, false);
        return new ViewHolder(customRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = addedItemsToCart.get(position);
        if (item.getItemHowOftenIsAddedToCart() > 0) {
            holder.itemNameTV.setText(item.getItemName());
            holder.itemDescription.setText(item.getItemDescription());
            holder.amountOfItemTV.setText(String.valueOf(item.getItemHowOftenIsAddedToCart()));
            byte[] bytesImage = item.getItemImage();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytesImage, 0, bytesImage.length);
            holder.itemImage.setImageBitmap(bitmap);

            double itemAmount = item.getItemHowOftenIsAddedToCart() * item.getItemPrice();
            if (item.getItemPrice() != 0) {
                holder.itemPriceTV.setText(String.valueOf(itemAmount));
            } else {
                holder.itemPriceTV.setText(String.valueOf(item.getItemHowOftenIsAddedToCart() * item.getItemRegularPrice()));
            }

            //                holder.minusImage.setBackgroundResource(R.drawable.ic_baseline_delete_24);
            if (item.getItemHowOftenIsAddedToCart() == 1) {
//                holder.minusImage.setBackground(context.getResources().getDrawable(ic_baseline_delete_24));
                holder.minusImage.setBackground(context.getResources().getDrawable(ic_baseline_delete_24,null));
            }
        }
    }

    @Override
    public int getItemCount() {
        return addedItemsToCart.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView itemNameTV, itemPriceTV, itemDescription, amountOfItemTV;
        private ImageView itemImage, minusImage, plusImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNameTV = itemView.findViewById(com.example.hfpizza.R.id.customCartItemRowNameTextViewId);
            itemPriceTV = itemView.findViewById(com.example.hfpizza.R.id.customCartItemRowItemPriceTextViewId);
            itemDescription = itemView.findViewById(com.example.hfpizza.R.id.customCartItemRowDescriptionTextViewId);
            amountOfItemTV = itemView.findViewById(com.example.hfpizza.R.id.customCartItemRowAmountOfItemTextViewId);
            itemImage = itemView.findViewById(com.example.hfpizza.R.id.customCartItemRowItemImageViewId);
            minusImage = itemView.findViewById(com.example.hfpizza.R.id.customCartItemRowMinusImageViewId);
            plusImage = itemView.findViewById(com.example.hfpizza.R.id.customCartItemRowPlusImageViewId);

            minusImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Item item = addedItemsToCart.get(position);
                    int amountOfItem = Integer.parseInt(amountOfItemTV.getText().toString());

                    if (amountOfItem >= 2) {
                        amountOfItem--;
                        amountOfItemTV.setText(String.valueOf(amountOfItem));
                        item.setItemHowOftenIsAddedToCart(amountOfItem);
                        databaseHandler.updateItem(item);
                        if (item.getItemPrice() != 0) {
                            itemPriceTV.setText(String.valueOf(item.getItemHowOftenIsAddedToCart() * item.getItemPrice()));
                        } else {
                            itemPriceTV.setText(String.valueOf(item.getItemHowOftenIsAddedToCart() * item.getItemRegularPrice()));
                        }

                        if (amountOfItem == 1) {
                            minusImage.setBackground(context.getResources().getDrawable(ic_baseline_delete_24));
                        }
                    } else if (amountOfItem == 1) {
//                        to set it to zero
                        amountOfItem--;
                        item.setItemHowOftenIsAddedToCart(amountOfItem);
                        databaseHandler.updateItem(item);
                        addedItemsToCart.remove(item);
                        notifyItemRemoved(position);
//                        Todo after removing item it should again populate recyclerview
                    }

                    if (databaseHandler.totalItemCount() == 0) {
                        selectedPaymentModeButton.setVisibility(View.GONE);
                        scrollView.setVisibility(View.GONE);
                        emptyCartLayout.setVisibility(View.VISIBLE);
                    }

                    calculateTotal();
                }
            });

            plusImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Item item = addedItemsToCart.get(getAdapterPosition());
                    int amountOfItem = Integer.parseInt(amountOfItemTV.getText().toString());
                    amountOfItem++;
                    if (amountOfItem > 1) {
                        minusImage.setBackgroundResource(R.drawable.minus);
                    }
                    amountOfItemTV.setText(String.valueOf(amountOfItem));
                    item.setItemHowOftenIsAddedToCart(amountOfItem);
                    databaseHandler.updateItem(item);
                    if (item.getItemPrice() != 0) {
                        itemPriceTV.setText(String.valueOf(item.getItemHowOftenIsAddedToCart() * item.getItemPrice()));
                    } else {
                        itemPriceTV.setText(String.valueOf(item.getItemHowOftenIsAddedToCart() * item.getItemRegularPrice()));
                    }
                    calculateTotal();
                }
            });

        }
    }

    private void calculateTotal() {
        double subTotal = 0;
        for (Item item : addedItemsToCart) {
            if (item.getItemPrice() != 0) {
                subTotal = subTotal + (item.getItemHowOftenIsAddedToCart() * item.getItemPrice());
            } else {
                subTotal = subTotal + (item.getItemHowOftenIsAddedToCart() * item.getItemRegularPrice());
            }
        }
        subTotalTV.setText(String.valueOf(subTotal));
        discountTV.setText("0%");
        double taxAndCharges = (subTotal * 5) / 100;
        double total = subTotal + taxAndCharges;
        taxesAndChargesTV.setText(String.valueOf(taxAndCharges));
        grandTotalTV.setText(String.format("%.2f", total));
    }
}
