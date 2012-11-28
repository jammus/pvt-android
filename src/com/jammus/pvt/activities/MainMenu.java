package com.jammus.pvt.activities;

import java.util.Random;

import com.jammus.pvt.R;
import com.jammus.pvt.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainMenu extends Activity {
	
	private static final String USER_PREFS = "UserPrefs";
	
	private User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = fetchUser();
        setContentView(R.layout.activity_start_screen);
        TextView usernameView = (TextView) findViewById(R.id.username);
        usernameView.setText("Signed in as: " + user.username());
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
        String username = settings.getString("username", "");
        if (userId <= 0) {
        	userId = new Random().nextInt(3000);
        	username = "guest" + String.valueOf(userId);
        	Editor settingsEditor = settings.edit();
        	settingsEditor.putInt("user_id", userId);
        	settingsEditor.putString("username", username);
        	settingsEditor.commit();
        }
        return new User(userId, username);
    }
}
