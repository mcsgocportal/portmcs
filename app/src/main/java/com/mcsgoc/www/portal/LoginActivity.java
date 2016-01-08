package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button facregister, studreg, login;

    EditText name, pass;

    TextView forgotPassword;

    private ProgressDialog dialog;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);

        facregister = (Button) findViewById(R.id.fac);
        facregister.setOnClickListener(this);
        studreg = (Button) findViewById(R.id.stud);
        studreg.setOnClickListener(this);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        name = (EditText) findViewById(R.id.name);
        pass = (EditText) findViewById(R.id.college);
        forgotPassword= (TextView) findViewById(R.id.forgot);

        user = ParseUser.getCurrentUser();
        if (user != null) {
            String sessionToken = user.getSessionToken();
            Intent homeIntent = new Intent(LoginActivity.this, ICloudActivity.class);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
            finish();

        } else {
            user = new ParseUser();
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
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

    private void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(final View view) {

        if (view.getId()==R.id.login)
        {

        String inputUserId = name.getText().toString().trim();
        String inputpassword = pass.getText().toString().trim();

        if (inputUserId.isEmpty()) {
            Toast.makeText(LoginActivity.this, "please fill the username", Toast.LENGTH_SHORT).show();
        } else if (inputpassword.isEmpty()) {
            Toast.makeText(LoginActivity.this, "please fill the password", Toast.LENGTH_SHORT).show();
        }
        if (!inputpassword.isEmpty() && !inputUserId.isEmpty()) {
            dialog.show();
            ParseUser.logInInBackground(inputUserId, inputpassword, new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    dialog.hide();
                    if (e == null) {

                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        String sessionToken = user.getSessionToken();
                        Intent homeIntent = new Intent(LoginActivity.this, ICloudActivity.class);
                        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(homeIntent);
                        finish();

                    } else {
                        Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                    }
                    LoginActivity.this.name.setText("");
                    LoginActivity.this.pass.setText("");
                }
            });
        }

        }

        if (view.getId() == R.id.stud) {
            Intent i = new Intent(this, RegActivity.class);
            startActivity(i);
        }

        if (view.getId()==R.id.fac){
            Intent a = new Intent(this, FacultyRegActivity.class);
            startActivity(a);
        }
    }
}
