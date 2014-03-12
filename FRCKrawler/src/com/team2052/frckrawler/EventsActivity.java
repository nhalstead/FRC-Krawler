package com.team2052.frckrawler;

import java.util.Date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TableLayout;
import android.widget.TextView;

import com.team2052.frckrawler.database.DBContract;
import com.team2052.frckrawler.database.DBManager;
import com.team2052.frckrawler.database.structures.Event;
import com.team2052.frckrawler.database.structures.Robot;
import com.team2052.frckrawler.gui.MyButton;
import com.team2052.frckrawler.gui.MyTableRow;
import com.team2052.frckrawler.gui.MyTextView;
import com.team2052.frckrawler.gui.PopupMenuButton;
import com.team2052.frckrawler.gui.ProgressSpinner;

public class EventsActivity extends StackableTabActivity implements OnClickListener {
	
	private static final int EDIT_EVENT_ID = 1;
	private static final int ATTENDING_TEAMS_ID = 2;
	private static final int ROBOTS_ID = 3;
	private static final int COMP_DATA_ID = 4;
	private static final int DRIVER_DATA_ID = 5;
	private static final int COMPILED_DATA_ID = 6;
	private static final int LISTS_ID = 7;
	private static final int IMPORT_ID = 8;
	
	private DBManager dbManager;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_events);
		
		try {
			String value = 
					databaseValues[getAddressOfDatabaseKey(DBContract.COL_GAME_NAME)];
			findViewById(R.id.addEventButton).setOnClickListener(this);
		} catch(ArrayIndexOutOfBoundsException e) {
			findViewById(R.id.addEventButton).setEnabled(false);
		}
		
		dbManager = DBManager.getInstance(this);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		new GetEventsTask().execute();
	}
	
	public void postResults(Event[] events) {
		TableLayout table = (TableLayout)findViewById(R.id.eventsDataTable);
		table.removeAllViews();
		
		MyTableRow descriptorsRow = new MyTableRow(this);
		descriptorsRow.addView(new MyTextView(this, " ", 18));
		descriptorsRow.addView(new MyTextView(this, " ", 18));
		descriptorsRow.addView(new MyTextView(this, "Name", 18));
		descriptorsRow.addView(new MyTextView(this, "Location", 18));
		descriptorsRow.addView(new MyTextView(this, "Game", 18));
		descriptorsRow.addView(new MyTextView(this, "Date", 18));
		table.addView(descriptorsRow);
		
		for(int i = 0; i < events.length; i++) {
			int color;
			if(i % 2 == 0)
				color = GlobalValues.ROW_COLOR;
			else
				color = Color.TRANSPARENT;
			//Create the popup menu
			PopupMenuButton menu = new PopupMenuButton(this);
			final String eventID = Integer.toString(events[i].getEventID());
			menu.addItem("Summary", new Runnable(){
				@Override
				public void run() {
					Intent i = new Intent(EventsActivity.this, SummaryActivity.class);
					i.putExtra(PARENTS_EXTRA, new String[] {});
					i.putExtra(DB_VALUES_EXTRA, new String[] {eventID});
					i.putExtra(DB_KEYS_EXTRA, new String[] {DBContract.COL_EVENT_ID});
					startActivity(i);
				}
			});
			menu.addItem("Match Data", new Runnable(){
				@Override
				public void run() {
					Intent i = new Intent(EventsActivity.this, RawMatchDataActivity.class);
					i.putExtra(PARENTS_EXTRA, new String[] {});
					i.putExtra(DB_VALUES_EXTRA, new String[] {eventID});
					i.putExtra(DB_KEYS_EXTRA, new String[] {DBContract.COL_EVENT_ID});
					i.putExtra(RawMatchDataActivity.LIMIT_LOADING_EXTRA, true);
					startActivity(i);
				}
			});
			menu.addItem("Robots", new Runnable(){
				@Override
				public void run() {
					new StartRobotsActivityTask()
						.execute(Integer.parseInt(eventID));
				}
			});
			menu.addItem("Attending Teams", new Runnable(){
				@Override
				public void run() {
					Intent i = new Intent(EventsActivity.this, 
							AttendingTeamsDialogActivity.class);
					i.putExtra(AttendingTeamsDialogActivity.GAME_NAME_EXTRA, 
							databaseValues[getAddressOfDatabaseKey(DBContract.COL_GAME_NAME)]);
					i.putExtra(AttendingTeamsDialogActivity.EVENT_ID_EXTRA, eventID);
					startActivity(i);
				}
			});
			menu.addItem("Lists", new Runnable(){
				@Override
				public void run() {
					Intent i = new Intent(EventsActivity.this, ListsActivity.class);
					i.putExtra(ListsActivity.EVENT_ID_EXTRA, Integer.parseInt(eventID));
					startActivity(i);
				}
			});
			menu.addItem("Import", new Runnable(){
				@Override
				public void run() {
					ConnectivityManager connMgr = (ConnectivityManager) 
							getSystemService(Context.CONNECTIVITY_SERVICE);
					NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
					
					if (networkInfo != null && networkInfo.isConnected()) {
						Intent i = new Intent(EventsActivity.this, ImportDialogActivity.class);
						i.putExtra(ImportDialogActivity.EVENT_ID_EXTRA, 
								Integer.parseInt(eventID));
						startActivity(i);
					} else {
						AlertDialog.Builder b = new AlertDialog.Builder(EventsActivity.this);
						b.setMessage("You must have internet connection to import event data " +
								"and OPR from the web.");
						b.setNeutralButton("Close", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						b.show();
					}
				}
			});
			//Create the edit button
			MyButton editEventButton = new MyButton(this, "Edit", this, 
					Integer.valueOf(events[i].getEventID()));
			editEventButton.setId(EDIT_EVENT_ID);
			//Set up the datestamp format
			Date dateStamp = events[i].getDateStamp();
			String dateString = " " + dateStamp.getMonth() + "/" + dateStamp.getDay() + 
					"/" +(dateStamp.getYear() + 1900);
			//Add everything to the table
			table.addView(new MyTableRow(this, new View[] {
					menu,
					editEventButton,
					new MyTextView(this, events[i].getEventName(), 18),
					new MyTextView(this, events[i].getLocation(), 18),
					new MyTextView(this, events[i].getGameName(), 18),
					new MyTextView(this, dateString, 18)
			}, color));
		}
	}
	
	@Override
	public void onClick(View v) {
		Intent i;
		switch(v.getId()) {
			case R.id.addEventButton:
				if(parents[getAddressOfDatabaseKey(DBContract.COL_GAME_NAME)] != null) {
					
					i = new Intent(this, AddEventDialogActivity.class);
					i.putExtra(AddEventDialogActivity.GAME_NAME_EXTRA,
							parents[getAddressOfDatabaseKey(DBContract.COL_GAME_NAME)]);
					startActivity(i);
				}
				break;
				
			case EDIT_EVENT_ID:
				i = new Intent(this, EditEventDialogActivity.class);
				i.putExtra(EditEventDialogActivity.EVENT_ID_EXTRA, v.getTag().toString());
				startActivity(i);
		}
	}
	
	private class GetEventsTask extends AsyncTask<Void, Void, Event[]> {
		private int eventNum = 0;
		
		@Override
		protected Event[] doInBackground(Void... params) {
			Event[] e = dbManager.getEventsByColumns(databaseKeys, databaseValues);
			eventNum = e.length;
			return e;
		}
		
		@Override
		protected void onPostExecute(Event[] events) {
			((TextView)findViewById(R.id.eventNum)).setText(eventNum + " Events");
			postResults(events);
		}
	}
	
	private class StartRobotsActivityTask extends AsyncTask<Integer, Void, Intent> {
		private AlertDialog progressDialog;
		
		@Override
		protected void onPreExecute() {
			AlertDialog.Builder builder = new AlertDialog.Builder(EventsActivity.this);
			builder.setTitle("Loading...");
			builder.setView(new ProgressSpinner(EventsActivity.this));
			builder.setCancelable(false);
			progressDialog = builder.create();
			progressDialog.show();
		}

		@Override
		protected Intent doInBackground(Integer... params) {
			Robot[] attendingRobots = dbManager.getRobotsAtEvent
					(params[0]);
			String[] dbValsArr = new String[attendingRobots.length];
			String[] dbKeysArr = new String[attendingRobots.length];

			for(int k = 0; k < attendingRobots.length; k ++) {
				dbValsArr[k] = Integer.toString(attendingRobots[k].getID());
				dbKeysArr[k] = DBContract.COL_ROBOT_ID;
			}
			
			Intent i = new Intent(EventsActivity.this, RobotsActivity.class);
			i.putExtra(DB_VALUES_EXTRA, dbValsArr);
			i.putExtra(DB_KEYS_EXTRA, dbKeysArr);
			return i;
		}
		
		@Override
		protected void onPostExecute(Intent i) {
			progressDialog.dismiss();
			startActivity(i);
		}
	}
}
