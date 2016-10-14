package com.ahmedbass.mypetfriend;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MyPetsActivity extends AppCompatActivity {

    private ArrayList<Pet> myListOfPets = new ArrayList<>();
    LinearLayout myPetsList;
    //MyListOfPetsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);

        generateList();
    }

    private void generateList() {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH);
        int day = today.get(Calendar.DAY_OF_MONTH);
        today.set(year - 3, month - 3, day);
        //generate fake list
        myListOfPets.add(new Pet("Snowball", today.getTimeInMillis(),2,5,"Cat","Siamese"));
        myListOfPets.add(new Pet("Fluffy", System.currentTimeMillis(),1,7,"Cat", "Persian"));
        myListOfPets.add(new Pet("كلب البحر",System.currentTimeMillis(),1,25,"Dog","Golden Retriever"));



        //handle listview and assign adapter //now i'm changing this to HorizontalScrollView
        myPetsList= (LinearLayout)findViewById(R.id.my_pets_container_lout);

//        for (int i = 0; i < myListOfPets.size(); i++) {
//            LinearLayout singlePet_lout = (LinearLayout)findViewById(R.id.single_pet_lout);
//            myPetsList.addView(singlePet_lout);
//        }


//        //instantiate custom adapter
//        adapter = new MyListOfPetsAdapter(this, myListOfPets);
//        myPetsList.setAdapter(adapter);
//        //when click on existing pet, move to its profile
//        myPetsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent moveToPetProfile = new Intent(getBaseContext(), PetProfileActivity.class);
//                moveToPetProfile.putExtra("petInfo", adapter.getItem(position));
//                startActivity(moveToPetProfile);
//            }
//        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu. this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.mypets_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                //startActivity(new Intent(this, AddNewPetActivity.class));
                Toast.makeText(this, "TODO: Open a new activity to create a new pet profile", Toast.LENGTH_SHORT).show();
                return true;

            default:
                // If we got here, the user's action was not recognized. Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    //---------------------------------------------------------------------

    class MyListOfPetsAdapter extends ArrayAdapter<Pet> {

        MyListOfPetsAdapter(Activity context, ArrayList<Pet> pets) {
            super(context, 0, pets);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_pet, parent, false);
            }

            // Get the Pet object located at this position in the list
            Pet currentPet = getItem(position);

//        ImageView petPhoto = (ImageView) listItemView.findViewById(R.id.list_pet_thumbnail);
//        if (!currentPet.getAllPhotos().contains(currentPet)) {
//            petPhoto.setImageBitmap(currentPet.getAllPhotos().get(0));
//            Toast.makeText(getContext(), "TODO: should change the thumbnail based on pet's top photo", Toast.LENGTH_SHORT).show();
//        }
            TextView petName = (TextView) listItemView.findViewById(R.id.list_pet_name);
            assert currentPet != null;
            petName.setText(currentPet.getName());

            return listItemView;
        }
    }
}
