package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstName_etxt, lastName_etxt, password_etxt, email_etxt, dayPick_etxt, yearPick_etxt;
    AutoCompleteTextView country_etxt, city_etxt;
    Spinner monthPick_spnr;
    RadioGroup gender_rgrp, usertype_rgrp;
    RadioButton male_rbtn, female_rbtn, petOwner_rbtn, petCareProvider_rbtn;
    Button register_btn;
    String firstName, lastName, email, password, country, city, gender, userType;
    long birthDate;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    Bundle bundle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeMyViews();
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

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
                    }
                    if (monthPick_spnr.getSelectedItemPosition() == 1 || monthPick_spnr.getSelectedItemPosition() == 3 ||
                            monthPick_spnr.getSelectedItemPosition() == 5 || monthPick_spnr.getSelectedItemPosition() == 7 ||
                            monthPick_spnr.getSelectedItemPosition() == 8 || monthPick_spnr.getSelectedItemPosition() == 10 ||
                            monthPick_spnr.getSelectedItemPosition() == 12) {
                        if (Integer.parseInt(dayPick_etxt.getText().toString()) > 31) {
                            dayPick_etxt.setText("31");
                        }
                    } else if (monthPick_spnr.getSelectedItemPosition() == 4 || monthPick_spnr.getSelectedItemPosition() == 6 ||
                            monthPick_spnr.getSelectedItemPosition() == 9 || monthPick_spnr.getSelectedItemPosition() == 11) {
                        if (Integer.parseInt(dayPick_etxt.getText().toString()) > 30) {
                            dayPick_etxt.setText("30");
                        }
                    } else if (monthPick_spnr.getSelectedItemPosition() == 2) {
                        if (Integer.parseInt(dayPick_etxt.getText().toString()) > 29) {
                            dayPick_etxt.setText("29");
                        }
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
                        yearPick_etxt.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1));
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

        register_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    firstName = firstName_etxt.getText().toString().trim();
                    lastName = lastName_etxt.getText().toString().trim();
                    email = email_etxt.getText().toString().trim();
                    password = password_etxt.getText().toString().trim();
                    country = country_etxt.getText().toString().trim();
                    city = city_etxt.getText().toString().trim();
                    gender = (gender_rgrp.getCheckedRadioButtonId() == male_rbtn.getId() ? "Male" :
                            (gender_rgrp.getCheckedRadioButtonId() == female_rbtn.getId() ? "Female" : ""));
                    userType = (usertype_rgrp.getCheckedRadioButtonId() == petOwner_rbtn.getId() ? "PetOwner" :
                            (usertype_rgrp.getCheckedRadioButtonId() == petCareProvider_rbtn.getId() ? "PetCareProvider" : ""));
                    String taskType = "register";

                    if (validateFields()) {
                        Calendar birthDateCalendar = Calendar.getInstance();
                        birthDateCalendar.set(Integer.parseInt(yearPick_etxt.getText().toString()),
                                monthPick_spnr.getSelectedItemPosition(), Integer.parseInt(dayPick_etxt.getText().toString()));
                        birthDate = birthDateCalendar.getTimeInMillis();
                        if (userType.equals("PetOwner")) {
                            BackgroundWorker backgroundWorker = new BackgroundWorker(RegistrationActivity.this);
                            backgroundWorker.execute(taskType, String.valueOf(System.currentTimeMillis()),
                                    userType, firstName, lastName, email, password, String.valueOf(birthDate), gender, country, city,
                                    "", "", "", "", "", "", "", "");
                        } else if (userType.equals("PetCareProvider")) {
                            Intent intent = new Intent(getBaseContext(), PetCareProviderRegistrationActivity.class);
                            intent.putExtra("firstName", firstName);
                            intent.putExtra("lastName", lastName);
                            intent.putExtra("email", email);
                            intent.putExtra("password", password);
                            intent.putExtra("birthDate", birthDate);
                            intent.putExtra("gender", gender);
                            intent.putExtra("country", country);
                            intent.putExtra("city", city);
                            intent.putExtra("userType", userType);
                            startActivity(intent);
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateFields() {
        String allowedNameCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz ";
        String[] availableCountries = getResources().getStringArray(R.array.countries_array);
        boolean flagCorrectCountry = false;

        if (firstName.length() < 3 || lastName.length() < 3) {
            Toast.makeText(this, "name must be at least three characters", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            for (int i = 0; i < firstName.length(); i++) {
                if (!allowedNameCharacters.contains(String.valueOf(firstName.charAt(i)))) {
                    Toast.makeText(this, "Please enter your first name correctly. only letters allowed", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
            for (int i = 0; i < lastName.length(); i++) {
                if (!allowedNameCharacters.contains(String.valueOf(lastName.charAt(i)))) {
                    Toast.makeText(this, "Please enter your last name correctly. only letters allowed", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        if (email.startsWith(".") || email.endsWith(".") || email.contains(" ") ||
                !email.contains("@") || email.startsWith("@") || email.endsWith("@") ||
                !(email.endsWith(".com") || email.endsWith(".net") || email.endsWith(".edu") || email.endsWith(".org") || email.endsWith(".gov"))) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6 || password.length() > 20) {
            Toast.makeText(this, "Please enter password between 6 and 20 characters", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (country.isEmpty()) {
            Toast.makeText(this, "Please enter your country", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            for (String availableCountry : availableCountries) {
                if (availableCountry.equalsIgnoreCase(country.trim())) {
                    flagCorrectCountry = true;
                    break;
                }
            }
            if (!flagCorrectCountry) {
                Toast.makeText(this, "Please select an available country", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        if (city.isEmpty()) {
            Toast.makeText(this, "Please enter your city", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            for (int i = 0; i < city.length(); i++) {
                if (!allowedNameCharacters.contains(String.valueOf(city.charAt(i)))) {
                    Toast.makeText(this, "Please enter your city correctly. only letters allowed", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
        if (yearPick_etxt.getText().toString().trim().isEmpty() || dayPick_etxt.getText().toString().trim().isEmpty()) {
            Toast.makeText(getBaseContext(), "Please enter your birth date correctly", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (gender.isEmpty()) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (userType.isEmpty()) {
            Toast.makeText(this, "Please select a user type", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void initializeMyViews() {
        register_btn = (Button) findViewById(R.id.myProfile_btn);
        firstName_etxt = (EditText) findViewById(R.id.firstName_etxt);
        lastName_etxt = (EditText) findViewById(R.id.lastName_etxt);
        email_etxt = (EditText) findViewById(R.id.email_etxt);
        password_etxt = (EditText) findViewById(R.id.password_etxt);
        country_etxt = (AutoCompleteTextView) findViewById(R.id.country_etxt);
        city_etxt = (AutoCompleteTextView) findViewById(R.id.city_etxt);
        dayPick_etxt = (EditText) findViewById(R.id.dayPick_etxt);
        monthPick_spnr = (Spinner) findViewById(R.id.monthPick_spnr);
        yearPick_etxt = (EditText) findViewById(R.id.yearPick_etxt);
        gender_rgrp = (RadioGroup) findViewById(R.id.gender_rgrp);
        male_rbtn = (RadioButton) findViewById(R.id.genderMale_rbtn);
        female_rbtn = (RadioButton) findViewById(R.id.genderFemale_rbtn);
        usertype_rgrp = (RadioGroup) findViewById(R.id.userType_rgrp);
        petOwner_rbtn = (RadioButton) findViewById(R.id.petOwner_rbtn);
        petCareProvider_rbtn = (RadioButton) findViewById(R.id.petCareProvider_rbtn);

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.countries_array));
        country_etxt.setAdapter(countriesAdapter);
        country_etxt.setThreshold(1);
    }

    public void moveToSignIn(View view) {
        startActivity(new Intent(this, LoginActivity.class), bundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
