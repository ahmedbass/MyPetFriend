package com.ahmedbass.mypetfriend;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

class PetCareProvider implements Serializable {

    private int petCareProviderId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private long birthDate; //in milliseconds
    private int gender; //1=male, 2=female
    private String city;
    private String country;
    private double latitudeLocation;
    private double longitudeLocation;

    private Bitmap profilePicture;
    private String profileTitle;
    private String profileBio;
    private String phoneNumber;
    private boolean isHidePhone;
    private int yearsOfExperience;
    private int averageSalary;
    private int availability; //1=FULL-TIME, 2=PART-TIME
    private int distanceWillingToTravel;
    private ArrayList servicesProvidedFor;
    private ArrayList servicesProvided;

    private ArrayList reviews; //this will be of type review? which contains the review message, currentDate, rating, and sender?


    PetCareProvider(){}

    //half-way registration constructor (can set remaining information later)
    public PetCareProvider(int petCareProviderId, String firstName, String lastName, String email, String password, long birthDate, int gender, String city, String country, double latitudeLocation, double longitudeLocation) {
        this.petCareProviderId = petCareProviderId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;
        this.country = country;
        this.latitudeLocation = latitudeLocation;
        this.longitudeLocation = longitudeLocation;
    }

    //complete registration constructor
    public PetCareProvider(int petCareProviderId, String firstName, String lastName, String email, String password, long birthDate, int gender, String city, String country, double latitudeLocation, double longitudeLocation, Bitmap profilePicture, String profileTitle, String profileBio, String phoneNumber, boolean isHidePhone, int yearsOfExperience, int averageSalary, int availability, int distanceWillingToTravel, ArrayList servicesProvidedFor, ArrayList servicesProvided) {
        this.petCareProviderId = petCareProviderId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.gender = gender;
        this.city = city;
        this.country = country;
        this.latitudeLocation = latitudeLocation;
        this.longitudeLocation = longitudeLocation;
        this.availability = availability;
        this.profilePicture = profilePicture;
        this.profileTitle = profileTitle;
        this.profileBio = profileBio;
        this.phoneNumber = phoneNumber;
        this.isHidePhone = isHidePhone;
        this.yearsOfExperience = yearsOfExperience;
        this.averageSalary = averageSalary;
        this.distanceWillingToTravel = distanceWillingToTravel;
        this.servicesProvidedFor = servicesProvidedFor;
        this.servicesProvided = servicesProvided;
    }

    public void setProfilePicture(Bitmap profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setProfileTitle(String profileTitle) {
        this.profileTitle = profileTitle;
    }

    public void setProfileBio(String profileBio) {
        this.profileBio = profileBio;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setHidePhone(boolean hidePhone) {
        isHidePhone = hidePhone;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setAverageSalary(int averageSalary) {
        this.averageSalary = averageSalary;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setDistanceWillingToTravel(int distanceWillingToTravel) {
        this.distanceWillingToTravel = distanceWillingToTravel;
    }

    public void setServicesProvidedFor(ArrayList servicesProvidedFor) {
        this.servicesProvidedFor = servicesProvidedFor;
    }

    public void setServicesProvided(ArrayList servicesProvided) {
        this.servicesProvided = servicesProvided;
    }

    public int getUserType() {
        //PetOwner userType = 1 , PetCareProvider userType = 2
        return 2;
    }

    public int getPetCareProviderId() {
        return petCareProviderId;
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

    public int getGender() {
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

    public Bitmap getProfilePicture() {
        return profilePicture;
    }

    public String getProfileTitle() {
        return profileTitle;
    }

    public String getProfileBio() {
        return profileBio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isHidePhone() {
        return isHidePhone;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public int getAverageSalary() {
        return averageSalary;
    }

    public int getAvailability() {
        return availability;
    }

    public int getDistanceWillingToTravel() {
        return distanceWillingToTravel;
    }

    public ArrayList getServicesProvidedFor() {
        return servicesProvidedFor;
    }

    public ArrayList getServicesProvided() {
        return servicesProvided;
    }
}
