package testmaster.android.page;

import testmaster.android.testingboard.MainIntroActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

public class OnSpinnerItemClicked implements OnItemSelectedListener {
	private Spinner master, slave;
	
	private int position = 0;
	
	public int getPosition() {
		return position;
	}

	public OnSpinnerItemClicked(Spinner master, Spinner slave) {
		// TODO Auto-generated constructor stub
		this.master = master;
		this.slave = slave;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		if (parent == master && position != 0) {
			slave.setSelection(0);
			Log.d(MainIntroActivity._DEBUG_TAG + " OnSpinnerItemClicked", "Master");
		} else if (parent == slave && position != 0){
			master.setSelection(0);
			Log.d(MainIntroActivity._DEBUG_TAG + " OnSpinnerItemClicked", "Slave");
		}
		this.position = position;
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
