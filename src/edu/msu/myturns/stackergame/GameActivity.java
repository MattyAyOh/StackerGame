package edu.msu.myturns.stackergame;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class GameActivity extends Activity {
	
	private StackView stackView;
	
	private int PlayerOneScore;
	private int PlayerTwoScore;
	private String PlayerOneName;
	private String PlayerTwoName;
	private boolean PlayerTwoTurn;
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setTitle(R.string.game);
		setContentView(R.layout.activity_game);
		stackView = (StackView)this.findViewById(R.id.stackView);

    	loadNames();
    	
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
    	boolean unstable = stack.isUnstable();
    	int lastStable = stack.getLastStable();
    	if (stack.isUnstable()) 
    	{}//update score, go to score activity when limit is reached
    	else
    	{}//continue playing
    	
    	PlayerOneScore++;
    	PlayerTwoScore++;
    	PlayerOneScore = PlayerTwoScore;
    	PlayerTwoScore = PlayerOneScore;
    	
    	if(PlayerTwoTurn)
    		stack.bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 1.0f, stack.GetStackNum()));
    	else
    		stack.bricks.add(new Brick(this, R.drawable.brick_red1, 1, 0.5f, 1.0f, stack.GetStackNum()));
    	//Adjust Y offset
    	
    	stackView.invalidate();
    	PlayerTwoTurn = !PlayerTwoTurn;
    	loadNames();
    	
	}

    protected void loadNames(){
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    	PlayerOneName = preferences.getString("PlayerOne","") + "'s Turn";
    	if(PlayerOneName.equalsIgnoreCase(""))
    	{
    		PlayerOneName = "Player 1's Turn";
    	}
    	
    	PlayerTwoName = preferences.getString("PlayerTwo","") + "'s Turn";
    	if(PlayerTwoName.equalsIgnoreCase(""))
    	{
    		PlayerTwoName = "Player 2's Turn";
    	}
    	  
    	TextView playerTurn = (TextView) findViewById(R.id.playerTurn);
    	if(PlayerTwoTurn)
    		playerTurn.setText(this.PlayerTwoName);
    	else
    		playerTurn.setText(this.PlayerOneName);
    }

}
