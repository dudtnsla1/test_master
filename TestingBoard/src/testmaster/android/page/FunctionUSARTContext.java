package testmaster.android.page;

import testmaster.android.packet.SettingPacket;
import testmaster.android.packet.UsartPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FunctionUSARTContext extends FunctionContext implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener{
	private int baudrateIndex = 0;
	private Spinner usartSpinner;
	private Button Send_rate;
	private Button send;
	
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
			presentString = s.toString();
		}
		
	}

	private EditText endcharEdit = null, startcharEdit = null, dividecharEdit = null, turncharEdit = null, presentcharEdit = null;
	private EditText sendEdit = null;
	private TextView receiveText = null;
	private TextView sendText = null;
	private String baudrate_string;
	private String presentString = "";
	private NotNullEditTextDecorator editsListenDeco;

	public FunctionUSARTContext(MainFunctionActivity context) {
		super(context);
		editsListenDeco = new NotNullEditTextDecorator(context, pageChanger);
		// TODO Auto-generated constructor stub
	}

	private void setUnitSpinner(Activity context) {
		usartSpinner = (Spinner)activity.findViewById(R.id.usartspinner1);
		ArrayAdapter<CharSequence> usartadapter = ArrayAdapter.createFromResource(context, R.array.baud_rate, android.R.layout.simple_spinner_item);
		usartadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		usartSpinner.setAdapter(usartadapter);
		usartSpinner.setOnItemSelectedListener(this);
		Send_rate = (Button) activity.findViewById(R.id.function_usart_setting);
		Send_rate.setOnClickListener(this);			
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
//보어레이트, 스타트문자, 마지막문자, 구분할 문자, 턴 넘버
		packet.setUSARTPacket((byte)baudrateIndex, 
				editsListenDeco.getText(startcharEdit), 
				editsListenDeco.getText(endcharEdit),
				editsListenDeco.getText(dividecharEdit),
				editsListenDeco.getText(turncharEdit));
		return packet;
	}

	public void initSecondPage(Activity activity) {
		receiveText = (TextView)activity.findViewById(R.id.function_usart_receive_textview);
		sendText = (TextView)activity.findViewById(R.id.function_usart_send_textview);
		sendEdit = (EditText)activity.findViewById(R.id.function_usart_send_edittext);
		send = (Button)activity.findViewById(R.id.function_usart_send);
		send.setOnClickListener(this);		
	}

	@Override
	public int pageChanged(int pageNum) {
		if (pageNum == 0) {
			initFirstPage(activity);
		} else if (pageNum == 1) {
			initSecondPage(activity);
		}

		return 0;
	}

	private void initFirstPage(Activity context) {
		setUnitSpinner(context);
		editsListenDeco.init();
		dividecharEdit = (EditText)context.findViewById(R.id.dividechar_edit);
		endcharEdit = (EditText)context.findViewById(R.id.endchar_edit);
		turncharEdit = (EditText)context.findViewById(R.id.turnchar_edit);
		startcharEdit = (EditText)context.findViewById(R.id.startchar_edit);
		presentcharEdit = (EditText)context.findViewById(R.id.presentchar_edit);
		presentcharEdit.addTextChangedListener(new TextEditWatcher());
		editsListenDeco.addEditText(dividecharEdit);
		editsListenDeco.addEditText(endcharEdit);
		editsListenDeco.addEditText(turncharEdit);
		editsListenDeco.addEditText(startcharEdit);
	}


	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		baudrateIndex = position;
		baudrate_string= parent.getItemAtPosition(position).toString();
		//Toast.makeText(activity, parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT ).show();
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.function_usart_setting: 
			((PageChanger)activity).setNextPage();
			Toast.makeText(activity, "Baud Rate : "+baudrate_string, Toast.LENGTH_SHORT).show();
			break;
		case R.id.function_usart_send:
			UsartPacket packet = new UsartPacket();
			packet.setPacket(sendEdit.getText().toString());
			packet.sendPacket(packet.getPacket());
			if (sendText != null) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						sendText.append(sendEdit.getText().toString() + "\n");  
					}
				});
			}		
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
					receiveText.append(presentString + ":" + data + "\n");   
					buffer.append(data+"\n");			

					if (buffer.toString().length() > bufferFull) {
						receiveText.setText(presentString + ":" + buffer.toString());
						buffer.delete(0, buffer.length());
					}	
				}
			});
		}		
	}

	protected void resetDataTemplate() {

	}
}

