package com.ahmedbass.mypetfriend;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class AddEditAdvertActivity extends AppCompatActivity {

    Spinner advertType_spnr, petType_spnr, petGender_spnr, monthPick_spnr;
    EditText advertTitle_etxt, advertPrice_etxt, advertDetails_etxt, dayPick_etxt, yearPick_etxt, city_etxt, email_etxt, phoneNumber_etxt;
    AutoCompleteTextView country_etxt, petBreed_atxt;
    RadioGroup isNeutered_rgrp, isMicrochipped_rgrp, isVaccinated_rgrp;
    RadioButton neutered_rbtn, notNeutered_rbtn, microchipped_rbtn, notMicrochipped_rbtn, vaccinated_rbtn, notVaccinated_rbtn;
    Button publishAdvert_btn;

    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_advert);

        preferences = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE);
        initializeMyViews();
        setUserInfoInViews();
    }

    private void setUserInfoInViews() {
        if (getIntent() != null && getIntent().getSerializableExtra("editAdvert") != null) {
            Advert currentAdvert = (Advert) getIntent().getSerializableExtra("editAdvert");
            getSupportActionBar().setTitle("Edit Advert");
            publishAdvert_btn.setText("Update Advert");
            advertTitle_etxt.setText(currentAdvert.getTitle());
            advertPrice_etxt.setText(String.valueOf(currentAdvert.getPrice()));
            advertDetails_etxt.setText(currentAdvert.getDetails());
            petBreed_atxt.setText(currentAdvert.getPetBreed());
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(currentAdvert.getPetBirthDate());
            dayPick_etxt.setText(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
            monthPick_spnr.setSelection(calendar.get(Calendar.MONTH));
            yearPick_etxt.setText(String.valueOf(calendar.get(Calendar.YEAR)));
            isNeutered_rgrp.check(currentAdvert.isNeutered() ? neutered_rbtn.getId() : notNeutered_rbtn.getId());
            isMicrochipped_rgrp.check(currentAdvert.isPetMicroChipped() ? microchipped_rbtn.getId() : notMicrochipped_rbtn.getId());
            isVaccinated_rgrp.check(currentAdvert.isPetVaccinated()? vaccinated_rbtn.getId() : notVaccinated_rbtn.getId());
            country_etxt.setText(currentAdvert.getCountry());
            city_etxt.setText(currentAdvert.getCity());
            email_etxt.setText(currentAdvert.getEmail());
            phoneNumber_etxt.setText(currentAdvert.getPhone());
        } else {
            country_etxt.setText(preferences.getString(MyPetFriendContract.UsersEntry.COLUMN_COUNTRY, ""));
            city_etxt.setText(preferences.getString(MyPetFriendContract.UsersEntry.COLUMN_CITY, ""));
            email_etxt.setText(preferences.getString(MyPetFriendContract.UsersEntry.COLUMN_EMAIL, ""));
            phoneNumber_etxt.setText(preferences.getString(MyPetFriendContract.UsersEntry.COLUMN_PHONE, ""));
        }
    }

    private void initializeMyViews() {
        advertType_spnr = (Spinner) findViewById(R.id.advertType_spnr);
        petType_spnr = (Spinner) findViewById(R.id.petType_spnr);
        petGender_spnr = (Spinner) findViewById(R.id.petGender_spnr);
        advertTitle_etxt = (EditText) findViewById(R.id.advertTitle_etxt);
        advertPrice_etxt = (EditText) findViewById(R.id.advertPrice_etxt);
        advertDetails_etxt = (EditText) findViewById(R.id.advertDetails_etxt);
        dayPick_etxt = (EditText) findViewById(R.id.dayPick_etxt);
        monthPick_spnr = (Spinner) findViewById(R.id.monthPick_spnr);
        yearPick_etxt = (EditText) findViewById(R.id.yearPick_etxt);
        country_etxt = (AutoCompleteTextView) findViewById(R.id.country_etxt);
        city_etxt = (EditText) findViewById(R.id.city_etxt);
        email_etxt = (EditText) findViewById(R.id.email_etxt);
        phoneNumber_etxt = (EditText) findViewById(R.id.phoneNumber_etxt);
        petBreed_atxt = (AutoCompleteTextView) findViewById(R.id.petBreed_atxt);
        isNeutered_rgrp = (RadioGroup) findViewById(R.id.isNeutered_rgrp);
        isMicrochipped_rgrp = (RadioGroup) findViewById(R.id.isMicrochipped_rgrp);
        isVaccinated_rgrp = (RadioGroup) findViewById(R.id.isVaccinated_rgrp);
        neutered_rbtn = (RadioButton) findViewById(R.id.neutered_rbtn);
        notNeutered_rbtn = (RadioButton) findViewById(R.id.notNeutered_rbtn);
        microchipped_rbtn = (RadioButton) findViewById(R.id.microchipped_rbtn);
        notMicrochipped_rbtn = (RadioButton) findViewById(R.id.notMicrochipped_rbtn);
        vaccinated_rbtn = (RadioButton) findViewById(R.id.vaccinated_rbtn);
        notVaccinated_rbtn = (RadioButton) findViewById(R.id.notVaccinated_rbtn);
        publishAdvert_btn = (Button) findViewById(R.id.publishAdvert_btn);

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.countries_array));
        country_etxt.setAdapter(countriesAdapter);
        country_etxt.setThreshold(1);
    }

    public void publishAdvert(View view) {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) {
            int sellerId = preferences.getInt(MyPetFriendContract.UsersEntry.USER_ID, -1);
            Long createDate = System.currentTimeMillis();
            int isSold = 0;
            int viewCount = 0;
            String category = Advert.CATEGORY_PETS;
            String type = advertType_spnr.getSelectedItem().toString().trim();
            String title = advertTitle_etxt.getText().toString().trim();
            String price = advertPrice_etxt.getText().toString().trim().isEmpty() ? "0" : advertPrice_etxt.getText().toString().trim();
            String details = advertDetails_etxt.getText().toString().trim();
            String country = country_etxt.getText().toString().trim();
            String city = city_etxt.getText().toString().trim();
            String email = email_etxt.getText().toString().trim();
            String phone = phoneNumber_etxt.getText().toString().trim();
            String petType = petType_spnr.getSelectedItem().toString().trim();
            String petBreed = petBreed_atxt.getText().toString().trim();
            long petBirthDate;
            try {
                Calendar calendarBirthDate = Calendar.getInstance();
                calendarBirthDate.set(Integer.parseInt(yearPick_etxt.getText().toString()), monthPick_spnr.getSelectedItemPosition(), Integer.parseInt(dayPick_etxt.getText().toString()));
                petBirthDate  = calendarBirthDate.getTimeInMillis();
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Pet Birth Date Was Not Set Properly", Toast.LENGTH_SHORT).show();
                petBirthDate = 0;
            }
            String petGender = petGender_spnr.getSelectedItem().toString().trim();
            int isPetNeutered = isNeutered_rgrp.getCheckedRadioButtonId() == neutered_rbtn.getId() ? 1 : 0;
            int isPetMicrochipped = isMicrochipped_rgrp.getCheckedRadioButtonId() == microchipped_rbtn.getId() ? 1 : 0;
            int isPetVaccinated = isVaccinated_rgrp.getCheckedRadioButtonId() == vaccinated_rbtn.getId() ? 1 : 0;
            String photo = "";

            if (getIntent() != null && getIntent().getSerializableExtra("editAdvert") != null) {
                //TODO update advert
                Toast.makeText(this, "TODO: Update Advert", Toast.LENGTH_SHORT).show();
            } else {
                BackgroundWorker backgroundWorker = new BackgroundWorker(this);
                backgroundWorker.execute("addNewAdvert", String.valueOf(sellerId), String.valueOf(createDate), String.valueOf(isSold), String.valueOf(viewCount),
                        category, type, title, price, details, country, city, email, phone, petType, petBreed, String.valueOf(petBirthDate),
                        petGender, String.valueOf(isPetNeutered), String.valueOf(isPetMicrochipped), String.valueOf(isPetVaccinated), photo);
            }
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

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
