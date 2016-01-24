package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mcsgoc.www.portal.fragments.FeesRecyclerAdapter;
import com.mcsgoc.www.portal.helper.ConnectionDetector;
import com.mcsgoc.www.portal.helper.Constants;
import com.mcsgoc.www.portal.helper.FeesItem;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class FeesActivity extends AppCompatActivity {
    ArrayList<FeesItem> feesItems;
    private static ArrayList<FeesItem> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fees);

        if (!new ConnectionDetector(getApplicationContext()).isConnectingToInternet()) {
            Toast.makeText(FeesActivity.this, "No Internet Available", Toast.LENGTH_SHORT).show();
           // Intent i=new Intent(this,HomeActivity.class);
            //startActivity(i);
            finish();
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final ProgressDialog dialog = ProgressDialog.show(this, "loading...", "Fees is loading.", false);
        dialog.setCancelable(true);
        feesItems = new ArrayList<>();
        final RecyclerView feeslist = (RecyclerView) findViewById(R.id.recyclerFees);
        feeslist.setLayoutManager(new LinearLayoutManager(this));
        // fetching data from parse
        ParseQuery<ParseObject> fees = new ParseQuery<ParseObject>(Constants.FEES);
        fees.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (ParseObject item : list) {
                            String course = item.getString(Constants.COURSE);
                            int firstInst = item.getInt(Constants.FIRST_INSTALLMENT);
                            int secondInst = item.getInt(Constants.SECOND_INSTALLMENT);
                            feesItems.add(new FeesItem(course, firstInst, secondInst));
                        }

                        FeesRecyclerAdapter adapter = new FeesRecyclerAdapter(FeesActivity.this, feesItems);
                        feeslist.setAdapter(adapter);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(FeesActivity.this, "" + list.size(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(FeesActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.fees, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.action_rules:
                AlertDialog dialog = new AlertDialog.Builder(this).setMessage(R.string.fee_rules).setTitle("Rules").setPositiveButton("OK",null).create();
                dialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
