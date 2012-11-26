package com.jammus.pvt.activities;

import java.util.Date;

import com.jammus.pvt.R;
import com.jammus.pvt.PvtResults;
import com.jammus.pvt.views.Pvt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PerformTest extends Activity {
	public boolean showStimulus = false;
	
	private final int MAX_TESTS = 3;
	private int testCount = 0;
	private PvtResults results;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		results = new PvtResults(MAX_TESTS);
		setContentView(new Pvt(this));
	}

	public void registerError() {
		results.addError();
	}

	public void registerScore(float score) {
		testCount++;
		results.addScore(score);
		if (isTestComplete()) {
			showResults();
		}
	}
	
	public boolean isTestComplete() {
		return testCount >= MAX_TESTS;
	}
	
	private void showResults() {
        setContentView(R.layout.activity_results_screen);
        
		TextView resultsText = (TextView) findViewById(R.id.results);
		resultsText.setText(String.valueOf(results.averageRt()) + "ms");
		
		TextView errorsText = (TextView) findViewById(R.id.errors);
		errorsText.setText(String.valueOf(results.errorCount()));
	}
}
