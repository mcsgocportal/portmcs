package com.mcsgoc.www.portal;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mcsgoc.www.portal.helper.Constants;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class EnterNewsActivity extends AppCompatActivity implements View.OnClickListener {

    EditText department;

    EditText title;
    EditText content;
    Button submit;
    EditText date;
    private TextInputLayout textInputDate;
    private TextInputLayout textInputSection;
    private TextInputLayout textInputTitle;
    private TextInputLayout textInputDepartment;
    private TextInputLayout textInputContent;
    private static Date choosenDate;
    private static TextView textDateChoose;
    private ParseFile noticeFile;
    Spinner spinnerType;
    private String[] noticeType;
    private ProgressDialog dialog;
    private FloatingActionButton fab;
    public static final int REQUEST_FILE = 2;
    private File photoFile;
    private Intent captureImage;
    private TextView filesPreview;
    private boolean isSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_news);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());

        dialog = new ProgressDialog(this);

        department = (EditText) findViewById(R.id.editDepartment);
        title = (EditText) findViewById(R.id.editTitle);
        content = (EditText) findViewById(R.id.editContent);
        submit = (Button) findViewById(R.id.buttonSubmit);
        /*date = (EditText) findViewById(R.id.editDate);*/
        textInputSection = (TextInputLayout) findViewById(R.id.textInputSection);
        textInputTitle = (TextInputLayout) findViewById(R.id.textInputTitle);
        textInputDepartment = (TextInputLayout) findViewById(R.id.textInputDepartment);
        textInputContent = (TextInputLayout) findViewById(R.id.textInputContent);
        /*textInputDate = (TextInputLayout) findViewById(R.id.textInputDate);*/
        submit.setOnClickListener(this);
        textDateChoose = (TextView) findViewById(R.id.textDateChoose);
        textDateChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        filesPreview = (TextView) findViewById(R.id.imagePreview);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        noticeType = new String[]{"all", "student", "faculty"};
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, noticeType);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);
        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                }
                intent.setType("*/*");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_FILE);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_FILE && resultCode == RESULT_OK) {
            dialog.setMessage("uploading");
            dialog.show();
            ContentResolver resolver = getContentResolver();
            filesPreview.setVisibility(View.VISIBLE);
            Uri fileURi = data.getData();
            File file = new File(fileURi.getPath());
            String mimeType = resolver.getType(fileURi);
            try {
                InputStream inputStream = resolver.openInputStream(fileURi);
                byte[] bytes = convertFileToByteArray(inputStream);
                saveToDatabase(bytes, mimeType);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static byte[] convertFileToByteArray(InputStream inputStream) {
        byte[] byteArray = null;
        try {
            /*InputStream inputStream = new FileInputStream(f);*/
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024 * 8];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }

            byteArray = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return byteArray;
    }

    private void saveToDatabase(byte[] bytes, String mimeType) {
        noticeFile = new ParseFile("notice" + mimeType.split("/")[1], bytes, mimeType);
        noticeFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    isSaved = true;
                    dialog.dismiss();
                } else {
                    e.printStackTrace();
                    Log.i("ERROR", e.getMessage());
                    dialog.dismiss();
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {
                dialog.setMessage("uploading " + percentDone);
            }
        });
    }

    @Override
    public void onClick(View view) {
        String inputDepartment = department.getText().toString().trim();
        String inputTitle = title.getText().toString().trim();
        String inputContent = content.getText().toString().trim();
        //String inputDate = date.getText().toString().trim();
        String chosenNoticeType = spinnerType.getSelectedItem().toString();


        if (!inputDepartment.isEmpty() && !inputTitle.isEmpty() && !inputContent.isEmpty()) {
            if (choosenDate == null) {
                choosenDate = new Date();
            }
            saveToOnlineNotice(inputContent, inputDepartment, inputTitle, choosenDate,chosenNoticeType);

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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void saveToOnlineNotice(String inputContent, String inputDepartment, final String inputTitle, Date dateC,String chosenNoticeType) {

        final ProgressDialog dialog = ProgressDialog.show(this, "wait", "loading data", true);
        ParseObject newNotice = new ParseObject(Constants.DIR_COL_NEWS);
        newNotice.put(Constants.DIR_COL_NEWS_SUB, inputTitle);
        newNotice.put(Constants.DIR_COL_NEWS_NOTICE, inputContent);
        newNotice.put(Constants.DIR_COL_NEWS_DEP, inputDepartment);

        newNotice.put(Constants.DATE, dateC);
        newNotice.put(Constants.DIR_COL_USER_TYPE, chosenNoticeType);
        if (isSaved) {
            newNotice.put(Constants.DIR_COL_NEWS_ATTACHMENT, noticeFile);
        }

        newNotice.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e == null) {
                    Snackbar.make(submit, "Successfull", Snackbar.LENGTH_LONG).show();
                    ParsePush push = new ParsePush();
                    push.setChannel("Notice");
                    push.setMessage(inputTitle);
                    push.sendInBackground();

                } else {
                    Snackbar.make(submit, e.getMessage() + "Please try again", Snackbar.LENGTH_INDEFINITE).show();
                }
            }
        });

        dialog.show();
    }


    class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker

            Calendar c = GregorianCalendar.getInstance(TimeZone.getDefault(), Locale.ENGLISH);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            choosenDate = new Date(year-1900, month, day);
            showChosenDate(choosenDate);
        }

    }

    private void showChosenDate(Date choosenDate) {
        textDateChoose.setText(getFormattedDate(choosenDate));
    }

    private String getFormattedDate(Date choosenDate) {

        DateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(choosenDate);
    }

}