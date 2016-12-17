package com.ahmedbass.mypetfriend;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

class Advert implements Serializable {

    //TODO see if this class needs modifications (specially with country, and other category than pets)

    final static String CATEGORY_PETS = "Pets";
    final static String CATEGORY_TOOLS = "Tools";
    final static String TYPE_SALE = "For Sale";
    final static String TYPE_ADOPTION = "For Adoption";
    final static String TYPE_BREEDING = "For Breeding";
    final static String TYPE_WANTED = "Wanted";

    private long advertId;
    private long sellerId;
    private long createDate; //in milliseconds
    private boolean isSold;
    private int viewCount;
    private String category;
    private String type;
    private String title;
    private double price;
    private String details;
    private String country;
    private String city;
    private String email;
    private String phone;
    private String petType;
    private String petBreed;
    private long petBirthDate;
    private String petGender;
    private boolean isPetMicroChipped;
    private boolean isNeutered;
    private boolean isPetVaccinated;
    private String photo;
    private ArrayList<Bitmap> photos = new ArrayList<>();

    public Advert(long advertId, long sellerId, long createDate, boolean isSold, int viewCount, String category, String type,
                  String title, double price, String details, String country, String city, String email, String phone,
                  String petType, String petBreed, long petBirthDate, String petGender,
                  boolean isPetMicroChipped, boolean isNeutered, boolean isPetVaccinated, String photo) {
        this.advertId = advertId;
        this.sellerId = sellerId;
        this.createDate = createDate;
        this.isSold = isSold;
        this.viewCount = viewCount;
        this.category = category;
        this.type = type;
        this.title = title;
        this.price = price;
        this.details = details;
        this.country = country;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.petType = petType;
        this.petBreed = petBreed;
        this.petBirthDate = petBirthDate;
        this.petGender = petGender;
        this.isPetMicroChipped = isPetMicroChipped;
        this.isNeutered = isNeutered;
        this.isPetVaccinated = isPetVaccinated;
        this.photo = photo;
    }

    public long getAdvertId() {
        return advertId;
    }

    public long getSellerId() {
        return sellerId;
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

    public String getPetGender() {
        return petGender;
    }

    public boolean isPetMicroChipped() {
        return isPetMicroChipped;
    }

    public boolean isNeutered() {
        return isNeutered;
    }

    public boolean isPetVaccinated() {
        return isPetVaccinated;
    }

    public boolean isSold() {
        return isSold;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getPetBirthDate() {
        return petBirthDate;
    }

    public void setPetBirthDate(long petBirthDate) {
        this.petBirthDate = petBirthDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void incrementViewCount(){
        this.viewCount++;
    }
}
