package com.ahmedbass.mypetfriend;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class PetProfileActivity extends AppCompatActivity {

    private final static int GENDER_MALE = 1;
    private final static int GENDER_FEMALE = 2;

    TextView gender_txtv, breed_txtv, birthdate_txtv, weight_txtv;
    String name, birthdate, gender, breed, weight;
    int ageInMonth;
    int ageInYear;

    ArrayList<Integer> picturesResources;
    ImageView petProfilePic_img1, petProfilePic_img2;
    Timer repeatTask;
    ViewSwitcher viewSwitcher;
//    Animation fadeIn, fadeOut;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_profile);

        initializeMyViews();
        createMySlideShow();

        //set the viewpager for the 3 different sections of pet information
        ViewPager myPager = (ViewPager) findViewById(R.id.pager);
        myPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] tabTitles = {"Overview", "Schedule", "Vaccinations"};

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0: return new PetInfoFragment();
                    case 1: return new PetScheduleFragment();
                    default: return new PetInfoFragment();
                }
            }
            @Override
            public int getCount() {
                return tabTitles.length;
            }
            public String getPageTitle(int position) {
                return tabTitles[position];
            }
        });

        //set the tabs title
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(myPager);

        //get the pet information from the passed intent
        if (getIntent() != null && getIntent().getSerializableExtra("petInfo") != null) {

            Pet petInfo = (Pet) getIntent().getSerializableExtra("petInfo");

            //assign the pet info into variables to display them on UI
            name = petInfo.getName();
            //format the birthDate in millies
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            //calculating the age in years and months
            ageInYear = petInfo.getPetAgeInYear();
            ageInMonth = petInfo.getPetAgeInMonth();
            birthdate = "Age: " + ageInYear + " years and " + ageInMonth + " months\nBirthday: " + sdf.format(petInfo.getBirthDate());
            gender = petInfo.getGender() + "";
            breed = petInfo.getBreed() + " " + petInfo.getType();
            weight = "Weight: " + petInfo.getWeight() + " kg";

            //set pet info on the UI
//            actionBar.setTitle(name);
//            gender_txtv.setText(gender);
//            breed_txtv.setText(breed);
//            birthdate_txtv.setText(birthdate);
//            weight_txtv.setText(weight);
        }
    }

    //make slideshow of pet pictures using ViewSwitcher with just two ImageViews by scheduling the switch through background thread. perfect!
    private void createMySlideShow() {
        picturesResources = new ArrayList<>(); // TODO: change this later to take the pics of the pet
        picturesResources.add(R.drawable.pic);
        picturesResources.add(R.drawable.dog_img);
        picturesResources.add(R.drawable.picture1);
        picturesResources.add(R.drawable.picture2);
        picturesResources.add(R.drawable.picture3);
        picturesResources.add(R.drawable.picture4);
        picturesResources.add(R.drawable.picture5);
        picturesResources.add(R.drawable.picture6);
        picturesResources.add(R.drawable.picture7);
        picturesResources.add(R.drawable.picture8);
        picturesResources.add(R.drawable.picture9);
        picturesResources.add(R.drawable.picture10);
        picturesResources.add(R.drawable.picture11);
        picturesResources.add(R.drawable.picture12);
        picturesResources.add(R.drawable.picture13);
        picturesResources.add(R.drawable.picture14);
        picturesResources.add(R.drawable.picture15);
        picturesResources.add(R.drawable.picture16);
        picturesResources.add(R.drawable.picture17);
        picturesResources.add(R.drawable.picture18);
        picturesResources.add(R.drawable.picture19);
        picturesResources.add(R.drawable.picture20);
        picturesResources.add(R.drawable.picture28);
        picturesResources.add(R.drawable.picture29);
        picturesResources.add(R.drawable.picture23);
        picturesResources.add(R.drawable.picture24);
        picturesResources.add(R.drawable.picture25);
        picturesResources.add(R.drawable.picture26);
        picturesResources.add(R.drawable.picture27);

        //switch between two ImageViews
        viewSwitcher = (ViewSwitcher) this.findViewById(R.id.viewSwitcher);
        viewSwitcher.setInAnimation(this, android.R.anim.fade_in);
        viewSwitcher.setOutAnimation(this, android.R.anim.fade_out);

        repeatTask = new Timer(); //timer repeating schedule for changing image
        repeatTask.scheduleAtFixedRate(new TimerTask() {
            int index = 0;
            @Override
            public void run() {
                //index = index < picturesResources.size() - 1 ? index += 1 : 0; //increment index linearly
                index = (int) (Math.random() * picturesResources.size()); //pick image index randomly
                //resize image in background thread before loading it to avoid OutOfMemory Exception & blocking the UI thread
                loadBitmap(picturesResources.get(index), petProfilePic_img1, petProfilePic_img2);
            }
        }, 0, 3000);
    }

    public void loadBitmap(int imageId, ImageView imageView1, ImageView imageView2) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView1, imageView2);
        task.execute(imageId);
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions, and pass the options
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Then decode bitmap again with inSampleSize set, and inJustDecodeBounds set to false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    //This method calculates a power of 2 SampleSize value based on target width and height:
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw width and height of image
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;

        try {
            // Calculate the largest power of 2 inSampleSize value and keep both w & h larger than requested w & h /2.
            while ((width / inSampleSize) >= reqWidth && (height / inSampleSize) >= reqHeight) {
                inSampleSize *= 2;
            }
        } catch (ArithmeticException ignored) {}

        return inSampleSize;
    }

    //get width and height same as ThreeFourImageView manually, because Views width and height aren't yet calculated at onCreate
    public static int getImageWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
    public static int getImageHeight() {
        if (Resources.getSystem().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            return (Resources.getSystem().getDisplayMetrics().widthPixels * 3 / 4);
        } else {
            return Resources.getSystem().getDisplayMetrics().heightPixels;
        }
    }

    private void initializeMyViews() {
        //set the title of the advert on the actionbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left);

        petProfilePic_img1 = (ThreeFourImageView) findViewById(R.id.petProfilePic_img1);
        petProfilePic_img2 = (ThreeFourImageView) findViewById(R.id.petProfilePic_img2);
    //        birthdate_txtv = (TextView) findViewById(R.id.pet_birthdate_txtv);
    //        gender_txtv = (TextView) findViewById(R.id.pet_gender_txtv);
    ////        breed_txtv = (TextView) findViewById(R.id.pet_breed_txtv);
    //        weight_txtv = (TextView) findViewById(R.id.pet_weight_txtv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pet_profile_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Toast.makeText(this, "TODO: modify the pet profile.", Toast.LENGTH_SHORT).show();
                return true;

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //-----------------------------------------------------------

    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference1, imageViewReference2;
        private int data = 0;

        BitmapWorkerTask(ImageView imageView1, ImageView imageView2) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference1 = new WeakReference<>(imageView1);
            imageViewReference2 = new WeakReference<>(imageView2);
        }

        @Override // Decode image in background.
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            return decodeSampledBitmapFromResource(getResources(), data, getImageWidth(), getImageHeight());
        }

        @Override // Once complete, see if ImageView is still around and set bitmap.
        protected void onPostExecute(final Bitmap bitmap) {
            if (bitmap != null) {
                if(viewSwitcher.getDisplayedChild()==0){
                    imageViewReference2.get().setImageBitmap(bitmap);
                    viewSwitcher.showNext();
                } else {
                    imageViewReference1.get().setImageBitmap(bitmap);
                    viewSwitcher.showPrevious();
                }
            }
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        if(repeatTask != null){
            repeatTask.cancel();
        }
    }
}

