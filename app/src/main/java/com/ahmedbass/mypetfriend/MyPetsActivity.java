package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MyPetsActivity extends AppCompatActivity {

//    public static final String MY_PETS_PREFS = "com.ahmedbass.mypetfriend.PREFERENCE_KEY_PETS";
    final static int REQUEST_CODE_ADD_PET = 1;

    private ArrayList<Pet> myListOfPets;
    ViewPager myPetsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        getPetListFromDB();
        setViewPager();
    }

    public void getPetListFromDB() {
        try {
            myListOfPets = new ArrayList<>();
            //read pets from database and create pet objects from it
            MyDBHelper dbHelper = new MyDBHelper(this);
            dbHelper.open();
            Cursor cursor = dbHelper.getAllRecords(MyPetFriendContract.PetsEntry.TABLE_NAME, null);
            while(cursor.moveToNext()) {
                myListOfPets.add(new Pet(cursor.getInt(0), cursor.getInt(1), cursor.getLong(2), cursor.getString(3), cursor.getLong(4),
                        cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getInt(8), (cursor.getInt(9) != 0), cursor.getString(10)));
            }
            dbHelper.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error: Cannot Retrieve Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void setViewPager() {
        myPetsViewPager = (ViewPager) findViewById(R.id.myPets_viewpager);
        myPetsViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                //make last created pets show first
                return ItemPetFragment.newInstance(myListOfPets.get((myListOfPets.size() - 1) - position));
            }
            @Override
            public int getCount() {
                return myListOfPets.size();
            }
        });

        if (myListOfPets.size() <= 0) {
            myPetsViewPager.setVisibility(View.GONE);
            findViewById(R.id.emptyView_txtv).setVisibility(View.VISIBLE);
        } else {
            myPetsViewPager.setVisibility(View.VISIBLE);
            findViewById(R.id.emptyView_txtv).setVisibility(View.GONE);
        }
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
            case R.id.action_add_pet:
                try {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    Intent intent = new Intent(this, AddEditPetActivity.class);
                    intent.putExtra("AddPetProfile", true);
                    startActivityForResult(intent, REQUEST_CODE_ADD_PET, bundle);
                    return true;
                } catch (Exception e) {
                    Toast.makeText(this, "Minor error just happened, but it's handled!", Toast.LENGTH_SHORT).show();
                }

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default: //If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_PET && resultCode == RESULT_OK) {
            getPetListFromDB();
            TransitionManager.beginDelayedTransition((FrameLayout) findViewById(R.id.container),
                    TransitionInflater.from(this).inflateTransition(R.transition.my_transition5));
            setViewPager();
        }
    }
}
