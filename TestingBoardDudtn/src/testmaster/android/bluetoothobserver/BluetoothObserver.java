package testmaster.android.bluetoothobserver;

public abstract class BluetoothObserver {
	
	public void insertObserver() {
		BluetoothObservable.insertObserver(this);
	}
	
	public abstract void update(String data);
	
	public abstract void bluetoothDisconnect();
	
	public void deleteObserver() {
		BluetoothObservable.deleteObserver(this);
	}
}