package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.ahmedbass.mypetfriend.LauncherActivity.CURRENT_USER_INFO_PREFS;

public class AdvertActivity extends AppCompatActivity {

    ImageView advertPhoto_img;
    TextView advertType_txtv, price_txtv, petType_txtv, petBreed_txtv, petAge_txtv, isPetMicrochipped_txtv, isPetNeutered_txtv, isPetVaccinated_txtv,
            advertDetails_txtv, sellerName_txtv, advertLocation_txtv;
    Button sellerPhone_btn, sellerEmail_btn;

    Advert currentAdvert;
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

        //get currentAdvert information from intent with currentAdvert object
        if (getIntent() != null && getIntent().getSerializableExtra("advertInfo") != null) {
            currentAdvert = (Advert) getIntent().getSerializableExtra("advertInfo");
        } else {
            Toast.makeText(this, "Error: Cannot Retrieve Advert Information", Toast.LENGTH_SHORT).show();
        }

        initializeMyViews();
        setAdvertInfoInViews();

        handleAdvertImages();

    }

    private void setAdvertInfoInViews() {
        //set the title of the currentAdvert on the actionbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(currentAdvert.getTitle());
        }
        advertType_txtv.setText(currentAdvert.getType().isEmpty() ? "Not Available" : currentAdvert.getType());
        price_txtv.setText(String.valueOf(currentAdvert.getPrice()).isEmpty() ?
                "Not Available" : NumberFormat.getCurrencyInstance(Locale.getDefault()).format(currentAdvert.getPrice()));
        petType_txtv.setText(currentAdvert.getPetType().isEmpty() ? "Not Available" : currentAdvert.getPetType());
        petBreed_txtv.setText(currentAdvert.getPetBreed().isEmpty() ? "Not Available" : currentAdvert.getPetBreed());
        petAge_txtv.setText(getDisplayedPetAge(currentAdvert.getPetBirthDate()));
        isPetMicrochipped_txtv.setText(currentAdvert.isPetMicroChipped() ? "Yes" : "No");
        isPetNeutered_txtv.setText(currentAdvert.isNeutered() ? "Yes" : "No");
        isPetVaccinated_txtv.setText(currentAdvert.isPetVaccinated() ? "Yes" : "No");
        advertDetails_txtv.setText(currentAdvert.getDetails().isEmpty() ? "Details Not Available" : currentAdvert.getDetails());
        advertLocation_txtv.setText((currentAdvert.getCity().isEmpty() && currentAdvert.getCountry().isEmpty()) ?
                "Location Not Available" : currentAdvert.getCity() + ", " + currentAdvert.getCountry());
        if (currentAdvert.getPhone().isEmpty()) {
            sellerPhone_btn.setEnabled(false);
            sellerPhone_btn.setText("Phone Number Unavailable");
        } else {
            sellerPhone_btn.setText("Phone: " + currentAdvert.getPhone());
        }
        if(currentAdvert.getEmail().isEmpty()) {
            sellerEmail_btn.setEnabled(false);
            sellerEmail_btn.setText("Email Unavailable");
        } else {
            sellerEmail_btn.setText("Email: " + currentAdvert.getEmail());
        }
    }

    public String getDisplayedPetAge(long birthDate) {
        int ageInYear, ageInMonth;
        Calendar birthCalender = Calendar.getInstance();
        birthCalender.setTimeInMillis(birthDate);
        ageInYear = Calendar.getInstance().get(Calendar.YEAR) - birthCalender.get(Calendar.YEAR);
        ageInMonth = (Calendar.getInstance().get(Calendar.MONTH) - birthCalender.get(Calendar.MONTH)) * 10 / 12;

        return ageInYear + (ageInMonth > 0 ? "." + ageInMonth : "") + " years old";
    }

    //possible ways of contacting the seller (phone call, email)
    public void contactSeller(View view) {
        switch (view.getId()) {
            case R.id.sellerPhone_btn:
                String sellerPhoneNumber = currentAdvert.getPhone();
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + sellerPhoneNumber));
                if (phoneIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(phoneIntent);
                }
                break;
            case R.id.sellerEmail_btn:
                String sellerEmail = currentAdvert.getEmail();
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("message/rfc822");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{sellerEmail});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "My PetFriend Advert: " + currentAdvert.getTitle());
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }
                break;
        }
    }

    private void handleAdvertImages() {
        advertPhoto_img.setImageResource(advertPhotos[0]);
        advertPhoto_img.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
            @Override
            public void onClick(View v) {
                showPhoto();
            }
        });
        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new ImageAdapter(this));
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //---display the images selected---
                advertPhoto_img.setImageResource(advertPhotos[position]);
            }
        });
    }

    // when user clicks on the currentAdvert image, it opens this dialog to show it bigger
    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    public void showPhoto() {
        Dialog builder = new Dialog(this);
        builder.requestWindowFeature(Window.FEATURE_SWIPE_TO_DISMISS);
        builder.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setCanceledOnTouchOutside(true);

        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(advertPhoto_img.getDrawable());
        builder.addContentView(imageView, new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        builder.show();
    }

    private void initializeMyViews() {
        advertPhoto_img = (ImageView) findViewById(R.id.advertPhoto_img);
        advertType_txtv = (TextView) findViewById(R.id.advertType_txtv);
        price_txtv = (TextView) findViewById(R.id.price_txtv);
        petType_txtv = (TextView) findViewById(R.id.petType_txtv);
        petBreed_txtv = (TextView) findViewById(R.id.petBreed_txtv);
        petAge_txtv = (TextView) findViewById(R.id.petAge_txtv);
        isPetMicrochipped_txtv = (TextView) findViewById(R.id.isPetMicrochipped_txtv);
        isPetNeutered_txtv = (TextView) findViewById(R.id.isPetNeutered_txtv);
        isPetVaccinated_txtv = (TextView) findViewById(R.id.isPetVaccinated_txtv);
        advertDetails_txtv = (TextView) findViewById(R.id.advertDetails_txtv);
        advertLocation_txtv = (TextView) findViewById(R.id.advertLocation_txtv);
        sellerPhone_btn = (Button) findViewById(R.id.sellerPhone_btn);
        sellerEmail_btn = (Button) findViewById(R.id.sellerEmail_btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if ((getIntent() != null && getIntent().getBooleanExtra("calledFromMyAdverts", false)) ||
                currentAdvert.getSellerId() == getSharedPreferences(CURRENT_USER_INFO_PREFS, MODE_PRIVATE).getInt(MyPetFriendContract.UsersEntry.USER_ID, -1)) {
            getMenuInflater().inflate(R.menu.user_profile_toolbar, menu);
        } else {
            getMenuInflater().inflate(R.menu.user_actions_menu, menu);
        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, AddEditAdvertActivity.class);
                intent.putExtra("editAdvert", currentAdvert);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                return true;
            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //----------------------------------------------------------------------------------------------
    //adapter for the gallery
    public class ImageAdapter extends BaseAdapter {

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
        public int getCount() {
            return advertPhotos.length;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

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

