package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

public class ItemPetFragment extends Fragment {
    private static final String ARG_PET = "myPet";
    final static int REQUEST_CODE_OPEN_PET_PROFILE = 111;

    private Pet currentPet;
    CardView myPetItem_crdv;
    TextView petName_txtv;
    ImageButton petPicture_img;
    TextView petAge_txtv;
    TextView petBreedAndType_txtv;
    TextView petWeight_txtv;

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
//        petPicture_img.setImageBitmap();
        petName_txtv.setText(currentPet.getName());
        petBreedAndType_txtv.setText(currentPet.getBreed() + " " + (currentPet.getType()));
        petAge_txtv.setText(currentPet.getPetAgeInYear() + " years and " + currentPet.getPetAgeInMonth() + " months old");
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
            startActivityForResult(moveToPetProfile, REQUEST_CODE_OPEN_PET_PROFILE, bundle);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_OPEN_PET_PROFILE && resultCode == RESULT_OK) {
            //if we get here, it means we either modified or deleted the pet (because that's only where we make result ok)
            if (data.getBooleanExtra("petDeleted", false)) {
                //when deleting, we read database and set the viewpager
                ((MyPetsActivity) getActivity()).getPetListFromDB();
                TransitionManager.beginDelayedTransition((FrameLayout) getActivity().findViewById(R.id.container),
                        TransitionInflater.from(getContext()).inflateTransition(R.transition.my_transition5));
                ((MyPetsActivity) getActivity()).setViewPager();
            }

            if (data.getSerializableExtra("petModified") != null) {
                //when modifying, we start activity with passing the modified pet object,
                // then read from database, and set viewpager (to make changes real)
                Intent moveToPetProfile = new Intent(getContext(), PetProfileActivity.class);
                moveToPetProfile.putExtra("petInfo", data.getSerializableExtra("petModified"));
                startActivityForResult(moveToPetProfile, REQUEST_CODE_OPEN_PET_PROFILE);

                ((MyPetsActivity)getActivity()).getPetListFromDB();
                ((MyPetsActivity) getActivity()).setViewPager();
            }
        }
    }
}
