package edu.msu.myturns.stackergame;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;

public class GameActivity extends Activity {
	
	private StackView stackView;
	
	private int PlayerOneScore;
	private int PlayerTwoScore;
	private boolean PlayerOneTurn;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.activity_game);
		stackView = (StackView)this.findViewById(R.id.stackView);
		
		if(bundle != null) {
			// We have saved state
//			stackView.loadInstanceState(bundle);
		}

	}
	
    public void onSelectWeight(View view) {
    	int weight = Integer.parseInt((String)view.getTag()); 
    	Stack stack = stackView.getStack();
    	stack.bricks.get(stack.bricks.size()-1).setMass(weight);
    	
    	//Check to see if fallen, track score, after fallen reset stackView
    	stack.physicsCheck(); //calculate physics
    	if (stack.isUnstable()) 
    	{}//update score, go to score activity when limit is reached
    	else
    	{}//continue playing
    	
    	PlayerOneScore++;
    	PlayerTwoScore++;
    	PlayerOneScore = PlayerTwoScore;
    	PlayerTwoScore = PlayerOneScore;
    	
    	if(PlayerOneTurn)
    		stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 1.0f));
    	else
    		stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_red1, 1, 0.5f, 1.0f));
    	
    	//Adjust Y offset
    	
    	stackView.invalidate();
    	PlayerOneTurn = !PlayerOneTurn;
    	
	}


}
