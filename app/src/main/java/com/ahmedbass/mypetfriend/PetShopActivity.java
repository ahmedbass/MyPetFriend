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
import java.util.Locale;

public class PetShopActivity extends AppCompatActivity {

    ListView dealsList;
    MyAdvertsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_shop);

        generateDummyList();
    }

    private void generateDummyList() {
        //generate fake list
        ArrayList<Advert> listOfAdverts = new ArrayList<>();
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                 "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "my city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "my city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "location"));


        //instantiate custom adapter
        adapter = new MyAdvertsAdapter(this, listOfAdverts);

        //handle listview and assign adapter
        dealsList = (ListView)findViewById(R.id.petShopDeals_listview);
        dealsList.setAdapter(adapter);
        //when click on existing pet, move to its profile
        dealsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToPetProfile = new Intent(getBaseContext(), AdvertActivity.class);
                moveToPetProfile.putExtra("advertInfo", adapter.getItem(position));
                startActivity(moveToPetProfile, ActivityOptions.makeSceneTransitionAnimation(PetShopActivity.this).toBundle());
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

    class MyAdvertsAdapter extends ArrayAdapter<Advert> {

        MyAdvertsAdapter(Context context, ArrayList<Advert> adverts) {
            super(context,0, adverts);
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

//        ImageView petPhoto = (ImageView) listItemView.findViewById(R.id.list_pet_thumbnail);
//        if (!currentPet.getAllPhotos().contains(currentPet)) {
//            petPhoto.setImageBitmap(currentPet.getAllPhotos().get(0));
//            Toast.makeText(getContext(), "TODO: should change the thumbnail based on pet's top photo", Toast.LENGTH_SHORT).show();
//        }

            TextView dealTitle = (TextView) listItemView.findViewById(R.id.list_deal_title);
            assert currentAdvert != null;
            dealTitle.setText(currentAdvert.getTitle());

            TextView dealLocation = (TextView) listItemView.findViewById(R.id.list_deal_location);
            dealLocation.setText(currentAdvert.getLoc());

            TextView dealDate = (TextView) listItemView.findViewById(R.id.list_deal_date);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            dealDate.setText(sdf.format(currentAdvert.getDate()));

            TextView dealPrice = (TextView) listItemView.findViewById(R.id.list_deal_price);
            dealPrice.setText(NumberFormat.getCurrencyInstance(Locale.getDefault()).format(currentAdvert.getPrice()));

            return listItemView;
        }
    }
}
