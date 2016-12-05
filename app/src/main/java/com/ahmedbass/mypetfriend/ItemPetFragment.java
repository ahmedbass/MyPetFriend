package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.ref.WeakReference;

public class ItemPetFragment extends Fragment {
    private static final String ARG_PET = "myPet";
    final static int REQUEST_CODE_OPEN_PET_PROFILE = 111;

    CardView myPetItem_crdv;
    TextView petName_txtv;
    ImageButton petPicture_img;
    TextView petAge_txtv;
    TextView petBreedAndType_txtv;
    TextView petWeight_txtv;

    private Pet currentPet;

    public ItemPetFragment() {
        // Required empty public constructor
    }

    //Use this factory method to create a new instance of this fragment using the provided parameters.
    public static ItemPetFragment newInstance(Pet pet) {
        ItemPetFragment fragment = new ItemPetFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PET, pet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentPet = (Pet) getArguments().getSerializable(ARG_PET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.list_item_pet, container, false);

        //initialize views
        myPetItem_crdv = (CardView) rootView.findViewById(R.id.pet_item_crdv);
        petName_txtv = (TextView) rootView.findViewById(R.id.pet_name_txtv);
        petPicture_img = (ImageButton) rootView.findViewById(R.id.pet_picture_img);
        petAge_txtv = (TextView) rootView.findViewById(R.id.pet_age_txtv);
        petBreedAndType_txtv = (TextView) rootView.findViewById(R.id.pet_breed_and_type_txtv);
        petWeight_txtv = (TextView) rootView.findViewById(R.id.pet_weight_txtv);

        //assign values to views
        if(!currentPet.getPetPhotos().isEmpty()) {
            petPicture_img.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            petPicture_img.setImageBitmap(currentPet.getPetPhotos().get(currentPet.getPetPhotos().size() - 1).getPhoto());
            loadBitmap(currentPet.getPetPhotos().get(currentPet.getPetPhotos().size() - 1).getPhoto(), petPicture_img);
        } else {
            petPicture_img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            petPicture_img.setImageResource(R.drawable.pet_paw);
        }
        petName_txtv.setText(currentPet.getName());
        petBreedAndType_txtv.setText(currentPet.getBreed() + " " + (currentPet.getType()));
        petAge_txtv.setText(currentPet.getPetAgeInYear(0) + " years and " + currentPet.getPetAgeInMonth(0) + " months old");
        petWeight_txtv.setText(currentPet.getCurrentWeight() == -1 ? "Weigh not set" : currentPet.getCurrentWeight() + " kg");

        //set onclick listener
        myPetItem_crdv.setOnClickListener(new HandleMyViewsClicks());
        petPicture_img.setOnClickListener(new HandleMyViewsClicks());

        return rootView;
    }

    //instead of my normal way of defining anonymous class for onClick, i defined it here to implement it for two views
    class HandleMyViewsClicks implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
            Intent moveToPetProfile = new Intent(getActivity(), PetProfileActivity.class);
            moveToPetProfile.putExtra("petInfo", currentPet);
            try {
                getActivity().startActivityForResult(moveToPetProfile, REQUEST_CODE_OPEN_PET_PROFILE, bundle);
            } catch (RuntimeException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loadBitmap(Bitmap bitmap, ImageView imageView) {
        BitmapWorkerTask task = new BitmapWorkerTask(imageView);
        task.execute(bitmap);
    }
    public static Bitmap decodeSampledBitmapFromResource(byte[] data, int reqWidth, int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions, and pass the options
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data,0, data.length, options);

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

    //-------------------------------------------------------------------
    class BitmapWorkerTask extends AsyncTask<Bitmap, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;

        BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override // Decode image in background.
        protected Bitmap doInBackground(Bitmap... params) {
            Bitmap data = params[0];
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            data.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            return decodeSampledBitmapFromResource(bytes.toByteArray(), 1000, 1000);
        }
        @Override // Once complete, see if ImageView is still around and set bitmap.
        protected void onPostExecute(final Bitmap bitmap) {
            if (bitmap != null) {
                imageViewReference.get().setImageBitmap(bitmap);
            }
        }
    }
}
