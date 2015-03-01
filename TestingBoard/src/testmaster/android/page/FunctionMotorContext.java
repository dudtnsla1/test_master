package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.chart.ChartFacade;
import testmaster.android.chart.GraphicalActivity;
import testmaster.android.database.DbOpenHelper;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.R;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FunctionMotorContext extends FunctionContext implements OnClickListener{
	
	public class NotNullEditTextAdapter implements TextWatcher {
		String text;
		EditText edit;		 
		
		public NotNullEditTextAdapter(EditText edit, String string) {
			// TODO Auto-generated constructor stub
			this.edit = edit;
			edit.addTextChangedListener(this);
			text = string;
		}
		
		public String getNotEmptyText() {
			if (edit.getText().toString().equals("")) {
				Toast.makeText(activity, "설정하지 않은 값이 있습니다. 0 으로 초기화 됩니다.", Toast.LENGTH_SHORT).show();
				return "0";
			}
			return edit.getText().toString();
		}
		
		public String getText() {
			return text;
		}

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
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			text = s.toString();
		}
	}

	Button btn_Send_PID;

	private static NotNullEditTextAdapter PID_P_e;
	private static NotNullEditTextAdapter PID_I_e;
	private static NotNullEditTextAdapter PID_D_e;
	private static NotNullEditTextAdapter Object_RPM_e;
	private static NotNullEditTextAdapter Motor_Gear_e;
	private static NotNullEditTextAdapter Motor_Pulse_e;

	private String PID_P="";
	private String PID_I="";
	private String PID_D="";
	private String Object_RPM="";
	private String Motor_Gear="";
	private String Motor_Pulse="";

	private DbOpenHelper dbHelper;
	private GraphicalView chart;
	private ChartOnClickAdpater barChartListener;
	private ChartFacade databaseDrawer;

	@Override
	public void destroy() {
		dbHelper.close();
	}
	
	@Override
	protected void updateTemplate(String data) {
		// TODO Auto-generated method stub
		super.updateTemplate(data);
	}
	
	@Override
	public void updatePreference() {
		databaseDrawer.updateSelectedDatabases();
	}
	
	public FunctionMotorContext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		dbHelper = new DbOpenHelper(context);
		dbHelper.open();
		databaseDrawer = new ChartFacade(dbHelper, (GraphicalActivity)activity, ChartFacade.KIND_MOTOR);
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setMotorPacket(PID_P_e.getText(), PID_I_e.getText(), PID_D_e.getText(), Object_RPM_e.getText(), Motor_Gear_e.getText(), Motor_Pulse_e.getText());
		return packet;
	}
	
	public void initFirstPage() {
		Motor_Gear_e= new NotNullEditTextAdapter((EditText) activity.findViewById(R.id.pid_gear_e), Motor_Gear);
		Motor_Pulse_e = new NotNullEditTextAdapter((EditText) activity.findViewById(R.id.pid_pulse_e), Motor_Pulse);
		Object_RPM_e= new NotNullEditTextAdapter((EditText) activity.findViewById(R.id.object_RPM), Object_RPM);
		PID_P_e= new NotNullEditTextAdapter((EditText) activity.findViewById(R.id.pid_p_Edit), PID_P);
		PID_I_e= new NotNullEditTextAdapter((EditText) activity.findViewById(R.id.pid_i_Edit), PID_I);
		PID_D_e= new NotNullEditTextAdapter((EditText) activity.findViewById(R.id.pid_d_Edit), PID_D);
		btn_Send_PID = (Button) activity.findViewById(R.id.btn_PID);
		btn_Send_PID.setOnClickListener(this);
		packet.setInitPacket();
		packet.sendPacket(packet.getPacket());
	}
	
	public void initSecondPage() {
		chart = ((GraphicalActivity)activity).getLineChartGraphicalView(0, 100, 0, 100);
		setBarChart(chart);
		barChartListener = new ChartOnClickAdpater(dbHelper, activity, ChartOnClickAdpater.KIND_MOTOR);
		barChartListener.setBarChartListener();
		updatePreference();
	}

	@Override
	public int pageChanged(int pageNum) {
		// TODO Auto-generated method stub
		if (pageNum == 0) {
			initFirstPage();
		} else if(pageNum == 1) {
			initSecondPage();
		}
		return 0;
	}

	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub
		if(PID_P.equals("") || PID_I.equals("") || PID_D.equals("") 
				|| Object_RPM.equals("") || Motor_Gear.equals("") || Motor_Pulse.equals("")) {
		}	
	}

	@Override
	public void onClick(View v) {
		Motor_Gear = Motor_Gear_e.getText();
		Motor_Pulse = Motor_Pulse_e.getText();
		Object_RPM = Object_RPM_e.getText();
		PID_P = PID_P_e.getText();
		PID_I = PID_I_e.getText();
		PID_D = PID_D_e.getText();
		
		if(PID_P.equals("") || PID_I.equals("") || PID_D.equals("") 
				|| Object_RPM.equals("") || Motor_Gear.equals("") || Motor_Pulse.equals("")) {
			Toast.makeText(activity, "항목을 모두 입력하세요", Toast.LENGTH_SHORT).show();
		} else {
			
			((PageChanger)activity).setNextPage();

		}
	}
}
