package edu.msu.myturns.stackergame;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends Activity {
	
	//private String pass;
	//private String user;
	private boolean created;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
		return true;
	}

	
	public void onCreateUser(View view){
		EditText username = (EditText)findViewById(R.id.editText1);
    	final String user = username.getText().toString();
    	EditText password = (EditText)findViewById(R.id.editText2);
    	final String pass = password.getText().toString();
    	EditText passwordvar = (EditText)findViewById(R.id.editText3);
    	String passvar = passwordvar.getText().toString();
    	boolean same = pass.equals(passvar);
    	if(same)
    	{
    		//create the user
    		//connect to the data base
        	
	    	new Thread(new Runnable() {
	
	            @Override
	            public void run() {
	                // Create a cloud object and get the XML
	                Web cloud = new Web();
	                created = cloud.createUser(user, pass);
	                
	                runOnUiThread(new Runnable(){
	                	@Override
	                	public void run()
	                	{
	                		if(!created)
	                		{
	                			String error = "Unable to create user!";
	            	    		Toast.makeText(NewUserActivity.this, error, Toast.LENGTH_SHORT).show();
	                		}
	                	}
	                });
	            }
	    	}).start();
	    	
	    	
	    	finish();
    	}
		else
		{
			String error = "Passwords don't match!";
    		Toast.makeText(view.getContext(), error, Toast.LENGTH_SHORT).show();
		}
	}
}
