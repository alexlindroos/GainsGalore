package com.example.iosdev.sensorproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by iosdev on 27.9.2016.
 */

public class Statistics extends AppCompatActivity implements Runnable {

    TextView steps,distance,hrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        steps = (TextView) findViewById(R.id.statsSteps);
        distance = (TextView) findViewById(R.id.statsDistance);
        hrate = (TextView) findViewById(R.id.statsHrate);

    }

    @Override
    public void run() {

        MainActivity ma = new MainActivity();

        steps.setText(ma.currentSteps);
        distance.setText(ma.currentSteps+"meters");
    }
}