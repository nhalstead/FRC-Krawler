package com.team2052.frckrawler;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.team2052.frckrawler.database.DatabaseHelper;
import com.team2052.frckrawler.db.DaoMaster;
import com.team2052.frckrawler.db.DaoSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/*
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;
import org.acra.sender.HttpSender;
*/

/**
 * @author Adam
 */
/*@ReportsCrashes(
        formKey = "",
        formUri = "http://adam8234.cloudant.com/acra-frc-krawler/_design/acra-storage/_update/report",
        reportType = HttpSender.Type.JSON,
        httpMethod = HttpSender.Method.PUT,
        formUriBasicAuthLogin = "ffeadaystranceforickephi",
        formUriBasicAuthPassword = "wcNwfC8R8wUeaX2Rf38DUxi3",
        mode = ReportingInteractionMode.DIALOG,
        resDialogText = R.string.dialog_error_report,
        resDialogCommentPrompt = R.string.dialog_error_report_comment,
        resDialogIcon = R.drawable.ic_launcher,
        resDialogEmailPrompt = R.string.dialog_error_report_email,
        resDialogTitle = R.string.dialog_error_report_title)*/
public class FRCKrawler extends Application {

    private DaoSession daoSession;
    private DaoMaster daoMaster;

    public static DaoSession getDaoSession(Context context) {
        return ((FRCKrawler) context.getApplicationContext()).getDaoSession();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setupDB();
        //ACRA.init(this);
    }

    private void setupDB() {
        DatabaseHelper helper = new DatabaseHelper(this, "FRCKrawler", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public File copyDB(File newPath) throws Exception {
        File currentDB = new File(daoMaster.getDatabase().getPath());
        if (currentDB.exists()) {
            FileOutputStream fileOutputStream = new FileOutputStream(newPath);
            FileInputStream fileInputStream = new FileInputStream(currentDB);
            FileChannel src = fileInputStream.getChannel();
            FileChannel dst = fileOutputStream.getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            fileOutputStream.close();
            fileInputStream.close();
        }
        return newPath;
    }

}
