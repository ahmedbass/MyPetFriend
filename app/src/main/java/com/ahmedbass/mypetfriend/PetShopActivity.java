package com.ahmedbass.mypetfriend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

        generateFakeList();
    }

    private void generateFakeList() {
        //generate fake list
        ArrayList<Advert> listOfAdverts = new ArrayList<>();
        listOfAdverts.add(new Advert(1,"sellerMe","Beautiful White German Shepherd puppies ", 2500, System.currentTimeMillis(),
                 "This is description of the deal I want to offer", 1, 1, "german shepard", 26, true, false, true, "my city, country"));
        listOfAdverts.add(new Advert(2,"sellerMe2","Persian cat for sale", 500, System.currentTimeMillis(),
                "This is description of the deal I want to offer", 1, 2, "persian", 26, false, false, true, "my location2"));


        //instantiate custom adapter
        adapter = new MyAdvertsAdapter(this, listOfAdverts);

        //handle listview and assign adapter
        dealsList = (ListView)findViewById(R.id.shop_deals_listview);
        dealsList.setAdapter(adapter);
        //when click on existing pet, move to its profile
        dealsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent moveToPetProfile = new Intent(getBaseContext(), AdvertActivity.class);
                moveToPetProfile.putExtra("petInfo", adapter.getItem(position));
                startActivity(moveToPetProfile);
            }
        });
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
