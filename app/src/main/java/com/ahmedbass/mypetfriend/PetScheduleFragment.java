package com.ahmedbass.mypetfriend;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import static com.ahmedbass.mypetfriend.R.id.activitiesOfDay_listview;

public class PetScheduleFragment extends Fragment {
    private static final String ARG_PET = "myPet";

    ArrayList<Pet.ScheduleActivity> activitiesOfTheDay = new ArrayList<>();
    ListView activitiesOfDay_lv;
    ListAdapter myAdapter;
    Pet myPet;
    Calendar currentDate;

    public static PetScheduleFragment newInstance(Pet pet) {
        PetScheduleFragment fragment = new PetScheduleFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PET, pet);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myPet = (Pet) getArguments().getSerializable(ARG_PET);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_pet_schedule, container, false);

        final LinearLayout scheduleDate_lout = (LinearLayout) rootView.findViewById(R.id.scheduleDate_lout);
        final TextView scheduleDate_txtv = (TextView) rootView.findViewById(R.id.scheduleDate_txtv);
        final CalendarView calendarView = (CalendarView) rootView.findViewById(R.id.calendarview);
        activitiesOfDay_lv = (ListView) rootView.findViewById(activitiesOfDay_listview);

        //set Calendar Min Date to choose from (starts from pet birth date, max is no limits)
        calendarView.setMinDate(myPet.getBirthDate());
        currentDate = Calendar.getInstance();

        myAdapter = new MyAdapter(getContext(), getActivitiesOfSelectedDate(currentDate));
        activitiesOfDay_lv.setAdapter(myAdapter);
        activitiesOfDay_lv.setEmptyView(rootView.findViewById(R.id.emptyList_txtv));

        scheduleDate_lout.setOnClickListener(new View.OnClickListener() {
            //show/hide the calendarview
            @Override
            public void onClick(View v) {
                TransitionManager.beginDelayedTransition(container);
                calendarView.setVisibility(calendarView.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        scheduleDate_lout.setOnLongClickListener(new View.OnLongClickListener() {
            //on long click reset to the currentDate of today
            @Override
            public boolean onLongClick(View v) {
                if (!(currentDate.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
                        && currentDate.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR))) {
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
                } else if (currentDate.get(Calendar.DAY_OF_YEAR) >= today.get(Calendar.DAY_OF_YEAR) &&
                        currentDate.get(Calendar.DAY_OF_YEAR) <= today.get(Calendar.DAY_OF_YEAR) + 7 &&
                        ((currentDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)))) {
                    scheduleDate_txtv.setText("Activities of Next " + currentDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
                } else if (currentDate.get(Calendar.DAY_OF_YEAR) <= today.get(Calendar.DAY_OF_YEAR) &&
                        currentDate.get(Calendar.DAY_OF_YEAR) >= today.get(Calendar.DAY_OF_YEAR) - 7 &&
                        ((currentDate.get(Calendar.YEAR) == today.get(Calendar.YEAR)))) {
                    scheduleDate_txtv.setText("Activities of Last " + currentDate.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()));
                } else {
                    scheduleDate_txtv.setText("Activities of " + dayOfMonth + " " +
                            currentDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + ", " + year);
                }
                getActivitiesOfSelectedDate(currentDate);

                TransitionManager.beginDelayedTransition(container);
                view.setVisibility(View.GONE);
            }
        });

        return rootView;
    }

    // Here is the IMPORTANT METHOD that calculates which days have which activities
    public ArrayList<Pet.ScheduleActivity> getActivitiesOfSelectedDate(Calendar currentDate) {
        activitiesOfTheDay.clear(); //clear so it displays only the activities of that day (rather than accumulate on old replicate entries)
        Calendar createDate = Calendar.getInstance();
        createDate.setTimeInMillis(myPet.getBirthDate());

        //count the difference of days between the two dates
        int createDay = createDate.get(Calendar.DAY_OF_YEAR);
        int currentDay = currentDate.get(Calendar.DAY_OF_YEAR);
        //if years are different add or subtract number of days in year to current day value
        if (currentDate.get(Calendar.YEAR) > createDate.get(Calendar.YEAR)) {
            //number of days in that year (mostly 365, but could be 366) * number of years difference
            currentDay += (currentDate.getActualMaximum(Calendar.DAY_OF_YEAR) * (currentDate.get(Calendar.YEAR) - createDate.get(Calendar.YEAR)));
        } else if (currentDate.get(Calendar.YEAR) < createDate.get(Calendar.YEAR)) {
            currentDay -= (currentDate.getActualMaximum(Calendar.DAY_OF_YEAR) * (createDate.get(Calendar.YEAR) - currentDate.get(Calendar.YEAR)));
        }

        //if( (current - create) % frequency == 0) { //activity would happen on that day! so add it to the list of the day's activities.}
        for (int i = 0; i < myPet.getPetScheduleActivities().size(); i++) {
            try {
                if ((currentDay - createDay) % myPet.getPetScheduleActivities().get(i).getFrequency() == 0) {
                    activitiesOfTheDay.add(myPet.getPetScheduleActivities().get(i));
                }
            } catch (ArithmeticException e) {
                Toast.makeText(getContext(), "Schedule activity \"" +
                        myPet.getPetScheduleActivities().get(i).getType() + "\" is not set properly", Toast.LENGTH_SHORT).show();
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
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_schedule_activity, parent, false);
            }

            // Get the scheduleActivity located at this position in the list
            final Pet.ScheduleActivity currentScheduleActivity = getItem(position);

            ImageView activityIcon = (ImageView) listItemView.findViewById(R.id.scheduleActivityIcon_img);
            activityIcon.setImageResource(currentScheduleActivity.getIcon());

            final TextView activityType_txtv = (TextView) listItemView.findViewById(R.id.scheduleActivityType_txtv);
            activityType_txtv.setText(currentScheduleActivity.getType());
            if (!currentScheduleActivity.getNotes().isEmpty()) {
                activityType_txtv.setOnClickListener(new View.OnClickListener() {
                    boolean isNotesShown;

                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onClick(View view) {
                        TransitionManager.beginDelayedTransition((ViewGroup) view.getParent());
                        if (isNotesShown) {
                            activityType_txtv.setText(currentScheduleActivity.getType());
                            isNotesShown = false;
                        } else {
                            activityType_txtv.setText(currentScheduleActivity.getType() + "\n(" + currentScheduleActivity.getNotes() + ")");
                            isNotesShown = true;
                        }
                    }
                });
            }

            TextView activityTime_txtv = (TextView) listItemView.findViewById(R.id.scheduleActivityTime_txtv);
            activityTime_txtv.setText(currentScheduleActivity.getDisplayTime());

            ImageView activityINotificationIcon = (ImageView) listItemView.findViewById(R.id.scheduleActivityNotificationIcon_img);
            activityINotificationIcon.setImageResource(currentScheduleActivity.getNotificationStatusIcon());

            return listItemView;
        }
    }
}


