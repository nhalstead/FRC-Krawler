package com.team2052.frckrawler.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*****
 * Class: DatabaseHelper
 * 
 * Summary: This extends the SQLiteHelper class. It handles the database on a lower level
 * than the DatabaseManager and should only be used by the DatabaseManager. Methods that
 * make changes to the database should be executed on a different thread than the
 * DatabaseManager's thread.
 *****/

/*
 * WARNING: When onUpgrade or onDowngrade is called, the database is wiped clean and rebuilt, this
 * should change in future implementation and is only done for development purposes.
 */

public class DBHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 32;	//You must add one when changing the 
		//structure of the database.
	
	public DBHelper(Context context, String path) {
		super(context, path, null, DATABASE_VERSION);
	}
	
	/*****
	 * Method: onCreate
	 * 
	 * Summary: Called only if there is no database already associated with this app.
	 * This is where you should create tables and set up the database.
	 *****/
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		DBContract.createSchema(database);
	}
	
	/*****
	 * Method: onUpgrade
	 * 
	 * Summary: Called when the app itself is updated to a newer version.
	 *****/
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if(newVersion == 28) {
			if(oldVersion < 27) {
				db.execSQL("ALTER TABLE " + DBContract.TABLE_ROBOTS + 
						" ADD COLUMN " + DBContract.COL_OPR);
				db.execSQL("ALTER TABLE " + DBContract.SCOUT_TABLE_ROBOTS + 
						" ADD COLUMN " + DBContract.COL_OPR);
			}
		}
		if(oldVersion < 29) {
			db.execSQL(DBContract.CREATE_TABLE_SCHEDULES);
		}
		if(oldVersion < 30) {
			db.execSQL(DBContract.CREATE_SCOUT_TABLE_SCHEDULE);
		}
		if(oldVersion < 31) {
			db.execSQL("DROP TABLE IF EXISTS summaryevents");
			db.execSQL("DROP TABLE IF EXISTS summarymatchdata");
			db.execSQL("DROP TABLE IF EXISTS summaryrobotdata");
			db.execSQL("DROP TABLE IF EXISTS summarytablematchesplayed");
			db.execSQL("DROP TABLE IF EXISTS summarycompiledmatchdata");
			db.execSQL("DROP TABLE IF EXISTS summarymatchmetrics");
			db.execSQL("DROP TABLE IF EXISTS summaryrobotmetrics");
		}
		if(oldVersion < 32) {
			db.execSQL(DBContract.CREATE_SUMMARY_TABLE_ROBOT_METRICS);
			db.execSQL(DBContract.CREATE_SUMMARY_TABLE_MATCH_PERF_METRICS);
			db.execSQL(DBContract.CREATE_SUMMARY_TABLE_MATCH_PERF);
			db.execSQL(DBContract.CREATE_SUMMARY_TABLE_SUMMARY_DATA);
		}
	}
	
	@Override
	public void onDowngrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		onUpgrade(database, oldVersion, newVersion);
	}
}
