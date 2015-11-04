public class NodeStack<T> {
	
	/* Node class */
	private class Node<T>{
		
		//reference variables
		private T data;
		private Node<T> link;
		
		//constructor
		Node(T data, Node<T> link){
			this.data = data;
			this.link = link;
		}
		
		//methods
		protected T getData(){
			return data;
		}
		
		protected Node<T> getLink(){
			return link;
		}
		
		protected void setData(T data){
			this.data = data;
		}
		
		protected void setLink(Node<T> link){
			this.link = link;
		}
	}
	
	/* 
	 * Implementation of NodeStack:
	 *     T add(T data)
	 *     T remove(): remove the last node
	 *     T peek(): get the last node data
	 */
	
	//reference variables
	private Node<T> head;
	
	//constructor
	public NodeStack(){
		head = null;
	}
	
	//implementing methods
	
	//helper method
	private Node<T> lastNode(){
		Node<T> travNode = head;
		while(travNode.getLink() != null){
			travNode = travNode.getLink();
		}
		return travNode;
	}
	
	public T add(T data){
		
		if(head == null){
			head = new Node<>(data, null);
			return head.getData();
		}
		
		lastNode().setLink(new Node<T>(data, null));
		
		return data;
	}
	
	public T remove(){
		
		T result = null;
		
		if(head != null){
			result = lastNode().getData();
			Node<T> rmNode = lastNode();
			rmNode = null;
			return result;
		}
		
		return result;
	}
	
	public T peek(){
		return lastNode().getData();
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
