package edu.msu.myturns.stackergame;

import java.util.ArrayList;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;


public class WaitingActivity extends Activity {
	
	private ArrayList<Web.Move> moves;
	private String uname;
	private String upass;
	private boolean wait;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_waiting);
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
		uname = preferences.getString("CUsername", "");
    	upass = preferences.getString("CPassword", "");
    	wait = true;
		run();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void run(){
		while(wait)
		{
		new Thread(new Runnable() {

            @Override
            public void run() {
                // Create a cloud object and get the XML
                Web cloud = new Web();
                moves = cloud.getGame(uname, upass);
                wait = check(moves);
                
            }
    	}).start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	private boolean check(ArrayList<Web.Move> moves){
		if(moves.get(moves.size()).username != uname)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
        case R.id.menu_exit:
            //kill thread
        	finish();
            return true;
        }
		return super.onOptionsItemSelected(item);
	}

}
