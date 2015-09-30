package cs141;

//Lee, Weiying {weiyinglee}
//CS 141 03
//Project #1 : Draw It 3D
//

import java.util.Scanner;

public class project1 
{	
	public static void main(String[] args)
	{	
		
		Scanner keyboard = new Scanner(System.in);		
		char[][] table = new char[70][100];
		
		getData(keyboard, table);
		printResult(table);
	}
	
	public static char[][] getData(Scanner keyboard, char[][] table) 
	{	
		int x= 0, y = 0, z = 0, PX = 0, PY = 0;
		while (x != -1 && y != -1 && z != -1)
		{			
			x = keyboard.nextInt();
			y = keyboard.nextInt();
			z = keyboard.nextInt();
			PX = (int)(x + 0.5 * z);
			PY = (int)(y + 0.5 * z);
			try{
				table[PX][PY] = '.';
			}
			catch(Exception e){
			}
		}
		
		return table;
	}

	public static void printResult(char[][] array)
	{
		for(int row = 0; row < array.length; row++)
		{
			for(int col = 0; col <array.length; col++)
			{
				if (array[row][col] == 0)
				{
					System.out.print(" ");
				}
				else
					System.out.print(array[row][col]);
				
			}
			System.out.println();
		}
	}
		
}
