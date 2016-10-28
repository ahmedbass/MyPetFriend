package com.ahmedbass.mypetfriend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PetProfileActivity extends AppCompatActivity {

    TextView gender_txtv, breed_txtv, birthdate_txtv, weight_txtv;
    String name, birthdate, gender, breed, weight;
    int ageInMonth;
    int ageInYear;
    ActionBar actionBar;

    private final static int GENDER_MALE = 1;
    private final static int GENDER_FEMALE = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile);

        initializeMyViews();

        ViewPager myPager = (ViewPager) findViewById(R.id.pager);
        myPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] tabTitles = {"Overview", "Schedule", "Vaccinations"};
            @Override
            public Fragment getItem(int position) {
                return new PetInfoFragment();
//                switch (position) {
//                    case 0: return new PetInfoFragment();
//                    case 1: return new ;
//                    default: return null ;
//                }
            }

            @Override
            public int getCount() { return tabTitles.length; }

            public String getPageTitle(int position) { return tabTitles[position]; }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(myPager);

        //get the pet information from the passed intent
        if (getIntent() != null && getIntent().getSerializableExtra("petInfo") != null) {

            Pet petInfo = (Pet) getIntent().getSerializableExtra("petInfo");

            //assign the pet info into variables to display them on UI
            name = petInfo.getName();
            //format the birthDate in millies
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            //calculating the age in years and months
            ageInYear = petInfo.getPetAgeInYear();
            ageInMonth = petInfo.getPetAgeInMonth();
            birthdate = "Age: " + ageInYear + " years and " + ageInMonth + " months\nBirthday: " + sdf.format(petInfo.getBirthDate());
            gender = getGender(petInfo.getGender()) + " " + petInfo.getBreed() + " " + petInfo.getType();
            breed = petInfo.getBreed() + " " + petInfo.getType();
            weight = "Weight: " + petInfo.getWeight() + " kg";

            //set pet info on the UI
//            actionBar.setTitle(name);
//            gender_txtv.setText(gender);
//          breed_txtv.setText(breed);
//            birthdate_txtv.setText(birthdate);
//            weight_txtv.setText(weight);
        }
    }

    private String getGender(int gender){
        switch (gender) {
            case GENDER_MALE: return "Male";
            case GENDER_FEMALE: return "Female";
            default: return "Unknown";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pet_profile_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(this, "TODO: modify the pet profile.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_delete:
                Toast.makeText(this, "TODO: display a dialog to confirm deletion.", Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeMyViews(){
        //set the title of the advert on the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left);
        toolbar.setTitle("what's up");

        ActionBar actionBar = getSupportActionBar();
//
//        birthdate_txtv = (TextView) findViewById(R.id.pet_birthdate_txtv);
//        gender_txtv = (TextView) findViewById(R.id.pet_gender_txtv);
////        breed_txtv = (TextView) findViewById(R.id.pet_breed_txtv);
//        weight_txtv = (TextView) findViewById(R.id.pet_weight_txtv);
    }
}

