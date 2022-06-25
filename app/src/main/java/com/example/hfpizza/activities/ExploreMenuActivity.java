package com.example.hfpizza.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.adapters.ExploreMenuCategoryAdapter;
import com.example.hfpizza.adapters.ExploreMenuItemAdapter;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Category;
import com.example.hfpizza.model.Item;
import com.example.hfpizza.utils.Constants;

import java.util.ArrayList;

public class ExploreMenuActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView menuCategoryRecyclerView, menuItemRecyclerView;

    private DatabaseHandler databaseHandler;
    private ArrayList<Category> categories;
    private ArrayList<Item> items;

    private ExploreMenuCategoryAdapter categoryAdapter;
    private ExploreMenuItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_menu);

        fieldsInitialization();

        populateItems();

        populateCategory();

    }

    private void fieldsInitialization() {
        toolbar = findViewById(R.id.exploreMenuActivityToolbarId);
        toolbar.setTitle("Explore Menu");
        setSupportActionBar(toolbar);
        menuCategoryRecyclerView = findViewById(R.id.exploreMenuActivityCategoriesRecyclerViewId);
        menuItemRecyclerView = findViewById(R.id.exploreMenuActivityItemsRecyclerViewId);
        menuCategoryRecyclerView.setHasFixedSize(true);
        menuCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        menuItemRecyclerView.setHasFixedSize(true);
        menuItemRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHandler = new DatabaseHandler(this);
    }

    private void populateCategory() {
        categories = databaseHandler.getAllCategories();
        categoryAdapter = new ExploreMenuCategoryAdapter(this, categories, itemAdapter);
        menuCategoryRecyclerView.setAdapter(categoryAdapter);
    }

    private void populateItems() {
//        Category category = (Category) getIntent().getSerializableExtra(Constants.SELECTED_CATEGORY_KEY);
        Bundle myBundle = getIntent().getExtras();
        int categoryId = myBundle.getInt(Constants.SELECTED_CATEGORY_ID);
        items = databaseHandler.getSpecificCategoryItems(categoryId);
        itemAdapter = new ExploreMenuItemAdapter(this, items,
                null, ExploreMenuActivity.class.getName());
        menuItemRecyclerView.setAdapter(itemAdapter);
    }
}