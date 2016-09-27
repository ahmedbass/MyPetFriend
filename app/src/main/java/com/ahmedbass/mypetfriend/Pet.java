package com.ahmedbass.mypetfriend;


import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

class Pet implements Serializable{

    private ArrayList<Bitmap> allPhotos = new ArrayList<>();
    private String name;
    private long birthDate; //in milliseconds
    private int gender; //0=unknown, 1=male, 2=female
    private int weight;
    private String kind;
    private String breed;
    private String[] vaccines; //TODO maybe change to type vaccines after gathering information

    public Pet(String name, long birthDate, int gender, int weight, String kind, String breed) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.weight = weight;
        this.kind = kind;
        this.breed = breed;
    }

    Pet(Bitmap photo, String name, long birthDate, int gender, int weight, String kind, String breed) {
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

    public String[] getVaccines() {
        return vaccines;
    }
}
