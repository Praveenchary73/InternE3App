package com.internship.project.interne3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class VerifyVehicle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_vehicle);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final EditText vehicleNo=(EditText)findViewById(R.id.vehicle_input);
        final Button verify = (Button)findViewById(R.id.verify);
        final Button take_ride = (Button)findViewById(R.id.take_ride);
        final Button cancel_ride = (Button)findViewById(R.id.cancel_ride);

        final View details=(View) findViewById(R.id.driver_details);
        final View ride_buttons = (View)findViewById(R.id.ride_buttons);
        ride_buttons.setVisibility(View.GONE);
        details.setVisibility(View.GONE);
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                details.setVisibility(View.GONE);
                ride_buttons.setVisibility(View.GONE);
                if(vehicleNo.getText().toString().equals(""))
                {
                    Toast.makeText(VerifyVehicle.this,"Enter a Vehicle No.",Toast.LENGTH_LONG).show();
                }
                else
                {
                    details.setVisibility(View.VISIBLE);
                    ride_buttons.setVisibility(View.VISIBLE);
                    ((TextView)findViewById(R.id.vehicle_no)).setText(vehicleNo.getText().toString());
                    vehicleNo.setText("");
                }
            }
        });
        cancel_ride.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            details.setVisibility(View.GONE);
            ride_buttons.setVisibility(View.GONE);
            }
        });
        take_ride.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            details.setVisibility(View.GONE);
            ride_buttons.setVisibility(View.GONE);
            Toast.makeText(VerifyVehicle.this, "Enjoy the Ride!", Toast.LENGTH_LONG).show();

        }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    }

}
