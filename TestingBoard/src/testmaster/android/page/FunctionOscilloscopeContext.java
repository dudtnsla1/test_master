package testmaster.android.page;

import testmaster.android.chart.ChartUpdateAdeptor;
import testmaster.android.chart.GraphicalActivity;
import testmaster.android.chart.PreferenceChartInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class FunctionOscilloscopeContext extends FunctionContext implements ChartUpdateAdeptor, PreferenceChartInfo {
	private OscChartOnClickAdapter oscChartListener;
		
	public FunctionOscilloscopeContext(MainFunctionActivity context) {

		super(context);
		// TODO Auto-generated constructor stub
	}
	
	private float maxAxisY = 1;
	
	protected void updateTemplate(String data) {
//		Log.d("TestingBoard FunctionContext", "bluetooth Observer Updated:" + data);
		if (Integer.parseInt(data) > maxAxisY)
			maxAxisY = Integer.parseInt(data);
		super.updateTemplate(data);
	}
	
	public void autoSacle() 
	{
		((GraphicalActivity)activity).setYAxisMax(maxAxisY);
	}
	
	@Override
	public void updatePreference() {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(activity);
		String x_max = preference.getString(KEY_X_MAX, "100");
		String y_max = preference.getString(KEY_Y_MAX, "3500");
		((GraphicalActivity)activity).setLables(0, Integer.parseInt(x_max), 0, Integer.parseInt(y_max));
	}
	
	@Override
	public SettingPacket settingChanged() {
		return packet;
	}

	@Override
	public int pageChanged(int pageNum) {
		if (pageNum == 0) {
			initFirstPage(activity);
		}
		else if (pageNum == 1) {
			initSecondPage(activity);
		}

		return 0;
	}

	private void initFirstPage(Activity context) {
		chart = ((GraphicalActivity)activity).getLineChartGraphicalView(0, 80, 0, 3500);
		
		setBarChart(chart);
		updatePreference();

		oscChartListener = new OscChartOnClickAdapter(activity, this);
		oscChartListener.setOSCChartListener();		
	}

	private void initSecondPage(Activity context) {
		
	}

	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub

	}

	@Override
	public double[] getIndex() {
		// TODO Auto-generated method stub
		return null;
	}
}
