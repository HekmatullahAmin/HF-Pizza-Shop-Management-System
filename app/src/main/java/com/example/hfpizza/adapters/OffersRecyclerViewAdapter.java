package com.example.hfpizza.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hfpizza.R;
import com.example.hfpizza.data.DatabaseHandler;
import com.example.hfpizza.fragments.CartFragment;
import com.example.hfpizza.model.Offer;
import com.example.hfpizza.utils.Constants;

import java.util.ArrayList;

public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Offer> offerArrayList;
    private DatabaseHandler databaseHandler;
    private String fromClassOrFragment;

    public OffersRecyclerViewAdapter(Context context, ArrayList<Offer> offerArrayList, String fromClassOrFragment) {
        this.context = context;
        this.offerArrayList = offerArrayList;
        this.fromClassOrFragment = fromClassOrFragment;
        databaseHandler = new DatabaseHandler(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View customRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_offer_row, parent, false);
        return new ViewHolder(customRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Offer offer = offerArrayList.get(position);
        holder.offerNameTV.setText(offer.getOfferName());
        holder.offerDescriptionTV.setText(offer.getOfferDescription());

        if (fromClassOrFragment.equals(Constants.CART_FRAGMENT_NAME)) {
            holder.deleteOrSelectOfferButton.setText("Select");
        }else {
            holder.deleteOrSelectOfferButton.setText("Delete");
        }
    }

    @Override
    public int getItemCount() {
        return offerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView offerNameTV, offerDescriptionTV;
        private Button deleteOrSelectOfferButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            offerNameTV = itemView.findViewById(R.id.customOfferRowNameTextViewId);
            offerDescriptionTV = itemView.findViewById(R.id.customOfferRowDescriptionTextViewId);
            deleteOrSelectOfferButton = itemView.findViewById(R.id.customOfferRowDeleteButtonId);

            deleteOrSelectOfferButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Offer offer = offerArrayList.get(position);
                    if (fromClassOrFragment.equals(Constants.MAIN_ACTIVITY_NAME)) {
                        databaseHandler.deleteOffer(offer.getOfferId());
                        offerArrayList.remove(position);
                        notifyItemRemoved(position);
                    }else {
//                        set offer to 1 for it is selected
                        offer.setOfferState(1);
                        databaseHandler.updateOffer(offer);
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        activity.finish();
//                        CartFragment cartFragment = new CartFragment();
//                        Bundle myBundle = new Bundle();
//                        myBundle.putSerializable("KEY_OFFER", offer);
//                        cartFragment.setArguments(myBundle);
//                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.mainActivityFrameLayoutId, cartFragment).commit();
////                        Fragment cartFragment = new CartFragment();
////                        activity.getSupportFragmentManager().beginTransaction()
////                                .replace(R.id.mainActivityDrawerLayoutId, cartFragment).addToBackStack(null).commit();
                    }
                }
            });
        }
    }
}
