package com.ahmedbass.mypetfriend;


import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

class Pet implements Serializable{

    final static int GENDER_MALE=1;
    final static int GENDER_FEMALE=2;
    final static int TYPE_CAT=1;
    final static int TYPE_DOG=2;

    protected int petId;
    protected int ownerId;
    protected ArrayList<Bitmap> allPhotos = new ArrayList<>();
    protected String name;
    protected long birthDate; //in milliseconds
    protected int gender; //0=unknown, 1=male, 2=female
    protected int type; //1=cat, 2=dog
    protected String breed;
    protected int weight;
    protected String microchipNumber;
    protected boolean isNeutered;
    protected Vaccine[] vaccines;

    public Pet(){}

    public Pet(String name, long birthDate, int gender, int type, String breed, int weight) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.type = type;
        this.breed = breed;
        this.weight = weight;
    }

    public int getPetAgeInYear(){
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(birthDate);
        return Calendar.getInstance().get(Calendar.YEAR) - birth.get(Calendar.YEAR);
    }

    public int getPetAgeInMonth(){
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(birthDate);
        return Calendar.getInstance().get(Calendar.MONTH) - birth.get(Calendar.MONTH);
    }

    public int getPetId() {
        return petId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public ArrayList<Bitmap> getAllPhotos() {
        return allPhotos;
    }

    public String getName() {
        return name;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public int getGender() {
        return gender;
    }

    public int getType() {
        return type;
    }

    public String getBreed() {
        return breed;
    }

    public int getWeight() {
        return weight;
    }

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public boolean isNeutered() {
        return isNeutered;
    }

    public Vaccine[] getVaccines() {
        return vaccines;
    }
}
