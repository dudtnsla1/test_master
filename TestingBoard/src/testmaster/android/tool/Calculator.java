package testmaster.android.tool;

import testmaster.android.testingboard.R;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Calculator {

	private String modify = "";
	private Button result_y;
	private Button value_x;
	private Activity activity;
	
	private KeyPadClickAdapter keypadAdapter = new KeyPadClickAdapter();
	private FunctionClickAdapter funcAdapter = new FunctionClickAdapter();

	private Button keypad[] = new Button[10];	
	
	private final boolean KEYPAD = false, FUNC = true;
	private boolean last_input_key = KEYPAD;
	
	private int keypadIds[] = {
			R.id.one,
			R.id.two,
			R.id.three,
			R.id.four,
			R.id.five,
			R.id.six,
			R.id.seven,
			R.id.eight,
			R.id.nine,
			R.id.zero
	};

	private Button func[] = new Button[10];	
	private int funcIds[] = {
			R.id.plus,
			R.id.minus,
			R.id.divide,
			R.id.multiply,
			R.id.equal,
			R.id.leftguard,
			R.id.rightguard,
			R.id.result_y,
			R.id.value_x,
			R.id.backspace
	};

	public String getModify()
	{
		return modify;
	}

	private void initKeypad()
	{
		for (int i = 0; i < keypad.length; i++) {
			keypad[i] = (Button)activity.findViewById(keypadIds[i]);
			keypad[i].setContentDescription( (i + 1) + "");
			keypad[i].setOnClickListener(keypadAdapter);
		}		
	}

	private void initFunc()
	{	
		String funcDescript[] = {
				"+",
				"-",
				"/",
				"*",
				"=",
				"(",
				")",
				"y",
				"x",
				"<-"
		};

		for (int i = 0; i < funcDescript.length; i++) {
			func[i].setContentDescription(funcDescript[i]);
			func[i].setOnClickListener(funcAdapter);
		}		
	}

	public Calculator(Activity activity) {
		this.activity = activity;
		initKeypad();
		initFunc();
	}

	private class FunctionClickAdapter implements OnClickListener {
		@Override
		public void onClick(View v) {
			
			switch (v.getId()) {
			case R.id.backspace:
				if (modify.length() > 1)
					modify = modify.substring(0, modify.length() - 2);
				else
					modify = "";
				break;
			default:
				if (last_input_key == KEYPAD)
					modify += v.getContentDescription();
			}
			
			last_input_key = FUNC;
		}		
	}
	
	private class KeyPadClickAdapter implements OnClickListener {
		@Override
		public void onClick(View v) {
			
			modify += v.getContentDescription();

			last_input_key = KEYPAD;
		}
		
	}
}
