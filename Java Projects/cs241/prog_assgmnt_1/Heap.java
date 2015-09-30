package edu.csupomona.cs.cs241.prog_assgmnt_1;
/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Programming Assignment #1
 * 
 * This is the interface of Heap that extends Comparable
 * with APIs of 
 * add(V value): to add value
 * toArray(V[] array): get data in an array form
 * remove(V value):remove a value
 * fromArray(V[] array): store array of data into heap
 * getSortedContents(V[] array): transform the heap into its array representation (sorted)
 * 
 * 
 * @author Weiying Lee
 * 
 */
public interface Heap<V extends Comparable<V>> {
	
	public void add(V value);
	
	public V[] toArray(V[] array);
	
	public V remove();
	
	public void fromArray(V[] array);
	
	public V[] getSortedContents(V[] array);
	
}
