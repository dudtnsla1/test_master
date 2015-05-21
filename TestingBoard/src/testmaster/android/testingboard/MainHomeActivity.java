package testmaster.android.testingboard;

import java.util.Set;

import testmaster.android.bluetooth.BlueClient;
import testmaster.android.bluetooth.VirtualConnectManager;
import testmaster.android.bluetoothobserver.BluetoothObservable;
import testmaster.android.bluetoothobserver.BluetoothObserver;
import testmaster.android.resource.LoadedImage;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainHomeActivity extends Activity {

	public final static String extraFunctionLayout = "functionLayout";
	public final static String extraFunctionSettingListener = "settingListener";

	private static final int REQUEST_ENABLE_BT = 0;	
	private static final int REQUEST_FUNCTION_ACTIVITY = 1;

	
	private int []functionButtonIds = new int[] {
			R.id.main_activity2_adc, 
			R.id.main_activity2_pwm,
			R.id.main_activity2_uart,
			R.id.main_activity2_i2c, 
			R.id.main_activity2_High_low,
			R.id.main_activity2_Motor
	};

	private ImageButton functionButtons[] = new ImageButton[functionButtonIds.length];

	private BluetoothAdapter mBluetoothAdapter;
	private BlueClient blueClient;

	private boolean onceInitialize = false;	

	private void init() { 
		if (onceInitialize == false) {
			initView();
			LoadedImage.loadImage(this);
			onceInitialize = true;		
			bluetoothServerObserver.insertObserver();
			BluetoothObservable.insertDestroyDecorator();
		}
		initBluetooth();
	}

	private void initView() {
		FunctionOnClick functionExplainListener = new FunctionOnClick();

		for (int i = 0; i < functionButtonIds.length; i++) {
			functionButtons[i] = (ImageButton)findViewById(functionButtonIds[i]);
			functionButtons[i].setOnClickListener(functionExplainListener);
		}
	}

	private void initBluetooth() {		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		if (mBluetoothAdapter == null) {
			// Device does not support Bluetooth
			Toast.makeText(this, "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
		}
		else if (!mBluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_home_change);
		init();
	}	

	public void superOnbackPressed() {
		super.onBackPressed();
	}	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		String title = "종료";
		String msg = "종료 하시겠습니까?";

		new AlertDialog.Builder(this)
		.setTitle(title)
		.setMessage(msg)
		.setPositiveButton("예",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				bluetoothServerObserver.deleteObserver();
				MainHomeActivity.this.superOnbackPressed();
			}
		})
		.setNegativeButton("아니요",
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int which) {

			}
		}).show();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (requestCode == REQUEST_FUNCTION_ACTIVITY) {
			if (resultCode == -1) {
				init();
			}
		} else if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == 0) { 
				Toast.makeText(this, "블루투스를 사용 할 수 없습니다.", Toast.LENGTH_SHORT).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	private class BluetoothConnectOnClick implements OnClickListener {
		Animation resizeBluetooth = null;

		private boolean bluetoothConnectVirtual() {
			VirtualConnectManager blue = new VirtualConnectManager();
			blue.start();
			return true;
		}

		private boolean bluetoothConnect() {
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter
					.getBondedDevices();


			if(pairedDevices.size()>0){
				final BluetoothDevice[] item =new BluetoothDevice[pairedDevices.size()];
				int count=0;
				for (BluetoothDevice device : pairedDevices) {
					item[count] = device;
					count++;
				}
				blueClient = new BlueClient(item[0]);
				blueClient.start();
				while(blueClient.connecting){};
				if (blueClient.isConnected())
					Toast.makeText(getApplicationContext(), "Blue가 연결되었습니다.", Toast.LENGTH_SHORT).show();
				else
					Toast.makeText(getApplicationContext(), "Blue연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(getApplicationContext(), "Blue연결에 실패하였습니다.", Toast.LENGTH_SHORT).show();
				return false;
			}
			return blueClient.isConnected();
		}

		@Override
		public void onClick(View v) {
			if (resizeBluetooth != null) {
				if (!resizeBluetooth.hasEnded())
					return;
			}

			if (!bluetoothConnectVirtual())
				return;
		}
	}

	private class FunctionOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int viewId = v.getId();
			Intent intent = new Intent(MainHomeActivity.this, MainFunctionActivity.class);

			for (int i = 0; i < functionButtonIds.length; i++) {
				if (viewId == functionButtonIds[i]) 
					intent.putExtra(extraFunctionLayout, MainFunctionActivity.contentLayoutIds[i]);
			}

			startActivityForResult(intent, REQUEST_FUNCTION_ACTIVITY);
		}
	}

	private class OptionOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainHomeActivity.this, MainPreferenceActivity.class);
			startActivity(intent);
		}
	}
	
	private BluetoothObserver bluetoothServerObserver = new BluetoothObserver("MainActivity Server Observer") {

		@Override
		public void update(String data) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void bluetoothDisconnect() {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					init();
				}
			});
		}

		@Override
		public void resetData() {
			// TODO Auto-generated method stub
			
		}
		
	};
}