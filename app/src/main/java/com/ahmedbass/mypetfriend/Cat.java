package com.ahmedbass.mypetfriend;

public class Cat extends Pet {

    //levels of characteristics
    final static int LEVEL_LOW = 1;
    final static int LEVEL_MEDIUM = 2;
    final static int LEVEL_HIGH = 3;

    //basic information
    private int size;
    private int minWeight;
    private int maxWeight;
    private int minLifeExpectancy;
    private int maxLifeExpectancy;
    private int feedingAmountPerDay;
    private int mealsPerDay;

    //breed characteristics
    private int familyAffectionateLevel;
    private int kidFriendlyLevel;
    private int petFriendlyLevel;
    private int strangerFriendlyLevel;
    private int amountOfSheddingLevel;
    private int easeOfGroomingLevel;
    private int generalHealthLevel;

}
