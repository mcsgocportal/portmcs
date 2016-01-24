package com.mcsgoc.www.portal;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.mcsgoc.www.portal.helper.Constants;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ProgressCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NoticeContentActivity extends AppCompatActivity {

    EditText department;
    EditText branch;
    EditText section;
    EditText title;
    EditText content;
    EditText date;
    private String noticeID;

    private ImageView imageNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);

        if (getIntent() != null) {
            noticeID = getIntent().getStringExtra(Constants.EXTRA_NOTICE_ID);
        }
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        date = (EditText) findViewById(R.id.editDate);
        department = (EditText) findViewById(R.id.editDepartment);
        branch = (EditText) findViewById(R.id.editBranch);

        title = (EditText) findViewById(R.id.editTitle);
        content = (EditText) findViewById(R.id.editContent);

        imageNotice = (ImageView) findViewById(R.id.noticeImageView);
        enableEditing(false);
        ParseQuery<ParseObject> currentNotification = new ParseQuery<>(Constants.DIR_COL_NEWS);
        // add a  progress dialog
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.show();
        currentNotification.getInBackground(noticeID, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject notice, ParseException e) {
                if (e == null) {
                    dialog.dismiss();
                    Date noticeDate = notice.getDate(Constants.DIR_COL_APLI_DATE);
                    String noticeDepartment = notice.getString(Constants.DIR_COL_NEWS_DEP);
                    String noticeTitle = notice.getString(Constants.DIR_COL_NEWS_SUB);
                    String noticeContent = notice.getString(Constants.DIR_COL_NEWS_NOTICE);
                    date.setText(getFormattedDate(noticeDate));
                    department.setText(noticeDepartment);
                    toolBarLayout.setTitle(noticeTitle);
                    content.setText(noticeContent);
                    try {
                        final ParseFile parseFile = notice.getParseFile(Constants.DIR_COL_NEWS_ATTACHMENT);
                        if (parseFile != null) {
                            showAttachment(parseFile);
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                } else {
                    dialog.dismiss();
                    e.printStackTrace();
                    finish();
                }
            }
        });
        //toolbar.setTitle("Notice Details");

    }

    private void downloadAttachment(ParseFile file) {
        String name = file.getName();
        String url = file.getUrl();

    }

    private void showAttachment(final ParseFile parseFile) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.show();
        parseFile.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    imageNotice.setVisibility(View.VISIBLE);
                    imageNotice.setImageBitmap(bitmap);
                    imageNotice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            downloadAttachment(parseFile);
                        }
                    });
                    dialog.dismiss();
                } else {
                    e.printStackTrace();
                    dialog.dismiss();
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer percentDone) {
                dialog.setMessage("" + percentDone);

            }
        });
    }

    private String getFormattedDate(Date choosenDate) {

        DateFormat dateFormat = new SimpleDateFormat();
        return dateFormat.format(choosenDate);
    }

    private void enableEditing(boolean b) {
        date.setEnabled(b);
        department.setEnabled(b);
        content.setEnabled(b);

    }
}