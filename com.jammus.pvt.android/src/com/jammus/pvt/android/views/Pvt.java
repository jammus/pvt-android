package com.jammus.pvt.android.views;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import com.jammus.pvt.R;
import com.jammus.pvt.android.activities.PerformTest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class Pvt extends View {
	private Paint circlePaint;
	private PerformTest testActivity;
	private long startTime;
	private boolean isStimulusShown;
	private Random randomGenerator;
	private int backgroundColor;
	
	private final int MINIMUM_DELAY = 2000;
	private final int MAXIMUM_DELAY = 10000;
	
	public Pvt(Context context) {
		super(context);
		
		testActivity = (PerformTest) context;
		
		backgroundColor = getResources().getColor(R.color.background);
		int stimulusColor = getResources().getColor(R.color.stimulus);
		circlePaint = new Paint();
		circlePaint.setColor(stimulusColor);
		
		randomGenerator = new Random();
		
		scheduleStimulus();
	}
	
	private void showStimulus() {
		isStimulusShown = true;
		postInvalidate();
	}
	
	private void hideStimulus() {
		isStimulusShown = false;
		postInvalidate();
	}
	
	private void scheduleStimulus() {
		TimerTask task = new TimerTask() {
			@Override
			public void run() { 
				startTime = System.nanoTime();
				showStimulus();
			 }
			
		};
		int delay = randomGenerator.nextInt(MAXIMUM_DELAY - MINIMUM_DELAY) + MINIMUM_DELAY;
		new Timer().schedule(task, delay);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		canvas.drawColor(backgroundColor);
		if (isStimulusShown) {
			drawStimulus(canvas);
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
			float score = calculateScore();
			testActivity.registerScore(score);
			hideStimulus();
			if (! testActivity.isTestComplete()) {
				scheduleStimulus();
			}
		}
		else {
			testActivity.registerError();
		}
		return true;
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
