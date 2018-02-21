package com.marting.photomod;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.*;

// Launch the camera app and then retrieve the image and display it.

public class MainPhotoActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageToManipulate;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentPhotoPath;
    private static final String TAG = "MainPhotoActivity";
    int shortDuration = Toast.LENGTH_SHORT;
    int longDuration = Toast.LENGTH_LONG;
    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_photo);
        verifyStoragePermissions(this);

        //links XML imagePhoto to image variable
        this.imageToManipulate = this.findViewById(R.id.imagePhoto);

        //links XML button_camera to button variable
        Button photoButton = this.findViewById(R.id.button_camera);

        //watches for click on button_camera aka 'Take New Picture' button
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
                Log.i(TAG, "createImageFile return value--" + photoFile);

            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i(TAG, "IOException");
            }

            // Continue only if the File was successfully created
            if (photoFile != null) {

                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);

                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);  // or use this CAMERA_REQUEST

                Log.i(TAG, "photoFile is NOT null");
            } else if (photoFile == null) {
                Log.i(TAG, "photoFile is null");

                //debugging to see if we hit this leg of code
                //Context context = getApplicationContext();
                //Toast showError = Toast.makeText(context, "photoFile is null", shortDuration);
                //showError.show();
            }
        }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Context context = getApplicationContext();
        Toast showErr = Toast.makeText(context, requestCode + " " + resultCode, shortDuration);
        showErr.show();

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Log.i(TAG, "got into onActivityResult and next to BitmapFactory");

            //set local variable to return of getBitmap
            imageToManipulate.setImageBitmap(BitmapFactory.decodeFile(currentPhotoPath));
        }
    }

    /**
     * Checks if the app has permission to write to device storag
     * If the app does not has permission then the user will be prompted to grant permissions
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user to allow or deny it
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = getExternalStoragePublicDirectory(DIRECTORY_PICTURES);

        // initialize image so it will compile and createImageFile will return something
        File image = null;

        try {
            image = File.createTempFile(
                    imageFileName,  // prefix
                    ".jpg",  // suffix
                    storageDir      // directory
            );

            // Save a file: path for use with ACTION_VIEW intents
            //currentPhotoPath = "file:" + image.getAbsolutePath();
            currentPhotoPath = image.getAbsolutePath();

            //debugging to see if we hit this leg of code
            Toast showImagePath = Toast.makeText(getApplicationContext(), currentPhotoPath, shortDuration);
            showImagePath.show();

            return image;

        } catch (IOException e){
            e.printStackTrace();
        }
    return image;
    }


  // Called when the user hits the accept button
    public void acceptPicture (View view) {
        Intent intent = new Intent(this, DisplayBasicImageActivity.class);

        // get value of editText from UI and put it in a local variable
        /* EditText editText = findViewById(R.id.editText);
        String message = editText.getText().toString();*/

        // stuff intent with the message, flag position integer, and light switch status
        //intent.putExtra(EXTRA_MESSAGE, message);

        /* Do something in response to the button*/
        startActivity(intent);

    }
}
