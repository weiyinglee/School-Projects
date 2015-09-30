package cs141;

public class project3 
{
	public static void main(String[] args) 
	{
		int N = 42;
		put_in_data(N);
		
	}
	
	public static void put_in_data(int N)
	{
		int row;

		row = (int)(N/5);
		for (int x = 0; x < row; x ++)
		{
			for (int y = 0; y < 5; y++)
			{
				System.out.printf("%15d ", getValue(y + 5 * x));
			}
			System.out.println();
		}
		if (N % 5 != 0)
		{
			for (int w = 0; w < N % 5; w++)
			{
				System.out.printf("%15d ", getValue((N - N%5) + w));
			}
		}		
			
	}
	public static long getValue(long n)
	{
		if (n == 0)
			return 1;
		else if (n == 1)
			return 2;
		else
		{
			return 2 * getValue(n-1);
		}
	}
	
	
}


