package testmaster.android.page;

import testmaster.android.packet.PacketInfo;
import testmaster.android.packet.SettingPacket;
import testmaster.android.testingboard.MainFunctionActivity;
import testmaster.android.testingboard.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class FunctionI2CContext extends FunctionContext implements OnClickListener, OnItemSelectedListener, OnCheckedChangeListener{

	String Address_set;
	Button Address_Check;
	Button Send_btn;
	Button Receive_btn;
	Button btn_Send_true;
	Button btn_Receive_true;
	




	private Spinner I2C_Setting_Spinner2;
	private Spinner I2C_Setting_Spinner3;
	private Spinner I2C_Setting_Spinner4;
	private Spinner I2C_Setting_Spinner5;
	private Spinner I2C_Setting_Spinner6;
	private Spinner I2C_Setting_Spinner7;
	private Spinner I2C_Setting_Spinner8;
	private Spinner I2C_Setting_Spinner9;
	private Spinner I2C_Setting_Spinner10;
	private Spinner I2C_Setting_Spinner11;
	private Spinner I2C_Setting_Spinner12;
	private Spinner I2C_Setting_Spinner13;
	private Spinner I2C_Setting_Spinner14;
	private Spinner I2C_Setting_Spinner15;
	private Spinner I2C_Setting_Spinner16;
	private Spinner I2C_Setting_Spinner17;
	private Spinner I2C_Setting_Spinner18;
	private Spinner I2C_Setting_Spinner19;
	private Spinner I2C_Setting_Spinner20;
	private Spinner I2C_Setting_Spinner21;
	private Spinner I2C_Setting_Spinner22;

	public FunctionI2CContext(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public SettingPacket settingChanged() {
		// TODO Auto-generated method stub
		packet.setI2CPacket();;
		return packet;
	}

	private void showDialog() {
		LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View dialogView = inflater.inflate(R.layout.i2c_setting_dialog, null);

		final AlertDialog.Builder d = new AlertDialog.Builder(activity)
		.setCancelable(true)
		.setTitle("I2C 설정")
		.setView(dialogView)
		.setNegativeButton("취소", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		})
		.setPositiveButton("확인", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				//확인 눌렀을때


				for(int i=0 ; i<22 ; i++){
					if(OnSpinnerItemClicked.S_Value[i]!=null)
					{
						Log.e("whatNum",OnSpinnerItemClicked.S_Value[i]);
						Log.e("where",i+"");
					}
				}





				//Toast.makeText(activity, Value[5], Toast.LENGTH_SHORT).show();
				//Toast.makeText(getActivity(), domainEdit.toString(), Toast.LENGTH_SHORT).show();
			}

		});





		try{
			final Spinner I2C_Setting_Spinner1 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn1);
			ArrayAdapter<CharSequence> i2cadapter1 = ArrayAdapter.createFromResource(activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner1.setAdapter(i2cadapter1);
			I2C_Setting_Spinner1.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner2 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn2);
			ArrayAdapter<CharSequence> i2cadapter2 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner2.setAdapter(i2cadapter2);
			I2C_Setting_Spinner2.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner3 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn3);
			ArrayAdapter<CharSequence> i2cadapter3 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner3.setAdapter(i2cadapter3);
			I2C_Setting_Spinner3.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner4 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn4);
			ArrayAdapter<CharSequence> i2cadapter4 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner4.setAdapter(i2cadapter4);
			I2C_Setting_Spinner4.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner5 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn5);
			ArrayAdapter<CharSequence> i2cadapter5 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner5.setAdapter(i2cadapter5);
			I2C_Setting_Spinner5.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner6 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn6);
			ArrayAdapter<CharSequence> i2cadapter6 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner6.setAdapter(i2cadapter6);
			I2C_Setting_Spinner6.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner7 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn7);
			ArrayAdapter<CharSequence> i2cadapter7 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner7.setAdapter(i2cadapter7);
			I2C_Setting_Spinner7.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner8 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn8);
			ArrayAdapter<CharSequence> i2cadapter8 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner8.setAdapter(i2cadapter8);
			I2C_Setting_Spinner8.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner9 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn9);
			ArrayAdapter<CharSequence> i2cadapter9 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter9.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner9.setAdapter(i2cadapter9);
			I2C_Setting_Spinner9.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner10 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn10);
			ArrayAdapter<CharSequence> i2cadapter10 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter10.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner10.setAdapter(i2cadapter10);
			I2C_Setting_Spinner10.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner11 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn11);
			ArrayAdapter<CharSequence> i2cadapter11 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter11.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner11.setAdapter(i2cadapter11);
			I2C_Setting_Spinner11.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner12 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn12);
			ArrayAdapter<CharSequence> i2cadapter12 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter12.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner12.setAdapter(i2cadapter12);
			I2C_Setting_Spinner12.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner13 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn13);
			ArrayAdapter<CharSequence> i2cadapter13 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter13.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner13.setAdapter(i2cadapter13);
			I2C_Setting_Spinner13.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner14 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn14);
			ArrayAdapter<CharSequence> i2cadapter14 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter14.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner14.setAdapter(i2cadapter14);
			I2C_Setting_Spinner14.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner15 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn15);
			ArrayAdapter<CharSequence> i2cadapter15 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter15.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner15.setAdapter(i2cadapter15);
			I2C_Setting_Spinner15.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner16 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn16);
			ArrayAdapter<CharSequence> i2cadapter16 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter16.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner16.setAdapter(i2cadapter16);
			I2C_Setting_Spinner16.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner17 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn17);
			ArrayAdapter<CharSequence> i2cadapter17 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter17.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner17.setAdapter(i2cadapter17);
			I2C_Setting_Spinner17.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner18 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn18);
			ArrayAdapter<CharSequence> i2cadapter18 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter18.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner18.setAdapter(i2cadapter18);
			I2C_Setting_Spinner18.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner19 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn19);
			ArrayAdapter<CharSequence> i2cadapter19 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter19.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner19.setAdapter(i2cadapter19);
			I2C_Setting_Spinner19.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner20 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn20);
			ArrayAdapter<CharSequence> i2cadapter20 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter20.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner20.setAdapter(i2cadapter20);
			I2C_Setting_Spinner20.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner21 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn21);
			ArrayAdapter<CharSequence> i2cadapter21 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter20.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner21.setAdapter(i2cadapter21);
			I2C_Setting_Spinner21.setOnItemSelectedListener(new OnSpinnerItemClicked());

			I2C_Setting_Spinner22 = (Spinner)dialogView.findViewById(R.id.i2c_setting_btn22);
			ArrayAdapter<CharSequence> i2cadapter22 = ArrayAdapter.createFromResource(this.activity, R.array.i2c_setting, R.layout.spinner_item);
			i2cadapter20.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			I2C_Setting_Spinner22.setAdapter(i2cadapter22);
			I2C_Setting_Spinner22.setOnItemSelectedListener(new OnSpinnerItemClicked());
		}
		catch(Exception e)
		{

		}

		d.create().show();

	}


	private static EditText Slave_ADDRESS;
	private static EditText Register_ADDRESS;
	private static EditText Write_DATA;
	private static EditText Number_READ;
	String Slave_ADD;
	String Register_ADD;
	String Wrte_DA;
	String Number_R;

	@Override
	public int pageChanged(int pageNum) {

		if(pageNum==0){
			initFistPage(activity);
		}

		Address_Check = (Button) activity.findViewById(R.id.function_i2c_setting_btn);
		Address_Check.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try{
					Slave_ADDRESS=(EditText) activity.findViewById(R.id.i2cEdit);
					Register_ADDRESS=(EditText) activity.findViewById(R.id.Register_Address);
					Write_DATA=(EditText) activity.findViewById(R.id.Write_data);
					Number_READ=(EditText) activity.findViewById(R.id.Number_of_Read);


					Slave_ADD=Slave_ADDRESS.getText().toString();
					Register_ADD=Register_ADDRESS.getText().toString();
					Wrte_DA=Write_DATA.getText().toString();
					Number_R=Number_READ.getText().toString();

				}
				catch (Exception e){

				}

			}
		});

		Send_btn = (Button) activity.findViewById(R.id.function_i2c_send_btn);
		Send_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for(int i =0 ; i<22 ; i++){
					OnSpinnerItemClicked.S_Value[i]="0";
				}
				showDialog();

			}
		});
		Receive_btn = (Button) activity.findViewById(R.id.function_i2c_receive_btn);
		Receive_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				for(int i =0 ; i<22 ; i++){
					OnSpinnerItemClicked.S_Value[i]="0";
				}
				showDialog();

			}
		});

		
		btn_Send_true = (Button) activity.findViewById(R.id.function_i2c_send_btn_true);
		btn_Send_true.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				

			}
		});
		
		btn_Receive_true = (Button) activity.findViewById(R.id.function_i2c_receive_btn_true);
		btn_Receive_true.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				((PageChanger)activity).setNextPage();
				
			}
		});
		
		
		
		
		return 0;
	}


	private void initFistPage(Activity context) {


	}

	@Override
	public void saveSettingData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}

