package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import static com.ahmedbass.mypetfriend.MainActivity.PREF_BACK_PRESSED;

public class WelcomeActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyAppPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //check if activity is launching after pressing back from MainActivity, to move directly to it
        if (getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).getBoolean(PREF_BACK_PRESSED, false)) {
            getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit().putBoolean(PREF_BACK_PRESSED, false).apply();
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
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
