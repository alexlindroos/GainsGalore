package com.example.iosdev.sensorproject;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    ProgressBar mprogressBar;
    Button btnDiscounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flop = false;
        isOn = false;
        currentSteps = 0;
        startingSteps = 0;

        //mprogressBar.setMax(100);

        tv = (TextView) findViewById(R.id.tv);
        resetSteps = (Button) findViewById(R.id.ResetSteps);
        btnDiscounts = (Button) findViewById(R.id.btnDiscounts);


        sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        stepCounter = sm.getSensorList(Sensor.TYPE_STEP_COUNTER).get(0);

        tv.setText(stepsTaken + 0);
        setupClickListeners();


        mprogressBar = (ProgressBar) findViewById(R.id.circular_progress_bar);
        ObjectAnimator anim = ObjectAnimator.ofInt(mprogressBar, "progress", 0, 100);
        anim.setDuration(15000);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.start();
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
            float myEventValues = event.values[0];
            int myInteger = (int) myEventValues;
            mprogressBar.setProgress(myInteger);
            tv.setText(stepsTaken+ (currentSteps - startingSteps));
            flop = !flop;

            if (currentSteps >= mprogressBar.getMax()){
                Context context = getApplicationContext();
                CharSequence text = "Congratulations! Look your reward in discount section";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
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
                startingSteps = 0;
                currentSteps = 0;
                tv.setText(stepsTaken + 0);
                mprogressBar.setProgress(0);
            }
        });

        btnDiscounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Discounts.class);
                startActivity(intent);
            }
        });
    }
}

