package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SignUp extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    	if (password.length() < 8 || password != confirmPassword) {
    		passwordView.setError(getString(R.string.invalid_signup_password_error));
    		isFormValid = false;
    	}
    	
    	if (! isFormValid) {
    		return;
    	}
    }
    
}
