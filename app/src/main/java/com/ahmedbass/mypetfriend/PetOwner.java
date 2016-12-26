package com.ahmedbass.mypetfriend;

import java.io.Serializable;
import java.util.ArrayList;

public class PetOwner implements Serializable {

    private int userId;
    private long createDate;
    private String userType;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long birthDate; //in milliseconds
    private String gender;
    private String country;
    private String city;
    private String phone;
    private String profilePhoto;
    private double latitude;
    private double longitude;

    private ArrayList<Pet> myPets = new ArrayList<>();
    private ArrayList<Advert> myAdverts = new ArrayList<>();
    private ArrayList<Advert> myFavoriteAdverts = new ArrayList<>();
    private ArrayList<Advert> myFavoritePetCareProviders = new ArrayList<>();

    public PetOwner() {}

    public PetOwner(int userId, long createDate, String userType, String firstName, String lastName,
                    String email, String password, long birthDate, String gender, String country, String city,
                    String phone, String profilePhoto, double latitude, double longitude) {
        this.userId = userId;
        this.createDate = createDate;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.profilePhoto = profilePhoto; //TODO see about this profilePhoto thing
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getPhone() {
        return phone;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String getUserType() {
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

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    void setUserType(String userType) {
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

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}

