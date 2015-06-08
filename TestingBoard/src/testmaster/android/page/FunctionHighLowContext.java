package testmaster.android.page;

import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FunctionHighLowContext extends FunctionContext implements OnClickListener{

	private ToggleButton toggle[] = new ToggleButton[4];
	private int  toggleIds[] = {
			R.id.highlow1,
			R.id.highlow2,
			R.id.highlow3,
			R.id.highlow4
	};

	private String state = "off";

	public FunctionHighLowContext(MainFunctionActivity context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		return null;
	}

	public byte onOffParseByte(String string) {		
		if (string.equals("On")) {
			return 1;
		}
		return 0;
	}

	@Override
	public int pageChanged(int pageNum) {		

		for (int i = 0; i < 4; i++) {
			toggle[i] = (ToggleButton) activity.findViewById(toggleIds[i]);
			toggle[i].setContentDescription((i + 1) + "¹ø");
			toggle[i].setOnClickListener(this);
		}

		return 0;
	}

	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub
	}

	@Override
	public void onClick(View v) {
		state = ((ToggleButton)v).getText().toString();
		Toast.makeText(activity, v.getContentDescription() + state, Toast.LENGTH_SHORT).show();
		packet.setHightLowPacket((byte)0, onOffParseByte(state));
		packet.sendPacket(packet.getPacket());		
	}
}

