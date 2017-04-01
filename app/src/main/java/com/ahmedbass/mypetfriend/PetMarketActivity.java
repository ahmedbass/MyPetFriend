package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class PetMarketActivity extends AppCompatActivity {

    ListView advertsList;
    MyAdvertsAdapter myListAdapter;
    private ArrayList<Advert> listOfAllAdverts = new ArrayList<>();
    private ArrayList<Advert> listOfFilteredAdverts = new ArrayList<>();

    private String keyword, advertType, petType, petBreed, country, city;
    private int minPrice, maxPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_market);

        if (getIntent() != null && getIntent().getSerializableExtra("advertsInfo") != null) {
            listOfAllAdverts = (ArrayList<Advert>) getIntent().getSerializableExtra("advertsInfo");
            //we'll keep listOfAllAdverts as backup in case user doesn't make any filters, but we'll use listOfFilteredAdverts to show results
            listOfFilteredAdverts = new ArrayList<>(listOfAllAdverts);
        }
        setUpListOfAdverts();
        country = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).getString(MyPetFriendContract.UsersEntry.COLUMN_COUNTRY, "");
        city = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).getString(MyPetFriendContract.UsersEntry.COLUMN_CITY, "");
    }

    private void setUpListOfAdverts() {
        advertsList = (ListView) findViewById(R.id.petShopDeals_listview);
        myListAdapter = new MyAdvertsAdapter(this, listOfFilteredAdverts);
        advertsList.setAdapter(myListAdapter);
        //when click on existing pet, move to its profile
        advertsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToPetProfile = new Intent(getBaseContext(), AdvertActivity.class);
                moveToPetProfile.putExtra("advertInfo", myListAdapter.getItem(position));
                startActivity(moveToPetProfile, ActivityOptions.makeSceneTransitionAnimation(PetMarketActivity.this).toBundle());
            }
        });
        TextView emptyListView_txtv = (TextView) findViewById(R.id.emptyView_txtv);
        advertsList.setEmptyView(emptyListView_txtv);
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
        dialog.setContentView(R.layout.dialog_search_adverts);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setTitle("Filter Results");

        // set the custom dialog components - text, image and button
        final EditText searchByKeyword_etxt = (EditText) dialog.findViewById(R.id.searchByKeyword_etxt);
        final Spinner advertType_spnr = (Spinner) dialog.findViewById(R.id.advertType_spnr);
        final Spinner petType_spnr = (Spinner) dialog.findViewById(R.id.petType_spnr);
        final AutoCompleteTextView petBreed_atxt = (AutoCompleteTextView) dialog.findViewById(R.id.petBreed_atxt);
        final EditText minPrice_etxt = (EditText) dialog.findViewById(R.id.minPrice_etxt);
        final EditText maxPrice_etxt = (EditText) dialog.findViewById(R.id.maxPrice_etxt);
        final AutoCompleteTextView country_atxt = (AutoCompleteTextView) dialog.findViewById(R.id.country_atxt);
        final EditText city_etxt = (EditText) dialog.findViewById(R.id.city_etxt);
        Button search_btn = (Button) dialog.findViewById(R.id.search_btn);
        Button cancel_btn = (Button) dialog.findViewById(R.id.cancel_btn);
        TextView clearFilter_txtv = (TextView) dialog.findViewById(R.id.clearFilter_txtv);

        ArrayAdapter<String> advertTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.advert_types));
        ArrayAdapter<String> petTypeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.pet_types));

        ArrayAdapter<String> countriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.countries_array));
        country_atxt.setAdapter(countriesAdapter);
        country_atxt.setThreshold(1);

        searchByKeyword_etxt.setText(keyword);
        advertType_spnr.setSelection(advertTypeAdapter.getPosition(advertType));
        petType_spnr.setSelection(petTypeAdapter.getPosition(petType));
        petBreed_atxt.setText(petBreed);
        minPrice_etxt.setText(minPrice == 0 ? "" : String.valueOf(minPrice));
        maxPrice_etxt.setText(maxPrice == 0 ? "" : String.valueOf(maxPrice));
        country_atxt.setText(country);
        city_etxt.setText(city);

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get values from views and set it in variables
                keyword = searchByKeyword_etxt.getText().toString().trim();
                advertType = advertType_spnr.getSelectedItemPosition() == 0 ? "" : advertType_spnr.getSelectedItem().toString();
                petType = petType_spnr.getSelectedItemPosition() == 0 ? "" : petType_spnr.getSelectedItem().toString();
                petBreed = petBreed_atxt.getText().toString();
                minPrice = minPrice_etxt.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(minPrice_etxt.getText().toString().trim());
                maxPrice = maxPrice_etxt.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(maxPrice_etxt.getText().toString().trim());
                country = country_atxt.getText().toString().trim();
                city = city_etxt.getText().toString().trim();

                //call search filter method
                filterResults(keyword, advertType, petType, petBreed, minPrice, maxPrice, country, city);
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
                advertType_spnr.setSelection(0);
                petType_spnr.setSelection(0);
                petBreed_atxt.setText("");
                minPrice_etxt.setText("");
                maxPrice_etxt.setText("");
                country_atxt.setText("");
                city_etxt.setText("");
            }
        });

        dialog.show();
    }

    //here is the method that filters our results based on all the factors passed to it
    private void filterResults(String keyword, String advertType, String petType, String petBreed, int minPrice, int maxPrice, String country, String city) {
        listOfFilteredAdverts = new ArrayList<>(listOfAllAdverts);
        if (!(keyword.isEmpty() && advertType.isEmpty() && petType.isEmpty() && petBreed.isEmpty() && minPrice == 0 && maxPrice == 0 && country.isEmpty() && city.isEmpty())) {
            for (int i = 0; i < listOfFilteredAdverts.size(); i++) {
                if (!keyword.isEmpty()) { //search for keyword in first name, last name and description
                    if (!(listOfFilteredAdverts.get(i).getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                            listOfFilteredAdverts.get(i).getDetails().toLowerCase().contains(keyword.toLowerCase()))) {
                        listOfFilteredAdverts.remove(i);
                        i--; //reducing counter by 1 because array size reduces by 1 when deleting, so it fixes the problem i faced of not filtering results properly
                        continue;
                    }
                }
                if (!advertType.isEmpty()) {
                    if (!listOfFilteredAdverts.get(i).getType().equals(advertType)) {
                        listOfFilteredAdverts.remove(i);
                        i--;
                        continue;
                    }
                }
                if (!petType.isEmpty()) {
                    if (!listOfFilteredAdverts.get(i).getPetType().contains(petType)) {
                        listOfFilteredAdverts.remove(i);
                        i--;
                        continue;
                    }
                }
                if (!petBreed.isEmpty()) {
                    if (!listOfFilteredAdverts.get(i).getPetBreed().contains(petBreed)) {
                        listOfFilteredAdverts.remove(i);
                        i--;
                        continue;
                    }
                }
                if (minPrice != 0) {
                    if (listOfFilteredAdverts.get(i).getPrice() < minPrice) {
                        listOfFilteredAdverts.remove(i);
                        i--;
                        continue;
                    }
                }
                if (maxPrice != 0) {
                    if (listOfFilteredAdverts.get(i).getPrice() > maxPrice) {
                        listOfFilteredAdverts.remove(i);
                        i--;
                        continue;
                    }
                }
                if (!country.isEmpty()) {
                    if (!listOfFilteredAdverts.get(i).getCountry().contains(country)) {
                        listOfFilteredAdverts.remove(i);
                        i--;
                        continue;
                    }
                }
                if (!city.isEmpty()) {
                    if (!listOfFilteredAdverts.get(i).getCity().toLowerCase().contains(city.toLowerCase())) {
                        listOfFilteredAdverts.remove(i);
                        i--;
                        continue;
                    }
                }
            }
        }
        //then finally update the list after filtering the list
        myListAdapter.clear();
        myListAdapter.addAll(listOfFilteredAdverts);
        advertsList.setAdapter(myListAdapter);
    }

    //---------------------------------------------------------------------
    class MyAdvertsAdapter extends ArrayAdapter<Advert> {

        Advert currentAdvert;

        MyAdvertsAdapter(Context context, ArrayList<Advert> adverts) {
            super(context, 0, adverts);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_advert, parent, false);
            }

            // Get the Advert object located at this position in the list
            /*Advert*/
            currentAdvert = getItem(position);

//          ImageView petPhoto = (ImageView) listItemView.findViewById(R.id.list_pet_thumbnail);
//          TODO: change the thumbnail based on currentAdvert's top photo

            TextView advertTitle_txtv = (TextView) listItemView.findViewById(R.id.advertTitle_txtv);
            assert currentAdvert != null;
            advertTitle_txtv.setText(currentAdvert.getTitle());

            TextView advertLocation_txtv = (TextView) listItemView.findViewById(R.id.advertLocation_txtv);
            advertLocation_txtv.setText((currentAdvert.getCity().isEmpty() && currentAdvert.getCountry().isEmpty()) ?
                    "Location Not Available" : currentAdvert.getCity() + ", " + currentAdvert.getCountry());

            TextView advertCreateDate_txtv = (TextView) listItemView.findViewById(R.id.advertCreateDate_txtv);
            advertCreateDate_txtv.setText(setDisplayAdvertCreateDate(currentAdvert.getCreateDate()));

            TextView advertPrice_txtv = (TextView) listItemView.findViewById(R.id.advertPrice_txtv);
            advertPrice_txtv.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(currentAdvert.getPrice()));

            return listItemView;
        }

        private String setDisplayAdvertCreateDate(long createDate) {
            String displayedAdvertCreateDate;
            Calendar calendarCreate = Calendar.getInstance();
            Calendar calendarCurrent = Calendar.getInstance();
            calendarCreate.setTimeInMillis(createDate);

            int differenceInYears = calendarCurrent.get(Calendar.YEAR) - calendarCreate.get(Calendar.YEAR);
            int differenceInMonths = calendarCurrent.get(Calendar.MONTH) - calendarCreate.get(Calendar.MONTH);
            int differenceInWeeks = calendarCurrent.get(Calendar.WEEK_OF_YEAR) - calendarCreate.get(Calendar.WEEK_OF_YEAR);
            int differenceInDays = calendarCurrent.get(Calendar.DAY_OF_YEAR) - calendarCreate.get(Calendar.DAY_OF_YEAR);
            int differenceInHours = calendarCurrent.get(Calendar.HOUR_OF_DAY) - calendarCreate.get(Calendar.HOUR_OF_DAY);

            if (differenceInYears == 0) {
                if (differenceInMonths == 0) {
                    if (differenceInWeeks == 0) {
                        if (differenceInDays == 0) {
                            if (differenceInHours == 0) {
                                if (calendarCurrent.get(Calendar.MINUTE) - calendarCreate.get(Calendar.MINUTE) == 0) {
                                    displayedAdvertCreateDate = "Now";
                                } else {
                                    displayedAdvertCreateDate = calendarCurrent.get(Calendar.MINUTE) - calendarCreate.get(Calendar.MINUTE) + " minutes ago";
                                }
                            } else {
                                displayedAdvertCreateDate = differenceInHours + (differenceInHours == 1 ? " hour ago" : " hours ago");
                            }
                        } else {
                            displayedAdvertCreateDate = differenceInDays + (differenceInDays == 1 ? " day ago" : " days ago");
                        }
                    } else {
                        displayedAdvertCreateDate = differenceInWeeks + (differenceInWeeks == 1 ? " week ago" : " weeks ago");
                    }
                } else {
                    displayedAdvertCreateDate = differenceInMonths + (differenceInMonths == 1 ? " month ago" : " months ago");
                }
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                displayedAdvertCreateDate = sdf.format(createDate);
            }

            return displayedAdvertCreateDate;
        }
    }
}
