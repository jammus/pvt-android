package com.jammus.pvt;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;

public class PerformTestActivity extends Activity {
	public boolean showStimulus = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GraphicsView(this));
	}
}
