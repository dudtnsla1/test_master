
package testmaster.android.page;

import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FunctionI2CContext extends FunctionContext implements
		OnClickListener {

	private Button btn_Receive_true;
	private I2CSpinner[] I2C_Setting_Spinner = new I2CSpinner[11];
	private byte[] order = new byte[11];
	private TextView receiveText;
	private static EditText Slave_ADDRESS;
	private static EditText Register_ADDRESS;
	private static EditText Number_READ;
	private static EditText PresentEdit;
	private static String presentStr;

	private NotNullEditTextDecorator editsListenDeco;

	class TextEditWatcher implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public synchronized void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			Log.d("TestingBoard FunctionUSAUTContext", "remove test - " + s.toString());
			presentStr = s.toString();
		}
		
	}

	private int spinnerId[] = new int[] { R.id.i2c_setting_btn1,
			R.id.i2c_setting_btn2, R.id.i2c_setting_btn3,
			R.id.i2c_setting_btn4, R.id.i2c_setting_btn5,
			R.id.i2c_setting_btn6, R.id.i2c_setting_btn7,
			R.id.i2c_setting_btn8, R.id.i2c_setting_btn9,
			R.id.i2c_setting_btn10, R.id.i2c_setting_btn11,
			R.id.i2c_setting_btn12, R.id.i2c_setting_btn13,
			R.id.i2c_setting_btn14, R.id.i2c_setting_btn15,
			R.id.i2c_setting_btn16, R.id.i2c_setting_btn17,
			R.id.i2c_setting_btn18, R.id.i2c_setting_btn19,
			R.id.i2c_setting_btn20, R.id.i2c_setting_btn21,
			R.id.i2c_setting_btn22, };

	private class I2CSpinner {
		private Spinner master;
		private Spinner slave;
		private int order;

		private OnSpinnerItemClicked spinnerListener;

		public int getSelectedPosition() {
			return spinnerListener.getPosition();
		}

		private void setOnSpinnerInit(Spinner spinner) {
			ArrayAdapter<CharSequence> i2cadapter = ArrayAdapter
					.createFromResource(activity, R.array.i2c_setting,
							R.layout.spinner_item);
			i2cadapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner.setAdapter(i2cadapter);
			spinner.setOnItemSelectedListener(spinnerListener);
		}

		public I2CSpinner(View view, int order) {
			master = (Spinner) view.findViewById(spinnerId[order * 2]);
			slave = (Spinner) view.findViewById(spinnerId[order * 2 + 1]);
			this.order = order;
			spinnerListener = new OnSpinnerItemClicked(master, slave);
			setOnSpinnerInit(master);
			setOnSpinnerInit(slave);
		}
	}

	public FunctionI2CContext(MainFunctionActivity context) {
		super(context);
		editsListenDeco = new NotNullEditTextDecorator(activity, pageChanger);

		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setI2CPacket(
				parseByteFromHex(editsListenDeco.getText(Slave_ADDRESS)),
				parseByteFromHex(editsListenDeco.getText(Register_ADDRESS)),
				Byte.parseByte(editsListenDeco.getText(Number_READ)), order);
		return packet;
	}

	private void showDialog() {
		LayoutInflater inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View dialogView = inflater.inflate(R.layout.i2c_setting_dialog,
				null);

		final AlertDialog.Builder d = new AlertDialog.Builder(activity)
				.setCancelable(true).setTitle("I2C 설정").setView(dialogView)
				.setNegativeButton("취소", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				})
				.setPositiveButton("확인", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

						for (int i = 0; i < 11; i++)
							order[i] = (byte) I2C_Setting_Spinner[i]
									.getSelectedPosition();
					}

				});

		for (int i = 0; i < 11; i++) {
			I2C_Setting_Spinner[i] = new I2CSpinner(dialogView, i);
		}

		d.create().show();
	}

	@Override
	public int pageChanged(int pageNum) {

		if (pageNum == 0) {
			initFistPage(activity);
		} else if (pageNum == 1) {
			initSecondPage();
		}

		return 0;
	}

	private void initFistPage(Activity context) {
		
		Slave_ADDRESS = (EditText) activity.findViewById(R.id.i2cEdit);
		Register_ADDRESS = (EditText) activity.findViewById(R.id.Register_Address);
		Number_READ = (EditText) activity.findViewById(R.id.Number_of_Read);
		PresentEdit = (EditText) activity.findViewById(R.id.present_edit);
		
		editsListenDeco.init();
		editsListenDeco.addEditText(Slave_ADDRESS);
		editsListenDeco.addEditText(Register_ADDRESS);
		editsListenDeco.addEditText(Number_READ);
		PresentEdit.addTextChangedListener(new TextEditWatcher());
		
		
		btn_Receive_true = (Button) activity
				.findViewById(R.id.function_i2c_receive_btn_true);
		btn_Receive_true.setOnClickListener(this);
	}

	private void initSecondPage() {
		receiveText = (TextView) activity
				.findViewById(R.id.function_i2c_receive_textview);
	}

	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub

	}

	private byte parseByteFromHex(String string) {
		int start = 0;
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == 'x' || string.charAt(i) == 'X')
				start = i + 1;
		}
		return (byte) Integer.parseInt(
				string.substring(start, string.length()), 16);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.function_i2c_receive_btn_true:
			((PageChanger) activity).setNextPage();
			break;
		}
	}

	private StringBuffer buffer = new StringBuffer();
	private final int bufferFull = 2000;

	protected void updateTemplate(final String data) {
		if (receiveText != null) {
			activity.runOnUiThread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					receiveText.append(presentStr + ":" + data + "\n");
					buffer.append(data + "\n");

					if (buffer.toString().length() > bufferFull) {
						receiveText.setText(presentStr + ":" + buffer.toString());
						buffer.delete(0, buffer.length());
					}
				}
			});
		}
	}
}
