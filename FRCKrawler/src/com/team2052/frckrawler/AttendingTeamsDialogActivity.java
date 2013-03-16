package com.team2052.frckrawler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.example.frckrawler.R;
import com.team2052.frckrawler.database.*;
import com.team2052.frckrawler.database.structures.Robot;

public class AttendingTeamsDialogActivity extends Activity implements OnClickListener {
	
	public static String GAME_NAME_EXTRA = "com.team2052.frckrawler.gameNameExtra";
	public static String EVENT_ID_EXTRA = "com.team2052.frckrawler.eventIDExtra";
	
	private DBManager dbManager;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogactivity_attending_teams);
		
		findViewById(R.id.save).setOnClickListener(this);
		findViewById(R.id.cancel).setOnClickListener(this);
		
		dbManager = DBManager.getInstance(this);
	}
	
	public void onResume() {
		
		super.onResume();
		
		LinearLayout robotList = (LinearLayout)findViewById(R.id.teamList);
		robotList.removeAllViews();
		
		Robot[] allRobots = dbManager.getRobotsByColumns(new String[] {DBContract.COL_GAME_NAME}, 
				new String[] {getIntent().getStringExtra(GAME_NAME_EXTRA)});
				
		Robot[] selectedRobots = dbManager.getRobotsAtEvent(
				Integer.parseInt(getIntent().getStringExtra(EVENT_ID_EXTRA)));
		
		for(Robot r : allRobots) {
			
			CheckBox checkBox = new CheckBox(this);
			checkBox.setId(r.getID());
			checkBox.setText(Integer.toString(r.getTeamNumber()));
			
			for(Robot sr : selectedRobots)
				if(r.getID() == sr.getID())
					checkBox.setChecked(true);
			
			robotList.addView(checkBox);
		}
	}

	public void onClick(View v) {
		
		if(v.getId() == R.id.save) {
			
			LinearLayout robotList = (LinearLayout)findViewById(R.id.teamList);
			
			for(int currentChild = 0; currentChild < robotList.getChildCount(); currentChild++) {
				
				CheckBox box = (CheckBox)robotList.getChildAt(currentChild);
				
				if(box.isChecked())
					dbManager.addRobotToEvent(
							Integer.parseInt(getIntent().getStringExtra(EVENT_ID_EXTRA)), 
							box.getId());
				else
					dbManager.removeRobotFromEvent(
							Integer.parseInt(getIntent().getStringExtra(EVENT_ID_EXTRA)), 
							box.getId());
				
				System.out.println(box.isChecked());
			}
			
			finish();
			
		} else if(v.getId() == R.id.cancel) {
			
			finish();
		}
	}

}
