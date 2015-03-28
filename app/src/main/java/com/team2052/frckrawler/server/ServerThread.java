package com.team2052.frckrawler.server;

import android.app.NotificationManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

import com.team2052.frckrawler.client.ScoutPackage;
import com.team2052.frckrawler.core.BluetoothInfo;
import com.team2052.frckrawler.core.FRCKrawler;
import com.team2052.frckrawler.core.database.DBManager;
import com.team2052.frckrawler.core.util.LogHelper;
import com.team2052.frckrawler.core.util.Utilities;
import com.team2052.frckrawler.db.Event;

import org.acra.ACRA;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

public class ServerThread extends Thread {

    private final BluetoothAdapter mBluetoothAdapter;
    public boolean isOpen;
    private DBManager mDbManager = null;
    private Context context;
    private Event hostedEvent;
    private BluetoothServerSocket serverSocket;

    public ServerThread(ServerService c, Event e) {
        isOpen = false;
        context = c.getApplicationContext();
        hostedEvent = e;
        serverSocket = null;
        mDbManager = ((FRCKrawler) c.getApplicationContext()).getDBSession();
        mBluetoothAdapter = Utilities.BluetoothUtil.getBluetoothAdapter();
    }

    @Override
    public void run() {
        String deviceName = "device";

        isOpen = true;
        while (isOpen) {
            try {
                serverSocket = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(BluetoothInfo.SERVICE_NAME, UUID.fromString(BluetoothInfo.UUID));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (serverSocket != null) {
                try {

                    BluetoothSocket clientSocket = serverSocket.accept();
                    deviceName = clientSocket.getRemoteDevice().getName();
                    //handler.onSyncStart(deviceName);
                    serverSocket.close();

                    ObjectOutputStream toScoutStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream fromScoutStream = new ObjectInputStream(clientSocket.getInputStream());
                    BluetoothInfo.ConnectionType connectionType = BluetoothInfo.ConnectionType.VALID_CONNECTION_TYPES[fromScoutStream.readInt()];


                    long startTime = System.currentTimeMillis();
                    LogHelper.info("Syncing");
                    switch (connectionType) {
                        case SCOUT_SYNC:
                            ServerPackage serverPackage = (ServerPackage) fromScoutStream.readObject();
                            toScoutStream.writeObject(new ScoutPackage(mDbManager, hostedEvent));
                            toScoutStream.flush();
                            clientSocket.close();
                            serverPackage.save(mDbManager);
                    }

                    //handler.onSyncSuccess(deviceName);
                    Log.d("FRCKrawler", "Synced in: " + (System.currentTimeMillis() - startTime) + "ms");
                } catch (IOException e) {
                    e.printStackTrace();
                    ACRA.getErrorReporter().handleException(e);
                    ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancel(ServerCallbackHandler.SYNC_ONGOING_ID);
                } catch (ClassNotFoundException e) {
                    ACRA.getErrorReporter().handleException(e);
                    e.printStackTrace();
                }
            }
        }
    }

    public void closeServer() {
        isOpen = false;
        if (serverSocket != null)
            try {
                serverSocket.close();
            } catch (IOException ignored) {
            }

    }

}
