package com.internship.project.interne3;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }
    public static String getTimeDifference(Long date)
    {
        if(date==0) return "not updated...";
        Date dt1=new Date(date);
        Date dt2 = new Date();
        long diff = dt2.getTime() - dt1.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);

        if (diffHours/24 > 1)
        {
            return ((int)(diffHours/24))+" days ago";
        }
        else if (diffHours > 1)
        {
            return diffHours+" hours ago";
        }
        else if ((diffHours == 1))
        {
            return diffHours+" hrs "+diffMinutes+" mins ago";
        }
        else if(diffMinutes>1) {
            return diffMinutes + " mins ago";
        }
        else {
            return diffSeconds + " secs ago";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myFragment=inflater.inflate(R.layout.fragment_home, container, false);

        TextView display_last_updated=(TextView)myFragment.findViewById(R.id.last_updated);
        TextView display_location=(TextView)myFragment.findViewById(R.id.location);
        TextView username = (TextView)myFragment.findViewById(R.id.username);
        EditText status=(EditText)myFragment.findViewById(R.id.status);
        SharedPreferences last_location=this.getActivity().getSharedPreferences("last_location",0);
        String difference_in_time=getTimeDifference(last_location.getLong("last_updated",0));
        display_last_updated.setText(difference_in_time);
        display_location.setText(last_location.getString("last_location_name","Not Updated"));
        status.setText(last_location.getString("my_status","no status..."));
        if(last_location.getLong("last_updated",0)==0)
        {
            ImageButton maps=(ImageButton)myFragment.findViewById(R.id.goto_maps);
            maps.setVisibility(View.INVISIBLE);
        }
        return myFragment;
    }

}
