package testmaster.android.page;

import org.achartengine.GraphicalView;

import testmaster.android.chart.GraphicalActivity;
import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import android.content.Context;
import android.content.pm.PackageInfo;

public class FunctionPWMContext extends FunctionContext{
	GraphicalView lineChart;

	public FunctionPWMContext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setPacket(PacketInfo.MODE_PWM);
		return packet;
	}

	@Override
	public int pageChanged(int pageNum) {
		if (pageNum == 1) {
			lineChart = ((GraphicalActivity)activity).getLineChartGraphicalView(0, 30, 0, 40);
			setLineChart(lineChart);
		}
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateChartTemplate() {
		// TODO Auto-generated method stub
		
	}
}
