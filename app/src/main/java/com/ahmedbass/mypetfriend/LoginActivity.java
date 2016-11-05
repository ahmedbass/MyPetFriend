package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import static com.ahmedbass.mypetfriend.WelcomeActivity.MY_PREFS_NAME;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    EditText email_etxt, password_etxt;
    TextView moveToResetPassword_txtv;
    CheckBox rememberMe_chk;
    String email, password, taskType;
    boolean isRememberMe;

    //save login information in preferences when user checks "remember me"
    SharedPreferences pref;
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private static final String PREF_REMEMBERME = "rememberme";

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    Bundle bundle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeMyViews();
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
//                networkInfo = connectivityManager.getActiveNetworkInfo();
//                if(networkInfo != null && networkInfo.isConnected()) {
//                    email = email_etxt.getText().toString().trim();
//                    password = password_etxt.getText().toString().trim();
//                    taskType = "login";
//                    if (isRememberMe) { //if "remember me" is checked, save the login info
//                        getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit()
//                                .putString(PREF_USERNAME, email)
//                                .putString(PREF_PASSWORD, password)
//                                .putBoolean(PREF_REMEMBERME, isRememberMe)
//                                .apply();
//                    } else{ //else clear login info
//                        getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit().clear().apply();
//                    }
//
//                    if (!email.isEmpty() && !password.isEmpty()) {
//                        startActivity(new Intent(getBaseContext(), MainActivity.class), bundle); //TODO remove this
////                        BackgroundWorker backgroundWorker = new BackgroundWorker(Login.this);
////                        backgroundWorker.execute(taskType, username, password);
//                    } else{
//                        Toast.makeText(LoginActivity.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, "Error: Cannot connect to the internet", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getBaseContext(), MainActivity.class), bundle); //TODO remove this
//                }

                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent); //TODO remove this

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
                startActivity(new Intent(getBaseContext(), ForgotPasswordActivity.class), bundle);
            }
        });
    }

    private void initializeMyViews() {
        login_btn = (Button) findViewById(R.id.login_btn);
        email_etxt = (EditText) findViewById(R.id.email_etxt);
        password_etxt = (EditText) findViewById(R.id.password_etxt);
        moveToResetPassword_txtv = (TextView) findViewById(R.id.moveToResetPassword_txtv);
        rememberMe_chk = (CheckBox) findViewById(R.id.rememberMe_chk);

        pref = getSharedPreferences(MY_PREFS_NAME,MODE_PRIVATE);
        email = pref.getString(PREF_USERNAME, null);
        password = pref.getString(PREF_PASSWORD, null);
        isRememberMe = pref.getBoolean(PREF_REMEMBERME, false);
        if (email != null && password != null) {
            email_etxt.setText(email);
            password_etxt.setText(password);
        }
        rememberMe_chk.setChecked(isRememberMe);
        if(!isRememberMe) {
            getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit().clear().apply();
        }
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
