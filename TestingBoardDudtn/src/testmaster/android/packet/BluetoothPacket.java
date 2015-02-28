package testmaster.android.packet;

import testmaster.android.bluetooth.ConnectManager;
import testmaster.android.bluetoothobserver.BluetoothObservable;

public class BluetoothPacket {

	protected ConnectManager bluetoothServer = null;
	
	public BluetoothPacket() {
		bluetoothServer = BluetoothObservable.getBluetoothServer();
		// TODO Auto-generated constructor stub
	}
	public void sendPacket(byte []packet) {
		if (bluetoothServer != null)
			bluetoothServer.write(packet);
	}
}