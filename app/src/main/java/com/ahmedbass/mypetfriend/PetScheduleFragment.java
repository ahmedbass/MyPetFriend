package com.ahmedbass.mypetfriend;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import static com.ahmedbass.mypetfriend.R.id.activitiesOfDay_listview;

public class PetScheduleFragment extends Fragment {

    ArrayList<Pet.ScheduleActivity> activitiesOfTheDay = new ArrayList<>();
    ListView activitiesOfDay_lv;
    ListAdapter myAdapter;
    Pet pet = new Pet();
    Calendar currentDate;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pet_schedule, container, false);

        final TextView scheduleDate_txtv = (TextView) rootView.findViewById(R.id.scheduleDate_txtv);
        final CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.calendarview);
        activitiesOfDay_lv = (ListView) rootView.findViewById(activitiesOfDay_listview);

        //TODO remove this dummy data
        Calendar date = Calendar.getInstance();
        date.set(2016,11,4,9,0);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_BREAKFAST, date, 1, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_OFF);
        date.set(2016,11,4,11,30);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_GROOMING, date, 5, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_ON);
        date.set(2016,11,4,19,45);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_DINNER, date, 1, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_OFF);
        date.set(2016,11,4,14,30);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_MEDICAL_CHECKUP, date, 100, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_ALARM);
        date.set(2016,11,4,16,0);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_TRAINING, date, 3, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_ON);
        date.set(2016,11,4,15,30);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_WEIGHING, date, 30, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_ON);
        date.set(2016,11,4,19,0);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_BIRTHDAY, date, 363, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_ALARM);
        date.set(2016,11,4,10,0);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_EXERCISING, date, 363, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_ON);
        date.set(2016,11,4,11,0);
        pet.addNewScheduleActivity(Pet.ScheduleActivity.TYPE_BATHING, date, 363, "notes", Pet.ScheduleActivity.NOTIFICATION_STATUS_ALARM);

        myAdapter = new MyAdapter(getContext(), getActivitiesOfSelectedDate(Calendar.getInstance()));
        activitiesOfDay_lv.setAdapter(myAdapter);
        activitiesOfDay_lv.setEmptyView(rootView.findViewById(R.id.emptyList_txtv));


        //set Calendar Min & Max currentDate to choose from (after one year)
        currentDate = Calendar.getInstance();
        currentDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR)-1); //TODO set this to the startDate of pet registration
        calendarView.setMinDate(currentDate.getTimeInMillis());
        currentDate.set(Calendar.YEAR, currentDate.get(Calendar.YEAR)+2);
        calendarView.setMaxDate(currentDate.getTimeInMillis());
        currentDate.setTimeInMillis(Calendar.getInstance().getTimeInMillis());


        scheduleDate_txtv.setOnClickListener(new View.OnClickListener() {
            //show/hide the calendarview
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(container);
                calendarView.setVisibility(calendarView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        scheduleDate_txtv.setOnLongClickListener(new View.OnLongClickListener() {
            //on long click reset to the currentDate of today
            @Override
            public boolean onLongClick(View v) {
                if(currentDate.get(Calendar.DAY_OF_YEAR) != Calendar.getInstance().get(Calendar.DAY_OF_YEAR)) {
                    currentDate.setTimeInMillis(System.currentTimeMillis());
                    calendarView.setDate(System.currentTimeMillis());
                    scheduleDate_txtv.setText("Activities of Today");
                    TransitionManager.beginDelayedTransition(container);
                    getActivitiesOfSelectedDate(Calendar.getInstance());
                }
                return true;
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            Calendar today = Calendar.getInstance();
            int tomorrow, yesterday;
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                tomorrow = today.get(Calendar.DAY_OF_YEAR) + 1;
                yesterday = today.get(Calendar.DAY_OF_YEAR) - 1;
                currentDate.set(year, month, dayOfMonth);

                //if currentDate is today or tomorrow or yesterday, simply display that instead of the whole currentDate
                if ((currentDate.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) &&
                        ((currentDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)))) {
                    scheduleDate_txtv.setText("Activities of Today");
                } else if (currentDate.get(Calendar.DAY_OF_YEAR) == tomorrow &&
                        ((currentDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)))) {
                    scheduleDate_txtv.setText("Activities of Tomorrow");
                } else if (currentDate.get(Calendar.DAY_OF_YEAR) == yesterday &&
                        ((currentDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)))) {
                    scheduleDate_txtv.setText("Activities of Yesterday");
                } else {
                    scheduleDate_txtv.setText("Activities of " + dayOfMonth + " " +
                            currentDate.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault()) + ", " + year);
                }
                getActivitiesOfSelectedDate(currentDate);

                TransitionManager.beginDelayedTransition(container);
                view.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    // Here is the IMPORTANT METHOD that calculates which days have which activities
    public ArrayList<Pet.ScheduleActivity> getActivitiesOfSelectedDate(Calendar currentDate){
        activitiesOfTheDay.clear(); //clear so it displays only the activities of that day
        Calendar createDate = Calendar.getInstance();
//        createDate.setTimeInMillis(pet.getCreateDate()); //TODO change to get the pet createDate

        //count the difference of days between the two dates
        int createDay = createDate.get(Calendar.DAY_OF_YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_YEAR);

        if(currentDate.get(Calendar.YEAR) > createDate.get(Calendar.YEAR) ){
            //number of days in that year (mostly 365, but could be 366) * number of years difference
            currentDay += (currentDate.getActualMaximum(Calendar.DAY_OF_YEAR) * (currentDate.get(Calendar.YEAR) - createDate.get(Calendar.YEAR))) ;
        }
        else if( currentDate.get(Calendar.YEAR) < createDate.get(Calendar.YEAR)){
            currentDay -= (currentDate.getActualMaximum(Calendar.DAY_OF_YEAR) * (createDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR)));
        }

        //if( (current - start) % frequency == 0) { //activity would happen on that day! so add it to the list of the day's activities.}
        for(int i = 0; i < pet.getScheduleActivities().size(); i++ ) {
            if((currentDay - createDay) % pet.getScheduleActivities().get(i).getFrequency() == 0){
                activitiesOfTheDay.add(pet.getScheduleActivities().get(i));
            }
        }

        //arrange the activities based on their hour of the day
        Collections.sort(activitiesOfTheDay, new Comparator<Pet.ScheduleActivity>() {
            @Override
            public int compare(Pet.ScheduleActivity activity1, Pet.ScheduleActivity activity2) {
                if ((((Integer) activity1.getHourOfDay()).compareTo(activity2.getHourOfDay())) == 0) {
                    return (((Integer) activity1.getMinuteOfDay()).compareTo(activity2.getMinuteOfDay()));
                }
                return (((Integer) activity1.getHourOfDay()).compareTo(activity2.getHourOfDay()));
            }
        });

        //set adapter after updating the arraylist to update the listview
        activitiesOfDay_lv.setAdapter(myAdapter);
        return activitiesOfTheDay;
    }

    //------------------------------------------------------------------
    private class MyAdapter extends ArrayAdapter<Pet.ScheduleActivity> {

        MyAdapter(Context context, ArrayList<Pet.ScheduleActivity> activitiesList) {
            super(context, 0, activitiesList);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            // Check if the existing view is being reused, otherwise inflate the view
            View listItemView = convertView;
            if(listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_schedule_activity, parent, false);
            }

            // Get the scheduleActivity located at this position in the list
            Pet.ScheduleActivity currentScheduleActivity = getItem(position);

            ImageView activityIcon = (ImageView) listItemView.findViewById(R.id.scheduleActivityIcon_img);
            activityIcon.setImageResource(currentScheduleActivity.getIcon());

            TextView activityType_txtv = (TextView) listItemView.findViewById(R.id.scheduleActivityType_txtv);
            activityType_txtv.setText(currentScheduleActivity.getType());

            TextView activityTime_txtv = (TextView) listItemView.findViewById(R.id.scheduleActivityTime_txtv);
            activityTime_txtv.setText(currentScheduleActivity.getDisplayTime());

            ImageView activityINotificationIcon = (ImageView) listItemView.findViewById(R.id.scheduleActivityNotificationIcon_img);
            activityINotificationIcon.setImageResource(currentScheduleActivity.getNotificationStatusIcon());

            return listItemView;
        }
    }
}


