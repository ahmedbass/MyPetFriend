package com.ahmedbass.mypetfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "TODO: move to settings activity", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
