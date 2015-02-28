package testmaster.android.testingboard;

import testmaster.android.bluetoothobserver.BluetoothObserver;
import testmaster.android.chart.GraphicalActivity;
import testmaster.android.page.PageChanger;
import testmaster.android.page.ViewPagerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainFunctionActivity extends GraphicalActivity implements OnClickListener, PageChanger{

	private static final int []PAGES_ADC = {R.layout.function_content_adc_activity1, R.layout.barchart_layout};
	private static final int []PAGES_PWM = {R.layout.function_content_pwm_activity1, R.layout.linechart_layout};
	private static final int []PAGES_USART = {R.layout.function_content_usart_activity1, R.layout.function_content_usart_activity2};
	private static final int []PAGES_I2C = {R.layout.function_content_i2c_activity1, R.layout.function_content_i2c_activity2};
	private static final int []PAGES_CLOCK = {R.layout.function_content_highlow_activity1};
	private static final int []PAGES_MOTOR = {R.layout.function_content_motor_activity1};
	
	private final int REQUEST_CODE_PREFERENCE = 1;

	public ViewPagerAdapter pagerAdapter;
	public ViewPager viewPager;
	
	private TextView title;
	
	public enum ContentLayoutIds {
		ADC(PAGES_ADC, 0), PWM(PAGES_PWM, 0), USART(PAGES_USART, 0), I2C(PAGES_I2C, 0), CLOCK(PAGES_CLOCK, 0), MOTOR(PAGES_MOTOR, 0);
		int []ids;
		int settingLayoutNum;
		ContentLayoutIds(int []ids, int settingLayoutNum) {
			this.ids = ids;
			this.settingLayoutNum = settingLayoutNum;
			// TODO Auto-generated constructor stub
		}
		
		public int getSettingChangeIndex() {
			return settingLayoutNum;
		}
		
		public int []getPageIds() {
			return ids;
		}
	}
	
	public static final ContentLayoutIds contentLayoutIds[] = {
		ContentLayoutIds.ADC, 
		ContentLayoutIds.PWM,
		ContentLayoutIds.USART,
		ContentLayoutIds.I2C,
		ContentLayoutIds.CLOCK,
		ContentLayoutIds.MOTOR
	};
	
	private BluetoothObserver dataRecvObserver = new BluetoothObserver("Function Observer") {	
		private double parsingData(String data) {
			return Double.parseDouble(data);

		}
		
		@Override
		public void update(String data) {
			updateCurrent(parsingData(data));
			pagerAdapter.updateChart();
			Log.d("TestingBoard MaingFunctionActivity", "bluetooth Observer Updated:" + data);			
		}

		@Override
		public void bluetoothDisconnect() {
			// TODO Auto-generated method stub
		}

		@Override
		public void resetData() {
			// TODO Auto-generated method stub
			resetLineChart(0);
			pagerAdapter.updateChart();
		}
	};	
	
	private void ObserverInit() {
		dataRecvObserver.insertObserver(); 
	}
	
	@Override
	public void onBackPressed() {
		dataRecvObserver.deleteObserver();
		Log.d("TestingBoard MaingFunctionActivity", "bluetooth Observer Delete");
		pagerAdapter.destroy();
		super.onBackPressed();
	};
	
	private void pageInit(Intent intent) {		
		ContentLayoutIds layoutId = (ContentLayoutIds)intent.getExtras().getSerializable(MainHomeActivity.extraFunctionLayout);
		viewPager = (ViewPager)findViewById(R.id.main_fucntion_container_viewpager);
		pagerAdapter = new ViewPagerAdapter(this, layoutId);
		viewPager.setAdapter(pagerAdapter);
		title = (TextView)findViewById(R.id.main_function_container_title);
		title.setText(layoutId.toString());
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		super.onCreate(savedInstanceState);
		chartInit();
		setContentView(R.layout.main_function_container);
		pageInit(getIntent());
		ObserverInit();
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_intro, menu);
		return super.onCreateOptionsMenu(menu);
	};	


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d("TestingBoard MainFuntionActivity", "test");
		setLables(0, 100, 0, 100);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			onBackPressed();
			break;
		case R.id.function_menu_configure:
			Intent intent = new Intent(MainFunctionActivity.this, FunctionPreferenceActivity.class);
			startActivityForResult(intent, REQUEST_CODE_PREFERENCE);
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		pagerAdapter.updatePreference();
		super.onActivityResult(requestCode, resultCode, intent);
	}

	@Override
	public void setNextPage() {
		// TODO Auto-generated method stub
		viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
	}
}