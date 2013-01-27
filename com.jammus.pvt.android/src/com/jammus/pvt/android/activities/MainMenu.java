package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.core.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
	        setContentView(R.layout.activity_start_screen);
	        TextView usernameView = (TextView) findViewById(R.id.username);
	        usernameView.setText("Signed in as: " + user.email());
        }
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
        int userId = settings.getInt("user_id", -1);
        String email = settings.getString("email", "");
        String token = settings.getString("token", "");
        if (userId <= 0 || token == "") {
        	return null;
        	/*userId = new Random().nextInt(3000);
        	username = "guest" + String.valueOf(userId);
        	Editor settingsEditor = settings.edit();
        	settingsEditor.putInt("user_id", userId);
        	settingsEditor.putString("username", username);
        	settingsEditor.commit();*/
        }
        return new User(userId, email, token);
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
    }
    
    public void startCreateUser(View view) {
        setContentView(R.layout.activity_create_user);
    }
}
