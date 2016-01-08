package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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

import com.mcsgoc.www.portal.fragments.NewsFragment;
import com.mcsgoc.www.portal.helper.Constants;
import com.mcsgoc.www.portal.helper.NewsItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsActivity extends AppCompatActivity {
    ArrayList<NewsItem> noticeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        noticeItems = new ArrayList<>();
        final ProgressDialog dialog = ProgressDialog.show(this, "loading...", "notice is loading.", true, true);

        ParseQuery<ParseObject> notificationQuery = new ParseQuery<>(Constants.DIR_COL_NEWS_NOTICE);
        notificationQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (ParseObject item : list) {
                            String objID = item.getObjectId();
                            String noticeTitle = item.getString(Constants.DIR_COL_NEWS_SUB);
                            String college = item.getString(Constants.DIR_COL_NEWS_COLLEGE);
                            String department = item.getString(Constants.DIR_COL_NEWS_DEP);
                            Date date = item.getDate(Constants.DATE);
                            final ViewPager pager = (ViewPager) findViewById(R.id.pager);
                            final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
                            tabLayout.setTabMode(TabLayout.MODE_FIXED);
                            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
                            noticeItems.add(new NewsItem(objID, noticeTitle, department, date));
                            pager.setAdapter(new MyAdapter(getSupportFragmentManager()));
                            tabLayout.setupWithViewPager(pager);
                        }
                    } else {
                        Toast.makeText(NewsActivity.this, "" + list.size(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewsActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });




    }

    private class MyAdapter extends FragmentPagerAdapter {
        private ArrayList<NewsItem> pastNoticeItems;
        private ArrayList<NewsItem> presentNoticeItems;
        private ArrayList<NewsItem> futureNoticeItems;

        public MyAdapter(FragmentManager fm) {
            super(fm);
            pastNoticeItems = new ArrayList<>();
            presentNoticeItems = new ArrayList<>();
            futureNoticeItems = new ArrayList<>();
            Date currentDate = new Date();
            for (NewsItem item : noticeItems) {
                int compareDate = currentDate.compareTo(item.getDate());
                if (compareDate > 0) {
                    pastNoticeItems.add(item);
                }
                if (compareDate == 0) {
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

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }
}
