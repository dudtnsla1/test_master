package testmaster.android.page;

import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.R;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FunctionMotorContext extends FunctionContext implements OnClickListener{

	Button btn_Send_PID;
	String PID_P;
	String PID_I;
	String PID_D;
	String Object_RPM;
	
	
	public FunctionMotorContext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setPacket(PacketInfo.MODE_MOTOR);
		return packet;
	}

	@Override
	public int pageChanged(int pageNum) {
		// TODO Auto-generated method stub
		
		
		btn_Send_PID = (Button) activity.findViewById(R.id.btn_PID);
		btn_Send_PID.setOnClickListener(this);
		return 0;
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
	public void onClick(View v) {
		try{
			String PID_P;
			 PID_I;
			 PID_D;
			Object_RPM;
		}catch(Exception e){
			
		}
		
		
		
		
	}
}
