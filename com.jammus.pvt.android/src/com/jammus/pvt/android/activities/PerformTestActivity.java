package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.android.api.AndroidApiClient;
import com.jammus.pvt.android.data.PvtResultsDataStore;
import com.jammus.pvt.android.data.PvtResultsSubmission;
import com.jammus.pvt.android.data.sqlite.PvtResultsSQLiteDataStore;
import com.jammus.pvt.android.views.PvtView;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.PvtResult;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class PerformTestActivity extends Activity {
	private static final long TEST_DURATION_NS = 10 * 60 * 1000 * 1000000l; // m * s * ms * ns
	private PvtResult result;
	private PvtResultsDataStore localResultsDataStore;
	private String accessToken;
	private int userId;
	private long startTime;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		accessToken = getIntent().getStringExtra("access_token");
		userId = getIntent().getIntExtra("userId", -1);
		startTime = System.nanoTime();
		result = new PvtResult();
		localResultsDataStore = new PvtResultsSQLiteDataStore(this);
		setContentView(new PvtView(this));
	}

	public void registerError() {
		result.addError();
	}

	public void registerScore(float score) {
		result.addResponseTime(score);
		
		if (isTestComplete()) {
			finishTest();
		}
	}
	
	public void finishTest() {
		saveResults();
		showResults();
	}
	
	public boolean isTestComplete() {
		return (System.nanoTime() - startTime) > TEST_DURATION_NS;
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
		
		// loadReport();
	}
	
	private void loadReport() {
		new FetchReportTask().execute(result);
	}
	
	private class FetchReportTask extends AsyncTask<PvtResult, Void, String> {

		@Override
		protected String doInBackground(PvtResult... params) {
			PvtApi pvtApi = new PvtApi(new AndroidApiClient());
			PvtResultsSubmission submission = new PvtResultsSubmission(pvtApi);
			return submission.submit(accessToken, params[0]);
		}
		
		@Override
		protected void onPostExecute(String result) {
			TextView reportView = (TextView) findViewById(R.id.report);
			reportView.setText(result);
		}
	
	}
	
}
