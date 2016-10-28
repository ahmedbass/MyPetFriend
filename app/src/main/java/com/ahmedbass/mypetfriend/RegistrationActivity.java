package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText firstName_etxt, lastName_etxt, password_etxt, email_etxt;
    AutoCompleteTextView location_etxt;
    RadioGroup usertype_rgrp;
    RadioButton petOwner_rbtn, petCareProvider_rbtn;
    Button register_btn;
    String firstName, lastName, email, password, location;
    int usertype; //1=petOwner, 2=petCareProvider

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    Bundle bundle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        initializeMyViews();
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        register_btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                networkInfo = connectivityManager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()) {
                    firstName = firstName_etxt.getText().toString().trim();
                    lastName = lastName_etxt.getText().toString().trim();
                    password = password_etxt.getText().toString().trim();
                    email = email_etxt.getText().toString().trim();
                    location = location_etxt.getText().toString().trim();
                    //phone = phone_etxt.getText().toString().trim();
                    usertype = (usertype_rgrp.getCheckedRadioButtonId() == petOwner_rbtn.getId() ? 1 :
                            (usertype_rgrp.getCheckedRadioButtonId() == petCareProvider_rbtn.getId() ? 2 : 0));
                    String taskType = "register";

                    if(firstName.isEmpty()|| lastName.isEmpty()|| password.isEmpty()|| email.isEmpty()|| location.isEmpty()|| usertype==0) {
                        Toast.makeText(getBaseContext(), "Please fill-in all fields", Toast.LENGTH_SHORT).show();

                        //TODO remove this
                        if(usertype == 1){
                            startActivity(new Intent(getBaseContext(), MainActivity.class), bundle);
                        } else if(usertype == 2){
                            startActivity(new Intent(getBaseContext(), PetCareProviderRegisterationActivity.class), bundle);
                        }
                    } else {
                        //TODO use the background worker instead
                        if(usertype == 1){
                            startActivity(new Intent(getBaseContext(), MainActivity.class), bundle);
                        } else if(usertype == 2){
                            startActivity(new Intent(getBaseContext(), PetCareProviderRegisterationActivity.class), bundle);
                        }
//                        BackgroundWorker backgroundWorker = new BackgroundWorker(getBaseContext());
//                        backgroundWorker.execute(taskType, firstName, password, lastName, usertype);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Error: Cannot connect to the internet", Toast.LENGTH_SHORT).show();

                    //TODO remove this
                    if(usertype == 2){
                        startActivity(new Intent(getBaseContext(), PetCareProviderRegisterationActivity.class), bundle);
                    } else {
                        startActivity(new Intent(getBaseContext(), MainActivity.class), bundle); //TODO remove this
                    }
                }
            }
        });
    }

    private void initializeMyViews() {

        register_btn = (Button) findViewById(R.id.register_btn);
        firstName_etxt = (EditText) findViewById(R.id.firstname_etxt);
        lastName_etxt = (EditText) findViewById(R.id.lastname_etxt);
        password_etxt = (EditText) findViewById(R.id.password_etxt);
        email_etxt = (EditText) findViewById(R.id.email_etxt);
        location_etxt = (AutoCompleteTextView) findViewById(R.id.location_etxt);
        usertype_rgrp = (RadioGroup) findViewById(R.id.usertype_rgrp);
        petOwner_rbtn = (RadioButton) findViewById(R.id.petOwner_rbtn);
        petCareProvider_rbtn = (RadioButton) findViewById(R.id.petCareProvider_rbtn);
    }

    public void moveToSignIn(View view) {
        startActivity(new Intent(this, LoginActivity.class), bundle);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
