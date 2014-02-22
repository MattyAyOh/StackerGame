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
	private boolean active;
	private int num;
	private int id;
	
	public Brick(Context context, int id, int m, float x, float y, int n) {
		this.x = x;
		this.y = y;
		this.mass = m;
		this.active = true;
		this.num = n;
		this.id = id;
		brickImage = BitmapFactory.decodeResource(context.getResources(), id);
	}
	
	public void setMass(int m) {
		this.mass = m;
		this.active = false;
	}
	
	public float getHeight() {
		return brickImage.getHeight();
	}
	
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public int getMass() {
		return mass;
	}
	public void setM(int mass) {
		this.mass = mass;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
    public void move(float dx) {
        x += dx;
    }
    
    public void scroll(float dy){
    	y+= dy;
    }

	public void draw(Canvas canvas, int marginX, int marginY, float offset, int stackSize, float scaleFactor,
			boolean falling, float baseX, float baseY, float baseOffset, int fallDirection,
			int animationStep, int maxSteps) {
		if (falling) {
			canvas.save();
			canvas.translate(marginX + x * stackSize, marginY + y * stackSize - offset);
			canvas.translate((x - baseX), (offset - baseOffset));
			canvas.rotate(60 * fallDirection * Math.min(animationStep, maxSteps)/maxSteps);
			canvas.translate((baseX - x), (baseOffset - offset));
			canvas.scale(scaleFactor, scaleFactor);
			canvas.translate(-brickImage.getWidth() / 2, -brickImage.getHeight() / 2);
			canvas.drawBitmap(brickImage, 0, 0, null);
			canvas.restore();
		}
		else {
			canvas.save();
			canvas.translate(marginX + x * stackSize, marginY + y * stackSize - offset);
			canvas.scale(scaleFactor, scaleFactor);
			canvas.translate(-brickImage.getWidth() / 2, -brickImage.getHeight() / 2);
			canvas.drawBitmap(brickImage, 0, 0, null);
			canvas.restore();
		}
	}
	
    public boolean hit(float testX, float testY, int stackSize, float scaleFactor) {
    	if(this.active) {
		    int pX = (int)((testX - x) * stackSize / scaleFactor) + brickImage.getWidth() / 2;
		    int pY = (int)(((testY - y ) * stackSize )/ scaleFactor + num*brickImage.getHeight()) + brickImage.getHeight() / 2  ;
		    
		    if(pX < 0 || pX >= brickImage.getWidth() ||
		       pY < 0 || pY >= brickImage.getHeight()) {
		        return false;
		    }
		    
		    return (brickImage.getPixel(pX, pY) & 0xff000000) != 0;
    	}
    	else
    		return false;
    }
    
    public boolean isUnderCenter(float testX, int stackSize, float scaleFactor) {
		    int pX = (int)((testX - x) * stackSize / scaleFactor) + brickImage.getWidth() / 2;
		    if(pX < 0 || pX >= brickImage.getWidth())
		        return false;
		    return true;
    }
    
    public int fallDirection(float testX, int stackSize, float scaleFactor){
    	 int pX = (int)((testX - x) * stackSize / scaleFactor) + brickImage.getWidth() / 2;
		    if(pX < 0)
		    	return -1;
		    else
		    	return 1;
    }

}
