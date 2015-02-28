package testmaster.android.page;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class OnSpinnerItemClicked implements OnItemSelectedListener {
	public static String[] S_Value= new String[100];
	

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		//Log.e("what",parent.getItemAtPosition(position).toString());
		//Log.e("whatn",position+"".toString());
		//Log.e("gogo",parent.getId()-2131361892+"");
		try
		{
			S_Value[parent.getId()-2131361892]=parent.getItemAtPosition(position).toString();
		}
		catch (Exception e){

		}
		//Toast.makeText(parent.getContext(), "Clicked : " +
		//      parent.getItemAtPosition(position).toString()+position+"", Toast.LENGTH_SHORT).show();


	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
