package com.example.iosdev.sensorproject;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity {


    String _id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.testheartrate);

        // Get intent values
        _id = getIntent().getStringExtra(RewardListActivity.ID_EXTRA);
        System.out.println("ID " + _id);

        TabLayout tabs = (TabLayout) findViewById(R.id.sliding_tabs);
        ViewPager pager = (ViewPager) findViewById(R.id.viewpager);
        ActivityPagerAdapter adapter = new ActivityPagerAdapter(getSupportFragmentManager());

        pager.setAdapter(adapter);
        tabs.setupWithViewPager(pager);


    }

}
