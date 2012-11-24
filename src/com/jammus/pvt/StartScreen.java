package com.jammus.pvt;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class StartScreen extends Activity {

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
    	Button button = (Button) findViewById(R.id.startButton);
    	button.setVisibility(View.GONE);
    	Intent intent = new Intent(this, PerformTestActivity.class);
    	startActivity(intent);
    }
}
