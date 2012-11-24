package com.jammus.pvt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class GraphicsView extends View {
	private Paint circlePaint;
	
	private PerformTestActivity testActivity;
	
	public GraphicsView(Context context) {
		super(context);
		
		testActivity = (PerformTestActivity) context;
		
		circlePaint = new Paint(getResources().getColor(R.color.stimulus));
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();
		
		canvas.drawCircle(width / 2, height / 2, 100, circlePaint);
	}
}
