package testmaster.android.page;

import testmaster.android.chart.PreferenceChartInfo;
import testmaster.android.database.DataObservable;
import testmaster.android.database.DbOpenProxy;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class ChartOnClickAdpater implements OnClickListener, PreferenceChartInfo{
	
	private DbOpenProxy dbHelper;
	private Activity activity;
	private int dataKind;	
 
	public ChartOnClickAdpater(DbOpenProxy dbHelper, Activity activity , int dataKind) {
		this.activity = activity;
		this.dbHelper = dbHelper;
		this.dataKind = dataKind;
	}
	
	public void setBarChartListener() {
		((ImageButton)activity.findViewById(R.id.barchart_play_btn)).setOnClickListener(this);
		((ImageButton)activity.findViewById(R.id.barchart_stop_btn)).setOnClickListener(this);
		((ImageButton)activity.findViewById(R.id.barchart_upload_btn)).setOnClickListener(this);
		((ImageButton)activity.findViewById(R.id.barchart_reset_btn)).setOnClickListener(this);
	}
	
	private void streamStart() {
		DataObservable.enableUpate();
	}
	
	private void streamStop() {
		DataObservable.disableUpdate();		
	}
	
	private void dataBaseUpload() {
		
		AlertDialog.Builder alert = new AlertDialog.Builder(activity);
		alert.setTitle("데이터베이스 저장");
		alert.setMessage("테스트 케이스 명칭을 입력해 주세요.");
		// Set an EditText view to get user input

		final EditText input = new EditText(activity);
		alert.setView(input);
		alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				String value = input.getText().toString();
				dbHelper.insertDatabase(value, dataKind);
				// Do something with value!		
			}
		});
		alert.setNegativeButton("Cancel",

				new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// Canceled.
			}
		});		
		alert.show(); 		
		
	}	
	
	private void streamReset() {
		DataObservable.resetData();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.barchart_play_btn:
			streamStart();
			break;
		case R.id.barchart_stop_btn:
			streamStop();
			break;
		case R.id.barchart_upload_btn:
			dataBaseUpload();
			break;			
		case R.id.barchart_reset_btn:
			streamReset();
			break;
		}		
	}
}
