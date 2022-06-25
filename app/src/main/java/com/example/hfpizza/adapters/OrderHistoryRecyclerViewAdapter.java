package com.example.hfpizza.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.model.Order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class OrderHistoryRecyclerViewAdapter extends RecyclerView.Adapter<OrderHistoryRecyclerViewAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Order> orders;
    private LinearLayout emptyFavouriteLinearLayout;

    public OrderHistoryRecyclerViewAdapter(Context context, ArrayList<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customOrderRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order_history_row, parent, false);
        return new ViewHolder(customOrderRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.orderNumberTV.setText(String.valueOf(order.getOrderId()));
        holder.orderNamesTV.setText(order.getOrderNames());
        holder.orderAmount.setText(String.valueOf(order.getOrderAmount()));

        String[] itemsCount = order.getOrderNames().split(", ");
        holder.orderItemsCountTV.setText(String.valueOf(itemsCount.length));

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMM/yyyy");
        String date = simpleDateFormat.format(new Date(order.getOrderDate()));
        holder.orderDate.setText(date);

        String paymentModeString = order.getPaymentMode();
        if (paymentModeString.equals("Cash")) {
            holder.orderPaymentModeImageView.setImageResource(R.drawable.cash);
        } else if (paymentModeString.equals("Paytm")) {
            holder.orderPaymentModeImageView.setImageResource(R.drawable.paytm);
        } else if (paymentModeString.equals("Debit Credit")) {
            holder.orderPaymentModeImageView.setImageResource(R.drawable.debit_card);
        } else {
            holder.orderPaymentModeImageView.setImageResource(R.drawable.credit_card);
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView orderNumberTV, orderItemsCountTV, orderNamesTV, orderDate, orderAmount;
        private ImageView orderPaymentModeImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderNumberTV = itemView.findViewById(R.id.customOrderHistoryRowOrderNumberTextViewId);
            orderItemsCountTV = itemView.findViewById(R.id.customOrderHistoryRowOrderCountNumberTextViewId);
            orderNamesTV = itemView.findViewById(R.id.customOrderHistoryRowOrderNamesTextViewId);
            orderDate = itemView.findViewById(R.id.customOrderHistoryRowOrderDateTextViewId);
            orderAmount = itemView.findViewById(R.id.customOrderHistoryRowOrderAmountTextViewId);
            orderPaymentModeImageView = itemView.findViewById(R.id.customOrderHistoryRowPaymentModeImageViewId);
        }
    }
}
