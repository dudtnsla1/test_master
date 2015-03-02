package testmaster.android.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import testmaster.android.bluetoothobserver.BluetoothObservable;
import testmaster.android.resource.DestroyDecorator;
import testmaster.android.resource.DestroyInterface;
import android.bluetooth.BluetoothSocket;
import android.os.Message;
import android.util.Log;

public class VirtualConnectManager extends ConnectManager implements DestroyInterface{

	private final BluetoothSocket mmSocket;
	
	private boolean state = true;

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

		mmSocket = null;
		BluetoothObservable.setBluetoothServer(this);
	}

	public void run() {
		int i = 0;
		String read_str = null;

		//		BufferedReader in = new BufferedReader(new InputStreamReader(mmInStream));
		try {
			while (state) {

				//				read_str = in.readLine();

				/**/
				Thread.sleep(10);
				read_str = "axgbasdgasgkahgoheqghograsg" + (i++);
				/**/
//				Log.i("TestingBoard ConnectManager", "read:" + read_str);

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
