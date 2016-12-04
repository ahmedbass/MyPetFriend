package com.ahmedbass.mypetfriend;


import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

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

    private int petId;
    private int ownerId;
    private long createDate;
    private String name;
    private long birthDate; //in milliseconds
    private String gender;
    private String type;
    private String breed;
    private int currentWeight;
    private boolean isNeutered;
    private String microchipNumber;

    private int minWeight;
    private int maxWeight;
    private double dailyFeedingAmountInCups;
    private int trainingSessionInMinutes;
    private int exerciseNeedsInMinutes;

    private ArrayList<Bitmap> petPhotos = new ArrayList<>();
    private ArrayList<ScheduleActivity> petScheduleActivities = new ArrayList<>();
    private ArrayList<Vaccine> petVaccines = new ArrayList<>();
    private ArrayList<Integer> petWeightList = new ArrayList<>();

    public Pet() {}

    public Pet(int petId, int ownerId, long createDate, String name, long birthDate, String gender, String type, String breed, int currentWeight, boolean isNeutered, String microchipNumber, int minWeight, int maxWeight, double dailyFeedingAmountInCups, int trainingSessionInMinutes, int exerciseNeedsInMinutes) {
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
    }

    public int getPetAgeInYear(long birthDate) {
        birthDate = birthDate == 0 ? this.birthDate : birthDate;
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(birthDate);
        return Calendar.getInstance().get(Calendar.YEAR) - birth.get(Calendar.YEAR);
    }

    public int getPetAgeInMonth(long birthDate) {
        birthDate = birthDate == 0 ? this.birthDate : birthDate;
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

    public ArrayList<Bitmap> getPetPhotos() {
        return petPhotos;
    }

    public ArrayList<ScheduleActivity> getPetScheduleActivities() {
        return petScheduleActivities;
    }

    public ArrayList<Vaccine> getPetVaccines() {
        return petVaccines;
    }

    public long getCreateDate() {
        return createDate;
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

    public void setIsNeutered(boolean neutered) {
        isNeutered = neutered;
    }

    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public void addNewScheduleActivity(int scheduleActivityId, int petId, String scheduleType, long scheduleCreateDate, int hourOfDay, int minuteOfDay, int frequency, String notes, int notificationStatus) {
        petScheduleActivities.add(
                new ScheduleActivity(scheduleActivityId, petId, scheduleType, scheduleCreateDate, hourOfDay, minuteOfDay, frequency, notes, notificationStatus));
    }

    public void removeScheduleActivity(int scheduleActivityIndex) {
        petScheduleActivities.remove(scheduleActivityIndex);
    }

    public void addNewVaccine(int vaccineId, int petId, String name, int category, long createDate, int frequency, String notes, Context context) {
        this.petVaccines.add(new Vaccine(vaccineId, petId, name, category, createDate, frequency, notes));
        Toast.makeText(context, "HII", Toast.LENGTH_SHORT).show();
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

    public void addPhoto(Bitmap bitmap) {
        petPhotos.add(bitmap);
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

//        private final String[] catScheduleActivityTypes = {TYPE_BREAKFAST, TYPE_DINNER, TYPE_BATHING, TYPE_GROOMING, TYPE_TEETH_BRUSHING,
//                TYPE_NAIL_TRIMMING, TYPE_WEIGHING, TYPE_VACCINATION, TYPE_MEDICAL_CHECKUP, TYPE_BIRTHDAY};
//        private final String[] dogScheduleActivityTypes = {TYPE_BREAKFAST, TYPE_DINNER, TYPE_BATHING, TYPE_GROOMING, TYPE_TEETH_BRUSHING,
//                TYPE_NAIL_TRIMMING, TYPE_EXERCISING, TYPE_TRAINING, TYPE_WEIGHING, TYPE_VACCINATION, TYPE_MEDICAL_CHECKUP, TYPE_BIRTHDAY};

        final static int NOTIFICATION_STATUS_OFF = 0;
        final static int NOTIFICATION_STATUS_ON = 1;
        final static int NOTIFICATION_STATUS_ALARM = 2;

        private int scheduleId;
        private int petId;
        private int icon;
        private String type; //has to be one the defined types
        private long createDate; //when pet first registered in millis
        private int hourOfDay; //extract from Calendar createDate
        private int minuteOfDay; //extract from Calendar createDate
        private int frequency; //means: activity happens once every ? days
        private String notes; //some information to show the user about that pet activity
        private int notificationStatus; //0=off, 1=On, 2=Alarm
        private int notificationStatusIcon; //based on the notificationStatus

        public ScheduleActivity(int scheduleId, int petId, String type, long createDate, int hourOfDay, int minuteOfDay, int frequency, String notes, int notificationStatus) {
            this.scheduleId = scheduleId;
            this.petId = petId;
            this.type = type;
            this.createDate = createDate;
            this.hourOfDay = hourOfDay;
            this.minuteOfDay = minuteOfDay;
            this.frequency = frequency;
            setNotificationStatus(notificationStatus);
            setNotes(notes);
            setIcon(type);
        }

        public int getScheduleId() {
            return scheduleId;
        }

        public int getPetId() {
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
                case TYPE_BREAKFAST:
                    this.notes = "some valuable information about food for our beloved users";
                    break;
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
                    icon = R.drawable.paw_default;
                    break;
            }
        }
    }

    //----------------------------------------------------------------------------------------------

    class Vaccine implements Serializable{

        //4 months, below is a puppy, above is an adult dog
        final static int DOG_TURNING_AGE = 4;
        //core petVaccines should be given to every dog
        final static int CATEGORY_CORE = 1;
        //noncore petVaccines are recommended only for some dogs, depending on the dog situation:
        // e.g. age, breed, health status, and the potential exposure to a diseased animal.
        // the type of vaccine and how common the disease is in the geographical area where the dog lives or may visit.
        final static int CATEGORY_NON_CORE = 2;

        //TODO list all possible petVaccines for cats(about6) and dogs(about 11) here as final static

        private int vaccineId;
        private int petId;
        private String name;
        private int category; //1=core, 2=non-core
        private long createDate;
        private int frequency; //means: take another dose after ? days
        private String notes;

        public Vaccine(int vaccineId, int petId, String name, int category, long createDate, int frequency, String notes) {
            this.vaccineId = vaccineId;
            this.petId = petId;
            this.name = name;
            this.category = category;
            this.createDate = createDate;
            this.frequency = frequency;
            this.notes = notes;
        }

        public int getVaccineId() {
            return vaccineId;
        }

        public int getPetId() {
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

}
