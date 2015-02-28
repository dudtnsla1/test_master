package testmaster.android.bluetooth;

import java.io.IOException;
import java.util.UUID;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

/*test*/
public class BlueClient extends Thread{
	private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");//UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
	private final BluetoothSocket mmSocket;
	private final BluetoothDevice mmDevice;

	public BluetoothAdapter mBluetoothAdapter;

	ConnectManager connectManager;

	public boolean connecting = true;
	public boolean connected = false;

	public BlueClient(BluetoothDevice device) {
		BluetoothSocket tmp = null;
		mmDevice = device;
		//Log.e("client", device.toString());
		try {
			tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
			//Log.e("BlueClient", mmDevice.getAddress());

		} 
		catch (IOException e) { 
			Log.e("TestBoard BlueClient", "start");
		}
		mmSocket = tmp;
		Log.d("TestBoard BlueClient", "start");
	}

	public void run() {

		try {
			mmSocket.connect();
		}  
		catch (IOException connectException) {
			Log.e("TestBoard BlueClient", "IOException");
		}
		connectManager = new ConnectManager(mmSocket);
		connectManager.start();

		connecting = false;
		if (connectManager.isConnected()) {
			connected = true;
		}
	}	 

	/** Will cancel an in-progress connection, and close the socket */
	public void cancel() {
		connecting = true;
		connected = false;
		try {
			if(mmSocket != null){	
				connectManager.write("close".getBytes());
				connectManager.interrupt();
				mmSocket.close();
			}
			Log.i("TestBoard BlueClient", "cancel");
		} 
		catch (IOException e) { 
			Log.e("TestBoard BlueClient", "Cancle IOException");
		}
	}
	
	public boolean isConnected() {
		return connected;
	}

}
