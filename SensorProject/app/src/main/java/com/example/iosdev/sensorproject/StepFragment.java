package com.example.iosdev.sensorproject;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;



/*
*
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StepFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StepFragment#newInstance} factory method to
 * create an instance of this fragment.
*/
public class StepFragment extends Fragment implements SensorEventListener, View.OnClickListener {
    private SensorManager sm;
    private Sensor stepCounter;
    int startingSteps;
    int currentSteps;
    boolean flop, isOn;
    String stepsTaken = "Steps: ";
    static String ARG_PAGE_NUMBER = "page_number";
    ProgressBar mprogressBar;
    public TextView txt;
    public Button btnDiscounts, btnStatistics, btnRewards;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step, container, false);
        flop = false;
        isOn = false;
        currentSteps = 0;
        startingSteps = 0;


        txt = (TextView) rootView.findViewById(R.id.tv);
        sm = (SensorManager) this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
        stepCounter = sm.getSensorList(Sensor.TYPE_STEP_COUNTER).get(0);
        btnDiscounts = (Button) rootView.findViewById(R.id.btnDiscounts);
        btnStatistics = (Button) rootView.findViewById(R.id.btnStatistics);
        btnRewards = (Button) rootView.findViewById(R.id.btnRewards);

        txt.setText(stepsTaken + 0);
        btnDiscounts.setOnClickListener(this);
        btnStatistics.setOnClickListener(this);
        btnRewards.setOnClickListener(this);


        mprogressBar = (ProgressBar) rootView.findViewById(R.id.circular_progress_bar);
        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
        anim.setDuration(150);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
        return rootView;
    }


    private OnFragmentInteractionListener mListener;

    public StepFragment() {
        // Required empty public constructor
    }

    public static StepFragment newInstance(int page) {
        StepFragment fragment = new StepFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE_NUMBER, page);
        fragment.setArguments(args);
        return fragment;
    }




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    @Override
    public void onResume() {
        super.onResume();
        sm.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        this.currentSteps = (int) event.values[0];
        float myEventValues = event.values[0];
        int myInteger = (int) myEventValues;
        mprogressBar.setProgress(myInteger);
        txt.setText(Integer.toString(currentSteps));
        flop = !flop;

        if (currentSteps >= mprogressBar.getMax()){
            Context context = getActivity().getApplicationContext();
            CharSequence text = "Congratulations! Look for your reward in the discount section";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            startingSteps = 0;
            currentSteps = 0;
            txt.setText(stepsTaken + 0);
            mprogressBar.setProgress(0);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
      if (id == R.id.btnDiscounts) {
            Intent discIntent = new Intent(StepFragment.this.getActivity(), Discounts.class);
            startActivity(discIntent);
        }else if (id == R.id.btnStatistics) {
            Intent statIntent = new Intent(StepFragment.this.getActivity(), Statistics.class);
            statIntent.putExtra("cSteps",currentSteps);
            startActivity(statIntent);
        } else if (id == R.id.btnRewards) {
            Intent rewardsIntent = new Intent(StepFragment.this.getActivity(), RewardListActivity.class);
            startActivity(rewardsIntent);
        }

    }

    public void updateProgressbar(){
        int goal = 0;
        mprogressBar.setMax(goal);
    }


   /* if (id == R.id.ResetSteps) {
        startingSteps = 0;
        currentSteps = 0;
        txt.setText(stepsTaken + 0);
        mprogressBar.setProgress(0);
    */

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
