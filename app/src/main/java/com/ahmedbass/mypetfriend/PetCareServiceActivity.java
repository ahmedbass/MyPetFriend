package com.ahmedbass.mypetfriend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
    ListView petCareProvidersList;
    PetCareProvidersListAdapter adapter;

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
        adapter = new PetCareProvidersListAdapter(this, listOfPetCareProviders);

        //handle listview and assign adapter
        petCareProvidersList = (ListView)findViewById(R.id.petCareService_listview);
        petCareProvidersList.setAdapter(adapter);
        //when click on existing pet, move to its profile
        petCareProvidersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToPetCareProviderProfile = new Intent(getBaseContext(), PetCareProviderProfileActivity.class);
                moveToPetCareProviderProfile.putExtra("petCareProviderInfo", adapter.getItem(position));
                startActivity(moveToPetCareProviderProfile);
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

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //---------------------------------------------------------------------

    class PetCareProvidersListAdapter extends ArrayAdapter<PetCareProvider> {

        PetCareProvidersListAdapter(Activity context, ArrayList<PetCareProvider> petCareProviders) {
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

//        ImageView petPhoto = (ImageView) listItemView.findViewById(R.id.list_pet_thumbnail);
//        if (!currentPet.getAllPhotos().contains(currentPet)) {
//            petPhoto.setImageBitmap(currentPet.getAllPhotos().get(0));
//            Toast.makeText(getContext(), "TODO: should change the thumbnail based on pet's top photo", Toast.LENGTH_SHORT).show();
//        }
//            TextView petCareProviderName = (TextView) listItemView.findViewById(R.id.list_pet_name);
//            petName.setText(currentPetCareProvider.getName());

            return listItemView;
        }
    }
}
