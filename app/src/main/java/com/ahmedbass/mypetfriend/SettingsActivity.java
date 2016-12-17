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

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void moveToEditUserProfile(View view) {
        Intent intent = new Intent(this, EditUserProfileActivity.class);
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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
}
