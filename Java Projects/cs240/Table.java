package cs240;

/*
 *  Name:		Lee, Weiying
 *  Homework:	#1
 *  Due:		Jan.27 2015
 *  Course:		cs-240-02-w15
 *  
 *  Description:
 *  			Add two class methods to the Matrix class,
 *  			1. multiplication: A = B * C and A = c * B
 *  			2. addition: A = B + C
 *  			3. Throw invalid dimensions as IllegalArgumentException
 *  			4. Provide a class method main to verify 1, 2, and 3.
 */
import java.io.*;
import java.util.StringTokenizer;

public class Table<K,E> {
	
	private int manyElement;
	private int collision;
	private Object[] Key;
	private Object[] Data;
	private boolean hasBeenUsed[];
	
	public Table(int capacity) {
		if(capacity <= 0)
			throw new IllegalStateException("The capacity is negative or zero.");
		else {
			Key = new Object[capacity];
			Data = new Object[capacity];
			hasBeenUsed = new boolean[capacity];
		}
	}
	
	private int hash(Object key) {
		return Math.abs(key.hashCode()) % Data.length;
	}
	
	private int nextIndex(int index) {
		if(index+1 == Data.length)
			return 0;
		else
			return index+1;
	}
	
	private int findIndex(K key) {
		int count = 0;
		int index = hash(key);
		
		while((count < Key.length) && (hasBeenUsed[index])){
			if(key.equals(Key[index]))
				return index;
			count++;
			index = nextIndex(index);
		}
		return -1;
	}
	
	public boolean containsKey(K key) {
		return findIndex(key) != -1;
	}
	
	@SuppressWarnings("unchecked")
	public E get(K key) {
		int index = findIndex(key);
		
		if(index != -1)
			return (E)Data[index];
		else
			return null;
	}
	
	@SuppressWarnings("unchecked")
	public E put(K key, E element) {
		int index = findIndex(key);
		E copy;
		
		if(index != -1){
			copy = (E)Data[index];
			Data[index] = element;
			return copy;
		}
		else if(manyElement < Data.length){
			index = hash(key);
			//The Key has already existed, therefore collision happenes
			while(Key[index] != null){
				index = nextIndex(index);
				//counting the collision amount
				collision++;
			}
			Key[index] = key;
			Data[index] = element;
			hasBeenUsed[index] = true;
			manyElement++;
			return null;	
		}
		else
			throw new IllegalStateException("Table is full");
	}
	
	@SuppressWarnings("unchecked")
	public E remove(K key) {
		int index = findIndex(key);
		E copy = null;
		
		if(index != -1) {
			copy = (E)Data[index];
			Key[index] = null;
			Data[index] = null;
			manyElement--;
		}
		return copy;
	}
	
	public String toString(){
		return Key.length + " " + manyElement + " " + collision + " " + 
				(double)collision/manyElement;
	}
	
	public static void main(String[] args){
		Table<String, Integer> tb = new Table<String, Integer>(1000);
		String line, word;
		int count = 0;
		StringTokenizer st;
	
		try{
			FileReader fr = new FileReader("src/cs240/declare.txt");
			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null){
				st = new StringTokenizer(line);
				while(st.hasMoreTokens()){
					word = st.nextToken();
					count++;
					tb.put(word, word.hashCode());
				}
			}
			fr.close();
			br.close();
			System.out.println(count);
			System.out.println(tb);
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
