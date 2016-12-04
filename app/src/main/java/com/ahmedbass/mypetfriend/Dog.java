package com.ahmedbass.mypetfriend;

public class Dog extends Pet {

    //basic information
    private String breedGroup;
    private int size;
    private int minWeight;
    private int maxWeight;
    private int minLifeExpectancy;
    private int maxLifeExpectancy;
    private int feedingAmountPerDay;
    private int mealsPerDay;

    //breed characteristics
    private int adaptability; //calculate average of following attributes
    private int apartmentLivingAdaptabilityLevel;
    private int goodForNoviceOwnersLevel;
    private int sensitivityLevel;
    private int beingAloneToleranceLevel;
    private int coldWeatherToleranceLevel;
    private int hotWeatherToleranceLevel;

    private int friendliness; //calculate average of following attributes
    private int familyAffectionateLevel;
    private int kidFriendlyLevel;
    private int petFriendlyLevel;
    private int strangerFriendlyLevel;

    private int maintenance; //calculate average of following attributes
    private int amountOfSheddingLevel;
    private int easeOfGroomingLevel;
    private int droolingPotentialLevel;
    private int weightGainPotentialLevel;
    private int generalHealthLevel;

    private int trainability; //calculate average of following attributes
    private int easeOfTrainingLevel;
    private int IntelligenceLevel;
    private int barkingLevel;
    private int mouthinessPotentialLevel;
    private int preyDriveLevel;
    private int wanderlustPotentialLevel;

    private int exercising; //calculate average of following attributes
    private int energyLevel;
    private int exerciseNeedsLevel;
    private int playfulnessPotentialLevel;

}
