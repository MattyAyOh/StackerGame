package edu.msu.myturns.stackergame;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class StackView extends View {

	@Override
	public boolean onTouchEvent(MotionEvent event) {
        return stack.onTouchEvent(this, event);
	}

	private Stack stack;
    public Stack getStack() {
        return stack;
    }
    
	public StackView(Context context) {
		super(context);
		init(context);
	}

	public StackView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public StackView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		stack = new Stack(this, context);
	}
	
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		stack.draw(canvas);
	}

//	public void saveInstanceState(Bundle bundle) {
//		stack.saveInstanceState(bundle);
//	}
//	
//	public void loadInstanceState(Bundle bundle) {
//		stack.loadInstanceState(bundle);
//	}

}
