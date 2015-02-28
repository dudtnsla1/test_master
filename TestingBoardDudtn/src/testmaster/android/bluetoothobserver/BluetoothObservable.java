package testmaster.android.bluetoothobserver;

import java.util.ArrayList;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import testmaster.android.bluetooth.ConnectManager;
import testmaster.android.packet.PacketInfo;
import testmaster.android.resource.DestroyDecorator;
import testmaster.android.resource.DestroyInterface;

public class BluetoothObservable implements DestroyInterface{

	private static ConnectManager bluetoothServer = null;
	private static ArrayList<BluetoothObserver> observerList = new ArrayList<BluetoothObserver>();
	private static BluetoothObservable This;
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

	public static void setBluetoothServer(ConnectManager conn) {
		if (conn == null) {
			disconnected();
		}
		bluetoothServer = conn;
	}

	public static ConnectManager getBluetoothServer() {
		return bluetoothServer;
	}

	public static int insertObserver(BluetoothObserver ob) {
		observerList.add(ob);
		Log.d("TestingBoard MaingFunctionActivity", 
				ob.getName() + " Observer Added");
		return 0;
	}

	public static void disconnected() {
		for (int i = 0; i < observerList.size(); i++)	{
			observerList.get(i).bluetoothDisconnect();
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

	public static void deleteObserver(BluetoothObserver ob) {
		for (int i = 0; i < observerList.size(); i++) {
			if (observerList.get(i).equals(ob)) {
				Log.d("TestingBoard MaingFunctionActivity",
						observerList.get(i).getName() + " Observer Delete");
				observerList.remove(i);
			}
		}
	}

	public static void insertDestroyDecorator() {
		if (This == null)
			This = new BluetoothObservable();
		DestroyDecorator.addDecorate(This);
	}

	@Override
	public void decoratingDestroy() {
		// TODO Auto-generated method stub
		observerList.clear();
		bluetoothServer = null;
		This = null;
		enableSwitch = true;
		Log.d("TestingBoard BluetoothObservable", "destroy (final decorator)");
	}
}