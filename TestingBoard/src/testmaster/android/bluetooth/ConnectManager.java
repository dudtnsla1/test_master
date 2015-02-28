package testmaster.android.bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import testmaster.android.bluetoothobserver.BluetoothObservable;
import testmaster.android.resource.DestroyDecorator;
import testmaster.android.resource.DestroyInterface;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

public class ConnectManager extends Thread implements DestroyInterface{

	private final BluetoothSocket mmSocket;
	private final InputStream mmInStream;
	private final OutputStream mmOutStream;

	private boolean state = true;
	private boolean connected = false;

	private void detroy() {
		BluetoothObservable.disconnected();
	}

	public boolean isConnected() {
		return connected;
	} 

	public ConnectManager(BluetoothSocket socket) {
		DestroyDecorator.addDecorate(this);
		mmSocket = socket;
		InputStream ip = null;
		OutputStream op = null;
		if (mmSocket.isConnected()) {

			Log.e("TestBoard", "connect");
			try {
				ip = mmSocket.getInputStream();
				op = mmSocket.getOutputStream();
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			connected = true;
			mmInStream = ip;
			mmOutStream = op;
			BluetoothObservable.setBluetoothServer(this);
		}
		else { 
			mmInStream = null;
			mmOutStream = null;
		}
	}

	public void run() {
		String read_str = null;

		BufferedReader in = new BufferedReader(new InputStreamReader(mmInStream));
		try {
			while (state) {
				Message msg = new Message();
				read_str = in.readLine();
				msg.obj = read_str;

				//				Log.i("TestingBoard ConnectManager", "read:" + read_str);
				BluetoothObservable.messageReceiver.sendMessage(msg);
			}
		}

		catch (NullPointerException NullPointer) {
			Log.e("TestingBoard ConnectManager", "NullPointer_error");
			NullPointer.printStackTrace();
			detroy();
		} catch (Exception Exception) {
			Log.e("TestingBoard ConnectManager", "Exception");
			Exception.printStackTrace();
			detroy();
		}
	}

	public void write(byte[] write_buffer) {
		try {
			mmOutStream.write(write_buffer);
			StringBuffer logBuffer = new StringBuffer();

			for (int i = 0; i < write_buffer.length; i++) {
				logBuffer.append((char)write_buffer[i]);
			}

			Log.i("TestingBoard ConnectManager", "write:" + logBuffer.toString());
		}
		catch (IOException e) {
			detroy();
		}
	}

	@Override
	public void decoratingDestroy() {
		// TODO Auto-generated method stub		
		state = false;
		if (mmSocket != null) {
			try {
				if (mmSocket.isConnected())
					mmSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Log.d("TestingBoard ConnectManager", "Destroy (final decorator)");
	}
}
