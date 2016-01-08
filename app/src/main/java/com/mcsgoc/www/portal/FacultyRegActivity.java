package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class FacultyRegActivity extends AppCompatActivity {
    EditText name, id, email, pass, colg, mob, subject;

    Button submit;
    private CollapsingToolbarLayout toolBarLayout;
    private Toolbar toolbar;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_reg);
        initViews();

    }

    private void initViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        name = (EditText) findViewById(R.id.regName);
        id = (EditText) findViewById(R.id.regid);
        email = (EditText) findViewById(R.id.regEmail);
        pass = (EditText) findViewById(R.id.regPass);
        colg = (EditText) findViewById(R.id.college);
        mob = (EditText) findViewById(R.id.regMob);
        subject = (EditText) findViewById(R.id.regSubject);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(FacultyRegActivity.this, "wait", "registering..");
                registerUser(view);
            }
        });


    }

    private void registerUser(View view) {
        String strName = name.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strid = id.getText().toString().trim();
        String strPass = pass.getText().toString().trim();
        String strColg = colg.getText().toString().trim();
        String strSubject = subject.getText().toString().trim();

        String strMobile = mob.getText().toString().trim();

        if (validate(strName) && validate(strEmail) && validate(strid) && validate(strPass) && validate(strColg) && validate(strSubject) && validate(strMobile)) {
            long numMobile = Long.parseLong(strMobile);


            if (registerUserToParseFinally(strName, strEmail, strid, strPass, strColg, numMobile, strSubject)) {

                // do something awesome here
            } else {
                Snackbar.make(view, "Sorry, try again after sometime", Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(view, "Please fill all fields correctly", Snackbar.LENGTH_LONG).show();
        }


    }

    private boolean registerUserToParseFinally(String strName, String strEmail, String numid, String strPass, String strColg, long numMobile, String strSubject) {
        ParseUser newUser = new ParseUser();
        newUser.setUsername(strName);
        newUser.setPassword(strPass);
        newUser.setEmail(strEmail);
        newUser.put("faculty_id", numid);
        newUser.put("contact_no", numMobile);
        newUser.put("subject", strSubject);
        newUser.put("college", strColg);
        newUser.put("faculty", "faculty");

        newUser.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(FacultyRegActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FacultyRegActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

        });
        return true;
    }

    private boolean validate(String str) {
        Log.e("TAG", str);
        return !(str.isEmpty() || str.length() <= 1 || str.length() >= 64);
    }

}




