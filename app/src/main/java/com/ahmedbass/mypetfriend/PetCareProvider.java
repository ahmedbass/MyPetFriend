package com.ahmedbass.mypetfriend;

import java.util.ArrayList;

class PetCareProvider extends PetOwner {

    //only for pet care provider
    private String profileBio;
    private int yearsOfExperience;
    private int averageSalary;
    private int availability; //1=FULL-TIME, 2=PART-TIME
    private int distanceWillingToTravel;
    private ArrayList<String> servicesProvidedFor = new ArrayList<>();
    private ArrayList<String> servicesProvided = new ArrayList<>();
    //private ArrayList reviews; //this will be of type review? which contains the review message, date, rating, and sender?

    public PetCareProvider() {
    }

    //half-way registration constructor (can set remaining information later)
    public PetCareProvider(int userId, String firstName, String lastName, String email, String password,
                           long birthDate, String gender, String city, String country) {
        super(userId, firstName, lastName, email, password, birthDate, gender, city, country);
        setUserType(USER_TYPE_PET_CARE_PROVIDER);
    }

    public String getProfileBio() {
        return profileBio;
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

    public ArrayList<String> getServicesProvidedFor() {
        return servicesProvidedFor;
    }

    public ArrayList<String> getServicesProvided() {
        return servicesProvided;
    }

    public void setProfileBio(String profileBio) {
        this.profileBio = profileBio;
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

    public void setServicesProvidedFor(ArrayList<String> servicesProvidedFor) {
        this.servicesProvidedFor = servicesProvidedFor;
    }

    public void setServicesProvided(ArrayList<String> servicesProvided) {
        this.servicesProvided = servicesProvided;
    }
}
