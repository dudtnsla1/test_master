package testmaster.android.tool;

import java.util.ArrayList;
import java.util.Stack;

/*
 * 
			�� �˰����� �ٽ� ������ ���� ������ ����.
			0. �켱������ '(' < '+' = '-' < '*' = '/' ���̴�.
			1. �Է¹��� ���� ǥ��Ŀ��� �� ���ھ� �о���δ�.
			2. �о���� ���ڰ� �ǿ������̸� ���� ǥ��Ŀ� ���´�.
			3. �о���� ���ڰ� �������̸�
			3-1. ���� ��ȣ�� ��� ���ÿ� �ִ´�.
			3-2. ������ ��ȣ�� ��� ���� ��ȣ�� ���� ������ ���ÿ��� �ϳ��� ���� ���� ǥ��Ŀ� ���� ���� ��ȣ�� ������ ������ ������.
			3-3. ������ ��Ģ �������� ��� �� �����ڰ� ������ �ֻ��� ��庸�� �켱������ ���� ������ ������ �ֻ��� ��带 ���� ���� ǥ��Ŀ� ���´�. ���� �����ڰ� ������ �ֻ��� ��庸�� �켱������ �������ٸ� ���ÿ� �ְ� ������. 
			�� ) ���� : * + * /, �о���� ������ : * �� ��� ���ÿ��� / * �� ���ʷ� ���� ���� ǥ��Ŀ� ���� *�� ���ÿ� �ִ´�.
			       ���� ���� : * + *, ���� ǥ�� : / *
			4. 1~3 �� �ݺ��ؼ� ���� ǥ����� ��� �о�鿴�ٸ� ���ÿ� �ִ� �����ڸ� �ֻ��� ������ ���� ���� ǥ��Ŀ� ���´�.
 */
public class Modifyer {

	enum precedence {lparen, rparen, plus, minus, times, divide, mod, eos, operand};

	Stack<precedence> stack = new Stack<precedence>();

	private int isp[] = {0, 19, 12, 12, 13, 13, 13, 0};	
	private int icp[] = {20, 19, 12, 12, 13, 13, 13, 0};

	private ArrayList<calcI> calcList = new ArrayList<calcI>();

	private precedence parsingToken(String modify, String num)
	{
		num = "";
		
		while (true) {
			char c1 = modify.charAt(0), c2 = modify.charAt(1);
			switch (c1) {
			case '(':
				return precedence.lparen;
			case ')':
				return precedence.rparen;
			case '+':
				return precedence.plus;
			case '-':
				return precedence.minus;
			case '/':
				return precedence.divide;
			case '*':
				return precedence.times;
			case '%':
				return precedence.mod;
			case '\0':
				return precedence.eos;
			default:
				num += c1;
				if (c2 == '\0')
					return precedence.operand;
			}
		}
	}
	
	private abstract class calcI {
		boolean variable = false;
		float b;
		abstract public float calc(float x);
	};
	
	private void infixToPostfix()
	{
		
	}

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