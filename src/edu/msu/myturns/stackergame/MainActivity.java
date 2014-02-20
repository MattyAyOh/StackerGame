package edu.msu.myturns.stackergame;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.mainMenu);
		setContentView(R.layout.activity_main);
	}

    public void onStartGame(View view) {
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
    
    public void onHowTo(View view) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("How to Play");
        alert.setMessage("Decide who is player 1; he will go first.\n"
        		+ "Choose the position of the brick by dragging it side.\n"
        		+ "You can then select a weight and the brick will be placed"
        		+ "at it's current position.\nThen hand the device to Player 2, "
        		+ "and repeat the process.\nAt any point if a player places a brick"
        		+ "that causes the stack to topple, that player will lose the round."
        		+ "The first player to win 5 rounds wins the game.  Good luck!");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Your action here
            }
        });


        alert.show();
    }

}
