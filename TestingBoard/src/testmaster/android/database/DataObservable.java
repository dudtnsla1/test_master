package testmaster.android.database;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import testmaster.android.bluetooth.BluetoothConnectManager;
import testmaster.android.packet.PacketInfo;
import testmaster.android.resource.DestroyDecorator;
import testmaster.android.resource.DestroyInterface;

public class DataObservable implements DestroyInterface{

	private static BluetoothConnectManager bluetoothServer = null;
	private static ArrayList<DataObserver> observerList = new ArrayList<DataObserver>();
	private static DataObservable This;
	private static boolean enableSwitch = true;

	public static Handler messageReceiver = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			update((String)msg.obj);
		}
	};
	
	public static void resetData() {
		for (int i = 0; i < observerList.size(); i++)	{
			observerList.get(i).resetData();
		}		
	}
	
	public static void disableUpdate() {
		enableSwitch = false;
		Log.d("TestingBoard BluetoothObservable", "bluetooth disable");
	}
	
	public static void enableUpate() {
		enableSwitch = true;
		Log.d("TestingBoard BluetoothObservable", "bluetooth enable");
	}

	public static void setBluetoothServer(BluetoothConnectManager conn) {
		if (conn == null) {
			disconnected();
		}
		bluetoothServer = conn;
	}

	public static BluetoothConnectManager getBluetoothServer() {
		return bluetoothServer;
	}

	public static int insertObserver(DataObserver ob) {
		observerList.add(ob);
		Log.d("TestingBoard BluetoothObservable", 
				ob.getName() + " Observer Added");
		return 0;
	}

	public static void disconnected() {
		Log.d("TestingBoard MaingFunctionActivity", 
				"Bluetooth Disconnected");
		for (int i = 0; i < observerList.size(); i++)	{
			observerList.get(i).bluetoothDisconnect();
		}
		
		if (bluetoothServer != null) {
			bluetoothServer.disconnected();
		}
		
		bluetoothServer = null;
	}

	public static void update(String data) {		
		if (enableSwitch) {		
			for (int i = 0; i < observerList.size(); i++)	{
				observerList.get(i).update(data);
			}
		}
	}

	public static void deleteObserver(DataObserver ob) {
		for (int i = 0; i < observerList.size(); i++) {
			if (observerList.get(i).equals(ob)) {
				Log.d("TestingBoard BluetoothObservable",
						observerList.get(i).getName() + " Observer Delete");
				observerList.remove(i);
			}
		}
	}

	public static void insertDestroyDecorator() {
		if (This == null)
			This = new DataObservable();
		DestroyDecorator.addDecorate(This);
	}

	@Override
	public void decoratingDestroy() {
		// TODO Auto-generated method stub
		observerList.clear();
		disconnected();
		This = null;
		enableSwitch = true;
		Log.d("TestingBoard BluetoothObservable", "destroy (final decorator)");
	}
}