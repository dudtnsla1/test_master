package testmaster.android.bluetoothobserver;

public abstract class BluetoothObserver {
	private String name;
	
	public BluetoothObserver(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void insertObserver() {
		BluetoothObservable.insertObserver(this);
	}

	public void deleteObserver() {
		BluetoothObservable.deleteObserver(this);
	}
	
	public abstract void update(String data);	
	public abstract void bluetoothDisconnect();
	public abstract void resetData();
}