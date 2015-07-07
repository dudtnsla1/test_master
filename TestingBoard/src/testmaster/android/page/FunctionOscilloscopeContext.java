package testmaster.android.page;

import testmaster.android.chart.ChartUpdateAdeptor;
import testmaster.android.chart.GraphicalActivity;
import testmaster.android.chart.PreferenceChartInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.usbserial.OscilloCommand;
import testmaster.android.usbserial.UsbSerialManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class FunctionOscilloscopeContext extends FunctionContext implements PreferenceChartInfo {
	private OscChartOnClickAdapter oscChartListener;
	private UsbSerialManager usbManager;
	private OscChartOnClickAdapter chartFacade;

	private OscilloCommand oscilloCommand = new OscilloCommand() {

		private double chartData[];

		private ChartUpdateAdeptor chartAdeptor = new ChartUpdateAdeptor() {

			@Override
			public double[] getIndex() {
				// TODO Auto-generated method stub
				return chartData;
			}
		};

		@Override
		public void oscilloLoop(double[] data, int lowestData, int highestData) {
			// TODO Auto-generated method stub 
			
			if (chartFacade.isUsbEnable()) {
				chartData = data;
				((GraphicalActivity)activity).resetLineChart(1);
				((GraphicalActivity)activity).updateChart(1, chartAdeptor);
				updateChart();

				if (chartFacade.isAutoScaleable())
					((GraphicalActivity)activity).setLables(0, 1000, lowestData - 10, highestData + 10);
			}
		}
	};

	public FunctionOscilloscopeContext(MainFunctionActivity context) {

		super(context);
		// TODO Auto-generated constructor stub
	}

	private float maxAxisY = 1;

	protected void updateTemplate(String data) {
	}

	public void autoSacle() 
	{
		((GraphicalActivity)activity).setYAxisMax(maxAxisY);
	}

	@Override
	public void updatePreference() {
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(activity);
		String x_max = "100";
		String y_max = "100";
		//		String y_max = preference.getString(KEY_Y_MAX, "3500");
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
		packet.setOscilloPacket();
		packet.sendPacket(packet.getPacket());
		chartFacade = new OscChartOnClickAdapter(context);
		usbManager = new UsbSerialManager(context, oscilloCommand);
		chart = ((GraphicalActivity)activity).getLineChartGraphicalView(0, 80, 0, 3500);

		setBarChart(chart);
		updatePreference();

//		oscChartListener = new OscChartOnClickAdapter(activity, this);
//		oscChartListener.setOSCChartListener();		
	}

	private void initSecondPage(Activity context) {

	}

	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub

	}
}
