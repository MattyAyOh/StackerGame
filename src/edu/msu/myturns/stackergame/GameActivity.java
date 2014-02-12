package edu.msu.myturns.stackergame;

import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.content.Intent;

public class GameActivity extends Activity {
	
	private StackView stackView;
	
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
	
    public void onTempScore(View view) {
		Intent intent = new Intent(this, ScoreActivity.class);
		startActivity(intent);
	}


}
