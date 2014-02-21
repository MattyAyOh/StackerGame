package edu.msu.myturns.stackergame;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Stack {

	final static float SCALE_IN_VIEW = 0.9f;
	private float yOffset;
	
	private final static String LOCATIONS = "Puzzle.locations";
	private final static String IDS = "Puzzle.ids";
	
	private Paint fillPaint;
	private Paint outlinePaint;
	
	
	public ArrayList<Brick> bricks = new ArrayList<Brick>();
	
    private int stackSize;
    int animationStep = 0;
    private static final int maxSteps = 50;
    private float scaleFactor;
    
    private int marginX;
    private int marginY;
   
    private Brick dragging = null;
    

    private float lastRelX;
    private float lastRelY;
    
    private View sView;
    
    private boolean done;
    private boolean unstable = false;
    private int lastStable;
    private int fallDirection;
    

    public boolean isUnstable(){
    	return unstable;
    }
    
    public int getLastStable(){
    	return lastStable;
    }
	public Stack(View view, Context context) {
//		fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		fillPaint.setColor(0xffcccccc);
//		outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		outlinePaint.setColor(0xff008000);
//		outlinePaint.setStyle(Paint.Style.STROKE);
		
		bricks.add(new Brick(context, R.drawable.brick_blue, 1, 0.5f, 1.0f));
		yOffset=0;
        sView=view;
	}
    
	public void draw(Canvas canvas) {
		int wid = canvas.getWidth();
		int hit = canvas.getHeight();
		
		// Determine the minimum of the two dimensions
//		int minDim = wid < hit ? wid : hit;
		
		stackSize = (int)(hit * SCALE_IN_VIEW);
		
		// Compute the margins so we center the puzzle
		marginX = (wid - stackSize) / 2;
		marginY = (hit - stackSize) / 2;


//		canvas.drawRect(marginX, marginY, marginX + stackSize, marginY + stackSize, fillPaint);
//		canvas.drawRect(marginX, marginY, marginX + stackSize, marginY + stackSize, outlinePaint);
		
		scaleFactor = (float)stackSize  / wid;
		canvas.save();
		canvas.translate(marginX, marginY);
		canvas.scale(scaleFactor, scaleFactor);
		canvas.restore();
			
		Brick base = bricks.get(lastStable);
		yOffset = base.getHeight()*scaleFactor;
		int i = 0;
		if (unstable)
		{
			animationStep++;
			for(Brick brick : bricks.subList(0, lastStable+1)) {
				brick.draw(canvas, marginX, marginY, i * yOffset, stackSize, scaleFactor,
						false, base.getX(), base.getY(), lastStable * yOffset, fallDirection
						,animationStep, maxSteps);
				i++;
			}
			for(Brick brick : bricks.subList(lastStable+1, bricks.size()))
			{
				brick.draw(canvas, marginX, marginY, i * yOffset, stackSize, scaleFactor,
						true, base.getX(), base.getY(), lastStable * yOffset, fallDirection
						,animationStep, maxSteps);
				i++;
			}
			if (animationStep < 50)
				sView.invalidate();
		}
		else
			for(Brick brick : bricks) {
				brick.draw(canvas, marginX, marginY, i * yOffset, stackSize, scaleFactor,
						false, base.getX(), base.getY(), lastStable * yOffset, fallDirection
						,animationStep, maxSteps);
				i++;
			}	
	}
	
    public boolean onTouchEvent(View view, MotionEvent event) { 
        float relX = (event.getX() - marginX) / stackSize;
        float relY = (event.getY() - marginY) / stackSize;
        switch (event.getActionMasked()) {
        
        case MotionEvent.ACTION_DOWN:
        	return onTouched(relX, relY);
				
        case MotionEvent.ACTION_UP:
        	return onReleased(view, relX, relY);
        	
        case MotionEvent.ACTION_CANCEL:
            if(dragging != null) {
                dragging = null;
                return true;
            }

        case MotionEvent.ACTION_MOVE:
            if(dragging != null) {
                dragging.move(relX - lastRelX);
                lastRelX = relX;
                done = false;
                view.invalidate();
                return true;
            }
        }
        return false;
    }
    

    private boolean onTouched(float x, float y) {
        
        for(int b=bricks.size()-1; b>=0;  b--) {
            if(bricks.get(b).hit(x, y, stackSize, scaleFactor)) {
            	dragging = bricks.get(b);

            	lastRelX = x;
            	lastRelY = y;
                return true;
            }
        }
        return false;
    }
    

    private boolean onReleased(View view, float x, float y) {
        if(dragging != null) {       
            dragging = null;
            return true;
        }
        return false;
    }
    
    public void physicsCheck(){
    	for (int i = 0; i < bricks.size(); i++){
    		Brick base = bricks.get(i);
    		lastStable = i;
    		float sum = 0;
    		float totalMass = 0;
    		for (Brick b : bricks.subList(i+1,bricks.size())){
    			sum += b.getMass() * b.getX();
    			totalMass += b.getMass();
    		}
    		float center = sum / totalMass;
    		if (!base.isUnderCenter(center, stackSize, scaleFactor)){
    			unstable = true;
    			fallDirection = base.fallDirection(center, stackSize, scaleFactor);
    			return;
    		}
    	}
    }


//
//	public void saveInstanceState(Bundle bundle) {
//		float [] locations = new float[pieces.size() * 2];
// 		int [] ids = new int[pieces.size()];
// 		
//		for(int i=0;  i<pieces.size(); i++) {
//			PuzzlePiece piece = pieces.get(i);
//			locations[i*2] = piece.getX();
//			locations[i*2+1] = piece.getY();
//			ids[i] = piece.getId();
//		}
//		
//		bundle.putFloatArray(LOCATIONS, locations);
//		bundle.putIntArray(IDS,  ids);
//	}
//	
//	public void loadInstanceState(Bundle bundle) {
//		float [] locations = bundle.getFloatArray(LOCATIONS);
//		int [] ids = bundle.getIntArray(IDS);
//        
//		for(int i=0; i<ids.length-1; i++) {
//			
//			// Find the corresponding piece
//			// We don't have to test if the piece is at i already,
//			// since the loop below will fall out without it moving anything
//			for(int j=i+1;  j<ids.length;  j++) {
//				if(ids[i] == pieces.get(j).getId()) {
//					// We found it
//					// Yah...
//					// Swap the pieces
//					PuzzlePiece t = pieces.get(i);
//					pieces.set(i, pieces.get(j));
//					pieces.set(j, t);
//				}
//			}
//		}
//		for(int i=0;  i<pieces.size(); i++) {
//			PuzzlePiece piece = pieces.get(i);
//			piece.setX(locations[i*2]);
//			piece.setY(locations[i*2+1]);
//		}
//	}
	
	
}
