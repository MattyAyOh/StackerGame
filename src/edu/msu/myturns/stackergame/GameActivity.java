package edu.msu.myturns.stackergame;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

public class GameActivity extends Activity {
	
	private StackView stackView;
	private int PlayerOneScore;
	private int PlayerTwoScore;
	private String PlayerOneName;
	private String PlayerTwoName;
	private boolean PlayerTwoTurn;
	
	@Override
	protected void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		stackView.saveInstanceState(bundle);
	}
	
	@Override
	protected void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setTitle(R.string.game);
		setContentView(R.layout.activity_game);
		stackView = (StackView)this.findViewById(R.id.stackView);
		
    	loadNames();
		
		if(PlayerTwoTurn)
			stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_red1, 1, 0.5f, 1.0f, 0));
		else
			stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 1.0f, 0));
		if(bundle != null) {;
			stackView.loadInstanceState(bundle);
		}

	}
	
    protected void loadNames(){
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    	PlayerOneName = preferences.getString("PlayerOne","");
    	PlayerTwoName = preferences.getString("PlayerTwo","");
    	PlayerOneScore = preferences.getInt("PlayerOneScore", 0);
    	PlayerTwoScore = preferences.getInt("PlayerTwoScore", 0);
    	PlayerTwoTurn = preferences.getBoolean("PlayerTwoTurn", false);

    	  
    	TextView playerTurn = (TextView) findViewById(R.id.playerTurn);
    	TextView scoreBoard = (TextView) findViewById(R.id.scoreBoard);
    	if(PlayerTwoTurn)
    		playerTurn.setText(this.PlayerTwoName + "'s Turn");
    	else
    		playerTurn.setText(this.PlayerOneName + "'s Turn");
    	
    	scoreBoard.setText(this.PlayerOneName + ": " + this.PlayerOneScore 
    			+ " - " + this.PlayerTwoName  + ": " + this.PlayerTwoScore);
    }
    
    
    
    
    
    
	
    public void onSelectWeight(View view) {
    	Stack stack = stackView.getStack();
    	int weight = Integer.parseInt((String)view.getTag()); 
    	stack.bricks.get(stack.bricks.size()-1).setMass(weight);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = preferences.edit();
		
    	stack.physicsCheck(); 
    	if (stack.isUnstable()) 
    	{
    		if(PlayerTwoTurn)
    			PlayerOneScore++;
    		else
    			PlayerTwoScore++;

    		editor.putInt("PlayerOneScore", PlayerOneScore);
    		editor.putInt("PlayerTwoScore", PlayerTwoScore);

    		
    		if(PlayerOneScore == 5 || PlayerTwoScore == 5) {
    			Intent intent = new Intent(this, ScoreActivity.class);
    			startActivity(intent);
    			stack.bricks.clear();
        		editor.putInt("PlayerOneScore", PlayerOneScore);
        		editor.putInt("PlayerTwoScore", PlayerTwoScore);
        		editor.commit();
    			return;
    			
    		}

//    		stack.bricks.clear(); 
//    		stack.Reset();
    		
    		PlayerTwoTurn = !PlayerTwoTurn;
			if(PlayerTwoTurn)
	    		stack.bricks.add(new Brick(this, R.drawable.brick_red1, 1, 0.5f, 1.0f + stack.getYScroll(), 0));
	    	else
	    		stack.bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 1.0f + stack.getYScroll(), 0));
			
    		editor.putBoolean("PlayerTwoTurn", PlayerTwoTurn);
    		editor.commit();
    		stackView.invalidate();
    		Intent intent = new Intent(this, GameActivity.class);
    		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
    	}
    	else
    	{
        	int lastNum = stack.bricks.get(stack.bricks.size()-1).getNum() + 1;
        	if(PlayerTwoTurn)
        		stack.bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 1.0f + stack.getYScroll(), lastNum));
        	else
        		stack.bricks.add(new Brick(this, R.drawable.brick_red1, 1, 0.5f, 1.0f + stack.getYScroll(), lastNum));
        	PlayerTwoTurn = !PlayerTwoTurn;
    		editor.putBoolean("PlayerTwoTurn", PlayerTwoTurn);
    		editor.commit();
    	}
    	
    	stackView.invalidate();
    	loadNames();
    	
	}

    


}
