package com.example.iosdev.sensorproject;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.R.id.text1;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sm;
    private Sensor stepCounter;
    TextView tv;
    private int startingSteps;
    private int currentSteps;
    Button resetSteps;
    boolean flop, isOn;
    private static final String stepsTaken = "Steps taken: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flop = false;
        isOn = false;
        currentSteps = 0;
        startingSteps = 0;

        tv = (TextView) findViewById(R.id.tv);
        resetSteps = (Button) findViewById(R.id.ResetSteps);


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounter = sm.getSensorList(Sensor.TYPE_STEP_COUNTER).get(0);

        tv.setText(stepsTaken + 0);
        setupClickListeners();

    }
        @Override
        protected void onResume() {
            super.onResume();
            sm.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_FASTEST);
        }

        @Override
        protected void onPause() {
            super.onPause();
            sm.unregisterListener(this);
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            this.currentSteps = (int) event.values[0];
            tv.setText(stepsTaken+ (currentSteps - startingSteps));
            flop = !flop;
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {
            tv.setText("Accuracy is now " +
                    (i == sm.SENSOR_STATUS_ACCURACY_HIGH ? "HIGH" :
                            (i == sm.SENSOR_STATUS_ACCURACY_MEDIUM ? "MEDIUM" :
                                    (i == sm.SENSOR_STATUS_ACCURACY_LOW ? "LOW" : "UNRELIABLE"))));
        }

    private void setupClickListeners(){
        resetSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startingSteps = currentSteps;
                tv.setText(stepsTaken + 0);
            }
        });
    }

    private void removePauseSteps(){

    }
}

