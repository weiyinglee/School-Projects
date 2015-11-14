import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class Node<V extends Comparable<V>> {
	
	private Node<V> parentLink;
	private  Node<V> leftLink;
	private Node<V> rightLink;
	private V data;
	
	Node(V data, Node<V> parentLink, Node<V> leftLink, Node<V> rightLink){
		this.data = data;
		this.parentLink = parentLink;
		this.leftLink = leftLink;
		this.rightLink = rightLink;
	}
	
	public void setData(V data){
		this.data = data;
	}
	
	public void setParentLink(Node<V> parentLink){
		this.parentLink = parentLink;
	}
	
	public void setLeftLink(Node<V> leftLink){
		this.leftLink = leftLink;
	}
	
	public void setRightLink(Node<V> rightLink){
		this.rightLink = rightLink;
	}
	
	public Node<V> getParentLink(){
		return parentLink;
	}
	
	public Node<V> getLeftLink(){
		return leftLink;
	}
	
	public Node<V> getRightLink(){
		return rightLink;
	}
	
	public V getData(){
		return data;
	}
}

public class BinaryTree<V extends Comparable<V>> {
	
	private Node<V> root;
	private int size;
	
	BinaryTree(){
		root = null;
		size = 0;
	}
	
	private void swap(Node<V> n1, Node<V> n2){
		V tmp = n1.getData();
		n1.setData(n2.getData());
		n2.setData(tmp);
	}
	
	private V inOrderTrav(Node<V> node){
		
		V result;
		
		if(node.getLeftLink() != null){
			inOrderTrav(node.getLeftLink());
		}
		result = node.getData();
		if(node.getRightLink() != null){
			inOrderTrav(node.getRightLink());
		}
		
		return result;
	}
	
	private V[] BFT(Node<V> node, V[] array){
		Queue<V> q = new LinkedList<>();
		ArrayList<V> list = new ArrayList<>();
		
		q.offer(node.getData());
		while(q.peek() != null){
			node.setData(q.poll());
			q.offer(node.getLeftLink().getData());
			q.offer(node.getRightLink().getData());
			list.add(node.getData());
		}
		
		for(int i = 0; i < size; i++){
			array[i] = list.get(i);
		}
		
		return array;
	}
	
	public void add(V data){
		
		if(size == 0){
			root = new Node<>(data, null, null, null);
			size++;
		}else{
			
			Node<V> curNode = root;
			
			while(curNode.getLeftLink() != null && curNode.getRightLink() != null){
				if(curNode.getData().compareTo(data) > 0){	//left
					curNode = curNode.getLeftLink();
				}else{	//right
					curNode = curNode.getRightLink();
				}
			}
			
			if(curNode.getLeftLink() == null){
				curNode.setLeftLink(new Node<>(data, curNode, null, null));
				size++;
			}else if(curNode.getRightLink() == null){
				curNode.setRightLink(new Node<>(data, curNode, null, null));
				size++;
			}
		}
	}
	
	public void remove(V data){
		if(size != 0){		//if the tree is empty
			if(size == 1){	//if there is only one root
				if(root.getData().compareTo(data) == 0){	//if the root matches the data, remove
					root = null;
					size--;
				}
			}else{
				Node<V> curNode = root;
				
				while(curNode.getLeftLink() != null && curNode.getRightLink() != null){
					if(curNode.getData().compareTo(data) > 0){	//left
						curNode = curNode.getLeftLink();
					}else if(curNode.getData().compareTo(data) < 0){	//right
						curNode = curNode.getRightLink();
					}else{	//found!
						break;
					}
				}
				
				if(curNode.getLeftLink() == null && curNode.getRightLink() == null){	//if it's a leaf, just remove it
					curNode = null;
					size--;
				}else if(curNode.getLeftLink() != null && curNode.getRightLink() == null){	//if it has a left child, make it's child parent parent
					
					curNode.getLeftLink().setParentLink(curNode.getParentLink());
					
					if(curNode.getParentLink().getData().compareTo(curNode.getLeftLink().getData()) > 0){
						curNode.getParentLink().setLeftLink(curNode.getLeftLink());
					}else{
						curNode.getParentLink().setLeftLink(curNode.getRightLink());
					}	
					size--;
				}else if(curNode.getLeftLink() == null && curNode.getRightLink() != null){	//if it has a right child, make it's child parent parent
					
					curNode.getRightLink().setParentLink(curNode.getParentLink());
					
					if(curNode.getParentLink().getData().compareTo(curNode.getRightLink().getData()) > 0){
						curNode.getParentLink().setRightLink(curNode.getLeftLink());
					}else{
						curNode.getParentLink().setRightLink(curNode.getRightLink());
					}	
					size--;
				}else{	// none of above, look for in order predecessor
					
					Node<V> trav = curNode.getLeftLink();
					while(trav.getRightLink() != null){
						trav = trav.getRightLink();
					}
					
					swap(trav, curNode);
					
					//remove the last element: trav
					trav = null;
					size--;
				}
				
			}
		}
	}
	
	public V[] toArray(V[] array){
		
		Node<V> curNode = root;	
		
		array = BFT(curNode, array);
		
		return array;
	}
	
	public int length(){
		return size;
	}
	
	public static void main(String[] args){
		
		BinaryTree<Integer> bt = new BinaryTree<>();
		
		bt.add(42);
		bt.add(17);
		bt.add(48);
		bt.add(5);
		bt.add(20);
		bt.add(45);
		bt.add(50);
		bt.add(1);
		bt.add(7);
		
		for(int i = 0; i < bt.length(); i++){
			System.out.print(bt.toArray(new Integer[9])[i] + " ");
		}
		
	}
}
