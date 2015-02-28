package testmaster.android.page;

import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.R;
import android.R.string;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FunctionHighLowContext extends FunctionContext implements OnClickListener{

	ToggleButton toggle1;
	ToggleButton toggle2;
	ToggleButton toggle3;
	ToggleButton toggle4;

	public FunctionHighLowContext(Context context) {
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
		toggle1 =((ToggleButton) activity.findViewById(R.id.highlow1));
		toggle1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String state1 = toggle1.getText().toString();
				Toast.makeText(activity, "1번 "+state1, Toast.LENGTH_SHORT).show();
				packet.setHightLowPacket((byte)0, (byte)0);
			}
		});
		toggle2=((ToggleButton) activity.findViewById(R.id.highlow2));
		toggle2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String state2 = toggle2.getText().toString();
				Toast.makeText(activity, "2번 "+state2, Toast.LENGTH_SHORT).show();
				packet.setHightLowPacket((byte)0, (byte)0);
			}
		});
		toggle3=((ToggleButton) activity.findViewById(R.id.highlow3));
		toggle3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String state3 = toggle3.getText().toString();
				Toast.makeText(activity, "3번 "+state3, Toast.LENGTH_SHORT).show();
				packet.setHightLowPacket((byte)0, (byte)0);
			}
		});
		toggle4=((ToggleButton) activity.findViewById(R.id.highlow4));
		toggle4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String state4 = toggle4.getText().toString();
				Toast.makeText(activity, "4번 "+state4, Toast.LENGTH_SHORT).show();
				packet.setHightLowPacket((byte)0, (byte)0);
			}
		});

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



	@Override
	public void onClick(View v) {
	}
}

