package com.internship.project.interne3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    boolean edit_status_enabled;
    EditText status;
    BottomNavigationView navigation;
    ImageButton edit_status;
    InputMethodManager myKeyboard;
    SharedPreferences last_location;
    SharedPreferences.Editor editor;
    TextView display_location,display_last_updated;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener()
    {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId())
            {
                case R.id.navigation_home:
                    if(getTitle()!="Home")
                    {
                        HomeFragment homeFragment = new HomeFragment();
                        FragmentTransaction myFragTrans1 = getSupportFragmentManager().beginTransaction();
                        if(getTitle()=="Friends")
                            myFragTrans1.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        else
                            myFragTrans1.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        setTitle("Home");
                        myFragTrans1.replace(R.id.page, homeFragment, "Navigate to friends");
                        myFragTrans1.commit();
                    }
                    return true;
                case R.id.navigation_friends:
                    if(getTitle()!="Friends")
                    {
//                        Toast.makeText(MainActivity.this, "Family!", Toast.LENGTH_SHORT).show();
                        FriendsFragment friendsFragment = new FriendsFragment();
                        FragmentTransaction myFragTrans = getSupportFragmentManager().beginTransaction();
                        myFragTrans.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                        setTitle("Friends");
                        myFragTrans.replace(R.id.page, friendsFragment, "");
                        myFragTrans.commit();
                    }
                    return true;
                case R.id.navigation_profile:
                    if(getTitle()!="Profile")
                    {
//                        Toast.makeText(MainActivity.this, "Family!", Toast.LENGTH_SHORT).show();
                        ProfileFragment profileFragment = new ProfileFragment();
                        FragmentTransaction myFragTrans = getSupportFragmentManager().beginTransaction();
                        myFragTrans.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                        setTitle("Profile");
                        myFragTrans.replace(R.id.page, profileFragment, "");
                        myFragTrans.commit();
                    }
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
        setTitle("Home");
        HomeFragment fragment1 = new HomeFragment();
        FragmentTransaction myFragTrans1= getSupportFragmentManager().beginTransaction();
        myFragTrans1.replace(R.id.page,fragment1,"");
        myFragTrans1.commit();
        edit_status_enabled=false;
        navigation = (BottomNavigationView)findViewById(R.id.navigation);
        myKeyboard = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);
        last_location=getSharedPreferences("last_location",0);

        editor=last_location.edit();
   }

    @Override
    protected void onResume() {
        super.onResume();
        TextView display_last_updated=(TextView)findViewById(R.id.last_updated);
        TextView display_location=(TextView)findViewById(R.id.location);
        TextView username = (TextView)findViewById(R.id.username);
        SharedPreferences last_location=getSharedPreferences("last_location",0);
        String difference_in_time=HomeFragment.getTimeDifference(last_location.getLong("last_updated",0));
        display_last_updated.setText(difference_in_time);
        display_location.setText(last_location.getString("last_location_name","Not Updated"));
    }

    public void ButtonClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.verify_vehicle:
            case R.id.verify_vehicle_2:
                startActivity(new Intent(MainActivity.this,VerifyVehicle.class));
//                Toast.makeText(MainActivity.this,"Get Vehicle Verification", Toast.LENGTH_SHORT).show();
                break;
            case R.id.send_location:
            case R.id.send_location_2:
//                Toast.makeText(MainActivity.this,"Send Location", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this,send_location.class));
                break;
            case R.id.share_location_2:
            case R.id.share_location:
                Toast.makeText(MainActivity.this,"Share Live Location", Toast.LENGTH_SHORT).show();
                break;
            case R.id.edit_status:

                status=(EditText)findViewById(R.id.status);
                edit_status=(ImageButton)findViewById(R.id.edit_status);
                if(edit_status_enabled)
                {
                    Toast.makeText(MainActivity.this, "Done...", Toast.LENGTH_SHORT).show();
                    edit_status.setBackgroundResource(android.R.drawable.ic_menu_edit);
                    status.clearFocus();
                    status.setFocusable(false);
                    status.setFocusableInTouchMode(false);
                    status.setActivated(false);
                    edit_status_enabled=false;
//                    hiding keyboard
                    myKeyboard.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                    status.setText(status.getText());
                    editor.putString("my_status",status.getText().toString()).commit();

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Set Status..", Toast.LENGTH_SHORT).show();
                    edit_status.setBackgroundResource(R.drawable.tick);
                    status.setFocusable(true);
                    status.setSelectAllOnFocus(true);
                    status.setFocusableInTouchMode(true);
                    edit_status_enabled=true;
                    status.requestFocus();
                    status.setSelectAllOnFocus(true);
                    myKeyboard.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
                }
                break;
            case R.id.goto_maps:
                String strUri = "http://maps.google.com/maps?q=loc:" + last_location.getString("last_latitude","0") + "," + last_location.getString("last_longitude","0") + " (" + "Label which you want" + ")";
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                startActivity(intent);
                break;
        }
    }
}
