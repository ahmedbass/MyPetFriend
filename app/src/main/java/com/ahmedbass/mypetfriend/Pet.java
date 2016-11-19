package com.ahmedbass.mypetfriend;


import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

class Pet implements Serializable {



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

    private ArrayList<Integer> petWeightList = new ArrayList<>();
    private ArrayList<Bitmap> petPhotos = new ArrayList<>();
    private ArrayList<ScheduleActivity> petScheduleActivities = new ArrayList<>();
    private ArrayList<Vaccine> petVaccines = new ArrayList<>();

    public Pet() {}

    public Pet(int petId, int ownerId, long createDate, String name, long birthDate, String gender, String type, String breed, int currentWeight, boolean isNeutered, String microchipNumber) {
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
    }

    public int getPetAgeInYear() {
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(birthDate);
        return Calendar.getInstance().get(Calendar.YEAR) - birth.get(Calendar.YEAR);
    }

    public int getPetAgeInMonth() {
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
        return petPhotos;
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

    public ArrayList<ScheduleActivity> getPetScheduleActivities() {
        return petScheduleActivities;
    }

    public ArrayList<Vaccine> getPetVaccines() {
        return petVaccines;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
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

    public void addNewVaccine(Vaccine newVaccine) {
        this.petVaccines.add(newVaccine);
    }

    public void removeVaccine(int vaccineIndex) {
        this.petVaccines.remove(vaccineIndex);
    }


    //----------------------------------------------------------------------------------------------

    class ScheduleActivity implements Serializable{

        final static String TYPE_BREAKFAST = "Breakfast";
        final static String TYPE_DINNER = "Dinner";
        final static String TYPE_BATHING = "Bathing";
        final static String TYPE_GROOMING = "Grooming";
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
            this.notificationStatus = notificationStatus;
            setIcon();
            setNotificationStatus(notificationStatus);
            setNotes(notes);
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

        private void setIcon() {
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
    }

    //----------------------------------------------------------------------------------------------

    class Vaccine implements Serializable{

        final static int DOG_TURNING_AGE = 4; //4 months, below is a puppy, above is an adult dog
        final static int CATEGORY_CORE = 1; //core petVaccines should be given to every dog
        //noncore petVaccines are recommended only for some dogs, depending on the dog situation:
        // e.g. age, breed, health status, and the potential exposure to a diseased animal.
        // the type of vaccine and how common the disease is in the geographical area where the dog lives or may visit.
        final static int CATEGORY_NON_CORE = 2;

        //TODO list all possible petVaccines for cats(about6) and dogs(about 11) here as final static

        private int vaccineId;
        private String name;
        private int category; //1=core, 2=non-core
        private long createDate;
        private int frequency; //means: take another dose after ? days
        private String notes;
        private int initialPuppyDoses; //how many doses to take at first time
        private int initialAdultDoses;

        public Vaccine(int vaccineId, String name, int category, int initialPuppyDoses, int initialAdultDoses, long createDate, int frequency, String notes) {
            this.vaccineId = vaccineId;
            this.name = name;
            this.category = category;
            this.initialPuppyDoses = initialPuppyDoses;
            this.initialAdultDoses = initialAdultDoses;
            this.createDate = createDate;
            this.frequency = frequency;
            this.notes = notes;
        }

        public int getVaccineId() {
            return vaccineId;
        }

        public void setVaccineId(int vaccineId) {
            this.vaccineId = vaccineId;
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
