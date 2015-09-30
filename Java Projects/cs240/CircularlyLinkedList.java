package cs240;
/*
 * Name:	Weiying Lee
 * E.C.:		#1
 * Course:	CS-240-02-w15
 * Due:		Feb.19.2015
 * 
 * Description:
 * 		Implement generic class for the CircularlyLinkedList ADT
 */

class Node<T> {
	private T data;
	private Node<T> link;
	
	public Node(T data, Node<T> link) {
		this.data= data;
		this.link = link;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public T getData(){
		return data;
	}
	
	public void setLink(Node<T> link) {
		this.link = link;
	}
	
	public Node<T> getLink() {
		return link;
	}
}

public class CircularlyLinkedList <T> {
	private Node<T> cursor;
	
	public CircularlyLinkedList() {
		cursor = null;
	}
	
	public void add(T data) {
		if(cursor == null)
			cursor = new Node<>(data, null);
		else{			
			Node<T> trav = cursor;
			while(trav.getLink() != null){
				trav = trav.getLink();
			}
			trav.setLink(new Node<>(data,null));
		}
	}
	
	public void remove() {
		if(cursor != null){
			Node<T> trav = cursor;
			while(trav.getLink().getLink() != null){
				trav = trav.getLink();
			}
			trav.setLink(null);
		}
	}
	
	public void advance() {
		if(cursor != null)
			cursor = cursor.getLink();
	}
	
	public T get() {
		return cursor.getData();
	}
	
	public String toString() {
		String str = "";
		for(Node<T> trav = cursor; trav != null; trav = trav.getLink())
			str += trav.getData() + " ";
		return str;
	}
	
	public static void main(String[] args){
		CircularlyLinkedList<Integer> link1 = new CircularlyLinkedList<>();
		CircularlyLinkedList<String> link2 = new CircularlyLinkedList<>();
	}
}
