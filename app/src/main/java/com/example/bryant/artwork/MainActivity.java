package com.example.bryant.artwork;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.wikitude.architect.ArchitectView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final int CAM = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for device support
        deviceSupport();
        // Clearing ArchitectView cache
        clearCache(ArchitectView.getCacheDirectoryAbsoluteFilePath(this));

        ImageView building = (ImageView) findViewById(R.id.building);
        Animation slideUpAnimation;
        slideUpAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slideup);
        building.startAnimation(slideUpAnimation);

        ImageView logo = (ImageView) findViewById(R.id.logo);
        Animation slideDownAnimation;
        slideDownAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slidedown);
        logo.startAnimation(slideDownAnimation);

        ImageView tagline = (ImageView) findViewById(R.id.tagline);
        Animation slideLeftAnimation;
        slideLeftAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slideleft);
        tagline.startAnimation(slideLeftAnimation);

        ImageView location = (ImageView) findViewById(R.id.location);
        Animation slideRightAnimation;
        slideRightAnimation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.slideright);
        location.startAnimation(slideRightAnimation);

        Button login = (Button) findViewById(R.id.start);
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTour();
            }
        });


        // Stops activity from timing out. Here for debugging purposes, remove upon release.
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    // Check if device can support Wikitude(AR) API
    private void deviceSupport() {
        if (ArchitectView.isDeviceSupported(this)) {
            Toast.makeText(getApplicationContext(), "Application supported by Wikitude.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    // To clear architectView cache files every time app is run.
    private void clearCache(final String path) {
        try {
            final File dir = new File(path);
            if (dir.exists() && dir.isDirectory()) {
                final String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    new File(dir, children[i]).delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startTour() {
        boolean cam = (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED);

        if (cam) {
            camPermission();
        } else {
            Intent intent = new Intent(MainActivity.this, ARtour.class);
            MainActivity.this.startActivity(intent);
        }

    }

    private void camPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Camera needs to be enabled for AR experience.").setTitle("App Unable to Start")
                    .setPositiveButton(R.string.AlertDialog_OK, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    CAM);
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    this.CAM);
        }
    }

}
