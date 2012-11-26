package com.jammus.pvt.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.PvtResult;
import com.jammus.pvt.data.PvtResultsDataStore;
import com.jammus.pvt.data.sqlite.PvtResultsSQLiteDataStore;
import com.jammus.pvt.views.Pvt;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class PerformTest extends Activity {
	public boolean showStimulus = false;
	
	private final int MAX_TESTS = 3;
	private int testCount = 0;
	private PvtResult result;
	
	private PvtResultsDataStore resultsDataStore;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		result = new PvtResult(MAX_TESTS);
		resultsDataStore = new PvtResultsSQLiteDataStore(this);
		setContentView(new Pvt(this));
	}

	public void registerError() {
		result.addError();
	}

	public void registerScore(float score) {
		testCount++;
		result.addResponseTime(score);
		if (isTestComplete()) {
			resultsDataStore.save(result);
			showResults();
		}
	}
	
	public boolean isTestComplete() {
		return testCount >= MAX_TESTS;
	}
	
	private void showResults() {
        setContentView(R.layout.activity_results_screen);
        
		TextView resultsText = (TextView) findViewById(R.id.results);
		resultsText.setText(String.valueOf(result.averageRt()) + "ms");
		
		TextView errorsText = (TextView) findViewById(R.id.errors);
		errorsText.setText(String.valueOf(result.errorCount()));
	}
}
