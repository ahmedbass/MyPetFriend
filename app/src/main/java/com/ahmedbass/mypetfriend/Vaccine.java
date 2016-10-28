package com.ahmedbass.mypetfriend;

class Vaccine {

    final static int DOG_TURNING_AGE = 4; //4 months, below is a puppy, above is an adult dog
    final static int CORE_VACCINE = 1;
    final static int NON_CORE_VACCINE = 2;
    final static long YEAR_IN_MILLIES = Long.parseLong("31556952000");
    final static long MONTH_IN_MILLIES = Long.parseLong("2629746000");

    private int petId;
    private int vaccineId;
    private String vaccineName;
    private int category; //1=core, 2=non-core
    private int initialPuppyDoses; //how many doses to take at first time
    private int initialAdultDoses;
    private int nextBooster; //after how long to take another dose
    private String comments;
    private long lastTimeTaken; //date in milliseconds

    public Vaccine(int petId, int vaccineId, String vaccineName, int category, int initialPuppyDoses, int initialAdultDoses, int nextBooster, String comments, long lastTimeTaken) {
        this.petId = petId;
        this.vaccineId = vaccineId;
        this.vaccineName = vaccineName;
        this.category = category;
        this.initialPuppyDoses = initialPuppyDoses;
        this.initialAdultDoses = initialAdultDoses;
        this.nextBooster = nextBooster;
        this.comments = comments;
        this.lastTimeTaken = lastTimeTaken;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(int vaccineId) {
        this.vaccineId = vaccineId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getInitialPuppyDoses() {
        return initialPuppyDoses;
    }

    public void setInitialPuppyDoses(int initialPuppyDoses) {
        this.initialPuppyDoses = initialPuppyDoses;
    }

    public int getInitialAdultDoses() {
        return initialAdultDoses;
    }

    public void setInitialAdultDoses(int initialAdultDoses) {
        this.initialAdultDoses = initialAdultDoses;
    }

    public int getNextBooster() {
        return nextBooster;
    }

    public void setNextBooster(int nextBooster) {
        this.nextBooster = nextBooster;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public long getLastTimeTaken() {
        return lastTimeTaken;
    }

    public void setLastTimeTaken(long lastTimeTaken) {
        this.lastTimeTaken = lastTimeTaken;
    }
}
