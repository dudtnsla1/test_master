
package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.chart.ChartFacade;
import testmaster.android.chart.GraphicalActivity;
import testmaster.android.database.DbOpenProxy;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.app.Activity;
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

public class FunctionADCContext extends FunctionContext implements
		OnClickListener, OnItemSelectedListener, OnCheckedChangeListener {
	
	private Button keypad[] = new Button[10];	
	private int keypadIds[] = {
			R.id.one,
			R.id.two,
			R.id.three,
			R.id.four,
			R.id.five,
			R.id.six,
			R.id.seven,
			R.id.eight,
			R.id.nine,
			R.id.zero
	};
	
	privatei 
	
	plus = (Button) activity.findViewById(R.id.plus);
	minus = (Button) activity.findViewById(R.id.minus);
	divide = (Button) activity.findViewById(R.id.divide);
	multiply = (Button) activity.findViewById(R.id.multiply);
	equal = (Button) activity.findViewById(R.id.equal);
	backspace = (Button) activity.findViewById(R.id.backspace);
	leftguard = (Button) activity.findViewById(R.id.leftguard);
	rightguard = (Button) activity.findViewById(R.id.rightguard);
	result_y = (Button) activity.findViewById(R.id.result_y);
	value_x = (Button) activity.findViewById(R.id.value_x);

	private Button func[] = new Button[10];
	// 수식 입력 버튼

	private Button result_y;
	private Button value_x;

	private EditText text;
	private String string;
	private String temp;
	private int length;
	private int yunsan;
	float sum = 0;
	Boolean on = false;
	Boolean clean = false;

	/**
	 * Setting Page
	 * 
	 * @author Administrator
	 *
	 */

	private Button configure;
	private Spinner pinSpinner;
	// private RadioGroup adcValueCalibrationSelect;
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
		databaseDrawer = new ChartFacade(dbHelper,
				(GraphicalActivity) activity, ChartFacade.KIND_ADC);
		pageChanger.setEnable();
	}

	private void setUnitSpinner(Activity context) {

		pinSpinner = (Spinner) activity
				.findViewById(R.id.function_adc_pinSpinner);
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
				context, R.array.adc_pin, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		pinSpinner.setAdapter(adapter2);
		pinSpinner.setOnItemSelectedListener(this);
	}

	private void initFirstPage(Activity context) {
		setUnitSpinner(context);
		// adcValueCalibrationSelect =
		// (RadioGroup)activity.findViewById(R.id.function_content_adc_dataconfig_radio_group);
		// adcValueCalibrationSelect.setOnCheckedChangeListener(this);
		configure = (Button) activity.findViewById(R.id.function_adc_setting);
		configure.setOnClickListener(this);

		text = (EditText) activity.findViewById(R.id.edittext_cal);

		one.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("1");
			}
		});

		two.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("2");
			}
		});

		three.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("3");
			}
		});

		four.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("4");
			}
		});

		five.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("5");
			}
		});

		six.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("6");
			}
		});

		seven.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("7");
			}
		});

		eight.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("8");
			}
		});

		nine.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("9");
			}
		});

		zero.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (on != true)
					function("0");
			}
		});

		plus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function2("+");
			}
		});

		minus.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function2("-");
			}
		});

		multiply.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function2("*");
			}
		});

		divide.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function2("÷");
			}
		});

		leftguard.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function3("(");
			}
		});
		rightguard.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function3(")");
			}
		});
		result_y.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("Y");
			}
		});
		value_x.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function("X");
			}
		});

		equal.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				function2("=");
			}
		});
		backspace.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (clean == true) {
					text.setText("");
					clean = false;
				} else if (!text.getText().toString().equals("")) {
					text.setText(text
							.getText()
							.toString()
							.substring(0,
									text.getText().toString().length() - 1));
					if (on == true)
						on = false;
				}
			}
		});

	}

	void function(String a) {
		string = text.getText().toString();
		length = text.getText().toString().length();

		if (string.equals("") || string.equals("0")) {
			text.setText("" + a);
			on = false;
		} else if (clean == true) {
			text.setText("" + a);
			clean = false;
		} else {
			text.setText(string + a);
			on = false;
		}
	}

	void function2(String a) {
		string = text.getText().toString();
		if (clean == true) {
			text.setText("");
			clean = false;

		} else if (on == false) {
			text.setText(string + a);
			on = true;
		}
	}

	void function3(String a) {
		string = text.getText().toString();

		text.setText(string + a);

	}

	private void initSecondPage(Activity context) {
		chart = ((GraphicalActivity) activity).getLineChartGraphicalView(0, 80,
				0, 3500);
		setBarChart(chart);
		barChartListener = new ChartOnClickAdpater(dbHelper, context,
				ChartOnClickAdpater.KIND_ADC);
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
	 * callBack, called When Function Setting changing
	 * 
	 * @param context
	 */
	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setADCPacket((byte) pinNumber);
		return packet;
	}

	/**
	 * unit spinner listener
	 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		switch (arg0.getId()) {

		case R.id.function_adc_pinSpinner:
			pinNumber = Integer.parseInt((String) arg0.getSelectedItem());
			Log.d("TestBoard FunctionADCContext", pinNumber + "");
			break;
		}

		// unitSpinner.setPrompt((CharSequence) arg0.getSelectedItem());
		// TODO Auto-generated method stub
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	/**
	 * radio group listener
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

		// if (checkedId == R.id.function_content_adc_adc_calibrationradio) {
		// LinearLayout layout =
		// (LinearLayout)activity.findViewById(R.id.function_adc_modify_layout);
		// layout.setVisibility(View.VISIBLE);
		// } else {
		// LinearLayout layout =
		// (LinearLayout)activity.findViewById(R.id.function_adc_modify_layout);
		// layout.setVisibility(View.INVISIBLE);
		// }
	}

	@Override
	public void saveSettingData() {

		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		// 여기에 수식이랑 핀번호 확인!!

		((PageChanger) activity).setNextPage();
		// TODO Auto-generated method stub
	}

	@Override
	public void destroy() {
		dbHelper.close();
		super.destroy();
	}
}