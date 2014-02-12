package edu.msu.myturns.stackergame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Brick {

	private Bitmap brickImage;
	
	private float x = 0;
	private float y = 0;
	private int mass = 0;
	
	private Stack stack;
	
	public Brick(Context context, int id, int m, float x, float y) {
		this.x = x;
		this.y = y;
		this.mass = m;
		brickImage = BitmapFactory.decodeResource(context.getResources(), id);
	}
	
	public void draw(Canvas canvas, int marginX, int marginY, int stackSize, float scaleFactor) {
		canvas.save();
		canvas.translate(marginX + x * stackSize, marginY + y * stackSize);
		canvas.scale(scaleFactor, scaleFactor);
		canvas.translate(-brickImage.getWidth() / 2, -brickImage.getHeight() / 2);
		canvas.drawBitmap(brickImage, 0, 0, null);
		canvas.restore();
	}

}
