package testmaster.android.page;

import testmaster.android.bluetoothobserver.BluetoothObservable;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.text.AlteredCharSequence;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class OscChartOnClickAdapter implements OnClickListener {

	private Activity activity;
	FunctionContext functionContext;
	final CharSequence[] items = { "0.1ms", "0.2ms", "0.5ms", "1.0ms", "2.0ms",
			"5.0ms", "10ms", "20ms", "50ms", "0.1sec", "0.2sec", "0.5sec" };

	public OscChartOnClickAdapter(Activity activity, FunctionContext functionContext)
	{
		this.activity = activity;
		this.functionContext = functionContext;
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

	private void streamStop() {
		// 그래프 멈추게하는거 이걸로 하면 되는감
		BluetoothObservable.disableUpdate();
	}

	private void streamReset() {
		BluetoothObservable.resetData();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.oscchart_auto_btn:
			// 자동 버튼 누르면
			((FunctionOscilloscopeContext)functionContext).autoSacle();
			break;
		case R.id.oscchart_reset_btn:
			streamReset();
			break;
		case R.id.oscchart_scale_btn:

			AlertDialog.Builder builder = new AlertDialog.Builder(activity);
			builder.setTitle("Set Time");

			builder.setItems(items, new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch (which) {
					case 0: //"0.1ms"

						break;
						
					case 1:// "0.2ms", 

						break;
						
					case 2: // "0.5ms"
						

						break;
						
					case 3: //"1.0ms"

						break;
						
					case 4: // "2.0ms",

						break;
						
					case 5: //"5.0ms"  

						break;
						
					case 6:// "10ms",

						break;
						
					case 7: //"20ms",

						break;
						
					case 8: //"50ms",

						break;
						
					case 9: //"0.1sec"

						break;
						
					case 10:  //"0.2sec"

						break;
						
					case 11:  //"0.5sec"
						Toast.makeText(activity, "0.5sec",Toast.LENGTH_SHORT).show();
						break;
					
					}
				}
				
			});
			AlertDialog alter = builder.create();
			
			alter.show();
			break;
			
		case R.id.oscchart_stop_btn:
			streamStop();
			break;
		}
	}

}
