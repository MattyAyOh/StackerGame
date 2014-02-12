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
	
	private final static String LOCATIONS = "Puzzle.locations";
	private final static String IDS = "Puzzle.ids";
	
	private Paint fillPaint;
	private Paint outlinePaint;
	
	
	public ArrayList<Brick> bricks = new ArrayList<Brick>();
	
    private int stackSize;
    
    private float scaleFactor;
    
    private int marginX;
    private int marginY;
   
    private Brick dragging = null;
    

    private float lastRelX;
    private float lastRelY;
    
    private View sView;
    
    private boolean done;
    
	public Stack(View view, Context context) {
//		fillPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		fillPaint.setColor(0xffcccccc);
//		outlinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		outlinePaint.setColor(0xff008000);
//		outlinePaint.setStyle(Paint.Style.STROKE);
		
		bricks.add(new Brick(context, R.drawable.brick_blue, 1, 0.259f, 0.238f));
		bricks.add(new Brick(context, R.drawable.brick_green1, 3, 0.0f, 0.0f));
        bricks.add(new Brick(context, R.drawable.brick_red1, 5, 0.666f, 0.158f));

        sView=view;
	}
    
	public void draw(Canvas canvas) {
		int wid = canvas.getWidth();
		int hit = canvas.getHeight();
		
		// Determine the minimum of the two dimensions
		int minDim = wid < hit ? wid : hit;
		
		stackSize = (int)(minDim * SCALE_IN_VIEW);
		
		// Compute the margins so we center the puzzle
		marginX = (wid - stackSize) / 2;
		marginY = (hit - stackSize) / 2;


//		canvas.drawRect(marginX, marginY, marginX + stackSize, marginY + stackSize, fillPaint);
//		canvas.drawRect(marginX, marginY, marginX + stackSize, marginY + stackSize, outlinePaint);
		
//		scaleFactor = (float)stackSize  / (float)puzzleComplete.getWidth();
		scaleFactor = .5f;
		canvas.save();
		canvas.translate(marginX, marginY);
		canvas.scale(scaleFactor, scaleFactor);
		canvas.restore();
			
		for(Brick brick : bricks) {
			brick.draw(canvas, marginX, marginY, stackSize, scaleFactor);
		}
	}
	
//	
//    public boolean onTouchEvent(View view, MotionEvent event) { 
//        float relX = (event.getX() - marginX) / stackSize;
//        float relY = (event.getY() - marginY) / stackSize;
//        switch (event.getActionMasked()) {
//        
//        case MotionEvent.ACTION_DOWN:
////            Log.i("onTouchEvent", "ACTION_DOWN");
//        	return onTouched(relX, relY);
//				
//        case MotionEvent.ACTION_UP:
//        	return onReleased(view, relX, relY);
//        	
//        case MotionEvent.ACTION_CANCEL:
////            Log.i("onTouchEvent", "ACTION_UP");
//            if(dragging != null) {
//                dragging = null;
//                return true;
//            }
//
//        case MotionEvent.ACTION_MOVE:
////        	Log.i("onTouchEvent",  "ACTION_MOVE: " + event.getX() + "," + event.getY());
//            // If we are dragging, move the piece and force a redraw
//            if(dragging != null) {
//                dragging.move(relX - lastRelX, relY - lastRelY);
//                lastRelX = relX;
//                lastRelY = relY;
//                done = false;
//                view.invalidate();
//                return true;
//            }
//        }
//        return false;
//    }
    
//
//    private boolean onTouched(float x, float y) {
//        
//        // Check each piece to see if it has been hit
//        // We do this in reverse order so we find the pieces in front
//    	
//        for(int p=pieces.size()-1; p>=0;  p--) {
//            if(pieces.get(p).hit(x, y, puzzleSize, scaleFactor)) {
//                // We hit a piece!
//            	dragging = pieces.get(p);
//            	
//            	pieces.remove(dragging);
//            	pieces.add(pieces.size(), dragging);
//            	
//            	lastRelX = x;
//            	lastRelY = y;
//                return true;
//            }
//        }
//        
//        return false;
//    }
//    
//
//    private boolean onReleased(View view, float x, float y) {
//
//        if(dragging != null) {
//            if(dragging.maybeSnap()) {
//                // We have snapped into place
//                view.invalidate();
//                
//                if(isDone()) {
////                	Log.i("Puzzle", "Puzzle complete");
//                    // The puzzle is done
//                    // Instantiate a dialog box builder
//                	view.invalidate();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                    ShuffleListener listener = new ShuffleListener();
//                    // Parameterize the builder
//                    builder.setTitle(R.string.hurrah);
//                    builder.setMessage(R.string.completed_puzzle);
//                    builder.setPositiveButton(android.R.string.ok, null);
//                    builder.setNegativeButton(R.string.shuffle, listener);
//                    
//                    // Create the dialog box and show it
//                    AlertDialog alertDialog = builder.create();
//                    alertDialog.show();
//                    
//                }
//                pieces.remove(dragging);
//                pieces.add(0, dragging);
//            }
//            dragging = null;
//            return true;
//        }
//        return false;
//    }
//	private class ShuffleListener implements DialogInterface.OnClickListener {
//
//		@Override
//		public void onClick(DialogInterface dialog, int which) {
//			shuffle();
//			pView.invalidate();
//		}
//		
//	}
//    /**
//     * Determine if the puzzle is done!
//     * @return true if puzzle is done
//     */
//    public boolean isDone() {
//        for(PuzzlePiece piece : pieces) {
//            if(!piece.isSnapped()) {
//            	done = false;
//                return false;
//            }
//        }
//        done = true;
//        return true;
//    }

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
//	
	
}
