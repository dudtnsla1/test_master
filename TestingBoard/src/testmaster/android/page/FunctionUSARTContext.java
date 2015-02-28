package testmaster.android.page;

import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class FunctionUSARTContext extends FunctionContext implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener{
	String present_rate;
	private Spinner usartSpinner;
	Button Send_rate;
	public FunctionUSARTContext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	
	private void setUnitSpinner(Activity context) {
		usartSpinner = (Spinner)activity.findViewById(R.id.usartspinner1);
		ArrayAdapter<CharSequence> usartadapter = ArrayAdapter.createFromResource(context, R.array.baud_rate, android.R.layout.simple_spinner_item);
		usartadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		usartSpinner.setAdapter(usartadapter);
		usartSpinner.setOnItemSelectedListener(this);
	}
	
	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub

		packet.setPacket(PacketInfo.MODE_USART, Integer.parseInt(present_rate));
		return packet;
	}

	@Override
	public int pageChanged(int pageNum) {
			if(pageNum==0)
			{
				initFirstPage(activity);
			}
			
			Send_rate = (Button) activity.findViewById(R.id.function_usart_setting);
			Send_rate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(present_rate!= null){
						//present_rate 에 담긴거 보내면됨
						//Toast.makeText(activity, present_rate, Toast.LENGTH_SHORT).show();
						((PageChanger)activity).setNextPage();
					}
					else
					{
						Toast.makeText(activity, "baud rate를 설정하세요", Toast.LENGTH_SHORT).show();
					}
				
					
				}
			});
			
			
		return 0;
	}
	
	private void initFirstPage(Activity context) {
		setUnitSpinner(context);
		
	}


	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateChartTemplate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		present_rate=parent.getItemAtPosition(position).toString();
		//Toast.makeText(activity, parent.getItemAtPosition(position).toString(),Toast.LENGTH_SHORT ).show();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

