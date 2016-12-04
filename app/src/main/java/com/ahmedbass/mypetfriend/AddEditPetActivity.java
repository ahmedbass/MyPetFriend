package com.ahmedbass.mypetfriend;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.NOTIFICATION_STATUS_ON;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_BATHING;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_BIRTHDAY;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_BREAKFAST;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_DINNER;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_EXERCISING;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_GROOMING;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_MEDICAL_CHECKUP;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_NAIL_TRIMMING;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_TEETH_BRUSHING;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_TRAINING;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_VACCINATION;
import static com.ahmedbass.mypetfriend.Pet.ScheduleActivity.TYPE_WEIGHING;

public class AddEditPetActivity extends AppCompatActivity {

    ImageButton uploadPhoto_btn;
    EditText petName_etxt, dayPick_etxt, yearPick_etxt, petWeight_etxt, microchipNumber_etxt;
    Spinner monthPick_spnr, typePick_spnr;
    RadioGroup genderPick_rgrb, isNeutered_rgrp;
    AutoCompleteTextView petBreed_atxt;
    ArrayAdapter<String> typeAdapter, breedAdapter;
    Button savePet_btn, deletePet_btn;

    MyDBHelper dbHelper;
    Cursor cursor;

    Pet myPet;
    String name, gender, type, breed, microchipNumber;
    long petCreateDate, birthDate;
    int petId, ownerId, weight, isNeutered, minWeight, maxWeight, trainingSessionInMinutes, exerciseNeedsInMinutes;;
    double dailyFeedingAmountInCups;

    String previousSelectedBreed, receivedBreedFromEditing;

    //to be filled on choosing pet type
    ArrayList<String> availableCatBreeds = new ArrayList<>();
    ArrayList<String> availableDogBreeds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pet);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dbHelper = new MyDBHelper(getBaseContext());
        initializeMyViews();

        if(getIntent() != null && getIntent().getBooleanExtra("AddPetProfile", false)) {
            forAddProfile();
        } else if (getIntent() != null && getIntent().getBooleanExtra("EditPetProfile", false)) {
            forEditProfile();
        }

        //listeners for instant data validation for date, weight
        dayPick_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (!dayPick_etxt.getText().toString().isEmpty()) {
                    if (Integer.parseInt(dayPick_etxt.getText().toString()) < 0) {
                        dayPick_etxt.setText("0");
                    } else if (Integer.parseInt(dayPick_etxt.getText().toString()) > 31) {
                        dayPick_etxt.setText("31");
                    }
                }
            }
        });
        yearPick_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (!yearPick_etxt.getText().toString().isEmpty()) {
                    if (Integer.parseInt(yearPick_etxt.getText().toString()) < 0) {
                        yearPick_etxt.setText("0");
                    } else if (Integer.parseInt(yearPick_etxt.getText().toString()) > Calendar.getInstance().get(Calendar.YEAR)) {
                        yearPick_etxt.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
                    }
                }
            }
        });
        petWeight_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if (!petWeight_etxt.getText().toString().isEmpty() && Integer.parseInt(petWeight_etxt.getText().toString()) < 0) {
                    petWeight_etxt.setText("0");
                }
            }
        });

        typePick_spnr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int previousItemSelected;
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //when selecting different pet type do the following (if selecting same type, no need to do all that again)
                if (position != previousItemSelected) {
                    petBreed_atxt.setText("");
                    if (typePick_spnr.getSelectedItem().toString().equals(Pet.TYPE_CAT)) {
                        //this is to avoid reading from db every time selecting type cat (within same activity session)
                        if (availableCatBreeds.isEmpty()) {
                            dbHelper.open();
                            cursor = dbHelper.getAllRecords(MyPetFriendContract.StoredCatBreedsEntry.TABLE_NAME,
                                    new String[]{MyPetFriendContract.StoredCatBreedsEntry.NAME});
                            while (cursor.moveToNext()) {
                                availableCatBreeds.add(cursor.getString(0));
                            }
                            cursor.close();
                            dbHelper.close();
                        }
                        breedAdapter.clear();
                        breedAdapter.addAll(availableCatBreeds);
                        petBreed_atxt.setAdapter(breedAdapter);
                    } else if (typePick_spnr.getSelectedItem().toString().equals(Pet.TYPE_DOG)) {
                        //this is to avoid reading from db every time selecting type dog (within same activity session)
                        if (availableDogBreeds.isEmpty()) {
                            dbHelper.open();
                            cursor = dbHelper.getAllRecords(MyPetFriendContract.StoredDogBreedsEntry.TABLE_NAME, new String[]{MyPetFriendContract.StoredDogBreedsEntry.NAME});
                            while (cursor.moveToNext()) {
                                availableDogBreeds.add(cursor.getString(0));
                            }
                            cursor.close();
                            dbHelper.close();
                        }
                        breedAdapter.clear();
                        breedAdapter.addAll(availableDogBreeds);
                        petBreed_atxt.setAdapter(breedAdapter);
                    } else {
                        breedAdapter.clear();
                        petBreed_atxt.setAdapter(breedAdapter);
                        Toast.makeText(AddEditPetActivity.this, "Warning: only cats and dogs are currently supported. You can still save other pet types, but it won't have vaccinations or activities schedule.", Toast.LENGTH_SHORT).show();
                    }
                }
                previousItemSelected = position;
                //to solve problem of clearing petBreed_atxt even when launching activity for editing (while we wanna keep the selected one)
                if (receivedBreedFromEditing != null) {
                    petBreed_atxt.setText(receivedBreedFromEditing);
                    receivedBreedFromEditing = null;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
    }

    private void forAddProfile() {
        deletePet_btn.setVisibility(View.GONE);
        savePet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first validate and set values to variables then come back and write them to database
                if(validatePetData()) {
                    if (!setPetSchedule(false)) {
                        Toast.makeText(AddEditPetActivity.this, "Warning: Cannot Setup Pet's Schedule Activities", Toast.LENGTH_SHORT).show();
                    } else if (!setPetVaccines(false)) {
                        Toast.makeText(AddEditPetActivity.this, "Warning: Cannot Setup Pet's Schedule Vaccinations", Toast.LENGTH_SHORT).show();
                    }
                    dbHelper.open();
                    String[] columnNamesPets = dbHelper.getColumnNames(MyPetFriendContract.PetsEntry.TABLE_NAME);
                    Object[] columnValuesPets = {petId, ownerId, petCreateDate, name, birthDate, gender, type, breed, weight, isNeutered,
                            microchipNumber, minWeight, maxWeight, dailyFeedingAmountInCups, trainingSessionInMinutes, exerciseNeedsInMinutes};
                    dbHelper.insetRecord(MyPetFriendContract.PetsEntry.TABLE_NAME, columnNamesPets, columnValuesPets);
                    dbHelper.close();

                    setResult(RESULT_OK);
                    finish();
                    Toast.makeText(getBaseContext(), "Pet Profile Created Successfully", Toast.LENGTH_SHORT).show();
//        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
//        startActivity(new Intent(this, SetupVaccines.class), bundle);
                }
            }
        });
    }

    private void forEditProfile() {
        getSupportActionBar().setTitle("Edit Pet Profile");
        deletePet_btn.setVisibility(View.VISIBLE);
        if (getIntent().getSerializableExtra("petInfo") != null) {
            myPet = (Pet) getIntent().getSerializableExtra("petInfo");
            //set the initial values to the ones has already been set to that pet object
            getSupportActionBar().setTitle("Edit " + myPet.getName() + "'s Profile");
            petName_etxt.setText(myPet.getName());
            Calendar calendarBirthDate = Calendar.getInstance();
            calendarBirthDate.setTimeInMillis(myPet.getBirthDate());
            dayPick_etxt.setText(String.valueOf(calendarBirthDate.get(Calendar.DAY_OF_MONTH)));
            monthPick_spnr.setSelection(calendarBirthDate.get(Calendar.MONTH));
            yearPick_etxt.setText(String.valueOf(calendarBirthDate.get(Calendar.YEAR)));
            genderPick_rgrb.check(myPet.getGender().equals(Pet.GENDER_MALE) ? R.id.genderMale_rbtn : R.id.genderFemale_rbtn);
            typePick_spnr.setSelection(typeAdapter.getPosition(myPet.getType()));
            petBreed_atxt.setText(myPet.getBreed());
            petWeight_etxt.setText(String.valueOf(myPet.getCurrentWeight() == -1 ? "" : myPet.getCurrentWeight()));
            isNeutered_rgrp.check(myPet.isNeutered() ? R.id.neutered_rbtn : R.id.notNeutered_rbtn);
            microchipNumber_etxt.setText(myPet.getMicrochipNumber());
            receivedBreedFromEditing = previousSelectedBreed = myPet.getBreed();

            savePet_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validatePetData()) {
                        if (!setPetSchedule(true)) {
                            Toast.makeText(AddEditPetActivity.this, "Warning: Cannot Setup Pet's Schedule Activities", Toast.LENGTH_SHORT).show();
                        } else if (!setPetVaccines(true)) {
                            Toast.makeText(AddEditPetActivity.this, "Warning: Cannot Setup Pet's Schedule Vaccinations", Toast.LENGTH_SHORT).show();
                        }
                        dbHelper.open();
                        String[] columnNames = dbHelper.getColumnNames(MyPetFriendContract.PetsEntry.TABLE_NAME);
                        Object[] columnValues = {petId, ownerId, petCreateDate, name, birthDate, gender, type, breed, weight, isNeutered, microchipNumber,
                                minWeight, maxWeight, dailyFeedingAmountInCups, trainingSessionInMinutes, exerciseNeedsInMinutes};
                        dbHelper.updateRecord(MyPetFriendContract.PetsEntry.TABLE_NAME, columnNames, columnValues, MyPetFriendContract.PetsEntry._ID, petId);
                        dbHelper.close();

                        //set the new values to myPet object to send it back to show in profile, before committing to database
                        myPet.setName(name);
                        myPet.setBirthDate(birthDate);
                        myPet.setGender(gender);
                        myPet.setType(type);
                        myPet.setBreed(breed);
                        myPet.setCurrentWeight(weight);
                        myPet.setIsNeutered(isNeutered == 1);
                        myPet.setMicrochipNumber(microchipNumber);

                        Intent intent = new Intent();
                        intent.putExtra("petModified", myPet);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }
            });
            deletePet_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showConfirmDeleteDialog();
                }
            });

        } else {
            Toast.makeText(this, "Error: Cannot Retrieve Pet Information", Toast.LENGTH_SHORT).show();
            savePet_btn.setEnabled(false);
            deletePet_btn.setEnabled(false);
        }
    }

    private boolean validatePetData() {
        //validating data
        if (petName_etxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Pet name is missing", Toast.LENGTH_SHORT).show();
        } else if (dayPick_etxt.getText().toString().trim().isEmpty() || yearPick_etxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Pet birth date is not entered correctly", Toast.LENGTH_SHORT).show();
        } else if (genderPick_rgrb.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Pet gender is missing", Toast.LENGTH_SHORT).show();
        } else if (petBreed_atxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Pet breed is missing", Toast.LENGTH_SHORT).show();
        } else {
            //assigning values
            Calendar calendar = Calendar.getInstance();
            if (myPet != null) { //if we're updating pet
                petId = myPet.getPetId();
                ownerId = myPet.getOwnerId();
                petCreateDate = myPet.getCreateDate();
            } else { //if we're creating new pet
                petId = 0;
                ownerId = 0; //TODO change this to take real petOwner id
                petCreateDate = calendar.getTimeInMillis();
            }
            name = petName_etxt.getText().toString().trim();
            calendar.set(Integer.parseInt(yearPick_etxt.getText().toString()),
                    monthPick_spnr.getSelectedItemPosition(), Integer.parseInt(dayPick_etxt.getText().toString()));
            birthDate = calendar.getTimeInMillis();
            gender = genderPick_rgrb.getCheckedRadioButtonId() == R.id.genderMale_rbtn ? Pet.GENDER_MALE : Pet.GENDER_FEMALE;
            type = typePick_spnr.getSelectedItem().toString().trim();
            breed = petBreed_atxt.getText().toString().trim();
            weight = petWeight_etxt.getText().toString().isEmpty() ? -1 : Integer.parseInt(petWeight_etxt.getText().toString());
            isNeutered = isNeutered_rgrp.getCheckedRadioButtonId() == -1 ? -1 :
                    (isNeutered_rgrp.getCheckedRadioButtonId() == R.id.neutered_rbtn ? Pet.NEUTERED : Pet.NOT_NEUTERED);
            microchipNumber = microchipNumber_etxt.getText().toString().trim();

            return true;
        }
        return false;
    }

    //Read pet information from "storedPetBreeds" based on type&breed. and then calculate its recommended schedule and save it to db "petScheduleActivities"
    private boolean setPetSchedule(boolean isEditedRecord) {
        dbHelper.open();
        //if we're editing current pet, first we delete its saved schedule, and then set the new schedule  for the new situation like normal
        if (isEditedRecord) {
            //if user didn't change type & breed, stop from doing all the following stuff
            if (previousSelectedBreed.equals(breed)) {
                dbHelper.close();
                return true;
            }
            dbHelper.deleteRecord(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME, MyPetFriendContract.PetScheduleActivitiesEntry.PET_ID, String.valueOf(petId));
        }
        String[] columnNamesScheduleActivities = dbHelper.getColumnNames(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME);

        if (type.equals(Pet.TYPE_CAT)) {
            //first read from storedCatBreeds data related to chosen breed
            cursor = dbHelper.getRecord(MyPetFriendContract.StoredCatBreedsEntry.TABLE_NAME, null, MyPetFriendContract.StoredCatBreedsEntry.NAME, breed);
            if (cursor.moveToFirst()) {
                //---Retrieve and calculate frequencies and needed values---
                dailyFeedingAmountInCups = cursor.getInt(2);
                minWeight = cursor.getInt(5);
                maxWeight = cursor.getInt(6);
                trainingSessionInMinutes = 0;
                exerciseNeedsInMinutes = 0;
                int generalHealthLevel = cursor.getInt(4);
                int breakfastFrequency = 1, dinnerFrequency = 1, birthdayFrequency = 365,
                        groomingFrequency = cursor.getInt(3) * 2, bathingFrequency = cursor.getInt(3) * 30,
                        teethBrushingFrequency = 2, nailTrimmingFrequency = 14,
                        medicalCheckupFrequency, weighingFrequency;
                cursor.close();

                //---Calculate medical checkups---
                if (getPetAgeInYear(birthDate) <= 1) {
                    medicalCheckupFrequency = 30; //young pets need frequent medical checkups (once a month)
                } else if (getPetAgeInYear(birthDate) > 10) {
                    medicalCheckupFrequency = generalHealthLevel * 122; //if senior pet, double the medical checkups
                } else {
                    medicalCheckupFrequency = generalHealthLevel * 244; //either once in 0.6 year / once in 1.3 year / once in 2 years
                }
                //---Calculate weighing frequency and daily feeding amount---
                if(weight < minWeight){
                    weighingFrequency = 21;
                    dailyFeedingAmountInCups *= 1.2;
                } else if(weight > maxWeight){
                    weighingFrequency = 21;
                    dailyFeedingAmountInCups *= .8;
                } else {
                    weighingFrequency = 90;
                }

                //set the pet schedule activities after getting required values (vaccines are added in next method)
                String[] catScheduleActivityTypes = {TYPE_BREAKFAST, TYPE_DINNER, TYPE_GROOMING, TYPE_BATHING,
                        TYPE_TEETH_BRUSHING, TYPE_NAIL_TRIMMING, TYPE_WEIGHING, TYPE_MEDICAL_CHECKUP, TYPE_BIRTHDAY};
                int[] catScheduleActivityFrequencies = {breakfastFrequency, dinnerFrequency, groomingFrequency, bathingFrequency,
                        teethBrushingFrequency, nailTrimmingFrequency, weighingFrequency, medicalCheckupFrequency, birthdayFrequency};
                int[] catScheduleActivityTimes = {8, 20, 15, 15, 21, 21, 9, 11, 0};
                for(int i = 0; i < catScheduleActivityTypes.length; i++) {
                    dbHelper.insetRecord(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME, columnNamesScheduleActivities,
                            new Object[]{0, petId, catScheduleActivityTypes[i], birthDate, catScheduleActivityFrequencies[i],
                                    catScheduleActivityTimes[i], 0, "notes", NOTIFICATION_STATUS_ON});
                }
                return true;
            } else {
                cursor.close();
                return false;
            }
        }

        else if (type.equals(Pet.TYPE_DOG)) {
            cursor = dbHelper.getRecord(MyPetFriendContract.StoredDogBreedsEntry.TABLE_NAME, null, MyPetFriendContract.StoredDogBreedsEntry.NAME, breed);
            if (cursor.moveToFirst()) {
                //---Retrieve and calculate frequencies and needed values---
                dailyFeedingAmountInCups = cursor.getInt(2);
                trainingSessionInMinutes = 30 / cursor.getInt(5); //either 30 or 15 or 10 minutes based on dog's trainability
                exerciseNeedsInMinutes = cursor.getInt(6) * 30; //either 30 or 60 or 90 minutes based on dog's exercise needs
                minWeight = cursor.getInt(7);
                maxWeight = cursor.getInt(8);
                int generalHealthLevel = cursor.getInt(4);
                int breakfastFrequency = 1, dinnerFrequency = 1, birthdayFrequency = 365,
                        groomingFrequency = cursor.getInt(3) * 2, bathingFrequency = cursor.getInt(3) * 12,
                        teethBrushingFrequency = 2, nailTrimmingFrequency = 14,
                        trainingFrequency = 3, exerciseFrequency = 1,
                        medicalCheckupFrequency, weighingFrequency;
                cursor.close();

                //---Calculate medical checkups---
                if (getPetAgeInYear(birthDate) <= 1) {
                    medicalCheckupFrequency = 30; //young pets need frequent medical checkups (once a month)
                } else if (getPetAgeInYear(birthDate) > 7) {
                    medicalCheckupFrequency = generalHealthLevel * 122; //if senior pet, double the medical checkups
                } else {
                    medicalCheckupFrequency = generalHealthLevel * 244; //either once in 0.6 year / once in 1.3 year / once in 2 years
                }
                //---Calculate weighing frequency and daily feeding amount---
                if(weight < minWeight){
                    weighingFrequency = 14;
                    dailyFeedingAmountInCups *= 1.25;
                } else if(weight > maxWeight){
                    weighingFrequency = 14;
                    dailyFeedingAmountInCups *= .75;
                } else {
                    weighingFrequency = 45;
                }

                //set the pet schedule activities after getting required values (vaccines are added in next method)
                String[] dogScheduleActivityTypes = {TYPE_BREAKFAST, TYPE_DINNER, TYPE_GROOMING, TYPE_BATHING, TYPE_TEETH_BRUSHING,
                        TYPE_NAIL_TRIMMING, TYPE_EXERCISING, TYPE_TRAINING, TYPE_WEIGHING, TYPE_MEDICAL_CHECKUP, TYPE_BIRTHDAY};
                int[] dogScheduleActivityFrequencies = {breakfastFrequency, dinnerFrequency, groomingFrequency, bathingFrequency, teethBrushingFrequency,
                        nailTrimmingFrequency, exerciseFrequency, trainingFrequency, weighingFrequency, medicalCheckupFrequency, birthdayFrequency};
                int[] dogScheduleActivityTimes = {8, 20, 15, 15, 21, 21, 18, 17, 9, 11, 0};
                for(int i = 0; i < dogScheduleActivityTypes.length; i++) {
                    dbHelper.insetRecord(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME, columnNamesScheduleActivities,
                            new Object[]{0, petId, dogScheduleActivityTypes[i], birthDate, dogScheduleActivityFrequencies[i],
                                    dogScheduleActivityTimes[i], 0, "notes", NOTIFICATION_STATUS_ON});
                }
                return true;
            } else {
                cursor.close();
                return false;
            }
        }

        dbHelper.close();
        return false;
    }

    private boolean setPetVaccines(boolean isEditedRecord) {
        dbHelper.open();
        //if we're editing current pet, first we delete its saved vaccines, and then set the new vaccines like normal
        if (isEditedRecord) {
            //if user didn't change type & breed, stop from doing all the following stuff
            if (previousSelectedBreed.equals(breed)) {
                dbHelper.close();
                return true;
            }
            dbHelper.deleteRecord(MyPetFriendContract.PetVaccinesEntry.TABLE_NAME, MyPetFriendContract.PetVaccinesEntry.PET_ID, String.valueOf(petId));
        }
        String[] columnNamesVaccines = dbHelper.getColumnNames(MyPetFriendContract.PetVaccinesEntry.TABLE_NAME);
        String[] columnNamesScheduleActivities = dbHelper.getColumnNames(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME);
        cursor = dbHelper.getRecord(MyPetFriendContract.StoredVaccinesEntry.TABLE_NAME, null, MyPetFriendContract.StoredVaccinesEntry.PET_TYPE, type);
        if (!cursor.moveToFirst()) {
            dbHelper.close();
            return false;
        }
        while (cursor.moveToNext()) {
            if(dbHelper.insetRecord(MyPetFriendContract.PetVaccinesEntry.TABLE_NAME, columnNamesVaccines,
                    new Object[]{0, petId, cursor.getString(2), cursor.getInt(3), birthDate, (cursor.getInt(4) * 365), cursor.getString(5)}) == -1
                    || dbHelper.insetRecord(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME, columnNamesScheduleActivities,
                    new Object[]{0, petId, TYPE_VACCINATION, birthDate, (cursor.getInt(4) * 365), 11, 0, cursor.getString(5), NOTIFICATION_STATUS_ON}) == -1){
                dbHelper.close();
                return false;
            }
        }
        dbHelper.close();
        return true;
    }

    private void showConfirmDeleteDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete " + myPet.getName() + "'s profile and all its associated data?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDBHelper dbHelper = new MyDBHelper(getBaseContext());
                        dbHelper.open();
                        dbHelper.deleteRecord(MyPetFriendContract.PetsEntry.TABLE_NAME, MyPetFriendContract.PetsEntry._ID, String.valueOf(myPet.getPetId()));
                        dbHelper.deleteRecord(MyPetFriendContract.PetPhotosEntry.TABLE_NAME, MyPetFriendContract.PetPhotosEntry.PET_ID, String.valueOf(myPet.getPetId()));
                        dbHelper.deleteRecord(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME, MyPetFriendContract.PetScheduleActivitiesEntry.PET_ID, String.valueOf(myPet.getPetId()));
                        dbHelper.deleteRecord(MyPetFriendContract.PetVaccinesEntry.TABLE_NAME, MyPetFriendContract.PetVaccinesEntry.PET_ID, String.valueOf(myPet.getPetId()));
                        dbHelper.deleteRecord(MyPetFriendContract.PetWeightListEntry.TABLE_NAME, MyPetFriendContract.PetWeightListEntry.PET_ID, String.valueOf(myPet.getPetId()));
                        dbHelper.close();

                        Intent intent = new Intent();
                        intent.putExtra("petDeleted", true);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which){
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public int getPetAgeInYear(long birthDate) {
        Calendar birth = Calendar.getInstance();
        birth.setTimeInMillis(birthDate);
        return Calendar.getInstance().get(Calendar.YEAR) - birth.get(Calendar.YEAR);
    }

    private void initializeMyViews() {
        uploadPhoto_btn = (ImageButton) findViewById(R.id.uploadPhoto_btn);
        petName_etxt = (EditText) findViewById(R.id.petName_etxt);
        dayPick_etxt = (EditText) findViewById(R.id.dayPick_etxt);
        monthPick_spnr = (Spinner) findViewById(R.id.monthPick_spnr);
        yearPick_etxt = (EditText) findViewById(R.id.yearPick_etxt);
        genderPick_rgrb = (RadioGroup) findViewById(R.id.genderPick_rgrb);
        petWeight_etxt = (EditText) findViewById(R.id.petWeight_etxt);
        isNeutered_rgrp = (RadioGroup) findViewById(R.id.isNeutered_rgrp);
        microchipNumber_etxt = (EditText) findViewById(R.id.microchipNumber_etxt);
        savePet_btn = (Button) findViewById(R.id.savePetProfile_btn);
        deletePet_btn = (Button) findViewById(R.id.deletePet_btn);

        typePick_spnr = (Spinner) findViewById(R.id.typePick_spnr);
        typeAdapter = new ArrayAdapter<> (this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.pet_types));
        typePick_spnr.setAdapter(typeAdapter);

        petBreed_atxt = (AutoCompleteTextView) findViewById(R.id.petBreed_atxt);
        petBreed_atxt.setThreshold(1);
        if (availableCatBreeds.isEmpty()) {
            dbHelper.open();
            cursor = dbHelper.getAllRecords(MyPetFriendContract.StoredCatBreedsEntry.TABLE_NAME,
                    new String[]{MyPetFriendContract.StoredCatBreedsEntry.NAME});
            while (cursor.moveToNext()) {
                availableCatBreeds.add(cursor.getString(0));
            }
            cursor.close();
            dbHelper.close();
        }
        breedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, availableCatBreeds);
        petBreed_atxt.setAdapter(breedAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
