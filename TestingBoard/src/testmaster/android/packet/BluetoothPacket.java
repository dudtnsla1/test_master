package testmaster.android.packet;

import testmaster.android.bluetooth.BluetoothConnectManager;
import testmaster.android.database.DataObservable;

public class BluetoothPacket {

	protected BluetoothConnectManager bluetoothServer = null;
	
	public BluetoothPacket() {
		bluetoothServer = DataObservable.getBluetoothServer();
		// TODO Auto-generated constructor stub
	}
	
	public void sendPacket(byte []packet) {
		
		if (bluetoothServer == null) {
			bluetoothServer = DataObservable.getBluetoothServer();			
		}
		if (bluetoothServer != null) {
			bluetoothServer.write(packet);
		} 
	}
}