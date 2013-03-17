package com.jammus.pvt.android.activities;

import com.jammus.pvt.R;
import com.jammus.pvt.R.layout;
import com.jammus.pvt.R.menu;
import com.jammus.pvt.android.fragments.DatePickerFragment;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class ScreeningSurveyActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screening_survey);
        EditText datePicker = (EditText) findViewById(R.id.editDate);
        datePicker.setFocusable(false);
        
        Spinner spinner = (Spinner) findViewById(R.id.genderPicker);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genders_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_screening_survey, menu);
        return true;
    }
    
    public void pickDate(View view) {
    	DialogFragment dialogFragment = new DatePickerFragment();
    	dialogFragment.show(getSupportFragmentManager(), "datePicker");
    }
    
}
