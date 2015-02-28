package testmaster.android.page;

import testmaster.android.packet.SettingPacket;
import android.content.Context;

public class FunctionUSARTContext extends FunctionContext{

	public FunctionUSARTContext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	public int pageChanged(int pageNum) {
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

