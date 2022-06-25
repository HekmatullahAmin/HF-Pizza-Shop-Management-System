package com.example.hfpizza.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.adapters.OrderHistoryRecyclerViewAdapter;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Order;

import java.util.ArrayList;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OrderHistoryRecyclerViewAdapter orderHistoryRecyclerViewAdapter;
    private DatabaseHandler databaseHandler;
    private LinearLayout emptyOrderLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        fieldsInitialization();

        populateRecyclerView();
    }

    private void fieldsInitialization() {
        emptyOrderLinearLayout = findViewById(R.id.orderHistoryActivityEmptyFavouriteMainLinearLayoutId);
        databaseHandler = new DatabaseHandler(this);
        recyclerView = findViewById(R.id.orderHistoryActivityRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
    }

    private void populateRecyclerView() {
        ArrayList<Order> orders = databaseHandler.getAllOrders();
        orderHistoryRecyclerViewAdapter = new OrderHistoryRecyclerViewAdapter(this, orders);
        recyclerView.setAdapter(orderHistoryRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (databaseHandler.totalOrderCount() > 0) {
            emptyOrderLinearLayout.setVisibility(View.GONE);
        } else {
            emptyOrderLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}