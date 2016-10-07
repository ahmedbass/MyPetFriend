package com.ahmedbass.mypetfriend;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class AdvertActivity extends AppCompatActivity {

    Advert advert;
    ImageView advertImage;
    int[] advertPhotos = {
            R.drawable.dog_img,
            R.drawable.pic,
            R.drawable.dog_img,
            R.drawable.pic,
            R.drawable.dog_img
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);

        advert = (Advert) getIntent().getSerializableExtra("petInfo"); //get advert information from intent with advert object

        //set the title of the advert on the actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(advert.getTitle());

        handleAdvertImages();

    }

    private void handleAdvertImages() {
        advertImage = (ImageView) findViewById(R.id.advert_images);
        advertImage.setImageResource(advertPhotos[0]);
        advertImage.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void onClick(View v) {
                showImage();
            }
        });
        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //---display the images selected---
                advertImage.setImageResource(advertPhotos[position]);
            }
        });
    }

    // when user clicks on the advert image, it opens this dialog to show it bigger
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public void showImage() {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setCanceledOnTouchOutside(true);

        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(advertImage.getDrawable());
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    //possible ways of contacting the seller (phone call, email)
    public void contactSeller(View view) {
        switch (view.getId()) {
            case R.id.call_seller_btn:
                String sellerPhoneNumber = "+966123456789";
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + sellerPhoneNumber));
                if (phoneIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(phoneIntent);
                }
                break;
            case R.id.email_seller_btn:
                String sellerEmail = "jon@example.com";
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {sellerEmail});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My PetFriend Advert: " + advert.getTitle());
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }
                break;
        }
    }

    //adapter for the gallery
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
        public int getCount() { return advertPhotos.length; }

        public Object getItem(int position) { return position; }

        public long getItemId(int position) { return position; }

        //---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {
            imageview = new ImageView(context);
            imageview.setImageResource(advertPhotos[position]);
            imageview.setLayoutParams(new Gallery.LayoutParams(200, 150));
            imageview.setBackgroundResource(itemBackground);
            return imageview;
        }
    }
}

