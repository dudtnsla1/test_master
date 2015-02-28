package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.bluetoothobserver.BluetoothObserver;
import testmaster.android.chart.GraphicalActivity;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.LinearLayout;

public abstract class FunctionContext implements PageChangeListener{
	
	protected Activity activity = null;
	private LinearLayout barChartLayout;
	protected SettingPacket packet = new SettingPacket();
	GraphicalView chart = null;		

	private void ObserverInit() {
		dataRecvObserver.insertObserver(); 
	}

	private double parsingData(String data) {
		return Double.parseDouble(data);
	}
	
	protected void update(String data) {
		((GraphicalActivity)activity).updateCurrent(parsingData(data));
		updateChart();
		Log.d("TestingBoard MaingFunctionActivity", "bluetooth Observer Updated:" + data);				
	}	
	
	private BluetoothObserver dataRecvObserver = new BluetoothObserver("Function Observer") {	
		
		@Override
		public void update(String data) {	
		}

		@Override
		public void bluetoothDisconnect() {
			// TODO Auto-generated method stub
		}

		@Override
		public void resetData() {
			// TODO Auto-generated method stub
			((GraphicalActivity)activity).resetLineChart(0);
			updateChart();
		}
	};	
	
	abstract protected void updateChartTemplate();
	
	public void updatePreference() {
		
	}
	
	public void destroy() {
		packet.setInitPacket();
		packet.sendPacket(packet.getPacket());
		dataRecvObserver.deleteObserver();
	}
	
	public void updateChart() {
		if (chart != null)
			chart.repaint();
	}
		
	protected void setBarChart(GraphicalView chart) {
		this.chart = chart;
		barChartLayout = (LinearLayout)activity.findViewById(R.id.barchart_layout);
		barChartLayout.removeAllViews();
		barChartLayout.addView(chart);
	}
		
	protected FunctionContext(Context context) {
		// TODO Auto-generated constructor stub
		activity = (Activity)context;
		ObserverInit();
	}

	
}