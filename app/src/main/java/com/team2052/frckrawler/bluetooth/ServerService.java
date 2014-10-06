package com.team2052.frckrawler.bluetooth;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.team2052.frckrawler.FRCKrawler;
import com.team2052.frckrawler.R;

import frckrawler.Event;

public class ServerService extends Service
{
    public static int SERVER_OPEN_ID = 10;
    public static String EVENT_ID = "EVENT_ID";
    private ServerThread thread;
    private PendingIntent openServerIntent;

    public Intent newInstance(Context context, Event hostedEvent)
    {
        Intent i = new Intent(context, ServerService.class);
        i.putExtra(EVENT_ID, hostedEvent.getId());
        return i;
    }

    @Override
    public void onCreate()
    {
        thread = null;
    }

    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;    //Do not allow binding
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (thread == null) {
            Event e = ((FRCKrawler)getApplication()).getDaoSession().getEventDao().load(intent.getLongExtra(EVENT_ID, 0));
            NotificationCompat.Builder b = new NotificationCompat.Builder(this);
            b.setSmallIcon(R.drawable.ic_stat_knightkrawler);
            b.setContentTitle("Server open");
            b.setContentText("The FRCKrawler server is open for scouts to sync");
            b.setOngoing(true);
            NotificationManager m = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            m.notify(SERVER_OPEN_ID, b.build());
            thread = new ServerThread(this, e, new ServerCallbackHandler(this));
            new Thread(thread).start();
        }
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy()
    {
        thread.closeServer();
    }
}
