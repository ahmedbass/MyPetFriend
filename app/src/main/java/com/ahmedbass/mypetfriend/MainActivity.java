package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import static com.ahmedbass.mypetfriend.LauncherActivity.MY_APP_PREFS;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_BACK_PRESSED = "MainActivityOnBackPressed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void moveToActivity(View view) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        switch (view.getId()) {
            case R.id.my_pets_btn:
                startActivity(new Intent(this, MyPetsActivity.class), bundle);
                break;
            case R.id.pet_places_btn:
                startActivity(new Intent(this, PetPlacesActivity.class), bundle);
                break;
            case R.id.pet_shop_btn:
                startActivity(new Intent(this, PetShopActivity.class), bundle);
                break;
            case R.id.petServices_btn:
                startActivity(new Intent(this, PetCareServiceActivity.class), bundle);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getSharedPreferences(MY_APP_PREFS, MODE_PRIVATE).edit().putBoolean(PREF_BACK_PRESSED, true).apply();
    }
}
