package com.ahmedbass.mypetfriend;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

class Pet implements Serializable {

    //---Static Values---
    public final static String GENDER_MALE = "Male";
    public final static String GENDER_FEMALE = "Female";
    public final static String TYPE_CAT = "Cat";
    public final static String TYPE_DOG = "Dog";
    public final static int NEUTERED = 1;
    public final static int NOT_NEUTERED = 0;
    //levels of characteristics
    final static int LEVEL_LOW = 1;
    final static int LEVEL_MEDIUM = 2;
    final static int LEVEL_HIGH = 3;

    private long petId;
    private long ownerId;
    private long createDate;
    private String name;
    private long birthDate; //in milliseconds
    private String gender;
    private String type;
    private String breed;
    private int currentWeight;
    private boolean isNeutered;
    private String microchipNumber;
    private byte[] currentPhoto;

    private int minWeight;
    private int maxWeight;
    private double dailyFeedingAmountInCups;
    private int trainingSessionInMinutes;
    private int exerciseNeedsInMinutes;

    private String breedHighlights;
    private String breedPersonality;
    private String breedHealth;
    private String breedCare;
    private String breedFeeding;
    private String breedHistory;

    private ArrayList<ScheduleActivity> petScheduleActivities = new ArrayList<>();
    private ArrayList<Vaccine> petVaccines = new ArrayList<>();
    private ArrayList<PetPhoto> petPhotos = new ArrayList<>();
    private ArrayList<Integer> petWeightList = new ArrayList<>();

    public Pet() {}

    public Pet(long petId, long ownerId, long createDate, String name, long birthDate, String gender, String type, String breed, int currentWeight, boolean isNeutered, String microchipNumber, int minWeight, int maxWeight, double dailyFeedingAmountInCups, int trainingSessionInMinutes, int exerciseNeedsInMinutes, String breedHighlights, String breedPersonality, String breedHealth, String breedCare, String breedFeeding, String breedHistory) {
        this.petId = petId;
        this.ownerId = ownerId;
        this.createDate = createDate;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.type = type;
        this.breed = breed;
        this.currentWeight = currentWeight;
        this.isNeutered = isNeutered;
        this.microchipNumber = microchipNumber;

        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
        this.dailyFeedingAmountInCups = dailyFeedingAmountInCups;
        this.trainingSessionInMinutes = trainingSessionInMinutes;
        this.exerciseNeedsInMinutes = exerciseNeedsInMinutes;

        this.breedHighlights = breedHighlights;
        this.breedPersonality = breedPersonality;
        this.breedHealth = breedHealth;
        this.breedCare = breedCare;
        this.breedFeeding = breedFeeding;
        this.breedHistory = breedHistory;
    }

    public int getPetAgeInYear(long birthDate) {
        birthDate = (birthDate == 0 ? this.birthDate : birthDate);
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(birthDate);
        return Calendar.getInstance().get(Calendar.YEAR) - birth.get(Calendar.YEAR);
    }

    public int getPetAgeInMonth(long birthDate) {
        birthDate = (birthDate == 0 ? this.birthDate : birthDate);
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(birthDate);
        return Calendar.getInstance().get(Calendar.MONTH) - birth.get(Calendar.MONTH);
    }

    public long getPetId() {
        return petId;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String getName() {
        return name;
    }

    public long getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getType() {
        return type;
    }

    public String getBreed() {
        return breed;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public ArrayList<Integer> getPetWeightList() {
        return petWeightList;
    }

    public boolean isNeutered() {
        return isNeutered;
    }

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public ArrayList<PetPhoto> getPetPhotos() {
        return petPhotos;
    }

    public ArrayList<Bitmap> getPetPhotosExtracted() {
        ArrayList<Bitmap> petPhotosExtracted = new ArrayList<>();
        for (PetPhoto petPhoto : petPhotos) {
            petPhotosExtracted.add(BitmapFactory.decodeByteArray(petPhoto.getPhoto(), 0, petPhoto.getPhoto().length));
        }
        return petPhotosExtracted;
    }

    public ArrayList<ScheduleActivity> getPetScheduleActivities() {
        return petScheduleActivities;
    }

    public ArrayList<Vaccine> getPetVaccines() {
        return petVaccines;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(long birthDate) {
        this.birthDate = birthDate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setCurrentWeight(int weight) {
        currentWeight = weight;
        this.petWeightList.add(weight);
    }

    public void setIsNeutered(boolean isNeutered) {
        this.isNeutered = isNeutered;
    }

    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public void addPetPhoto(long id, long petId, byte[] photo, long photoDate, String photoDescription) {
        petPhotos.add(new PetPhoto(id, petId, photo, photoDate, photoDescription));
    }

    public void addNewScheduleActivity(long scheduleActivityId, long petId, String scheduleType, long scheduleCreateDate, int frequency, int hourOfDay, int minuteOfDay, String notes, int notificationStatus) {
        this.petScheduleActivities.add(
                new ScheduleActivity(scheduleActivityId, petId, scheduleType, scheduleCreateDate, frequency, hourOfDay, minuteOfDay, notes, notificationStatus));
    }

    public void removeScheduleActivity(int scheduleActivityIndex) {
        this.petScheduleActivities.remove(scheduleActivityIndex);
    }

    public void addNewVaccine(long vaccineId, long petId, String name, int category, long createDate, int frequency, String notes) {
        this.petVaccines.add(new Vaccine(vaccineId, petId, name, category, createDate, frequency, notes));
    }

    public void removeVaccine(int vaccineIndex) {
        this.petVaccines.remove(vaccineIndex);
    }

    public int getMinWeight() {
        return minWeight;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public double getDailyFeedingAmountInCups() {
        return dailyFeedingAmountInCups;
    }

    public void setDailyFeedingAmountInCups(double dailyFeedingAmountInCups) {
        this.dailyFeedingAmountInCups = dailyFeedingAmountInCups;
    }

    public int getTrainingSessionInMinutes() {
        return trainingSessionInMinutes;
    }

    public int getExerciseNeedsInMinutes() {
        return exerciseNeedsInMinutes;
    }

    public void setMinWeight(int minWeight) {
        this.minWeight = minWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void setTrainingSessionInMinutes(int trainingSessionInMinutes) {
        this.trainingSessionInMinutes = trainingSessionInMinutes;
    }

    public void setExerciseNeedsInMinutes(int exerciseNeedsInMinutes) {
        this.exerciseNeedsInMinutes = exerciseNeedsInMinutes;
    }

    public String getBreedHighlights() {
        return breedHighlights;
    }

    public String getBreedPersonality() {
        return breedPersonality;
    }

    public String getBreedHealth() {
        return breedHealth;
    }

    public String getBreedCare() {
        return breedCare;
    }

    public String getBreedFeeding() {
        return breedFeeding;
    }

    public String getBreedHistory() {
        return breedHistory;
    }

    public void setBreedHighlights(String breedHighlights) {
        this.breedHighlights = breedHighlights;
    }

    public void setBreedPersonality(String breedPersonality) {
        this.breedPersonality = breedPersonality;
    }

    public void setBreedHealth(String breedHealth) {
        this.breedHealth = breedHealth;
    }

    public void setBreedCare(String breedCare) {
        this.breedCare = breedCare;
    }

    public void setBreedFeeding(String breedFeeding) {
        this.breedFeeding = breedFeeding;
    }

    public void setBreedHistory(String breedHistory) {
        this.breedHistory = breedHistory;
    }

    public byte[] getCurrentPhoto() {
        return currentPhoto;
    }

    public void setCurrentPhoto(byte[] currentPhoto) {
        this.currentPhoto = currentPhoto;
    }

    //----------------------------------------------------------------------------------------------

    class ScheduleActivity implements Serializable{

        //these are all types of schedule activities that are supported in our app. in the future, we may allow user to add their own.
        final static String TYPE_BREAKFAST = "Breakfast";
        final static String TYPE_DINNER = "Dinner";
        final static String TYPE_GROOMING = "Grooming";
        final static String TYPE_BATHING = "Bathing";
        final static String TYPE_TEETH_BRUSHING = "Teeth Brushing";
        final static String TYPE_NAIL_TRIMMING = "Nail Trimming";
        final static String TYPE_EXERCISING = "Exercising";
        final static String TYPE_TRAINING = "Training";
        final static String TYPE_WEIGHING = "Weighing";
        final static String TYPE_VACCINATION = "Vaccination";
        final static String TYPE_MEDICAL_CHECKUP = "Medical Checkup";
        final static String TYPE_BIRTHDAY = "Birthday";

        final static int NOTIFICATION_STATUS_OFF = 0;
        final static int NOTIFICATION_STATUS_ON = 1;
        final static int NOTIFICATION_STATUS_ALARM = 2;

        private long scheduleId;
        private long petId;
        private int icon;
        private String type; //has to be one the defined types
        private long createDate; //when pet first registered in millis
        private int frequency; //means: activity happens once every ? days
        private int hourOfDay; //extract from Calendar createDate
        private int minuteOfDay; //extract from Calendar createDate
        private String notes; //some information to show the user about that pet activity
        private int notificationStatus; //0=off, 1=On, 2=Alarm
        private int notificationStatusIcon; //based on the notificationStatus

        public ScheduleActivity(long scheduleId, long petId, String type, long createDate, int frequency, int hourOfDay, int minuteOfDay, String notes, int notificationStatus) {
            this.scheduleId = scheduleId;
            this.petId = petId;
            this.type = type;
            this.createDate = createDate;
            this.frequency = frequency;
            this.hourOfDay = hourOfDay;
            this.minuteOfDay = minuteOfDay;
            setNotificationStatus(notificationStatus);
            setNotes(notes);
            setIcon(type);
        }

        public long getScheduleId() {
            return scheduleId;
        }

        public long getPetId() {
            return petId;
        }

        public int getIcon() {
            return icon;
        }

        public String getType() {
            return type;
        }

        public long getCreateDate() {
            return createDate;
        }

        public int getHourOfDay() {
            return hourOfDay;
        }

        public int getMinuteOfDay() {
            return minuteOfDay;
        }

        public String getDisplayTime() {
            String displayTime = "";
            if (getHourOfDay() > 12) {
                displayTime += (getHourOfDay() - 12) + ":";
            } else {
                displayTime += getHourOfDay() + ":";
            }
            if (getMinuteOfDay() < 10) {
                displayTime += "0" + getMinuteOfDay();
            } else {
                displayTime += getMinuteOfDay();
            }
            if (getHourOfDay() < 12) {
                displayTime += " AM";
            } else {
                displayTime += " PM";
            }

            return displayTime;
        }

        public int getFrequency() {
            return frequency;
        }

        public String getNotes() {
            return notes;
        }

        public int getNotificationStatus() {
            return notificationStatus;
        }

        public int getNotificationStatusIcon() {
            return notificationStatusIcon;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public void setHourOfDay(int hourOfDay) {
            this.hourOfDay = hourOfDay;
        }

        public void setMinuteOfDay(int minuteOfDay) {
            this.minuteOfDay = minuteOfDay;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public void setNotificationStatus(int notificationStatus) {
            this.notificationStatus = notificationStatus;
            switch (notificationStatus) {
                case NOTIFICATION_STATUS_OFF:
                    notificationStatusIcon = R.drawable.notification_off;
                    break;
                case NOTIFICATION_STATUS_ON:
                    notificationStatusIcon = R.drawable.notification_on;
                    break;
                case NOTIFICATION_STATUS_ALARM:
                    notificationStatusIcon = R.drawable.notification_alarm;
                    break;
            }
        }

        public void setNotes(String notes) {
            switch (type) {
//                case TYPE_BREAKFAST:
//                    this.notes = "some valuable information about food for our beloved users";
//                    break;
                //...
                default:
                    this.notes = notes;
            }
        }

        private void setIcon(String type) {
            switch (type) {
                case TYPE_BREAKFAST:
                case TYPE_DINNER:
                    icon = R.drawable.food;
                    break;
                case TYPE_BATHING:
                    icon = R.drawable.bathing;
                    break;
                case TYPE_GROOMING:
                    icon = R.drawable.grooming;
                    break;
                case TYPE_TEETH_BRUSHING:
                    icon = R.drawable.teeth_brushing;
                    break;
                case TYPE_NAIL_TRIMMING:
                    icon = R.drawable.nail_trimming;
                    break;
                case TYPE_EXERCISING:
                    icon = R.drawable.exercising;
                    break;
                case TYPE_TRAINING:
                    icon = R.drawable.training;
                    break;
                case TYPE_WEIGHING:
                    icon = R.drawable.weighing;
                    break;
                case TYPE_VACCINATION:
                    icon = R.drawable.vaccination;
                    break;
                case TYPE_MEDICAL_CHECKUP:
                    icon = R.drawable.medical_checkup;
                    break;
                case TYPE_BIRTHDAY:
                    icon = R.drawable.birthday;
                    break;
                default:
                    icon = R.drawable.paw_symbol;
                    break;
            }
            if (type.contains(TYPE_VACCINATION)) {
                icon = R.drawable.vaccination;
            }
        }
    }

    //----------------------------------------------------------------------------------------------

    class Vaccine implements Serializable{

        final static int CATEGORY_CORE = 1; //core petVaccines should be given to every dog
        //noncore petVaccines are recommended only for some dogs, depending on the dog situation:
        // e.g. age, breed, health status, and the potential exposure to a diseased animal.
        // the type of vaccine and how common the disease is in the geographical area where the dog lives or may visit.
        final static int CATEGORY_NON_CORE = 2;

        //TODO list all possible petVaccines for cats(about6) and dogs(about 11) here as final static

        private long vaccineId;
        private long petId;
        private String name;
        private int category; //1=core, 2=non-core
        private long createDate;
        private int frequency; //means: take another dose after ? days
        private String notes;

        public Vaccine(long vaccineId, long petId, String name, int category, long createDate, int frequency, String notes) {
            this.vaccineId = vaccineId;
            this.petId = petId;
            this.name = name;
            this.category = category;
            this.createDate = createDate;
            this.frequency = frequency;
            this.notes = notes;
        }

        public long getVaccineId() {
            return vaccineId;
        }

        public long getPetId() {
            return petId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCategory() {
            return category;
        }

        public void setCategory(int category) {
            this.category = category;
        }

        public long getCreateDate() {
            return createDate;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public int getFrequency() {
            return frequency;
        }

        public void setFrequency(int frequency) {
            this.frequency = frequency;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }
    }

    //----------------------------------------------------------------------------------------------

    class PetPhoto implements Serializable{
        private long id;
        private long petId;
        private byte[] photo;
        private long photoDate;
        private String photoDescription;

        public PetPhoto(long id, long petId, byte[] photo, long photoDate, String photoDescription) {
            this.id = id;
            this.petId = petId;
            this.photo = photo;
            this.photoDate = photoDate;
            this.photoDescription = photoDescription;
        }

        public long getId() {
            return id;
        }

        public long getPetId() {
            return petId;
        }

        public byte[] getPhoto() {
            return photo;
        }

        public long getPhotoDate() {
            return photoDate;
        }

        public String getPhotoDescription() {
            return photoDescription;
        }

        public void setPhotoDescription(String photoDescription) {
            this.photoDescription = photoDescription;
        }
    }

}
