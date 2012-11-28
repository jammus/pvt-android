package com.jammus.pvt.activities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.jammus.pvt.R;
import com.jammus.pvt.PvtResult;
import com.jammus.pvt.data.PvtResultsDataStore;
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
		private static final String POST_URL = "http://ec2-176-34-215-74.eu-west-1.compute.amazonaws.com/lalalalalala/";
		private HttpResponse response;

		@Override
		protected String doInBackground(PvtResult... params) {
			PvtResult result = params[0];
			HttpPost httpPost = new HttpPost(POST_URL);
			HttpClient httpClient = new DefaultHttpClient();
			List<NameValuePair> postParams = new ArrayList<NameValuePair>(4);
			postParams.add(new BasicNameValuePair("user_id", String.valueOf(userId)));
			postParams.add(new BasicNameValuePair("date", String.valueOf(result.date())));
			postParams.add(new BasicNameValuePair("errorCount", String.valueOf(result.errorCount())));
			postParams.add(new BasicNameValuePair("averageRt", String.valueOf(result.averageRt())));
			postParams.add(new BasicNameValuePair("responseTimes", responseTimesToString(result.responseTimes())));
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(postParams));
				response = httpClient.execute(httpPost);
			} catch (UnsupportedEncodingException e) {
				return e.getMessage();
			} catch (ClientProtocolException e) {
				return e.getMessage();
			} catch (IOException e) {
				return e.getMessage();
			}
			
			HttpEntity httpEntity = response.getEntity();
			if (httpEntity != null) {
				InputStream inputStream;
				try {
					inputStream = httpEntity.getContent();
				} catch (IllegalStateException e) {
					return e.getMessage();
				} catch (IOException e) {
					return e.getMessage();
				}
				try {
					BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
					StringBuilder sb = new StringBuilder();
					String s;
					while (true) {
						s = buffer.readLine();
						if (s == null || s.length() == 0) {
							break;
						}
						sb.append(s);
					}
					buffer.close();
					inputStream.close();
					return sb.toString();
				} catch (UnsupportedEncodingException e) {
					return e.getMessage();
				} catch (IOException e) {
					return e.getMessage();
				}
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {
			TextView reportView = (TextView) findViewById(R.id.report);
			reportView.setText(result);
		}
		
		private String responseTimesToString(float[] times) {
			StringBuilder sb = new StringBuilder();
			for (int index = 0; index < times.length; index++) {
				if (index > 0) {
					sb.append(",");
				}
				sb.append(String.valueOf(times[index]));
			}
			return sb.toString();
		}
	}
}
