package com.example.iosdev.sensorproject;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import static com.example.iosdev.sensorproject.R.id.speedtxt;
import static com.example.iosdev.sensorproject.R.id.tv;
import static com.example.iosdev.sensorproject.StepFragment.ARG_PAGE_NUMBER;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpeedFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpeedFragment extends Fragment {

    public TextView speedtv;
    private LocationManager lm;
    private LocationListener ll;
    double mySpeed, maxSpeed;
    private final String Speed = null;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_speed, container, false);
        Log.i(Speed, "working1 ");
        super.onCreate(savedInstanceState);
        speedtv = (TextView) rootView.findViewById(R.id.speedtxt);

        maxSpeed = mySpeed = 0;
        Log.i(Speed, "working1 ");
        lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        ll = new SpeedoActionListener();
        try {
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, ll);
        } catch (SecurityException e) {
            System.out.println("you fucked up");
        }
        return rootView;
    }
    public SpeedFragment() {
        // Required empty public constructor
    }

    public static SpeedFragment newInstance(int page) {
        SpeedFragment fragment = new SpeedFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }

    private class SpeedoActionListener implements LocationListener
    {



        @Override
        public void onLocationChanged(Location location) {
            Log.i(Speed, "working2 ");
            if(location!=null) {
                if(location.hasSpeed()){
                    mySpeed = location.getSpeed();
                    speedtv.setText("Current speed: " + Double.toString(mySpeed) + " km/h, Max speed: " + Double.toString(maxSpeed) + " km/h");
                }
            }
            Log.i(Speed, "working3 ");
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle
                extras) {
            // TODO Auto-generated method stub

        }
    }
}