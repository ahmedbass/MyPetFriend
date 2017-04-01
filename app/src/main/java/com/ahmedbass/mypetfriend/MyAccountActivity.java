package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class MyAccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void moveToActivity(View view) {
        Intent intent;
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        switch (view.getId()) {
            case R.id.myProfile_btn:
                intent = new Intent(this, EditUserProfileActivity.class);
                startActivity(intent, bundle);
                break;
            case R.id.myAdverts_btn:
                intent = new Intent(this, MyAdvertsActivity.class);
                startActivity(intent, bundle);
                break;
        }
    }

    public void logout(View view) {
        new AlertDialog.Builder(this).setTitle("Logout?")
                .setIcon(R.drawable.logout1)
                .setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //this clears current user's information, and also the LOGGED_IN pref
                        getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).edit().clear().apply();
                        Intent intent = new Intent(getBaseContext(), LauncherActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void aboutApp_dialog(View view) {
        new AlertDialog.Builder(this).setTitle("About 'MyPetFriend'")
                .setMessage("Welcome to 'MyPetFriend' application. 'MyPetFriend' is aimed for pet owners to help them have a good care of their pets. 'MyPetFriend' currently has four main objectives:" +
                        "\n\n*Create a profile for your pets and get automatically generated valuable information based on your pet situation to help you understand your pets better and how to take care for them properly." +
                        "\n\n*Easily find pet-related services located around you on the map." +
                        "\n\n*View pet care providers who are registered in our system and easily communicate with them via email or phone " +
                        "\n\n*Market zone for offering pets for sale and view other users' offers, and easily communicate with them via email or phone." +
                        "\n\nThank you for using my application." +
                        "\n\n\n© All Rights Reserved for Ahmed Ashraf Mahmoud 2017")
                .setCancelable(false)
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }
}
