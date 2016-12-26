package com.ahmedbass.mypetfriend;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class PetCareServiceActivity extends AppCompatActivity {

    private ArrayList<PetCareProvider> listOfAllPetCareProviders = new ArrayList<>();
    private ArrayList<PetCareProvider> listOfFilteredPetCareProviders = new ArrayList<>();

    MyPetCareProvidersListAdapter myListAdapter;
    ListView petCareProvidersList;
    private String keyword, country, city;
    private int minPrice, maxPrice;
    private ArrayList<String> serviceProvided = new ArrayList<>();
    private ArrayList<String> serviceProvidedFor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_care_service);

        if (getIntent() != null && getIntent().getSerializableExtra("petCareProvidersInfo") != null) {
            listOfAllPetCareProviders = (ArrayList<PetCareProvider>)getIntent().getSerializableExtra("petCareProvidersInfo");
            //we'll keep listOfAllPetCareProviders as backup in case user doesn't make any filters, but we'll use listOfFilteredPetCareProviders to show results
            listOfFilteredPetCareProviders = new ArrayList<>(listOfAllPetCareProviders); //equaling by constructor is to create new list with new reference
        }
        setupListOfPetCareProviders();
        country = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).getString(MyPetFriendContract.UsersEntry.COLUMN_COUNTRY, "");
        city = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).getString(MyPetFriendContract.UsersEntry.COLUMN_CITY, "");
    }

    private void setupListOfPetCareProviders() {
        petCareProvidersList = (ListView) findViewById(R.id.petCareService_listview);
        myListAdapter = new MyPetCareProvidersListAdapter(this, listOfFilteredPetCareProviders);
        petCareProvidersList.setAdapter(myListAdapter);
        //when click on petCareProvider at that position in list, move to its profile
        petCareProvidersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToPetCareProviderProfile = new Intent(getBaseContext(), PetCareProviderProfileActivity.class);
                moveToPetCareProviderProfile.putExtra("petCareProviderInfo", myListAdapter.getItem(position));
                startActivity(moveToPetCareProviderProfile, ActivityOptions.makeSceneTransitionAnimation(PetCareServiceActivity.this).toBundle());
            }
        });
        petCareProvidersList.setEmptyView(findViewById(R.id.emptyView_txtv));
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
                showSearchDialog();
                return true;

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //long method but don't freak out, its all about getting xml references and assigning values
    private void showSearchDialog() {
        // custom dialog
        final Dialog dialog = new Dialog(this, R.style.mySearchDialogTheme);
        dialog.setContentView(R.layout.dialog_search_pet_care_providers);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Filter Results");

        // set the custom dialog components - text, image and button
        final EditText searchByKeyword_etxt = (EditText) dialog.findViewById(R.id.searchByKeyword_etxt);
        final EditText minPrice_etxt = (EditText) dialog.findViewById(R.id.minPrice_etxt);
        final EditText maxPrice_etxt = (EditText) dialog.findViewById(R.id.maxPrice_etxt);
        final AutoCompleteTextView country_atxt = (AutoCompleteTextView) dialog.findViewById(R.id.country_atxt);
        final EditText city_etxt = (EditText) dialog.findViewById(R.id.city_etxt);
        final CheckBox petSitting_chk = (CheckBox) dialog.findViewById(R.id.petSitting_chk);
        final CheckBox petWalking_chk = (CheckBox) dialog.findViewById(R.id.petWalking_chk);
        final CheckBox petGrooming_chk = (CheckBox) dialog.findViewById(R.id.petGrooming_chk);
        final CheckBox petTraining_chk = (CheckBox) dialog.findViewById(R.id.petTraining_chk);
        final CheckBox petVeterinary_chk = (CheckBox) dialog.findViewById(R.id.petVeterinary_chk);
        final CheckBox petBoarding_chk = (CheckBox) dialog.findViewById(R.id.petBoarding_chk);
        final CheckBox catService_chk = (CheckBox) dialog.findViewById(R.id.catService_chk);
        final CheckBox dogService_chk = (CheckBox) dialog.findViewById(R.id.dogService_chk);
        final CheckBox horseService_chk = (CheckBox) dialog.findViewById(R.id.horseService_chk);
        final CheckBox birdService_chk = (CheckBox) dialog.findViewById(R.id.birdService_chk);
        final CheckBox fishService_chk = (CheckBox) dialog.findViewById(R.id.fishService_chk);
        final CheckBox smallAnimalService_chk = (CheckBox) dialog.findViewById(R.id.smallAnimalService_chk);
        Button search_btn = (Button) dialog.findViewById(R.id.search_btn);
        Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
        TextView clearFilter_txtv = (TextView) dialog.findViewById(R.id.clearFilter_txtv);

        searchByKeyword_etxt.setText(keyword);
        minPrice_etxt.setText(minPrice == 0 ? "" : String.valueOf(minPrice));
        maxPrice_etxt.setText(maxPrice == 0 ? "" : String.valueOf(maxPrice));
        country_atxt.setText(country);
        city_etxt.setText(city);
        petSitting_chk.setChecked(serviceProvided.contains(petSitting_chk.getText().toString()));
        petWalking_chk.setChecked(serviceProvided.contains(petWalking_chk.getText().toString()));
        petGrooming_chk.setChecked(serviceProvided.contains(petGrooming_chk.getText().toString()));
        petTraining_chk.setChecked(serviceProvided.contains(petTraining_chk.getText().toString()));
        petVeterinary_chk.setChecked(serviceProvided.contains(petVeterinary_chk.getText().toString()));
        petBoarding_chk.setChecked(serviceProvided.contains(petBoarding_chk.getText().toString()));
        catService_chk.setChecked(serviceProvidedFor.contains(catService_chk.getText().toString()));
        dogService_chk.setChecked(serviceProvidedFor.contains(dogService_chk.getText().toString()));
        horseService_chk.setChecked(serviceProvidedFor.contains(horseService_chk.getText().toString()));
        birdService_chk.setChecked(serviceProvidedFor.contains(birdService_chk.getText().toString()));
        fishService_chk.setChecked(serviceProvidedFor.contains(fishService_chk.getText().toString()));
        smallAnimalService_chk.setChecked(serviceProvidedFor.contains(smallAnimalService_chk.getText().toString()));

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.countries_array));
        country_atxt.setAdapter(countriesAdapter);
        country_atxt.setThreshold(1);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get values from views and set it in variables
                keyword = searchByKeyword_etxt.getText().toString().trim();
                minPrice = minPrice_etxt.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(minPrice_etxt.getText().toString().trim());
                maxPrice = maxPrice_etxt.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(maxPrice_etxt.getText().toString().trim());
                country = country_atxt.getText().toString().trim();
                city = city_etxt.getText().toString().trim();
                serviceProvided = new ArrayList<>();
                serviceProvidedFor = new ArrayList<>();
                CheckBox[] serviceProvidedChk = {petSitting_chk, petWalking_chk, petGrooming_chk, petTraining_chk, petVeterinary_chk, petBoarding_chk};
                for(CheckBox itemServiceProvidedChk : serviceProvidedChk) {
                    if (itemServiceProvidedChk.isChecked()) { //adding which services to search for
                        serviceProvided.add(itemServiceProvidedChk.getText().toString());
                    }
                }
                CheckBox[] serviceProvidedForChk = {catService_chk, dogService_chk, horseService_chk, birdService_chk, fishService_chk, smallAnimalService_chk};
                for(CheckBox itemServiceProvidedForChk : serviceProvidedForChk) {
                    if (itemServiceProvidedForChk.isChecked()) {//adding which serviceFor to search for
                        serviceProvidedFor.add(itemServiceProvidedForChk.getText().toString());
                    }
                }
                //call search filter method
                filterResults(keyword, minPrice, maxPrice, country, city,
                        serviceProvided.toArray(new String[serviceProvided.size()]), serviceProvidedFor.toArray(new String[serviceProvidedFor.size()]));
                dialog.dismiss();
            }
        });
        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        clearFilter_txtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchByKeyword_etxt.setText("");
                minPrice_etxt.setText("");
                maxPrice_etxt.setText("");
                country_atxt.setText("");
                city_etxt.setText("");
                petSitting_chk.setChecked(false);
                petWalking_chk.setChecked(false);
                petGrooming_chk.setChecked(false);
                petTraining_chk.setChecked(false);
                petVeterinary_chk.setChecked(false);
                petBoarding_chk.setChecked(false);
                catService_chk.setChecked(false);
                dogService_chk.setChecked(false);
                horseService_chk.setChecked(false);
                birdService_chk.setChecked(false);
                fishService_chk.setChecked(false);
                smallAnimalService_chk.setChecked(false);
            }
        });

        dialog.show();
    }

    //here is the method that filters our results based on all the factors passed to it
    private void filterResults(String keyword, int minPrice, int maxPrice, String country, String city, String[] serviceProvided, String[] serviceProvidedFor) {
        listOfFilteredPetCareProviders = new ArrayList<>(listOfAllPetCareProviders);
        if (!(keyword.isEmpty() && minPrice == 0 && maxPrice == 0 && country.isEmpty() && city.isEmpty() && serviceProvided.length == 0 && serviceProvidedFor.length == 0)) {
            outerLoop:
            for (int i = 0; i < listOfFilteredPetCareProviders.size(); i++) {
                if (!keyword.isEmpty()) { //search for keyword in first name, last name and description
                    if (!(listOfFilteredPetCareProviders.get(i).getFirstName().toLowerCase().contains(keyword.toLowerCase()) ||
                            listOfFilteredPetCareProviders.get(i).getLastName().toLowerCase().contains(keyword.toLowerCase()) ||
                            listOfFilteredPetCareProviders.get(i).getProfileDescription().toLowerCase().contains(keyword.toLowerCase()))) {
                        listOfFilteredPetCareProviders.remove(i);
                        i--; //reducing counter by 1 because array size reduces by 1 when deleting, so it fixes the problem i faced of not filtering results properly
                        continue;
                    }
                }
                if (minPrice != 0) {
                    int price = listOfFilteredPetCareProviders.get(i).getAverageRatePerHour().equals("50+") ?
                            51 : Integer.parseInt(listOfFilteredPetCareProviders.get(i).getAverageRatePerHour());
                    if (price < minPrice) {
                        listOfFilteredPetCareProviders.remove(i);
                        i--;
                        continue;
                    }
                }
                if (maxPrice != 0) {
                    int price = listOfFilteredPetCareProviders.get(i).getAverageRatePerHour().equals("50+") ?
                            51 : Integer.parseInt(listOfFilteredPetCareProviders.get(i).getAverageRatePerHour());
                    if (price > maxPrice) {
                        listOfFilteredPetCareProviders.remove(i);
                        i--;
                        continue;
                    }
                }
                if (!country.isEmpty()) {
                    if (!listOfFilteredPetCareProviders.get(i).getCountry().contains(country)) {
                        listOfFilteredPetCareProviders.remove(i);
                        i--;
                        continue;
                    }
                }
                if (!city.isEmpty()) {
                    if (!listOfFilteredPetCareProviders.get(i).getCity().toLowerCase().contains(city.toLowerCase())) {
                        listOfFilteredPetCareProviders.remove(i);
                        i--;
                        continue;
                    }
                }
                for (String itemServiceProvided : serviceProvided) {
                    if (!listOfFilteredPetCareProviders.get(i).getServicesProvided().toLowerCase().contains(itemServiceProvided.toLowerCase())) {
                        listOfFilteredPetCareProviders.remove(i);
                        i--;
                        continue outerLoop;
                    }
                }
                for (String itemServiceProvidedFor : serviceProvidedFor) {
                    if (!listOfFilteredPetCareProviders.get(i).getServicesProvidedFor().toLowerCase().contains(itemServiceProvidedFor.toLowerCase())) {
                        listOfFilteredPetCareProviders.remove(i);
                        i--;
                        continue outerLoop;
                    }
                }
            }
        }
        //then finally update the list after filtering the list
        myListAdapter.clear();
        myListAdapter.addAll(listOfFilteredPetCareProviders);
        petCareProvidersList.setAdapter(myListAdapter);
    }

    //---------------------------------------------------------------------
    class MyPetCareProvidersListAdapter extends ArrayAdapter<PetCareProvider> {

        MyPetCareProvidersListAdapter(Activity context, ArrayList<PetCareProvider> petCareProviders) {
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

            TextView petCareProviderName_txtv = (TextView) listItemView.findViewById(R.id.petCareProviderName_txtv);
            assert currentPetCareProvider != null;
            petCareProviderName_txtv.setText(currentPetCareProvider.getFirstName() + " " + currentPetCareProvider.getLastName().charAt(0) + ".");

            TextView petCareProviderLocation_txtv = (TextView) listItemView.findViewById(R.id.petCareProviderLocation_txtv);
            petCareProviderLocation_txtv.setText(currentPetCareProvider.getCity() + ", " + currentPetCareProvider.getCountry());

            TextView petCareProviderAvailability_txtv = (TextView) listItemView.findViewById(R.id.petCareProviderAvailability_txtv);
            petCareProviderAvailability_txtv.setText((currentPetCareProvider.getAvailability().isEmpty() ?
                    "?" : currentPetCareProvider.getAvailability()));

            TextView petCareProviderExperienceYears_txtv = (TextView) listItemView.findViewById(R.id.petCareProviderExperienceYears_txtv);
            petCareProviderExperienceYears_txtv.setText((currentPetCareProvider.getYearsOfExperience().isEmpty() ?
                    "?" : currentPetCareProvider.getYearsOfExperience()));

            TextView petCareProviderAvergeRatePerHour_txtv = (TextView) listItemView.findViewById(R.id.petCareProviderAverageRatePerHour_txtv);
            petCareProviderAvergeRatePerHour_txtv.setText( currentPetCareProvider.getAverageRatePerHour().isEmpty() ?
                    "?" : "$" + currentPetCareProvider.getAverageRatePerHour() + "/hr");

            return listItemView;
        }
    }
}
