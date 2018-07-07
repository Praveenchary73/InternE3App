package com.internship.project.interne3;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
{
    boolean edit_status_enabled;
    EditText status;
    BottomNavigationView navigation;
    ImageButton edit_status;
    InputMethodManager myKeyboard;

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
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        myKeyboard = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.getMenu().getItem(1).setChecked(true);
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
                Toast.makeText(MainActivity.this,"Send Location", Toast.LENGTH_SHORT).show();
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
        }
    }
}
