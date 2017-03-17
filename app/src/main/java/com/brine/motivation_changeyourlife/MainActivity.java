package com.brine.motivation_changeyourlife;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.brine.motivation_changeyourlife.flagment.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if (getFragmentManager().findFragmentById(R.id.containerView) == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerView, new HomeFragment(), "homefragment")
                    .commit();
        }
    }
}
