package testmaster.android.usbserial;

import java.io.IOException;

import android.app.Activity;
import android.hardware.usb.UsbManager;
import android.util.Log;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

public class UsbSerialManager {

	private UsbSerialDriver usb = null;
	private OscilloCommand command;
	private double data[] = new double[100];
	private byte readBuf[] = new byte[300];
	private String writeBuf = "$\r\n";
	private int count = 0;

	public void readOneBlock() throws IOException {
		
		int max = 0;
		int min = 0;
		int readByte = 0;
		int writeByte = 0;

		Log.d("TestingBoard UsbSerialManager", "read onebyte start");

		readByte = usb.read(readBuf, 100);
		Log.d("TestingBoard UsbSerialManager", readByte + " byteread success");

		for (int i = 0; i < 200; i += 2) {

			data[i/2] = readBuf[i] + ((double)readBuf[i + 1])/100;
/*			
			if (data[i/2] != 49.49)
				Log.d("TestingBoard UsbSerialManager", readByte + "byteread wrong data:" + data[i]);
*/
			if (data[i/2] > max)
				max = (int)data[i/2];
			if (data[i/2] < min)
				min = (int)data[i/2];
		}

		command.oscilloLoop(data, min, max);

	}

	private Thread readThread = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (true) {
					readOneBlock();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});

	public UsbSerialManager(Activity activity, OscilloCommand command) {

		UsbManager usbManager = null;
		// TODO Auto-generated constructor stub
		usbManager = (UsbManager) activity.getSystemService(activity.USB_SERVICE);
		usb = UsbSerialProber.acquire(usbManager);
		this.command = command;
		driverOpen();
	}

	private void driverOpen()
	{
		try {
			usb.open();
			usb.setBaudRate(115200);
			readThread.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
