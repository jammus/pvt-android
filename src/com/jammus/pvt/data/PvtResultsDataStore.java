package com.jammus.pvt.data;

import java.util.List;

import com.jammus.pvt.PvtResults;

public interface PvtResultsDataStore {
	void save(PvtResults result);
	List<PvtResults> fetchAll();
}
