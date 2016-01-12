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
import android.widget.TextView;

import com.mcsgoc.www.portal.helper.Constants;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

import java.util.Date;

public class EnterNewsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText department;

    EditText title;
    EditText content;
    Button submit;
    EditText date;
    private TextInputLayout textInputDate;
    private TextInputLayout textInputTitle;
    private TextInputLayout textInputDepartment;
    private TextInputLayout textInputContent;
    private static Date choosenDate;
    private TextView textDateChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        department = (EditText) findViewById(R.id.editDepartment);
        title = (EditText) findViewById(R.id.editTitle);
        content = (EditText) findViewById(R.id.editContent);
        submit = (Button) findViewById(R.id.buttonSubmit);
        /*date = (EditText) findViewById(R.id.editDate);*/
        textInputTitle = (TextInputLayout) findViewById(R.id.textInputTitle);
        textInputDepartment = (TextInputLayout) findViewById(R.id.textInputDepartment);
        textInputContent = (TextInputLayout) findViewById(R.id.textInputContent);
        /*textInputDate = (TextInputLayout) findViewById(R.id.textInputDate);*/
        submit.setOnClickListener(this);
        textDateChoose = (TextView) findViewById(R.id.textDateChoose);

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
    public void onClick(View view) {
        String inputDepartment = department.getText().toString().trim();
        String inputTitle = title.getText().toString().trim();
        String inputContent = content.getText().toString().trim();
        //String inputDate = date.getText().toString().trim();


        if (!inputDepartment.isEmpty()&& !inputTitle.isEmpty() && !inputContent.isEmpty()) {
            if (choosenDate == null) {
                choosenDate = new Date();
            }
            saveToOnlineNotice(inputContent, inputDepartment, inputTitle, choosenDate);

        } else {
            if (inputDepartment.isEmpty()) {
                textInputDepartment.setError("Please fill the Department");

            }

            if (inputTitle.isEmpty()) {
                textInputTitle.setError("Please fill the Phone Title");

            }
            if (inputContent.isEmpty()) {
                textInputContent.setError("Please fill the Content");
            }

        }
    }

    private void saveToOnlineNotice(String inputContent,String inputDepartment, String inputTitle, Date dateC) {

        final ProgressDialog dialog = ProgressDialog.show(this, "wait", "loading data", true);
        ParseObject newNotice = new ParseObject(Constants.DIR_COL_NEWS);
        newNotice.put(Constants.DIR_COL_NEWS_SUB, inputTitle);
        newNotice.put(Constants.DIR_COL_NEWS_NOTICE, inputContent);
        newNotice.put(Constants.DIR_COL_NEWS_DEP, inputDepartment);

        newNotice.put(Constants.DATE, dateC);
        newNotice.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e == null) {
                    Snackbar.make(submit, "Successfull", Snackbar.LENGTH_LONG).show();

                } else {
                    Snackbar.make(submit, e.getMessage() + "Please try again", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });

        dialog.show();
    }
}
