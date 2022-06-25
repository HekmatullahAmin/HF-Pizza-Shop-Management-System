package com.example.hfpizza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.hfpizza.R;
import com.example.hfpizza.adapters.ViewPagerAdapter;
import com.example.hfpizza.utils.Constants;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;

    private String userType = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fieldsInitialization();

        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        if (userType != null) {
            if (userType.equals("M")) {
                Menu navigationMenu = navigationView.getMenu();
                navigationMenu.findItem(R.id.navigationDrawerMenuAddItemId).setVisible(false);
                navigationMenu.findItem(R.id.navigationDrawerMenuAddCategoryId).setVisible(false);
            }
        }
    }

    private void fieldsInitialization() {

        userType = getIntent().getExtras().getString(Constants.USER_TYPE_KEY);
        toolbar = findViewById(R.id.mainActivityToolbarId);
        drawerLayout = findViewById(R.id.mainActivityDrawerLayoutId);
        navigationView = findViewById(R.id.mainActivityNavigationViewId);
        tabLayout = findViewById(R.id.mainActivityTabLayoutId);
        viewPager2 = findViewById(R.id.mainActivityViewPager2Id);
        viewPagerAdapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(viewPagerAdapter);

        setSupportActionBar(toolbar);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position) {
                    case 0:
                        tab.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_home_24, null));
                        tab.setText("Home");
                        break;
                    case 1:
                        tab.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_shopping_cart_24, null));
                        tab.setText("Cart");
                        break;
                }
            }
        }).attach();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigationDrawerMenuAddCategoryId:
                Intent addCategoryIntent = new Intent(this, AddCategoryActivity.class);
                addCategoryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(addCategoryIntent);
                break;
            case R.id.navigationDrawerMenuAddItemId:
                Intent addItemIntent = new Intent(this, AddItemActivity.class);
                addItemIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(addItemIntent);
                break;
            case R.id.navigationDrawerMenuAddOfferId:
                Intent addOfferIntent = new Intent(this, DealsAndOffersActivity.class);
                addOfferIntent.putExtra(Constants.CLASS_NAME_KEY, Constants.MAIN_ACTIVITY_NAME);
                addOfferIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(addOfferIntent);
                break;
            case R.id.navigationDrawerMenuOrderHistoryId:
                Intent orderHistoryIntent = new Intent(this, OrderHistoryActivity.class);
                orderHistoryIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(orderHistoryIntent);
                break;
            case R.id.navigationDrawerMenuMyFavouritesId:
                Intent favouriteIntent = new Intent(this, FavouriteActivity.class);
                favouriteIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(favouriteIntent);
                break;
            case R.id.navigationDrawerMenuLogoutId:
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}