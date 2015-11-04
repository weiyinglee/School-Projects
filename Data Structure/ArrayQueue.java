public class ArrayQueue<T>{
	
	//reference variables
	private int front;
	private int rear;
	private T[] queue;
	private int size;
	private static int DEFAULT_SIZE = 1000;
	
	//constructor
	public ArrayQueue(int capacity){
		if(capacity <= 0){
			throw new RuntimeException("The capacity cannot be " + capacity);
		}
		front = 0;
		rear = 0;
		queue = (T[])new Object[capacity];
		size = 0;
	}
	
	//overloaded constructor
	public ArrayQueue(){
		this(DEFAULT_SIZE);
	}
	
	/* 
	 * Implement the Queue:
	 *    void enqueue(T data);
	 *    T denqueue();
	 *    T poll();
	 */
	
	private boolean isEmpty(){
		return size == 0;
	}
	
	private boolean isFull(){
		return size >= queue.length;
	}
	
	private int nextIndex(int index){
		//if there is no more available next index(queue full)
		if(index + 1 == queue.length){
			return 0;
		}
		return index + 1;
	}
	
	public void enqueue(T data){
		
		if(isFull()){
			throw new RuntimeException("The queue is full");
		}else if(size > 0){
			//if the size is not 0 and we try to add one in, it can be added in next index as rear.
			rear = nextIndex(rear);
		}
		queue[rear] = data;
		size++;
	}
	
	public T denqueue(){
		
		T result;
		
		if(isEmpty()){
			throw new RuntimeException("The queue is empty");
		}
		result = queue[front];
		front = nextIndex(front); //advances front index, so that means we get rid of the prevouse front element
		size--;
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
