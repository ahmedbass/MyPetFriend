package com.ahmedbass.mypetfriend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PetProfileActivity extends AppCompatActivity {

    TextView nameTV, birthdateTV, genderTV, breedTV, weightTV;
    String name, birthdate, gender, breed, weight;

    private final static int GENDER_MALE = 1;
    private final static int GENDER_FEMALE = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile);
        initializeMyViews();

        //get the pet information from the passed intent
        Pet petInfo = (Pet) getIntent().getSerializableExtra("petInfo");

        //format the birthDate in millies
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        birthdate = sdf.format(petInfo.getBirthDate());
        name = petInfo.getName();
        gender = getGender(petInfo.getGender());
        breed = petInfo.getKind() + " - " + petInfo.getBreed();
        weight = petInfo.getWeight()+" kg";

        //set pet info on the UI
        nameTV.setText(name);
        birthdateTV.setText(birthdate);
        genderTV.setText(gender);
        breedTV.setText(breed);
        weightTV.setText(weight);
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
                Toast.makeText(this, "TODO: it should allow to modify the pet profile.", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_delete:
                Toast.makeText(this, "TODO: it should allow to display a dialog to confirm deletion.", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initializeMyViews(){
        nameTV = (TextView) findViewById(R.id.pet_name_txt);
        birthdateTV = (TextView) findViewById(R.id.pet_bdate_txt);
        genderTV = (TextView) findViewById(R.id.pet_gender_txt);
        breedTV = (TextView) findViewById(R.id.pet_breed_txt);
        weightTV = (TextView) findViewById(R.id.pet_weight_txt);
    }
}

