package com.mcsgoc.www.portal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.mcsgoc.www.portal.helper.Constants;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.ProgressCallback;
import com.parse.SaveCallback;
import java.io.File;
import java.nio.ByteBuffer;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name;
    EditText enrollNumber;
    EditText email;
    EditText password;
    EditText phoneNo;
    EditText branch;
    private int counter = 0;
    public static final int REQUEST_PHOTO = 2;
    private File photoFile;
    private Intent captureImage;
    ImageView profileImage;
    private ParseUser currentUser;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        toolBarLayout.setTitle(getTitle());
        currentUser = ParseUser.getCurrentUser();
        initializeCameraSetup();

        name = (EditText) findViewById(R.id.editName);
        enrollNumber = (EditText) findViewById(R.id.editEnrollNo);
        email = (EditText) findViewById(R.id.editEmail);
        password = (EditText) findViewById(R.id.editPassword);
        phoneNo = (EditText) findViewById(R.id.editPhoneNo);
        branch = (EditText) findViewById(R.id.editBranch);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        profileImage.setOnClickListener(this);
        updatePhotoView(profileImage);


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


        if (currentUser != null) {
            email.setText(currentUser.get("email").toString());
            name.setText(currentUser.get("name").toString());
            enrollNumber.setText(currentUser.get("username").toString());
            phoneNo.setText(String.valueOf(currentUser.getLong("contact_no")));
            branch.setText(currentUser.get("branch").toString());
            password.setText(String.valueOf(currentUser.getLong("roll_no")));

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

    public void initializeCameraSetup() {
        photoFile = getPhotoFile();

        captureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        boolean canTakePhoto = photoFile != null &&
                captureImage.resolveActivity(getPackageManager()) != null;

        if (canTakePhoto) {
            Uri uri = Uri.fromFile(photoFile);
            captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
    }

    public void openCamera() {
        startActivityForResult(captureImage, REQUEST_PHOTO);
    }

    public File getPhotoFile() {
        File externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if (externalFilesDir == null) {
            return null;
        }

        return new File(externalFilesDir, getPhotoFilename());
    }

    public String getPhotoFilename() {
        return "IMG_" + getId() + ".jpg";
    }

    private String getId() {
        return currentUser.getUsername();
    }

    private void updatePhotoView(ImageView img) {
        Bitmap imageFromDatabase = getImageFromDatabase();
        if(imageFromDatabase!=null){
            img.setImageBitmap(imageFromDatabase);
        }
        if (photoFile == null || !photoFile.exists()) {
            img.setImageDrawable(null);
        } else {
            Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), 512, 512);
            getSharedPreferences("profile_pref", MODE_PRIVATE).edit().putString("path", photoFile.getPath()).apply();
            img.setImageBitmap(bitmap);
            saveProfilePicture(bitmap);
        }
    }

    private void saveProfilePicture(Bitmap bitmap) {

        int bytes = byteSizeOf(bitmap);
        ByteBuffer buffer = ByteBuffer.allocate(bytes); //Create a new buffer
        bitmap.copyPixelsToBuffer(buffer); //Move the byte data to the buffer

        byte[] array = buffer.array(); //Get the underlying array containing the data.
        final ParseFile profilePicFile = new ParseFile(getPhotoFilename(), array);
        profilePicFile.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    currentUser.put(Constants.DIR_COLC_USER_PIC, profilePicFile);
                    currentUser.saveEventually(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                // profile updated
                            } else {
                                e.printStackTrace();
                            }
                        }
                    });
                } else {
                    e.printStackTrace();
                }
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer integer) {
                Log.i("TAG_IMG", "" + integer);
            }
        });
    }

    private int byteSizeOf(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR1) {
            return bitmap.getRowBytes() * bitmap.getHeight();
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return bitmap.getByteCount();
        } else {
            return bitmap.getAllocationByteCount();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PHOTO) {
            updatePhotoView(profileImage);
        }
    }

    @Override
    public void onClick(View view) {
        openCamera();
    }

    public Bitmap getImageFromDatabase() {
        final Bitmap[] bitmap = {null};
        ParseFile savedImage = (ParseFile) currentUser.get(Constants.DIR_COLC_USER_PIC);
        savedImage.getDataInBackground(new GetDataCallback() {
            public void done(byte[] data, ParseException e) {
                if (e == null) {
                    // data has the bytes for the resume
                    Toast.makeText(ProfileActivity.this, "ok", Toast.LENGTH_SHORT).show();
                    bitmap[0] = BitmapFactory.decodeByteArray(data, 0, data.length);
                } else {
                    // something went wrong
                    e.printStackTrace();
                }
            }
        });
        return bitmap[0];
    }

    public static class PictureUtils {
        public static Bitmap getScaledBitmap(String path, Activity activity) {
            Point size = new Point();
            activity.getWindowManager().getDefaultDisplay()
                    .getSize(size);

            return getScaledBitmap(path, size.x, size.y);
        }

        public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight) {
            // Read in the dimensions of the image on disk
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            float srcWidth = options.outWidth;
            float srcHeight = options.outHeight;

            // Figure out how much to scale down by
            int inSampleSize = 1;
            if (srcHeight > destHeight || srcWidth > destWidth) {
                if (srcWidth > srcHeight) {
                    inSampleSize = Math.round(srcHeight / destHeight);
                } else {
                    inSampleSize = Math.round(srcWidth / destWidth);
                }
            }

            options = new BitmapFactory.Options();
            options.inSampleSize = inSampleSize;

            // Read in and create final bitmap
            return BitmapFactory.decodeFile(path, options);
        }
    }

}
