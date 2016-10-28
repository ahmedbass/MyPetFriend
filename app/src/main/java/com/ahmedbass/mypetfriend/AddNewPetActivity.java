package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class AddNewPetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pet);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void SavePetProfile(View view) {
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
//        startActivity(new Intent(this, SetupVaccines.class), bundle);
    }
}
