package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

import static com.ahmedbass.mypetfriend.MyDBHelper.DATABASE_NAME;

public class LauncherActivity extends AppCompatActivity {

    public static final String MY_APP_PREFS = "com.ahmedbass.mypetfriend.MY_APP_PREFS";
    public static final String CURRENT_USER_INFO_PREFS = "com.ahmedbass.mypetfriend.CURRENT_USER_INFO";
    public static final String PREF_LOGGED_IN = "USER_LOGGED_IN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        //check if activity is launching after pressing back from MainActivity, to move directly to it
        SharedPreferences pref = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE);
        if (pref.getBoolean(PREF_LOGGED_IN, false)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        //when clicking logo_img 7 times, show dialog to set server domain url
        findViewById(R.id.app_logo_img).setOnClickListener(new View.OnClickListener() {
            int setDomainClickCount = 0;

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                setDomainClickCount++;
                if (setDomainClickCount >= 7) {
                    setDomainClickCount = 0;
                    final EditText editText = new EditText(getBaseContext());
                    editText.setTextColor(Color.BLACK);
                    editText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    AlertDialog.Builder alert = new AlertDialog.Builder(LauncherActivity.this);
                    alert.setTitle("Set Domain Server URL").setView(editText)
                            .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    if (!editText.getText().toString().trim().isEmpty()) {
                                        BackgroundWorker.setServerDomainUrl(editText.getText().toString());
                                    }
                                }
                            })
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            })
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    Toast.makeText(LauncherActivity.this, "New Domain URL: " + BackgroundWorker.serverDomain, Toast.LENGTH_SHORT).show();
                                }
                            })
                            .show()
                            .setCancelable(false);
                }
            }
        });

        //copy database from assets to the android device, if it doesn't already exist (which is only the very first time app is launched)
        try {
            String destPath = getDatabasePath(DATABASE_NAME).getPath();
            File file = new File(destPath);
            if (!file.exists()) {
                //this opening before copying db, is the solution to unable to copy db
                MyDBHelper dbHelper = new MyDBHelper(this);
                dbHelper.open();
                dbHelper.close();
                MyDBHelper.copyDB(this, destPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
