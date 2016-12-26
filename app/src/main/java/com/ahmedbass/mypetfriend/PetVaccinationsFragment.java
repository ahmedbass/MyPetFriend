package com.ahmedbass.mypetfriend;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class PetVaccinationsFragment extends Fragment {
    private static final String ARG_PARAM1 = "myPetInfo";
    private Pet myPet;

    public static PetVaccinationsFragment newInstance(Pet myPet) {
        PetVaccinationsFragment fragment = new PetVaccinationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, myPet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myPet = (Pet) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pet_vaccinations, container, false);
        ListView vaccinations_lstv = (ListView) rootView.findViewById(R.id.vaccinations_lstv);
        ArrayList<String> vaccinationNames = new ArrayList<>();
        final ArrayList<Pet.Vaccine> petVaccines = myPet.getPetVaccines();
        for (Pet.Vaccine vaccineItem : petVaccines) {
            vaccinationNames.add(vaccineItem.getName());
        }
        ArrayAdapter<String> vaccinationsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, vaccinationNames);
        vaccinations_lstv.setAdapter(vaccinationsAdapter);
        vaccinations_lstv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Dialog dialog = new Dialog(getContext());
                dialog.setTitle(petVaccines.get(position).getName());
                TextView textView = new TextView(getContext());
                textView.setPadding(20, 20, 20, 30);
                textView.setTextColor(Color.BLACK);
                String vaccineDetails = "Category: " + (petVaccines.get(position).getCategory() == Pet.Vaccine.CATEGORY_CORE ? "Core Vaccination" : "Non-Core Vaccination") +
                        "\n\n" + petVaccines.get(position).getNotes() +
                        "\n\nPrevious Booster: " + calculateVaccineBooster("previous", petVaccines.get(position).getCreateDate(), petVaccines.get(position).getFrequency()) +
                        "\nNext Booster: " + calculateVaccineBooster("next", petVaccines.get(position).getCreateDate(), petVaccines.get(position).getFrequency());
                textView.setText(vaccineDetails);
                dialog.setContentView(textView);
                dialog.show();
            }
        });
        vaccinations_lstv.setEmptyView(rootView.findViewById(R.id.emptyList_txtv));

        return rootView;
    }

    private String calculateVaccineBooster(String previousOrNextBooster, long createDateInMillis, int frequency) {
        Calendar currentDate = Calendar.getInstance();
        Calendar createDate = Calendar.getInstance();
        createDate.setTimeInMillis(createDateInMillis);

        //count the difference of days between the two dates
        int createDay = createDate.get(Calendar.DAY_OF_YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_YEAR);
        //if years are different add or subtract number of days in year to current day value
        if (currentDate.get(Calendar.YEAR) > createDate.get(Calendar.YEAR)) {
            //number of days in that year (mostly 365, but could be 366) * number of years difference
            currentDay += (currentDate.getActualMaximum(Calendar.DAY_OF_YEAR) * (currentDate.get(Calendar.YEAR) - createDate.get(Calendar.YEAR)));
        }

        if(previousOrNextBooster.equals("next")) {
            return "after " + (frequency - ((currentDay - createDay) % frequency)) + " days";
        } else {
            return ((currentDay - createDay) % frequency) + " days ago";
        }
    }
}
