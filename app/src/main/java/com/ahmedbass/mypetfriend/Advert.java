package com.ahmedbass.mypetfriend;

import android.graphics.Bitmap;
import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

class Advert implements Serializable {

    //TODO see if this class needs modifications (specially with location, and other category than pets)

    final static String CATEGORY_PETS = "Pets";
    final static String CATEGORY_TOOLS = "Tools";
    final static String TYPE_SALE = "For Sale";
    final static String TYPE_ADOPTION = "For Adoption";
    final static String TYPE_BREEDING = "For Breeding";
    final static String TYPE_WANTED = "Wanted";

    private int advertId;
    private PetOwner seller;
    private long createDate; //in milliseconds
    private boolean isSold;
    private int viewCount;
    private String title;
    private double price;
    private String category;
    private String type;
    private String details;
    private Location location;
    private String city;
    private String country;
    private ArrayList<Bitmap> photos = new ArrayList<>();

    private String petType; //1=cats, 2=dogs, 3=horses, 4=rabbits, 5=birds, 6=fish;
    private String petBreed; //each pet has different breeds obv.
    private int petAgeInMonths;
    private String petGender;
    private boolean isMicroChipped;
    private boolean isNeutered;
    private boolean isVaccinated;

    public Advert(int advertId, PetOwner seller, String title, double price, String category,
                  String type, String details, ArrayList<Bitmap> photos, Location location, String city, String country) {
        this.advertId = advertId;
        this.seller = seller;
        this.title = title;
        this.price = price;
        this.createDate = System.currentTimeMillis();
        this.category = category;
        this.type = type;
        this.details = details;
        this.photos = photos;
        this.location = location;
        this.city = city;
        this.country = country;
    }

    public Advert(int advertId, PetOwner seller, String title, double price, long createDate, String category,
                  String type, String details, ArrayList<Bitmap> photos, Location location, String city,
                  String country, String petType, String petBreed, int petAgeInMonths, String petGender,
                  boolean isMicroChipped, boolean isNeutered, boolean isVaccinated, boolean isSold) {
        this.advertId = advertId;
        this.seller = seller;
        this.title = title;
        this.price = price;
        this.createDate = createDate;
        this.category = category;
        this.type = type;
        this.details = details;
        this.photos = photos;
        this.location = location;
        this.city = city;
        this.country = country;
        this.petType = petType;
        this.petBreed = petBreed;
        this.petAgeInMonths = petAgeInMonths;
        this.petGender = petGender;
        this.isMicroChipped = isMicroChipped;
        this.isNeutered = isNeutered;
        this.isVaccinated = isVaccinated;
        this.isSold = isSold;
    }

    public int getAdvertId() {
        return advertId;
    }

    public PetOwner getSeller() {
        return seller;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public String getDetails() {
        return details;
    }

    public ArrayList<Bitmap> getPhotos() {
        return photos;
    }

    public Location getLocation() {
        return location;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getPetType() {
        return petType;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public int getPetAgeInMonths() {
        return petAgeInMonths;
    }

    public String getPetGender() {
        return petGender;
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

    public boolean isSold() {
        return isSold;
    }
}
