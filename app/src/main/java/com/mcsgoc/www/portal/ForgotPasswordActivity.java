package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email;
    Button submit;
    private ProgressDialog dialog;
    private ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        dialog=new ProgressDialog(this);
        dialog.setCancelable(false);
        email= (EditText) findViewById(R.id.email);
        submit= (Button) findViewById(R.id.submit);
        submit.setOnClickListener(this);

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
    public void onClick(final View view) {
        dialog.show();

        String inputemail = email.getText().toString().trim();

        if (inputemail.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "please enter email", Toast.LENGTH_SHORT).show();

        }
        if (!inputemail.isEmpty()) {

            ParseUser.requestPasswordResetInBackground(inputemail, new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                    dialog.dismiss();
                    if (e == null) {
                        Snackbar.make(view,"An email was successfully sent with reset instructions.",Snackbar.LENGTH_LONG) .show();
                    } else {
                        Snackbar.make(view,"Something went wrong. Look at the ParseException to see what's up.",Snackbar.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
