package com.jammus.pvt;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class GraphicsView extends View {
	private Paint circlePaint;
	private PerformTestActivity testActivity;
	private long startTime;
	private boolean isStimulusShown;
	private Random randomGenerator;
	
	private final int MINIMUM_DELAY = 2000;
	private final int MAXIMUM_DELAY = 10000;
	
	public GraphicsView(Context context) {
		super(context);
		
		testActivity = (PerformTestActivity) context;
		
		circlePaint = new Paint(getResources().getColor(R.color.stimulus));
		
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
				startTime = scheduledExecutionTime();
				showStimulus();
			 }
			
		};
		int delay = randomGenerator.nextInt(MAXIMUM_DELAY - MINIMUM_DELAY) + MINIMUM_DELAY;
		new Timer().schedule(task, delay);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		if (isStimulusShown) {
			drawStimulus(canvas);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (isStimulusShown) {
			testActivity.registerScore();
			hideStimulus();
			scheduleStimulus();
		}
		else {
			testActivity.registerError();
		}
		return true;
	}
	
	private void drawStimulus(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		canvas.drawCircle(width / 2, height / 2, 100, circlePaint);
	}
}
