package com.jammus.pvt.android.data.sqlite;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jammus.pvt.android.data.PvtResultsDataStore;
import com.jammus.pvt.core.PvtResult;

public class PvtResultsSQLiteDataStore implements PvtResultsDataStore {
	
	private PvtResultsSQLiteDatabase resultsDatabase;
	
	public PvtResultsSQLiteDataStore(Context context) {
		this.resultsDatabase = new PvtResultsSQLiteDatabase(context);
	}

	public void save(int userId, PvtResult result) {
		SQLiteDatabase db = resultsDatabase.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(Columns.USER_ID, userId);
		values.put(Columns.DATE, result.date().getTime());
		values.put(Columns.AVERAGE_RT, result.averageRt());
		values.put(Columns.ERROR_COUNT, result.errorCount());
		
		long pvtResultId = db.insertOrThrow(Tables.PvtResults, null, values);
		
		saveResponseTimes(db, pvtResultId, result.responseTimes());
	}
	
	private void saveResponseTimes(SQLiteDatabase db, long pvtResultId, float[] times) {
		for (int index = 0; index < times.length; index++) {
			ContentValues values = new ContentValues();
			values.put(Columns.PVT_RESULT_ID, pvtResultId);
			values.put(Columns.TEST_NO, index);
			values.put(Columns.RESPONSE_TIME, times[index]);
			
			db.insertOrThrow(Tables.PvtResultTimes, null, values);
		}
	}

	public List<PvtResult> fetchAllForUser(int userId) {
		String[] columns = { Columns._ID, Columns.DATE, Columns.ERROR_COUNT };
		String filter = Columns.USER_ID + " = ?";
		String[] args = { String.valueOf(userId) };
		String orderBy = Columns.DATE + " DESC";
		
		SQLiteDatabase db = resultsDatabase.getReadableDatabase();
		Cursor cursor = db.query(Tables.PvtResults, columns, filter, args, null, null, orderBy);
		
		List<PvtResult> results = new ArrayList<PvtResult>();
		
		while (cursor.moveToNext()) {
			long pvtResultId = cursor.getLong(0);
			Date date = new Date(cursor.getLong(1));
			int errors = cursor.getInt(2);
			float[] times = fetchTimes(pvtResultId);
			PvtResult result = new PvtResult(date, times, errors);
			results.add(result);
		}
		
		return results;
	}
	
	private float[] fetchTimes(long pvtResultId) {
		String[] columns = { Columns.RESPONSE_TIME };
		String filter = Columns.PVT_RESULT_ID + " = ?";
		String orderBy = Columns.TEST_NO;
		String[] args = new String[] { String.valueOf(pvtResultId) };
		
		SQLiteDatabase db = resultsDatabase.getReadableDatabase();
		Cursor cursor = db.query(Tables.PvtResultTimes, columns, filter, args, null, null, orderBy);
		
		int count = cursor.getCount();
		float[] times = new float[count];
		int index = 0;
		
		while (cursor.moveToNext()) {
			float time = cursor.getFloat(0);
			times[index++] = time;
		}
		
		return times;
	}
}