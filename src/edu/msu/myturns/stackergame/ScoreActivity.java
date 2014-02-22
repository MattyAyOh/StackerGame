package edu.msu.myturns.stackergame;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class ScoreActivity extends Activity {

	private String PlayerOneName;
	private String PlayerTwoName;
	private int PlayerOneScore;
	private int PlayerTwoScore;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.score);
		setContentView(R.layout.activity_score);
		
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    	PlayerOneScore = preferences.getInt("PlayerOneScore", 0);
    	PlayerTwoScore = preferences.getInt("PlayerTwoScore", 0);
    	
    	PlayerOneName = preferences.getString("PlayerOne","") + ": " + PlayerTwoScore;
    	if(PlayerOneName.equalsIgnoreCase(""))
    	{
    		PlayerOneName = "Player 1: " + PlayerTwoScore;
    	}
    	
    	PlayerTwoName = preferences.getString("PlayerTwo","") + ": " + PlayerOneScore;
    	if(PlayerTwoName.equalsIgnoreCase(""))
    	{
    		PlayerTwoName = "Player 2: " + PlayerOneScore;
    	}
    	  
    	TextView playerOne = (TextView) findViewById(R.id.textView2);
    	TextView playerTwo = (TextView) findViewById(R.id.textView3);

		playerOne.setText(this.PlayerTwoName);

		playerTwo.setText(this.PlayerOneName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.score, menu);
		return true;
	}
	
    public void onGoHome(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
