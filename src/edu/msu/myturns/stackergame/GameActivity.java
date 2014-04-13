package edu.msu.myturns.stackergame;


import java.util.ArrayList;

import android.R.drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
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
	
	private boolean move_sent;
	private String uname;
	private String upass;
	private int bricknum;
	private float brickx;
	private float bricky;
	private int brickweight;
	private boolean newgame;
	
	private ArrayList<Move> received;
	
	public class Move{
		public int number;
		public float x;
		public float y;
		public int weight;
		public String username;
	}
	
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
		
		//receive game or create new one if needed
		//uncomment me to get game!
//		new Thread(new Runnable() {
//
//            @Override
//            public void run() {
//                // Create a cloud object and get the XML
//                Web cloud = new Web();
//                receive = cloud.getGame(user, pass);
//               stackView.post(new Runnable(){
//                	@Override
//                	public void run()
//                	{
//                		if(receive == null)
//                		{
//                			String error = "No Game for this user!";
//            	    		Toast.makeText(stackView.getContext(), error, Toast.LENGTH_SHORT).show();
//							createGame();
//                		}
//                		else
//                		{
//                			LoadGame();
//                		}
//                	}
//                });
//            }
//    	}).start();
		
    	loadNames();
    	
    	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
    	uname = pref.getString("CUsername", "");
    	upass = pref.getString("CPassword", "");
    	
		stackView.getStack().bricks.clear();
		
		//this is here so there are no errors? comment me later!
		if(PlayerTwoTurn)
			stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_red1, 1, 0.5f, 0.7f, 0));
		else
			stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 0.7f, 0));
		if(bundle != null) {;
			stackView.loadInstanceState(bundle);
		}

	}
	
	public void loadGame()
	{
		stackView.getStack().bricks.clear();
		for(Move moves: received)
		{
			boolean red = true;
			if(moves.username == uname)
			{
				 red = false;
			}
			if(red)
			{
				stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_red1,moves.weight, moves.x,moves.y,moves.number ));
			}else
			{
				stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_blue,moves.weight, moves.x,moves.y,moves.number ));
			}
		}
	}
	
	private void createGame()
	{
		new Thread(new Runnable() {

            @Override
            public void run() {
                // Create a cloud object and get the XML
                Web cloud = new Web();
                newgame = cloud.newGame(uname, upass);
               stackView.post(new Runnable(){
                	@Override
                	public void run()
                	{
                		if(!newgame)
                		{
                			String error = "New Game could not be created!";
            	    		Toast.makeText(stackView.getContext(), error, Toast.LENGTH_SHORT).show();
                		}
                		else
                		{
                			startGame();
                		}
                	}
                });
            }
    	}).start();
	}
	
	private void startGame()
	{
		stackView.getStack().bricks.clear();
		stackView.getStack().bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 0.7f, 0));
	}
	
    protected void loadNames(){
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    	PlayerOneName = preferences.getString("PlayerOne","");
    	PlayerTwoName = preferences.getString("PlayerTwo","");
    	PlayerOneScore = preferences.getInt("PlayerOneScore", 0);
    	PlayerTwoScore = preferences.getInt("PlayerTwoScore", 0);
    	PlayerTwoTurn = preferences.getBoolean("PlayerTwoTurn", false);
    	
    	uname = preferences.getString("CUsername", "");
    	upass = preferences.getString("CPassword", "");

    	  
    	TextView playerTurn = (TextView) findViewById(R.id.playerTurn);
    	TextView scoreBoard = (TextView) findViewById(R.id.scoreBoard);
//    	if(PlayerTwoTurn)
//    		playerTurn.setText(this.PlayerTwoName + "'s Turn");
//    	else
//    		playerTurn.setText(this.PlayerOneName + "'s Turn");
    	
    	playerTurn.setText("Its your turn!");
    	
    	scoreBoard.setText(this.PlayerOneName + ": " + this.PlayerOneScore 
    			+ " - " + this.PlayerTwoName  + ": " + this.PlayerTwoScore);
    }
    
    
    private void gotoWait(){
    	Intent intent = new Intent(this, WaitingActivity.class);
        startActivity(intent);
    }

	
    public void onSelectWeight(View view) {
    	
    	Stack stack = stackView.getStack();
    	brickweight = Integer.parseInt((String)view.getTag()); 
    	stack.bricks.get(stack.bricks.size()-1).setMass(brickweight);
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
//			if(PlayerTwoTurn)
//	    		stack.bricks.add(new Brick(this, R.drawable.brick_red1, 1, 0.5f, 0.7f + stack.getYScroll(), 0));
//	    	else
//	    		stack.bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 0.7f + stack.getYScroll(), 0));
			
    		editor.putBoolean("PlayerTwoTurn", PlayerTwoTurn);
    		editor.commit();
    		//stackView.invalidate();
    		Intent intent = new Intent(this, GameActivity.class);
    		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    		startActivity(intent);
    	}
    	else
    	{
//        	int lastNum = stack.bricks.get(stack.bricks.size()-1).getNum() + 1;
//        	if(PlayerTwoTurn)
//        		stack.bricks.add(new Brick(this, R.drawable.brick_blue, 1, 0.5f, 0.7f + stack.getYScroll(), lastNum));
//        	else
//        		stack.bricks.add(new Brick(this, R.drawable.brick_red1, 1, 0.5f, 0.7f + stack.getYScroll(), lastNum));
        	PlayerTwoTurn = !PlayerTwoTurn;
    		editor.putBoolean("PlayerTwoTurn", PlayerTwoTurn);
    		editor.commit();
    	}
    	
    	//stackView.invalidate();
    	loadNames();
    	
    	//send move
    	
    	brickx = stack.bricks.get(stack.bricks.size()-1).getX();
    	bricky = stack.bricks.get(stack.bricks.size()-1).getY();
    	bricknum = stack.bricks.get(stack.bricks.size()-1).getNum();
    	
    	//Uncomment me to send move!
    	//SendMove();
	}
    
    private void SendMove()
    {
    
    	new Thread(new Runnable() {

            @Override
            public void run() {
                // Create a cloud object and get the XML
                Web cloud = new Web();
                move_sent = cloud.sendMove(uname, upass, bricknum, brickx, bricky, brickweight);
                
                stackView.post(new Runnable(){
                	@Override
                	public void run()
                	{
                		if(!move_sent)
                		{
                			String error = "Unable to send move!";
            	    		Toast.makeText(stackView.getContext(), error, Toast.LENGTH_SHORT).show();
                		}
                		else
                		{
                			gotoWait();
                		}
                	}
                });
            }
    	}).start();
    }

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
        case R.id.menu_exit:
            //finish
        	finish();
            return true;
            

        }
		return super.onOptionsItemSelected(item);
	}


}
