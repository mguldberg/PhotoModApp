package com.marting.photomod;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DisplayBasicImageActivity extends AppCompatActivity {

    private static final String TAG = "DisplayBasicImageActivity";
    private Bitmap bitmapToDisplayFromMainPhotoActivity = null;
    private ImageView basicImageDisplay;
    private Uri imageUriFromMainPhotoActivity = null;
    private SeekBar sliderBarVar;
    private TextView textSliderValue;

        //declare a stream to read the image data
    InputStream inputStreamOfGalleryImageSecondActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_basic_image);

        //link seekBar aka sliderBar to local variable
        sliderBarVar = findViewById(R.id.sliderBar);

        //initialize sliderBar text
        textSliderValue = findViewById(R.id.textSliderValue);
        textSliderValue.setText("Ski-U-Mah!");
        Log.i(TAG, "in DisplayBasicImageActivity " + textSliderValue);

        //links XML imagePhoto to image variable
        basicImageDisplay = findViewById(R.id.imagePhotoOnSecondScreen);

        // get Uri from main activity
        imageUriFromMainPhotoActivity = getIntent().getData();
        Log.i(TAG, "in DisplayBasicImageActivity " + imageUriFromMainPhotoActivity);

        //set the image on the 2nd screen to the image passed in from the main photo activity
        //basicImageDisplay.setImageResource();  // link this to the ImageView for the XML for this screen
        basicImageDisplay.setImageBitmap(bitmapToDisplayFromMainPhotoActivity);

        try {
            inputStreamOfGalleryImageSecondActivity = getContentResolver().openInputStream(imageUriFromMainPhotoActivity);

            //set image to picture selected from gallery
            bitmapToDisplayFromMainPhotoActivity = BitmapFactory.decodeStream(inputStreamOfGalleryImageSecondActivity);
            basicImageDisplay.setImageBitmap(bitmapToDisplayFromMainPhotoActivity);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            // display message to user that we are unable to open the image from the gallery
            Toast.makeText(this, "Unable to open image from gallery", Toast.LENGTH_LONG).show();

        }

        // put a listener on the sliderBar
        sliderBarVar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                //display the value of the sliderBar in real time - passed in variable i could also be referred to as progress
                textSliderValue.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
