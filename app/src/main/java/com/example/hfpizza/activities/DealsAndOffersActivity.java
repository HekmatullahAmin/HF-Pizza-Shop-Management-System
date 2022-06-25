package com.example.hfpizza.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hfpizza.R;
import com.example.hfpizza.adapters.CardItemsRecyclerViewAdapter;
import com.example.hfpizza.adapters.OffersRecyclerViewAdapter;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Offer;
import com.example.hfpizza.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DealsAndOffersActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private OffersRecyclerViewAdapter offersRecyclerViewAdapter;
    private FloatingActionButton fab;
    private BottomSheetDialog bottomSheetDialog;
    private DatabaseHandler databaseHandler;
    private ArrayList<Offer> offerArrayList;
    private String fromFragmentOrActivityString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deals_and_offers);

        fieldsInitialization();

        if (fromFragmentOrActivityString.equals(Constants.CART_FRAGMENT_NAME)) {
            fab.setVisibility(View.GONE);
        }else {
            fab.setVisibility(View.VISIBLE);
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View customBottomSheetDialogView = LayoutInflater.from(DealsAndOffersActivity.this)
                        .inflate(R.layout.custom_add_offer_bottom_sheet_dialog, null);
                bottomSheetDialog = new BottomSheetDialog(DealsAndOffersActivity.this);
                bottomSheetDialog.setContentView(customBottomSheetDialogView);

                EditText offerNameET = customBottomSheetDialogView.findViewById(R.id.customAddOfferBottomSheetDialogOfferNameEditTextId);
                EditText offerDescriptionET = customBottomSheetDialogView.findViewById(R.id.customAddOfferBottomSheetDialogOfferDescriptionEditTextId);
                EditText offerAmountET = customBottomSheetDialogView.findViewById(R.id.customAddOfferBottomSheetDialogOfferAmountEditTextId);
                Button saveOffer = customBottomSheetDialogView.findViewById(R.id.customAddOfferBottomSheetDialogSaveOfferButtonId);

                saveOffer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String offerNameString = offerNameET.getText().toString().trim();
                        String offerDescriptionString = offerDescriptionET.getText().toString().trim();
                        double offerAmount = Double.parseDouble(offerAmountET.getText().toString().trim());

                        if (!TextUtils.isEmpty(offerNameString) && !TextUtils.isEmpty(offerDescriptionString)) {
                            Offer offer = new Offer();
                            offer.setOfferName(offerNameString);
                            offer.setOfferDescription(offerDescriptionString);
                            offer.setOfferAmount(offerAmount);
                            databaseHandler.addOffer(offer);
                            bottomSheetDialog.dismiss();
                            refreshRecyclerView();

                        }else {
                            Toast.makeText(DealsAndOffersActivity.this, "Please fill all fields",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                bottomSheetDialog.show();
            }
        });
    }

    private void fieldsInitialization() {
        databaseHandler = new DatabaseHandler(this);
        fromFragmentOrActivityString = getIntent().getExtras().getString(Constants.CLASS_NAME_KEY);

        fab = findViewById(R.id.dealsAndOffersActivityFABId);
        recyclerView = findViewById(R.id.dealsAndOffersActivityRecyclerViewId);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void refreshRecyclerView() {
        offerArrayList = databaseHandler.getAllOffers();
        offersRecyclerViewAdapter = new OffersRecyclerViewAdapter(this, offerArrayList, fromFragmentOrActivityString);
        recyclerView.setAdapter(offersRecyclerViewAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (databaseHandler.totalOfferCount() > 0) {
            refreshRecyclerView();
        }
    }
}