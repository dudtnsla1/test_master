package testmaster.android.tool;

import java.util.ArrayList;
import java.util.Stack;

/*
 * 
			이 알고리즘을 다시 정리한 것은 다음과 같다.
			0. 우선순위는 '(' < '+' = '-' < '*' = '/' 순이다.
			1. 입력받은 중위 표기식에서 한 글자씩 읽어들인다.
			2. 읽어들인 글자가 피연산자이면 후위 표기식에 적는다.
			3. 읽어들인 글자가 연산자이면
			3-1. 왼쪽 괄호일 경우 스택에 넣는다.
			3-2. 오른쪽 괄호일 경우 왼쪽 괄호를 꺼낼 때까지 스택에서 하나씩 꺼내 후위 표기식에 적고 왼쪽 괄호를 뽑으면 버리고 끝낸다.
			3-3. 나머지 사칙 연산자의 경우 그 연산자가 스택의 최상위 노드보다 우선순위가 높을 때까지 스택의 최상위 노드를 꺼내 후위 표기식에 적는다. 현재 연산자가 스택의 최상위 노드보다 우선순위가 높아졌다면 스택에 넣고 끝낸다. 
			예 ) 스택 : * + * /, 읽어들인 연산자 : * 일 경우 스택에서 / * 를 차례로 꺼내 후위 표기식에 적고 *를 스택에 넣는다.
			       최종 스택 : * + *, 후위 표기 : / *
			4. 1~3 을 반복해서 중위 표기식을 모두 읽어들였다면 스택에 있는 연산자를 최상위 노드부터 꺼내 후위 표기식에 적는다.
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