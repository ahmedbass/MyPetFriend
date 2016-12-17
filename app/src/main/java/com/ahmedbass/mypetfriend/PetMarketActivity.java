package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class PetMarketActivity extends AppCompatActivity {

    ListView advertsList;
    private ArrayList<Advert> listOfAllAdverts = new ArrayList<>();
    private ArrayList<Advert> listOfFilteredAdverts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_market);

        if (getIntent() != null && getIntent().getSerializableExtra("advertsInfo") != null) {
            listOfAllAdverts = (ArrayList<Advert>) getIntent().getSerializableExtra("advertsInfo");
            //we'll keep listOfAllAdverts as backup in case user doesn't make any filters, but we'll use listOfFilteredAdverts to show results
            listOfFilteredAdverts = listOfAllAdverts;
        }
        setUpListOfAdverts();

    }

    private void setUpListOfAdverts() {
        advertsList = (ListView) findViewById(R.id.petShopDeals_listview);
        final MyAdvertsAdapter adapter = new MyAdvertsAdapter(this, listOfFilteredAdverts);
        advertsList.setAdapter(adapter);
        //when click on existing pet, move to its profile
        advertsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToPetProfile = new Intent(getBaseContext(), AdvertActivity.class);
                moveToPetProfile.putExtra("advertInfo", adapter.getItem(position));
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
    class MyAdvertsAdapter extends ArrayAdapter<Advert> {

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
            Advert currentAdvert = getItem(position);

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
    }

    private String setDisplayAdvertCreateDate(long createDate) {
        String displayedAdvertCreateDate = "Not Available";
        Calendar calendarCreate = Calendar.getInstance();
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCreate.setTimeInMillis(createDate);

        int differenceInYears = calendarCurrent.get(Calendar.YEAR) - calendarCreate.get(Calendar.YEAR);
        int differenceInMonths = calendarCurrent.get(Calendar.MONTH) - calendarCreate.get(Calendar.MONTH);
        int differenceInWeeks = calendarCurrent.get(Calendar.WEEK_OF_YEAR) - calendarCreate.get(Calendar.WEEK_OF_YEAR);
        int differenceInDays = calendarCurrent.get(Calendar.DAY_OF_YEAR) - calendarCreate.get(Calendar.DAY_OF_YEAR);

        if(differenceInYears == 0) {
            if (differenceInMonths == 0) {
                if (differenceInWeeks == 0) {
                    if (differenceInDays == 0) {
                        if (calendarCurrent.get(Calendar.HOUR_OF_DAY) - calendarCreate.get(Calendar.HOUR_OF_DAY) == 0) {
                            if (calendarCurrent.get(Calendar.MINUTE) - calendarCreate.get(Calendar.MINUTE) == 0) {
                                displayedAdvertCreateDate = "Now";
                            } else {
                                displayedAdvertCreateDate = calendarCurrent.get(Calendar.MINUTE) - calendarCreate.get(Calendar.MINUTE) + " minutes ago";
                            }
                        } else {
                            displayedAdvertCreateDate = calendarCurrent.get(Calendar.HOUR_OF_DAY) - calendarCreate.get(Calendar.HOUR_OF_DAY) == 1 ?
                                    "1 hour ago" : calendarCurrent.get(Calendar.HOUR_OF_DAY) - calendarCreate.get(Calendar.HOUR_OF_DAY) + " hours ago";
                        }
                    } else {
                        displayedAdvertCreateDate = differenceInDays + " days ago";
                    }
                } else {
                    displayedAdvertCreateDate = differenceInWeeks + " weeks ago";
                }
            } else if(differenceInMonths == 1) {
                displayedAdvertCreateDate = "1 month ago";
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                displayedAdvertCreateDate = sdf.format(createDate);
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            displayedAdvertCreateDate = sdf.format(createDate);
        }

        return displayedAdvertCreateDate;
    }
}
