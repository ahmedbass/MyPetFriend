package com.ahmedbass.mypetfriend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PetCareProviderProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_care_provider_profile);

        populateReviews();

    }

    private void populateReviews() {
        LinearLayout reviews_lout = (LinearLayout) findViewById(R.id.reviews_lout);
        String[] tv = {"This Pet sitter is great", "I'd recommend working with this caregiver again. ", "blablablablabla",
                "This Pet sitter is great", "I'd recommend working with this caregiver again. ", "blablablablabla",
                "This Pet sitter is great", "I'd recommend working with this caregiver again. ", "blablablablabla",
                "This Pet sitter is great", "I'd recommend working with this caregiver again. ", "blablablablabla"};

        for (int i  = 0; i < tv.length; i++) {
            TextView singleReview = new TextView(this);
            singleReview.setText((i+1) + ". " + tv[i]);

            View separateLine = new View(this);
            separateLine.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1));
            separateLine.setBackgroundColor(Color.parseColor("#aaaaaa"));

            reviews_lout.addView(singleReview);
            reviews_lout.addView(separateLine);
        }
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
            case R.id.action_contact:
                Toast.makeText(this, "TODO: popup dialog to choose between phone call, email message, or request message", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_save:
                Toast.makeText(this, "TODO: user profile saved in your favorite section", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

}