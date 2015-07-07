package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.chart.ChartFacade;
import testmaster.android.chart.GraphicalActivity;
import testmaster.android.database.DbOpenProxy;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FunctionMotorContext extends FunctionContext implements OnClickListener{


	Button btn_Send_PID;

	private NotNullEditTextDecorator editsListenDeco;

	private EditText PID_P_e;
	private EditText PID_I_e;
	private EditText PID_D_e;
	private EditText Object_RPM_e;
	private EditText Motor_Gear_e;
	private EditText Motor_Pulse_e;

	private String PID_P="";
	private String PID_I="";
	private String PID_D="";
	private String Object_RPM="";
	private String Motor_Gear="";
	private String Motor_Pulse="";

	private DbOpenProxy dbHelper;
	private GraphicalView chart;
	private ChartOnClickAdpater barChartListener;
	private ChartFacade databaseDrawer;

	@Override
	public void destroy() {
		dbHelper.close();
		super.destroy();
	}

	@Override
	protected void updateTemplate(String data) {
		// TODO Auto-generated method stub
		super.updateTemplate(data);
	}

	@Override
	public void updatePreference() {
		databaseDrawer.updateSelectedDatabases();
		databaseDrawer.updateLables();
	}

	public FunctionMotorContext(MainFunctionActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
		dbHelper = new DbOpenProxy(context);
		dbHelper.open();
		databaseDrawer = new ChartFacade(dbHelper, (GraphicalActivity)activity, ChartFacade.KIND_MOTOR);
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setMotorPacket(editsListenDeco.getText(PID_P_e), 
				editsListenDeco.getText(PID_I_e), 
				editsListenDeco.getText(PID_D_e), 
				editsListenDeco.getText(Object_RPM_e), 
				editsListenDeco.getText(Motor_Gear_e),
				editsListenDeco.getText(Motor_Pulse_e));
		return packet;
	}

	public void initFirstPage() {
		pageChanger.setDisable();
		editsListenDeco = new NotNullEditTextDecorator(activity, pageChanger);
		Motor_Gear_e = (EditText) activity.findViewById(R.id.pid_gear_e);
		Motor_Pulse_e =(EditText) activity.findViewById(R.id.pid_pulse_e);
		Object_RPM_e= (EditText) activity.findViewById(R.id.object_RPM);
		PID_P_e= (EditText) activity.findViewById(R.id.pid_p_Edit);
		PID_I_e= (EditText) activity.findViewById(R.id.pid_i_Edit);		
		PID_D_e= (EditText) activity.findViewById(R.id.pid_d_Edit);

		editsListenDeco.addEditText(Motor_Gear_e);
		editsListenDeco.addEditText(Motor_Pulse_e);
		editsListenDeco.addEditText(Object_RPM_e);
		editsListenDeco.addEditText(PID_P_e);
		editsListenDeco.addEditText(PID_I_e);
		editsListenDeco.addEditText(PID_D_e);

		btn_Send_PID = (Button) activity.findViewById(R.id.btn_PID);
		btn_Send_PID.setOnClickListener(this);

		packet.setInitPacket();
		packet.sendPacket(packet.getPacket());
	}

	public void initSecondPage() {
		chart = ((GraphicalActivity)activity).getLineChartGraphicalView(0, 100, 0, 1000);
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
		pageChanger.setNextPage();
	}
}
