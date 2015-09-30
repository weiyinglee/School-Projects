package edu.csupomona.cs.cs241.prog_assgmnt_1;
/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Programming Assignment #1
 * 
 * This class is the implementation of heap using the linked node.
 * The internal implementation is a tree, and the APIs of the class are
 * add(V value), 
 * toArray(V[] array), 
 * remove(V value), 
 * fromArray(V[] array)
 * getSortedContents(V[] array) with heap-sort on it.
 * 
 * 
 * @author Weiying Lee
 * 
 */
import java.util.ArrayList;
public class NodeHeap<V extends Comparable<V>> implements Heap<V> {

	/**
	 * Inner class of Nodes for heap.
	 * @param <V> generic type that is subclass of Comparable
	 */
	private class Node<V> {
		private Node<V> parentLink;	//link back to parent
		private Node<V> leftLink;	//link for left child
		private Node<V> rightLink;	//link for right child
		private V data;				
		
		protected Node(V data, Node<V> parentLink, Node<V> leftLink, Node<V> rightLink){
			this.data = data;
			this.parentLink = parentLink;
			this.leftLink = leftLink;
			this.rightLink = rightLink;
		}
		
		protected void setLeftLink(Node<V> leftLink){
			this.leftLink = leftLink;
		}
		protected void setRightLink(Node<V> rightLink){
			this.rightLink = rightLink;
		}
		protected void setParentLink(Node<V> parentLink){
			this.parentLink = parentLink;
		}
		protected void setData(V data){
			this.data = data;
		}
		
		protected Node<V> getParentLink(){
			return parentLink;
		}
		protected Node<V> getLeftLink(){
			return leftLink;
		}
		protected Node<V> getRightLink(){
			return rightLink;
		}
		protected V getData(){
			return data;
		}
	}
	
	private Node<V> root;
	private int size;
	
	public NodeHeap(){
		root = null;
		size = 0;
	}
	

	
	/**
	 * Swap the top node and bottom node
	 * @param top node
	 * @param bottom node
	 */
	private void swap(Node<V> topNode, Node<V> bottomNode){
		V tmp = topNode.getData();				//temperate variable for holding data
		
		//Exchange the data for two nodes
		topNode.setData(bottomNode.getData());
		bottomNode.setData(tmp);
	}

	/**
	 * Add the value into the heap
	 * @param value
	 */
	public void add(V value){
		
		//if the root is empty, just add directly
		if(size == 0){
			root = new Node<>(value, null, null, null);
			size++;
		}else{
			String pos = Integer.toBinaryString(size + 1);	//The position the node should go to in the tree
			Node<V> currNode = root;	//current node
			
			/*
			 * the binary number of the current position will indicates the position of element in tree
			 *  0 : means traversing to left
			 *  1: means traversing to right
			 */
			for(int i = 1; i < pos.length() - 1; i++){	 //pos.length() - 1 otherwise it would get a NullPointerException
				if(pos.charAt(i) == '0'){
					//the binary 0 means traverse to left
					currNode = currNode.getLeftLink();
				
				}else{
					//the binary 1 means traverse to right 
					currNode = currNode.getRightLink();
			
				}
			}
			
			if(currNode.getLeftLink() == null){
				//create the left child of the current node
				currNode.setLeftLink(new Node<>(value, currNode, null, null));
				
				currNode = currNode.getLeftLink();	//now the current node is the created left child
					
				//compare the current's left child value and current node value
				while(currNode.getParentLink() != null && (currNode.getParentLink().getData()).compareTo(currNode.getData()) < 0 ){ //child value greater
					//if the child value is greater than parent value, then sift-up happens
					swap(currNode.getParentLink(), currNode);
					currNode = currNode.getParentLink();
				}
				size++;
			}else if(currNode.getRightLink() == null){
				//create the right child to the current node
				currNode.setRightLink(new Node<>(value, currNode, null, null));
				
				currNode = currNode.getRightLink();	//now the current node is the created right child
					
				//compare the current's left child value and current node value
				while(currNode.getParentLink() != null && (currNode.getParentLink().getData()).compareTo(currNode.getData()) < 0){ //child value greater
					//if the child value is greater than parent value, then sift-up happens
					swap(currNode.getParentLink(), currNode);
					currNode = currNode.getParentLink();
				}
				size++;
			}
		}
	}
	
	/**
	 * Turn the heap into array
	 * @param given array
	 * @return array representation of the heap
	 */
	public V[] toArray(V[] array){
		
		ArrayList<V> list = new ArrayList<>();	//Using ArrayList to store the data first
		
		if(size == 0){
			array = null;
		}else if(size == 1){	//if only root exists
			list.add(root.getData());
		}else{
			String pos;					//the element position
			int index = 2;				//element position index
			
			list.add(root.getData());
			
			//traverse the whole tree
			while(index <= size){
				pos = Integer.toBinaryString(index);
				Node<V> currNode = root;	//current node
				
				//find the current index of element and then add to the ArrayList 
				for(int i = 1; i < pos.length(); i++){
					if(pos.charAt(i) == '0'){	//'0' goes left
						currNode = currNode.getLeftLink();
					}else{	//'1' goes right
						currNode = currNode.getRightLink();
					}
				}
				
				//Found the element of the current index, add to the ArrayList
				list.add(currNode.getData());
				
				index++;
			}
			
			//Put those elements in the list into our passed-in array
			array = list.toArray(array);
		}
		return array;
	}
	
	/**
	 * Remove value from top of the heap while doing heapify
	 * @return removed value
	 */
	public V remove(){
		V result = null;
		
		if(size != 0){
			Node<V> currNode = root;	//current node
			Node<V> lastNode;
			String pos;					//Position of the element in the tree
			
			/*
			 * Step 1:
			 * 	Swap the last element and the first element in the tree
			 */
			
			//Traverse through the tree and find the last element
			pos = Integer.toBinaryString(size);
			for(int i = 1; i < pos.length(); i++){
				if(pos.charAt(i) == '0'){	//'0' goes left
					currNode = currNode.getLeftLink();
				}else{	//'1' goes right
					currNode = currNode.getRightLink();
				}
			}
			lastNode = currNode;	//The last element in the tree
			
			//Swap with the first(which is always the root)
			swap(root, lastNode);
			
			/*
			 * Step 2:
			 * 	Remove the last element (which was the first element before swapped)
			 */
			
			Node<V> prevNode = lastNode.getParentLink();	//the node linked to last node
			result = lastNode.getData();					//This is the result we are returning for this method
			if(prevNode != null && lastNode == prevNode.getLeftLink()){
				//The last node is the left child
				prevNode.setLeftLink(null);
				lastNode.setParentLink(null);
				size--;
			}else if(prevNode != null){
				//The last node is the right child
				prevNode.setRightLink(null);
				lastNode.setParentLink(null);
				size--;
			}else if(size == 1){
				lastNode = null;
				size--;
			}
			
			/*
			 * Step 3:
			 * 	Re-do heapify by doing sift-down to get our proper heap tree again
			 */
			
			currNode = root;
			while(currNode.getLeftLink() != null){
				if((currNode.getLeftLink().getData()).compareTo(currNode.getData()) > 0 && (currNode.getRightLink() == null ||
						(currNode.getLeftLink().getData()).compareTo(currNode.getRightLink().getData()) > 0)){	
					//if the left child is greater than current node and left child is greater than right child
					swap(currNode, currNode.getLeftLink());
					currNode = currNode.getLeftLink();
					
				}else if(currNode.getRightLink() != null && (currNode.getRightLink().getData()).compareTo(currNode.getData()) > 0 &&
						(currNode.getRightLink().getData()).compareTo(currNode.getLeftLink().getData()) > 0){ 	//if the right child is greater
					//if the right child is greater than current node and right child is greater than left child
					swap(currNode, currNode.getRightLink());
					currNode = currNode.getRightLink();
				}else{
					currNode = currNode.getLeftLink();
				}
			}
			
		}	
		return result;
		
	}
	/**
	 * turn the passed in array into heap
	 * @param array
	 */
	public void fromArray(V[] array){
		for(int i = 0; i < array.length; i++){
			add(array[i]);
		}
	}
	
	/**
	 * Heap-sort the heap content
	 * @param array
	 * @return heap-sorted array
	 */
	public V[] getSortedContents(V[] array){
		/*
		 * Since every time remove() method is called, it will return the removed element
		 * which will be always the greatest element. The remove() method will also re-sort the heap
		 * after the most top element is removed. Therefore, by using remove() method to obtain element
		 * and add to array will make an array of sorted element, which meet our goal for heap-sort
		 */
		
		ArrayList<V> list = new ArrayList<>();	//put into ArrayList first
		NodeHeap<V> newHeap = new NodeHeap<>();	//The processed heap, so that we will not alter the original heap
		
		if(size == 0){
			array = null;
		}else{
			String pos;					//the element position
			int index = 2;				//element position index
			
			newHeap.add(root.getData());	//add root first 
			//traverse the whole tree
			while(index <= size){
				pos = Integer.toBinaryString(index);
				Node<V> currNode = root;	//current node
				
				//find the current index of element and then add to the ArrayList 
				for(int i = 1; i < pos.length(); i++){
					if(pos.charAt(i) == '0'){		//'0' goes left
						currNode = currNode.getLeftLink();
					}else{	//'1' goes right
						currNode = currNode.getRightLink();
					}
				}
				
				//Found the element of the current index, add to the ArrayList
				newHeap.add(currNode.getData());
				index++;
			}
		}
		
		for(int i = 0; i < size; i++){
			V tmpValue = newHeap.remove();	//temperate variable to hold data
			list.add(tmpValue);		//add the removed values to ArrayList
		}
		
		//re-put into given array
		array = list.toArray(array);
		
		return array;
	}
}
