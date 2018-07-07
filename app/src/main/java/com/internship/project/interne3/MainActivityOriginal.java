package com.internship.project.interne3;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivityOriginal extends AppCompatActivity
{
    boolean edit_status_enabled;
    EditText status;
    BottomNavigationView navigation;
    ImageButton edit_status;
    InputMethodManager myKeyboard;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    Toast.makeText(MainActivityOriginal.this, "Welcome To Home!", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_friends:
                    Toast.makeText(MainActivityOriginal.this, "Family!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivityOriginal.this,FriendsActivity.class));
                    overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                    finish();
                    return true;
                case R.id.navigation_profile:
                    Toast.makeText(MainActivityOriginal.this, "Check profile!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivityOriginal.this,ProfileActivity.class));
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                    finish();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_status_enabled=false;
        status=(EditText)findViewById(R.id.status);
        status.setFocusable(false);
        edit_status=(ImageButton)findViewById(R.id.edit_status);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        myKeyboard = (InputMethodManager) getSystemService(MainActivityOriginal.INPUT_METHOD_SERVICE);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);
   }


    public void ButtonClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.verify_vehicle:
            case R.id.verify_vehicle_2:
                startActivity(new Intent(MainActivityOriginal.this,VerifyVehicle.class));
//                Toast.makeText(MainActivity.this,"Get Vehicle Verification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_location:
            case R.id.send_location_2:
                Toast.makeText(MainActivityOriginal.this,"Send Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_location_2:
            case R.id.share_location:
                Toast.makeText(MainActivityOriginal.this,"Share Live Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit_status:
                if(edit_status_enabled)
                {
                    Toast.makeText(MainActivityOriginal.this, "Done...", Toast.LENGTH_SHORT).show();
                    edit_status.setBackgroundResource(android.R.drawable.ic_menu_edit);
                    status.clearFocus();
                    status.setFocusable(false);
                    status.setFocusableInTouchMode(false);
                    status.setActivated(false);
                    edit_status_enabled=false;
//                    hiding keyboard
                    myKeyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                }
                else
                {
                    Toast.makeText(MainActivityOriginal.this, "Set Status..", Toast.LENGTH_SHORT).show();
                    edit_status.setBackgroundResource(R.drawable.tick);
                    status.setFocusable(true);
                    status.setSelectAllOnFocus(true);
                    status.setFocusableInTouchMode(true);
                    edit_status_enabled=true;
                    status.requestFocus();
                    status.setSelectAllOnFocus(true);
                    myKeyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                }
        }
    }
}
