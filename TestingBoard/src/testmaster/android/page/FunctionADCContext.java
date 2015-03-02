package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.chart.ChartFacade;
import testmaster.android.chart.GraphicalActivity;
import testmaster.android.database.DbOpenProxy;
import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class FunctionADCContext extends FunctionContext implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener{

	/**
	 * Setting Page
	 * @author Administrator
	 *
	 */

	private Button configure;
	private EditText modify;
	private Spinner unitSpinner;
	private Spinner pinSpinner;
	//private RadioGroup adcValueCalibrationSelect;
	private GraphicalView chart;
	private ChartOnClickAdpater barChartListener;
	private ChartFacade databaseDrawer;
	
	private int pinNumber;
	DbOpenProxy dbHelper;

	@Override
	public void updatePreference() {
		databaseDrawer.updateSelectedDatabases();
		databaseDrawer.updateLables();
	}
	
	public FunctionADCContext(MainFunctionActivity context) {
		// TODO Auto-generated constructor stub
		super(context);
		dbHelper = new DbOpenProxy(context);
		dbHelper.open();
		databaseDrawer = new ChartFacade(dbHelper, (GraphicalActivity)activity, ChartFacade.KIND_ADC);
		pageChanger.setEnable();
	}

	private void setUnitSpinner(Activity context) {
		unitSpinner = (Spinner)activity.findViewById(R.id.function_adc_unitSpinner);
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(context, R.array.adc_unit, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unitSpinner.setAdapter(adapter1);
		unitSpinner.setOnItemSelectedListener(this);
		
		pinSpinner = (Spinner)activity.findViewById(R.id.function_adc_pinSpinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(context, R.array.adc_pin, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pinSpinner.setAdapter(adapter2);
		pinSpinner.setOnItemSelectedListener(this);
	}

	private void initFirstPage(Activity context) {
		setUnitSpinner(context);
		//adcValueCalibrationSelect = (RadioGroup)activity.findViewById(R.id.function_content_adc_dataconfig_radio_group);
		//adcValueCalibrationSelect.setOnCheckedChangeListener(this);
		configure = (Button)activity.findViewById(R.id.function_adc_setting);
		configure.setOnClickListener(this);
		modify = (EditText)context.findViewById(R.id.function_adc_modification_edittext);
	}
	
	private void initSecondPage(Activity context) {
		chart = ((GraphicalActivity)activity).getLineChartGraphicalView(0, 80, 0, 3500);
		setBarChart(chart); 
		barChartListener = new ChartOnClickAdpater(dbHelper, context, ChartOnClickAdpater.KIND_ADC);
		barChartListener.setBarChartListener();
		updatePreference();
	}

	@Override
	public int pageChanged(int pageNum) {
		// TODO Auto-generated method stub
		if (pageNum == 0) {
			initFirstPage(activity);
		} else if (pageNum == 1) {
			initSecondPage(activity);
		}
		return 0;
	}

	/**
	 * callBack, called  When Function Setting changing
	 * @param context
	 */
	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setADCPacket((byte)pinNumber);		
		return packet;
	}

	/**
	 *  unit spinner listener */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {
		case R.id.function_adc_unitSpinner:
			break;
		case R.id.function_adc_pinSpinner:
			pinNumber = Integer.parseInt((String) arg0.getSelectedItem());
			Log.d("TestBoard FunctionADCContext", pinNumber+"");
			break;
		}

		//		unitSpinner.setPrompt((CharSequence) arg0.getSelectedItem());
		// TODO Auto-generated method stub
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 *  radio group listener */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

//		if (checkedId == R.id.function_content_adc_adc_calibrationradio) {
//			LinearLayout layout = (LinearLayout)activity.findViewById(R.id.function_adc_modify_layout);
//			layout.setVisibility(View.VISIBLE);
//		} else {
//			LinearLayout layout = (LinearLayout)activity.findViewById(R.id.function_adc_modify_layout);
//			layout.setVisibility(View.INVISIBLE);
//		}
	}

	@Override
	public void saveSettingData() {
		
		// TODO Auto-generated method stub		
	}

	@Override
	public void onClick(View v) {
		((PageChanger)activity).setNextPage();
		// TODO Auto-generated method stub
	}	
	
	@Override
	public void destroy() {
		dbHelper.close();
		super.destroy();
	}
}