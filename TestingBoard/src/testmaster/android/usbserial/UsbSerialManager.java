package testmaster.android.usbserial;

import java.io.IOException;

import android.app.Activity;
import android.hardware.usb.UsbManager;

import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialProber;

public class UsbSerialManager {
	UsbSerialDriver usb = null;
	public UsbSerialManager(Activity activity) {
		UsbManager usbManager = null;
		// TODO Auto-generated constructor stub
		usbManager = (UsbManager) activity.getSystemService(activity.USB_SERVICE);
		usb = UsbSerialProber.acquire(usbManager);
	}
	
	public void driverOpen()
	{
		try {
			usb.open();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void readASync()
	{
		
	}
}
