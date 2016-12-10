package com.ahmedbass.mypetfriend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PetInfoFragment extends Fragment {
    private static final String ARG_PET = "myPet";
    Pet myPet;

    View rootView;
    TextView birthDate_txtv, gender_txtv, type_txtv, breed_txtv, weight_txtv, isNeutered_txtv, microchipNumber_txtv;
    TextView personalInformation_txtv, breedHighlights_txtv, breedPersonality_txtv, breedHealth_txtv,breedCare_txtv, breedFeeding_txtv, breedHistory_txtv;
    GridLayout personalInformation_gridLout;
    int ageInMonth, ageInYear;

    public static PetInfoFragment newInstance(Pet pet) {
        PetInfoFragment fragment = new PetInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PET, pet);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            myPet = (Pet) getArguments().getSerializable(ARG_PET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pet_info, container, false);
        this.rootView = rootView;

        personalInformation_gridLout = (GridLayout) rootView.findViewById(R.id.personalInformation_gridLout);
        personalInformation_txtv = (TextView) rootView.findViewById(R.id.personalInformation_txtv);
        birthDate_txtv = (TextView) rootView.findViewById(R.id.birthDate_txtv);
        gender_txtv = (TextView) rootView.findViewById(R.id.gender_txtv);
        type_txtv = (TextView) rootView.findViewById(R.id.type_txtv);
        breed_txtv = (TextView) rootView.findViewById(R.id.breed_txtv);
        weight_txtv = (TextView) rootView.findViewById(R.id.weight_txtv);
        isNeutered_txtv = (TextView) rootView.findViewById(R.id.isNeutered_txtv);
        microchipNumber_txtv = (TextView) rootView.findViewById(R.id.microchipNumber_txtv);

        breedHighlights_txtv = (TextView) rootView.findViewById(R.id.breedHighlights_txtv);
        breedPersonality_txtv = (TextView) rootView.findViewById(R.id.breedPersonality_txtv);
        breedHealth_txtv = (TextView) rootView.findViewById(R.id.breedHealth_txtv);
        breedCare_txtv = (TextView) rootView.findViewById(R.id.breedCare_txtv);
        breedFeeding_txtv = (TextView) rootView.findViewById(R.id.breedFeeding_txtv);
        breedHistory_txtv = (TextView) rootView.findViewById(R.id.breedHistory_txtv);

        //assign the pet info into variables to display them on UI
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        //calculating the age in years and months
        ageInYear = myPet.getPetAgeInYear(0);
        ageInMonth = myPet.getPetAgeInMonth(0) * 10 / 12;
        birthDate_txtv.setText(sdf.format(myPet.getBirthDate()) +
                "\n(" + ageInYear + (ageInMonth == 0 ? "" : "." + ageInMonth) + " years old)");
        gender_txtv.setText(myPet.getGender());
        type_txtv.setText(myPet.getType());
        breed_txtv.setText( myPet.getBreed());
        weight_txtv.setText(myPet.getCurrentWeight() == -1 ? "Not set" : myPet.getCurrentWeight() + " kg");
        isNeutered_txtv.setText(myPet.isNeutered() ? "Yes" : "No");
        microchipNumber_txtv.setText(myPet.getMicrochipNumber().isEmpty() ? "Not set" : myPet.getMicrochipNumber());

        breedHighlights_txtv.setVisibility(myPet.getBreedHighlights().trim().isEmpty() ? View.GONE : View.VISIBLE);
        breedPersonality_txtv.setVisibility(myPet.getBreedPersonality().trim().isEmpty() ? View.GONE : View.VISIBLE);
        breedHealth_txtv.setVisibility(myPet.getBreedHealth().trim().isEmpty() ? View.GONE : View.VISIBLE);
        breedCare_txtv.setVisibility(myPet.getBreedCare().trim().isEmpty() ? View.GONE : View.VISIBLE);
        breedFeeding_txtv.setVisibility(myPet.getBreedFeeding().trim().isEmpty() ? View.GONE : View.VISIBLE);
        breedHistory_txtv.setVisibility(myPet.getBreedHistory().trim().isEmpty() ? View.GONE : View.VISIBLE);

        personalInformation_txtv.setOnClickListener(new View.OnClickListener() {
            boolean isDetailsShown;
            @Override
            public void onClick(View view) {
                if (isDetailsShown) {
                    personalInformation_gridLout.setVisibility(View.GONE);
                    isDetailsShown = false;
                } else {
                    personalInformation_gridLout.setVisibility(View.VISIBLE);
                    isDetailsShown = true;
                }
            }
        });

        breedHighlights_txtv.setOnClickListener(new HandleMyViewsClicks());
        breedPersonality_txtv.setOnClickListener(new HandleMyViewsClicks());
        breedHealth_txtv.setOnClickListener(new HandleMyViewsClicks());
        breedCare_txtv.setOnClickListener(new HandleMyViewsClicks());
        breedFeeding_txtv.setOnClickListener(new HandleMyViewsClicks());
        breedHistory_txtv.setOnClickListener(new HandleMyViewsClicks());

        return rootView;
    }

    //for showing/hiding different pet information
    class HandleMyViewsClicks implements View.OnClickListener {
        boolean isDetailsShown;
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.breedHighlights_txtv:
                    if (isDetailsShown) {
                        breedHighlights_txtv.setText("HighLights");
                        breedHighlights_txtv.setTextAppearance(getContext(), R.style.myTextViewStyle_myTitleStyle);
                        isDetailsShown = false;
                    } else {
                        breedHighlights_txtv.setText(myPet.getBreedHighlights());
                        breedHighlights_txtv.setTextSize(16);
                        breedHighlights_txtv.setTextColor(getResources().getColor(R.color.primaryText));
                        isDetailsShown = true;
                    }
                    break;
                case R.id.breedPersonality_txtv:
                    if (isDetailsShown) {
                        breedPersonality_txtv.setText("Personality");
                        breedPersonality_txtv.setTextAppearance(getContext(), R.style.myTextViewStyle_myTitleStyle);
                        isDetailsShown = false;
                    } else {
                        breedPersonality_txtv.setText(myPet.getBreedPersonality());
                        breedPersonality_txtv.setTextSize(16);
                        breedPersonality_txtv.setTextColor(getResources().getColor(R.color.primaryText));
                        isDetailsShown = true;
                    }
                    break;
                case R.id.breedHealth_txtv:
                    if (isDetailsShown) {
                        breedHealth_txtv.setText("Health");
                        breedHealth_txtv.setTextAppearance(getContext(), R.style.myTextViewStyle_myTitleStyle);
                        isDetailsShown = false;
                    } else {
                        breedHealth_txtv.setText(myPet.getBreedHealth());
                        breedHealth_txtv.setTextSize(16);
                        breedHealth_txtv.setTextColor(getResources().getColor(R.color.primaryText));
                        isDetailsShown = true;
                    }
                    break;
                case R.id.breedCare_txtv:
                    if (isDetailsShown) {
                        breedCare_txtv.setText("Care");
                        breedCare_txtv.setTextAppearance(getContext(), R.style.myTextViewStyle_myTitleStyle);
                        isDetailsShown = false;
                    } else {
                        breedCare_txtv.setText(myPet.getBreedCare());
                        breedCare_txtv.setTextSize(16);
                        breedCare_txtv.setTextColor(getResources().getColor(R.color.primaryText));
                        isDetailsShown = true;
                    }
                    break;
                case R.id.breedFeeding_txtv:
                    if (isDetailsShown) {
                        breedFeeding_txtv.setText("Feeding");
                        breedFeeding_txtv.setTextAppearance(getContext(), R.style.myTextViewStyle_myTitleStyle);
                        isDetailsShown = false;
                    } else {
                        breedFeeding_txtv.setText(myPet.getBreedFeeding());
                        breedFeeding_txtv.setTextSize(16);
                        breedFeeding_txtv.setTextColor(getResources().getColor(R.color.primaryText));
                        isDetailsShown = true;
                    }
                    break;
                case R.id.breedHistory_txtv:
                    if (isDetailsShown) {
                        breedHistory_txtv.setText("History");
                        breedHistory_txtv.setTextAppearance(getContext(), R.style.myTextViewStyle_myTitleStyle);
                        isDetailsShown = false;
                    } else {
                        breedHistory_txtv.setText(myPet.getBreedHistory());
                        breedHistory_txtv.setTextSize(16);
                        breedHistory_txtv.setTextColor(getResources().getColor(R.color.primaryText));
                        isDetailsShown = true;
                    }
                    break;
            }
        }
    }

}
