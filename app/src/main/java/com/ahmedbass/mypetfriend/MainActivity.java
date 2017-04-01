package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ahmedbass.mypetfriend.MyPetFriendContract.UsersEntry;

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class MainActivity extends AppCompatActivity {

    PetOwner petOwner;
    PetCareProvider petCareProvider;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE);

        //after login, get user's information and save it in sharedPreferences to access it from everywhere in the application
        if (getIntent() != null && getIntent().getSerializableExtra("userInfo") != null) {
            petOwner = ((PetOwner) getIntent().getSerializableExtra("userInfo"));
            preferences.edit()
                    .putInt(UsersEntry.USER_ID, petOwner.getUserId())
                    .putLong(UsersEntry.COLUMN_CREATE_DATE, petOwner.getCreateDate())
                    .putString(UsersEntry.COLUMN_USER_TYPE, petOwner.getUserType())
                    .putString(UsersEntry.COLUMN_FIRST_NAME, petOwner.getFirstName())
                    .putString(UsersEntry.COLUMN_LAST_NAME, petOwner.getLastName())
                    .putString(UsersEntry.COLUMN_EMAIL, petOwner.getEmail())
                    .putString(UsersEntry.COLUMN_PASSWORD, petOwner.getPassword())
                    .putLong(UsersEntry.COLUMN_BIRTH_DATE, petOwner.getBirthDate())
                    .putString(UsersEntry.COLUMN_GENDER, petOwner.getGender())
                    .putString(UsersEntry.COLUMN_COUNTRY, petOwner.getCountry())
                    .putString(UsersEntry.COLUMN_CITY, petOwner.getCity())
                    .putString(UsersEntry.COLUMN_PHONE, petOwner.getPhone())
                    .putString(UsersEntry.COLUMN_PROFILE_PHOTO, petOwner.getProfilePhoto())
                    .apply();
            //we know for sure the passed object is a petOwner, but if it's further more a petCareProvider, do the following
            if (getIntent().getSerializableExtra("userInfo") instanceof PetCareProvider) {
                petCareProvider = (PetCareProvider) getIntent().getSerializableExtra("userInfo");
                preferences.edit()
                        .putString(UsersEntry.COLUMN_PROFILE_DESCRIPTION, petCareProvider.getProfileDescription())
                        .putString(UsersEntry.COLUMN_AVAILABILITY, petCareProvider.getAvailability())
                        .putString(UsersEntry.COLUMN_YEARS_OF_EXPERIENCE, petCareProvider.getYearsOfExperience())
                        .putString(UsersEntry.COLUMN_AVERAGE_RATE_PER_HOUR, petCareProvider.getAverageRatePerHour())
                        .putString(UsersEntry.COLUMN_SERVICE_PROVIDED_FOR, petCareProvider.getServicesProvidedFor())
                        .putString(UsersEntry.COLUMN_SERVICE_PROVIDED, petCareProvider.getServicesProvided())
                        .apply();
            }
        }
        getSupportActionBar().setTitle("Welcome " + preferences.getString(UsersEntry.COLUMN_FIRST_NAME, "User"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void moveToActivity(View view) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        Intent intent;
        BackgroundWorker backgroundWorker;
        ConnectivityManager connectivityManager;
        NetworkInfo networkInfo;
        switch (view.getId()) {
            case R.id.myPets_btn:
                intent = new Intent(this, MyPetsActivity.class);
                startActivity(intent, bundle);
                break;
            case R.id.petPlaces_btn:
                intent = new Intent(this, PetPlacesMapActivity.class);
                startActivity(intent, bundle);
                break;
            case R.id.petServices_btn:
                connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    backgroundWorker = new BackgroundWorker(this);
                    backgroundWorker.execute("getPetCareProviders");
                } else {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, PetCareServiceActivity.class), bundle);
                }
                break;
            case R.id.petMarket_btn:
                connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    backgroundWorker = new BackgroundWorker(this);
                    backgroundWorker.execute("getAdverts");
                } else {
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, PetMarketActivity.class), bundle);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, MyAccountActivity.class);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
