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

import static com.ahmedbass.mypetfriend.ItemPetFragment.REQUEST_CODE_OPEN_PET_PROFILE;
import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class MyPetsActivity extends AppCompatActivity {

    //    public static final String MY_PETS_PREFS = "com.ahmedbass.mypetfriend.PREFERENCE_KEY_PETS";
    final static int REQUEST_CODE_ADD_PET = 1;
    ViewPager myPetsViewPager;
    int petOwnerId;
    private ArrayList<Pet> myListOfPets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        petOwnerId = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).getInt(MyPetFriendContract.UsersEntry.USER_ID, -1);
        if (petOwnerId == -1) {
            Toast.makeText(this, "Error: User Information Cannot Be Found", Toast.LENGTH_SHORT).show();
        }
        getPetListFromDB();
        setViewPager();
    }

    public void getPetListFromDB() {
        try {
            myListOfPets = new ArrayList<>();
            //read pets from database and create pet objects from it
            MyDBHelper dbHelper = new MyDBHelper(this);
            dbHelper.open();
            Cursor petsCursor = dbHelper.getRecord(MyPetFriendContract.PetsEntry.TABLE_NAME, null,
                    new String[]{MyPetFriendContract.PetsEntry.COLUMN_OWNER_ID}, new String[]{String.valueOf(petOwnerId)});
            Cursor petScheduleActivitiesCursor, petVaccinesCursor, petWeightListCursor;
            //get pets of current user from database, and nested whiles are for adding that pet's multi data (data that are stored in arrays)
            while (petsCursor.moveToNext()) {
                myListOfPets.add(new Pet(petsCursor.getLong(0), petsCursor.getLong(1), petsCursor.getLong(2), petsCursor.getString(3),
                        petsCursor.getLong(4), petsCursor.getString(5), petsCursor.getString(6), petsCursor.getString(7),
                        petsCursor.getInt(8), (petsCursor.getInt(9) == 1), petsCursor.getString(10), petsCursor.getInt(11),
                        petsCursor.getInt(12), petsCursor.getInt(13), petsCursor.getInt(14), petsCursor.getInt(15),
                        petsCursor.getString(16), petsCursor.getString(17), petsCursor.getString(18), petsCursor.getString(19),
                        petsCursor.getString(20), petsCursor.getString(21)));

                Cursor petPhotosCursor = dbHelper.getRecord(MyPetFriendContract.PetPhotosEntry.TABLE_NAME,
                        new String[]{MyPetFriendContract.PetPhotosEntry.PHOTO},
                        new String[]{MyPetFriendContract.PetPhotosEntry.PET_ID}, new String[]{String.valueOf(petsCursor.getLong(0))});
                if (petPhotosCursor.moveToLast()) {
                    myListOfPets.get(petsCursor.getPosition()).setCurrentPhoto(petPhotosCursor.getBlob(0));
                }

                petScheduleActivitiesCursor = dbHelper.getRecord(MyPetFriendContract.PetScheduleActivitiesEntry.TABLE_NAME, null,
                        new String[]{MyPetFriendContract.PetPhotosEntry.PET_ID}, new String[]{String.valueOf(petsCursor.getLong(0))});
                while (petScheduleActivitiesCursor.moveToNext()) {
                    myListOfPets.get(petsCursor.getPosition()).
                            addNewScheduleActivity(petScheduleActivitiesCursor.getInt(0), petScheduleActivitiesCursor.getInt(1),
                                    petScheduleActivitiesCursor.getString(2), petScheduleActivitiesCursor.getLong(3),
                                    petScheduleActivitiesCursor.getInt(4), petScheduleActivitiesCursor.getInt(5),
                                    petScheduleActivitiesCursor.getInt(6), petScheduleActivitiesCursor.getString(7), petScheduleActivitiesCursor.getInt(8));
                }

                petVaccinesCursor = dbHelper.getRecord(MyPetFriendContract.PetVaccinesEntry.TABLE_NAME, null,
                        new String[]{MyPetFriendContract.PetVaccinesEntry.PET_ID}, new String[]{String.valueOf(petsCursor.getLong(0))});
                while (petVaccinesCursor.moveToNext()) {
                    myListOfPets.get(petsCursor.getPosition()).
                            addNewVaccine(petVaccinesCursor.getInt(0), petVaccinesCursor.getInt(1), petVaccinesCursor.getString(2),
                                    petVaccinesCursor.getInt(3), petVaccinesCursor.getInt(4), petVaccinesCursor.getInt(5),
                                    petVaccinesCursor.getString(6));
                }

                petWeightListCursor = dbHelper.getRecord(MyPetFriendContract.PetWeightListEntry.TABLE_NAME, null,
                        new String[]{MyPetFriendContract.PetWeightListEntry.PET_ID}, new String[]{String.valueOf(petsCursor.getLong(0))});
                while (petWeightListCursor.moveToNext()) {
                    myListOfPets.get(petsCursor.getPosition()).setCurrentWeight(petWeightListCursor.getInt(2));
                }
            }
            dbHelper.close();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
            case R.id.action_add_advert:
                try {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    Intent intent = new Intent(this, AddEditPetActivity.class);
                    intent.putExtra("AddPetProfile", true);
                    intent.putExtra("userId", petOwnerId);
                    startActivityForResult(intent, REQUEST_CODE_ADD_PET, bundle);
                    return true;
                } catch (Exception e) {
                    Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
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

        if (requestCode == REQUEST_CODE_OPEN_PET_PROFILE && resultCode == RESULT_OK) {
            //if we get here, it means we either modified or deleted the pet (because that's only where we make result ok)
            if (data.getBooleanExtra("petDeleted", false)) {
                getPetListFromDB();
                TransitionManager.beginDelayedTransition((FrameLayout) findViewById(R.id.container),
                        TransitionInflater.from(this).inflateTransition(R.transition.my_transition5));
                setViewPager();
                Toast.makeText(this, "Pet Deleted Successfully", Toast.LENGTH_SHORT).show();
            }
            if (data.getSerializableExtra("petModified") != null) {
                getPetListFromDB();
                setViewPager();
                //when modifying, we start activity with passing the modified pet object,
                // then read from database, and set viewpager (to make changes real)
                Intent moveToPetProfile = new Intent(this, PetProfileActivity.class);
                moveToPetProfile.putExtra("petInfo", data.getSerializableExtra("petModified"));
                startActivityForResult(moveToPetProfile, REQUEST_CODE_OPEN_PET_PROFILE);
                Toast.makeText(this, "Changes Saved Successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
