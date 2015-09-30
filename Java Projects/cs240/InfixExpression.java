package cs240;
/*
 * Name: Lee, Weiying
 * Project:	#3
 * Due: Mar.12 2015
 * Course: cs-240-02-w15
 * 
 * Description:
 * 		implement two class methods(one for infix->postfix, another for infix->prefix):
 * 				String[] converToPostfix(String[] infixExpression)
 * 				String[] converToPrefix(String[] infixExpression)
 */

import java.util.*;
class Stack<T>{
	private final static int DEFAULT_CAPACITY = 50;
	private int top;
	private T[] stack;
	
	@SuppressWarnings("unchecked")
	public Stack(int capacity){
		if(capacity <= 0)
			throw new IllegalStateException("Capactiy is empty or negetive.");
		else{
			top = -1;
			stack = (T[])new Object[capacity];
		}
	}
	
	public Stack(){
		this(DEFAULT_CAPACITY);
	}
	
	public void push(T data){
		if(top+1 > stack.length)
			throw new RuntimeException("Stack is full.");
		else
			stack[++top] = data;
	}
	
	public T pop(){
		if(empty())
			throw new RuntimeException("Stack is empty.");
		return stack[top--];
	}
	
	public T peek(){
		if(!empty())
			return stack[top];
		return null;
	}
	
	public boolean empty(){
		return top == -1;
	}
}

public class InfixExpression {
	public static String[] convertToPostfix(String[] infixExpression) {
		
		Stack<String> stack = new Stack<String>();		//for processing conversion
		//for checking the validation of infix
		List<String> p1 = new ArrayList<String>();		//"("	
		List<String> p2 = new ArrayList<String>();		//")"
		List<String> opt = new ArrayList<String>();		// operators
		List<String> opd = new ArrayList<String>();		//operands
		boolean check = false;
		String[] output = new String[infixExpression.length];	//for the returning output
		
		//testing the validation of operads
		//if the first symbol is not "(" or variables, it's wrong
		if(infixExpression[0] == "+" || infixExpression[0] == "-" || infixExpression[0] == ")" ||
				infixExpression[0] == "*" || infixExpression[0] == "/" )
			throw new RuntimeException("Illegal state of infix");
		
		//if the last symbol is not ")" or variables, it's also wrong
		if(infixExpression[infixExpression.length-1] == "+" || infixExpression[infixExpression.length-1] == "-" || 
				infixExpression[infixExpression.length-1] == "(" ||infixExpression[infixExpression.length-1] == "*" || 
				infixExpression[infixExpression.length-1] == "/" )
			throw new RuntimeException("Illegal state of infix");
		
		for(int i = 0; i < infixExpression.length; i++){
			//first of all, if there is two operators binding together, it's wrong
			if(infixExpression[i] == "+" || infixExpression[i] == "-" || infixExpression[i] == "(" ||
					infixExpression[i] == "*" || infixExpression[i] == "/"){
				if(infixExpression[i+1] == "+" || infixExpression[i+1] == "-" ||
					infixExpression[i+1] == "*" || infixExpression[i+1] == "/" || infixExpression[i+1] == ")")
					throw new RuntimeException("Illegal state of infix");
			}
			if(infixExpression[i] == "+" || infixExpression[i] == "-" || 
					infixExpression[i] == "*" || infixExpression[i] == "/" ){
				opt.add(infixExpression[i]);	//put all the operators into list
			}else{
				if(infixExpression[i] != " ")
					opd.add(infixExpression[i]);	//put all the operands into list
			}
		}
		//if number of operands is zero or one, it's wrong.
		if(opd.size() <= 1)
			throw new RuntimeException("Illegal number of operands.");
		//when number of operands is odd, number of operators should be even
		//when number of operands is even, number of operators should be odd
		else if(opd.size() % 2 != 0){
			if(opt.size() % 2 != 0)
				throw new RuntimeException("Illegal number of operands or operators");
		}else if(opd.size() % 2 == 0){
			if(opt.size() % 2 == 0)
				throw new RuntimeException("Illegal number of operands or operators");
		}
		
		// testing the validation of infix
		for(int i = 0; i < infixExpression.length; i++){
			//if the infix contains the parenthesis
			if(infixExpression[i] == "(")
				p1.add(infixExpression[i]);		//"(" added to p1
			if(infixExpression[i] == ")")
				p2.add(infixExpression[i]);		//")" added to p2
		}
		//if the number of parenthesis isn't an even number, they are unmatched
		if(p1.size() != 0){
			throw new RuntimeException("The infix input is illegal.");
		}
		else{
			//the parenthesis is unbalanced
			if(p1.size() % 2 != 0 && p1.size() != p2.size()){
				throw new RuntimeException("The infix input is illegal.");
			}
			//the parenthesis are all the same type
			else if((p1.size() == 0 && p2.size() != 0) || (p1.size() != 0 && p2.size() == 0)){
				throw new RuntimeException("The infix input is illegal");
			}
			else{
				for(int i = 0; i < infixExpression.length; i++){
					//start from "(", it's normal(check is true)
					if(infixExpression[i] == "(")
						check = true;
					//contain ")" and before it there is actual a "(", it's good, otherwise, error!
					for(int c = 0; c < infixExpression.length; c++){
						//if there is a ")" exits
						if(infixExpression[c] == ")"){
							if(!check)
								throw new RuntimeException("The infix input is illegal.");
						}
					}
				}
			}
		}
		if(infixExpression.length == 0)
			throw new RuntimeException("The input is empty!");
		
		//Processing the conversion
		int c = 0;
		for(int i = 0; i < infixExpression.length; i++){
			if(infixExpression[i] == " ")
				continue;
			if(infixExpression[i] == "("){
				stack.push(infixExpression[i]);
			}else if(infixExpression[i] == "*" || infixExpression[i] == "/"){
				if(infixExpression[i] != stack.peek()){
					stack.push(infixExpression[i]);
				//if same precedence, pop off the top element to output
				}else{
					stack.push(infixExpression[i]);
					output[c] = stack.pop();
					c++;
				}
			}else if(infixExpression[i] == "+" || infixExpression[i] == "-"){
				
				//if the symbol is not lower or the same as the top
				if(infixExpression[i] != stack.peek() &&
						(stack.peek() != "*" || stack.peek() != "/")){
					stack.push(infixExpression[i]);
				//if same precedence or lower precedence, pop off the top element to output
				}else{
					stack.push(infixExpression[i]);
					output[c] = stack.pop();
					c++;
				}
			}else if(infixExpression[i] == ")"){
				while(!stack.empty()){
					if(stack.peek() == "("){
						stack.pop();
					}else{
						output[c] = stack.pop();
						c++;
					}
				}
			}else{
				output[c] = infixExpression[i];
				c++;
			}			
		}
		
		//put everything that is still in the stack
		if(!stack.empty()){
			while(!stack.empty()){
				output[c] = stack.pop();
				c++;
			}
		}
		
		//since we didn't put in "("s and ")"s, the output would contains nulls.(array isn't fully filled)
		for(int i = 0; i < output.length; i++){
			if(output[i] == null)
				output[i] = "";		//replace "" for the nulls in output
		}
			
		return output;
	}
	
	public static String[] convertToPrefix(String[] infixExpression) {
		//Two stacks: one for operators and the other for operands
		Stack<String> operators = new Stack<String>(infixExpression.length);
		Stack<String> operand = new Stack<String>(infixExpression.length);
		
		//Two ArrayLists for the validation of infix expression
		List<String> p1 = new ArrayList<String>();		//"("
		List<String> p2 = new ArrayList<String>();		//")"
		List<String> opt = new ArrayList<String>();		//operators
		List<String> opd = new ArrayList<String>();		//operands
		boolean check = false;	//for testing the validation of infix
		
		//Create a String[] arrays for output/op/RightOperand/LeftOperand
		String[] op = new String[infixExpression.length];
		String[] RightOperand = new String[infixExpression.length];
		String[] LeftOperand = new String[infixExpression.length];
		String[] output = new String[infixExpression.length];
		
		
		// testing the validation of infix
		
		//testing the validation of operads
		//if the first symbol is not "(" or variables, it's wrong
		if(infixExpression[0] == "+" || infixExpression[0] == "-" || infixExpression[0] == ")" ||
				infixExpression[0] == "*" || infixExpression[0] == "/" )
			throw new RuntimeException("Illegal state of infix");
		
		//if the last symbol is not ")" or variables, it's also wrong
		if(infixExpression[infixExpression.length-1] == "+" || infixExpression[infixExpression.length-1] == "-" || 
				infixExpression[infixExpression.length-1] == "(" ||infixExpression[infixExpression.length-1] == "*" || 
				infixExpression[infixExpression.length-1] == "/" )
			throw new RuntimeException("Illegal state of infix");
				
		for(int i = 0; i < infixExpression.length; i++){
			//first of all, if there is two operators binding together, it's wrong
			if(infixExpression[i] == "+" || infixExpression[i] == "-" || infixExpression[i] == "(" ||
					infixExpression[i] == "*" || infixExpression[i] == "/"){
				if(infixExpression[i+1] == "+" || infixExpression[i+1] == "-" ||
					infixExpression[i+1] == "*" || infixExpression[i+1] == "/" || infixExpression[i+1] == ")")
					throw new RuntimeException("Illegal state of infix");
			}
			if(infixExpression[i] == "+" || infixExpression[i] == "-" || 
					infixExpression[i] == "*" || infixExpression[i] == "/" ){
				opt.add(infixExpression[i]);	//put all the operators into list
			}else{
				if(infixExpression[i] != " ")
					opd.add(infixExpression[i]);	//put all the operands into list
			}
		}
		//if number of operands is zero or one, it's wrong.
		if(opd.size() <= 1)
			throw new RuntimeException("Illegal number of operands.");
		//when number of operands is odd, number of operators should be even
		//when number of operands is even, number of operators should be odd
		else if(opd.size() % 2 != 0){
			if(opt.size() % 2 != 0)
				throw new RuntimeException("Illegal number of operands or operators");
		}else if(opd.size() % 2 == 0){
			if(opt.size() % 2 == 0)
				throw new RuntimeException("Illegal number of operands or operators");
		}
				
		for(int i = 0; i < infixExpression.length; i++){
			//if the infix contains the parenthesis
			if(infixExpression[i] == "(")
				p1.add(infixExpression[i]);		//"(" added to p1
			if(infixExpression[i] == ")")
				p2.add(infixExpression[i]);		//")" added to p2
		}
		//if the number of parenthesis isn't an even number, they are unmatched
		if((p1.size() + p2.size()) % 2 != 0){
			throw new RuntimeException("The infix input is illegal.");
		}
		else{
			//the parenthesis is unbalanced
			if(p1.size() % 2 != 0 && p1.size() != p2.size()){
				throw new RuntimeException("The infix input is illegal.");
			}
			//the parenthesis are all the same type
			else if((p1.size() == 0 && p2.size() != 0) || (p1.size() != 0 && p2.size() == 0)){
				throw new RuntimeException("The infix input is illegal");
			}
			else{
				for(int i = 0; i < infixExpression.length; i++){
					//start from "(", it's normal(check is true)
					if(infixExpression[i] == "(")
						check = true;
					//contain ")" and before it there is actual a "(", it's good, otherwise, error!
					for(int c = 0; c < infixExpression.length; c++){
						//if there is a ")" exits
						if(infixExpression[c] == ")"){
							if(!check){
								throw new RuntimeException("The infix input is illegal.");
							}
						}
					}
				}
			}
		}
		if(infixExpression.length == 0){
			throw new RuntimeException("The input is empty!");	
		}
		
		//processing conversion
		int c = 0;
		for(int i = 0; i < infixExpression.length; i++){
			//Left parentheses are pushed onto the operators stack
			if(infixExpression[i] == " ")
				continue;
			if(infixExpression[i] == "("){
				operators.push(infixExpression[i]);
			}
			else if(infixExpression[i] == "*" || infixExpression[i] == "/"){
				if(infixExpression[i] != operators.peek()){
					operators.push(infixExpression[i]);
				}else{
					operators.push(infixExpression[i]);
					//if the same precedence, pop off to op
					op[c] = operators.pop();
					RightOperand[c] = operand.pop();
					LeftOperand[c] = operand.pop();
					//new expression "op LeftOperand RightOperand"
					operand.push(op[c]);
					operand.push(LeftOperand[c]);
					operand.push(RightOperand[c]);
					c++;
				}
			}
			else if(infixExpression[i] == "+" || infixExpression[i] == "-"){
				if(infixExpression[i] != operators.peek() && 
						(operators.peek() != "*" || operators.peek() != "/")){
					operators.push(infixExpression[i]);
				}else{
					operators.push(infixExpression[i]);
					//if the same precedence, pop off to op
					op[c] = operators.pop();
					RightOperand[c] = operand.pop();
					LeftOperand[c] = operand.pop();
					//new expression "op LeftOperand RightOperand"
					operand.push(op[c]);
					operand.push(LeftOperand[c]);
					operand.push(RightOperand[c]);
					c++;
				}
			}
			else if(infixExpression[i] == ")"){
				op[c] = operators.pop();
				RightOperand[c] = operand.pop();
				LeftOperand[c] = operand.pop();
				
				operand.push(op[c]);
				operand.push(LeftOperand[c]);
				operand.push(RightOperand[c]);
				c++;
			}
			//Variables are pushed onto operand stack
			else{
				operand.push(infixExpression[i]);
			}
		}
		
		
		Stack<String> stack = new Stack<>();
		
		
		if(!operators.empty() || !operand.empty()){
			/*
			 * There are two situations could happen when there are no parenthesis:
			 * 		1. The high precedence of symbol on the top of operands
			 * 		2. Precedences go in order(low precedence of symbol on the top of operands)
			 */
			
			//Situation 1
			if(operators.peek() != "*"){
				while(!operand.empty()){
					stack.push(operand.pop());
				}
				while(!operators.empty()){
					if(operators.peek() == "(")
						operators.pop();
					else
						stack.push(operators.pop());
				}
			}
			//Situation 2
			else{
				while(!operand.empty() || !operators.empty()){
					
					//need to pop off the two operands at a time since they should be operated first
					for(int i = 0; i < 2; i++){
						try{
							stack.push(operand.pop());
						}catch(RuntimeException e){
							//Skip the exception raised by popping empty stack
						}
					}
					stack.push(operators.pop());
				}
			}
		}
		
		//put into the output array for returning
		for(int i = 0; i < output.length; i++){
			if(!stack.empty())
				output[i] = stack.pop();
		}
		
		for(int i = 0; i < output.length; i++){
			if(output[i] == null)
				output[i] = "";
		}
		
		return output;
	}
	
	public static void main(String[] args){
			
		//correct input testing
		//test of a + b + c
		result(new String[]{"a", "+", "b", "+", "c"});
		//test of (a + b) * (c - d)
		result(new String[]{"a", "+", "b", "*", "c", "-", "d"});
		//test of a + b * c 
		result(new String[]{"(", "a", "+", "b", ")", "*", "c"});
		//test of a +    b /   c (handling multiple spaces)
		result(new String[]{"a", "+", " ", " ", " ", "b", "*", " ", " ", "c"});
		
		//unmatched parenthesis testing
		try{
			//test of a + b) * (c - d
			result(new String[]{"a", "+", "b",")", "*", "(", "c", "-", "d"});
			//test of (a + b * (c - d)
			result(new String[]{"(", "a", "+", "b", "*", "(", "c", "-", "d", ")"});
		}catch(RuntimeException e){
			System.out.println("For the unmatched parenthesis:\n" + e);
		}
		
		//Illegal infix input testing
		try{
			//test of ++abc
			result(new String[]{"+", "+", "a", "b", "c"});
			//test of bcd--
			result(new String[]{"b", "c", "d", "-", "-"});
			//test of a++c (when operand is missing)
			result(new String[]{"a","+", "+", "c"});
		}catch(RuntimeException e){
			System.out.println("For the illegal infix input:\n" + e);
		}
	}
	
	public static void result(String[] infix){
		String str = "";
		String str1 = "";
		String str2 = "";
		String[] postfix = convertToPostfix(infix);
		String[] prefix = convertToPrefix(infix);
		for(String s: infix)
			str += s;
		for(String s: postfix)
			str1 += s;
		for(String s: prefix)
			str2 += s;	
		System.out.println("Infix: " + str);
		System.out.println("Postfix: " + str1);
		System.out.println("Prefix: " + str2);
		System.out.println();
	}
}
