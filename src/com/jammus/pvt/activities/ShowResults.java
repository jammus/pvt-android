package com.jammus.pvt.activities;

import java.util.List;

import com.jammus.pvt.PvtResult;
import com.jammus.pvt.R;
import com.jammus.pvt.data.PvtResultsDataStore;
import com.jammus.pvt.data.sqlite.PvtResultsSQLiteDataStore;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class ShowResults extends Activity {
	
	private PvtResultsDataStore resultsDataStore;
	private int userId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resultsDataStore = new PvtResultsSQLiteDataStore(this);
        setContentView(R.layout.activity_show_results);
        userId = getIntent().getIntExtra("user_id", -1);
        showResults();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_show_results, menu);
        return true;
    }
    
    private void showResults() {
    	List<PvtResult> results = resultsDataStore.fetchAllForUser(userId);
    	StringBuilder sb = new StringBuilder();
    	for (PvtResult result: results) {
	    	sb.append("Date: ").append(result.date());
	    	sb.append("\nAvg RT: ").append(result.averageRt());
	    	sb.append("\nErrors: ").append(result.errorCount());
	    	sb.append("\n\n");
    	}
    	TextView textView = (TextView) findViewById(R.id.results);
    	textView.setText(sb);
    }
}
