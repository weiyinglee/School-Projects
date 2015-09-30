package cs240;
/*
 * Name:	Lee,Weiying
 * Extra Credit:	#2
 * Due:	Mar. 20, 2015
 * Course:	cs-240-02-w15
 * 
 * Description:
 * 			Implement a class that provides a class method:
 * 				int evalPostfix(String[] postfix, String[] variables, String[] values)
 * 			to evaluate a postfix expression using variable/value list
 * 			Need to Generate a runtime exception if a variable is not found or malformed expression 
 * 
 */
import java.util.Stack;

public class ExtraCredit {
	public static int evalPostfix(String[] postfix, String[] variables, String[] values){
		int sum = 0;
		String[] valuePostfix = new String[postfix.length]; //To contain the array of postfix with actual variables
		
		//Handle the exceptions
		if(variables.length > values.length)
			throw new RuntimeException("Value is missing");
		else if(variables.length < values.length)
			throw new RuntimeException("Variable is missing");
		
		//Traverse then create a new array of postfix with actual variables
		for(int postfixIndex = 0; postfixIndex < postfix.length; postfixIndex++){
			for(int variablesIndex = 0; variablesIndex < variables.length; variablesIndex++){
				if(postfix[postfixIndex] == variables[variablesIndex])
					//when the variables in postfix are matching the variables string array
					valuePostfix[postfixIndex] = values[variablesIndex];
				else if(postfix[postfixIndex] == "+" || postfix[postfixIndex] == "-" || postfix[postfixIndex] == "*" || postfix[postfixIndex] == "/")
					valuePostfix[postfixIndex] = postfix[postfixIndex];
				else if(postfix[postfixIndex] == "")
					valuePostfix[postfixIndex] = "";
			}
		}
		
		//if the postfix are not matching the variables, the valuePostfix will contain null 
		for(int i = 0; i < valuePostfix.length; i++){
			if(valuePostfix[i] == null)
				throw new RuntimeException("The variable is not matching the postfix");
		}
		
		
		//create a stack for processing the calculation
		Stack<String> stack = new Stack<String>();
 		
		/*
		 * put the elements into valuePostfix, 
		 * when encounter an operator, pop out two values in stack
		 * and then calculate the new value using that operator
		 * and then re-put into stack, keep going until the array is done traversing
		 */
		
		for(int i = 0; i < valuePostfix.length; i++){
			if(valuePostfix[i] != "+" && valuePostfix[i] != "*" && valuePostfix[i] != "/" && valuePostfix[i] != "-"
					&& valuePostfix[i] != ""){
				stack.push(valuePostfix[i]);
			}else{
				int value1, value2;
				//calculate the result of two variables with this operator
				switch(valuePostfix[i]){
					case "+":
						value1 = Integer.parseInt(stack.pop());
						value2 = Integer.parseInt(stack.pop());
						stack.push(Integer.toString(value2+value1));
						break;
					case "-":
						value1 = Integer.parseInt(stack.pop());
						value2 = Integer.parseInt(stack.pop());
						stack.push(Integer.toString(value2-value1));
						break;
					case "*":
						value1 = Integer.parseInt(stack.pop());
						value2 = Integer.parseInt(stack.pop());
						stack.push(Integer.toString(value2*value1));
						break;
					case "/":
						value1 = Integer.parseInt(stack.pop());
						value2 = Integer.parseInt(stack.pop());
						stack.push(Integer.toString(value2/value1));
						break;
					default:
				}
			}
		}
		
		sum = Integer.parseInt(stack.peek());
		
		return sum;
	}
	public static void main(String[] args){
		InfixExpression ie = new InfixExpression();
		String[] postfix, postfix1, postfix2, postfix3, postfix4, 
				variables, variables1, variables2, variables3, variables4,
				values, values1, values2, values3, values4;
		
		//normal situation
		postfix = ie.convertToPostfix(new String[]{"a", "+", "b", "*", "c"});
		variables = new String[]{"a", "b", "c"};
		values = new String[]{"1", "2", "3"};
		
		//case when variable is missing
		postfix1 = ie.convertToPostfix(new String[]{"a", "+", "b", "*", "c"});
		variables1 = new String[]{"a", "b"};
		values1 = new String[]{"1", "2", "3"};
		
		//case when value is missing
		postfix2 = ie.convertToPostfix(new String[]{"a", "+", "b", "*", "c"});
		variables2 = new String[]{"a", "b", "c"};
		values2 = new String[]{"1", "2"};
		
		//normal situation when variables are unordered with different values
		postfix3 = ie.convertToPostfix(new String[]{"a", "-", "b", "*", "d"});
		variables3 = new String[]{"d", "b", "a"};
		values3 = new String[]{"1", "3", "5"};
		
		//case when one of the postfix variable is not matching the provided variable
		postfix4 = ie.convertToPostfix(new String[]{"a", "+", "b", "*", "d"});
		variables4 = new String[]{"a", "b", "c"};
		values4 = new String[]{"1", "2", "3"};
		
		
		System.out.println("Infix: a+b*c\n" + "Variables: a,b,c\n" + "Values: 1,2,3\n" +
							"The result is: " + evalPostfix(postfix, variables, values) + "\n");
		
		System.out.println("Infix: a-b*d\n" + "Variables: d,b,a\n" + "Values: 1,3,5\n" +
				"The result is: " + evalPostfix(postfix3, variables3, values3) + "\n");
		
		try{
			System.out.println("Infix: a+b*c\n" + "Variables: a,b\n" + "Values: 1,2,3");
			System.out.println("The result is: " + evalPostfix(postfix1, variables1, values1));
		}catch(RuntimeException e){
			System.out.println(e);
		}
		System.out.println();
		try{
			System.out.println("Infix: a+b*c\n" + "Variables: a,b,c\n" + "Values: 1,2");
			System.out.println("The result is: " + evalPostfix(postfix2, variables2, values2));
		}catch(RuntimeException e){
			System.out.println(e);
		}
		System.out.println();
		try{
			System.out.println("Infix: a+b*d\n" + "Variables: a,b,c\n" + "Values: 1,2,3");
			System.out.println("The result is: " + evalPostfix(postfix4, variables4, values4));
		}catch(RuntimeException e){
			System.out.println(e);
		}
	}
}

