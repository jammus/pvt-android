package com.jammus.pvt.activities;

import com.jammus.pvt.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainMenu extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_testing_screen, menu);
        return true;
    }
    
    public void startTest(View view) {
    	Intent intent = new Intent(this, PerformTest.class);
    	startActivity(intent);
    }
}
