package com.ivchen.refresh;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class DrinkActivity extends AppCompatActivity {

    boolean runningLow = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        Firebase.setAndroidContext(DrinkActivity.this);

        int id = getIntent().getExtras().getInt("id");

        final Drink d = new Drink(ImageAdapter.thumbNames[id], ImageAdapter.mThumbIds[id]);
        int color = Color.parseColor(ImageAdapter.colors[ImageAdapter.random(0, ImageAdapter.colors.length - 1)]);

        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.75f; // value component
        int colorDarker = Color.HSVToColor(hsv);


        ((ImageView) findViewById(R.id.image)).setImageResource(d.getDrawableId());
        findViewById(R.id.image).setBackgroundColor(color);
        ((TextView) findViewById(R.id.name)).setText(d.getName());
        findViewById(R.id.name).setBackgroundColor(colorDarker);

        Window window = getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(color);
        }

        final Firebase ref = new Firebase("https://coke-cooler.firebaseio.com/coolers/12345/drinks");
        assert (findViewById(R.id.running_low)) != null;
        findViewById(R.id.running_low).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(findViewById(R.id.root), "Thanks for letting us know!", Snackbar.LENGTH_LONG).show();
                runningLow = !runningLow;
                ref.child(d.getName()).setValue(runningLow);
            }
        });

        findViewById(R.id.selfie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasAllPermissions()) {
                    ActivityCompat.requestPermissions(DrinkActivity.this, new String[]{Manifest.permission.CAMERA}, 50);
                }else{
                    Intent intent = new Intent(DrinkActivity.this, SelfieActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean hasAllPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Intent intent = new Intent(this, SelfieActivity.class);
        startActivity(intent);
    }
}
