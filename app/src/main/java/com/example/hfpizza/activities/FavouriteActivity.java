package com.example.hfpizza.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.adapters.ExploreMenuItemAdapter;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Item;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ExploreMenuItemAdapter exploreMenuItemAdapter;
    private ArrayList<Item> favouriteItems;
    private DatabaseHandler databaseHandler;
    private LinearLayout emptyFavouriteLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        fieldsInitialization();

        populateRecyclerView();
    }

    private void fieldsInitialization() {
        emptyFavouriteLinearLayout = findViewById(R.id.favouriteActivityEmptyFavouriteMainLinearLayoutId);
        recyclerView = findViewById(R.id.favouriteActivityRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        databaseHandler = new DatabaseHandler(this);
    }

    private void populateRecyclerView() {
        favouriteItems = databaseHandler.getFavouriteItems();
        exploreMenuItemAdapter = new ExploreMenuItemAdapter(this, favouriteItems,
                emptyFavouriteLinearLayout, FavouriteActivity.class.getName());
        recyclerView.setAdapter(exploreMenuItemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (databaseHandler.totalFavouriteItemCount() > 0) {
            emptyFavouriteLinearLayout.setVisibility(View.GONE);
        }else {
            emptyFavouriteLinearLayout.setVisibility(View.VISIBLE);
        }
    }
}