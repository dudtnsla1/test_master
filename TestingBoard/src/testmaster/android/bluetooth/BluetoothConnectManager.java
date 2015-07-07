package testmaster.android.bluetooth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import testmaster.android.dataobserver.DataObservable;
import testmaster.android.packet.AckPacket;
import testmaster.android.resource.DestroyDecorator;
import testmaster.android.resource.DestroyInterface;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

public class BluetoothConnectManager extends Thread implements DestroyInterface{

	private final BluetoothSocket mmSocket;
	private final InputStream mmInStream;
	private final OutputStream mmOutStream;
	private final AckPacket ackPacket = new AckPacket();

	private boolean state = true;
	private boolean connected = false;

	private void detroy() {
		DataObservable.disconnected();
	}

	public boolean isConnected() {
		return connected;
	} 
	
	protected BluetoothConnectManager() {
		mmSocket = null;
		mmInStream = null;
		mmOutStream = null;
	}

	public BluetoothConnectManager(BluetoothSocket socket) {
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
			DataObservable.setBluetoothServer(this);
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
				read_str = in.readLine();

				byte []read_buffer;
				boolean badPacket = false;

				read_buffer = read_str.getBytes();
				
				/*이상현상 예외*/
				for (int i = 0; i < read_buffer.length; i++) {
					if (read_buffer[i] == 0) {
						badPacket = true;
						break;
					}
				}
				if (badPacket || read_str.equals(""))
					continue;

				Log.i("TestingBoard ConnectManager", "read:" + read_str);
				DataObservable.update(read_str);
				ackPacket.send();
//				mmOutStream.write(buffer);
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
				logBuffer.append(write_buffer[i]+" ");
			}

			Log.i("TestingBoard ConnectManager", "write:" + logBuffer.toString());
		}
		catch (IOException e) {
			Log.e("TestingBoard ConnectManager", "Exception");
			e.printStackTrace();
			detroy();
		}
	}
	
	public void disconnected() {
		if (mmSocket != null) {
			try {
				if (mmSocket.isConnected())
					mmSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void decoratingDestroy() {
		// TODO Auto-generated method stub		
		state = false;
		disconnected();
		Log.d("TestingBoard ConnectManager", "Destroy (final decorator)");
	}
}
