package testmaster.android.usbserial;

import android.app.Activity;
import android.hardware.usb.UsbManager;

public class UsbSerialManagerVirtual {

	private OscilloCommand command;
	private double data[] = new double[1000];
	private int count = 0;

	private void readOneBlock() {
		int max = 0;
		int min = 0;

		for (int i = 0; i < 100; i++) {
			data[i] = count;
			if (data[i] > max)
				max = (int)data[i];
			if (data[i] < min)
				min = (int)data[i];					
		}

		count++;
		
		command.oscilloLoop(data, min, max);
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Thread readThread = new Thread(new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				readOneBlock();
			}
		}
	});

	public UsbSerialManagerVirtual(Activity activity, OscilloCommand command) {
		UsbManager usbManager = null;
		// TODO Auto-generated constructor stub
		usbManager = (UsbManager) activity.getSystemService(activity.USB_SERVICE);
		this.command = command;
		driverOpen();
	}	

	public void driverOpen()
	{
		readThread.start();
	}
}
