package com.ahmedbass.mypetfriend;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class PetOwner implements Serializable {

    final static int USER_TYPE_PET_OWNER = 1;
    final static int USER_TYPE_PET_CARE_PROVIDER = 2;
    final static int USER_TYPE_ADMIN = 3;
    final static String GENDER_MALE = "Male";
    final static String GENDER_FEMALE = "Female";

    private int userId;
    private long createDate;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long birthDate; //in milliseconds
    private String gender;
    private String city;
    private String country;
    private double latitudeLocation;
    private double longitudeLocation;
    private String phoneNumber;
    private boolean isHidePhone;
    private Bitmap profilePicture;
    private int userType;

    private ArrayList<Pet> myPets = new ArrayList<>();
    private ArrayList<Advert> myAdverts = new ArrayList<>();
    private ArrayList<Advert> myFavoriteAdverts = new ArrayList<>();
    private ArrayList<Advert> myFavoritePetCareProviders = new ArrayList<>();

    public PetOwner() {}

    //half-way registration constructor (can set remaining information later e.g. phoneNumber, profilePicture)
    public PetOwner(int userId, String firstName, String lastName, String email, String password,
                    long birthDate, String gender, String city, String country) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;
        this.country = country;
        createDate = System.currentTimeMillis();
        setUserType(USER_TYPE_PET_OWNER);
    }

    public PetOwner(int userId, String firstName, String lastName, String email, String password,
                    long birthDate, String gender, String city, String country, String phoneNumber,
                    boolean isHidePhone, Bitmap profilePicture, ArrayList<Pet> myPets, ArrayList<Advert> myAdverts) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.isHidePhone = isHidePhone;
        this.profilePicture = profilePicture;
        this.myPets = myPets;
        this.myAdverts = myAdverts;

        createDate = System.currentTimeMillis();
        setUserType(USER_TYPE_PET_OWNER);
    }

    public int getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public double getLatitudeLocation() {
        return latitudeLocation;
    }

    public double getLongitudeLocation() {
        return longitudeLocation;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isHidePhone() {
        return isHidePhone;
    }

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public long getCreateDate() {
        return createDate;
    }

    public int getUserType() {
        return userType;
    }

    public ArrayList<Pet> getMyPets() {
        return myPets;
    }

    public ArrayList<Advert> getMyAdverts() {
        return myAdverts;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLatitudeLocation(double latitudeLocation) {
        this.latitudeLocation = latitudeLocation;
    }

    public void setLongitudeLocation(double longitudeLocation) {
        this.longitudeLocation = longitudeLocation;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setHidePhone(boolean hidePhone) {
        isHidePhone = hidePhone;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    void setUserType(int userType) {
        this.userType = userType;
    }

    public void addPet(Pet newPet) {
        this.myPets.add(newPet);
    }

    public void removePet(int petIndex) {
        this.myPets.remove(petIndex);
    }

    public void addAdvert(Advert advert) {
        this.myAdverts.add(advert);
    }

    public void removeAdvert(int advertIndex) {
        this.myAdverts.remove(advertIndex);
    }
}

