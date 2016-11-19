package com.ahmedbass.mypetfriend;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PetCareServiceActivity extends AppCompatActivity {

    private ArrayList<PetCareProvider> listOfPetCareProviders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_petcare_service);

        generateList();
    }

    private void generateList() {
        //generate fake list
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());
        listOfPetCareProviders.add(new PetCareProvider());

        //instantiate custom adapter
        final MyPetCareProviderListAdapter adapter = new MyPetCareProviderListAdapter(this, listOfPetCareProviders);

        //handle listview and assign adapter
        ListView petCareProvidersList = (ListView) findViewById(R.id.petCareService_listview);
        petCareProvidersList.setAdapter(adapter);
        //when click on existing pet, move to its profile
        petCareProvidersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToPetCareProviderProfile = new Intent(getBaseContext(), PetCareProviderProfileActivity.class);
                moveToPetCareProviderProfile.putExtra("petCareProviderInfo", adapter.getItem(position));
                startActivity(moveToPetCareProviderProfile, ActivityOptions.makeSceneTransitionAnimation(PetCareServiceActivity.this).toBundle());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_filter_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Toast.makeText(this, "TODO: popup dialog for search refinement", Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //---------------------------------------------------------------------
    class MyPetCareProviderListAdapter extends ArrayAdapter<PetCareProvider> {

        MyPetCareProviderListAdapter(Activity context, ArrayList<PetCareProvider> petCareProviders) {
            super(context, 0, petCareProviders);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_pet_care_provider, parent, false);
            }

            // Get the PetCareProvider object located at this position in the list
            PetCareProvider currentPetCareProvider = getItem(position);

//            TextView petCareProviderName = (TextView) listItemView.findViewById(R.id.list_pet_name);
//            petName_txtv.setText(currentPetCareProvider.getName());

            return listItemView;
        }
    }
}
