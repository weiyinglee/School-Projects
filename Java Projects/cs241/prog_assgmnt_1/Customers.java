package edu.csupomona.cs.cs241.prog_assgmnt_1;
/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Programming Assignment #1
 * 
 * This is the class that represent customers.
 * It contains the names of customers to call out,
 * and also contains their priorities.
 * There are 7 orders (High to Low):
 * 		1. VIPs
 * 		2. Advance Call:customers who called in advance
 * 		3. Seniors
 * 		4. Veterans
 * 		5. Large Groups(more than 4)
 * 		6. Families with children
 * 		7. Everyone else
 * 
 * @author Weiying Lee
 * 
 */
public class Customers implements Comparable<Customers>{
	
	private int priority;
	private String customerName;
	
	/**
	 * Constructor to set the customer name and their priority
	 * @param customerName
	 * @param priority
	 */
	public Customers(String customerName, int priority){
		this.customerName = customerName;
		this.priority = priority;
	}
	
	/**
	 * the get the priority
	 * @return priority
	 */
	public int getPriority(){
		return priority;
	}
	
	/**
	 * Overrode toString method
	 * @return customer name
	 */
	@Override
	public String toString(){
		return customerName;
	}
	
	/**
	 * Overrode compareTo method
	 * @return result of priority
	 */
	@Override
	public int compareTo(Customers customer){
		
		int result = 0;
		
		if(this == customer){	//two objects are identical
			result = 0;
		}else if(getPriority() < customer.getPriority()){	//if this's priority is greater than other customer's, the result is positive
			result = 1;
		}else if(getPriority() > customer.getPriority()){	//if this's priority is less than other customer's, the result is negative
			result = -1;
		}
		return result;
	}
}
