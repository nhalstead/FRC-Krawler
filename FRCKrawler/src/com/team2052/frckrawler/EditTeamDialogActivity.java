package com.team2052.frckrawler;

/*****
 * Class: EditTeamDialogActivity
 * 
 * 
 *****/

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.example.frckrawler.R;
import com.team2052.frckrawler.database.DatabaseContract;
import com.team2052.frckrawler.database.DatabaseManager;
import com.team2052.frckrawler.database.structures.Team;
import com.team2052.frckrawler.gui.MyTextView;

public class EditTeamDialogActivity extends Activity implements OnClickListener {
	
	public static final String TEAM_NUMBER_EXTRA_KEY = "com.team2052.frckrawler.editTeamNumber";
	
	private DatabaseManager dbManager;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogactivity_edit_team);
		
		((Button)findViewById(R.id.saveButton)).setOnClickListener(this);
		((Button)findViewById(R.id.cancel)).setOnClickListener(this);
		
		dbManager = DatabaseManager.getInstance(this);
	}
	
	public void onResume() {
		
		super.onResume();
		
		Team t;
		Team[] arr = (dbManager.getTeamsByColumns(new String[] {DatabaseContract.COL_TEAM_NUMBER}, 
				new String[] {Integer.toString(getIntent().getIntExtra(TEAM_NUMBER_EXTRA_KEY, -1))}));
		
		if(arr.length > 0)
			t = arr[0];
		else
			return;
		
		Spinner stateSpinner = (Spinner)findViewById(R.id.stateVal);
		int stateSelection = ((ArrayAdapter<String>)stateSpinner.getAdapter()).
				getPosition(t.getStatePostalCode());
		
		((TextView)findViewById(R.id.numberVal)).setText(Integer.toString(t.getNumber()));
		((EditText)findViewById(R.id.nameVal)).setText(t.getName());
		((EditText)findViewById(R.id.schoolVal)).setText(t.getSchool());
		((EditText)findViewById(R.id.cityVal)).setText(t.getCity());
		((EditText)findViewById(R.id.rookieYearVal)).setText(t.getRookieYear());
		((EditText)findViewById(R.id.websiteVal)).setText(t.getWebsite());
		stateSpinner.setSelection(stateSelection);
		((EditText)findViewById(R.id.colorsVal)).setText(t.getColors());
	}

	
	/*****
	 * Method: onClick
	 * 
	 * Summary: Called when the user presses a button. The View
	 * is the button that the user pressed.
	 *****/
	
	public void onClick(View v) {
		
		if(v.getId() == R.id.saveButton) {
			
			String rookieYear = ((TextView)findViewById(R.id.rookieYearVal)).getText().toString();
			
			if(rookieYear.equals("0"))
				rookieYear = "";
			
			String[] queryVals = new String[] {((TextView)findViewById(R.id.numberVal)).
					getText().toString()};
			String[] updateCols = new String[] {
					DatabaseContract.COL_TEAM_NAME,
					DatabaseContract.COL_SCHOOL,
					DatabaseContract.COL_CITY,
					DatabaseContract.COL_ROOKIE_YEAR,
					DatabaseContract.COL_WEBSITE,
					DatabaseContract.COL_STATE_POSTAL_CODE,
					DatabaseContract.COL_COLORS
			};
			
			String[] updateVals = new String[] {
					((TextView)findViewById(R.id.nameVal)).getText().toString(), 
					((TextView)findViewById(R.id.schoolVal)).getText().toString(), 
					((TextView)findViewById(R.id.cityVal)).getText().toString(), 
					rookieYear, 
					((TextView)findViewById(R.id.websiteVal)).getText().toString(), 
					((Spinner)findViewById(R.id.stateVal)).getSelectedItem().toString(), 
					((TextView)findViewById(R.id.colorsVal)).getText().toString()
			};
			
			dbManager.updateTeams(new String[] {DatabaseContract.COL_TEAM_NUMBER}, 
					queryVals, updateCols, updateVals);
			
			finish();
			
		} else if(v.getId() == R.id.cancel) {
			
			finish();
		}
	}
}
