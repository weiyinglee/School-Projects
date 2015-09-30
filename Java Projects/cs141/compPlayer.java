package cs141;


public class compPlayer 
{
	 private String name = "";

	    private char mark = 0;

	    private tttBoard board;


	    public compPlayer( String n, char m, tttBoard B ) {

		name = n;

		mark = m;

		board = B;

	    }


	    public String toString() {

		return name + "( " + mark + " )";

	    }


	    public char getMark() {

		return mark;

	    }


	    public void move() throws Exception {

		// Take easy way out for now and just move in first

		//   available position

		int position = 1;

		boolean done = false;


		if ( board.full() )

		    throw new Exception( "Unable to move, board full." );

		while ( ! done ) {

		    if ( board.open( position ) ) {

			board.set( position, mark );

			done = true;

		    } else {

			position++;

		    }

		}

	    }


	    public void move_smart() throws Exception {


		if ( board.full() ) {

		    throw new Exception( "Unable to move, board full." );

		}


		// See if we need to block, else take first open position

		
		// Check row 1, left to right

		if ( board.get( 1 ) != tttBoard.BLANK &&

		     board.get( 2 ) != tttBoard.BLANK &&

		     board.get( 3 ) == tttBoard.BLANK &&

		     board.get( 1 ) == board.get( 2 ) &&

		     board.get( 1 ) != mark ) {

		    // Positions 1 and 2 are not blank, 3 is, AND

		    //  1 and 2 are not me...need to block!

		    board.set( 3, mark );

		}

		
		// ...


		// Check col 3, down

		if ( board.get( 3 ) != tttBoard.BLANK &&

		     board.get( 6 ) != tttBoard.BLANK &&

		     board.get( 9 ) == tttBoard.BLANK &&

		     board.get( 3 ) == board.get( 6 ) &&

		     board.get( 3 ) != mark ) {

		    // Positions 1 and 2 are not blank, 3 is, AND

		    //  1 and 2 are not me...need to block!

		    board.set( 9, mark );

		}

		
		// ... and more "intelligent" processing


	    }




}
