package com.team2052.frckrawler;

import java.text.DecimalFormat;
import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.Time;
import android.util.SparseArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team2052.frckrawler.database.DBContract;
import com.team2052.frckrawler.database.DBManager;
import com.team2052.frckrawler.database.structures.CompiledData;
import com.team2052.frckrawler.database.structures.List;
import com.team2052.frckrawler.database.structures.Metric;
import com.team2052.frckrawler.database.structures.MetricValue;
import com.team2052.frckrawler.database.structures.Query;
import com.team2052.frckrawler.database.structures.Robot;
import com.team2052.frckrawler.database.structures.SortKey;
import com.team2052.frckrawler.gui.MyTableRow;
import com.team2052.frckrawler.gui.MyTextView;
import com.team2052.frckrawler.gui.PopupMenuButton;
import com.team2052.frckrawler.gui.ProgressSpinner;
import com.team2052.frckrawler.gui.StaticTableLayout;

public class SummaryActivity extends StackableTabActivity implements OnClickListener {
	
	public static final int REQUEST_REFRESH = 1;
	public static final int REQUEST_NO_REFRESH = 2;
	
	private static SparseArray<Query[]> matchQuerys = new SparseArray<Query[]>();
	private static SparseArray<Query[]> pitQuerys = new SparseArray<Query[]>();
	private static SparseArray<Query[]> driverQuerys = new SparseArray<Query[]>();
	private static SortKey sortKey;
	
	private CompiledData[] data;
	private DBManager dbManager;
	private GetCompiledDataTask getDataTask;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_summary);
		findViewById(R.id.query).setOnClickListener(this);
		findViewById(R.id.lists).setOnClickListener(this);
		sortKey = null;
		dbManager = DBManager.getInstance(this);
		getDataTask = new GetCompiledDataTask();
		getDataTask.execute(this);
	}
	
	@Override
	public void onStop() {
		super.onStop();
		getDataTask.cancel(true);
	}
	
	@Override
	public void onClick(View v) {
		Intent i;
		switch(v.getId()) {
			case R.id.query:
				i = new Intent(this, QuerySortingDialogActivity.class);
				i.putExtra(QuerySortingDialogActivity.EVENT_ID_EXTRA, 
						databaseValues[getAddressOfDatabaseKey(DBContract.COL_EVENT_ID)]);
				startActivityForResult(i, 1);
				break;
				
			case R.id.lists:
				i = new Intent(this, ListsActivity.class);
				i.putExtra(ListsActivity.EVENT_ID_EXTRA, 
						Integer.parseInt(databaseValues
								[getAddressOfDatabaseKey(DBContract.COL_EVENT_ID)]));
				startActivity(i);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == REQUEST_REFRESH)
			new GetCompiledDataTask().execute(this);
	}
	
	public static void setQuery(int eventID, Query[] match, Query[] pit, 
			Query[] driver) {
		matchQuerys.put(eventID, match);
		pitQuerys.put(eventID, pit);
		driverQuerys.put(eventID, driver);
	}
	
	public static Query[] getMatchQuerys(int eventID) {
		return matchQuerys.get(eventID);
	}
	
	public static Query[] getPitQuerys(int eventID) {
		return pitQuerys.get(eventID);
	}
	
	public static Query[] getDriverQuerys(int eventID) {
		return driverQuerys.get(eventID);
	}
	
	public static void setSortKey(SortKey key) {
		sortKey = key;
	}
	
	public static SortKey getSortKey() {
		return sortKey;
	}
	
	private class GetCompiledDataTask extends AsyncTask
										<SummaryActivity, View, Void> {
		private volatile boolean readyForUIUpdate;
		private int dataNum;
		private StaticTableLayout table;
		private Time start;
											
		@Override
		protected void onPreExecute() {
			readyForUIUpdate = true;
			dataNum = 0;
			((FrameLayout)findViewById(R.id.progressFrame)).
					addView(new ProgressSpinner(getApplicationContext()));
			
			table = (StaticTableLayout)findViewById(R.id.adminSummaryTable);
			table.removeAllViews();
		}

		@Override
		protected Void doInBackground(SummaryActivity... params) {
			start = new Time();
			start.setToNow();
			SummaryActivity activity = params[0];
			
			ArrayList<Query> allQuerys = new ArrayList<Query>();
			Query[] matchQuerys = getMatchQuerys(Integer.parseInt
					(databaseValues[getAddressOfDatabaseKey
					                (DBContract.COL_EVENT_ID)]));
			Query[] pitQuerys = getPitQuerys(Integer.parseInt
					(databaseValues[getAddressOfDatabaseKey
					                (DBContract.COL_EVENT_ID)]));
			Query[] driverQuerys = getDriverQuerys(Integer.parseInt
					(databaseValues[getAddressOfDatabaseKey
					                (DBContract.COL_EVENT_ID)]));
			
			if(matchQuerys != null)
				for(Query q : matchQuerys)
					allQuerys.add(q);
			if(pitQuerys != null)
				for(Query q : pitQuerys)
					allQuerys.add(q);
			if(driverQuerys != null)
				for(Query q : driverQuerys)
					allQuerys.add(q);
					
			Query[] querys = allQuerys.toArray(new Query[0]);
			
			if(querys.length == 0) {
				data = dbManager.getCompiledEventData
					(Integer.parseInt(databaseValues[
					getAddressOfDatabaseKey(DBContract.COL_EVENT_ID)]), 
					new Query[0], sortKey);
				
			} else {
				try {
					data = dbManager.getCompiledEventData
							(Integer.parseInt(databaseValues[
							getAddressOfDatabaseKey(DBContract.COL_EVENT_ID)]), 
							querys, sortKey);
					
				} catch(NullPointerException e) {
					data = dbManager.getCompiledEventData
							(Integer.parseInt(databaseValues[
							getAddressOfDatabaseKey(DBContract.COL_EVENT_ID)]), 
							new Query[0], sortKey);
				}
			}
			
			MyTableRow staticDescriptorsRow = new MyTableRow(activity);
			MyTableRow descriptorsRow = new MyTableRow(activity);
			staticDescriptorsRow.addView(new MyTextView(activity, " ", 18));
			staticDescriptorsRow.addView(new MyTextView(activity, " ", 18));
			staticDescriptorsRow.addView(new MyTextView(activity, "Team", 18));
			descriptorsRow.addView(new MyTextView(activity, "M. Scouted", 18));
			descriptorsRow.addView(new MyTextView(activity, "OPR", 18));
			MetricValue[] matchMetrics;
			Metric[] robotMetrics;
			MetricValue[] driverMetrics;
			if(data.length > 0) {
				matchMetrics = data[0].getCompiledMatchData();
				robotMetrics = data[0].getRobot().getMetrics();
				driverMetrics = data[0].getCompiledDriverData();
			} else {
				matchMetrics = new MetricValue[0];
				robotMetrics = new Metric[0];
				driverMetrics = new MetricValue[0];
			}
			
			//Add all the metric names to the descriptors row, but 
			//only if they are displayed.
			for(MetricValue m : matchMetrics)
				if(m.getMetric().isDisplayed())
					descriptorsRow.addView(new MyTextView(activity, 
						m.getMetric().getMetricName(), 18));
			for(Metric m : robotMetrics)
				if(m.isDisplayed())
					descriptorsRow.addView(new MyTextView(activity, 
							m.getMetricName(), 18));
			for(MetricValue m : driverMetrics)
				if(m.getMetric().isDisplayed())
					descriptorsRow.addView(new MyTextView(activity, 
						m.getMetric().getMetricName(), 18));
			descriptorsRow.setLayoutParams(new TableLayout.LayoutParams());
			publishProgress(staticDescriptorsRow, descriptorsRow);
			
			//Create a new row for each piece of data
			for(int dataCount = 0; dataCount < data.length; dataCount++) {
				if(isCancelled()) {
					break;
				}
				
				int color;
				if(dataCount % 2 == 0) {
					color = GlobalValues.ROW_COLOR;
				} else {
					color = Color.TRANSPARENT;
				}
				
				MyTableRow staticRow = new MyTableRow(activity, color);
				MyTableRow dataRow = new MyTableRow(activity, color);
				
				PopupMenuButton menu = new PopupMenuButton(SummaryActivity.this);
				final Robot robot = data[dataCount].getRobot();
				final String[] matchComments = data[dataCount].getMatchComments();
				final int[] matchesPlayed = data[dataCount].getMatchesPlayed();
				menu.addItem("Match Data", new Runnable() {
					@Override
					public void run() {
						Intent i = new Intent(SummaryActivity.this, RawMatchDataActivity.class);
						i.putExtra(PARENTS_EXTRA, new String[] {});
						i.putExtra(DB_VALUES_EXTRA, new String[] {Integer.toString(robot.getID())});
						i.putExtra(DB_KEYS_EXTRA, new String[] {DBContract.COL_ROBOT_ID});
						i.putExtra(RawMatchDataActivity.DISABLE_BUTTONS_EXTRA, true);
						startActivity(i);
					}
				});
				menu.addItem("Match Comments", new Runnable() {
					@Override
					public void run() {
						Intent i = new Intent(SummaryActivity.this, CommentDialogActivity.class);
						i.putExtra(CommentDialogActivity.COMMENT_ARRAY_EXTRA, matchComments);
						i.putExtra(CommentDialogActivity.MATCHES_ARRAY_EXTRA, matchesPlayed);
						startActivity(i);
					}
				});
				menu.addItem("Picture", new Runnable() {
					@Override
					public void run() {
						Robot r = robot;
						String imagePath = r.getImagePath();
						AlertDialog.Builder builder = new AlertDialog.Builder(SummaryActivity.this);
						builder.setTitle("Team " + r.getTeamNumber() + "'s Robot");
						if(imagePath != null && !imagePath.equals("")) {
							ImageView image = new ImageView(SummaryActivity.this);
							image.setImageURI(Uri.parse(imagePath));
							builder.setView(image);
						} else {
							builder.setView(new MyTextView(SummaryActivity.this, "No image for this team.", 18));
						}
						builder.show();
					}
				});
				menu.addItem("Add to List", new Runnable() {
					@Override
					public void run() {
						final int robotID = robot.getID();
						final List[] lists = dbManager.getListsByColumns(
								new String[] {DBContract.COL_EVENT_ID}, 
								new String[] {(databaseValues[getAddressOfDatabaseKey
								                              (DBContract.COL_EVENT_ID)])});
						final CharSequence[] choices = new CharSequence[lists.length];
						
						for(int k = 0; k < lists.length; k++) {
							choices[k] = lists[k].getName();
						}
						
						AlertDialog.Builder builder = new AlertDialog.Builder(SummaryActivity.this);
						builder.setTitle("Add to List...");
						builder.setItems(choices, new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								if(!dbManager.addRobotToList(robotID, lists[which].getListID())) {
									Toast.makeText(SummaryActivity.this, "Robot is already in " +
											"that list.", Toast.LENGTH_SHORT).show();
								}
								
								dialog.dismiss();
							}
						});
						builder.show();
					}
				});
				staticRow.addView(menu);
				
				CheckBox checkBox = new CheckBox(activity);
				checkBox.setOnCheckedChangeListener(new CheckListener(
						data[dataCount].getEventID(), 
						data[dataCount].getRobot().getID()));
				checkBox.setChecked(data[dataCount].getRobot().isChecked());
				staticRow.addView(checkBox);
				
				double opr = data[dataCount].getRobot().getOPR();
				DecimalFormat oprFormat = new DecimalFormat("0.00");
				String oprString;
				if(opr == -1)
					oprString = "";
				else
					oprString = oprFormat.format(opr);
				
				staticRow.addView(new MyTextView(activity, Integer.toString(
						data[dataCount].getRobot().getTeamNumber()), 18));
				dataRow.addView(new MyTextView(activity, Integer.toString(
						data[dataCount].getMatchesPlayed().length), 18));
				dataRow.addView(new MyTextView(activity, oprString, 18));
				
				//Get the data arrays for the robot, matches, and driver data
				MetricValue[] matchData = data[dataCount].getCompiledMatchData();
				MetricValue[] robotData = data[dataCount].getRobot().
						getMetricValues();
				MetricValue[] driverData = data[dataCount].getCompiledDriverData();
				
				for(int i = 0; i < matchData.length; i++) {
					if(matchData[i].getMetric().isDisplayed()) {
						if(matchData[i].getMetric().getType() != DBContract.CHOOSER) {
							dataRow.addView(new MyTextView(activity, 
									matchData[i].getValueAsHumanReadableString(), 18));
							
						} else {
							boolean isNumeric = true;
							
							try {
								for(int choiceCount = 0; choiceCount < matchData[i].
										getMetric().getRange().length; 
										choiceCount++) {
									Double.parseDouble((String)matchData[i].getMetric().
											getRange()[choiceCount]);
								}
							} catch(NumberFormatException e) {
								isNumeric = false;
							}

							if(isNumeric) {
								dataRow.addView(new MyTextView(SummaryActivity.this, 
										matchData[i].getValueAsHumanReadableString(), 18));

							} else {
								int mostPickedAddress = 0;
								int mostPickedCounts = 0;

								for(int k = 0; k < matchData[i].getChooserCounts().length; k++) {
									if(matchData[i].getChooserCounts()[k] > mostPickedCounts) {
										mostPickedAddress = k;
										mostPickedCounts = matchData[i].getChooserCounts()[k];
									}
								}

								Button chooserButton = new Button(SummaryActivity.this);
								chooserButton.setCompoundDrawablesWithIntrinsicBounds
										(0, 0, R.drawable.btn_zoom_page_normal, 0);

								if(matchData[i].getValue().length > 0)
									chooserButton.setText(matchData[i]
											.getValue()[mostPickedAddress]);
								else
									chooserButton.setText("");

								chooserButton.setBackgroundColor(color);
								chooserButton.setTextColor(Color.LTGRAY);
								chooserButton.setTextSize(18);

								final MetricValue finalVal = matchData[i];
								final int teamNumber = data[dataCount].getRobot().getTeamNumber();
								chooserButton.setOnClickListener(new OnClickListener() {

									@Override
									public void onClick(View arg0) {
										AlertDialog.Builder builder = 
												new AlertDialog.Builder(SummaryActivity.this);
										builder.setTitle("Team " + 
												teamNumber + "'s " + 
												finalVal.getMetric().getMetricName() + " Data");

										LinearLayout builderView = new LinearLayout
												(SummaryActivity.this);
										builderView.setOrientation(LinearLayout.VERTICAL);
										for(int i = 0; i < finalVal.getValue().length; i++) {
											builderView.addView(new MyTextView(
													SummaryActivity.this,
													finalVal.getValue()[i],
													18));
										}

										builder.setView(builderView);
										builder.setNeutralButton("Close", 
												new DialogInterface.OnClickListener() {

											@Override
											public void onClick(DialogInterface dialog, int which) {
												dialog.dismiss();
											}
										});
										builder.show();
									}
								});
								dataRow.addView(chooserButton);
							}
						}
					}
				}
				
				for(int i = 0; i < robotData.length; i++)
					if(robotData[i].getMetric().isDisplayed())
						dataRow.addView(new MyTextView(activity, 
								robotData[i].getValueAsHumanReadableString(), 18));
				
				for(int i = 0; i < driverData.length; i++)
					if(driverData[i].getMetric().isDisplayed())
						dataRow.addView(new MyTextView(activity, 
								driverData[i].getValueAsHumanReadableString(), 18));
				
				while(!readyForUIUpdate) {
					try {	//Wait for the UI to update
						Thread.sleep(200);
					} catch(InterruptedException e) {}
				}
				
				publishProgress(staticRow, dataRow);
			}
			
			dataNum = data.length;
			return null;
		}
		
		@Override
		protected void onProgressUpdate(View... rows) {
			readyForUIUpdate = false;
			if(rows.length > 1) {
				table.addViewToStaticTable(rows[0]);
				table.addViewToMainTable(rows[1]);
				
			} else {
				table.addViewToMainTable(rows[0]);
			}
			readyForUIUpdate = true;
		}
		
		@Override
		protected void onPostExecute(Void v) {
			Time end = new Time();
			end.setToNow();
			System.out.println("Load Time: " + Long.toString(end.toMillis(true) - start.toMillis(false)));
			
			((TextView)findViewById(R.id.compiledNumber)).setText(dataNum + " Robots");
			((FrameLayout)findViewById(R.id.progressFrame)).removeAllViews();
		}
		
		@Override
		protected void onCancelled(Void v) {
			((FrameLayout)findViewById(R.id.progressFrame)).removeAllViews();
		}
	}
	
	private class CheckListener implements CompoundButton.OnCheckedChangeListener {
		
		private int eventID;
		private int robotID;
		
		public CheckListener(int _eventID, int _robotID) {
			eventID = _eventID;
			robotID = _robotID;
		}
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			dbManager.setRobotChecked(eventID, robotID, isChecked);
		}
	}
}
