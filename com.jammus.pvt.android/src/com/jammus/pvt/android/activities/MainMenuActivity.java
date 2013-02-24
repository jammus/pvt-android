package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.android.data.UserDataStore;
import com.jammus.pvt.android.data.sharedpreferences.UserSharedPreferencesDataStore;
import com.jammus.pvt.core.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
	
	private User user;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    	UserDataStore userDataStore = new UserSharedPreferencesDataStore(this);
        user = userDataStore.fetchUser();
        
        setContentView(R.layout.activity_start_screen);
        TextView usernameView = (TextView) findViewById(R.id.username);
        usernameView.setText("Signed in as: " + user.email());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_testing_screen, menu);
        return true;
    }
    
    public void startTest(View view) {
    	Intent intent = new Intent(this, PerformTestActivity.class);
    	intent.putExtra("user_id", user.id());
    	intent.putExtra("access_token", user.token());
    	startActivity(intent);
    }
    
    public void showResults(View view) {
    	Intent intent = new Intent(this, ShowResultsActivity.class);
    	intent.putExtra("user_id", user.id());
    	startActivity(intent);
    }
    
}
