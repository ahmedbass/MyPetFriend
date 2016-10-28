package com.ahmedbass.mypetfriend;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ItemPetFragment extends Fragment {
    private static final String ARG_PET = "myPet";

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
        petName_txtv.setText(currentPet.getName());
        petBreedAndType_txtv.setText(currentPet.getBreed() + " " + (currentPet.getType() == Pet.TYPE_CAT ? "Cat" : "Dog"));
        petAge_txtv.setText(currentPet.getPetAgeInYear() + " years and " + currentPet.getPetAgeInMonth() + " months old");
        petWeight_txtv.setText(currentPet.getWeight() + " kg");

        //set onclick listener
        myPetItem_crdv.setOnClickListener(new HandleMyViewsClicks());
        petPicture_img.setOnClickListener(new HandleMyViewsClicks());

        return rootView;
    }

    class HandleMyViewsClicks implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onClick(View v) {
            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle();
            Intent moveToPetProfile = new Intent(getActivity(), PetProfileActivity.class);
            moveToPetProfile.putExtra("petInfo", currentPet);
            startActivity(moveToPetProfile, bundle);
        }
    }
}