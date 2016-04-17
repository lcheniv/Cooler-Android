package com.ivchen.refresh;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.firebase.client.Firebase;

public class ChooseActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Firebase.setAndroidContext(this);
        Firebase ref = new Firebase("https://coke-cooler.firebaseio.com/coolers");

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                new AlertDialog.Builder(ChooseActivity.this)
                        .setTitle("Delete entry")
                        .setMessage("Are we running low?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                startSelfie();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                startSelfie();
                            }
                        })
                        .show();
            }
        });

    }

    private void startSelfie() {
        if (hasAllPermissions()) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 50);
        }else{
            Intent intent = new Intent(this, SelfieActivity.class);
            startActivity(intent);
        }
    }

    private boolean hasAllPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        Intent intent = new Intent(this, SelfieActivity.class);
        startActivity(intent);
    }

    public void onClickGoToFaq(View view) {
        Intent intent = new Intent(ChooseActivity.this, FaqActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
