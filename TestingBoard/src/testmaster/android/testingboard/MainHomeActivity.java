package testmaster.android.testingboard;

import java.util.Set;

import testmaster.android.bluetooth.BlueClient;
import testmaster.android.bluetooth.VirtualConnectManager;
import testmaster.android.bluetoothobserver.BluetoothObservable;
import testmaster.android.resource.LoadedImage;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnGenericMotionListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainHomeActivity extends Activity {

	public final static String extraFunctionLayout = "functionLayout";
	public final static String extraFunctionSettingListener = "settingListener";

	private static final int REQUEST_ENABLE_BT = 0;	
	private static final int REQUEST_FUNCTION_ACTIVITY = 0;

	public BluetoothAdapter mBluetoothAdapter;
	public BlueClient blueClient;

	private final int bluetoothConnectBtnId = R.id.main_home_bluetooth_connect_btn;
	
	private int []functionButtonIds = new int[] {
			R.id.main_home_adc_btn, 
			R.id.main_home_pwm_btn,
			R.id.main_home_usart_btn,
			R.id.main_home_i2c_btn, 
			R.id.main_home_clock_btn,
			R.id.main_home_motor_btn
	};

	private int []functionTextIds = new int[] {
			R.id.main_home_adc_text,
			R.id.main_home_pwm_text,
			R.id.main_home_usart_text,
			R.id.main_home_i2c_text,
			R.id.main_home_clock_text,
			R.id.main_home_motor_text
	};

	private TextView functionTexts[] = new TextView[functionButtonIds.length];
	private ImageButton functionButtons[] = new ImageButton[functionButtonIds.length];
	private ImageButton bluetoothConnectBtn;
	private ImageButton optionBtn;
	private LinearLayout bluetoothConnectLinearLayout;
	private LinearLayout functionLinearLayout;
	private boolean bigBluetoothFlag = true;
	private LayoutParams bluetoothLayoutParams;
	private LayoutParams functionLayoutParams;

	private boolean onceInitialize = false;	

	private void init() { 
		if (onceInitialize == false) {
			initView();
			LoadedImage.loadImage(this);
			onceInitialize = true;
		}
		initBluetoothImage();
		initBluetooth();
	}

	private void initBluetoothImage() {

		if (bigBluetoothFlag) {

		} else {
			functionLayoutParams.width -= bluetoothLayoutParams.width;
			bluetoothLayoutParams.width = bluetoothConnectBtn.getBackground().getBounds().width() * 2;
			setFunctionTextVisible(View.GONE);
			bluetoothConnectLinearLayout.setBackgroundColor(getResources().getColor(R.color.main_home_gray_alpha_b));

			bluetoothConnectLinearLayout.setLayoutParams(bluetoothLayoutParams);
			bluetoothConnectBtn.setBackground(LoadedImage.getImage(R.drawable.image_bluetooth_connect));
		}
		bigBluetoothFlag = true;
	}

	private void initView() {
		bluetoothConnectBtn = (ImageButton)findViewById(bluetoothConnectBtnId);
		bluetoothConnectBtn.setOnClickListener(new BluetoothConnectOnClick());
		bluetoothConnectLinearLayout = (LinearLayout)findViewById(R.id.main_home_bluetooth_connect_layout);
		bluetoothLayoutParams = bluetoothConnectLinearLayout.getLayoutParams();

		optionBtn = (ImageButton)findViewById(R.id.main_home_option_btn);
		optionBtn.setOnClickListener(new OptionOnClick());

		functionLinearLayout = (LinearLayout)findViewById(R.id.main_home_funtion_layout);
		functionLayoutParams = functionLinearLayout.getLayoutParams();

		FunctionTextOnClick funtionTestListener = new FunctionTextOnClick();
		FunctionImageOnClick functionExplainListener = new FunctionImageOnClick();

		for (int i = 0; i < functionButtonIds.length; i++) {
			functionTexts[i] = (TextView)findViewById(functionTextIds[i]);
			functionTexts[i].setOnClickListener(funtionTestListener);
			functionButtons[i] = (ImageButton)findViewById(functionButtonIds[i]);
			functionButtons[i].setOnGenericMotionListener(functionExplainListener);
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
		BluetoothObservable.insertDestroyDecorator();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_home_activity);
		init();
	}	

	private void setFunctionTextVisible(int visibility) {
		for (int i = 0; i < functionTexts.length; i++) {
			functionTexts[i].setVisibility(visibility);
		}
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
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 *  Item listeners */	
	class BluetoothResizeAnimationListener implements AnimationListener {

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub			
			functionLinearLayout.setVisibility(View.VISIBLE);
		}

		public void setBluetoothLayoutToDeviceInfo() {
			bluetoothConnectLinearLayout.setBackgroundColor(getResources().getColor(R.color.main_home_emerald_alpha_b));
		}

		public void setBluetoothLayoutToBluetoothInfo() {
			bluetoothConnectLinearLayout.setBackgroundColor(getResources().getColor(R.color.main_home_gray_alpha_b));
		}

		@Override
		public void onAnimationRepeat(Animation animation) { 
			// TODO Auto-generated method stub			
			bluetoothConnectLinearLayout.setLayoutParams(bluetoothLayoutParams);
			bigBluetoothFlag = !bigBluetoothFlag;
			if (bigBluetoothFlag) {
				setFunctionTextVisible(View.GONE);
				setBluetoothLayoutToBluetoothInfo();
			} else {
				setFunctionTextVisible(View.VISIBLE);	
				setBluetoothLayoutToDeviceInfo();			
			}
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
		}		
	}

	class BluetoothConnectOnClick implements OnClickListener {
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
			}
			return blueClient.isConnected();
		}

		private void showDeviceInfo() {
			//bluetoothConnectLinearLayout.removeView(findViewById(bluetoothConnectBtnId));
			
		}

		@Override
		public void onClick(View v) {
			if (resizeBluetooth != null) {
				if (!resizeBluetooth.hasEnded())
					return;
			}

			if (bigBluetoothFlag) {
				if (!bluetoothConnectVirtual())
					return;
				resizeBluetooth = new ScaleAnimation(1, 0f, 1, 1f);
				bluetoothLayoutParams.width = bluetoothConnectBtn.getBackground().getBounds().width() / 2;
				functionLayoutParams.width += bluetoothLayoutParams.width; 

				showDeviceInfo();

			} else {
				resizeBluetooth = new ScaleAnimation(1, 0f, 1, 1f);
				functionLayoutParams.width -= bluetoothLayoutParams.width;
				bluetoothLayoutParams.width = bluetoothConnectBtn.getBackground().getBounds().width() * 2;
			}

			resizeBluetooth.setDuration(500);
			resizeBluetooth.setRepeatCount(1);
			resizeBluetooth.setRepeatMode(ScaleAnimation.REVERSE);
			resizeBluetooth.setAnimationListener(new BluetoothResizeAnimationListener());
			functionLinearLayout.setVisibility(View.INVISIBLE);

			bluetoothConnectLinearLayout.startAnimation(resizeBluetooth);
		}
	}

	class FunctionImageOnClick implements OnGenericMotionListener {

		@Override
		public boolean onGenericMotion(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			return false;
		}
	}

	class FunctionTextOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int viewId = v.getId();
			Intent intent = new Intent(MainHomeActivity.this, MainFunctionActivity.class);

			for (int i = 0; i < functionTextIds.length; i++) {
				if (viewId == functionTextIds[i]) 
					intent.putExtra(extraFunctionLayout, MainFunctionActivity.contentLayoutIds[i]);
			}

			startActivityForResult(intent, REQUEST_FUNCTION_ACTIVITY);
		}
	}

	class OptionOnClick implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(MainHomeActivity.this, MainPreferenceActivity.class);
			startActivity(intent);
		}
	}
}