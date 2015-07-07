package testmaster.android.dataobserver;

public abstract class DataObserver {
	private String name;
	
	public DataObserver(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void insertObserver() {
		DataObservable.insertObserver(this);
	}

	public void deleteObserver() {
		DataObservable.deleteObserver(this);
	}
	
	public abstract void update(String data);	
	public abstract void bluetoothDisconnect();
	public abstract void resetData();
}