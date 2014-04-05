package edu.msu.myturns.stackergame;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	//private String pass;
	//private String user;
	private boolean created;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(R.string.mainMenu);
		setContentView(R.layout.activity_main);
	}

//    public void onStartGame(View view) {
//    	EditText playerOne = (EditText)findViewById(R.id.editText1);
//    	String oneName = playerOne.getText().toString();
//    	EditText playerTwo = (EditText)findViewById(R.id.editText2);
//    	String twoName = playerTwo.getText().toString();
//    	
//    	if(oneName.matches(""))
//    		oneName = "Player 1";
//    	if(twoName.matches(""))
//    		twoName = "Player 2";
//    	
//		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//		SharedPreferences.Editor editor = preferences.edit();
//		editor.putString("PlayerOne",oneName);
//		editor.putString("PlayerTwo",twoName);
//		editor.putInt("PlayerOneScore", 0);
//		editor.putInt("PlayerTwoScore", 0);
//		editor.putBoolean("PlayerTwoTurn", false);
//		editor.commit();
//		
//		
//		Intent intent = new Intent(this, GameActivity.class);
//		startActivity(intent);
//	}
    
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
            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        alert.show();
    }
    
    public void onLogin(View view){
    	CheckBox remember = (CheckBox)findViewById(R.id.checkBoxRemember);
    	if(remember.isChecked())
    	{
    		//save the values for later
    	}
    	
    	EditText username = (EditText)findViewById(R.id.editText1);
    	final String user = username.getText().toString();
    	EditText password = (EditText)findViewById(R.id.editText2);
    	final String pass = password.getText().toString();

    	//create the user
		//connect to the data base
    	
    	new Thread(new Runnable() {

            @Override
            public void run() {
                // Create a cloud object and get the XML
                Web cloud = new Web();
                created = cloud.login(user, pass);
                runOnUiThread(new Runnable(){
                	@Override
                	public void run()
                	{
                		if(!created)
                		{
                			String error = "Unable to login user!";
            	    		Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
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
    
    public void onNewUser(View view){
    	
    	Intent intent = new Intent(this, NewUserActivity.class);
		startActivity(intent);
    	
    }

    private void gotoWait(){
    	Intent intent = new Intent(this, WaitingActivity.class);
        startActivity(intent);
    }
}
