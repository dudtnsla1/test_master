package testmaster.android.page;

import testmaster.android.packet.SettingPacket;

public interface PageChangeListener {
	SettingPacket settingChanged();
	void saveSettingData();
	int pageChanged(int pageNum);
}
