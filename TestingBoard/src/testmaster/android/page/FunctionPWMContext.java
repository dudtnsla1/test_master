package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.chart.GraphicalActivity;
import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class FunctionPWMContext extends FunctionContext implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener{
	String SendHz_Frequency=null;
	String dan=null;
	String result;
	GraphicalView lineChart;
	Button setting;
	EditText editText;

	private Spinner hzSpinner;


	public FunctionPWMContext(Context context) {

		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setPacket(PacketInfo.MODE_PWM, Integer.parseInt(SendHz_Frequency));
		return packet;
	}
	private void setUnitSpinner(Activity context) {
		hzSpinner = (Spinner)activity.findViewById(R.id.pwmspinner1);
		ArrayAdapter<CharSequence> pwmadapter = ArrayAdapter.createFromResource(context, R.array.pwm_hz, android.R.layout.simple_spinner_item);
		pwmadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		hzSpinner.setAdapter(pwmadapter);
		hzSpinner.setOnItemSelectedListener(this);
		editText = (EditText) activity.findViewById(R.id.editText1);
		setting = (Button) activity.findViewById(R.id.function_pwm_setting);
	}

	@Override
	public int pageChanged(int pageNum) {
		if (pageNum == 0) {
			initFirstPage(activity);
		}
		else if (pageNum == 1) {
			lineChart = ((GraphicalActivity)activity).getLineChartGraphicalView(0, 30, 0, 40);
			setLineChart(lineChart);
		}

		setting.setOnClickListener(this);
		

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
		dan =parent.getItemAtPosition(position).toString();


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
		} catch(Exception e){
			Toast.makeText(activity, "주파수를 설정하세요",Toast.LENGTH_SHORT ).show();
		}

		if(SendHz_Frequency != null) {

			if(dan.equals("hz")) {
				result = SendHz_Frequency;
			} else if(dan.equals("Khz")) {
				result = SendHz_Frequency+"000"; 
			} else if(dan.equals("Mhz")) {
				result = SendHz_Frequency+"000000";
			}

			Toast.makeText(activity, result,Toast.LENGTH_SHORT ).show();
		} else {
			Toast.makeText(activity, "주파수를 설정하세요",Toast.LENGTH_SHORT ).show();
		}
	}
}
