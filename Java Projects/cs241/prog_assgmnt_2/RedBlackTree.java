package edu.csupomona.cs.cs241.prog_assgmnt_2;

/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Programming Assignment #2
 * 
 * This is the implementation of Tree interface that extends Comparable
 * with APIs of 
 * add(K key, V value): to add a key and a value
 * remove(K key): remove a given key
 * lookup(K key): 
 * toPrettyString(): return a string with the values in the tree, in a pyramid fashion
 * 
 * @author Weiying Lee
 * 
 */

import java.util.ArrayList;

public class RedBlackTree<K extends Comparable<K>, V> implements Tree<K, V> {
	
	/** enum class for the color of node */
	public static enum Color { RED, BLACK }

	/* Variables */
	private Node<K, V> root;
	private int size;
	
	/** Constructor */
	public RedBlackTree(){
		root = null;
		size = 0;
	}
	
	//////////////////////////////////////////////////////
	// Helper methods/classes							//
	//////////////////////////////////////////////////////
	
	/**
	 * Inner Node class
	 * @param <K>
	 * @param <V>
	 */
	private class Node<K, V> {
		
		private K key;
		private V value;
		private Node<K, V> parentLink;
		private Node<K, V> leftLink;
		private Node<K, V> rightLink;
		private Color color;
		
		/*Constructor*/
		private Node(K key, V value, Node<K, V> parentLink, Node<K, V> leftLink, Node<K, V> rightLink, Color color){
			this.key = key;
			this.value = value;
			this.parentLink = parentLink;
			this.leftLink = leftLink;
			this.rightLink = rightLink;
			this.color = color;
		}
		
		/* Mutator */
		void setKey(K key){
			this.key = key;
		}
		void setValue(V value){
			this.value = value;
		}
		void setParentLink(Node<K, V> parentLink){
			this.parentLink = parentLink;
		}
		void setLeftLink(Node<K, V> leftLink){
			this.leftLink = leftLink;
		}
		void setRightLink(Node<K, V> rightLink){
			this.rightLink = rightLink;
		}
		void setColor(Color color){
			this.color = color;
		}
		
		/* Accessor */
		K getKey(){
			return key;
		}
		V getValue(){
			return value;
		}
		Node<K, V> getParentLink(){
			return parentLink;
		}
		Node<K, V> getLeftLink(){
			return leftLink;
		}
		Node<K, V> getRightLink(){
			return rightLink;
		}
		
		Color getColor(){
			return color;
		}
		
	}
	
	/**
	 * To look for the node matching given key
	 * @param key
	 * @return found node
	 */
	private Node<K, V> findNode(K key){
		
		Node<K, V> curNode = root;
		
		while(curNode.getLeftLink() != null && curNode.getRightLink() != null){
			if(curNode.getKey().compareTo(key) > 0){	//left
				curNode = curNode.getLeftLink();
			}else if(curNode.getKey().compareTo(key) < 0){	//right
				curNode = curNode.getRightLink();
			}else{	//found!
				return curNode;
			}
		}
		return null;
	}
	
	/**
	 * Left-rotate the subtree based on the node with given key
	 * @param key
	 */
	private void leftRotation(K key){
		
		Node<K, V> oldTopNode = findNode(key);	//old node on top
		
		//check exception
		if(oldTopNode == null)
			return;
		
		Node<K, V> newTopNode = oldTopNode.getRightLink();	//new node on top
		
		if(newTopNode.getKey() == null)
			return;
		
		Color oldColor = oldTopNode.getColor();
		Color newColor = newTopNode.getColor();
		
		/* Setting the children */
		
		//Make old top node's right child becomes new top node's left child
		oldTopNode.setRightLink(newTopNode.getLeftLink());
		if(oldTopNode.getRightLink().getKey() != null)
			oldTopNode.getRightLink().setColor(newColor);	//set as the original color
		
		if(newTopNode.getLeftLink() != null){
			newTopNode.getLeftLink().setParentLink(oldTopNode);
		}
		
		/* Setting the parents */
		
		if(oldTopNode.getParentLink() != null){
			
			newTopNode.setParentLink(oldTopNode.getParentLink());
			
			if(oldTopNode.getParentLink().getLeftLink() == oldTopNode){
				oldTopNode.getParentLink().setLeftLink(newTopNode);
				oldTopNode.getParentLink().getLeftLink().setColor(oldColor);
			}else{
				oldTopNode.getParentLink().setRightLink(newTopNode);
				oldTopNode.getParentLink().getRightLink().setColor(oldColor);
			}
		}else{
			return;
		}
		
		newTopNode.setLeftLink(oldTopNode);
		oldTopNode.setParentLink(newTopNode);
	}
	
	/**
	 * Right-rotate the subtree based on the node with given key
	 * @param key
	 */
	private void rightRotation(K key){
		
		//Same implementation as leftRotation but just reverse the left to right
		
		Node<K, V> oldTopNode = findNode(key);		//old node on top
		
		//checking exception
		if(oldTopNode == null)
			return;
		
		Node<K, V> newTopNode = oldTopNode.getLeftLink();	//new node on top
		
		if(newTopNode.getKey() == null)
			return;
		
		Color oldColor = oldTopNode.getColor();
		Color newColor = newTopNode.getColor();
		
		/* Setting children */
		
		oldTopNode.setLeftLink(newTopNode.getRightLink());
		if(oldTopNode.getLeftLink().getKey() != null)
			oldTopNode.getLeftLink().setColor(newColor);	//set as the original color
		
		if(newTopNode.getRightLink() != null){
			newTopNode.getRightLink().setParentLink(oldTopNode);
		}
		
		/* Setting parents */
		
		if(oldTopNode.getParentLink() != null){
			
			newTopNode.setParentLink(oldTopNode.getParentLink());
			
			if(oldTopNode.getParentLink().getRightLink() == oldTopNode){
				oldTopNode.getParentLink().setRightLink(newTopNode);
				oldTopNode.getParentLink().getRightLink().setColor(oldColor);
			}else{
				oldTopNode.getParentLink().setLeftLink(newTopNode);
				oldTopNode.getParentLink().getLeftLink().setColor(oldColor);
			}
		}else{
			return;
		}
		
		newTopNode.setRightLink(oldTopNode);
		oldTopNode.setParentLink(newTopNode);
		
	}
	
	/**
	 * Swaping node's data purpose
	 * @param first node
	 * @param second node
	 */
	private void swap(Node<K, V> n1, Node<K, V> n2){
		K tmpKey = n1.getKey();
		V tmpValue = n1.getValue();
		n1.setKey(n2.getKey());
		n2.setKey(tmpKey);
		n1.setValue(n2.getValue());
		n2.setValue(tmpValue);
	}
	
	//////////////////////////////////////////////////////
	// RBT implementation for Tree interface			//
	//////////////////////////////////////////////////////
	
	/**
	 * To add a key and a value
	 * @param key
	 * @param value
	 */
	@Override
	public void add(K key, V value) {
		
		//Base case: The tree is empty, just add the black root and its two black leaves
		if(size == 0){
			root = new Node<>(key, value, null, null, null, Color.BLACK);
			root.setLeftLink(new Node<>(null, null, root, null, null, Color.BLACK));
			root.setRightLink(new Node<>(null, null, root, null, null, Color.BLACK));
			size += 3;
		}
		else{
			/* Add the value in the right spot in RBT */
			
			Node<K, V> curNode = root;	//current node
			Node<K, V> markNode;		//marked node
			
			//Find the right place to add the node
			while(curNode.getLeftLink() != null && curNode.getRightLink() != null){
				if(curNode.getKey().compareTo(key) > 0){	//left
					curNode = curNode.getLeftLink();
				}else{	//right
					curNode = curNode.getRightLink();
				}
			}
			//Add the node with red first
			if(curNode.getKey() == null){
				//add the red node in order to not breaking the fifth invariant of RBT
				curNode.setKey(key);
				curNode.setValue(value);
				curNode.setLeftLink(new Node<>(null, null, curNode, null, null, Color.BLACK));
				curNode.setRightLink(new Node<>(null, null, curNode, null, null, Color.BLACK));
				
				if(curNode.getParentLink() != null && curNode.getParentLink().getColor() == Color.BLACK){
					curNode.setColor(Color.RED);
				}else {
					curNode.setColor(Color.BLACK);
				}
			}
			
			
			/* Fixing the RBT follow by its invariants */
			
			//Setting the marked node
			markNode = curNode;
			
			/* Case 1: The node's parent is red and its uncle is red */
			while(markNode.getParentLink() != null && markNode.getParentLink().getParentLink() != null && 
					markNode.getParentLink().getParentLink().getLeftLink().getColor() == Color.RED && 
					markNode.getParentLink().getParentLink().getRightLink().getColor() == Color.RED){
				
				//Color the node's parent and uncle black
				markNode.getParentLink().getParentLink().getLeftLink().setColor(Color.BLACK);
				markNode.getParentLink().getParentLink().getRightLink().setColor(Color.BLACK);
				
				//Color the node's grand-parent red
				if(markNode.getParentLink().getParentLink() != root){
					markNode.getParentLink().getParentLink().setColor(Color.RED);
				}else{
					//if the added node's parent is on the left
					if(markNode.getParentLink().getParentLink().getLeftLink() == markNode.getParentLink()){
						markNode.getParentLink().getParentLink().getLeftLink().setColor(Color.RED);
					}else if(markNode.getParentLink().getParentLink().getRightLink() == markNode.getParentLink()){
						markNode.setColor(Color.RED);
					}
				}
				
				markNode = markNode.getParentLink();
			}
			
			markNode = curNode;
			
			/* Case 2: The node's parent is red and its uncle is black and the node is a right child*/
			while(markNode.getParentLink() != null && markNode.getParentLink().getParentLink() != null && 
					((markNode.getParentLink().getParentLink().getLeftLink().getColor() == Color.RED &&
					markNode.getParentLink().getParentLink().getRightLink().getColor() == Color.BLACK) ||
					(markNode.getParentLink().getParentLink().getRightLink().getColor() == Color.RED &&
					markNode.getParentLink().getParentLink().getLeftLink().getColor() == Color.BLACK)) &&
					markNode.getParentLink().getRightLink() == markNode){
				
				if(markNode.getParentLink() != root)
					leftRotation(markNode.getParentLink().getKey());		//left rotation on the current node's parent
				
				markNode = markNode.getParentLink();
			}
			
			markNode = curNode;
			
			/* Case 3: The node's parent is red and its uncle is black and the node is a left child */
			
			while(markNode.getParentLink() != null && markNode.getParentLink().getParentLink() != null &&
					((markNode.getParentLink().getParentLink().getLeftLink().getColor() == Color.RED &&
					markNode.getParentLink().getParentLink().getRightLink().getColor() == Color.BLACK) ||
					(markNode.getParentLink().getParentLink().getRightLink().getColor() == Color.RED &&
					markNode.getParentLink().getParentLink().getLeftLink().getColor() == Color.BLACK))&&
					markNode.getParentLink().getLeftLink() == markNode){
				
				if(markNode.getParentLink().getParentLink() != root)
					rightRotation(markNode.getParentLink().getParentLink().getKey());	//make a right rotation on the grandparent
				
				if(markNode.getParentLink().getParentLink() != root){
					markNode.getParentLink().setColor(Color.BLACK); 		//Now the parent of current node is on top, should be black
					markNode.getParentLink().getParentLink().setColor(Color.RED); 		//Change the original grandparent red in order to satisfy fifth invariant
				}
				
				markNode = markNode.getParentLink();
			}
			
			size += 2;
		}
	}
	
	
	/**
	 * To remove a key
	 * @param key
	 * @return value stored in the removed key
	 */
	@Override
	public V remove(K key) {
		
		V returnValue = null;
		
		//If the tree is not empty
		if(size > 0){
			
			//Base case: there is only one root
			if(size == 3){
				if(root.getKey() == key){
					returnValue = root.getValue();
					root.setLeftLink(null);
					root.setRightLink(null);
					root = null;
					size -= 3;
				}
			}
			else{
				/* remove the node first */
				Node<K, V> curNode;		//current node
				Node<K, V> trav;		//traversing node
				Node<K, V> pointerNode;	//pointer node
				Node<K, V> markNode;	//marking node
				
				Node<K, V> foundNode = findNode(key);
				
				if(foundNode == null)
					return null;
				
				returnValue = foundNode.getValue();
				
				curNode = foundNode;
				
				if(curNode.getLeftLink().getKey() == null && curNode.getRightLink().getKey() == null){	//if it's a leaf, just remove it
					
					pointerNode = curNode.getParentLink();
					
					if(pointerNode.getKey().compareTo(key) > 0){	//the removing node is on the left
						pointerNode.setLeftLink(new Node<>(null, null, pointerNode, null, null, Color.BLACK));
					}else{	//the removing node is on the right
						pointerNode.setRightLink(new Node<>(null, null, pointerNode, null, null, Color.BLACK));
					}
					
					size -= 2;
					
				}else if(curNode.getLeftLink().getKey() != null && curNode.getRightLink().getKey() == null){	//if it has a left child, make it's child parent parent
					
					pointerNode = curNode.getLeftLink();
					
					if(curNode.getParentLink() != null){
						
						pointerNode.setParentLink(curNode.getParentLink());
						
						if(curNode.getParentLink().getKey().compareTo(pointerNode.getKey()) > 0){
							curNode.getParentLink().setLeftLink(pointerNode);
						}else{
							curNode.getParentLink().setRightLink(pointerNode);
						}
						
					}else{	//the removing node is root
						
						trav = curNode.getLeftLink();
						while(trav.getRightLink().getKey() != null){
							trav = trav.getRightLink();
						}
						
						swap(trav, curNode);
						
						pointerNode = trav.getParentLink();
						
						//remove the last element: trav, and add another black null child node
						if(trav == pointerNode.getLeftLink()){
							pointerNode.setLeftLink(new Node<>(null, null, pointerNode, null, null, Color.BLACK));
						}else
							pointerNode.setRightLink(new Node<>(null, null, pointerNode, null, null, Color.BLACK));
					}
					
					size -= 2;
					
				}else if(curNode.getLeftLink().getKey() == null && curNode.getRightLink().getKey() != null){	//if it has a right child, make it's child parent parent
					
					if(curNode.getParentLink() != null){
						
						pointerNode = curNode.getRightLink();
						
						pointerNode.setParentLink(curNode.getParentLink());
					
						if(curNode.getParentLink() != null && curNode.getParentLink().getKey().compareTo(pointerNode.getKey()) > 0){
							curNode.getParentLink().setLeftLink(pointerNode);
						}else if(curNode.getParentLink() != null && curNode.getParentLink().getKey().compareTo(pointerNode.getKey()) <= 0){
							curNode.getParentLink().setRightLink(pointerNode);
						}
						
					}else{	//The removing node is root
						
						trav = curNode.getRightLink();
						while(trav.getLeftLink().getKey() != null){
							trav = trav.getLeftLink();
						}
						
						swap(trav, curNode);
						
						pointerNode = trav.getParentLink();
						
						//remove the last element: trav, and add another black null child node
						if(trav == pointerNode.getLeftLink()){
							pointerNode.setLeftLink(new Node<>(null, null, pointerNode, null, null, Color.BLACK));
						}else
							pointerNode.setRightLink(new Node<>(null, null, pointerNode, null, null, Color.BLACK));
					}
					
					size -= 2;
					
				}else{	// none of above, look for in order predecessor
					
					trav = curNode.getLeftLink();
					while(trav.getRightLink().getKey() != null){
						trav = trav.getRightLink();
					}
					
					swap(trav, curNode);
					
					pointerNode = trav.getParentLink();
					
					//remove the last element: trav, and add another black null child node
					if(trav == pointerNode.getLeftLink()){
						pointerNode.setLeftLink(new Node<>(null, null, pointerNode, null, null, Color.BLACK));
					}else
						pointerNode.setRightLink(new Node<>(null, null, pointerNode, null, null, Color.BLACK));
					
					size -= 2;
				}
				
				//If the removed node is red, then don't do any fixing color
				if(findNode(key).getColor() == Color.RED){
					return returnValue;
				}
				
				/*  Fixing the RBT follow by its invariants */
				
				/*
				 * Case 1: The node's sibling is red
				 */
				if(pointerNode.getParentLink() != null && pointerNode.getParentLink().getLeftLink() == pointerNode){	//if the pointerNode is the left child
					while(pointerNode.getParentLink().getRightLink().getColor() == Color.RED){
						
						//left rotate on the node's parent first, then change color of the new top node(black) and the old top node(red)
						leftRotation(pointerNode.getParentLink().getKey());
						pointerNode.getParentLink().getRightLink().setColor(Color.BLACK);
						pointerNode.getParentLink().setColor(Color.RED);
					}
					
				}else if(pointerNode.getParentLink() != null && pointerNode.getParentLink().getRightLink() == pointerNode){		//if the pointerNode is the right child
					while(pointerNode.getParentLink().getLeftLink().getColor() == Color.RED){
						
						//do the opposite way like the one when pointerNode is the left child
						rightRotation(pointerNode.getParentLink().getKey());
						pointerNode.getParentLink().getLeftLink().setColor(Color.BLACK);
						pointerNode.getParentLink().setColor(Color.RED);
					}
				}
				
				/*
				 * Case 2: The node's sibling is black, 
				 * 			and it's sibling's children are both black,
				 */
				markNode = pointerNode;
				
				if(markNode.getParentLink() != null && markNode.getParentLink().getLeftLink() == markNode){	//if the pointerNode is the left child
					while(markNode.getParentLink().getRightLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getRightLink().getLeftLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getRightLink().getRightLink().getColor() == Color.BLACK){
						
						//Set the the node's sibling red, call recursively on the top of node
						markNode.getParentLink().getRightLink().setColor(Color.RED);
						markNode = markNode.getParentLink();
						
					}
				}else if(markNode.getParentLink() != null && markNode.getParentLink().getRightLink() == markNode){		//if the pointerNode is the right child
					while(markNode.getParentLink().getLeftLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getLeftLink().getLeftLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getLeftLink().getRightLink().getColor() == Color.BLACK){
						
						//do the opposite way
						markNode.getParentLink().getLeftLink().setColor(Color.RED);
						markNode = markNode.getParentLink();
					}
				}
				
				/*
				 * Case 3: The node's sibling is black,
				 * 			and it's sibling's internal child is red
				 * 			and external child is black, then we
				 * 			make the external one to be the red one first
				 * 
				 */
				
				markNode = pointerNode;
				
				if(markNode.getParentLink() != null && markNode.getParentLink().getLeftLink() == markNode){	//if the pointerNode is the left child
					while(markNode.getParentLink().getRightLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getRightLink().getLeftLink().getColor() == Color.RED &&
							markNode.getParentLink().getRightLink().getRightLink().getColor() == Color.BLACK){
						
						//make the external to be the red one
						rightRotation(markNode.getParentLink().getRightLink().getKey());
						markNode.getParentLink().getRightLink().setColor(Color.BLACK); 		//change the original internal red node into black
						markNode.getParentLink().getRightLink().getRightLink().setColor(Color.RED);	//change the new external black node into red
						
					}
				}else if(markNode.getParentLink() != null && markNode.getParentLink().getRightLink() == markNode){		//if the pointerNode is the right child
					while(markNode.getParentLink().getLeftLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getLeftLink().getLeftLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getLeftLink().getRightLink().getColor() == Color.RED){
						
						//make the external to be the red one (do the opposite way)
						leftRotation(markNode.getParentLink().getLeftLink().getKey());
						markNode.getParentLink().getLeftLink().setColor(Color.BLACK); 		//change the original internal red node into black
						markNode.getParentLink().getLeftLink().getLeftLink().setColor(Color.RED);	//change the new external black node into red
					}
				}
				
				markNode = pointerNode;
				/*
				 * Case 4: The node's sibling is black 
				 * 			and it's sibling's internal child is black
				 * 			and external child is red
				 */
				if(markNode.getParentLink() != null && markNode.getParentLink().getLeftLink() == markNode){	//if the pointerNode is the left child
					while(markNode.getParentLink().getRightLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getRightLink().getLeftLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getRightLink().getRightLink().getColor() == Color.RED){
						
						leftRotation(markNode.getParentLink().getRightLink().getKey());
						markNode.getParentLink().getParentLink().getRightLink().setColor(Color.BLACK);  //Set the right child of the new top node black
						markNode = markNode.getParentLink().getParentLink();	//new x is the new top node
						
					}
				}else if(markNode.getParentLink() != null && markNode.getParentLink().getRightLink() == markNode){		//if the pointerNode is the right child
					while(markNode.getParentLink().getLeftLink().getColor() == Color.BLACK &&
							markNode.getParentLink().getLeftLink().getLeftLink().getColor() == Color.RED &&
							markNode.getParentLink().getLeftLink().getRightLink().getColor() == Color.BLACK){
						
						//do the opposite way
						rightRotation(markNode.getParentLink().getLeftLink().getKey());
						markNode.getParentLink().getParentLink().getLeftLink().setColor(Color.BLACK);
						markNode = markNode.getParentLink().getParentLink();	//new x is the new top node
					}
				}
				
			}
		}
		
		return returnValue;
	}
	
	/**
	 * Search for the key
	 * @param key
	 * @return value stored in the removed key
	 */
	@Override
	public V lookup(K key) {
		V returnValue = null;
		returnValue = findNode(key).getValue();
		return returnValue;
	}

	
	/**
	 * Return a string with the values in the tree in a pyramid fashion
	 * @return string with the values in the tree
	 */
	@Override
	public String toPrettyString() {
		
		String str = "";									//returning string
		
		ArrayList<String> wholeList = new ArrayList<>();	//Containing data and the node color in one set
		
		String pos;				//the element position
		int index = 1;			//the position index
		int fakeSize = size;	//temperate size for traversing
		
		while(index <= fakeSize){
			
			pos = Integer.toBinaryString(index);
			Node<K, V> curNode = root;
			
			for(int i = 1; i < pos.length(); i++){
				if(curNode != null){
					if(pos.charAt(i) == '0'){	//'0' goes left
						curNode = curNode.getLeftLink();
					}else{	//'1' goes right
						curNode = curNode.getRightLink();
					}
				}
			}
			
			if(curNode != null){
				if(curNode.getKey() != null){
					wholeList.add(curNode.getValue() + "(" + curNode.getColor() + ")" + " ");
				}else{
					wholeList.add("NIL" + "(" + curNode.getColor() + ")");
				}
			}else{
				wholeList.add("     ");
				fakeSize++;
			}
			index++;
		}
		
		/* Print as pyramid fashion */
		
		//counting depth of tree
		int depth = 1;		//depth of tree
		int elemNum = 1;	//counting number of elements
		
		while(elemNum < fakeSize){
			depth++;
			elemNum += (int)Math.pow(2, depth - 1);
		}
		
		int level = 1;	//level of the pyramid structure
		
		while(level <= depth){
			
			int levelElem = (int)Math.pow(2, level - 1);		//Number of elements in the level
			
			/* adding spaces to form a pyramid structure */
			
			//level one is located at the middle of bottom level
			if(level == 1){
				for(int space = 0; space < (int)Math.pow(2, depth - 1) / 2; space++){
					str += "       ";
				}
			//Other levels are located at (half of last level - the number of elements in current level) divided by 2
			}else{
				for(int space = 0; space < ((int)Math.pow(2, depth - 1) - levelElem) / 2; space++){
					str += "       ";
				}
			}
			
			/* puting elements in the list that contains data and color into the pyramid structure */
			for(int i = 0; i < levelElem; i++){
				
				if(levelElem - 1 + i < fakeSize){
					str += wholeList.get(levelElem - 1 + i);
					str += " ";
				}
			}
			str += "\n";
			level++;
		}
		
		return str;
	}
}
