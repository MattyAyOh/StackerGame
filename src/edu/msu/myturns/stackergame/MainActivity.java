package edu.msu.myturns.stackergame;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
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
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		if(pref.contains("Username") && pref.contains("Password"))
		{
			String uname;
			String upass;
			uname = pref.getString("Username", "");
			upass = pref.getString("Password", "");
			EditText username = (EditText)findViewById(R.id.editText1);
	    	username.setText(uname);
	    	EditText password = (EditText)findViewById(R.id.editText2);
	    	password.setText(upass);
		}
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
        alert.setMessage("Login to the game with your username and password.\n"
        		+ "You can create a new username with the designated button.\n"
        		+ "Choose the position of the brick by dragging it side.\n"
        		+ "You can then select a weight and the brick will be placed"
        		+ " at it's current position.\nThen wait for the other player to "
        		+ "make their play and repeat the process.\nAt any point if a player places a brick"
        		+ "that causes the stack to topple, that player will lose the round."
        		+ "The first player to win 5 rounds wins the game.  Good luck!");

        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {}
        });
        alert.show();
    }
    
    public void onLogin(View view){
    	CheckBox remember = (CheckBox)findViewById(R.id.checkBoxRemember);
    	
    	EditText username = (EditText)findViewById(R.id.editText1);
    	final String user = username.getText().toString();
    	EditText password = (EditText)findViewById(R.id.editText2);
    	final String pass = password.getText().toString();
    	
    	if(remember.isChecked())
    	{
    		//save the values for later logins
    		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
    		SharedPreferences.Editor editor = pref.edit();
    		editor.putString("Username", user);
    		editor.putString("Password", pass);
    		editor.commit();
    	}
    	else
    	{
    		//clear remember logins
    		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
    		SharedPreferences.Editor editor = pref.edit();
    		editor.putString("Username", "");
    		editor.putString("Password", "");
    		editor.commit();
    	}
    	
    	//save values for getting game
    	SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = pref.edit();
		editor.putString("CUsername", user);
		editor.putString("CPassword", pass);
		editor.commit();

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
                			gotoGame();
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

    private void gotoGame(){
    	Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
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
