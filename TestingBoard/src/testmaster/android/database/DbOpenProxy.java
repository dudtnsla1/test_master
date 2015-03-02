package testmaster.android.database;

import java.util.ArrayList;
import java.util.Calendar;

import testmaster.android.bluetoothobserver.BluetoothObservable;
import testmaster.android.bluetoothobserver.BluetoothObserver;
import testmaster.android.database.DataBases.CreateDB;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbOpenProxy {

	private static final String DATABASE_NAME = "testingboard.db";
	private static final int DATABASE_VERSION = 1;
	public static final int KIND_ADC = 0;
	public static final int KIND_MOTOR = 1;
	
	private static SQLiteDatabase mDB = null;
	private DatabaseHelper mDBHelper = null;
	private Context mCtx;
	private int pin = 0;
	private ArrayList<String> storedData = new ArrayList<String>();
	private static boolean openSingletoneFlag = false;

	BluetoothObserver dbObserver = new BluetoothObserver("DB Observer") {

		@Override
		public void update(String data) {
			storedData.add(data);
			//insertColumn(count, port, value, date);
		}

		@Override
		public void bluetoothDisconnect() {
			// TODO Auto-generated method stub
		}

		@Override
		public void resetData() {
			// TODO Auto-generated method stub
			storedData.clear();
		}
	};
	
	public void setPort(int pin) {
		this.pin = pin;
	}
	
	public Cursor getInfo(int kind) {
		return getSelectKind(kind);
	}
	
	public Cursor getData(double date) {
		return getSelectDate(CreateDB._DATA_TABLENAME, date);
	}
	
	public void insertDatabase(String name, int kind) {
		Calendar calendar = Calendar.getInstance();
		long time = calendar.getTimeInMillis();
		insertInfo(time, name, kind);

		for (int i = 0; i < storedData.size(); i++) {
			insertData(i, pin, Integer.parseInt(storedData.get(i)), time);
		}
		Log.d("TestingBoard DbOpenHelper", "insert database");
	}

	private class DatabaseHelper extends SQLiteOpenHelper{

		// 생성자
		public DatabaseHelper(Context context, String name,
				CursorFactory factory, int version) {

			super(context, name, factory, version);
		}

		// 최초 DB를 만들때 한번만 호출된다.
		@Override
		public void onCreate(SQLiteDatabase db) {
			Log.d("TestingBoard DBOpenHelper", "DB onCreate");
			db.execSQL(DataBases.CreateDB._CREATE_DATA);
			db.execSQL(DataBases.CreateDB._CREATE_INFO);
		}
		
		// 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._DATA_TABLENAME);
			db.execSQL("DROP TABLE IF EXISTS "+DataBases.CreateDB._INFO_TABLENAME);
			onCreate(db);
		}
	}

	public DbOpenProxy(Context context){
		this.mCtx = context;
	}

	public DbOpenProxy open() throws SQLException{
		if (!openSingletoneFlag) {
			mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
			mDB = mDBHelper.getWritableDatabase();
			BluetoothObservable.insertObserver(dbObserver);
			openSingletoneFlag = true;
		}
		return this;
	}

	public void close(){
		BluetoothObservable.deleteObserver(dbObserver);
		if (mDB != null) {
			mDB.close();
		}
		openSingletoneFlag = false;
		mDB = null;
		mDBHelper = null;
	}
	
	// Insert DB
	private long insertData(int count, int port, int value, long date){
		ContentValues values = new ContentValues();
		values.put(DataBases.CreateDB.COUNT, count);
		values.put(DataBases.CreateDB.PORT, port);
		values.put(DataBases.CreateDB.VALUE, value);
		values.put(DataBases.CreateDB.DATE, date);
		return mDB.insert(DataBases.CreateDB._DATA_TABLENAME, null, values);
	}
	
	private long insertInfo(long date, String name, int kind){
		ContentValues values = new ContentValues();
		values.put(DataBases.CreateDB.DATE, date);
		values.put(DataBases.CreateDB.KIND, kind);
		values.put(DataBases.CreateDB.NAME, name);
		return mDB.insert(DataBases.CreateDB._INFO_TABLENAME, null, values);
	}


	// Delete ID
	private boolean deleteColumn(long id){
		return mDB.delete(DataBases.CreateDB._DATA_TABLENAME, "_id="+id, null) > 0;
	}

	// Delete Contact
	private boolean search(String name){
		return mDB.delete(DataBases.CreateDB._DATA_TABLENAME, CreateDB.NAME + "="+"'"+name+"'", null) > 0;
	}
	// Select All
	private Cursor getAllColumns(){
		return mDB.query(DataBases.CreateDB._DATA_TABLENAME, null, null, null, null, null, null);
	}

	private Cursor getSelectKind(int kind){
		Cursor c = mDB.query(DataBases.CreateDB._INFO_TABLENAME, null, 
				CreateDB.KIND + "=" + kind, null, null, null, null);
		if(c != null && c.getCount() != 0)
			c.moveToFirst();
		return c;
	}

	// 이름 검색 하기 (rawQuery)
	private Cursor getSelectDate(String table, double date){
		Cursor c = mDB.query(table, null, 
				CreateDB.DATE + "=" + date, null, null, null, null);
		if(c != null && c.getCount() != 0)
			c.moveToFirst();
		return c;
	}

}