package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.mcsgoc.www.portal.fragments.PersonFragment;
import com.mcsgoc.www.portal.helper.Constants;
import com.mcsgoc.www.portal.helper.Person;
import com.mcsgoc.www.portal.helper.RecyclerItemClickListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class DirectoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public static final String VIEW_PERSON = "view_person";
    private RecyclerView directoryList;
    private ArrayList<Person> items;
    private MyAdapter adapter;
    private ArrayList<Person> searchItems;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);


        SearchView searchView = (SearchView) findViewById(R.id.search);
        searchView.setOnQueryTextListener(this);
        directoryList = (RecyclerView) findViewById(R.id.recyler);
        directoryList.setLayoutManager(new LinearLayoutManager(this));
        directoryList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("tag", String.valueOf(position) + items.get(position));
            }
        }));
        items = new ArrayList<>();
        final ProgressDialog dialog = ProgressDialog.show(this, "wait", "loading data", true);
        ParseQuery<ParseObject> getDir = new ParseQuery<>(Constants.TABLE_DIRECTORY);

        getDir.orderByAscending(Constants.DIR_COL_COLEGE);
        getDir.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    if (list.size() > 0) {
                        for (ParseObject item : list) {
                            String name = item.getString(Constants.DIR_COL_NAME);
                            String college = item.getString(Constants.DIR_COL_COLEGE);
                            String contact = String.valueOf(item.getLong(Constants.DIR_COL_CONTACT));
                            String dept = item.getString(Constants.DIR_COL_DEP);
                            String design = item.getString(Constants.DIR_COL_DESIG);
                            String email = item.getString(Constants.DIR_COL_EMAIL);
                            items.add(new Person(name, college, design, dept, contact, email));
                        }

                        adapter = new MyAdapter(items);
                        directoryList.setAdapter(adapter);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(DirectoryActivity.this, "" + list.size(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });




        directoryList.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (searchItems != null && searchItems.size() > 0) {
                    showDetailFragment(position, searchItems);
                } else {
                    showDetailFragment(position, items);
                }
            }

        }));
    }

    private void showDetailFragment(int position, ArrayList<Person> items) {
        Person person = items.get(position);
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom, R.anim.abc_fade_out, R.anim.abc_fade_in, R.anim.abc_popup_exit);
        transaction.replace(R.id.wrapper, PersonFragment.newInstance(person), VIEW_PERSON).addToBackStack("display").commit();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        searchItems = new ArrayList<>();

        if (newText.length() > 0) {
            for (int i = 0; i < items.size(); i++) {
                Person person = items.get(i);
                if (person.name.toLowerCase().contains(newText)) {
                    Log.i("tag", person.name + "\n");
                    searchItems.add(person);
                }

            }
            MyAdapter searchAdapter = new MyAdapter(searchItems);
            directoryList.swapAdapter(searchAdapter, true);

        } else {
            searchItems.clear();
            directoryList.swapAdapter(adapter, true);
        }
        return true;
    }
}
