package expression;

import java.io.*;
import java.util.*;

public class Expression {
	private ArrayList expression = new ArrayList();// �洢������ʽ

	private ArrayList right = new ArrayList();// �洢������ʽ

	private String result;// ���

	// ����������Ϣ�������󣬽���ֵ�����������ArrayList��
	private Expression(String input) {
		StringTokenizer st = new StringTokenizer(input, "+-*/()", true);
		while (st.hasMoreElements()) {
			String s = st.nextToken();
			expression.add(s);
		}
	}

	// ��������ʽת��Ϊ������ʽ
	private void toRight() {
		Stacks aStack = new Stacks();
		String operator;
		int position = 0;
		while (true) {
			if (Calculate.isOperator((String) expression.get(position))) {
				if (aStack.top == -1
						|| ((String) expression.get(position)).equals("(")) {
					aStack.push(expression.get(position));
				} else {
					if (((String) expression.get(position)).equals(")")) {
						while (true) {

							if (aStack.top != -1
									&& !((String) aStack.top()).equals("(")) {
								operator = (String) aStack.pop();
								right.add(operator);
							} else {
								if (aStack.top != -1)
									aStack.pop();
								break;
							}
						}
					} else {
						while (true) {
							if (aStack.top != -1
									&& Calculate.priority((String) expression
											.get(position)) <= Calculate
											.priority((String) aStack.top())) {
								operator = (String) aStack.pop();
								if (!operator.equals("("))
									right.add(operator);
							} else {
								break;
							}

						}
						aStack.push(expression.get(position));
					}
				}
			} else
				right.add(expression.get(position));
			position++;
			if (position >= expression.size())
				break;
		}
		while (aStack.top != -1) {
			operator = (String) aStack.pop();
			if (!operator.equals("("))
				right.add(operator);
		}
	}

	// ��������ʽ������ֵ
	private void getResult() {
		this.toRight();
		for (int i = 0; i < right.size(); i++) {
			System.out.println(right.get(i));
		}
		Stacks aStack = new Stacks();
		String op1, op2, is = null;
		Iterator it = right.iterator();

		while (it.hasNext()) {
			is = (String) it.next();
			if (Calculate.isOperator(is)) {
				op1 = (String) aStack.pop();
				op2 = (String) aStack.pop();
				aStack.push(Calculate.twoResult(is, op1, op2));
			} else
				aStack.push(is);
		}
		result = (String) aStack.pop();
		it = expression.iterator();
		while (it.hasNext()) {
			System.out.print((String) it.next());
		}
		System.out.println("=" + result);
	}

	public static void main(String avg[]) {
		try {
			System.out.println("Input a expression:");
			BufferedReader is = new BufferedReader(new InputStreamReader(
					System.in));
			for (;;) {
				String input = new String();
				input = is.readLine().trim();
				if (input.equals("q"))
					break;
				else {
					Expression boya = new Expression(input);
					boya.getResult();
				}
				System.out
						.println("Input another expression or input 'q' to quit:");
			}
			is.close();
		} catch (IOException e) {
			System.out.println("Wrong input!!!");
		}
	}
}
