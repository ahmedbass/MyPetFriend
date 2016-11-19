package com.ahmedbass.mypetfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class AddEditPetActivity extends AppCompatActivity {

    ImageButton uploadPhoto_btn;
    EditText petName_etxt, dayPick_etxt, yearPick_etxt, petWeight_etxt, microchipNumber_etxt;
    Spinner monthPick_spnr, typePick_spnr;
    RadioGroup genderPick_rgrb, isNeutered_rgrp;
    AutoCompleteTextView petBreed_atxt;
    ArrayAdapter<String> adapter;

    Pet myPet;

    String name, gender, type, breed, microchipNumber;
    long petCreateDate, birthDate;
    int petId, ownerId, weight, isNeutered;

    int scheduleActivityId, scheduleCreateDate, hourOfDay, minuteOfDay, frequency, notificationStatus;
    String scheduleType, notes;

    Button savePet_btn, deletePet_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_pet);

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

    }

    private void forAddProfile() {
        findViewById(R.id.uploadPhoto_lout).setVisibility(View.VISIBLE);
        deletePet_btn.setVisibility(View.GONE);
        savePet_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //first validate and set values to variables then come back and write them to database
                if(validateAndAssignData()) {
                    MyDBHelper dbHelper = new MyDBHelper(getBaseContext());
                    dbHelper.open();
                    String[] columnNamesPets = dbHelper.getColumnNames(MyPetFriendContract.PetsEntry.TABLE_NAME);
                    Object[] columnValues = {petId, ownerId, petCreateDate, name, birthDate, gender, type, breed, weight, isNeutered, microchipNumber};
                    dbHelper.insetRecord(MyPetFriendContract.PetsEntry.TABLE_NAME, columnNamesPets, columnValues);

                    String[] columnNamesScheduleActivities = dbHelper.getColumnNames(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME);
                    Object[] columnValuesScheduleActivities = {scheduleActivityId, petId, scheduleType, scheduleCreateDate, hourOfDay, minuteOfDay, frequency, notes, notificationStatus};


                    dbHelper.close();

                    setResult(RESULT_OK);
                    finish();

//        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
//        startActivity(new Intent(this, SetupVaccines.class), bundle);
                }
            }
        });
    }

    private void forEditProfile() {
        getSupportActionBar().setTitle("Edit Pet Profile");
        findViewById(R.id.uploadPhoto_lout).setVisibility(View.GONE);
        deletePet_btn.setVisibility(View.VISIBLE);
        if (getIntent().getSerializableExtra("petInfo") != null) {
            myPet = (Pet) getIntent().getSerializableExtra("petInfo");
            //set the initial values to the ones has already been set to that pet object
            getSupportActionBar().setTitle("Edit " + myPet.getName() + "'s Profile");
            petName_etxt.setText(myPet.getName());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(myPet.getBirthDate());
            dayPick_etxt.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            monthPick_spnr.setSelection(calendar.get(Calendar.MONTH));
            yearPick_etxt.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            genderPick_rgrb.check(myPet.getGender().equals(MyPetFriendContract.PetsEntry.GENDER_MALE) ? R.id.genderMale_rbtn : R.id.genderFemale_rbtn);
            typePick_spnr.setSelection(adapter.getPosition(myPet.getType()));
            petBreed_atxt.setText(myPet.getBreed());
            petWeight_etxt.setText(String.valueOf(myPet.getCurrentWeight() == -1 ? "" : myPet.getCurrentWeight()));
            isNeutered_rgrp.check(myPet.isNeutered() ? R.id.neutered_rbtn : R.id.notNeutered_rbtn);
            microchipNumber_etxt.setText(myPet.getMicrochipNumber());

            deletePet_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(AddEditPetActivity.this, "Are you sure you want to delete " + myPet.getName() + " profile and all it's data?" +
                            "\nThis action cannot be undone.", Toast.LENGTH_SHORT).show();
                    MyDBHelper dbHelper = new MyDBHelper(getBaseContext());
                    dbHelper.open();
                    dbHelper.deleteRecord(MyPetFriendContract.PetsEntry.TABLE_NAME, MyPetFriendContract.PetsEntry._ID, myPet.getPetId());
                    dbHelper.deleteRecord(MyPetFriendContract.PetPhotosEntry.TABLE_NAME, MyPetFriendContract.PetPhotosEntry.PET_ID, myPet.getPetId());
                    dbHelper.close();

                    Intent intent = new Intent();
                    intent.putExtra("petDeleted", true);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });

            savePet_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validateAndAssignData()) {
                        MyDBHelper dbHelper = new MyDBHelper(getBaseContext());
                        dbHelper.open();
                        String[] columnNames = dbHelper.getColumnNames(MyPetFriendContract.PetsEntry.TABLE_NAME);
                        Object[] columnValues = {petId, ownerId, petCreateDate, name, birthDate, gender, type, breed, weight, isNeutered, microchipNumber};
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

        } else {
            Toast.makeText(this, "Error: Cannot find pet", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validateAndAssignData() {
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
            if (myPet != null) {
                petId = myPet.getPetId();
                ownerId = myPet.getOwnerId();
                petCreateDate = myPet.getCreateDate();
            } else {
                petId = 0;
                ownerId = 0; //TODO change this to take the real user id
                petCreateDate = calendar.getTimeInMillis();
            }
            name = petName_etxt.getText().toString().trim();
            calendar.set(Integer.parseInt(yearPick_etxt.getText().toString()),
                    monthPick_spnr.getSelectedItemPosition(), Integer.parseInt(dayPick_etxt.getText().toString()));
            birthDate = calendar.getTimeInMillis();
            gender = genderPick_rgrb.getCheckedRadioButtonId() == R.id.genderMale_rbtn ?
                    MyPetFriendContract.PetsEntry.GENDER_MALE : MyPetFriendContract.PetsEntry.GENDER_FEMALE;
            type = typePick_spnr.getSelectedItem().toString().trim();
            breed = petBreed_atxt.getText().toString().trim();
            weight = petWeight_etxt.getText().toString().isEmpty() ? -1 : Integer.parseInt(petWeight_etxt.getText().toString());
            isNeutered = isNeutered_rgrp.getCheckedRadioButtonId() == -1 ? -1 :
                    (isNeutered_rgrp.getCheckedRadioButtonId() == R.id.neutered_rbtn ? MyPetFriendContract.PetsEntry.NEUTERED : MyPetFriendContract.PetsEntry.NOT_NEUTERED);
            microchipNumber = microchipNumber_etxt.getText().toString().trim();

            setPetSchedule();

            return true;
        }
        return false;
    }

    private void setPetSchedule() {

    }

    private void initializeMyViews() {
        uploadPhoto_btn = (ImageButton) findViewById(R.id.uploadPhoto_btn);
        petName_etxt = (EditText) findViewById(R.id.petName_etxt);
        dayPick_etxt = (EditText) findViewById(R.id.dayPick_etxt);
        monthPick_spnr = (Spinner) findViewById(R.id.monthPick_spnr);
        yearPick_etxt = (EditText) findViewById(R.id.yearPick_etxt);
        genderPick_rgrb = (RadioGroup) findViewById(R.id.genderPick_rgrb);
        petBreed_atxt = (AutoCompleteTextView) findViewById(R.id.petBreed_atxt);
        petWeight_etxt = (EditText) findViewById(R.id.petWeight_etxt);
        isNeutered_rgrp = (RadioGroup) findViewById(R.id.isNeutered_rgrp);
        microchipNumber_etxt = (EditText) findViewById(R.id.microchipNumber_etxt);
        savePet_btn = (Button) findViewById(R.id.savePetProfile_btn);
        deletePet_btn = (Button) findViewById(R.id.deletePet_btn);

        typePick_spnr = (Spinner) findViewById(R.id.typePick_spnr);
        adapter = new ArrayAdapter<> (this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.pet_types));
        typePick_spnr.setAdapter(adapter);
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
