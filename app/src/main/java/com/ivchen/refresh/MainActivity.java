package com.ivchen.refresh;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.client.Firebase;
import com.firebase.client.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(MainActivity.this);
        Firebase.getDefaultConfig().setLogLevel(Logger.Level.DEBUG);
        findLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickGoMain(View view) {
        Intent intent = new Intent(MainActivity.this, ChooseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void findLocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 50);
            return;
        }
        Location l = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(l != null){
          registerLocation(l);
        }else{
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 20, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    registerLocation(location);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.d("COOLER", "status chagned: " + status);
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.d("COOLER", "provider enabled: " + provider);
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.d("COOLER", "provider disabled: " + provider);
                }
            });
        }
    }

    private void registerLocation(Location location) {
        Firebase ref = new Firebase("https://coke-cooler.firebaseio.com/coolers/12345");
        Firebase loc = ref.child("location");
        loc.child("latitude").setValue(location.getLatitude());
        loc.child("longitude").setValue(location.getLongitude());

        Log.d("COOLERER", "Location changed: " + location.getLongitude() +  ": " + location.getLatitude());
    }

}
