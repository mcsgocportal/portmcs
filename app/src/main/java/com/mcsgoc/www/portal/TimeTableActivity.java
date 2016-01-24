package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mcsgoc.www.portal.fragments.DayFragment;
import com.mcsgoc.www.portal.helper.ConnectionDetector;
import com.mcsgoc.www.portal.helper.Constants;
import com.mcsgoc.www.portal.helper.TimeTableItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimeTableActivity extends AppCompatActivity {

    ArrayList<TimeTableItem> timeTableItems;
    int[] Colors = new int[]{Color.RED, Color.GREEN, Color.CYAN};
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        if (!new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {
            Toast.makeText(TimeTableActivity.this, "No Internet Available", Toast.LENGTH_SHORT).show();
            // Intent i=new Intent(this,HomeActivity.class);
            //startActivity(i);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressDialog dialog = ProgressDialog.show(this, "loading...", "timetable is loading.", false);
        dialog.setCancelable(true);
        timeTableItems = new ArrayList<>();
        // fetching data from parse
        ParseQuery<ParseObject> timeTableQuery = new ParseQuery<ParseObject>(Constants.TIMETABLE);
        timeTableQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (ParseObject item : list) {

                            String subjectName = item.getString(Constants.SUBJECT_NAME);
                            String subjectCode = item.getString(Constants.SUBJECT_CODE);
                            String teacherName = item.getString(Constants.TEACHER_NAME);
                            String place = item.getString(Constants.PLACE);
                            String schedule = item.getString(Constants.SCHEDULE);
                            final ViewPager pager = (ViewPager) findViewById(R.id.pager);
                            final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                            tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
                            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                            timeTableItems.add(new TimeTableItem(teacherName, subjectName, subjectCode, place, schedule));

                            pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
                            tabLayout.setupWithViewPager(pager);
                            dialog.dismiss();
                        }
                    } else {
                        Toast.makeText(TimeTableActivity.this, "" + list.size(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(TimeTableActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return DayFragment.newInstance(timeTableItems);
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Monday";
                case 1:
                    return "Tuesday";
                case 2:
                    return "Wednesday";
                case 3:
                    return "Thusday";
                case 4:
                    return "Friday";
                case 5:
                    return "Saturday";


            }
            return super.getPageTitle(position);
        }
    }
}
