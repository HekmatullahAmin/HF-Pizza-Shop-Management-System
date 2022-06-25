package com.example.hfpizza.model;

import java.io.Serializable;

public class Offer implements Serializable {
    private int offerId, offerState;
    private String offerName, offerDescription;
    private double offerAmount;

    public Offer() {
    }

    public Offer(int offerId, int offerState, String offerName, String offerDescription, double offerAmount) {
        this.offerId = offerId;
        this.offerState = offerState;
        this.offerName = offerName;
        this.offerDescription = offerDescription;
        this.offerAmount = offerAmount;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public double getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(double offerAmount) {
        this.offerAmount = offerAmount;
    }

    public int getOfferState() {
        return offerState;
    }

    public void setOfferState(int offerState) {
        this.offerState = offerState;
    }
}
