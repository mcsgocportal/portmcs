package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.mcsgoc.www.portal.fragments.NewsFragment;
import com.mcsgoc.www.portal.helper.ConnectionDetector;
import com.mcsgoc.www.portal.helper.Constants;
import com.mcsgoc.www.portal.helper.NewsItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements NewsFragment.RefreshNotice {
    ArrayList<NewsItem> noticeItems;
    private ArrayList<NewsItem> pastNoticeItems;
    private ArrayList<NewsItem> presentNoticeItems;
    private ArrayList<NewsItem> futureNoticeItems;
    private ViewPager pager;
    private TabLayout tabLayout;
    private ParseUser currentUser;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        if (!new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {
            Toast.makeText(NewsActivity.this, "No Internet Available", Toast.LENGTH_SHORT).show();
            //Intent i=new Intent(this,HomeActivity.class);
           // startActivity(i);
            finish();
        }
        currentUser = ParseUser.getCurrentUser();
        userType = currentUser.getString("type");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pager = (ViewPager) findViewById(R.id.pager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        loadNoticeList();

    }

    private void loadNoticeList() {
        noticeItems = new ArrayList<>();
        final ProgressDialog dialog = ProgressDialog.show(this, "loading...", "notice is loading.", true, true);

        ParseQuery<ParseObject> notificationQuery = new ParseQuery<>(Constants.DIR_COL_NEWS);
        notificationQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (ParseObject item : list) {
                            String noticeType = item.getString(Constants.DIR_COL_USER_TYPE);
                            if (noticeType != null && (noticeType.equals(userType) || noticeType.equals("all"))) {
                                String noticeContent = item.getString(Constants.DIR_COL_NEWS_NOTICE);
                                String objID = item.getObjectId();
                                String noticeTitle = item.getString(Constants.DIR_COL_NEWS_SUB);
                                String college = item.getString(Constants.DIR_COL_NEWS_COLLEGE);
                                String department = item.getString(Constants.DIR_COL_NEWS_DEP);
                                Date date = item.getDate(Constants.DATE);
                                noticeItems.add(new NewsItem(objID, noticeTitle, department, date));
                            }

                        }
                    } else {
                        Toast.makeText(NewsActivity.this, "no notice available" + list.size(), Toast.LENGTH_SHORT).show();
                    }
                    if (noticeItems.size() > 0) {
                        pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
                        tabLayout.setupWithViewPager(pager);
                        dialog.dismiss();
                    }
                } else {
                    dialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(NewsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void refreshNoticeList() {
        loadNoticeList();
    }

    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
            pastNoticeItems = new ArrayList<>();
            presentNoticeItems = new ArrayList<>();
            futureNoticeItems = new ArrayList<>();
            Date currentDate = new Date();
            for (NewsItem item : noticeItems) {
                Calendar cal1 = Calendar.getInstance();
                Calendar cal2 = Calendar.getInstance();
                cal1.setTime(item.getDate());
                cal2.setTime(currentDate);
                boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                        cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
                int compareDate = currentDate.compareTo(item.getDate());
                if (compareDate > 0 && !sameDay) {
                    pastNoticeItems.add(item);
                }
                if (sameDay) {
                    presentNoticeItems.add(item);
                }
                if (compareDate < 0) {
                    futureNoticeItems.add(item);
                }
            }

        }


        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return NewsFragment.newInstance(pastNoticeItems);
                case 1:
                    return NewsFragment.newInstance(presentNoticeItems);
                case 2:
                    return NewsFragment.newInstance(futureNoticeItems);
            }
            return null;
        }


        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position) {
                case 0:
                    return "Past";
                case 1:
                    return "Present";
                case 2:
                    return "Future";
            }
            return super.getPageTitle(position);
        }


    }
}
