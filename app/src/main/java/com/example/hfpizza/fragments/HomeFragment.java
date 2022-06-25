package com.example.hfpizza.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.hfpizza.R;
import com.example.hfpizza.adapters.BestSellersRecyclerViewAdapter;
import com.example.hfpizza.adapters.MenuAdapter;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Category;
import com.example.hfpizza.model.Item;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View fragmentHome;
    private RecyclerView exploreMenuRecyclerView, bestSellersRecyclerView;
    private MenuAdapter menuAdapter;
    private BestSellersRecyclerViewAdapter bestSellersRecyclerViewAdapter;

    private DatabaseHandler databaseHandler;
    private ArrayList<Item> bestSellers;
    private ArrayList<Category> categories;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHome = inflater.inflate(R.layout.fragment_home, container, false);

        fieldsInitialization();

        return fragmentHome;
    }

    private void fieldsInitialization() {
        databaseHandler = new DatabaseHandler(getContext());
        bestSellersRecyclerView = fragmentHome.findViewById(R.id.homeFragmentBestSellersRecyclerViewId);
        exploreMenuRecyclerView = fragmentHome.findViewById(R.id.homeFragmentExploreMenuRecyclerViewId);
        bestSellersRecyclerView.setHasFixedSize(true);
        exploreMenuRecyclerView.setHasFixedSize(true);
        bestSellersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        exploreMenuRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
    }

    private void refreshRecyclerView() {
        bestSellers = databaseHandler.getBestsellersItem();
        categories = databaseHandler.getAllCategories();
        bestSellersRecyclerViewAdapter = new BestSellersRecyclerViewAdapter(getContext(), bestSellers);
        menuAdapter = new MenuAdapter(getContext(), categories);
        bestSellersRecyclerView.setAdapter(bestSellersRecyclerViewAdapter);
        exploreMenuRecyclerView.setAdapter(menuAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshRecyclerView();
    }
}