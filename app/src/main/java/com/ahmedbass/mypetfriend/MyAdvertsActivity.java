package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class MyAdvertsActivity extends AppCompatActivity {

    static final int REQUEST_CODE_ADD_NEW_ADVERT = 115;

    ArrayList<Advert> listOfMyAdverts = new ArrayList<>();
    MyAdvertsAdapter myAdvertsAdapter;
    ListView myAdvertsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_adverts);

        getListOfMyAdverts();
        setUpListOfMyAdverts();
    }

    private void getListOfMyAdverts() {
        SharedPreferences preferences = getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE);
        MyDBHelper dbHelper = new MyDBHelper(this);
        dbHelper.open();
        Cursor cursor = dbHelper.getRecord(MyPetFriendContract.UserOfferedAdvertsEntry.TABLE_NAME, null,
                new String[]{MyPetFriendContract.UserOfferedAdvertsEntry.SELLER_ID},
                new String[]{String.valueOf(preferences.getInt(MyPetFriendContract.UsersEntry.USER_ID, -1))});
        if(cursor.moveToLast()) {
            do {
                listOfMyAdverts.add(new Advert(cursor.getLong(0), cursor.getLong(1), cursor.getLong(2), (cursor.getInt(3) == 1),
                        cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getString(7),
                        cursor.getDouble(8), cursor.getString(9), cursor.getString(10), cursor.getString(11),
                        cursor.getString(12), cursor.getString(13), cursor.getString(14), cursor.getString(15),
                        cursor.getLong(16), cursor.getString(17), (cursor.getInt(18) == 1), (cursor.getInt(19) == 1),
                        (cursor.getInt(20) == 1), "photo"));
            } while (cursor.moveToPrevious());
            cursor.close();
            dbHelper.close();
        }
    }

    private void setUpListOfMyAdverts() {
        myAdvertsList = (ListView) findViewById(R.id.myAdverts_lstv);
        myAdvertsAdapter = new MyAdvertsAdapter(this, listOfMyAdverts);
        myAdvertsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToPetProfile = new Intent(getBaseContext(), AdvertActivity.class);
                moveToPetProfile.putExtra("advertInfo", myAdvertsAdapter.getItem(position));
                moveToPetProfile.putExtra("calledFromMyAdverts", true);
                startActivityForResult(moveToPetProfile, REQUEST_CODE_ADD_NEW_ADVERT,
                        ActivityOptions.makeSceneTransitionAnimation(MyAdvertsActivity.this).toBundle());
            }
        });
        myAdvertsList.setAdapter(myAdvertsAdapter);
        myAdvertsList.setEmptyView(findViewById(R.id.emptyView_txtv));
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_NEW_ADVERT && resultCode == RESULT_OK) {
            listOfMyAdverts.clear();
            getListOfMyAdverts();
            TransitionManager.beginDelayedTransition((ViewGroup) findViewById(R.id.myAdverts_lstv));
            myAdvertsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my_adverts_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_advert:
                startActivityForResult(new Intent(this, AddEditAdvertActivity.class), REQUEST_CODE_ADD_NEW_ADVERT,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                return true;
            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class MyAdvertsAdapter extends ArrayAdapter<Advert>{
        public MyAdvertsAdapter(Context context, ArrayList<Advert> myAdverts) {
            super(context, 0, myAdverts);
        }
        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
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
        String displayedAdvertCreateDate;
        Calendar calendarCreate = Calendar.getInstance();
        Calendar calendarCurrent = Calendar.getInstance();
        calendarCreate.setTimeInMillis(createDate);

        int differenceInYears = calendarCurrent.get(Calendar.YEAR) - calendarCreate.get(Calendar.YEAR);
        int differenceInMonths = calendarCurrent.get(Calendar.MONTH) - calendarCreate.get(Calendar.MONTH);
        int differenceInWeeks = calendarCurrent.get(Calendar.WEEK_OF_YEAR) - calendarCreate.get(Calendar.WEEK_OF_YEAR);
        int differenceInDays = calendarCurrent.get(Calendar.DAY_OF_YEAR) - calendarCreate.get(Calendar.DAY_OF_YEAR);
        int differenceInHours = calendarCurrent.get(Calendar.HOUR_OF_DAY) - calendarCreate.get(Calendar.HOUR_OF_DAY);

        if(differenceInYears == 0) {
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
