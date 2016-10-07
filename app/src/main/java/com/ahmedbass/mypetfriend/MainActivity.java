package com.ahmedbass.mypetfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void moveToActivity(View view) {
        switch (view.getId()){
            case R.id.my_pets_btn: startActivity(new Intent(this, MyPetsActivity.class));
                break;
            case R.id.pet_places_btn: startActivity(new Intent(this, PetPlacesActivity.class));
                break;
            case R.id.pet_shop_btn: startActivity(new Intent(this, PetShopActivity.class));
                break;
            case R.id.petServices_btn: startActivity(new Intent(this, PetCareServiceActivity.class));
                break;
        }
    }
}
