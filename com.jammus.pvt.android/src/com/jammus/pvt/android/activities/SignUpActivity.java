package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.android.api.AndroidApiClient;
import com.jammus.pvt.android.data.UserDataStore;
import com.jammus.pvt.android.data.sharedpreferences.UserSharedPreferencesDataStore;
import com.jammus.pvt.api.ApiClient;
import com.jammus.pvt.api.PvtApi;
import com.jammus.pvt.core.User;
import com.jammus.pvt.interactors.SignUp;
import com.jammus.pvt.interactors.SignUpResult;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends Activity {

	UserDataStore userDataStore;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDataStore = new UserSharedPreferencesDataStore(this);
        setContentView(R.layout.activity_signup);
    }
    
    public void submitSignUpForm(View view) {
    	boolean isFormValid = true;
    	
    	TextView createFailureMessageView = (TextView) findViewById(R.id.signUpResultMessage);
    	createFailureMessageView.setVisibility(View.INVISIBLE);
    	
    	EditText nameView = (EditText) findViewById(R.id.signUpName);
    	String name = nameView.getText().toString().trim();
    	if (name.isEmpty()) {
    		nameView.setError(getString(R.string.invalid_signup_name_error));
    		isFormValid = false;
    	}
    	
    	EditText emailView = (EditText) findViewById(R.id.signUpEmail);
    	String email = emailView.getText().toString().trim();
    	if (email.isEmpty() || ! android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
    		emailView.setError(getString(R.string.invalid_signup_email_error));
    		isFormValid = false;
    	}
    	
    	EditText passwordView = (EditText) findViewById(R.id.signUpPassword);
    	String password = passwordView.getText().toString();
    	EditText confirmPasswordView = (EditText) findViewById(R.id.signUpConfirmPassword);
    	String confirmPassword = confirmPasswordView.getText().toString();
    	if (password.length() < 8 || ! password.equals(confirmPassword)) {
    		passwordView.setError(getString(R.string.invalid_signup_password_error));
    		isFormValid = false;
    	}
    	
    	if (isFormValid) {
    		new SignUpTask(this).execute(name, email, password);
    	}
    }
    
    private void onSignUpResult(SignUpResult result) {
    	TextView createFailureMessageView = (TextView) findViewById(R.id.signUpResultMessage);
    	
    	if (result.hasError(SignUpResult.DUPLICATE_ACCOUNT)) {
    		createFailureMessageView.setText(R.string.existing_account_error);
	    	createFailureMessageView.setVisibility(View.VISIBLE);
	    	return;
    	}
    	
    	if ( ! result.isOk()) {
    		createFailureMessageView.setText(R.string.could_not_process_signup);
	    	createFailureMessageView.setVisibility(View.VISIBLE);
	    	return;
    	}
    	
    	User user = result.user();
		saveUser(user);
		showMainMenu();  	
    }
    
    private void saveUser(User user) {
    	userDataStore.saveUser(user);
    }
    
    private void showMainMenu() {
    	Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
    }
    
	class SignUpTask extends AsyncTask<String, Void, SignUpResult> {
		
		private ProgressDialog progressDialog;
		
		public SignUpTask(Activity context) {
			progressDialog = new ProgressDialog(context);
		}
		
		protected void onPreExecute() {
			progressDialog.setTitle("Please Wait");
			progressDialog.setMessage("Creating account...");
			progressDialog.show();
		}
		
		@Override
		protected SignUpResult doInBackground(String... params) {
	    	ApiClient apiClient = new AndroidApiClient();
	    	PvtApi pvtApi = new PvtApi(apiClient);
	    	SignUp signUp = new SignUp(pvtApi);
	    	return signUp.execute(params[0], params[1], params[2]);
		}
		
		protected void onPostExecute(SignUpResult result) {
	    	progressDialog.dismiss();
	    	onSignUpResult(result);
		}
		
	}
}
