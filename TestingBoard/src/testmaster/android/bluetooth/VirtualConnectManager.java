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

public class VirtualConnectManager extends ConnectManager implements DestroyInterface{

	private final BluetoothSocket mmSocket;
	private final InputStream mmInStream;
	private final OutputStream mmOutStream;

	private boolean state = true;
	private boolean connected = false;

	private void detroy() {
		BluetoothObservable.disconnected();
	}

	public boolean isConnected() {
		//		return connected;
		return true;
	}

	public VirtualConnectManager() {
		super();
		DestroyDecorator.addDecorate(this);

		connected = true;
		mmSocket = null;
		mmInStream = null;
		mmOutStream = null;
		BluetoothObservable.setBluetoothServer(this);
	}

	public void run() {
		String read_str = null;

		//		BufferedReader in = new BufferedReader(new InputStreamReader(mmInStream));
		try {
			while (state) {

				Message msg = new Message();
				//				read_str = in.readLine();

				/**/
				Thread.sleep(2000);
				read_str = "1000";
				/**/

				msg.obj = read_str;
				BluetoothObservable.update(read_str);
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
		StringBuffer logBuffer = new StringBuffer();

		for (int i = 0; i < write_buffer.length; i++) {
			logBuffer.append((char)write_buffer[i]);
		}

		Log.i("TestingBoard ConnectManager", "write:" + logBuffer.toString());
	}

	@Override
	public void decoratingDestroy() {
		// TODO Auto-generated method stub		
		state = false;
		try {
			if (mmSocket != null) {
				if (mmSocket.isConnected())
					mmSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("TestingBoard ConnectManager", "Destroy (final decorator)");
	}
}
