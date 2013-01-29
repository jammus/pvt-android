package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreateUser extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
    }
    
    public void submitCreateAccountForm(View view) {
    	boolean isFormValid = true;
    	
    	TextView createFailureMessageView = (TextView) findViewById(R.id.createAccountValidationMessage);
    	createFailureMessageView.setVisibility(View.INVISIBLE);
    	
    	EditText nameView = (EditText) findViewById(R.id.userName);
    	String name = nameView.getText().toString().trim();
    	if (name.isEmpty()) {
    		nameView.setError(getString(R.string.invalid_user_name_error));
    		isFormValid = false;
    	}
    	
    	EditText emailView = (EditText) findViewById(R.id.userEmail);
    	String email = emailView.getText().toString().trim();
    	if (email.isEmpty() || ! android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
    		emailView.setError(getString(R.string.invalid_user_email_error));
    		isFormValid = false;
    	}
    	
    	EditText passwordView = (EditText) findViewById(R.id.userPassword);
    	String password = passwordView.getText().toString();
    	EditText confirmPasswordView = (EditText) findViewById(R.id.userConfirmPassword);
    	String confirmPassword = confirmPasswordView.getText().toString();
    	if (password.length() < 8 || password != confirmPassword) {
    		passwordView.setError(getString(R.string.invalid_user_password_error));
    		isFormValid = false;
    	}
    	
    	if (! isFormValid) {
    		return;
    	}
    }
    
}
