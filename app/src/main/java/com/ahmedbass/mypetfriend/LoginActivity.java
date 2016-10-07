package com.ahmedbass.mypetfriend;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    EditText email_etxt, password_etxt;
    TextView moveToResetPassword_txtv;
    CheckBox rememberMe_chk;
    String email, password, taskType;
    boolean isRememberMe;

    //save login information in preferences when user checks "remember me"
    SharedPreferences pref;
    public static final String PREFS_NAME = "MyLoginPrefs";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String PREF_REMEMBERME = "rememberme";

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeMyViews();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()) {
                    email = email_etxt.getText().toString().trim();
                    password = password_etxt.getText().toString().trim();
                    taskType = "login";
                    if (isRememberMe) { //if "remember me" is checked, save the login info
                        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
                                .putString(PREF_USERNAME, email)
                                .putString(PREF_PASSWORD, password)
                                .putBoolean(PREF_REMEMBERME, isRememberMe)
                                .apply();
                    } else{ //else clear login info
                        getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().clear().apply();
                    }

                    if (!email.isEmpty() && !password.isEmpty()) {
                        startActivity(new Intent(getBaseContext(), MainActivity.class)); //TODO remove this
//                        BackgroundWorker backgroundWorker = new BackgroundWorker(Login.this);
//                        backgroundWorker.execute(taskType, username, password);
                    } else{
                        startActivity(new Intent(getBaseContext(), MainActivity.class)); //TODO remove this
                        finish();
                        Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Error: Cannot connect to the internet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rememberMe_chk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isRememberMe = isChecked;
            }
        });

        moveToResetPassword_txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ForgotPasswordActivity.class));
            }
        });
    }

    private void initializeMyViews() {
        login_btn = (Button) findViewById(R.id.login_btn);
        email_etxt = (EditText) findViewById(R.id.email_etxt);
        password_etxt = (EditText) findViewById(R.id.password_etxt);
        moveToResetPassword_txtv = (TextView) findViewById(R.id.moveToResetPassword_txtv);
        rememberMe_chk = (CheckBox) findViewById(R.id.rememberMe_chk);

        pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        email = pref.getString(PREF_USERNAME, null);
        password = pref.getString(PREF_PASSWORD, null);
        isRememberMe = pref.getBoolean(PREF_REMEMBERME, false);
        if (email != null && password != null) {
            email_etxt.setText(email);
            password_etxt.setText(password);
        }
        rememberMe_chk.setChecked(isRememberMe);
        if(!isRememberMe) {
            getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit().clear().apply();
        }
    }
}
