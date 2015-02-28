package testmaster.android.page;

import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.R;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FunctionMotorContext extends FunctionContext implements OnClickListener{

	Button btn_Send_PID;

	private static EditText PID_P_e;
	private static EditText PID_I_e;
	private static EditText PID_D_e;
	private static EditText Object_RPM_e;
	private static EditText Motor_Gear_e;
	private static EditText Motor_Pulse_e;

	String PID_P="";
	String PID_I="";
	String PID_D="";
	String Object_RPM="";
	String Motor_Gear="";
	String Motor_Pulse="";


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

		Motor_Gear_e=(EditText) activity.findViewById(R.id.pid_gear_e);
		Motor_Gear=Motor_Gear_e.getText().toString();

		Motor_Pulse_e = (EditText) activity.findViewById(R.id.pid_pulse_e);
		Motor_Pulse = Motor_Pulse_e.getText().toString();

		Object_RPM_e=(EditText) activity.findViewById(R.id.object_RPM);
		Object_RPM = Object_RPM_e.getText().toString();

		PID_P_e=(EditText) activity.findViewById(R.id.pid_p_Edit);
		PID_P = PID_P_e.getText().toString();

		PID_I_e=(EditText) activity.findViewById(R.id.pid_i_Edit);
		PID_I = PID_I_e.getText().toString();

		PID_D_e=(EditText) activity.findViewById(R.id.pid_d_Edit);
		PID_D = PID_D_e.getText().toString();

		if(PID_D.equals("") || PID_I.equals("") || PID_D.equals("") 
				|| Object_RPM.equals("") || Motor_Gear.equals("") || Motor_Pulse.equals(""))
		{
			Toast.makeText(activity, "항목을 모두 입력하세요", Toast.LENGTH_SHORT).show();
		}
		else
		{
			//Toast.makeText(activity, PID_D, Toast.LENGTH_SHORT).show();
			//((PageChanger)activity).setNextPage();
		}
	}
}
