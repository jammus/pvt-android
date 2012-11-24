package com.jammus.pvt;

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
	
	public GraphicsView(Context context) {
		super(context);
		
		testActivity = (PerformTestActivity) context;
		
		circlePaint = new Paint(getResources().getColor(R.color.stimulus));
		
		scheduleStimulus();
	}
	
	private void showStimulus() {
		isStimulusShown = true;
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
		new Timer().schedule(task, 2000);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		if (isStimulusShown) {
			drawStimulus(canvas);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent e) {
		if (! isStimulusShown) {
			testActivity.registerError();
		}
		else {
			isStimulusShown = false;
			testActivity.registerScore();
			scheduleStimulus();
			postInvalidate();
		}
		return true;
	}
	
	private void drawStimulus(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		canvas.drawCircle(width / 2, height / 2, 100, circlePaint);
	}
}
