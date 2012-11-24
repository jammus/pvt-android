package com.jammus.pvt;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;

public class PerformTestActivity extends Activity {
	public boolean showStimulus = false;
	
	private int errorCount = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GraphicsView(this));
	}

	public void registerError() {
		errorCount++;
	}

	public void registerScore() {
		// TODO Auto-generated method stub
		
	}
}
