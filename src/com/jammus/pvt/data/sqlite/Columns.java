package com.jammus.pvt.data.sqlite;

import android.provider.BaseColumns;

public interface Columns extends BaseColumns {
	String DATE = "date";
	String USER_ID = "user_id";
	String ERROR_COUNT = "error_count";
	String AVERAGE_RT = "average_response_time";
	String PVT_RESULT_ID = "result_id";
	String TEST_NO = "test_no";
	String RESPONSE_TIME = "response_time";
}