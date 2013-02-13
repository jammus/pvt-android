package com.jammus.pvt.android.data.sharedpreferences;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.jammus.pvt.android.data.UserDataStore;
import com.jammus.pvt.core.User;

public class UserSharedPreferencesDataStore implements UserDataStore {
	
	private static final String USER_PREFS = "UserPrefs";
	
	Activity activity;
	
	public UserSharedPreferencesDataStore(Activity activity) {
		this.activity = activity;
	}

	public User fetchUser() {
        SharedPreferences settings = activity.getSharedPreferences(USER_PREFS, 0);
        int id = settings.getInt("id", -1);
        String name = settings.getString("name", "");
        String email = settings.getString("email", "");
        String token = settings.getString("token", "");
        if (email == ""|| token == "") {
        	return null;
        }
        return new User(id, email, name, token);
	}

	public void saveUser(User user) {
		SharedPreferences settings = activity.getSharedPreferences(USER_PREFS, 0);
		Editor settingsEditor = settings.edit();
		settingsEditor.putInt("id", user.id());
		settingsEditor.putString("name", user.name());
		settingsEditor.putString("email", user.email());
		settingsEditor.putString("token", user.token());
		settingsEditor.commit();
	}

}
