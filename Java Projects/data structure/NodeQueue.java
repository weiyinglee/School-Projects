public class NodeQueue<T> {
	
	/* Create Node class */
	private class Node<T>{
		
		private T data;
		private Node<T> link;
		
		Node(T data, Node<T> link){
			this.data = data;
			this.link = link;
		}
		
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
	 * Implement the Queue:
	 *    void enqueue(T data);
	 *    T denqueue();
	 *    T poll();
	 */
	
	private Node<T> head;
	
	//constructor
	public NodeQueue(){
		head = null;
	}
	
	//methods
	private boolean isEmpty(){
		return head == null;
	}
	
	public void enqueue(T data){
		
		if(isEmpty()){
			head = new Node<>(data, null);
		}else{
			Node<T> travNode = head;
			while(travNode.getLink() != null){
				travNode = travNode.getLink();
			}
			travNode.setLink(new Node<>(data, null));
		}
	}
	
	public T denqueue(){
		
		T result = null;
		Node<T> nextNode = head.getLink();
		Node<T> delNode = head;
		
		if(isEmpty()){
			throw new RuntimeException("The queue is empty, can not denqueue.");
		}
		
		result = delNode.getData();
		delNode = null;
		head = nextNode;
		
		return result;
	}
	
	
	public static void main(String[] args){
		
		NodeQueue<Integer> q = new NodeQueue<>();
		
		for(int i = 0; i < 10; i++){
			q.enqueue(i);
		}
		
		try{
			for(int i = 0; i < 11; i++){
				System.out.println("Take out " + q.denqueue());
			}
		}catch(RuntimeException e){
			System.out.println("There is no more element in the queue");
		}
	}
}
