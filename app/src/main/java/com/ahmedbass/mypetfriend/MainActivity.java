package com.ahmedbass.mypetfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    ImageButton myPets_btn, petPlaces_btn, vets_btn, petShop_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeMyViews();
    }

    private void initializeMyViews() {
        myPets_btn = (ImageButton) findViewById(R.id.my_pets_btn);
        myPets_btn = (ImageButton) findViewById(R.id.pet_places_btn);
        myPets_btn = (ImageButton) findViewById(R.id.pet_shop_btn);
        myPets_btn = (ImageButton) findViewById(R.id.vets_btn);

    }

    public void moveToActivity(View view) {
        switch (view.getId()){
            case R.id.my_pets_btn: startActivity(new Intent(this, MyListOfPetsActivity.class));
                break;
            case R.id.pet_places_btn: startActivity(new Intent(this, PetPlacesActivity.class));
                break;
            case R.id.pet_shop_btn: startActivity(new Intent(this, PetShopActivity.class));
                break;
            case R.id.vets_btn: startActivity(new Intent(this, VetsActivity.class));
                break;
        }
    }
}
