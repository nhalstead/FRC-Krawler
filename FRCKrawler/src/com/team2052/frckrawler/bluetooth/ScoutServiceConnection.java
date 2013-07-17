package com.team2052.frckrawler.bluetooth;

import com.team2052.frckrawler.bluetooth.BluetoothScoutClientService.ClientBinder;

import android.content.ComponentName;
import android.os.IBinder;

public class ScoutServiceConnection implements ClientConnection {
	
	private ClientThreadListener listener;
	private ClientBinder binder;
	
	private ScoutServiceConnection() {}
	
	public ScoutServiceConnection(ClientThreadListener _listener) {
		listener = _listener;
	}

	@Override
	public void onServiceConnected(ComponentName c, IBinder i) {
		binder = (ClientBinder)i;
		binder.setListener(listener);
	}

	@Override
	public void onServiceDisconnected(ComponentName i) {
		closeBTConnection();
	}
	
	@Override
	public void closeBTConnection() {
		if(binder != null)
			binder.closeConnection();
	}
}
