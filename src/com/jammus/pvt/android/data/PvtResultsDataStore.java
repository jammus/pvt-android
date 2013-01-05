package com.jammus.pvt.android.data;

import java.util.List;

import com.jammus.pvt.core.PvtResult;

public interface PvtResultsDataStore {
	void save(int userId, PvtResult result);
	List<PvtResult> fetchAllForUser(int userId);
}
