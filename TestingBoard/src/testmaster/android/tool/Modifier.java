package testmaster.android.tool;

import java.util.ArrayList;
import java.util.Stack;

import android.util.Log;

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
public class Modifier {

	private ArrayList<CalcI> calcList = new ArrayList<CalcI>();
	CalcFactory calcFactory = new CalcFactory();

	Stack<precedence> operatorStack = new Stack<precedence>();
	Stack<Integer> operandIndexStack = new Stack<Integer>(); 
	private int operandIndexCur = 0;
	private float operandArray[] = new float[200];
	private int xIndexArray[] = new int[200];
	private int xIndexCur = 0;

	private enum precedence {lparen, rparen, plus, minus, times, divide, mod, eos, operand, x};
	private int isp[] = {0, 1, 12, 12, 13, 13, 13, 0};
	private int icp[] = {20, 1, 12, 12, 13, 13, 13, 0};

	private String postfix = "";
	
	public void init()
	{
		postfix = "";
		calcList.clear();
		operandIndexStack.clear();
		operatorStack.clear();
		operandIndexCur = 0;
		xIndexCur = 0;
	}

	class CalcFactory {
		public final int WHAT_PLUS = 2;
		public final int WHAT_MINUS = 3;
		public final int WHAT_TIMES = 4;
		public final int WHAT_DIVIDE = 5;

		public CalcI createCalcI(int index1, int index2, int retIndex, int what) 
		{
			switch (what) {
			case WHAT_PLUS:
				return new Plus(index1, index2, retIndex);
			case WHAT_MINUS:
				return new Sub(index1, index2, retIndex);
			case WHAT_TIMES:
				return new Multi(index1, index2, retIndex);
			case WHAT_DIVIDE:
				return new Devide(index1, index2, retIndex);
			}
			return null;
		}
	}

	String popToLowerIcp(Stack<precedence> stack, precedence icpOrdinal)
	{
		String postfix = "";
		int pop;
		CalcI calcI;
		while (!stack.isEmpty() && isp[stack.peek().ordinal()] >= icp[icpOrdinal.ordinal()]) {
			pop = stack.pop().ordinal();
			if (pop >= precedence.plus.ordinal() && pop <= precedence.divide.ordinal()) {
				calcI = calcFactory.createCalcI(operandIndexStack.pop(), operandIndexStack.pop(), operandIndexCur, pop);
				operandIndexStack.push(operandIndexCur);
				operandIndexCur++;
				calcList.add(calcI);
				postfix += pop;
			}
		}

		return postfix;
	}

	private precedence parsingToken(StringBuffer modify, StringBuffer num)
	{
		if (num.length() > 0)
			num.setLength(0);

		while (true) {
			char c1 = modify.charAt(0), c2 = modify.charAt(1);
			modify.deleteCharAt(0);

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
			case 'X':
				return precedence.x;
			default:
				num.append(c1);
				if (c2 < '0' || c2 > '9' || c2 == '.')
					return precedence.operand;
			}
		}
	}

	private void infixToPostfix(StringBuffer text)
	{		
		StringBuffer num = new StringBuffer("");
		precedence ret;

		text.append('~');

		while (text.length() > 1) {

			ret = parsingToken(text, num);
			if (ret.ordinal() == precedence.operand.ordinal()) {
				operandArray[operandIndexCur] = Integer.parseInt(num.toString());
				operandIndexStack.push(operandIndexCur);
				operandIndexCur++;
				postfix += " " + num + " ";
			} else if(ret.ordinal() == precedence.x.ordinal()) {
				operandIndexStack.push(operandIndexCur);
				xIndexArray[xIndexCur++] = operandIndexCur++;
			} else if (ret.ordinal() == precedence.rparen.ordinal()) {
				icp[precedence.lparen.ordinal()] = 0;
				postfix += popToLowerIcp(operatorStack, ret);
				icp[precedence.lparen.ordinal()] = 20;
			} else {
				postfix += popToLowerIcp(operatorStack, ret);
				operatorStack.push(ret);
			}
		}

		postfix += popToLowerIcp(operatorStack, precedence.eos);
	}

	private abstract class CalcI {
		public CalcI(int index1, int index2, int retIndex) 
		{
			this.index1 = index1;
			this.index2 = index2;
			this.retIndex = retIndex;
		}
		int index1, index2;
		int retIndex;
		abstract public void calc();
	};

	private class Devide extends CalcI {
		public Devide(int index1, int index2, int retIndex) 
		{
			super(index2, index1, retIndex);
		}

		@Override
		public void calc() 
		{
			operandArray[retIndex] = operandArray[index1] / operandArray[index2];
		}		
	}

	private class Multi extends CalcI {
		public Multi(int index1, int index2, int retIndex) 
		{
			super(index1, index2, retIndex);
		}

		@Override
		public void calc() 
		{
			operandArray[retIndex] = operandArray[index1] * operandArray[index2];
		}
	}

	private class Plus extends CalcI {
		public Plus(int index1, int index2, int retIndex) 
		{
			super(index1, index2, retIndex);
		}

		@Override
		public void calc() 
		{
			operandArray[retIndex] = operandArray[index1] + operandArray[index2];
		}		
	}

	private class Sub extends CalcI {
		public Sub(int index1, int index2, int retIndex) 
		{
			super(index2, index1, retIndex);
		}

		@Override
		public void calc() {
			operandArray[retIndex] = operandArray[index1] - operandArray[index2];
		}		
	}

	private boolean parsingModify(String modify)
	{
		init();
		
		try {
			infixToPostfix(new StringBuffer(modify));
		} catch (Exception e) {
			Log.e("Testboard Modifyer", "parsing modify exception");
			return false;
		}
		
		return true;
	}

	public boolean setModify(String modify)
	{
		return parsingModify(modify);
	}
	
	public float getY(float x)
	{
		if (operandIndexCur > 0) {
			for (int i = 0; i < xIndexCur; i++) {
				operandArray[xIndexArray[i]] = x;
			}

			for (int i = 0; i < calcList.size(); i++) {
				calcList.get(i).calc();
			}
			return operandArray[operandIndexCur - 1];
		}
		return x;
	}
}