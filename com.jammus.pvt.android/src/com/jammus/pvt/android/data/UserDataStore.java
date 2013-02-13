package com.jammus.pvt.android.data;

import com.jammus.pvt.core.User;

public interface UserDataStore {
	User fetchUser();
	void saveUser(User user);
}
