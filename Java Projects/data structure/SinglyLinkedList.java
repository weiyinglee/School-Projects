public class SinglyLinkedList<T> {
	
	/* Create a private inner class for Node class */
	private class Node <T> {
		
		protected T data;
		protected Node<T> link;
		
		//constructor
		Node(T data, Node<T> link){
			this.data = data;
			this.link = link;
		}
		
		//implementation: getData, setData, getLink, setLink
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
		Implementation of the singlyLinkedList:
			T add(data): add to the last element, return the added value
			T remove(): remove the last element, return the removed value
			T remove(data): remove the node with data, return the removed value
			boolean isEmpty(): return boolean for whether it's empty or not
			T get(): return the first element in the list
			String toString: overrode toString method, returning the String of data chunk
	*/
	
	//reference variables
	private Node<T> head;
	private int size;
	
	//constructor
	public SinglyLinkedList(){
		head = null;
		size = 0;
	}
	
	//helper methods
	private Node<T> search(T data){
		Node<T> found = null;
		if(!isEmpty()){
			//find value
			for(Node<T> travNode = head; travNode.getLink() != null; travNode = travNode.getLink()){
				if(travNode.getData().equals(data)){
					found = travNode;
				}
			}
		}
		return found;
	}
	
	//Implementing method
	public T add(T data){
		
		T result = data;
		
		if(isEmpty()){
			//if the list is empty, just add the node
			head = new Node<T>(data, null);
			size++;
		}else{
			Node<T> travNode = head;
			//first traverse the list to the end
			while(travNode.getLink() != null){
				travNode = travNode.getLink();
			}
			travNode.setLink(new Node<T>(data, null));
			size++;
		}
		return data;
	}
	
	public T remove(){
		T result = null;
		
		if(!isEmpty()){
			Node<T> travNode = head;
			while(travNode.getLink().getLink() != null){
				travNode = travNode.getLink();
			}
			result = travNode.getLink().getData();
			travNode.setLink(null);
			size--;
		}
		return result;
	}
	
	public T remove(T data){
		T result = null;
		
		if(!isEmpty()){
			Node<T> travNode = head;
			Node<T> delNode = search(data);
			if(travNode != delNode){
				for(; travNode.getLink().getLink() != null; travNode = travNode.getLink()){
					if(travNode.getLink() == delNode){
						result = travNode.getLink().getData();
						travNode.setLink(travNode.getLink().getLink());
						size--;
						break;
					}
				}
			}else{
				result = travNode.getData();
				head = travNode.getLink();
				travNode = null;
				size--;
			}
		}
		return result;
	}
	
	public boolean isEmpty(){
		return size == 0;
	}
	
	public T get(){
		return head.getData();
	}
	
	public int length(){
		return size;
	}
	
	public String toString(){
		String str = "";
		for(Node<T> travNode = head; travNode != null; travNode = travNode.getLink()){
			str += (travNode.getData() + " ");
		}
		return str;
	}
	
	public static void main(String[] args){
		
		SinglyLinkedList<Integer> list = new SinglyLinkedList<Integer>();
		
		for(int i = 0; i < 10; i++){
			System.out.println("The node is added: " + list.add(i));
		}
		
		System.out.println("The list length is: " + list.length());
		System.out.println("The first element is: " + list.get());
		
		System.out.println("The list is contained: " + list.toString());
		
		System.out.println("Removed the last element: " + list.remove());
		System.out.println("The list is contained: " + list.toString());
		System.out.println("Removed 5: " + list.remove(5));
		System.out.println("The list is contained: " + list.toString());
		
	}
}