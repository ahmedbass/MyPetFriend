package com.ahmedbass.mypetfriend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    public void moveToActivity(View view) {
        switch (view.getId()) {

            case R.id.moveToLogin_btn: startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.moveToRegister_btn: startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }
    }
}
