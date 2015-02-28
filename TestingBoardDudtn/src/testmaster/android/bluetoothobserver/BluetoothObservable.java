package testmaster.android.bluetoothobserver;

import java.util.ArrayList;

import android.util.Log;
import testmaster.android.bluetooth.ConnectManager;
import testmaster.android.resource.DestroyDecorator;
import testmaster.android.resource.DestroyInterface;

public class BluetoothObservable implements DestroyInterface{
	
	private static ConnectManager bluetoothServer = null;
	private static ArrayList<BluetoothObserver> observerList = new ArrayList<BluetoothObserver>();
	private static BluetoothObservable This; 
	
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
		return 0;
	}
	
	public static void disconnected() {
		for (int i = 0; i < observerList.size(); i++)	{
			observerList.get(i).bluetoothDisconnect();
		}
		bluetoothServer = null;
	}
	
	public static void update(String data) {

		for (int i = 0; i < observerList.size(); i++)	{
			observerList.get(i).update(data);
		}
	}
	
	public static void deleteObserver(BluetoothObserver ob) {
		for (int i = 0; i < observerList.size(); i++) {
			if (observerList.get(i).equals(ob))
				observerList.remove(i);
		}
	}
	
	public static void insertDestroyDecorator() {
		if (This == null)
			This = new BluetoothObservable();
		DestroyDecorator.addThreadDecorate(This);
	}

	@Override
	public void decoratingDestroy() {
		// TODO Auto-generated method stub
		observerList.clear();
		bluetoothServer = null;
		This = null;
		Log.d("TestingBoard BluetoothObservable", "destroy (final decorator)");
	}
}