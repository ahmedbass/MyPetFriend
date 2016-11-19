package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static com.ahmedbass.mypetfriend.MainActivity.PREF_BACK_PRESSED;
import static com.ahmedbass.mypetfriend.MyDBHelper.DATABASE_NAME;

public class LauncherActivity extends AppCompatActivity {

    public static final String MY_APP_PREFS = "com.ahmedbass.mypetfriend.PREFERENCE_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //check if activity is launching after pressing back from MainActivity, to move directly to it
        SharedPreferences pref = getSharedPreferences(MY_APP_PREFS, MODE_PRIVATE);
        if (pref.getBoolean(PREF_BACK_PRESSED, false)) {
            pref.edit().putBoolean(PREF_BACK_PRESSED, false).apply();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        try {
            String destPath = getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
            File file = new File(destPath);
            if (!file.exists()) {
                MyDBHelper.copyDB(this);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: Cannot Build Database", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void moveToActivity(View view) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
        switch (view.getId()) {
            case R.id.moveToLogin_btn:
                startActivity(new Intent(this, LoginActivity.class), bundle);
                break;
            case R.id.moveToRegister_btn:
                startActivity(new Intent(this, RegistrationActivity.class), bundle);
                break;
        }
    }
}
