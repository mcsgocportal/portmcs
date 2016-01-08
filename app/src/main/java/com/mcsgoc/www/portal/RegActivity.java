package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class RegActivity extends AppCompatActivity {

    EditText name, email, rollno, pass, colg, branch, year, mob;

    Button submit;
    private CollapsingToolbarLayout toolBarLayout;
    private Toolbar toolbar;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        initViews();


    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        name = (EditText) findViewById(R.id.regName);
        email = (EditText) findViewById(R.id.regEmail);
        rollno = (EditText) findViewById(R.id.regRoll);
        pass = (EditText) findViewById(R.id.regPass);
        colg = (EditText) findViewById(R.id.college);
        branch = (EditText) findViewById(R.id.regBranch);
        year = (EditText) findViewById(R.id.regYear);
        mob = (EditText) findViewById(R.id.regMob);
        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = ProgressDialog.show(RegActivity.this, "wait", "registering..");
                registerUser(view);
            }
        });
    }

    private void registerUser(View view) {
        String strName = name.getText().toString().trim();
        String strEmail = email.getText().toString().trim();
        String strRoll = rollno.getText().toString().trim();
        String strPass = pass.getText().toString().trim();
        String strColg = colg.getText().toString().trim();
        String strBranch = branch.getText().toString().trim();
        String strYear = year.getText().toString().trim();
        String strMobile = mob.getText().toString().trim();

        if (validate(strName) && validate(strEmail) && validate(strRoll) && validate(strPass) && validate(strColg) && validate(strMobile)) {
            long numMobile = Long.parseLong(strMobile);
            int numRoll = Integer.parseInt(strRoll);
            int numYear = Integer.parseInt(strYear);


            if (registerUserToParseFinally(strName, strEmail, numRoll, strPass, strColg, strBranch, numYear, numMobile)) {
                dialog.hide();
                // do something awesome here
            } else {
                dialog.hide();
                Snackbar.make(view, "Sorry, try again after sometime", Snackbar.LENGTH_LONG).show();
            }
        } else {
            dialog.hide();
            Snackbar.make(view, "Please fill all fields correctly", Snackbar.LENGTH_LONG).show();
        }


    }


    private boolean validate(String str) {
        Log.e("TAG", str);
        return !(str.isEmpty() || str.length() <= 1 || str.length() >= 64);
    }

    private boolean registerUserToParseFinally(String strName, String strEmail, int numRoll, String strPass, String strColg, String strBranch, int numYear, long numMobile) {

        ParseUser newUser = new ParseUser();
        newUser.setUsername(strName);
        newUser.setPassword(strPass);
        newUser.setEmail(strEmail);
        newUser.put("roll_no", numRoll);
        newUser.put("contact_no", numMobile);
        newUser.put("year", numYear);
        newUser.put("branch", strBranch);
        newUser.put("college", strColg);
        newUser.put("student", "student");
        newUser.signUpInBackground(new SignUpCallback() {

            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Toast.makeText(RegActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }

        });
        return true;
    }
}
