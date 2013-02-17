package com.jammus.pvt.android.views;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.jammus.pvt.R;
import com.jammus.pvt.android.activities.PerformTestActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class PvtView extends View {
	private Paint circlePaint;
	private PerformTestActivity testActivity;
	private long startTime;
	private boolean isStimulusShown;
	private Random randomGenerator;
	private int backgroundColor;
	
	private static final int MINIMUM_DELAY_MS = 1000;
	private static final int MAXIMUM_DELAY_MS = 2500;
	private static final int STIMULUS_TIMEOUT_MS = 1000;
	
	private TimerTask timeoutTask;
	
	public PvtView(Context context) {
		super(context);
		
		testActivity = (PerformTestActivity) context;
		
		backgroundColor = getResources().getColor(R.color.background);
		int stimulusColor = getResources().getColor(R.color.stimulus);
		circlePaint = new Paint();
		circlePaint.setColor(stimulusColor);
		
		randomGenerator = new Random();
		
		scheduleStimulus();
	}
	
	private void showStimulus() {
		startTime = System.nanoTime();
		isStimulusShown = true;
		timeoutTask = new TimerTask() {

			@Override
			public void run() {
				timeoutStimulus();
			}
			
		};
		new Timer().schedule(timeoutTask, STIMULUS_TIMEOUT_MS);
		postInvalidate();
	}
	
	private void timeoutStimulus() {
		registerError();
		hideStimulus();
		if ( ! testActivity.isTestComplete()) {
			scheduleStimulus();
		}
	}
	
	private void hideStimulus() {
		isStimulusShown = false;
		postInvalidate();
	}
	
	private void scheduleStimulus() {
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() { 
				showStimulus();
			 }
			
		};
		int delay = randomGenerator.nextInt(MAXIMUM_DELAY_MS - MINIMUM_DELAY_MS) + MINIMUM_DELAY_MS;
		new Timer().schedule(task, delay);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(backgroundColor);
		if (isStimulusShown) {
			drawStimulus(canvas);
		}
		else if (testActivity.isTestComplete()) {
			testActivity.finishTest();
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		int action = e.getAction();
		int actionCode = action & MotionEvent.ACTION_MASK;
		if (actionCode != MotionEvent.ACTION_DOWN) {
			return true;
		}
		if (isStimulusShown) {
			timeoutTask.cancel();
			registerScore();
			hideStimulus();
			if ( ! testActivity.isTestComplete()) {
				scheduleStimulus();
			}
		}
		else {
			testActivity.registerError();
		}
		return true;
	}
	
	private void registerScore() {
		float score = calculateScore();
		testActivity.registerScore(score);
	}
	
	private void registerError() {
		testActivity.registerError();
	}
	
	private float calculateScore() {
		return (System.nanoTime() - startTime) / (float) 1000000;
	}
	
	private void drawStimulus(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		canvas.drawCircle(width / 2, height / 2, 100, circlePaint);
	}
}
