package com.jammus.pvt;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PerformTestActivity extends Activity {
	public boolean showStimulus = false;
	
	private final int MAX_TESTS = 3;
	private int errorCount = 0;
	private float[] scores;
	private int testCount = 0;
	private float mean = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		scores = new float[9];
		setContentView(new GraphicsView(this));
	}

	public void registerError() {
		errorCount++;
	}

	public void registerScore(float score) {
		scores[testCount++] = score;
		if (isTestComplete()) {
			calculateResults();
			showResults();
		}
	}
	
	public boolean isTestComplete() {
		return testCount >= MAX_TESTS;
	}
	
	private void calculateResults() {
		float total = 0;
		for (int i = 0; i < testCount; i++) {
			total += scores[i];
		}
		mean = total / (float) testCount;
	}
	
	private void showResults() {
        setContentView(R.layout.activity_results_screen);
        
		TextView resultsText = (TextView) findViewById(R.id.results);
		resultsText.setText(String.valueOf(mean) + "ms");
		
		TextView errorsText = (TextView) findViewById(R.id.errors);
		errorsText.setText(String.valueOf(errorCount));
	}
}
