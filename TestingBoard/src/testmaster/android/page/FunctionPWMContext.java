package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.chart.ChartUpdateAdeptor;
import testmaster.android.chart.GraphicalActivity;
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
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

public class FunctionPWMContext extends FunctionContext implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener, ChartUpdateAdeptor{
	private String SendHz_Frequency="0";
	private String Pin_number_String="0";
	private String dan=null;
	private String result = "";
	private GraphicalView lineChart;
	private Button setting;
	private EditText editText;
	private EditText pwm_duty_e;
	private String pwm_duty_s = "50";
	private double[]data = new double[8];
	private double[]xList = new double[8];

	private Spinner hzSpinner;
	private Spinner Pin_number_pwm;
	
	public void drawDuty(int duty) {
		data[0] = 5;
		data[1] = 5;
		data[2] = 90;
		data[3] = 90;
		data[4] = 5;
		data[5] = 5;
		data[6] = 90;
		data[7] = 90;
		
		xList[0] = 0;
		xList[1] = 10;
		xList[2] = 10;
		xList[3] = duty+10;
		xList[4] = duty+10;
		xList[5] = 110;
		xList[6] = 110;
		xList[7] = 120;
		
		((GraphicalActivity)activity).resetLineChart(2);
		((GraphicalActivity)activity).updateChart(2, this, xList);
	}

	public FunctionPWMContext(Context context) {

		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void updateTemplate(String data) {
		// TODO Auto-generated method stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setPWMPacket(Byte.parseByte(Pin_number_String), Byte.parseByte(SendHz_Frequency));
		return packet;
	}
	
	private void setUnitSpinner(Activity context) {
		hzSpinner = (Spinner)activity.findViewById(R.id.pwmspinner1);
		ArrayAdapter<CharSequence> pwmadapter = ArrayAdapter.createFromResource(context, R.array.pwm_hz, android.R.layout.simple_spinner_item);
		pwmadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		hzSpinner.setAdapter(pwmadapter);
		hzSpinner.setOnItemSelectedListener(this);

		Pin_number_pwm = (Spinner)activity.findViewById(R.id.pwm_pin);
		ArrayAdapter<CharSequence> pwmadapter2 = ArrayAdapter.createFromResource(context, R.array.pwm_pin_num, android.R.layout.simple_spinner_item);
		pwmadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Pin_number_pwm.setAdapter(pwmadapter2);
		Pin_number_pwm.setOnItemSelectedListener(this);



		editText = (EditText) activity.findViewById(R.id.editText1);
		setting = (Button) activity.findViewById(R.id.function_pwm_setting);
	}

	@Override
	public int pageChanged(int pageNum) {
		if (pageNum == 0) {
			initFirstPage(activity);
		}
		else if (pageNum == 1) {
			initSecondPage(activity);
		}

		setting.setOnClickListener(this);


		return 0;
	}

	private void initFirstPage(Activity context) {
		setUnitSpinner(context);
		chart = ((GraphicalActivity)activity).getLineChartGraphicalView(0, 120, 0, 100);
		((GraphicalActivity)activity).setOrientationHorizontal();
		setBarChart(chart); 
		pwm_duty_e = (EditText) activity.findViewById(R.id.duty_rate_e);		
	}

	private void initSecondPage(Activity context) {

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
		dan =parent.getItemAtPosition(position).toString();
		Pin_number_String=parent.getItemAtPosition(position).toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub


		try	{
			SendHz_Frequency=editText.getText().toString();
			pwm_duty_s=pwm_duty_e.getText().toString();

		} catch(Exception e){
		}

		if(SendHz_Frequency.equals("")||pwm_duty_s.equals("")) {
			Toast.makeText(activity, "항목을 모두 입력하세요.",Toast.LENGTH_SHORT ).show();
		} else {
			if(dan.equals("hz")) {
				result = SendHz_Frequency;
			} else if(dan.equals("Khz")) {
				result = SendHz_Frequency+"000"; 
			} else if(dan.equals("Mhz")) {
				result = SendHz_Frequency+"000000";
			}

			if(Integer.parseInt(result)>72000000)
			{

			}
			if(Integer.parseInt(result)<1000)
			{

			}

			drawDuty(Integer.parseInt(pwm_duty_s));

			Toast.makeText(activity, result,Toast.LENGTH_SHORT ).show();
		}
	}

	@Override
	public double[] getIndex() {
		// TODO Auto-generated method stub
		return data;
	}

}
