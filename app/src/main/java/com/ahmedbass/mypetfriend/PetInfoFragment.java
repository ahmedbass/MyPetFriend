package com.ahmedbass.mypetfriend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class PetInfoFragment extends Fragment {

    ArrayList<String> myList = new ArrayList<>(Arrays.asList("hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
    ,"hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
    ,"hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
    ,"hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
    ,"hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
    ,"hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"
    ,"hello", "world", "what's up", "how's my list?", "hopefully this works", "okay see ya"));

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pet_info, container, false);
        final ListView myListView = (ListView) rootView.findViewById(R.id.listview);
        final ListAdapter MyAdapter = new MyAdapter(getContext(), myList);

        final View petInfo_header = inflater.inflate(R.layout.header_view, null);
        myListView.addHeaderView(petInfo_header);
        myListView.setAdapter(MyAdapter);

        return rootView;
    }

    private class MyAdapter extends ArrayAdapter<String>{

        public MyAdapter(Context context, ArrayList<String> list) {
            super(context, 0, list);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_row, parent, false);
            }

            // Get the AndroidFlavor object located at this position in the list
            String currentRowItem = getItem(position);

            TextView text = (TextView) listItemView.findViewById(R.id.textView);
            text.setText(currentRowItem);

            return listItemView;
        }
    }
}
