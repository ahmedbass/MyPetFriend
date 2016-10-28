package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;

public class MyPetsActivity extends AppCompatActivity {

    private ArrayList<Pet> myListOfPets = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        generateList();

        ViewPager myPetsViewPager = (ViewPager) findViewById(R.id.myPets_viewpager);
        myPetsViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return ItemPetFragment.newInstance(myListOfPets.get(position));
            }

            @Override
            public int getCount() {
                return  myListOfPets.size();
            }
        });
    }

    private void generateList() {
        Calendar birthdate = Calendar.getInstance();
        int year = birthdate.get(Calendar.YEAR);
        int month = birthdate.get(Calendar.MONTH);
        int day = birthdate.get(Calendar.DAY_OF_MONTH);
        birthdate.set(year - 3, month + 5, day);
        //generate fake list
        myListOfPets.add(new Pet("Fluffy", System.currentTimeMillis(),Pet.GENDER_FEMALE,Pet.TYPE_DOG, "German Shepherd",15));
        myListOfPets.add(new Pet("Snowball", birthdate.getTimeInMillis(), Pet.GENDER_FEMALE, Pet.TYPE_CAT,"Siamese",4));
        myListOfPets.add(new Pet("Milo", System.currentTimeMillis(), Pet.GENDER_MALE, Pet.TYPE_DOG, "German Shepherd",17));
        myListOfPets.add(new Pet("كلب البحر",System.currentTimeMillis(), Pet.GENDER_MALE, Pet.TYPE_DOG,"Golden Retriever", 25));
        myListOfPets.add(new Pet("Tintin",System.currentTimeMillis(), Pet.GENDER_MALE, Pet.TYPE_DOG, "Golden Retriever", 28));
        myListOfPets.add(new Pet("garfield",System.currentTimeMillis(), Pet.GENDER_MALE, Pet.TYPE_CAT,"Persian", 10));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mypets_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                startActivity(new Intent(this, AddNewPetActivity.class), bundle);
                return true;

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default: //If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
