package edu.csupomona.cs.cs241.prog_assgmnt_1;
/**
 * CS 241: Data Structures and Algorithms II
 * Professor: Edwin Rodr&iacute;guez
 * 
 * Programming Assignment #1
 * 
 * This is the class for table-assigning, and interacting with users. 
 * When the new data is inputed, it is inserted into the heap
 * Store the customer data and structured based on their priority.
 * @author Weiying Lee
 *  * 
 */
import java.util.Scanner;
import java.util.InputMismatchException;

public class TableAssign {
	
	public static void main(String[] args){
		
		NodeHeap<Customers> heap = new NodeHeap<>();	//Heap for storing customer data
		Scanner keyboard = new Scanner(System.in);		//for user to input
		String customerName;		//Customer's name
		int priority, command;		//priority, and the input of command
		
		OuterLoop: while(true){		//label the loop as OuterLoop
			try{
				System.out.print("\nEnter the command number(1.Add a customer. 2.Get the next seat. 3.Exit): ");
				command = keyboard.nextInt();
				keyboard.nextLine();		//To restore the new line character
				
				switch(command){
					case 1:
						InnerLoop: while(true){		//label the loop as InnerLoop
							try{
								System.out.print("\nWhat is your name: ");
								customerName = keyboard.nextLine();
								
								System.out.println("\n(1. VIP, 2. Advance Call, 3.Seniors, 4. Veterans, 5. Large Groups(more than 4)" +
												" 6. Families with children, 7. Everyone else)");
								System.out.print("What is the priority: ");
								priority = keyboard.nextInt();
								
								//Handle the base case here
								if(priority > 7 || priority <= 0){
									System.out.println("Please enter the given priority\n");
									keyboard.nextLine();	//To restore the new line character
								}else{
									heap.add(new Customers(customerName, priority));	//put the customer data into heap
									break InnerLoop;
								}
							}catch(InputMismatchException e){
								System.out.println("Enter the correct value\n");
								keyboard.nextLine();		//To restore the new line character
							}
						}
						break;
					case 2:
						System.out.println("The next customer for next available seat is: " + 
												heap.remove());	//get the the next seat while removing the customer from heap
						break;
					case 3:
						break OuterLoop;
					default:
						System.out.println("Enter the given command number\n");
						break;
				}
				
			}catch(InputMismatchException e){
				System.out.println("Enter the correct value\n");
				keyboard.nextLine();		//To restore the new line character
			}
		}
		
		
	}
}
