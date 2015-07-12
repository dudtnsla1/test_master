package testmaster.android.page;

import testmaster.android.database.DataObservable;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.AlteredCharSequence;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class OscChartOnClickAdapter implements OnClickListener {

	private boolean autoScaleable = false;
	private boolean usbEnable = true;
	private boolean watchUsbIsEnable = true;

	static private final int default5Milliseconds = 50;
	static public int xScale = default5Milliseconds;
	
	public int getXScale() {
		return xScale;
	}

	public boolean isAutoScaleable() {
		return autoScaleable;
	}
	
	public boolean isUsbEnable() {
		watchUsbIsEnable = true;
		return usbEnable;
	}
	
	private Activity activity;
	final CharSequence[] items = { "1.0ms", "2.0ms", "5.0ms", "7.0ms",
			"10ms", "0.1s"};

	public OscChartOnClickAdapter(Activity activity)
	{
		this.activity = activity;
	}
	
	public void setOSCChartListener() {
		((ImageButton) activity.findViewById(R.id.oscchart_auto_btn))
				.setOnClickListener(this);
		((ImageButton) activity.findViewById(R.id.oscchart_reset_btn))
				.setOnClickListener(this);
		((ImageButton) activity.findViewById(R.id.oscchart_scale_btn))
				.setOnClickListener(this);
		((ImageButton) activity.findViewById(R.id.oscchart_stop_btn))
				.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.oscchart_auto_btn:
			autoScaleable = !autoScaleable;
			// 자동 버튼 누르면
			break;
		case R.id.oscchart_reset_btn:
/*
			usbEnable = true;
			watchUsbIsEnable = false;
			while (
*/
			break;
		case R.id.oscchart_scale_btn:

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("Set Time");

			builder.setItems(items, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch (which) {
					case 0: //"1.0ms"
						xScale = 100;
						break;
					case 1: // "2.0ms",
						xScale = 70;
						break;
					case 2: //"5.0ms"  
						xScale = 50;
						break;
					case 3:// "7.0ms",
						xScale = 20;
						break;
					case 4:// "10ms",
						xScale = 10;
						break;
					case 5:// "0.1s",
						xScale = 2;
						break;
					}
				}
				
			});
			AlertDialog alter = builder.create();
			
			alter.show();
			break;
			
		case R.id.oscchart_stop_btn:
			Log.d("TestingBoard OscilloChartFacade", "usb enable " + usbEnable);
			usbEnable = !usbEnable;
			break;
		}
	}
}