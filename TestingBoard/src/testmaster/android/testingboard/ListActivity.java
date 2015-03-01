package testmaster.android.testingboard;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import testmaster.android.chart.PreferenceChartInfo;
import testmaster.android.database.DataBases;
import testmaster.android.database.DbOpenHelper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

public class ListActivity extends Activity implements OnItemClickListener, PreferenceChartInfo{
	ListPreference list;
	ListView listView;
	ArrayAdapterListItem adapter;
	DbOpenHelper dbHelper;
	Cursor cursor;
	SharedPreferences preference;
	Editor editor;
	String key;

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		editor.putInt(key, position - 1);
		editor.apply();
	}
	
	public void init() {
		listView = (ListView)findViewById(R.id.list_listview);
		adapter = new ArrayAdapterListItem(this, R.layout.listview_item);
		dbHelper = new DbOpenHelper(this);
		dbHelper.open();
		preference = PreferenceManager.getDefaultSharedPreferences(this);
		editor = preference.edit();

		Intent intent = getIntent();
		String kind = intent.getExtras().getString(FunctionPreferenceActivity.EXTRA_KIND);
		int order = intent.getExtras().getInt(FunctionPreferenceActivity.EXTRA_ORDER);
		
		if (kind.equals(FunctionPreferenceActivity.ADC_DATA_DEP)) {
			cursor = dbHelper.getInfo(DbOpenHelper.KIND_ADC);
			key = KEYS_ADC[order];
		} else if (kind.equals(FunctionPreferenceActivity.MOTOR_DATA_DEP)) {
			cursor = dbHelper.getInfo(DbOpenHelper.KIND_MOTOR);
			key = KEYS_MOTOR[order];
		}
		
		cursor.moveToFirst();
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}

	@Override
	protected void onCreate(final Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_activity);
		init();
	}
	public class ArrayAdapterListItem extends BaseAdapter implements Filterable {

		private Context mContext; 
		private int mFieldId = 0;

		public ArrayAdapterListItem(Context context, int textViewResourceId) {
			super();
			mContext = context;
			mFieldId = textViewResourceId;
		}

		@Override
		public int getCount() {
			return cursor.getCount() + 1;
		}

		@Override
		public Object getItem(int position) {
			String result = new String(position+"");
			return result;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View v = convertView; 
			
			if (v == null) { 
				LayoutInflater vi = LayoutInflater.from(mContext); 
				v = vi.inflate(mFieldId, null);
			}
			
			if (position == 0) {
				TextView t1_1 = (TextView) v.findViewById(R.id.listview_sub1_1);  
				t1_1.setText("사용안함");                        
				TextView t1_2 = (TextView) v.findViewById(R.id.listview_sub1_2);  
				t1_2.setText("");        
				TextView t2_1 = (TextView) v.findViewById(R.id.listview_sub2_1);  
				t2_1.setText("");
				return v;
			}			

			position -= 1;
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat dateForm = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			if (cursor.moveToPosition(position)) {
				TextView t1_1 = (TextView) v.findViewById(R.id.listview_sub1_1);  
				t1_1.setText("차수 : " + cursor.getInt(cursor.getColumnIndexOrThrow(DataBases.CreateDB._ID)));                        
				TextView t1_2 = (TextView) v.findViewById(R.id.listview_sub1_2);  
				t1_2.setText("이름 : " + cursor.getString(cursor.getColumnIndexOrThrow(DataBases.CreateDB.NAME)));        

				cal.setTimeInMillis((long) cursor.getDouble(cursor.getColumnIndexOrThrow(DataBases.CreateDB.DATE)));
				TextView t2_1 = (TextView) v.findViewById(R.id.listview_sub2_1);  
				t2_1.setText(dateForm.format(cal.getTime()));
			}
			return v; 
		}

		@Override
		public Filter getFilter() {
			return null;
		}
	}
}
