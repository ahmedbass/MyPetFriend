package com.ahmedbass.mypetfriend;


import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

class Pet implements Serializable{

    private int petId;
    private ArrayList<Bitmap> allPhotos = new ArrayList<>();
    private String name;
    private long birthDate; //in milliseconds
    private int gender; //0=unknown, 1=male, 2=female
    private int weight;
    private String kind;
    private String breed;
    private String microchipNumber;
    private boolean isNeutered;
    private boolean isVaccinated;
    private String[] vaccines; //TODO maybe change to type vaccines after gathering information

    Pet(String name, long birthDate, int gender, int weight, String kind, String breed) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.kind = kind;
        this.breed = breed;
    }

    Pet(String name, long birthDate, int gender, int weight, String kind, String breed, Bitmap photo) {
        this.allPhotos.add(photo);
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.kind = kind;
        this.breed = breed;
    }

    public void addPhoto(Bitmap photo){
        this.allPhotos.add(photo);
    }

    public int getPetId() {
        return petId;
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

    public int getWeight() {
        return weight;
    }

    public String getKind() {
        return kind;
    }

    public String getBreed() {
        return breed;
    }

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public boolean isNeutered() {
        return isNeutered;
    }

    public boolean isVaccinated() {
        return isVaccinated;
    }

    public String[] getVaccines() {
        return vaccines;
    }
}
