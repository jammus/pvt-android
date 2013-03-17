package com.jammus.pvt.android.fragments;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.jammus.pvt.R;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

	Activity parentActivity;
	
	DateFormat dateFormat;
	
	EditText datePicker;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstance) {
		parentActivity = getActivity();
        dateFormat = android.text.format.DateFormat.getMediumDateFormat(parentActivity);
        datePicker = (EditText) parentActivity.findViewById(R.id.editDate);
        try {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dateFormat.parse(datePicker.getText().toString()));
			return new DatePickerDialog(getActivity(), this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
		} catch (ParseException e) {
			return new DatePickerDialog(getActivity(), this, 1970, 1, 1);
		}
	}
	
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
        datePicker.setText(dateFormat.format(calendar.getTime()));
	}

}
