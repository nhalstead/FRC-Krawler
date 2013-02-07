package com.team2052.frckrawler;

/*****
 * Class: EditTeamDialogActivity
 * 
 * 
 *****/

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

import com.example.frckrawler.R;
import com.team2052.frckrawler.database.DBContract;
import com.team2052.frckrawler.database.DBManager;
import com.team2052.frckrawler.database.structures.Team;
import com.team2052.frckrawler.gui.MyTextView;

public class EditTeamDialogActivity extends Activity implements OnClickListener, DialogInterface.OnClickListener {
	
	public static final String TEAM_NUMBER_EXTRA_KEY = "com.team2052.frckrawler.editTeamNumber";
	
	private DBManager dbManager;
	
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogactivity_edit_team);
		
		((Button)findViewById(R.id.saveButton)).setOnClickListener(this);
		((Button)findViewById(R.id.remove)).setOnClickListener(this);
		((Button)findViewById(R.id.cancel)).setOnClickListener(this);
		
		dbManager = DBManager.getInstance(this);
	}
	
	public void onResume() {
		
		super.onResume();
		
		Team t;
		Team[] arr = (dbManager.getTeamsByColumns(new String[] {DBContract.COL_TEAM_NUMBER}, 
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
			
			if(rookieYear.equals("0") || rookieYear.equals("-1"))
				rookieYear = "";
			
			String[] queryVals = new String[] {((TextView)findViewById(R.id.numberVal)).
					getText().toString()};
			String[] updateCols = new String[] {
					DBContract.COL_TEAM_NAME,
					DBContract.COL_SCHOOL,
					DBContract.COL_CITY,
					DBContract.COL_ROOKIE_YEAR,
					DBContract.COL_WEBSITE,
					DBContract.COL_STATE_POSTAL_CODE,
					DBContract.COL_COLORS
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
			
			dbManager.updateTeams(new String[] {DBContract.COL_TEAM_NUMBER}, 
					queryVals, updateCols, updateVals);
			
			finish();
			
		} else if(v.getId() == R.id.cancel) {
			
			finish();
			
		} else if(v.getId() == R.id.remove) {
			
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			
			builder.setMessage("Are you sure you want to remove this team from the database? " +
					"This will remove all robots, contacts, and match data they have from the database and " +
					"they will be cast into the cold void of cyberspace for eternity.");
			builder.setTitle("");
			builder.setPositiveButton("Yes", this);
			builder.setNegativeButton("No", this);
			
			builder.show();
		}
	}
	
	
	/*****
	 * Method: onClick
	 * 
	 * @param dialog
	 * @param which
	 * 
	 * Summary: This method is the callback for the AlertDialog
	 * that pops up when the user wants to delete a team. It 
	 * does nothing, or removes the team, depending on what
	 * the user wants.
	 *****/
	
	public void onClick(DialogInterface dialog, int which) {
		
		if(which == DialogInterface.BUTTON_POSITIVE) {
			
			dbManager.removeTeam(getIntent().getIntExtra(TEAM_NUMBER_EXTRA_KEY, -1));
			dialog.dismiss();
			finish();
			
		} else {
			
			dialog.dismiss();
		}
		
	}
}
