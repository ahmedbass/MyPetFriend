package com.ahmedbass.mypetfriend;

public class Dog extends Pet {

    //dog size
    final static int SIZE_TOY = 1;
    final static int SIZE_SMALL = 2;
    final static int SIZE_MEDIUM = 3;
    final static int SIZE_LARGE = 4;
    final static int SIZE_GIANT = 5;

    //Group of Breed
    final static int GROUP_COMPANION = 1;
    final static int GROUP_HERDING = 2;
    final static int GROUP_HOUND = 3;
    final static int GROUP_SPORTING = 4;
    final static int GROUP_TERRIER = 5;
    final static int GROUP_WORKING = 6;

    //levels of characteristics
    final static int LEVEL_LOW = 1;
    final static int LEVEL_MEDIUM = 2;
    final static int LEVEL_HIGH = 3;

    //basic information
    private int breedGroup;
    private int size;
    private int minWeight;
    private int maxWeight;
    private int minLifeExpectancy;
    private int maxLifeExpectancy;
    private int feedingAmountPerDay;

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

    //schedule information
    private int mealsPerMonth;

}
