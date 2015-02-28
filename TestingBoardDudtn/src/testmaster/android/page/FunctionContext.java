package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

public abstract class FunctionContext implements PageChangeListener{
	
	protected Activity activity;
	private LinearLayout barChartLayout;
	private LinearLayout lineChartLayout;
	protected SettingPacket packet = new SettingPacket();
	GraphicalView chart = null;
	
	abstract protected void updateChartTemplate();
	
	public void updatePreference() {
		
	}
	
	public void destroy() {
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
	
	protected void setLineChart(GraphicalView chart) {
		this.chart = chart;
		lineChartLayout = (LinearLayout)activity.findViewById(R.id.linechart_layout);
		lineChartLayout.removeAllViews();
		lineChartLayout.addView(chart);		
	}
	
	protected FunctionContext(Context context) {
		// TODO Auto-generated constructor stub
		activity = (Activity)context;
	}	
}