package cs141;
//Lee, Weiying {weiyinglee}
//CS 141 03
//Project #2 : Bouncy Ball
//10/23/2014
import java.util.*;

public class project2 
{

	public static void main(String[] args) throws Exception
	{	
		Scanner keyboard = new Scanner(System.in);
		int width = keyboard.nextInt();
		int height = keyboard.nextInt();
		char[][] boxData = new char[height][width];
		
		make_box(keyboard, width, height, boxData);
		place_ball(width, height, boxData);
		
			
	}	
	
	public static char[][] make_box(Scanner k, int width, int height, char[][] boxData) throws Exception
	{
		
		k.nextLine();
		
		for (int r = 0; r < height; r++)
		{
			String data = k.nextLine();
			for (int c = 0; c < width; c++)
			{
				boxData[r][c] = data.charAt(c);
			}
		}

		create_ball(boxData, width, height);
		
		return boxData;
	
			
	}
	
	public static void place_ball(int w, int h, char[][] boxData)
	{
		//int msec = Integer.parseInt(args);
		for (int r = 0; r < h; r++)
		{
			for(int c = 0; c < w; c++)
			{ 	
				// printing out the boarder
				System.out.print(boxData[r][c]);
			}
			System.out.println();
		}
		
		
	}
	public static char[][] create_ball(char[][] array, int w, int h)
	{
		char b = 'O';
		for(int r = 0; r < h-2; r++)
		{
			for (int c = 0; c < h-2; c++)
			{
				cls();
				r = r + 1;
				array[r][c+1] = b;
				try
				{
					Thread.sleep(100);
				}
				catch(Exception e)
				{
					
				}
			}

		}
		System.out.println();
		
		
		return array;
	}
	
	public static void cls()
	{
		System.out.print( "\033[2J" );
		System.out.print( "\033[0;0H" );
	}
}

		
	

