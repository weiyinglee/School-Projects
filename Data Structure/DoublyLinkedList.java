public class DoublyLinkedList <T> {
	
	/* Create a node class */
	private class Node<T>{
		
		protected T data;
		protected Node<T> prevLink;
		protected Node<T> nextLink;
		
		Node(T data, Node<T> prevLink, Node<T> nextLink){
			this.data = data;
			this.prevLink = prevLink;
			this.nextLink = nextLink;
		}
		
		protected T getData(){
			return data;
		}
		
		protected Node<T> getPrevLink(){
			return prevLink;
		}
		
		protected Node<T> getNextLink(){
			return nextLink;
		}
		
		protected void setData(T data){
			this.data = data;
		}
		
		protected void setPrevLink(Node<T> prevLink){
			this.prevLink = prevLink;
		}
		
		protected void setNextLink(Node<T> nextLink){
			this.nextLink = nextLink;
		}
		
	}
	
	/* 
		Implementation of DoublyLinkedList:
			T add(data): add to the last element, return the added value
			T remove(): remove the last element, return the removed value
			T remove(data): remove the node with data, return the removed value
			boolean isEmpty(): return boolean for whether it's empty or not
			T get(): return the first element in the list
			String toString: overrode toString method, returning the String of data chunk
	*/
	private Node<T> head;
	private Node<T> tail;
	private int length;
	
	//constructor
	public DoublyLinkedList(){
		head = null;
		tail = null;
		
	}
	
	public T add(T data){
		
	}
	
	public T remove(){
		
	}
	
	public T remove(T data){
		
	}
	
	public boolean isEmpty(){
		return length == 0;
	}
	
	public String toString(){
		
	}
	
	
}