package testmaster.android.tool;

import java.util.ArrayList;

public class Modifyer {

	private ArrayList<calcI> calcList = new ArrayList<calcI>();
	
	private abstract class calcI {
		boolean variable = false;
		float b;
		abstract public float calc(float x);
	};
	
	private class Devide extends calcI {
		@Override
		public float calc(float x) {
			return x / b;
		}		
	}
	
	private class Multi extends calcI {
		@Override
		public float calc(float x) {
			return x * b;
		}
	}
	
	private class Plus extends calcI {
		@Override
		public float calc(float x) {
			return x + b;
		}		
	}
	
	private class Sub extends calcI {
		@Override
		public float calc(float x) {
			return x - b;
		}		
	}
	
	public boolean setModify(String modify)
	{
		return parsingModify(modify);
	}
	
	private boolean parsingModify(String modify)
	{
		return true;
	}
	
	public float getY(float x)
	{
		for (int i = 0; i < calcList.size(); i++) {
			if (calcList.get(i).variable == true)
				calcList.get(i).b = x;				
		}
		
		for (int i = 0; i < calcList.size(); i++) {
			x = calcList.get(i).calc(x);
		}
		
		return x;
	}
}