package com.ahmedbass.mypetfriend;

import java.util.ArrayList;

class PetCareProvider extends PetOwner {

    final static String AVAILABILITY_FULL_TIME = "FULL-TIME";
    final static String AVAILABILITY_PART_TIME = "PART-TIME";

    //only for pet care provider
    private String profileDescription;
    private int availability; //FULL-TIME, PART-TIME
    private int yearsOfExperience;
    private int averageRatePerHour;
    private ArrayList<String> servicesProvided = new ArrayList<>();
    private ArrayList<String> servicesProvidedFor = new ArrayList<>();

    public PetCareProvider() {
    }

    //half-way registration constructor (can set remaining information later)
    public PetCareProvider(int userId, String firstName, String lastName, String email, String password,
                           long birthDate, String gender, String city, String country) {
        super(userId, firstName, lastName, email, password, birthDate, gender, city, country);
        setUserType(USER_TYPE_PET_CARE_PROVIDER);
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public int getAverageRatePerHour() {
        return averageRatePerHour;
    }

    public int getAvailability() {
        return availability;
    }

    public ArrayList<String> getServicesProvidedFor() {
        return servicesProvidedFor;
    }

    public ArrayList<String> getServicesProvided() {
        return servicesProvided;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setAverageRatePerHour(int averageRatePerHour) {
        this.averageRatePerHour = averageRatePerHour;
    }

    public void setAvailability(int availability) {
        this.availability = availability;
    }

    public void setServicesProvidedFor(ArrayList<String> servicesProvidedFor) {
        this.servicesProvidedFor = servicesProvidedFor;
    }

    public void setServicesProvided(ArrayList<String> servicesProvided) {
        this.servicesProvided = servicesProvided;
    }
}
