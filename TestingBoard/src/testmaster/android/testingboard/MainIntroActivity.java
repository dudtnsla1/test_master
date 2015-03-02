package testmaster.android.testingboard;

import testmaster.android.resource.DestroyDecorator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

public class MainIntroActivity extends Activity {
	public static final String _DEBUG_TAG = "TestingBoard";
	private final int MAIN_ACTIVITY_RESULT = 0;

	private void testingCode() {
		/*test code*/
		/*
		DbOpenHelper dbHelper = new DbOpenHelper(this);
		dbHelper.open();
		Log.d("TestingBoard MainIntroActivity", DataBases.CreateDB._CREATE_DATA);
		dbHelper.close();		
		 */
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		testingCode();

		setContentView(R.layout.main_intro_activity);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run()  {
				Intent intent = new Intent(MainIntroActivity.this, MainHomeActivity.class);
				startActivityForResult(intent, MAIN_ACTIVITY_RESULT);
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		}, 1000);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case MAIN_ACTIVITY_RESULT:
			if (DestroyDecorator.isHaveDoing()) {
				DestroyDecorator.getDestroyDecorator().destroy();
			}
			Log.i("TestingBoard IntroActivity", "Destroy");
			finish();

			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_intro, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}
}
