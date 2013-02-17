package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.android.api.AndroidApiClient;
import com.jammus.pvt.android.data.UserDataStore;
import com.jammus.pvt.android.data.sharedpreferences.UserSharedPreferencesDataStore;
import com.jammus.pvt.api.ApiClient;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.User;
import com.jammus.pvt.interactors.LogInResult;
import com.jammus.pvt.interactors.LogIn;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainMenuActivity extends Activity {
	
	private User user;
	
	private UserDataStore userDataStore;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	userDataStore = new UserSharedPreferencesDataStore(this);
        user = userDataStore.fetchUser();
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
    	Intent intent = new Intent(this, PerformTestActivity.class);
    	intent.putExtra("user_id", user.id());
    	startActivity(intent);
    }
    
    public void showResults(View view) {
    	Intent intent = new Intent(this, ShowResultsActivity.class);
    	intent.putExtra("user_id", user.id());
    	startActivity(intent);
    }
    
    public void submitLogInForm(View view) {
    	boolean isFormValid = true;
    	
    	TextView loginMessageView = (TextView) findViewById(R.id.loginValidationMessage);
    	loginMessageView.setVisibility(TextView.INVISIBLE);
    	
    	EditText emailView = (EditText) findViewById(R.id.loginEmail);
    	String email = emailView.getText().toString().trim();
    	if (email.isEmpty() || ! android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
    		emailView.setError(getString(R.string.invalid_login_email_error));
    		isFormValid = false;
    	}
    	
    	EditText passwordView = (EditText) findViewById(R.id.loginPassword);
    	String password = passwordView.getText().toString().trim();
    	if (password.isEmpty()) {
    		passwordView.setError(getString(R.string.invalid_login_password_error));
    		isFormValid = false;
    	}
    	
    	if (isFormValid) {
	    	new LogInTask(this).execute(email, password);
    	}
    }
        	
	class LogInTask extends AsyncTask<String, Void, LogInResult> {
		
		private ProgressDialog progressDialog;
		
		public LogInTask(Activity context) {
			progressDialog = new ProgressDialog(context);
		}
		
		protected void onPreExecute() {
			progressDialog.setTitle("Please Wait");
			progressDialog.setMessage("Logging you in...");
			progressDialog.show();
		}
		
		@Override
		protected LogInResult doInBackground(String... params) {
	    	ApiClient apiClient = new AndroidApiClient();
	    	PvtApi pvtApi = new PvtApi(apiClient);
	    	LogIn logInUser = new LogIn(pvtApi);
	    	return logInUser.execute(params[0], params[1]);
		}
		
		protected void onPostExecute(LogInResult result) {
	    	progressDialog.dismiss();
	    	onLogInResult(result);
		}
		
	}

    private void onLogInResult(LogInResult result) {
		TextView loginMessageView = (TextView) findViewById(R.id.loginValidationMessage);
		
		if (result.hasError(LogInResult.INVALID_EMAIL_OR_PASSWORD)) {
			loginMessageView.setText(R.string.incorrect_login_details_message);
			loginMessageView.setVisibility(TextView.VISIBLE);
			return;
		} 
		
		if ( ! result.isOk()) {
			loginMessageView.setText(R.string.could_not_process_login_details_message);
			loginMessageView.setVisibility(TextView.VISIBLE);
			return;
		}
		
		user = result.user();
		
		saveUser(user);
		showMainMenu(user);  	
    }
    
    private void saveUser(User user) {
    	userDataStore.saveUser(user);
    }
    
    public void startSignUp(View view) {
    	Intent intent = new Intent(this, SignUpActivity.class);
    	startActivity(intent);
    }
    
}
