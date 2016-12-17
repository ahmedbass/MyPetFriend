package com.ahmedbass.mypetfriend;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class PetCareProviderProfileActivity extends AppCompatActivity {

    ImageView petCareProviderPhoto_img;
    TextView petCareProviderLocation_txtv, petCareProviderAvailability_txtv, petCareProviderExperienceYears_txtv,
            petCareProviderAverageRatePerHour_txtv, petCareProviderCreateDate_txtv, petCareProviderName_txtv,
            petCareProviderProfileDescription_txtv, petCareProviderServiceProvidedFor_txtv, petCareProviderServiceProvided_txtv;
    Button petCareProviderPhone_btn, petCareProviderEmail_btn;
    PetCareProvider currentPetCareProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_care_provider_profile);

        if (getIntent() != null && getIntent().getSerializableExtra("petCareProviderInfo") != null) {
            currentPetCareProvider = (PetCareProvider) getIntent().getSerializableExtra("petCareProviderInfo");
        } else {
            Toast.makeText(this, "Error: Cannot Retrieve PetCareProvider Information", Toast.LENGTH_SHORT).show();
        }

        initializeMyViews();
        setUserInfoInViews();
    }

    private void setUserInfoInViews() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(currentPetCareProvider.getFirstName() + " " + currentPetCareProvider.getLastName());
        }
        petCareProviderLocation_txtv.setText(currentPetCareProvider.getCity() + ", " + currentPetCareProvider.getCountry());
        petCareProviderAvailability_txtv.setText(currentPetCareProvider.getAvailability().isEmpty() ?
                "? Availability" : currentPetCareProvider.getAvailability() + " Pet Setter");
        petCareProviderExperienceYears_txtv.setText((currentPetCareProvider.getYearsOfExperience().isEmpty() ?
                "?" : currentPetCareProvider.getYearsOfExperience()) + " YRS EXP.");
        petCareProviderAverageRatePerHour_txtv.setText((currentPetCareProvider.getAverageRatePerHour().isEmpty() ?
                "?" : "$" + currentPetCareProvider.getAverageRatePerHour()) + " HOURLY RATE");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentPetCareProvider.getCreateDate());
        petCareProviderCreateDate_txtv.setText("Member Since " +
                calendar.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault()) + " " + calendar.get(Calendar.YEAR));
        petCareProviderName_txtv.setText("Meet " + currentPetCareProvider.getFirstName());
        petCareProviderProfileDescription_txtv.setText(currentPetCareProvider.getProfileDescription().isEmpty() ?
                "Description Not Available" : currentPetCareProvider.getProfileDescription());
        petCareProviderServiceProvidedFor_txtv.setText(currentPetCareProvider.getServicesProvidedFor().isEmpty() ?
                "Nothing" : currentPetCareProvider.getServicesProvidedFor());
        petCareProviderServiceProvided_txtv.setText(currentPetCareProvider.getServicesProvided().isEmpty() ?
                "Nothing" : currentPetCareProvider.getServicesProvided());
        if (currentPetCareProvider.getPhone().isEmpty()) {
            petCareProviderPhone_btn.setEnabled(false);
            petCareProviderPhone_btn.setText("Phone Number Unavailable");
        } else {
            petCareProviderPhone_btn.setText("Phone: " + currentPetCareProvider.getPhone());
        }
        if(currentPetCareProvider.getEmail().isEmpty()) {
            petCareProviderEmail_btn.setEnabled(false);
            petCareProviderEmail_btn.setText("Email Unavailable");
        } else {
            petCareProviderEmail_btn.setText("Email: " + currentPetCareProvider.getEmail());
        }
    }

    private void initializeMyViews() {
        petCareProviderPhoto_img = (ImageView) findViewById(R.id.petCareProviderPhoto_img);
        petCareProviderLocation_txtv = (TextView) findViewById(R.id.petCareProviderLocation_txtv);
        petCareProviderAvailability_txtv = (TextView) findViewById(R.id.petCareProviderAvailability_txtv);
        petCareProviderExperienceYears_txtv = (TextView) findViewById(R.id.petCareProviderExperienceYears_txtv);
        petCareProviderAverageRatePerHour_txtv = (TextView) findViewById(R.id.petCareProviderAverageRatePerHour_txtv);
        petCareProviderCreateDate_txtv = (TextView) findViewById(R.id.petCareProviderCreateDate_txtv);
        petCareProviderName_txtv = (TextView) findViewById(R.id.petCareProviderName_txtv);
        petCareProviderProfileDescription_txtv = (TextView) findViewById(R.id.petCareProviderProfileDescription_txtv);
        petCareProviderServiceProvidedFor_txtv = (TextView) findViewById(R.id.petCareProviderServiceProvidedFor_txtv);
        petCareProviderServiceProvided_txtv = (TextView) findViewById(R.id.petCareProviderServiceProvided_txtv);
        petCareProviderPhone_btn = (Button) findViewById(R.id.petCareProviderPhone_btn);
        petCareProviderEmail_btn = (Button) findViewById(R.id.petCareProviderEmail_btn);
    }

    public void contactPetCareProvider(View view) {
        switch (view.getId()) {
            case R.id.petCareProviderPhone_btn:
                String petCareProviderPhoneNumber = currentPetCareProvider.getPhone();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + petCareProviderPhoneNumber));
                if (phoneIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(phoneIntent);
                }
                break;
            case R.id.petCareProviderEmail_btn:
                String petCareProviderEmail = currentPetCareProvider.getEmail();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{petCareProviderEmail});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My PetFriend Care Request");
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public void showProfilePhoto(View view) {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setCanceledOnTouchOutside(true);

        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(petCareProviderPhoto_img.getDrawable());
        imageView.setPadding(10, 10, 10, 10);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_actions_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Toast.makeText(this, "TODO: user profile saved in your favorite section", Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}