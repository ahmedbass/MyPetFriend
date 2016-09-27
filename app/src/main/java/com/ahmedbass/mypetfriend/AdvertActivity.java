package com.ahmedbass.mypetfriend;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;

public class AdvertActivity extends AppCompatActivity {

    Button callSeller, emailSeller;
    ImageView advertImages;
    int[] imageID = {
            R.drawable.dog_img,
            R.drawable.pic,
            R.drawable.dog_img,
            R.drawable.pic,
            R.drawable.dog_img
    };

    Advert advert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        initializeMyViews();
        advert = (Advert) getIntent().getSerializableExtra("petInfo"); //get intent with deal information

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(advert.getTitle());

        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //---display the images selected---
                advertImages.setImageResource(imageID[position]);
            }
        });

        callSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = "+966535823919";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        emailSeller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {"jon@example.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My PetFriend Advert: " + advert.getTitle());
                startActivity(emailIntent);
            }
        });

    }

    private void initializeMyViews() {
        advertImages = (ImageView) findViewById(R.id.advert_images);
        callSeller = (Button) findViewById(R.id.call_seller_btn);
        emailSeller = (Button) findViewById(R.id.email_seller_btn);
    }

    public class ImageAdapter extends BaseAdapter{

        private Context context;
        private int itemBackground;
        ImageView imageview;

        ImageAdapter(Context c) {
            context = c;
            //---setting the style---
            TypedArray a = obtainStyledAttributes(R.styleable.Gallery1);
            itemBackground = a.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 0);
            a.recycle();
        }

        //---returns the number of images---
        public int getCount() { return imageID.length; }

        //---returns the ID of an item---
        public Object getItem(int position) { return position; }

        //---returns the ID of an item---
        public long getItemId(int position) { return position; }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {
            imageview = new ImageView(context);
            imageview.setImageResource(imageID[position]);
            imageview.setLayoutParams(new Gallery.LayoutParams(200, 150));
            imageview.setBackgroundResource(itemBackground);
            return imageview;
        }
    }
}

