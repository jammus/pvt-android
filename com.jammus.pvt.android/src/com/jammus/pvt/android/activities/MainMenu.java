package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.android.api.AndroidApiClient;
import com.jammus.pvt.api.ApiClient;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.User;
import com.jammus.pvt.interactors.LogInResult;
import com.jammus.pvt.interactors.LogInUser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainMenu extends Activity {
	
	private static final String USER_PREFS = "UserPrefs";
	
	private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = fetchUser();
        if (user == null) {
	        setContentView(R.layout.activity_login);
        } else {
        	showMainMenu(user);
        }
    }
    
    private void showMainMenu(User user) {
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
    	Intent intent = new Intent(this, PerformTest.class);
    	intent.putExtra("user_id", user.id());
    	startActivity(intent);
    }
    
    public void showResults(View view) {
    	Intent intent = new Intent(this, ShowResults.class);
    	intent.putExtra("user_id", user.id());
    	startActivity(intent);
    }
    
    private User fetchUser() {
        SharedPreferences settings = getSharedPreferences(USER_PREFS, 0);
        String email = settings.getString("email", "");
        String token = settings.getString("token", "");
        if (email == ""|| token == "") {
        	return null;
        }
        return new User(-1, email, token);
    }
    
    public void login(View view) {
    	boolean hasErrors = false;
    	
    	TextView loginMessageView = (TextView) findViewById(R.id.loginValidationMessage);
    	loginMessageView.setVisibility(TextView.INVISIBLE);
    	
    	EditText emailView = (EditText) findViewById(R.id.loginEmail);
    	String email = emailView.getText().toString().trim();
    	
    	EditText passwordView = (EditText) findViewById(R.id.loginPassword);
    	String password = passwordView.getText().toString().trim();
    	
    	hasErrors |= email.isEmpty();
    	hasErrors |= password.isEmpty();
    	
    	if (hasErrors) {
    		loginMessageView.setText(R.string.invalid_login_details_message);
    		loginMessageView.setVisibility(TextView.VISIBLE);
    		return;
    	}
    	
    	new AsyncTask<String, Void, LogInResult>() {

			@Override
			protected LogInResult doInBackground(String... params) {
		    	ApiClient apiClient = new AndroidApiClient();
		    	PvtApi pvtApi = new PvtApi(apiClient);
		    	LogInUser logInUser = new LogInUser(pvtApi);
		    	return logInUser.execute(params[0], params[1]);
			}
			
			protected void onPostExecute(LogInResult result) {
		    	TextView loginMessageView = (TextView) findViewById(R.id.loginValidationMessage);
		    	if (result.hasError(LogInResult.INVALID_EMAIL_OR_PASSWORD)) {
		    		loginMessageView.setText(R.string.incorrect_login_details_message);
		    		loginMessageView.setVisibility(TextView.VISIBLE);
		    		return;
		    	} else if (!result.isOk()) {
		    		loginMessageView.setText(R.string.could_not_process_login_details_message);
		    		loginMessageView.setVisibility(TextView.VISIBLE);
		    		return;
		    	}
		    	
		        SharedPreferences settings = getSharedPreferences(USER_PREFS, 0);
		    	Editor settingsEditor = settings.edit();
		    	settingsEditor.putString("email", result.user().email());
		    	settingsEditor.putString("token", result.user().token());
		    	settingsEditor.commit();
		    	
		    	showMainMenu(user);
			}
			
    	}.execute(email, password);
    	
    }
    
    public void startCreateUser(View view) {
        setContentView(R.layout.activity_create_user);
    }
}
