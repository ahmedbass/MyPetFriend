package com.ahmedbass.mypetfriend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class PetInfoFragment extends Fragment {
    private static final String ARG_PET = "myPet";

    ArrayList<String> timeLineEvents = new ArrayList<>(Arrays.asList(
            "hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
            , "hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
            , "hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
            , "hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
            , "hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
            , "hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
            , "hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"));

    Pet currentPet;
    TextView birthDate_txtv, gender_txtv, type_txtv, breed_txtv, weight_txtv, isNeutered_txtv, microchipNumber_txtv;
    String name, birthDate, gender, type, breed, weight, isNeutered, microchipNumber;
    int ageInMonth;
    int ageInYear;

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
            currentPet = (Pet) getArguments().getSerializable(ARG_PET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pet_info, container, false);
        final ListView timeline_lv = (ListView) rootView.findViewById(R.id.timeline_listview);
        final ListAdapter myAdapter = new MyAdapter(getContext(), timeLineEvents);

        final View petInfo_header = inflater.inflate(R.layout.fragment_pet_info_header, null);
        timeline_lv.addHeaderView(petInfo_header);
        timeline_lv.setAdapter(myAdapter);

        petInfo_header.setOnClickListener(null); //to prevent that ripple effect when clicking it

        birthDate_txtv = (TextView) rootView.findViewById(R.id.birthDate_txtv);
        gender_txtv = (TextView) rootView.findViewById(R.id.gender_txtv);
        type_txtv = (TextView) rootView.findViewById(R.id.type_txtv);
        breed_txtv = (TextView) rootView.findViewById(R.id.breed_txtv);
        weight_txtv = (TextView) rootView.findViewById(R.id.weight_txtv);
        isNeutered_txtv = (TextView) rootView.findViewById(R.id.isNeutered_txtv);
        microchipNumber_txtv = (TextView) rootView.findViewById(R.id.microchipNumber_txtv);

        //assign the pet info into variables to display them on UI
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy", Locale.getDefault());
        //calculating the age in years and months
        ageInYear = currentPet.getPetAgeInYear(0);
        ageInMonth = currentPet.getPetAgeInMonth(0);
        birthDate = sdf.format(currentPet.getBirthDate()) +
                "\n(" + ageInYear + " years, " + ageInMonth + " months)";
        gender = currentPet.getGender();
        type = currentPet.getType();
        breed = currentPet.getBreed();
        weight = currentPet.getCurrentWeight() == -1 ? "Not set" : currentPet.getCurrentWeight() + " kg";
        isNeutered = currentPet.isNeutered() ? "Yes" : "No";
        microchipNumber = currentPet.getMicrochipNumber().isEmpty() ? "Not set" :currentPet.getMicrochipNumber();

        //set pet info on the UI
        birthDate_txtv.setText(birthDate);
        gender_txtv.setText(gender);
        type_txtv.setText(type);
        breed_txtv.setText(breed);
        weight_txtv.setText(weight);
        isNeutered_txtv.setText(isNeutered);
        microchipNumber_txtv.setText(microchipNumber);

        return rootView;
    }

    //-----------------------------------------------------
    private class MyAdapter extends ArrayAdapter<String> {

        MyAdapter(Context context, ArrayList<String> list) {
            super(context, 0, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_timeline, parent, false);
            }

            // Get the object located at this position in the list
            String currentRowItem = getItem(position);

            TextView text = (TextView) listItemView.findViewById(R.id.textView);
            text.setText(currentRowItem);

            return listItemView;
        }
    }
}
