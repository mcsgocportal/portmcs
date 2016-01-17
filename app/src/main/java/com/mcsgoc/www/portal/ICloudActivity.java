package com.mcsgoc.www.portal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class ICloudActivity extends AppCompatActivity implements View.OnClickListener {

    Button profile,timetable,edittime,fees,notice,chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_icloud);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        profile= (Button) findViewById(R.id.profilebtn);
        profile.setOnClickListener(this);
        timetable= (Button) findViewById(R.id.timebtn);
        timetable.setOnClickListener(this);
        edittime= (Button) findViewById(R.id.edittimebtn);
        edittime.setOnClickListener(this);
        fees= (Button) findViewById(R.id.feesbtn);
        fees.setOnClickListener(this);
        notice= (Button) findViewById(R.id.noticebtn);
        notice.setOnClickListener(this);
        chat= (Button) findViewById(R.id.chatbtn);
        chat.setOnClickListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_icloud, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.action_profile)
        {
            Intent i=new Intent(this,ProfileActivity.class);
            startActivity(i);
            return true;
        }

        if (id==R.id.action_enter_timetable)
        {
            Intent i=new Intent(this,EnterTimeTableActivity.class);
            startActivity(i);
            return true;
        }

        if (id==R.id.action_timetable)
        {
            Intent i=new Intent(this,TimeTableActivity.class);
            startActivity(i);
            return true;
        }

        if (id==R.id.action_fees)
        {
            Intent i=new Intent(this,FeesActivity.class);
            startActivity(i);
            return true;
        }

        if (id==R.id.action_send_notice)
        {
            Intent i=new Intent(this,EnterNewsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();

        switch (id)
        {
            case R.id.profilebtn:
                Intent i=new Intent(this,ProfileActivity.class);
                startActivity(i);
                break;
            case R.id.timebtn:
                Intent j=new Intent(this,TimeTableActivity.class);
                startActivity(j);
                break;
            case R.id.edittimebtn:
                Intent k=new Intent(this,EnterTimeTableActivity.class);
                startActivity(k);
                break;
            case R.id.feesbtn:
                Intent l=new Intent(this,FeesActivity.class);
                startActivity(l);
                break;
            case R.id.noticebtn:
                Intent m=new Intent(this,EnterNewsActivity.class);
                startActivity(m);
                break;
            case R.id.chatbtn:
                break;




        }

    }
}
