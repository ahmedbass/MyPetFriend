package com.ahmedbass.mypetfriend;

class PetCareProvider extends PetOwner {

    //only for pet care provider
    private String profileDescription;
    private String availability; //FULL-TIME, PART-TIME
    private String yearsOfExperience;
    private String averageRatePerHour;
    private String servicesProvidedFor;
    private String servicesProvided;

    public PetCareProvider() {
    }

    public PetCareProvider(int userId, long createDate, String userType, String firstName, String lastName, String email, String password,
                           long birthDate, String gender, String country, String city, String phone, String profilePhoto,
                           String profileDescription, String availability, String yearsOfExperience, String averageRatePerHour,
                           String servicesProvidedFor, String servicesProvided, double latitude, double longitude) {
        super(userId, createDate, userType, firstName, lastName, email, password, birthDate, gender, country, city, phone, profilePhoto, latitude, longitude);
        this.profileDescription = profileDescription;
        this.availability = availability;
        this.yearsOfExperience = yearsOfExperience;
        this.averageRatePerHour = averageRatePerHour;
        this.servicesProvidedFor = servicesProvidedFor;
        this.servicesProvided = servicesProvided;
    }

    public String getProfileDescription() {
        return profileDescription;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public String getAverageRatePerHour() {
        return averageRatePerHour;
    }

    public String getAvailability() {
        return availability;
    }

    public String getServicesProvidedFor() {
        return servicesProvidedFor;
    }

    public String getServicesProvided() {
        return servicesProvided;
    }

    public void setProfileDescription(String profileDescription) {
        this.profileDescription = profileDescription;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public void setAverageRatePerHour(String averageRatePerHour) {
        this.averageRatePerHour = averageRatePerHour;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setServicesProvidedFor(String servicesProvidedFor) {
        this.servicesProvidedFor = servicesProvidedFor;
    }

    public void setServicesProvided(String servicesProvided) {
        this.servicesProvided = servicesProvided;
    }
}
