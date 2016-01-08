package com.mcsgoc.www.portal;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseUser;

public class ProfileActivity extends AppCompatActivity {
    EditText name;
    EditText enrollNumber;
    EditText email;
    EditText password;
    EditText phoneNo;
    EditText branch;
    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        ContentLoadingProgressBar progressBar = new ContentLoadingProgressBar(this);
        progressBar.show();

        name = (EditText) findViewById(R.id.editName);
        enrollNumber = (EditText) findViewById(R.id.editEnrollNo);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        phoneNo = (EditText) findViewById(R.id.editPhoneNo);
        branch = (EditText) findViewById(R.id.editBranch);

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (counter) {
                    case 1:
                        saveUpdatedProfile();
                        fab.setImageResource(android.R.drawable.ic_menu_edit);
                        enableEditing(false);
                        counter = 0;
                        break;
                    case 0:
                        enableEditing(true);
                        fab.setImageResource(android.R.drawable.ic_menu_save);
                        counter = 1;
                        break;
                }
            }
        });

        if (ParseUser.getCurrentUser() != null) {
            progressBar.hide();
            email.setText(ParseUser.getCurrentUser().get("email").toString());
            name.setText(ParseUser.getCurrentUser().get("name").toString());
            enrollNumber.setText(ParseUser.getCurrentUser().get("username").toString());
            phoneNo.setText(String.valueOf(ParseUser.getCurrentUser().getLong("contact_no")));
            branch.setText(ParseUser.getCurrentUser().get("branch").toString());
            password.setText(String.valueOf(ParseUser.getCurrentUser().getLong("roll_no")));

            toolbar.setTitle("Profile");

        } else {
            toolbar.setTitle("sorry");
        }
    }


    private void saveUpdatedProfile() {

    }

    private void enableEditing(boolean b) {
        email.setEnabled(b);
        password.setEnabled(b);
        phoneNo.setEnabled(b);

    }
}
