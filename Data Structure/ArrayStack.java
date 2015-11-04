public class ArrayStack<T> {
	
	// reference variable
	private int top;
	private T[] stack;
	private static final int DEFAULT_SIZE = 1000;
	
	// constructor
	public ArrayStack(int capacity){
		
		if(capacity > 0){
			top = -1;
			stack = (T[])new Object[capacity];
		}else{
			throw new RuntimeException("The capacity can not be less than or equal to zero.");
		}
	}
	
	// overloaded constructor
	public ArrayStack(){
		this(DEFAULT_SIZE);
	}
	
	/* 
	 * Implementation of Stack:
	 *     T push(T data): add the data
	 *     T pop(): remove the last element
	 *     T peek(): get the last element
	 */
	
	private boolean isEmpty(){
		return top == -1;
	}
	
	private boolean isFull(){
		return top >= DEFAULT_SIZE;
	}
	
	public T push(T data){
		
		if(isFull()){
			throw new RuntimeException("The stack is full.");
		}
		stack[++top] = data;
		return stack[top];
	}
	
	public T pop(){
		
		if(!isEmpty()){
			return stack[top--];
		}
		
		return null;
	}
	
	public T peek(){
		
		if(!isEmpty()){
			return stack[top];
		}
		
		return null;
	}
	
	//testing the stack
	public static void main(String[] args){
		
		ArrayStack<Integer> stack = new ArrayStack<>();
		
		for(int i = 0; i < 10; i++){
			System.out.println("Now we are adding " + i + " into stack:");
			System.out.println("The stack added " + stack.push(i) + " in it\n");
		}
		
		System.out.println("Testing peek method, the stack now has " + stack.peek() + " in the last element\n");
		
		for(int i = 0; i < 10; i++){
			System.out.println("Now we removed " + stack.pop() + " from the stack");
			System.out.println("Testing peek method, the stack now has " + stack.peek() + " in the last element\n");
		}
		
	}

}
