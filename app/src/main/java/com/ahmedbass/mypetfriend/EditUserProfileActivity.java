package com.ahmedbass.mypetfriend;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.ahmedbass.mypetfriend.MyPetFriendContract.UsersEntry;

import java.util.Calendar;

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;
import static com.ahmedbass.mypetfriend.R.array.availability;

public class EditUserProfileActivity extends AppCompatActivity {

    ImageButton uploadPhoto_btn;
    AutoCompleteTextView country_etxt, city_etxt;
    EditText firstName_etxt, lastName_etxt, email_etxt, password_etxt,
            dayPick_etxt, yearPick_etxt, phoneNumber_etxt, profileDescription_etxt;
    Spinner monthPick_spnr, availability_spnr, experienceYears_spnr, averageRatePerHour_spnr;
    RadioGroup gender_rgrp, usertype_rgrp;
    RadioButton genderMale_rbtn, genderFemale_rbtn, petOwner_rbtn, petCareProvider_rbtn;
    CheckBox catService_chk, dogService_chk, smallAnimalService_chk, horseService_chk, birdService_chk, fishService_chk,
            petSitting_chk, petWalking_chk, petGrooming_chk, petTraining_chk, petVeterinary_chk, petBoarding_chk;
    LinearLayout profileDescription_lout, experienceAndSalary_lout, additionalInformation_lout;
    ArrayAdapter<String> availabilityAdapter, averageRateAdpter;
    Calendar calendarBirthDate;

    SharedPreferences preferences;
    boolean isClickedEditButton, isPasswordEditing;
    String oldPassword;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        preferences = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE);
        initializeMyViews();
        setUserInfoInViews();

        dayPick_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!dayPick_etxt.getText().toString().isEmpty()) {
                    if (Integer.parseInt(dayPick_etxt.getText().toString()) < 1) {
                        dayPick_etxt.setText("1");
                    } else if (Integer.parseInt(dayPick_etxt.getText().toString()) > 31) {
                        dayPick_etxt.setText("31");
                    }
                }
            }
        });
        yearPick_etxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!yearPick_etxt.getText().toString().isEmpty()) {
                    if (Integer.parseInt(yearPick_etxt.getText().toString()) > Calendar.getInstance().get(Calendar.YEAR)) {
                        yearPick_etxt.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
                    }
                }
            }
        });
        yearPick_etxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (!isFocused) {
                    if (!yearPick_etxt.getText().toString().isEmpty() && Integer.parseInt(yearPick_etxt.getText().toString()) < 1900) {
                        yearPick_etxt.setText("1900");
                    }
                }
            }
        });

        usertype_rgrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                TransitionManager.beginDelayedTransition((ViewGroup) radioGroup.getRootView());
                switch (checkedId) {
                    case R.id.petOwner_rbtn:
                        profileDescription_lout.setVisibility(View.GONE);
                        experienceAndSalary_lout.setVisibility(View.GONE);
                        additionalInformation_lout.setVisibility(View.GONE);
                        break;
                    case R.id.petCareProvider_rbtn:
                        profileDescription_lout.setVisibility(View.VISIBLE);
                        experienceAndSalary_lout.setVisibility(View.VISIBLE);
                        additionalInformation_lout.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        email_etxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstName_etxt.isEnabled()) {
                    Toast.makeText(getBaseContext(), "Sorry, you cannot change the email you have signed up with", Toast.LENGTH_SHORT).show();
                }
            }
        });

        password_etxt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if (isFocused) {
                    oldPassword = password_etxt.getText().toString();
                    isPasswordEditing = true;
                }
                if (!isFocused) {
                    isPasswordEditing = false;
                    if (!password_etxt.getText().toString().equals(oldPassword)) {
                        confirmNewPasswordDialog(oldPassword);
                    }
                }
            }
        });
    }

    private void confirmNewPasswordDialog(final String oldPassword) {
        final EditText newPassword_etxt = new EditText(this);
        newPassword_etxt.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        new AlertDialog.Builder(this).setTitle("Please re-enter your new password")
                .setView(newPassword_etxt)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (password_etxt.getText().toString().equals(newPassword_etxt.getText().toString())) {
                            if (password_etxt.getText().toString().length() < 6 || password_etxt.getText().toString().length() > 20) {
                                Toast.makeText(getBaseContext(), "Please enter password between 6 and 20 characters", Toast.LENGTH_SHORT).show();
                                password_etxt.setText(oldPassword);
                            } else {
                                password_etxt.setText(newPassword_etxt.getText());
                            }
                        } else {
                            Toast.makeText(EditUserProfileActivity.this, "Password is not matching. Please try again", Toast.LENGTH_SHORT).show();
                            password_etxt.setText(oldPassword);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        password_etxt.setText(oldPassword);
                        Toast.makeText(EditUserProfileActivity.this, "Password has not changed", Toast.LENGTH_SHORT).show();
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    //when first start activity, set the user information in the fields
    private void setUserInfoInViews() {
        firstName_etxt.setText(preferences.getString(UsersEntry.COLUMN_FIRST_NAME, ""));
        lastName_etxt.setText(preferences.getString(UsersEntry.COLUMN_LAST_NAME, ""));
        email_etxt.setText(preferences.getString(UsersEntry.COLUMN_EMAIL, ""));
        password_etxt.setText(preferences.getString(UsersEntry.COLUMN_PASSWORD, ""));
        country_etxt.setText(preferences.getString(UsersEntry.COLUMN_COUNTRY, ""));
        city_etxt.setText(preferences.getString(UsersEntry.COLUMN_CITY, ""));
        calendarBirthDate = Calendar.getInstance();
        calendarBirthDate.setTimeInMillis(preferences.getLong(UsersEntry.COLUMN_BIRTH_DATE, 0));
        dayPick_etxt.setText(String.valueOf(calendarBirthDate.get(Calendar.DAY_OF_MONTH)));
        monthPick_spnr.setSelection(calendarBirthDate.get(Calendar.MONTH));
        yearPick_etxt.setText(String.valueOf(calendarBirthDate.get(Calendar.YEAR)));
        gender_rgrp.check((preferences.getString(UsersEntry.COLUMN_GENDER, "").equals(UsersEntry.GENDER_FEMALE) ?
                genderFemale_rbtn.getId() : genderMale_rbtn.getId()));
        usertype_rgrp.check((preferences.getString(UsersEntry.COLUMN_USER_TYPE, "").equals(UsersEntry.USER_TYPE_PET_CARE_PROVIDER) ?
                petCareProvider_rbtn.getId() : petOwner_rbtn.getId()));
        phoneNumber_etxt.setText(preferences.getString(UsersEntry.COLUMN_PHONE, ""));

        // if userType == PetCareProvider do the following. otherwise, hide the unnecessary fields
        if (preferences.getString(UsersEntry.COLUMN_USER_TYPE, "").equals(UsersEntry.USER_TYPE_PET_CARE_PROVIDER)) {
            profileDescription_etxt.setText(preferences.getString(UsersEntry.COLUMN_PROFILE_DESCRIPTION, ""));
            availability_spnr.setSelection(availabilityAdapter.getPosition(preferences.getString(UsersEntry.COLUMN_AVAILABILITY, "")));
            experienceYears_spnr.setSelection(Integer.parseInt(preferences.getString(UsersEntry.COLUMN_YEARS_OF_EXPERIENCE, "0").equals("10+") ?
                    "10" : preferences.getString(UsersEntry.COLUMN_YEARS_OF_EXPERIENCE, "0")));
            averageRatePerHour_spnr.setSelection(averageRateAdpter.getPosition(preferences.getString(UsersEntry.COLUMN_AVERAGE_RATE_PER_HOUR, "10")));

            String[] serviceProvidedFor = preferences.getString(UsersEntry.COLUMN_SERVICE_PROVIDED_FOR, "").split(",");
            String[] serviceProvided = preferences.getString(UsersEntry.COLUMN_SERVICE_PROVIDED, "").split(",");

            catService_chk.setChecked(arrayContains(serviceProvidedFor, "Cats"));
            dogService_chk.setChecked(arrayContains(serviceProvidedFor, "Dogs"));
            horseService_chk.setChecked(arrayContains(serviceProvidedFor, "Horses"));
            birdService_chk.setChecked(arrayContains(serviceProvidedFor, "Birds"));
            fishService_chk.setChecked(arrayContains(serviceProvidedFor, "Fish"));
            smallAnimalService_chk.setChecked(arrayContains(serviceProvidedFor, "Small Animals"));
            petSitting_chk.setChecked(arrayContains(serviceProvided, "Pet Sitting"));
            petWalking_chk.setChecked(arrayContains(serviceProvided, "Pet Walking"));
            petGrooming_chk.setChecked(arrayContains(serviceProvided, "Pet Grooming"));
            petTraining_chk.setChecked(arrayContains(serviceProvided, "Pet Training"));
            petVeterinary_chk.setChecked(arrayContains(serviceProvided, "Veterinary"));
            petBoarding_chk.setChecked(arrayContains(serviceProvided, "Pet Boarding"));

        } else {
            profileDescription_lout.setVisibility(View.GONE);
            experienceAndSalary_lout.setVisibility(View.GONE);
            additionalInformation_lout.setVisibility(View.GONE);
        }
    }

    //method for checking the checkboxes based on what petCareProvider has set in their serviceProvided/For
    private boolean arrayContains(String[] array, String key) {
        for (String arrayElement : array) {
            if (arrayElement.contains(key)) {
                return true;
            }
        }
        return false;
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

        if (serviceProvidedFor.trim().length() > 0) {
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

        if (serviceProvided.trim().length() > 0) {
            return serviceProvided.substring(0, serviceProvided.length() - 2); //this substring just to remove the last ', '
        } else {
            return serviceProvided.trim();
        }
    }

    private void initializeMyViews() {
        uploadPhoto_btn = (ImageButton) findViewById(R.id.uploadPhoto_btn);
        firstName_etxt = (EditText) findViewById(R.id.firstName_etxt);
        lastName_etxt = (EditText) findViewById(R.id.lastName_etxt);
        email_etxt = (EditText) findViewById(R.id.email_etxt);
        password_etxt = (EditText) findViewById(R.id.password_etxt);
        country_etxt = (AutoCompleteTextView) findViewById(R.id.country_etxt);
        city_etxt = (AutoCompleteTextView) findViewById(R.id.city_etxt);
        dayPick_etxt = (EditText) findViewById(R.id.dayPick_etxt);
        yearPick_etxt = (EditText) findViewById(R.id.yearPick_etxt);
        phoneNumber_etxt = (EditText) findViewById(R.id.phoneNumber_etxt);
        profileDescription_etxt = (EditText) findViewById(R.id.profileDescription_etxt);
        monthPick_spnr = (Spinner) findViewById(R.id.monthPick_spnr);
        availability_spnr = (Spinner) findViewById(R.id.availability_spnr);
        experienceYears_spnr = (Spinner) findViewById(R.id.experienceYears_spnr);
        averageRatePerHour_spnr = (Spinner) findViewById(R.id.averageRatePerHour_spnr);
        gender_rgrp = (RadioGroup) findViewById(R.id.gender_rgrp);
        usertype_rgrp = (RadioGroup) findViewById(R.id.userType_rgrp);
        genderMale_rbtn = (RadioButton) findViewById(R.id.genderMale_rbtn);
        genderFemale_rbtn = (RadioButton) findViewById(R.id.genderFemale_rbtn);
        petOwner_rbtn = (RadioButton) findViewById(R.id.petOwner_rbtn);
        petCareProvider_rbtn = (RadioButton) findViewById(R.id.petCareProvider_rbtn);
        catService_chk = (CheckBox) findViewById(R.id.catService_chk);
        dogService_chk = (CheckBox) findViewById(R.id.dogService_chk);
        horseService_chk = (CheckBox) findViewById(R.id.horseService_chk);
        birdService_chk = (CheckBox) findViewById(R.id.birdService_chk);
        fishService_chk = (CheckBox) findViewById(R.id.fishService_chk);
        smallAnimalService_chk = (CheckBox) findViewById(R.id.smallAnimalService_chk);
        petSitting_chk = (CheckBox) findViewById(R.id.petSitting_chk);
        petWalking_chk = (CheckBox) findViewById(R.id.petWalking_chk);
        petGrooming_chk = (CheckBox) findViewById(R.id.petGrooming_chk);
        petTraining_chk = (CheckBox) findViewById(R.id.petTraining_chk);
        petVeterinary_chk = (CheckBox) findViewById(R.id.petVeterinary_chk);
        petBoarding_chk = (CheckBox) findViewById(R.id.petBoarding_chk);
        profileDescription_lout = (LinearLayout) findViewById(R.id.profileDescription_lout);
        experienceAndSalary_lout = (LinearLayout) findViewById(R.id.ExperienceAndSalary_lout);
        additionalInformation_lout = (LinearLayout) findViewById(R.id.additionalInformation_lout);

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.countries_array));
        country_etxt.setAdapter(countriesAdapter);
        country_etxt.setThreshold(1);

        availabilityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(availability));
        availability_spnr.setAdapter(availabilityAdapter);
        averageRateAdpter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.average_rate_per_hour_range));
        averageRatePerHour_spnr.setAdapter(averageRateAdpter);

        monthPick_spnr.setEnabled(false);
        availability_spnr.setEnabled(false);
        experienceYears_spnr.setEnabled(false);
        averageRatePerHour_spnr.setEnabled(false);
    }

    private void saveUpdatedUserInfo() {
        ConnectivityManager connectivityManager;
        NetworkInfo networkInfo;
        String taskType, firstName, lastName, email, password, country, city, gender, userType, phone;
        String profilePhoto = "", profileDescription = "", availability = "", yearsOfExperience = "", averageRatePerHour = "";
        long birthDate;

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            taskType = "updateUserInfo";
            firstName = firstName_etxt.getText().toString().trim();
            lastName = lastName_etxt.getText().toString().trim();
            email = email_etxt.getText().toString().trim();
            password = password_etxt.getText().toString().trim();
            country = country_etxt.getText().toString().trim();
            city = city_etxt.getText().toString().trim();
            try {
                calendarBirthDate.set(Integer.parseInt(yearPick_etxt.getText().toString()), monthPick_spnr.getSelectedItemPosition(), Integer.parseInt(dayPick_etxt.getText().toString()));
                birthDate = calendarBirthDate.getTimeInMillis();
            } catch (NumberFormatException e) {
                Toast.makeText(EditUserProfileActivity.this, "Please enter birth date correctly", Toast.LENGTH_SHORT).show();
                return;
            }
            gender = (gender_rgrp.getCheckedRadioButtonId() == genderMale_rbtn.getId() ? "Male" :
                    (gender_rgrp.getCheckedRadioButtonId() == genderFemale_rbtn.getId() ? "Female" : ""));
            userType = (usertype_rgrp.getCheckedRadioButtonId() == petOwner_rbtn.getId() ? UsersEntry.USER_TYPE_PET_OWNER :
                    (usertype_rgrp.getCheckedRadioButtonId() == petCareProvider_rbtn.getId() ? UsersEntry.USER_TYPE_PET_CARE_PROVIDER : ""));
            phone = phoneNumber_etxt.getText().toString().trim();
            if ((userType).equals(UsersEntry.USER_TYPE_PET_CARE_PROVIDER)) {
                profileDescription = profileDescription_etxt.getText().toString();
                availability = availability_spnr.getSelectedItem().toString();
                yearsOfExperience = experienceYears_spnr.getSelectedItem().toString();
                averageRatePerHour = averageRatePerHour_spnr.getSelectedItem().toString();
            }

            BackgroundWorker backgroundWorker = new BackgroundWorker(EditUserProfileActivity.this);
            backgroundWorker.execute(taskType, String.valueOf(preferences.getInt(UsersEntry.USER_ID, -1)),
                    userType, firstName, lastName, email, password, String.valueOf(birthDate), gender, country, city, phone,
                    profilePhoto, profileDescription, availability, yearsOfExperience, averageRatePerHour, setServiceProvidedFor(), setServiceProvided());
        } else {
            Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.user_profile_toolbar, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                if (isClickedEditButton) {
                    new AlertDialog.Builder(this).setTitle("Save Changes?")
                            .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    saveUpdatedUserInfo();
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setCancelable(false)
                            .show();

                    //this is to show confirm password dialog if we clicked save while still on password_etxt focus
                    //(because it won't show it by itself, because focus hasn't changed)
                    if (isPasswordEditing && !oldPassword.equals(password_etxt.getText().toString())) {
                        confirmNewPasswordDialog(password_etxt.getText().toString());
                    }
                }
                if (!firstName_etxt.isEnabled()) {
                    isClickedEditButton = true;
                    TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.container_lout));
                    menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.save));
                    firstName_etxt.setEnabled(true);
                    lastName_etxt.setEnabled(true);
                    email_etxt.setEnabled(true);
                    password_etxt.setEnabled(true);
                    country_etxt.setEnabled(true);
                    city_etxt.setEnabled(true);
                    dayPick_etxt.setEnabled(true);
                    monthPick_spnr.setEnabled(true);
                    yearPick_etxt.setEnabled(true);
                    genderMale_rbtn.setEnabled(true);
                    genderFemale_rbtn.setEnabled(true);
                    petOwner_rbtn.setEnabled(true);
                    petCareProvider_rbtn.setEnabled(true);
                    phoneNumber_etxt.setEnabled(true);
                    profileDescription_etxt.setEnabled(true);
                    availability_spnr.setEnabled(true);
                    experienceYears_spnr.setEnabled(true);
                    averageRatePerHour_spnr.setEnabled(true);
                    catService_chk.setEnabled(true);
                    dogService_chk.setEnabled(true);
                    horseService_chk.setEnabled(true);
                    birdService_chk.setEnabled(true);
                    fishService_chk.setEnabled(true);
                    smallAnimalService_chk.setEnabled(true);
                    petSitting_chk.setEnabled(true);
                    petWalking_chk.setEnabled(true);
                    petGrooming_chk.setEnabled(true);
                    petTraining_chk.setEnabled(true);
                    petVeterinary_chk.setEnabled(true);
                    petBoarding_chk.setEnabled(true);
                }
                return true;
            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
