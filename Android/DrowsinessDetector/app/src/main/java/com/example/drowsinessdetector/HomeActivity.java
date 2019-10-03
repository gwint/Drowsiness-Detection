package com.example.drowsinessdetector;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private final int CAMERA_PERMISSION_REQUEST = 0;

    // Check if this device has a camera
    private boolean deviceHasCamera(Context context) {
        // no camera on this device
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA); // this device has a camera
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d("HomeActivity", "onCreate(): Has camera: " + deviceHasCamera(this));
    }

    // Called when user decides to grant a permission or not
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case CAMERA_PERMISSION_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("HomeActivity", "Permission was granted. Yay!");
                } else {
                    Log.d("HomeActivity", "Permission was denied. Boohoo.");
                }
                return;
            }
        }
        // Check other permissions here
    }

    // Check if the app has permission to use the camera
    // The user can always disable permission anytime ; can't assume we always have permission
    private boolean getCameraPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            Log.d("HomeActivity", "Permission to use the camera was not granted.");

            // Ask user for permission to use the camera (pop-up message)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST);
            Log.d("HomeActivity", "CAMERA_PERMISSION_REQUEST: " + CAMERA_PERMISSION_REQUEST);

            // Check result of pop-up message
            return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_DENIED;
        }

        return true;
    }

    public void startCamera(View view) {
        Log.d("HomeActivity", "startCamera()");

        // check if this device has a camera
        if(!deviceHasCamera(this)) {
            Log.d("HomeActivity", "No camera on this device.");
            // return to HomeActivity
            finish();
        }

        if(!getCameraPermission()) {
            Log.d("HomeActivity", "Failed to get permission from user.");
            // return to HomeActivity
            finish();
        }

        // start CameraActivity
        Log.d("HomeActivity", "Ready to start CameraActivity.");
        Intent intent = new Intent(this, CameraActivity.class);
        startActivity(intent);
    }
}