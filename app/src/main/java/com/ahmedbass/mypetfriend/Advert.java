package com.ahmedbass.mypetfriend;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.Serializable;

class Advert implements Serializable {

    private int dealId;
    private String sellerUserName;
    private String title;
    private double price;
    private long date; //in milli seconds
    private Location location;
    private String details;
    private Bitmap[] photos = new Bitmap[5];
    private int category; //1=animals, 2=tools
    private int petType; //1=cats, 2=dogs, 3=horses, 4=rabbits, 5=birds, 6=fish;
    private String petBreed; //each pet has different breeds obv.
    private int AgeInMonth;
    private boolean isMicroChipped;
    private boolean isNeutered;
    private boolean isVaccinated;
    private boolean isSold;

    private String loc;//TODO remove this later

    public Advert(int dealId, String sellerUserName, String title, double price, long date, String details, int category, int petType, String petBreed, int ageInMonth, boolean isMicroChipped, boolean isNeutered, boolean isVaccinated, String loc) {
        this.dealId = dealId;
        this.sellerUserName = sellerUserName;
        this.title = title;
        this.price = price;
        this.date = date;
        this.details = details;
        this.category = category;
        this.petType = petType;
        this.petBreed = petBreed;
        AgeInMonth = ageInMonth;
        this.isMicroChipped = isMicroChipped;
        this.isNeutered = isNeutered;
        this.isVaccinated = isVaccinated;
        this.loc = loc;
    }

    public int getDealId() {
        return dealId;
    }

    public String getSellerUserName() {
        return sellerUserName;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public long getDate() {
        return date;
    }

    public Location getLocation() {
        return location;
    }

    public String getDetails() {
        return details;
    }

    public int getCategory() {
        return category;
    }

    public int getPetType() {
        return petType;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public int getAgeInMonth() {
        return AgeInMonth;
    }

    public boolean isMicroChipped() {
        return isMicroChipped;
    }

    public boolean isNeutered() {
        return isNeutered;
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public Bitmap[] getPhotos() {
        return photos;
    }

    public String getLoc() {
        return loc;
    }
}
