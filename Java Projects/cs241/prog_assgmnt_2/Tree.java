package edu.csupomona.cs.cs241.prog_assgmnt_2;
/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Programming Assignment #2
 * 
 * This is the interface of Red-Black Tree that extends Comparable
 * with APIs of 
 * add(K key, V value): to add a key and a value
 * remove(K key): remove a given key
 * lookup(K key): 
 * toPrettyString(): return a string with the values in the tree, in a pyramid fashion
 * 
 * @author Weiying Lee
 * 
 */
public interface Tree<K extends Comparable<K>, V> {
	
	public void add(K key, V value);
	
	public V remove(K key);
	
	public V lookup(K key);
	
	public String toPrettyString();
}
