package testmaster.android.chart;

import testmaster.android.database.DataBases.CreateDB;
import testmaster.android.database.DbOpenHelper;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;

public class ChartFacade implements PreferenceChartInfo, ChartUpdateAdeptor {
	private GraphicalActivity chartActivity;
	private DbOpenHelper dbHelper;
	private Cursor cursor = null;
	private int kind;
	
	public ChartFacade (DbOpenHelper dbHelper, GraphicalActivity chartActivity, int kind) {
		this.chartActivity = chartActivity;
		this.dbHelper = dbHelper;
		this.kind = kind;
	}
	
	private Cursor getFromDataTable(long date) {
		return dbHelper.getData(date);
	}
	
	public void updateSelectedDatabases() {
		String []keys = null;
		if (kind == KIND_ADC)
			keys = KEYS_ADC;
		else if (kind == KIND_MOTOR)
			keys = KEYS_MOTOR;
		
		SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(chartActivity);
		
		for (int i = 0; i < keys.length; i++) {
			int index = preference.getInt(keys[i], -1);
			chartActivity.resetLineChart(i + 1);
			if (index == -1) {
				continue;
			}
			
			cursor = dbHelper.getInfo(kind);
			cursor.moveToPosition(index);
			long date = cursor.getLong(cursor.getColumnIndexOrThrow(CreateDB.DATE));

			cursor = getFromDataTable(date);
			chartActivity.updateChart(i + 1, this);
		}
	}

	@Override
	public double []getIndex() {
		// TODO Auto-generated method stub
		double []data = new double[cursor.getCount()];
		
		for (int i = 0; i < data.length; i++) {
			cursor.moveToPosition(i);
			data[i] = cursor.getDouble(cursor.getColumnIndexOrThrow(CreateDB.VALUE));
		}
		return data;
	}
}
