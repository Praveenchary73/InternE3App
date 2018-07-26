package com.internship.project.interne3;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class send_location extends AppCompatActivity
{
    private static final int REQUEST_LOCATION=1;
    //creating the manager variable
    LocationManager myLocationManager;
    Button get_location;
    String longitude_str,lattitude_str;
    double longitude,latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_location);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width=metrics.widthPixels;
        int height=metrics.heightPixels;
        //getWindow().setLayout((int)(width*0.9),(int)(height*0.6));

        get_location = (Button)findViewById(R.id.get_location);
//        requesting permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
    }
    public void set_location(View v)
    {
//        initializing location manager
        myLocationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        if(myLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            if (ActivityCompat.checkSelfPermission(send_location.this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                    (send_location.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(send_location.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

            } else {
                Location location = myLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                Location location1 = myLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                Location location2 = myLocationManager.getLastKnownLocation(LocationManager. PASSIVE_PROVIDER);

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    lattitude_str = String.valueOf(latitude);
                    longitude_str = String.valueOf(longitude);
                    get_address(latitude,longitude);
//                  display.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude_str+ "\n" + "Longitude = " + longitude_str);

                } else  if (location1 != null) {
                    latitude = location1.getLatitude();
                    longitude = location1.getLongitude();
                    lattitude_str = String.valueOf(latitude);
                    longitude_str = String.valueOf(longitude);
                    get_address(latitude,longitude);
//                  display.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude_str + "\n" + "Longitude = " + longitude_str);


                } else  if (location2 != null) {
                    latitude = location2.getLatitude();
                    longitude = location2.getLongitude();
                    lattitude_str = String.valueOf(latitude);
                    longitude_str = String.valueOf(longitude);
                    get_address(latitude,longitude);
//                    display.setText("Your current location is"+ "\n" + "Lattitude = " + lattitude_str + "\n" + "Longitude = " + longitude_str);

                }
                else
                {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Please Turn ON your GPS Connection")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(final DialogInterface dialog, final int id) {
                                    dialog.cancel();
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
                    Toast.makeText(this,"Unble to Trace your location", Toast.LENGTH_SHORT).show();

                }

            }
        }
        else
        {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Please Turn ON your GPS Connection")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        }
    }
    public void get_address(double latitude,double longitude) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();
            //appending all to store data
            String address_data=address;
//            Toast.makeText(send_location.this,address_data, Toast.LENGTH_SHORT).show();
            SharedPreferences last_location = getSharedPreferences("last_location", 0);
            SharedPreferences.Editor editor = last_location.edit();
            editor.putString("last_location_address",address_data).commit();
            editor.putString("last_latitude",lattitude_str).commit();
            editor.putString("last_longitude",longitude_str).commit();
            editor.putString("last_country",country).commit();
            editor.putString("last_city",city).commit();
            editor.putString("last_state",state).commit();
            editor.putString("last_postal_code",postalCode).commit();
            editor.putString("last_location_name",knownName).commit();
            Date current = new Date();
            editor.putLong("last_updated",current.getTime()).commit();
//            Toast.makeText(send_location.this,current.toString(), Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
            e.printStackTrace();
            Log.w("Current loction address", "Cannot get Address!");
            Toast.makeText(send_location.this,"Cannot Locate..", Toast.LENGTH_SHORT).show();

        }


    }


}
