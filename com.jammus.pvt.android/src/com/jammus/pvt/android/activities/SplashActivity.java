package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.android.data.UserDataStore;
import com.jammus.pvt.android.data.sharedpreferences.UserSharedPreferencesDataStore;
import com.jammus.pvt.core.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class SplashActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        
    	UserDataStore userDataStore = new UserSharedPreferencesDataStore(this);
        User user = userDataStore.fetchUser();
        
        if (user == null) {
	        startLogIn();
        } else {
        	startMainMenu();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_splash, menu);
        return true;
    }
    
    private void startLogIn() {
    	Intent intent = new Intent(this, LogInActivity.class);
    	startActivity(intent);
    }
    
    private void startMainMenu() {
    	Intent intent = new Intent(this, MainMenuActivity.class);
    	startActivity(intent);
    }
    
}
