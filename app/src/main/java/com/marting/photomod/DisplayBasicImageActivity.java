package com.marting.photomod;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class DisplayBasicImageActivity extends AppCompatActivity {

    private static final String TAG = "DisplayBasicImageActivity";
    private static final int LONGDURATION = Toast.LENGTH_LONG;
    private Bitmap bitmapToDisplayFromMainPhotoActivity = null;
    private ImageView basicImageDisplay;
    private Uri imageUriFromMainPhotoActivity = null;
    private SeekBar sliderBarVar;
    private TextView textSliderValue;
    public Toast messageForUser;


    //declare a stream to read the image data
    InputStream inputStreamOfGalleryImageSecondActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_basic_image);

        Context context = getApplicationContext();
        messageForUser = Toast.makeText(context, "Welcome to the Adding App!!", LONGDURATION);
        messageForUser.show();

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

    /* Called when the user hits the Add button */
    public void addTheNumbersUp(View view) {

        String messageForUser1;
        String messageForUser2;

        int number1 =0;
        int number2 =0;

        //this is for the toast message at the end
        Context context = getApplicationContext();

        // get value of/link editText from UI and put it in a local variable
        EditText editVarNum1 = findViewById(R.id.editTextNum1);
        EditText editVarNum2 = findViewById(R.id.editTextNum2);
        EditText editVarSum = findViewById(R.id.editTextSum);

        //this places the user input into an int var.
        String numberString1 = editVarNum1.getText().toString();
        String numberString2 = editVarNum2.getText().toString();

        //make sure number1 is an int
        try {
            number1 = Integer.parseInt(numberString1);
            Log.i("",number1 + " is a number");
            //display message to user
            messageForUser1 = "Success.  Num 1 is an integer.";

        } catch (NumberFormatException e) {
            Log.i("",numberString1 + " is not a number");
            //display message to user
            messageForUser1 = "Fail.  Num 1 is NOT an integer.  Please enter an integer.";

        }

        //make sure number2 is an int
        try {
            number2 = Integer.parseInt(numberString2);
            Log.i("",number1 + " is a number");
            //display message to user
            messageForUser2 = "Success.  Num 2 is an integer.";

        } catch (NumberFormatException e) {
            Log.i("",numberString2 + " is not a number");
            //display message to user
            messageForUser2 = "Fail.  Num 2 is NOT an integer.  Please enter an integer.";
        }

        // gives user feedback on the values if they need to reenter or if they were indeed integers
        messageForUser = Toast.makeText(context, messageForUser1 + "\n" + messageForUser2, LONGDURATION);
        messageForUser.show();

        //add the numbers
        int sum = number1 + number2;

        Log.i(TAG, "the value of of the variable is " + sum);
        //output the sum to the screen into the XML textbox object
        //you can't just put the int sum into the field....because then setText to a resource ID (which are Integer
        //indexed).  Like from a constant defined elsewhere
        editVarSum.setText(String.valueOf(sum));

        /**
         * int number1 =0;
         * int number2 =0;
         *
         * // get value of/link editText from UI and put it in a local variable
         * EditText editVarNum1 = findViewById(R.id.editTextNum1);
         * EditText editVarNum2 = findViewById(R.id.editTextNum2);
         * EditText editVarSum = findViewById(R.id.editTextSum);
         *
         * //converts the text that the user typed in into integers.  Assumes the user did enter integers.
         * //can use try {} catch {} to parse the vars so the app doesn't crash if the user puts in a letter, float, etc.
         * number1 = Integer.parseInt(editVarNum1.getText().toString());
         * number2 = Integer.parseInt(editVarNum2.getText().toString());
         *
         * //add the numbers
         * int sum = number1 + number2;
         *
         * //output the sum to the screen into the XML textbox object
         * //you can't just put the int sum into the field....because then setText to a resource ID (which are Integer
         * //indexed).  Like from a constant defined elsewhere
         * editVarSum.setText(String.valueOf(sum));
         */

    }
}
