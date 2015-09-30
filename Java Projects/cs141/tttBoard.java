package cs141;

public class tttBoard 

{
	public static final char BLANK = 0;

	private int position;

	private char mark;

	private char[] place;

	
	public tttBoard()
	{
	 	position = 0;

		mark = 0;

		place = new char[9];

	}
	
	public boolean winner()
	
	{

		// case with top horizontal

		if(place[0] == place[1] && place[1] == place[2] &&

		   place[0] != 0 && place[1] != 0 && place[2] != 0)

		{
			
			return true;

		}

		// case with middle horizontal

		else if(place[3] == place[4] && place[4] == place[5] &&

			place[3] != 0 && place[4] != 0 && place[5] != 0)

		{

			return true;

		}

		// case with bottom horizontal

		else if(place[6] == place[7] && place[7] == place[8] &&

				   place[6] != 0 && place[7] != 0 && place[8] != 0)

		{

			return true;

		}

		// case with left vertical

		else if(place[0] == place[3] && place[3] == place[6] &&

				   place[0] != 0 && place[3] != 0 && place[6] != 0)

		{

			return true;

		}

		// case with middle vertical

		else if(place[1] == place[4] && place[4] == place[7] &&

				   place[1] != 0 && place[4] != 0 && place[7] != 0)

		{

			return true;

		}

		// case with right vertical

		else if(place[2] == place[5] && place[5] == place[8] &&

				   place[2] != 0 && place[5] != 0 && place[8] != 0)

		{

			return true;

		}

		// case with top left to bottom right

		else if(place[0] == place[4] && place[4] == place[8] &&

				   place[0] != 0 && place[4] != 0 && place[8] != 0)

		{

			return true;

		}

		// case with top right to bottom left

		else if(place[2] == place[4] && place[4] == place[6] &&

				   place[2] != 0 && place[4] != 0 && place[6] != 0)

		{

			return true;

		}

		else

		{

			return false;

		}

	}

	
	public boolean full()
	{

		if(place[0] == mark && place[1] == mark && place[2] == mark &&

			place[3] == mark && place[4] == mark && place[5] == mark &&

			place[6] == mark && place[7] == mark && place[8] == mark &&

			place[0] != 0 && place[1] != 0 && place[2] != 0 &&

			place[3] != 0 && place[4] != 0 && place[5] != 0 &&

			place[6] != 0 && place[7] != 0 && place[8] != 0)

		{

			return true;

		}

		else

			return false;

	}

	
	public boolean open(int p)

	{

		if(p == 1 && place[0] == 0)

			return true;

		else if(p == 2 && place[1] == 0)

			return true;

		else if(p == 3 && place[2] == 0)

			return true;

		else if(p == 4 && place[3] == 0)

			return true;

		else if(p == 5 && place[4] == 0)

			return true;

		else if(p == 6 && place[5] == 0)

			return true;

		else if(p == 7 && place[6] == 0)

			return true;

		else if(p == 8 && place[7] == 0)

			return true;

		else if(p == 9 && place[8] == 0)

			return true;

		else

			return false;

	}

	
	public char getWinnerMark()

	{

		if(winner())

			return mark;

		else

			return 0;

	}

	
	public void clear()

	{

		//clear the screen and set the cursor to (0,0)

		System.out.print( "\033[2J" );

		System.out.print( "\033[0;0H" );

	}

	
	public char[] set(int p, char m)

	{

		position = p;

		mark = m;

		
		switch(p)

		{

		case 1:

			place[0] = m;

			break;

		case 2:

			place[1] = m;

			break;

		case 3:

			place[2] = m;

			break;

		case 4:

			place[3] = m;

			break;

		case 5:

			place[4] = m;

			break;

		case 6:

			place[5] = m;

			break;

		case 7:

			place[6] = m;

			break;

		case 8:

			place[7] = m;

			break;

		case 9:

			place[8] = m;

			break;

		}

		
		return place;

	}


	public char get(int i)
 
	{

		position = i;

		return place[i-1];

	}

	

	public String toString()

	{

		return " " + place[0] + " | " + place[1] + " | " + place[2] + "\n" +

				"-----------" + "\n" +

				" " + place[3] + " | " + place[4] + " | " + place[5] + "\n" +

				"-----------" + "\n" +

				" " + place[6] + " | " + place[7] + " | " + place[8] + "\n";

	}



}


