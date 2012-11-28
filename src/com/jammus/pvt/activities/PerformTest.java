package com.jammus.pvt.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.PvtResult;
import com.jammus.pvt.data.PvtResultsDataStore;
import com.jammus.pvt.data.PvtResultsSubmission;
import com.jammus.pvt.data.sqlite.PvtResultsSQLiteDataStore;
import com.jammus.pvt.views.Pvt;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class PerformTest extends Activity {
	public boolean showStimulus = false;
	
	private final int MAX_TESTS = 2;
	private int testCount = 0;
	private PvtResult result;
	
	private PvtResultsDataStore localResultsDataStore;
	
	private int userId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		userId = getIntent().getIntExtra("user_id", -1);
		result = new PvtResult(MAX_TESTS);
		localResultsDataStore = new PvtResultsSQLiteDataStore(this);
		setContentView(new Pvt(this));
	}

	public void registerError() {
		result.addError();
	}

	public void registerScore(float score) {
		testCount++;
		result.addResponseTime(score);
		if (isTestComplete()) {
			saveResults();
			showResults();
		}
	}
	
	public boolean isTestComplete() {
		return testCount >= MAX_TESTS;
	}
	
	private void saveResults() {
		localResultsDataStore.save(userId, result);
	}
	
	private void showResults() {
        setContentView(R.layout.activity_results_screen);
        
		TextView resultsText = (TextView) findViewById(R.id.results);
		resultsText.setText(String.valueOf(result.averageRt()) + "ms");
		
		TextView errorsText = (TextView) findViewById(R.id.errors);
		errorsText.setText(String.valueOf(result.errorCount()));
		
		new FetchReportTask().execute(result);
	}
	
	private class FetchReportTask extends AsyncTask<PvtResult, Void, String> {

		@Override
		protected String doInBackground(PvtResult... params) {
			PvtResultsSubmission submission = new PvtResultsSubmission();
			return submission.submit(userId, params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			TextView reportView = (TextView) findViewById(R.id.report);
			reportView.setText(result);
		}
	
	}
}
