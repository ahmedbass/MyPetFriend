package com.ahmedbass.mypetfriend;

import android.provider.BaseColumns;

// The schema of my application's database
// This outer class represents the whole database, and each inner class represents a table
public class MyPetFriendContract {

    private MyPetFriendContract() {} // Empty constructor to prevent accidental instantiation.

    public static final class UsersEntry implements BaseColumns {

        public final static String TABLE_NAME = "Users";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_CREATE_DATE = "createDate";
        public final static String COLUMN_USER_TYPE = "userType";
        public final static String COLUMN_EMAIL = "email";
        public final static String COLUMN_PASSWORD = "password";
        public final static String COLUMN_FIRST_NAME = "firstName";
        public final static String COLUMN_LAST_NAME = "lastName";
        public final static String COLUMN_BIRTH_DATE = "birthDate";
        public final static String COLUMN_GENDER = "gender";
        public final static String COLUMN_CITY = "city";
        public final static String COLUMN_COUNTRY = "country";
        public final static String COLUMN_PROFILE_PICTURE = "profilePicture";
        public final static String COLUMN_PHONE_NUMBER = "phoneNumber";

        public final static String COLUMN_PROFILE_BIO = "profileBio";
        public final static String COLUMN_YEARS_OF_EXPERIENCE = "yearsOfExperience";
        public final static String COLUMN_AVERAGE_RATE_PER_HOUR = "averageRatePerHour";
        public final static String COLUMN_AVAILABILITY = "availability";
        public final static String COLUMN_TRAVEL_DISTANCE = "travelDistance";
        public final static String COLUMN_SERVICE_PROVIDED_FOR = "serviceProvidedFor";
        public final static String COLUMN_SERVICE_PROVIDED = "serviceProvided";
    }

    public static final class UserOfferedAdvertsEntry implements BaseColumns {

        public final static String TABLE_NAME = "UserFavoriteAdverts";

        public final static String _ID = BaseColumns._ID;
        public final static String SELLER_ID = "sellerId";
        public final static String CREATE_DATE = "createDate";
        public final static String IS_SOLD = "isSold";
        public final static String VIEW_COUNT = "viewCount";
        public final static String TITLE = "title";
        public final static String PRICE = "price";
        public final static String CATEGORY = "category";
        public final static String TYPE = "type";
        public final static String DETAILS = "details";
        public final static String CITY = "city";
        public final static String COUNTRY = "country";
        public final static String EMAIL = "email";
        public final static String PHONE_NUMBER = "phoneNumber";
        public final static String PET_TYPE = "petType";
        public final static String PET_BREED = "petBreed";
        public final static String PET_AGE_IN_MONTHS = "petAgeInMonths";
        public final static String PET_GENDER = "petGender";
        public final static String IS_PET_NEUTERED = "isPetNuetered";
        public final static String IS_PET_MICROCHIPPED = "isPetMicrochipped";
        public final static String IS_PET_VACCINATED = "isPetVaccinated";
    }

    public static final class UserFavoriteAdvertsEntry implements BaseColumns {

        public final static String TABLE_NAME = "UserFavoriteAdverts";

        public final static String USER_ID = "userId";
        public final static String ADVERT_ID = "advertId";
        public final static String SELLER_ID = "sellerId";
        public final static String CREATE_DATE = "createDate";
        public final static String IS_SOLD = "isSold";
        public final static String VIEW_COUNT = "viewCount";
        public final static String TITLE = "title";
        public final static String PRICE = "price";
        public final static String CATEGORY = "category";
        public final static String TYPE = "type";
        public final static String DETAILS = "details";
        public final static String CITY = "city";
        public final static String COUNTRY = "country";
        public final static String EMAIL = "email";
        public final static String PHONE_NUMBER = "phoneNumber";
        public final static String PET_TYPE = "petType";
        public final static String PET_BREED = "petBreed";
        public final static String PET_AGE_IN_MONTHS = "petAgeInMonths";
        public final static String PET_GENDER = "petGender";
        public final static String IS_PET_NEUTERED = "isPetNuetered";
        public final static String IS_PET_MICROCHIPPED = "isPetMicrochipped";
        public final static String IS_PET_VACCINATED = "isPetVaccinated";

    }

    public static final class UserFavoritePetCareProvidersEntry implements BaseColumns {

        public final static String TABLE_NAME = "UserFavoritePetCareProviders";

        public final static String USER_ID = "userId";
        public final static String PET_CARE_PROVIDER_ID = "petCareProviderId";
        public final static String CREATE_DATE = "createDate";
        public final static String USER_TYPE = "userType";
        public final static String EMAIL = "email";
        public final static String PASSWORD = "password";
        public final static String FIRST_NAME = "firstName";
        public final static String LAST_NAME = "lastName";
        public final static String BIRTH_DATE = "birthDate";
        public final static String GENDER = "gender";
        public final static String CITY = "city";
        public final static String COUNTRY = "country";
        public final static String PROFILE_PICTURE = "profilePicture";
        public final static String PHONE_NUMBER = "phoneNumber";

        public final static String PROFILE_BIO = "profileBio";
        public final static String AVAILABILITY = "availability";
        public final static String YEARS_OF_EXPERIENCE = "yearsOfExperience";
        public final static String AVERAGE_RATE_PER_HOUR = "averageRatePerHour";
        public final static String SERVICE_PROVIDED_FOR = "serviceProvidedFor";
        public final static String SERVICE_PROVIDED = "serviceProvided";

    }

    public static final class AdvertPhotosEntry implements BaseColumns {

        public final static String TABLE_NAME = "AdvertPhotos";

        public final static String _ID = BaseColumns._ID;
        public final static String ADVERT_ID = "advertId";
        public final static String PHOTO = "photo";
    }

    public static final class PetsEntry implements BaseColumns {

        public final static String TABLE_NAME = "Pets";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_OWNER_ID = "ownerId";
        public final static String COLUMN_CREATE_DATE = "createDate";
        public final static String COLUMN_PET_NAME = "name";
        public final static String COLUMN_PET_BIRTH_DATE = "birthDate";
        public final static String COLUMN_PET_GENDER = "gender";
        public final static String COLUMN_PET_TYPE = "type";
        public final static String COLUMN_PET_BREED = "breed";
        public final static String COLUMN_PET_CURRENT_WEIGHT = "currentWeight";
        public final static String COLUMN_PET_MICROCHIP = "microchip";
        public final static String COLUMN_PET_NEUTERED = "neutered";

        public final static String COLUMN_PET_WEIGHTS = "weightList";
        public final static String COLUMN_PET_PHOTOS = "photos";
        public final static String COLUMN_PET_SCHEDULES = "schedules";
        public final static String COLUMN_PET_VACCINES = "vaccines";

        public final static String GENDER_MALE = "Male";
        public final static String GENDER_FEMALE = "Female";
        public final static String TYPE_CAT = "Cat";
        public final static String TYPE_DOG = "Dog";
        public final static int NEUTERED = 1;
        public final static int NOT_NEUTERED = 0;

    }

    public static final class PetWeightListEntry implements BaseColumns {

        public final static String TABLE_NAME = "PetWeightList";

        public final static String WEIGHT_ID = BaseColumns._ID;
        public final static String PET_ID = "petId";
        public final static String WEIGHT = "weight";
        public final static String WEIGHT_DATE = "weightDate";
    }

    public static final class PetPhotosEntry implements BaseColumns {

        public final static String TABLE_NAME = "PetPhotos";

        public final static String _ID = BaseColumns._ID;
        public final static String PET_ID = "petId";
        public final static String PHOTO = "photo";
        public final static String PHOTO_DATE = "photoDate";
        public final static String PHOTO_DESCRIPTION = "photoDescription";

    }

    public static final class PetScheduleActivitiesEntry implements BaseColumns {

    public final static String TABLE_NAME = "PetScheduleActivities";

        public final static String _ID = BaseColumns._ID;
        public final static String PET_ID = "petId";
        public final static String TYPE = "type";
        public final static String CREATE_DATE = "createDate";
        public final static String HOUR_OF_DAY = "hourOfDay";
        public final static String MINUTE_OF_DAY = "minuteOfDay";
        public final static String FREQUENCY = "frequency";
        public final static String NOTES = "notes";
        public final static String NOTIFICATION_STATUS = "notificationStatus";

    }

    public static final class PetVaccinesEntry implements BaseColumns {

        public final static String TABLE_NAME = "PetVaccines";

        public final static String _ID = BaseColumns._ID;
        public final static String PET_ID = "petId";
        public final static String NAME = "name";
        public final static String CATEGORY = "category";
        public final static String CREATE_DATE = "createDate";
        public final static String BOOSTER_FREQUENCY = "boosterFrequency";
        public final static String NOTES = "notes";

    }


    //to be filled by me, as general information about different breeds
    public static final class DogBreeds implements BaseColumns {

        public final static String TABLE_NAME = "DogBreeds";

        public final static String _ID = BaseColumns._ID;
        //basic information
        public final static String NAME = "name";
        public final static String BREAD_GROUP = "breedGroup";
        public final static String SIZE = "size";
        public final static String MIN_WEIGHT = "minWeight";
        public final static String MAX_WEIGHT = "maxWeight";
        public final static String MIN_LIFE_EXPECTANCY = "minLifeExpectancy";
        public final static String MAX_LIFE_EXPECTANCY = "maxLifeExpectancy";
        public final static String FEEDING_AMOUNT_PER_DAY = "feedingAmountPerDay";
        public final static String MEALS_PER_DAY = "mealsPerDay";

        //breed characteristics
        public final static String ADAPTABILITY = "adaptability"; //calculate average of following attributes
        public final static String APARTMENT_LIVING_ADAPTABILITY = "apartmentLivingAdaptabilityLevel";
        public final static String GOOD_FOR_NOVICE_OWNERS_LEVEL = "goodForNoviceOwnersLevel";
        public final static String SENSITIVITY_LEVEL = "sensitivityLevel";
        public final static String BEING_ALONE_TOLERANCE_LEVEL = "beingAloneToleranceLevel";
        public final static String COLD_WEATHER_TOLERANCE_LEVEL = "coldWeatherToleranceLevel";
        public final static String HOT_WEATHER_TOLERANCE_LEVEL = "hotWeatherToleranceLevel";

        public final static String FRIENDLINESS = "friendliness"; //calculate average of following attributes
        public final static String FAMILY_AFFECTIONATE_LEVEL = "familyAffectionateLevel";
        public final static String KID_FRIENDLY_LEVEL = "kidFriendlyLevel";
        public final static String PET_FRIENDLY_LEVEL = "petFriendlyLevel";
        public final static String STRANGER_FRIENDLY_LEVEL = "strangerFriendlyLevel";

        public final static String MAINTENANCE = "maintenance"; //calculate average of following attributes
        public final static String AMOUNT_OF_SHEDDING_LEVEL = "amountOfSheddingLevel";
        public final static String EASE_OF_GROOMING_LEVEL = "easeOfGroomingLevel";
        public final static String DROOLING_POTENTIAL_LEVEL = "droolingPotentialLevel";
        public final static String WEIGHT_GAIN_POTENTIAL_LEVEL = "weightGainPotentialLevel";
        public final static String GENERAL_HEALTH_LEVEL = "generalHealthLevel";

        public final static String TRAINABILITY = "trainability"; //calculate average of following attributes
        public final static String EASE_OF_TRAINING_LEVEL = "easeOfTrainingLevel";
        public final static String INTELLIGENCE_LEVEL = "IntelligenceLevel";
        public final static String BARKING_LEVEL = "barkingLevel";
        public final static String MOUTHINESS_POTENTIAL_LEVEL = "mouthinessPotentialLevel";
        public final static String PREY_DRIVE_LEVEL = "preyDriveLevel";
        public final static String WANDERLUST_POTENTIAL_LEVEL = "wanderlustPotentialLevel";

        public final static String EXERCING = "exercising"; //calculate average of following attributes
        public final static String ENERGY_LEVEL = "energyLevel";
        public final static String EXERCISE_NEEDS_LEVEL = "exerciseNeedsLevel";
    }

    public static final class CatBreeds implements BaseColumns {

        public final static String TABLE_NAME = "CatBreeds";

        public final static String _ID = BaseColumns._ID;
        //basic information
        public final static String NAME = "name";
        public final static String SIZE = "size";
        public final static String MIN_WEIGHT = "minWeight";
        public final static String MAX_WEIGHT = "maxWeight";
        public final static String MIN_LIFE_EXPECTANCY = "minLifeExpectancy";
        public final static String MAX_LIFE_EXPECTANCY = "maxLifeExpectancy";
        public final static String FEEDING_AMOUNT_PER_DAY = "feedingAmountPerDay";
        public final static String MEALS_PER_DAY = "mealsPerDay";

        //breed characteristics
        public final static String FAMILY_AFFECTINATE_LEVEL = "familyAffectionateLevel";
        public final static String KID_FRIENDLY_LEVEL = "kidFriendlyLevel";
        public final static String PET_FRIENDLY_LEVEL = "petFriendlyLevel";
        public final static String STRANGER_FRIENDLY_LEVEL = "strangerFriendlyLevel";
        public final static String INTELLIGENCE_LEVEL = "IntelligenceLevel";
        public final static String AMOUNT_OF_SHEDDING_LEVEL = "amountOfSheddingLevel";
        public final static String EASE_OF_GROOMING_LEVEL = "easeOfGroomingLevel";
        public final static String GENERAL_HEALTH_LEVEL = "generalHealthLevel";
    }
}













