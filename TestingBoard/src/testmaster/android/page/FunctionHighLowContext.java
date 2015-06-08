package testmaster.android.page;

import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FunctionHighLowContext extends FunctionContext implements OnClickListener{
 
    
	private ToggleButton toggle1;
	private ToggleButton toggle2;
	private ToggleButton toggle3;
	private ToggleButton toggle4;

	private String state1="Off";
	private String state2="Off";
	private String state3="Off";
	private String state4="Off";
	
	public FunctionHighLowContext(MainFunctionActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public byte onOffParseByte(String string) {		
		if (string.equals("On")) {
			return 1;
		}
		return 0;
	}

	@Override
	public int pageChanged(int pageNum) {		
				
		toggle1 =((ToggleButton) activity.findViewById(R.id.highlow1));
		toggle1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				state1 = toggle1.getText().toString();
				Toast.makeText(activity, "1번 "+state1, Toast.LENGTH_SHORT).show();
				packet.setHightLowPacket((byte)0, onOffParseByte(state1));
				packet.sendPacket(packet.getPacket());
			}
		});
		toggle2=((ToggleButton) activity.findViewById(R.id.highlow2));
		toggle2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				state2 = toggle2.getText().toString();
				Toast.makeText(activity, "2번 "+state2, Toast.LENGTH_SHORT).show();
				packet.setHightLowPacket((byte)1, onOffParseByte(state2));
				packet.sendPacket(packet.getPacket());
			}
		});
		toggle3=((ToggleButton) activity.findViewById(R.id.highlow3));
		toggle3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				state3 = toggle3.getText().toString();
				Toast.makeText(activity, "3번 "+state3, Toast.LENGTH_SHORT).show();
				packet.setHightLowPacket((byte)2, onOffParseByte(state3));
				packet.sendPacket(packet.getPacket());
			}
		});
		toggle4=((ToggleButton) activity.findViewById(R.id.highlow4));
		toggle4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				state4 = toggle4.getText().toString(); 
				Toast.makeText(activity, "4번 "+state4, Toast.LENGTH_SHORT).show();
				packet.setHightLowPacket((byte)3, onOffParseByte(state4));
				packet.sendPacket(packet.getPacket());
			}
		});
		
		return 0;
	}

	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
	}
}

