package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.android.api.AndroidApiClient;
import com.jammus.pvt.android.data.PvtResultsDataStore;
import com.jammus.pvt.android.data.UserDataStore;
import com.jammus.pvt.android.data.sharedpreferences.UserSharedPreferencesDataStore;
import com.jammus.pvt.android.data.sqlite.PvtResultsSQLiteDataStore;
import com.jammus.pvt.android.views.PvtView;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.PvtReport;
import com.jammus.pvt.core.PvtResult;
import com.jammus.pvt.core.User;
import com.jammus.pvt.interactors.SubmitPvt;
import com.jammus.pvt.interactors.SubmitPvtResult;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

public class PerformTestActivity extends Activity {
	
	private static final long TEST_DURATION_NS = (long)(0.5 * 60 * 1000) * 1000000l; // m * s * ms * ns
	private PvtResult result;
	private PvtResultsDataStore localResultsDataStore;
	private long startTime;
	private User user;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	UserDataStore userDataStore = new UserSharedPreferencesDataStore(this);
        user = userDataStore.fetchUser();
        
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
		localResultsDataStore.save(user.id(), result);
	}
	
	private void showResults() {
        setContentView(R.layout.activity_results_screen);
        
		TextView resultsText = (TextView) findViewById(R.id.results);
		resultsText.setText(String.valueOf(result.averageRt()) + "ms");
		
		TextView errorsText = (TextView) findViewById(R.id.errors);
		errorsText.setText(String.valueOf(result.errorCount()));
		
		loadReport();
	}
	
	private void loadReport() {
		new FetchReportTask().execute(result);
	}
	
	private class FetchReportTask extends AsyncTask<PvtResult, Void, SubmitPvtResult> {

		@Override
		protected SubmitPvtResult doInBackground(PvtResult... params) {
			PvtApi pvtApi = new PvtApi(new AndroidApiClient());
			SubmitPvt submitPvt = new SubmitPvt(pvtApi);
			return submitPvt.execute(user, params[0]);
		}
		
		@Override
		protected void onPostExecute(SubmitPvtResult result) {
			TextView reportView = (TextView) findViewById(R.id.report);
			if (result.isOk()) {
				String summary = getString(R.string.report_summary);
				PvtReport report = result.report();
				summary = summary.replace("{{averageRt}}", String.valueOf(report.averageRt()));
				summary = summary.replace("{{errors}}", String.valueOf(report.errors()));
				summary = summary.replace("{{lapses}}", String.valueOf(report.lapses()));
				reportView.setText(summary);
			} else {
				String error = getString(R.string.report_submission_error);
				reportView.setText(error);
			}
		}
	
	}
	
}