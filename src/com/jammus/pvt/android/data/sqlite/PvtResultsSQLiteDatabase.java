package com.jammus.pvt.android.data.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PvtResultsSQLiteDatabase extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 2;
	private static final String DATABASE_NAME = "results";
	
	public PvtResultsSQLiteDatabase(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(
			"CREATE TABLE " + Tables.PvtResults + " (" +
				Columns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + 
				Columns.USER_ID + " INTEGER NOT NULL," +
				Columns.DATE + " INTEGER NOT NULL," + 
				Columns.AVERAGE_RT + " REAL NOT NULL," +
				Columns.ERROR_COUNT + " INTEGER NOT NULL" +
			");"
		);
		
		db.execSQL(
			"CREATE TABLE " + Tables.PvtResultTimes + " (" + 
				Columns.PVT_RESULT_ID + " INTEGER NOT NULL," +
				Columns.TEST_NO + " INTEGER NOT NULL," +
				Columns.RESPONSE_TIME + " REAL NOT NULL, " +
				"PRIMARY KEY(" + Columns.PVT_RESULT_ID + ", " + Columns.TEST_NO + ")" + 
			");"
		);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Tables.PvtResultTimes);
		db.execSQL("DROP TABLE IF EXISTS " + Tables.PvtResults);
		onCreate(db);
	}

}
