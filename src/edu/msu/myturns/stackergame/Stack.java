package edu.msu.myturns.stackergame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class Stack {

	final static float SCALE_IN_VIEW = 0.9f;
	private float yOffset;
	private float yScroll;
	
	private final static String LOCATIONS = "Stack.locations";
	private final static String IDS = "Stack.ids";
	private final static String INFO = "Stack.info";	
	private int StackNum;
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
    
    private boolean unstable = false;
    private int lastStable;
    private int fallDirection;
    

    public boolean isUnstable(){
    	return unstable;
    }
    
    public int getLastStable(){
    	return lastStable;
    }
    
    public float getYScroll(){
    	return yScroll;
    }
	public Stack(View view, Context context) {
//		fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		fillPaint.setColor(0xffcccccc);
//		outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		outlinePaint.setColor(0xff008000);
//		outlinePaint.setStyle(Paint.Style.STROKE);
		StackNum = 0;
		bricks.add(new Brick(context, R.drawable.brick_blue, 1, 0.5f, 1.0f, StackNum));
		yOffset=0;
		yScroll = 0;
        sView=view;
	}
	
	//Gets and increases stack num
	public int GetStackNum(){
		return ++StackNum;
	}
    
	public void Reset(){
		StackNum = 0;
		unstable = false;
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
		canvas.translate(marginX, marginY +yOffset);
		canvas.scale(scaleFactor, scaleFactor);
		canvas.restore();
			
		Brick base = bricks.get(lastStable);
		yOffset = base.getHeight()*scaleFactor;
		int i = 0;
		if (unstable)
		{
			animationStep++;
			for(Brick brick : bricks.subList(0, lastStable+1)) {
				brick.draw(canvas, marginX, marginY, i * yOffset + yScroll*scaleFactor, stackSize, scaleFactor,
						false, base.getX(), base.getY(), lastStable * yOffset, fallDirection
						,animationStep, maxSteps);
				i++;
			}
			for(Brick brick : bricks.subList(lastStable+1, bricks.size()))
			{
				brick.draw(canvas, marginX, marginY, i * yOffset + yScroll*scaleFactor, stackSize, scaleFactor,
						true, base.getX(), base.getY(), lastStable * yOffset, fallDirection
						,animationStep, maxSteps);
				i++;
			}
			if (animationStep < 50)
				sView.invalidate();
		}
		else
			for(Brick brick : bricks) {
				brick.draw(canvas, marginX, marginY, i * yOffset + yScroll*scaleFactor, stackSize, scaleFactor,
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
                view.invalidate();
                return true;
            }
            else{
            	yScroll += relY - lastRelY;
            	for(Brick brick : bricks){
            	brick.scroll(relY - lastRelY);
            	}
            	lastRelY = relY;
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
//	this.x = x;
//	this.y = y;
//	this.mass = m;
//	this.active = true;
//	this.num = n;
//	brickImage = BitmapFactory.decodeResource(context.getResources(), id);
    
	public void saveInstanceState(Bundle bundle) {
		float [] locations = new float[bricks.size() * 2];
 		int [] ids = new int[bricks.size()];
 		int [] info = new int[bricks.size() * 2];
 		
		for(int i=0;  i<bricks.size(); i++) {
			Brick brick = bricks.get(i);
			locations[i*2] = brick.getX();
			locations[i*2+1] = brick.getY();
			ids[i*3] = brick.getId();
			ids[i*3+1] = brick.getNum();
			ids[i*3+2] = brick.getMass();
		}
		
		bundle.putFloatArray(LOCATIONS, locations);
		bundle.putIntArray(IDS,  ids);
		bundle.putIntArray(INFO, info);
	}
	
	public void loadInstanceState(Bundle bundle) {
		float [] locations = bundle.getFloatArray(LOCATIONS);
		int [] ids = bundle.getIntArray(IDS);
		int [] info = bundle.getIntArray(INFO);
        
		bricks.clear();
		for(int i=0; i<ids.length-1; i++) {
//			bricks.add(new Brick(context, ));
		}
		for(int i=0;  i<bricks.size(); i++) {
			Brick brick = bricks.get(i);
			brick.setX(locations[i*2]);
			brick.setY(locations[i*2+1]);
		}
	}
	
	
}
