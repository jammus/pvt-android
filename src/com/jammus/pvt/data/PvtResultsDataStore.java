package com.jammus.pvt.data;

import java.util.List;

import com.jammus.pvt.PvtResult;

public interface PvtResultsDataStore {
	void save(PvtResult result);
	List<PvtResult> fetchAll();
}
