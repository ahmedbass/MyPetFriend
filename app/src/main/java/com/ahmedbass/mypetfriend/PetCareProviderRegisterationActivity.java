package com.ahmedbass.mypetfriend;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class PetCareProviderRegisterationActivity extends AppCompatActivity {

    ImageButton uploadPhoto_btn;
    EditText profileDescription_etxt, phoneNumber_etxt;
    Spinner availability_spnr, experienceYears_spnr, averageRatePerHour_spnr;
    CheckBox catService_chk, dogService_chk, smallAnimalService_chk, horseService_chk, birdService_chk, fishService_chk,
            petSitting_chk, petWalking_chk, petGrooming_chk, petTraining_chk, petVeterinary_chk, petBoarding_chk;
    Button saveProfile_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_care_provider_registeration);

        initializeMyView();
    }

    //when clicking save button
    public void saveExtendedRegistration(View view) {
        if(profileDescription_etxt.getText().toString().isEmpty() || phoneNumber_etxt.getText().toString().isEmpty()
                || !(catService_chk.isChecked() || dogService_chk.isChecked() || horseService_chk.isChecked()
                || birdService_chk.isChecked() || fishService_chk.isChecked() || smallAnimalService_chk.isChecked())
                || !(petSitting_chk.isChecked() || petWalking_chk.isChecked() || petGrooming_chk.isChecked()
                || petTraining_chk.isChecked() || petVeterinary_chk.isChecked() || petBoarding_chk.isChecked())) {
            showConfirmSkipDialog();
        } else {
            register();
        }
    }

    private void showConfirmSkipDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Your profile is not complete yet. Are you sure you want to skip?")
                .setMessage("remember, you can edit your profile any time in settings.")
                .setPositiveButton("Skip For Now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        register();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void register() {
        String taskType = "register";
        BackgroundWorker backgroundWorker = new BackgroundWorker(PetCareProviderRegisterationActivity.this);
        backgroundWorker.execute(taskType, String.valueOf(System.currentTimeMillis()),
                getIntent().getStringExtra("userType"), getIntent().getStringExtra("firstName"), getIntent().getStringExtra("lastName"),
                getIntent().getStringExtra("email"), getIntent().getStringExtra("password"), String.valueOf(getIntent().getLongExtra("birthDate", 0)),
                getIntent().getStringExtra("gender"), getIntent().getStringExtra("country"), getIntent().getStringExtra("city"),
                phoneNumber_etxt.getText().toString(), "", profileDescription_etxt.getText().toString(), availability_spnr.getSelectedItem().toString(),
                experienceYears_spnr.getSelectedItem().toString(), averageRatePerHour_spnr.getSelectedItem().toString(),
                setServiceProvidedFor(), setServiceProvided());
    }

    private String setServiceProvidedFor() {
        String serviceProvidedFor = "";
        if (catService_chk.isChecked()) {
            serviceProvidedFor += "Cats, ";
        }
        if (dogService_chk.isChecked()) {
            serviceProvidedFor += "Dogs, ";
        }
        if (horseService_chk.isChecked()) {
            serviceProvidedFor += "Horses, ";
        }
        if (birdService_chk.isChecked()) {
            serviceProvidedFor += "Birds, ";
        }
        if (fishService_chk.isChecked()) {
            serviceProvidedFor += "Fish, ";
        }
        if (smallAnimalService_chk.isChecked()) {
            serviceProvidedFor += "Small Animals, ";
        }

        if(serviceProvidedFor.trim().length() > 0) {
            return serviceProvidedFor.substring(0, serviceProvidedFor.length() - 2); //this substring just to remove the last ', '
        } else {
            return serviceProvidedFor.trim();
        }
    }

    private String setServiceProvided() {
        String serviceProvided = "";
        if (petSitting_chk.isChecked()) {
            serviceProvided += "Pet Sitting, ";
        }
        if (petWalking_chk.isChecked()) {
            serviceProvided += "Pet Walking, ";
        }
        if (petGrooming_chk.isChecked()) {
            serviceProvided += "Pet Grooming, ";
        }
        if (petTraining_chk.isChecked()) {
            serviceProvided += "Pet Training, ";
        }
        if (petVeterinary_chk.isChecked()) {
            serviceProvided += "Veterinary, ";
        }
        if (petBoarding_chk.isChecked()) {
            serviceProvided += "Pet Boarding, ";
        }

        if(serviceProvided.trim().length() > 0) {
            return serviceProvided.substring(0, serviceProvided.length() - 2); //this substring just to remove the last ', '
        } else {
            return serviceProvided.trim();
        }
    }

    private void initializeMyView() {
        profileDescription_etxt = (EditText) findViewById(R.id.profileDescription_etxt);
        phoneNumber_etxt = (EditText) findViewById(R.id.phoneNumber_etxt);
        availability_spnr = (Spinner) findViewById(R.id.availability_spnr);
        experienceYears_spnr = (Spinner) findViewById(R.id.experienceYears_spnr);
        averageRatePerHour_spnr = (Spinner) findViewById(R.id.averageRatePerHour_spnr);
        catService_chk = (CheckBox)findViewById(R.id.catService_chk);
        dogService_chk = (CheckBox)findViewById(R.id.dogService_chk);
        smallAnimalService_chk = (CheckBox)findViewById(R.id.smallAnimalService_chk);
        horseService_chk = (CheckBox)findViewById(R.id.horseService_chk);
        birdService_chk = (CheckBox)findViewById(R.id.birdService_chk);
        fishService_chk = (CheckBox)findViewById(R.id.fishService_chk);
        petSitting_chk = (CheckBox)findViewById(R.id.petSitting_chk);
        petWalking_chk = (CheckBox)findViewById(R.id.petWalking_chk);
        petGrooming_chk = (CheckBox)findViewById(R.id.petGrooming_chk);
        petTraining_chk = (CheckBox)findViewById(R.id.petTraining_chk);
        petVeterinary_chk = (CheckBox)findViewById(R.id.petVeterinary_chk);
        petBoarding_chk = (CheckBox)findViewById(R.id.petBoarding_chk);
        saveProfile_btn = (Button) findViewById(R.id.saveProfile_btn);
    }
}
