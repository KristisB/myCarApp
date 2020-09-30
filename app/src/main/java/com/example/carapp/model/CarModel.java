package com.example.carapp.model;

public class CarModel {
    private int id;
    private String title;
    private String photoUrl;
    private int loyaltyPrize;
    private FareRate rate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public int getLoyaltyPrize() {
        return loyaltyPrize;
    }

    public void setLoyaltyPrize(int loyaltyPrize) {
        this.loyaltyPrize = loyaltyPrize;
    }

    public FareRate getRate() {
        return rate;
    }

    public void setRate(FareRate rate) {
        this.rate = rate;
    }
}
