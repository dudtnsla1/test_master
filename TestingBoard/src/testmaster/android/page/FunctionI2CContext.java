package testmaster.android.page;

import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import android.content.Context;

public class FunctionI2CContext extends FunctionContext{

	public FunctionI2CContext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setPacket(PacketInfo.MODE_I2C);
		return packet;
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

