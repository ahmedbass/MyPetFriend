package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class PetProfileActivity extends AppCompatActivity {

    final static int REQUEST_CODE_EDIT_PET = 222;
    final static int REQUEST_CODE_CAMERA = 30;
    final static int REQUEST_CODE_GALLERY = 31;

    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    ImageView petProfilePic_img1, petProfilePic_img2;
    FloatingActionButton addNewPhoto_fab;
    ViewSwitcher viewSwitcher;
    Timer repeatTask;

    Pet myPet;
    ArrayList<Bitmap> petPhotos = new ArrayList<>();

    public static Bitmap decodeSampledBitmapFromResource(byte[] data, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions, and pass the options
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Then decode bitmap again with inSampleSize set, and inJustDecodeBounds set to false
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //This method calculates a power of 2 SampleSize value based on target width and height:

        // Raw width and height of image
        final int width = options.outWidth;
        final int height = options.outHeight;
        int inSampleSize = 1;
        if (width > reqWidth || height > reqHeight) {
            final int halfWidth = width / 2;
            final int halfHeight = height / 2;
            // Calculate the largest power of 2 inSampleSize value and keep both w & h larger than requested w & h.
            while ((halfWidth / inSampleSize) >= reqWidth && (halfHeight / inSampleSize) >= reqHeight) {
                inSampleSize *= 2;
            }
        }
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);

        initializeMyViews();

        //get the pet information from the passed intent
        if (getIntent() != null && getIntent().getSerializableExtra("petInfo") != null) {
            myPet = (Pet) getIntent().getSerializableExtra("petInfo");
            setViewPager();
            createMySlideShow(true);
        } else {
            Toast.makeText(this, "Error: Cannot Retrieve Pet Information", Toast.LENGTH_SHORT).show();
        }

        addNewPhoto_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPhoto();
            }
        });
    }

    private void setViewPager() {
        collapsingToolbarLayout.setTitle(myPet.getName());
        //set viewpager for the 3 different sections of pet information
        ViewPager myPager = (ViewPager) findViewById(R.id.pager);
        myPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            String[] tabTitles = {"Overview", "Schedule", "Vaccinations"};

            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return PetInfoFragment.newInstance(myPet);
                    case 1:
                        return PetScheduleFragment.newInstance(myPet);
                    default:
                        return PetVaccinationsFragment.newInstance(myPet);
                }
            }

            @Override
            public int getCount() {
                return tabTitles.length;
            }

            @Override
            public String getPageTitle(int position) {
                return tabTitles[position];
            }
        });
        //set the tabs title
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(myPager);
    }

    private void addNewPhoto() {
        //set dialog Items
        final String[] items = {"Take Photo", "Choose From Gallery"};
        //create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.myDialogTheme);
        builder.setTitle("Add New Photo");
        builder.setIcon(R.drawable.camera_blue);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CODE_CAMERA);
                } else if (items[which].equals("Choose From Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), REQUEST_CODE_GALLERY);
                }
            }
        });
        builder.show();
    }

    private void savePhotoToDatabase(Bitmap bitmap) {
        // after we got the photo, we need to convert it to byte array to store it in database as blob
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] byteArray = bytes.toByteArray();

        MyDBHelper dbHelper = new MyDBHelper(this);
        dbHelper.open();
        String[] columnNames = dbHelper.getColumnNames(MyPetFriendContract.PetPhotosEntry.TABLE_NAME);
        Object[] columnValues = {0, myPet.getPetId(), byteArray, System.currentTimeMillis(), ""};
        long photoId = dbHelper.insetRecord(MyPetFriendContract.PetPhotosEntry.TABLE_NAME, columnNames, columnValues);
        dbHelper.close();

        myPet.addPetPhoto(photoId, myPet.getPetId(), byteArray, System.currentTimeMillis(), "");
        createMySlideShow(false);
    }

    //make slideshow of pet pictures using ViewSwitcher with just two ImageViews by scheduling the switch through background thread. perfect!
    private void createMySlideShow(boolean isFirstLaunching) {
        if (isFirstLaunching) {
            MyDBHelper dbHelper = new MyDBHelper(this);
            dbHelper.open();
            Cursor petPhotosCursor = dbHelper.getRecord(MyPetFriendContract.PetPhotosEntry.TABLE_NAME, null,
                    new String[]{MyPetFriendContract.PetPhotosEntry.PET_ID}, new String[]{String.valueOf(myPet.getPetId())});
            while (petPhotosCursor.moveToNext()) {
                myPet.addPetPhoto(petPhotosCursor.getLong(0), petPhotosCursor.getLong(1),
                        petPhotosCursor.getBlob(2), petPhotosCursor.getLong(3), petPhotosCursor.getString(4));
            }
            petPhotosCursor.close();
            dbHelper.close();
        }
        petPhotos = myPet.getPetPhotosExtracted();

        //switch between two ImageViews (with nice fading animation)
        viewSwitcher = (ViewSwitcher) this.findViewById(R.id.viewSwitcher);
        viewSwitcher.setInAnimation(this, android.R.anim.fade_in);
        viewSwitcher.setOutAnimation(this, android.R.anim.fade_out);

        if (petPhotos.size() > 1) {
            repeatTask = new Timer(); //timer repeating schedule for changing image
            repeatTask.scheduleAtFixedRate(new TimerTask() {
                int index = 0;

                @Override
                public void run() {
                    if (petPhotos.size() > 5) {
                        index = (int) (Math.random() * petPhotos.size()); //pick image index randomly (shuffle)
                    } else {
                        index = index < petPhotos.size() - 1 ? index += 1 : 0; //change image linearly (no shuffle)
                    }
                    //resize image in background thread before loading it to avoid OutOfMemory Exception & blocking the UI thread
                    loadBitmap(petPhotos.get(index), petProfilePic_img1, petProfilePic_img2);
                }
            }, 0, 3000);
        } else if (petPhotos.size() == 1) {
            loadBitmap(petPhotos.get(0), petProfilePic_img1, petProfilePic_img2);
        } else if (petPhotos.size() <= 0) {
            petProfilePic_img1.setImageResource(R.drawable.pet_paw);
            petProfilePic_img1.setBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    public void loadBitmap(Bitmap bitmap, ImageView imageView1, ImageView imageView2) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView1, imageView2);
        task.execute(bitmap);
    }

    private void initializeMyViews() {
        //set the title of the currentAdvert on the actionbar
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.arrow_left);

        petProfilePic_img1 = (ThreeFourImageView) findViewById(R.id.petProfilePic_img1);
        petProfilePic_img2 = (ThreeFourImageView) findViewById(R.id.petProfilePic_img2);
        addNewPhoto_fab = (FloatingActionButton) findViewById(R.id.add_fab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pet_profile_toolbar, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                Intent intent = new Intent(this, AddEditPetActivity.class);
                intent.putExtra("EditPetProfile", true);
                myPet.getPetPhotos().clear();
                intent.putExtra("petInfo", myPet);
                startActivityForResult(intent, REQUEST_CODE_EDIT_PET, bundle);
                return true;

            case android.R.id.home: // Respond to the action bar's Up/Home button
                supportFinishAfterTransition();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //when modifying or deleting the pet profile, we close the profile, and go back to onActivityResult of the calling fragment in ViewPager 
        if (requestCode == REQUEST_CODE_EDIT_PET && resultCode == RESULT_OK) {
            if (data.getSerializableExtra("petModified") != null) {
                setResult(RESULT_OK, data);
                finish();
            } else if (data.getSerializableExtra("petDeleted") != null) {
                setResult(RESULT_OK, data);
                finish();
            }
        }

        //this is part regarding taking photos..
        //get the bitmap from data, and pass it to be saved in database
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_CODE_CAMERA) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                savePhotoToDatabase(bitmap);
            } else if (requestCode == REQUEST_CODE_GALLERY) {
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                    savePhotoToDatabase(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (repeatTask != null) {
            repeatTask.cancel();
        }
    }

    //-------------------------------------------------------------------
    class BitmapWorkerTask extends AsyncTask<Bitmap, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference1, imageViewReference2;

        BitmapWorkerTask(ImageView imageView1, ImageView imageView2) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference1 = new WeakReference<>(imageView1);
            imageViewReference2 = new WeakReference<>(imageView2);
        }

        @Override // Decode image in background.
        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap data = params[0];
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            data.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            return decodeSampledBitmapFromResource(bytes.toByteArray(), getImageWidth(), getImageHeight());
        }

        @Override // Once complete, see if ImageView is still around and set bitmap.
        protected void onPostExecute(final Bitmap bitmap) {
            if (bitmap != null) {
                if (viewSwitcher.getDisplayedChild() == 0) {
                    imageViewReference2.get().setImageBitmap(bitmap);
                    viewSwitcher.showNext();
                } else {
                    imageViewReference1.get().setImageBitmap(bitmap);
                    viewSwitcher.showPrevious();
                }
            }
        }
    }
}

