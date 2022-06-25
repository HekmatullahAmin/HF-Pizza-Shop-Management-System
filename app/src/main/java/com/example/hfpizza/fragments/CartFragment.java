package com.example.hfpizza.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.activities.DealsAndOffersActivity;
import com.example.hfpizza.activities.ExploreMenuActivity;
import com.example.hfpizza.adapters.CardItemsRecyclerViewAdapter;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.model.Category;
import com.example.hfpizza.model.Item;
import com.example.hfpizza.model.Offer;
import com.example.hfpizza.model.Order;
import com.example.hfpizza.utils.Constants;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private View fragmentCart;
    private ScrollView scrollView;
    private LinearLayout emptyCartLayout;

    private TextView subTotalTV, discountTV, taxesAndChargesTV, grandTotalTV;
    private Button selectPaymentModeButton, exploreMenuButton;

    private RecyclerView addedItemsToCartRecyclerView;
    private CardItemsRecyclerViewAdapter cardItemsRecyclerViewAdapter;

    private DatabaseHandler databaseHandler;
    private ArrayList<Item> cartItems;

    private CardView applyCouponCV, appliedOfferCV;
    private TextView appliedOfferNameTV, appliedOfferAmountTV;
    private ImageView removeAppliedOfferIV;
    private Offer offer;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCart = inflater.inflate(R.layout.fragment_cart, container, false);

        fieldsInitialization();

        applyCouponCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dealsAndOffersActivityIntent = new Intent(getContext(), DealsAndOffersActivity.class);
                dealsAndOffersActivityIntent.putExtra(Constants.CLASS_NAME_KEY, Constants.CART_FRAGMENT_NAME);
                startActivity(dealsAndOffersActivityIntent);
            }
        });

        removeAppliedOfferIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                offer.setOfferState(0);
                databaseHandler.updateOffer(offer);
                applyCouponCV.setVisibility(View.VISIBLE);
                appliedOfferCV.setVisibility(View.GONE);
                appliedOfferAmountTV.setText(String.valueOf(0));
                calculateTotal(cartItems);
            }
        });

        selectPaymentModeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSelectPaymentModeBottomSheetDialog();
            }
        });

        exploreMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Category firstCategory = databaseHandler.getFirstCategory();
                Intent exploreMenuActivityIntent = new Intent(getContext(), ExploreMenuActivity.class);
//                try to pass to intent as much less data as possible
//                Bundle myCategoryBundle = new Bundle();
//                myCategoryBundle.putSerializable(Constants.SELECTED_CATEGORY_KEY, firstCategory);
//                exploreMenuActivityIntent.putExtras(myCategoryBundle);
                exploreMenuActivityIntent.putExtra(Constants.SELECTED_CATEGORY_ID, firstCategory.getCategoryId());
                exploreMenuActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(exploreMenuActivityIntent);
            }
        });

        return fragmentCart;
    }

    private void fieldsInitialization() {
        databaseHandler = new DatabaseHandler(getContext());
        applyCouponCV = fragmentCart.findViewById(R.id.cartFragmentApplyCouponCardViewId);
        appliedOfferCV = fragmentCart.findViewById(R.id.cartFragmentAppliedOfferCardViewId);
        appliedOfferNameTV = fragmentCart.findViewById(R.id.cartFragmentAppliedOfferNameTextViewId);
        appliedOfferAmountTV = fragmentCart.findViewById(R.id.cartFragmentAppliedOfferYouSavedAmountTextViewId);
        removeAppliedOfferIV = fragmentCart.findViewById(R.id.cartFragmentAppliedOfferDeleteImageViewId);

        subTotalTV = fragmentCart.findViewById(R.id.cartFragmentSubTotalAmountTextViewId);
        discountTV = fragmentCart.findViewById(R.id.cartFragmentDiscountAmountTextViewId);
        taxesAndChargesTV = fragmentCart.findViewById(R.id.cartFragmentTaxesAndChargesAmountTextViewId);
        grandTotalTV = fragmentCart.findViewById(R.id.cartFragmentGrandTotalAmountTextViewId);
        selectPaymentModeButton = fragmentCart.findViewById(R.id.cartFragmentSelectPaymentModeButtonId);
        exploreMenuButton = fragmentCart.findViewById(R.id.cartFragmentExploreMenuButtonId);

        scrollView = fragmentCart.findViewById(R.id.cartFragmentScrollViewId);
        emptyCartLayout = fragmentCart.findViewById(R.id.cartFragmentEmptyBagMainLinearLayoutId);
        addedItemsToCartRecyclerView = fragmentCart.findViewById(R.id.cartFragmentAddedItemsToCardRecyclerViewId);
        addedItemsToCartRecyclerView.setHasFixedSize(true);
        addedItemsToCartRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public void refreshRecyclerView() {
        cartItems = databaseHandler.getAllCardsItems();
        cardItemsRecyclerViewAdapter = new CardItemsRecyclerViewAdapter(getContext(), cartItems,
                subTotalTV, discountTV, taxesAndChargesTV, grandTotalTV, emptyCartLayout, scrollView, selectPaymentModeButton);
        addedItemsToCartRecyclerView.setAdapter(cardItemsRecyclerViewAdapter);
        calculateTotal(cartItems);
    }

    private void calculateTotal(ArrayList<Item> addedItemsToCart) {
        double subTotal = 0;
        for (Item item : addedItemsToCart) {
            if (item.getItemPrice() != 0) {
                subTotal = subTotal + (item.getItemHowOftenIsAddedToCart() * item.getItemPrice());
            } else {
                subTotal = subTotal + (item.getItemHowOftenIsAddedToCart() * item.getItemRegularPrice());
            }
        }
        subTotalTV.setText(String.valueOf(subTotal));
        discountTV.setText(appliedOfferAmountTV.getText().toString());
        double taxAndCharges = (subTotal * 5) / 100;
        double total = subTotal + taxAndCharges - Double.parseDouble(discountTV.getText().toString());
        taxesAndChargesTV.setText(String.valueOf(taxAndCharges));
        grandTotalTV.setText(String.format("%.2f", total));
    }

    private void openSelectPaymentModeBottomSheetDialog() {
        View customBottomSheetDialogView = LayoutInflater.from(getContext()).inflate(R.layout.custom_payment_mode_bottom_sheet_dialog, null);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(customBottomSheetDialogView);

        Button payButton = customBottomSheetDialogView.findViewById(R.id.customPaymentModeBottomSheetDialogPayButtonId);
        RadioGroup paymentModeRadioGroup = customBottomSheetDialogView.findViewById(R.id.customPaymentModeBottomSheetDialogRadioGroupId);

        final RadioButton[] selectedPaymentModeRadioButton = new RadioButton[1];
        paymentModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedPaymentModeRadioButton[0] = customBottomSheetDialogView.findViewById(checkedId);
            }
        });

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentModeString = selectedPaymentModeRadioButton[0].getText().toString();
                addOrderToDatabase(paymentModeString, bottomSheetDialog);
            }
        });

        bottomSheetDialog.show();
    }

    private void addOrderToDatabase(String paymentMode, BottomSheetDialog bottomSheetDialog) {
        Order orderToSave = new Order();
        double orderAmount = Double.parseDouble(String.format("%.2f", Double.parseDouble(grandTotalTV.getText().toString())));
        String cartItemsString = "";
        for (int i = 0; i < cartItems.size(); i++) {
            if (i == (cartItems.size() - 1)) {
                cartItemsString = cartItemsString + cartItems.get(i).getItemName();
                break;
            } else {
                cartItemsString = cartItemsString + (cartItems.get(i).getItemName() + ", ");
            }
        }
        orderToSave.setOrderAmount(orderAmount);
        orderToSave.setOrderNames(cartItemsString);
        orderToSave.setPaymentMode(paymentMode);
        databaseHandler.addOrder(orderToSave);
        for (Item item : cartItems) {
            item.setItemHowOftenIsAddedToCart(0);
            int numberOfTimesOrdered = item.getItemNumberOfTimesOrdered();
            numberOfTimesOrdered++;
            item.setItemNumberOfTimesOrdered(numberOfTimesOrdered);
            databaseHandler.updateItem(item);
        }

        viewsVisibilityCheck();
//        refreshRecyclerView();
        bottomSheetDialog.dismiss();
    }

    public void viewsVisibilityCheck() {
        if (databaseHandler.totalItemCount() == 0) {
            selectPaymentModeButton.setVisibility(View.GONE);
            scrollView.setVisibility(View.GONE);
            emptyCartLayout.setVisibility(View.VISIBLE);
        } else {
            selectPaymentModeButton.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            emptyCartLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        int totalItemCount = databaseHandler.totalItemCount();
        viewsVisibilityCheck();
        if (totalItemCount > 0) {
            offer = databaseHandler.getSelectedOffer();
            if (offer != null && offer.getOfferState() == 1) {
                applyCouponCV.setVisibility(View.GONE);
                appliedOfferCV.setVisibility(View.VISIBLE);
                appliedOfferNameTV.setText(offer.getOfferName());
                appliedOfferAmountTV.setText(String.valueOf(offer.getOfferAmount()));

            } else {
                applyCouponCV.setVisibility(View.VISIBLE);
                appliedOfferCV.setVisibility(View.GONE);
            }

            refreshRecyclerView();
        }
    }
}