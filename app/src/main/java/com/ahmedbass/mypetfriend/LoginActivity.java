package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Toast;

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;
import static com.ahmedbass.mypetfriend.LauncherActivity.MY_APP_PREFS;
import static com.ahmedbass.mypetfriend.LauncherActivity.PREF_LOGGED_IN;

public class LoginActivity extends AppCompatActivity {

    Button login_btn;
    EditText email_etxt, password_etxt;
    TextView moveToResetPassword_txtv;
    CheckBox rememberMe_chk;
    String email, password, taskType;
    boolean isRememberMe;

    //save login information in preferences when user checks "remember me"
    private static final String PREF_EMAIL = "email";
    private static final String PREF_PASSWORD = "password";
    public static final String PREF_REMEMBER_ME = "rememberMe";

    SharedPreferences pref;
    Bundle bundle;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeMyViews();
        bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        login_btn.setOnClickListener(new View.OnClickListener() {
            ConnectivityManager connectivityManager;
            NetworkInfo networkInfo;
            @Override
            public void onClick(View v) {
                email = email_etxt.getText().toString().trim();
                password = password_etxt.getText().toString().trim();
                if (!email.isEmpty() && !password.isEmpty()) {
                    if (isRememberMe) { //if "remember me" is checked, save the login info
                        getSharedPreferences(MY_APP_PREFS, MODE_PRIVATE).edit()
                                .putString(PREF_EMAIL, email)
                                .putString(PREF_PASSWORD, password)
                                .putBoolean(PREF_REMEMBER_ME, isRememberMe)
                                .apply();
                    } else { //else clear login info
                        getSharedPreferences(MY_APP_PREFS, MODE_PRIVATE).edit().clear().apply();
                    }
                    connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                    networkInfo = connectivityManager.getActiveNetworkInfo();
                    if(networkInfo != null && networkInfo.isConnected()) {
                            taskType = "login";
                            BackgroundWorker backgroundWorker = new BackgroundWorker(LoginActivity.this);
                            backgroundWorker.execute(taskType, email, password);
                    } else {
                        //if no internet connection, try to find user in the local database
                        PetOwner petOwner = getUserInfoFromLocalDatabase();
                        if (petOwner != null) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            intent.putExtra("userInfo", petOwner);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getBaseContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
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
                startActivity(new Intent(getBaseContext(), ForgotPasswordActivity.class), bundle);
            }
        });
    }

    private PetOwner getUserInfoFromLocalDatabase() {
        MyDBHelper dbHelper = new MyDBHelper(getBaseContext());
        dbHelper.open();
        Cursor cursor = dbHelper.getRecord(MyPetFriendContract.UsersEntry.TABLE_NAME, null,
                new String[]{MyPetFriendContract.UsersEntry.COLUMN_EMAIL, MyPetFriendContract.UsersEntry.COLUMN_PASSWORD}, new String[]{email, password});

        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
            getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).edit().putBoolean(PREF_LOGGED_IN, true).apply();
            if (cursor.getString(2).equals(MyPetFriendContract.UsersEntry.USER_TYPE_PET_CARE_PROVIDER)) {
                PetCareProvider petCareProvider = new PetCareProvider(cursor.getInt(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getLong(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), cursor.getString(13),
                        cursor.getString(14), cursor.getString(15), cursor.getString(16), cursor.getString(17), cursor.getString(18), 0, 0);
                cursor.close();
                dbHelper.close();
                return petCareProvider;
            } else {
                //if userType != PetCareProvider, assume it's PetOwner
                PetOwner petOwner = new PetOwner(cursor.getInt(0), cursor.getLong(1), cursor.getString(2), cursor.getString(3),
                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getLong(7), cursor.getString(8),
                        cursor.getString(9), cursor.getString(10), cursor.getString(11), cursor.getString(12), 0, 0);
                cursor.close();
                dbHelper.close();
                return petOwner;
            }
        } else {
            Toast.makeText(this, "Login Failed: Wrong user name or password", Toast.LENGTH_SHORT).show();
            cursor.close();
            dbHelper.close();
            return null;
        }
    }

    private void initializeMyViews() {
        login_btn = (Button) findViewById(R.id.login_btn);
        email_etxt = (EditText) findViewById(R.id.email_etxt);
        password_etxt = (EditText) findViewById(R.id.password_etxt);
        moveToResetPassword_txtv = (TextView) findViewById(R.id.moveToResetPassword_txtv);
        rememberMe_chk = (CheckBox) findViewById(R.id.rememberMe_chk);

        pref = getSharedPreferences(MY_APP_PREFS, MODE_PRIVATE);
        email = pref.getString(PREF_EMAIL, null);
        password = pref.getString(PREF_PASSWORD, null);
        isRememberMe = pref.getBoolean(PREF_REMEMBER_ME, false);
        if (email != null && password != null) {
            email_etxt.setText(email);
            password_etxt.setText(password);
        }
        rememberMe_chk.setChecked(isRememberMe);
        if (!isRememberMe) {
            getSharedPreferences(MY_APP_PREFS, MODE_PRIVATE).edit().clear().apply();
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
